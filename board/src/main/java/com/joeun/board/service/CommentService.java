package com.joeun.board.service;

import java.util.List;

import com.joeun.board.dto.Comment;

public interface CommentService {

    // 댓글 목록
    public List<Comment> list() throws Exception;

    // 댓글 조회
    public Comment select(int commentNo) throws Exception;

    // 댓글 등록
    public int insert(Comment comment) throws Exception;

    // 댓글 수정
    public int update(Comment comment) throws Exception;

    // 댓글 삭제
    public int delete(int commentNo) throws Exception;

    // 보드번호로 댓글 조회
    public List<Comment> listByBoard(int boardNo) throws Exception;

}
