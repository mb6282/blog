package site.metacoding.red.domain.boards.mapper;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PagingView {
	private Integer currentBlock; // 변수
	private Integer blockCount; // 상수 / 한 블락의 페이지 넘버 수 개수(5) 1-5, 6-10
	private Integer startPageNum; // 변수 1 -> 6 -> 11
	private Integer lastPageNum; // 변수 5 -> 10 -> 15
	private Integer startNum;
	private Integer totalCount;
	private Integer totalPage;
	private Integer currentPage;
	private boolean isLast; // getter가 만들어지면 getisLast()가 아닌 isLast() 이름으로 만들어짐 -> el표현식에서는 last로 찾아짐
	private boolean isFirst; // getter가 만들어지면 getisFirst()가 아닌isFirst() 이름으로 만들어짐 -> el표현식에서는 last로 찾아짐

	public void makeBlockInfo() {
		this.blockCount = 5;

		this.currentBlock = currentPage / blockCount;
		this.startPageNum = 1 + blockCount * currentBlock;
		this.lastPageNum = (startPageNum + blockCount) -1;

		if (totalPage < lastPageNum) {
			this.lastPageNum = totalPage;
		}
	}
}
