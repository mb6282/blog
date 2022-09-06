package site.metacoding.red.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import site.metacoding.red.domain.users.UsersDao;
import site.metacoding.red.web.dto.request.users.JoinDto;

@RequiredArgsConstructor
@Controller
public class UsersController {
	//회원가입, 로그인 등 인증에 필요한 주소는
	//주소에 Entity(table 명)을 붙이지 않는 경우가 많다.	
	
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

}
