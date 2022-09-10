package site.metacoding.red.domain.boards;

import java.sql.Timestamp;

import lombok.Getter;

@Getter
public class Boards {
	private Integer id;
	private String title;
	private String content;
	private Integer usersId;
	private Timestamp createdAt;
	
	//import lombok.AllArgsConstructor; <-정확한 생성자X
	//final도 X : return값을 돌려주는 생성자이기 때문에 값을 바꿀 수 없기 때문
	//값을 받을 직접적인 생성자를 만들어주는게 나음
	
	public Boards(String title, String content, Integer usersId) {
		this.title = title;
		this.content = content;
		this.usersId = usersId;
	}
}
