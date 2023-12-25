package com.joeun.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.joeun.board.dto.Board;
import com.joeun.board.dto.Option;
import com.joeun.board.dto.Page;

@Mapper
public interface BoardMapper {
    
    // 게시글 목록
    public List<Board> list() throws Exception;

    // 게시글 조회
    public Board select(int boardNo) throws Exception;

    // 게시글 등록
    public int insert(Board board) throws Exception;

    // 게시글 수정
    public int update(Board board) throws Exception;

    // 게시글 삭제
    public int delete(int boardNo) throws Exception;

    // 게시글 번호(기본키) 최댓값
    public int maxPk() throws Exception;

    // 조회수 조회
    public int searchViews(@Param("boardNo") int boardNo) throws Exception;

    // 조회수 업데이트
    public int updateViews(@Param("count") int count, @Param("boardNo") int boardNo) throws Exception;

    // 좋아요 업데이트
    public int updateLikes(@Param("count") int count, @Param("boardNo") int boardNo) throws Exception;

    // 검색
	public List<Board> search(String keyword) throws Exception;
	
	// [페이지] 게시글 목록
	public List<Board> page(Page page) throws Exception;
	
	// 게시글 개수
	public int count() throws Exception;
	
	// [검색][페이지] 게시글 목록
	public List<Board> boardList(@Param("page") Page page, @Param("option") Option option) throws Exception;
	
	// [검색] 게시글 개수
	public int countWithKeyword(@Param("option") Option option) throws Exception;
}
