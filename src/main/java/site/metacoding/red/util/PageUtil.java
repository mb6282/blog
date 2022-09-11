package site.metacoding.red.util;

public class PageUtil {
	public Integer getTotalPage(Integer totalCount) {
		Integer totalPage = totalCount/10;
		if(totalCount % 10 !=0) {
			totalPage++;
		}
		return totalPage;
	}
	
	public boolean getLast(Integer currentPage, Integer totalPage) {
		return currentPage == totalPage ? true : false;
	}
	
	public boolean getFirst(Integer currentPage, Integer totalPage) {
		return currentPage == 0 ? true : false;
	}
}
