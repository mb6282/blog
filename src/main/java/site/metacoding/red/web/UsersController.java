package site.metacoding.red.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import site.metacoding.red.domain.boards.BoardsDao;
import site.metacoding.red.domain.users.Users;
import site.metacoding.red.domain.users.UsersDao;
import site.metacoding.red.web.dto.request.users.JoinDto;
import site.metacoding.red.web.dto.request.users.LoginDto;
import site.metacoding.red.web.dto.request.users.UpdateDto;

@RequiredArgsConstructor
@Controller
public class UsersController {
	// 회원가입, 로그인 등 인증에 필요한 주소는
	// 주소에 Entity(table 명)을 붙이지 않는 경우가 많다.

	private final HttpSession session; // 스프링이 서버시작시에 IOC 컨테이너에 띄움
	private final UsersDao usersDao;
	private final BoardsDao boardsDao;

	@GetMapping("/joinForm")
	public String joinForm() {
		return "users/joinForm";
	}

	@PostMapping("/join")
	public String join(JoinDto joinDto) {
		usersDao.insert(joinDto);
		return "redirect:/loginForm";
	}

	@GetMapping("/loginForm")
	public String loginForm() {
		return "users/loginForm";
	}

	@PostMapping("/login")
	public String login(LoginDto loginDto) {
		Users usersPS = usersDao.login(loginDto);
		// DB에서 받은 데이터면 PS를 붙여주자.
		// 그래야 DB에서 받은건지, 사용자에게 받은건지 구분이 된다.
		if (usersPS != null) { // 인증됨
			session.setAttribute("principal", usersPS); // 키값은 무조건 String
			// 인증->인가에서 접근 주체=인정된 유저를 Principal이라고 함
			return "redirect:/";
		} else { // 인증안됨
			return "redirect:/loginForm";
		}
	}

	@GetMapping("/logout")
	public String logout() {
		session.invalidate();
		return "redirect:/";
	}

	@GetMapping("/users/{id}")
	public String updateForm(@PathVariable Integer id, Model model) {
		Users principal = (Users) session.getAttribute("principal");
		Users usersPS = usersDao.findById(id);
		//비정상 요청 체크
		if (usersPS == null) {
			return "errors/badPage";
		}

		// 인증 체크
		if (principal == null) {
			return "redirect:/loginForm";
		}

		// 권한 체크
		if (principal.getId() != usersPS.getId()) {
			return "errors/badPage";
		}
		model.addAttribute("users", usersPS);
		return "users/updateForm";
	}

	@PostMapping("/users/{id}/update")
	public String update(@PathVariable Integer id, UpdateDto updateDto) {
		Users principal = (Users) session.getAttribute("principal");

		// 1. 영속화
		Users usersPS = usersDao.findById(id);

		// 비정상 요청 체크
		if (usersPS == null) {
			return "errors/badPage";
		}

		// 인증 체크
		if (principal == null) {
			return "redirect:/loginForm";
		}

		// 권한 체크
		if (principal.getId() != usersPS.getId()) {
			return "errors/badPage";
		}
		// 2. 영속화된 객체 변경
		usersPS.update(updateDto);

		// 3. 디비 수행
		usersDao.update(usersPS);

		// 4. session값 변경
		session.setAttribute("principal", usersPS);
		return "redirect:/users/" + id;
	}

	@PostMapping("/users/{id}/delete")
	public String deleteBoards(@PathVariable Integer id) {
		Users principal = (Users) session.getAttribute("principal");

		// 영속화 : 하나의 코드로 떼어서 사용하기 위해 principal 뒤에 적었음 (boardsPS와 if문 묶음)
		Users usersPS = usersDao.findById(id);
		// 비정상 요청 체크
		if (usersPS == null) { // if는 비정상 로직을 타겟 해서 걸러내는 필터 역할을 하는 게 좋음
			return "errors/badPage";
		}

		// 인증 체크
		if (principal == null) {
			return "redirect:/loginForm";
		}

		// 권한 체크 ( 세션 principal.getId() 와 boardsPS의 userId를 비교)
		if (principal.getId() != usersPS.getId()) {
			return "errors/badPage";
		}
		
		boardsDao.updateByUsersId(id);
		usersDao.deleteById(id);
		session.invalidate();
		return "redirect:/";
	}
}