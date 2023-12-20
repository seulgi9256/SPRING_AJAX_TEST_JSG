package com.joeun.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joeun.board.dto.Board;
import com.joeun.board.dto.Option;
import com.joeun.board.dto.Page;
import com.joeun.board.mapper.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    private BoardMapper boardMapper;

    @Override
    public List<Board> list() throws Exception {
        List<Board> boardList = boardMapper.list();
        return boardList;
    }

    @Override
    public Board select(int boardNo) throws Exception {
        Board board = boardMapper.select(boardNo);
        return board;
    }

    @Override
    public int insert(Board board) throws Exception {
        int result = boardMapper.insert(board);
        return result;
    }

    @Override
    public int update(Board board) throws Exception {
        int result = boardMapper.update(board);
        return result;
    }

    @Override
    public int delete(int boardNo) throws Exception {
        int result = boardMapper.delete(boardNo);
        return result;
    }

    @Override
    public int searchViews(int boardNo) throws Exception {
        int result = boardMapper.searchViews(boardNo);
        return result;
    }

    @Override
    public int updateViews(int count, int boardNo) throws Exception {
        int result = boardMapper.updateViews(count, boardNo);
        return result;
    }

    @Override
    public int updateLikes(int count, int boardNo) throws Exception {
        int result = boardMapper.updateLikes(count, boardNo);
        return result;
    }

    @Override
	public List<Board> list(String keyword) throws Exception {
		
		// 검색어가 없을 때
		if( keyword == null ) keyword = "";
		
		List<Board> boardList = boardMapper.search(keyword);
		return boardList;
	}

	@Override
	public List<Board> list(Page page) throws Exception {

		// 전체 게시글 수
		int totalCount = boardMapper.count();
		
		// 페이징 처리
		page.setTotalCount(totalCount);
		page.calc(page);
		
		List<Board> boardList = boardMapper.page(page);
		
		return boardList;
	}

	@Override
	public int count() throws Exception {
		int count = boardMapper.count();
		return count;
	}

	@Override
	public List<Board> list(Page page, String keyword) throws Exception {
		
		// 검색어가 없을 때
		// if( keyword == null ) keyword = "";
		
		// 검색어 포함 게시글 수
		// list(page, option) 으로 전환함
		// int totalCount = boardMapper.countWithKeyword(keyword);
		// log.info("totalCount : " + totalCount);
		
		// 페이징 처리
		// page.setTotalCount(totalCount);
		// page.calc(page);
		
		// list(page, option) 으로 전환함
		// List<Board> boardList = boardMapper.boardList(page, keyword);
		
		// return boardList;
		return null;
	}

	@Override
	public List<Board> list(Page page, Option option) throws Exception {
		
		// 검색어 포함 게시글 수
		int totalCount = boardMapper.countWithKeyword(option);
		
		// 페이징 처리
		page.setTotalCount(totalCount);
		page.calc(page);
		
		List<Board> boardList = boardMapper.boardList(page, option);
		
		return boardList;
	}
}
