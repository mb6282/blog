package site.metacoding.red.domain.boards;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import site.metacoding.red.domain.boards.mapper.MainView;
import site.metacoding.red.domain.boards.mapper.PagingView;

public interface BoardsDao {
	public PagingView paging(@Param("page") Integer page, @Param("keyword") String keyword);
	public void insert(Boards boards);
	public Boards findById(Integer id);
	public List<MainView> findAll(@Param("startNum") Integer startNum, @Param("keyword") String keyword);
	public List<MainView> findSearch(@Param("startNum")Integer startNum, @Param("keyword") String keyword);
	public void update(Boards boards);
	public void delete(Integer id);
	public void updateByUsersId(Integer id);
}
