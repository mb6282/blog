package site.metacoding.red.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import site.metacoding.red.domain.users.Users;
import site.metacoding.red.domain.users.UsersDao;
import site.metacoding.red.web.dto.request.users.JoinDto;
import site.metacoding.red.web.dto.request.users.LoginDto;

@RequiredArgsConstructor
@Controller
public class UsersController {
	//회원가입, 로그인 등 인증에 필요한 주소는
	//주소에 Entity(table 명)을 붙이지 않는 경우가 많다.	
	
	private final HttpSession session; // 스프링이 서버시작시에 IOC 컨테이너에 띄움
	private final UsersDao usersDao;
	
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
		//DB에서 받은 데이터면 PS를 붙여주자.
		//그래야 DB에서 받은건지, 사용자에게 받은건지 구분이 된다.
		if(usersPS !=null){ // 인증됨
			session.setAttribute("principal", usersPS);
			//인증->인가에서 접근 주체=인정된 유저를 Principal이라고 함
			return"redirect:/";
		}else { // 인증안됨
		return "redirect:/loginForm";
		}
	}
	
	@GetMapping("/logout")
	public String logout() {
		session.invalidate();
		return "redirect:/";
	}

}