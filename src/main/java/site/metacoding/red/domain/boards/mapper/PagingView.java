package site.metacoding.red.domain.boards.mapper;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PagingView {
	private Integer startNum;
	private Integer totalCount;
	private Integer totalPage;
	private Integer currentPage;
	private boolean isLast; //getter가 만들어지면 getisLast()가 아닌 isLast() 이름으로 만들어짐 -> el표현식에서는 last로 찾아짐
	private boolean isFirst; //getter가 만들어지면 getisFirst()가 아닌isFirst() 이름으로 만들어짐 -> el표현식에서는 last로 찾아짐
}
