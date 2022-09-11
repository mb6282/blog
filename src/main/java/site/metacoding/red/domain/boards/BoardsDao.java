package site.metacoding.red.domain.boards;

import java.util.List;

import site.metacoding.red.domain.boards.mapper.MainView;
import site.metacoding.red.domain.boards.mapper.PagingView;

public interface BoardsDao {
	public PagingView paging(Integer page);
	public void insert(Boards boards);
	public Boards findById(Integer id);
	public List<MainView> findAll(Integer startNum);
	public void update(Boards boards);
	public void delete(Integer id);
}
