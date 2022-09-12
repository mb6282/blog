package site.metacoding.red.web.dto.request.boards;

import lombok.Getter;
import lombok.Setter;
import site.metacoding.red.domain.boards.Boards;

@Setter
@Getter
public class UpdateDto {
	private String title;
	private String content;
	
	//영속화-변경-수정을 사용하려면
	//toEntity는 없어도 겟터 세터는 있어야 한다.
	public Boards toEntity(Integer id) {
		Boards boards = new Boards(this.title, this.content, id);
		return boards;
	}
}
