package site.metacoding.red.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import site.metacoding.red.domain.boards.Boards;
import site.metacoding.red.domain.boards.BoardsDao;
import site.metacoding.red.domain.boards.mapper.MainView;
import site.metacoding.red.domain.boards.mapper.PagingView;
import site.metacoding.red.domain.users.Users;
import site.metacoding.red.web.dto.request.boards.WriteDto;

@RequiredArgsConstructor
@Controller
public class BoardsController {

	private final HttpSession session;
	private final BoardsDao boardsDao;

	// http://localhost:8000/
	// http://localhost:8000/?page=
	// 쿼리스트림이 없거나 키값이 없어서 Integer가 null이면 초깃값을 0으로 넣어줘야 함
	@GetMapping({ "/", "/boards" })
	public String getBoardList(Model model, Integer page) { // 0->0, 1->10, 2->20 연산하여 startNum을 만들어줘야 함
		// 애초에 쿼리스트림이나 키값이 없을 때 null이 나오는지 ""인지 확인해봐야함
		// System.out.println("page : "+page);
		if (page == null)
			page = 0; // if문에서 한줄은 {}안써도 됨
		Integer startNum = page * 3;

		List<MainView> boardsList = boardsDao.findAll(startNum);
		PagingView paging = boardsDao.paging(page);

		// DB에서 쿼리를 만들어 넣어주기 힘드니까 Java에서 나머지 변수들을 설정

		paging.makeBlockInfo();

		model.addAttribute("boardsList", boardsList);
		model.addAttribute("paging", paging);
		return "boards/main";
	}

	@GetMapping("/boards/{id}")
	public String getBoardDetail(@PathVariable Integer id, Model model) {
		model.addAttribute("boards", boardsDao.findById(id));
		return "boards/detail";
	}

	@GetMapping("/boards/writeForm")
	public String writeForm() {
		Users principal = (Users) session.getAttribute("principal");
		// setAttribute는 Object로 값을 받는데 UserPS를 담았음
		// 그런데 세션의 데이터를 받는 타입도 Object로 하면
		// Object o = session.getAttribute("principal");
		// o.getUsersId(); 실행 불가
		// getUsersId 메서드는 오브젝트 타입이 아니므로 Users로 받아야 함
		// 고로 object로 받은 session도 다운캐스팅
		if (principal == null) {
			return "redirect:/loginForm";
		}
		return "boards/writeForm";
		// 조건문 : 인증(세션의 값이 있는지만)을 검사하는 것
		// 관리자만 적을 수 있는 페이지 처럼 동호수 검사같은 것은 권한 체크(인가) 필요
	}

	@PostMapping("/boards")
	public String writeBoards(WriteDto writeDto) {
		// 메서드 이름을 지을 때는 동사를 먼저 적고 명사를 적자
		// userId는 FK라 없으면 insert가 되지 않는다
		// 그런데 WriteDto는 userId를 들고 있지 않기 때문에(통신에서는 무조건 받을 데이터만 적음)
		// 받기는 WriteDto로 받되 Boards로 옮겨 받아야 함

		// userId는 session의 principal에 있음
		Users principal = (Users) session.getAttribute("principal");

		if (principal == null) {
			return "redirect:/loginForm";
		} // return 되면 메서드는 종료되니까 여기서 쳐내
			// if else 조건문 쓰면 문장이 복잡해지니까 if까지만으로 쳐내기

		// dto를 entity(boards)로 변환해서 인수로 담아준다.
		boardsDao.insert(writeDto.toEntity(principal.getId()));
		return "redirect:/";

		// 연습용
		// 1번 세션에 접근해서 세션값을 확인한다. 그때 Users로 다운캐스팅하고 키값은 principal로 한다.
		// 2번 principal null인지 확인하고 null이면 loginForm 리다이렉션해준다.

		// 3번 BoardsDao에 접근해서 insert 메서드를 호출한다.
		// 조건 : dto를 entity로 변환해서 인수로 담아준다.
		// 조건 : entity에는 세션의 principal에 getId가 필요하다.
	}

	@PostMapping("/boards/{id}/delete")
	public String deleteBoards(@PathVariable Integer id) {

		Users principal = (Users) session.getAttribute("principal");
		
		// 영속화 : 하나의 코드로 떼어서 사용하기 위해 principal 뒤에 적었음 (boardsPS와 if문 묶음)
		Boards boardsPS = boardsDao.findById(id);
		if (boardsPS == null) { // if는 비정상 로직을 타겟 해서 걸러내는 필터 역할을 하는 게 좋음
			return "redirect:/boards/" + id;
		}

		// 인증 체크
		if (principal == null) {
			return "redirect:/loginForm";
		}

		// 권한 체크 ( 세션 principal.getId() 와 boardsPS의 userId를 비교)
		if (principal.getId() != boardsPS.getUsersId()) {
			return "redirect:/boards/" + id;
		}
		
		boardsDao.delete(id);
		return "redirect:/";
	}
}
