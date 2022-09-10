package site.metacoding.red.domain.boards;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//NoArgsConstructor랑 Setter : findById로 Setter->getter될 값을 받을 디폴트 생성자 생성
//setter를 받을 디폴트 생성자가 없으면 지금 Boards가 new가 안됨 (아래의 생성자 때문에)
//그리고 mapper로 DAO new해줄 때 Spring 전략이 디폴트 생성자에 setter getter로 받는 것으로 설정되어 있음
//내가 만든다면 생성자를 만들어서 주입할지 NoArgsConstructor-Setter-Getter로 할건지 설정할 수 있음

@NoArgsConstructor
@Setter
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
