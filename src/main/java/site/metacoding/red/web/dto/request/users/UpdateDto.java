package site.metacoding.red.web.dto.request.users;
// 프로젝트 red

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UpdateDto {
	private String password;
	private String email;
}
