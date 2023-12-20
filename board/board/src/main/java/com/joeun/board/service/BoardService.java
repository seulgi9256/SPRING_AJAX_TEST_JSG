package com.joeun.board.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.joeun.board.dto.Board;

public interface BoardService {

    // 게시글 목록
    public List<Board> list() throws Exception;

    // 게시글 조회
    public Board select(int boardNo) throws Exception;

    // 게시글 등록
    public int insert(Board board) throws Exception;

    // 게시글 수정
    public int update(Board board) throws Exception;

    // 게시글 삭제
    public int delete(@Param("boardNo") int boardNo) throws Exception;

    // 조회수 조회
    public int searchViews(@Param("boardNo") int boardNo) throws Exception;

    // 조회수 업데이트
    public int updateViews(int count, int boardNo) throws Exception;

    // 조회수 업데이트
    public int updateLikes(int count, int boardNo) throws Exception;

}
