package com.joeun.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.joeun.board.dto.Comment;
import com.joeun.board.service.CommentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 댓글 목록
     * [GET]
     * /board/list
     * model : commentList
     * @return
     * @throws Exception
     */
    @GetMapping(value="/list")
    public String list(Model model) throws Exception {
        log.info("[GET] - /comment/list");

        // 데이터 요청
        List<Comment> commentList= commentService.list();
        // 모델 등록
        model.addAttribute("commentList", commentList);
        // 뷰 페이지 지정
        return "comment/list";
    }

    /**
     * 댓글 조회
     * [GET] 
     * /comment/read
     * - model : 
     * @param model
     * @param commentNo
     * @return
     * @throws Exception
     */
    @GetMapping(value="/read")
    public String read(Model model, int commentNo) throws Exception {
        log.info("[GET] - /comment/read");

        // 데이터 요청
        Comment comment = commentService.select(commentNo);     // 댓글 정보

        // 모델 등록
        model.addAttribute("comment", comment);

        // 뷰 페이지 지정
        return "comment/read";
    }


    /**
     * 댓글 쓰기
     * [GET]
     * /comment/insert
     * model : ❌ 
     * @return
     */
    @GetMapping(value="/insert")
    public String insert(@ModelAttribute Comment comment) {
        return "comment/insert";
    }
    
    /**
     * 댓글 쓰기 처리
     * [POST]
     * /comment/insert
     * model : ❌
     * @param comment
     * @return
     * @throws Exception
     */
    @PostMapping(value="/insert")
    public String insertPro(@ModelAttribute Comment comment, int boardNo) throws Exception {
        log.info(("[POST] - comment/insert"));
        int parentNo = boardNo;
        comment.setParentNo(parentNo);
        
        int result = commentService.insert(comment);
        if( result == 0 ) return "comment/insert";
        return "redirect:/comment/list";
    }
    
    /**
     * 댓글 수정
     * [GET]
     * /comment/update
     * model : comment
     * @param model
     * @param commentNo
     * @return
     * @throws Exception
     */
    @GetMapping(value="/update")
    public String update(Model model, int commentNo) throws Exception {
        // 데이터 요청
        Comment comment = commentService.select(commentNo);
        // 모델 등록
        model.addAttribute("comment", comment);
        // 뷰 페이지 지정
        return "comment/update";
    }

    /**
     * 댓글 수정 처리
     * [POST]
     * /comment/update
     * model : comment
     * @param comment
     * @return
     * @throws Exception
     */
    @PostMapping(value="/update")
    public String updatePro(Comment comment) throws Exception {
        // 데이터 처리
        int result = commentService.update(comment);
        int commentNo = comment.getCommentNo();

        // 댓글 수정 실패 ➡ 댓글 수정 화면
        if( result == 0 ) return "redirect:/comment/update?commentNo=" + commentNo;
        
        // 뷰 페이지 지정
        return "redirect:/comment/list";
    }

    /**
     * 댓글 삭제 처리
     * [POST]
     * /comment/delete
     * model : ❌
     * @param commentNo
     * @return
     * @throws Exception
     */
    @PostMapping(value="/delete")
    public String deletePro(int commentNo) throws Exception {
        // 데이터 처리
        int result = commentService.delete(commentNo);
        
        // 댓글 삭제 실패 ➡ 댓글 수정 화면
        if( result == 0 ) return "redirect:/comment/update?commentNo=" + commentNo;

        // 뷰 페이지 지정
        return "redirect:/comment/list";
    }
    
    
}