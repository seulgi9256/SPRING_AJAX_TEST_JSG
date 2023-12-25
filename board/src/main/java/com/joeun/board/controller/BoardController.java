package com.joeun.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.joeun.board.dto.Board;
import com.joeun.board.dto.Option;
import com.joeun.board.dto.Page;
import com.joeun.board.service.BoardService;

import lombok.extern.slf4j.Slf4j;



@Slf4j
@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardService boardService;

    /**
     * 게시글 목록
     * [GET]
     * /board/list
     * model : boardList
     * @return
     * @throws Exception
     */
    // @GetMapping(value="/list")
    // public String list(Model model) throws Exception {
    //     log.info("[GET] - /board/list");

    //     // 데이터 요청
    //     List<Board> boardList= boardService.list();
    //     // 모델 등록
    //     model.addAttribute("boardList", boardList);
    //     // 뷰 페이지 지정
    //     return "board/list";
    // }

    // 게시글 목록 with 페이징 처리
	@GetMapping("/list")
	public String list(Model model, Option option, Page page) throws Exception {
		
		log.info("##### 페이징 처리 전 - page #####");
		log.info(page.toString());
		log.info("keyword : " + option.getKeyword());
		
		// 게시글 목록 요청
		List<Board> boardList = boardService.list(page, option);
		
		log.info("##### 페이징 처리 후 - page #####");
		log.info(page.toString());
		
		// 게시글 목록 모델에 등록
		model.addAttribute("boardList", boardList);
		model.addAttribute("page", page);
		
		return "/board/list";
	}

    /**
     * 게시글 조회
     * [GET] 
     * /board/read
     * - model : board, fileList
     * @param model
     * @param boardNo
     * @return
     * @throws Exception
     */
    @GetMapping(value="/read")
    public String read(Model model, int boardNo) throws Exception {
        log.info("[GET] - /board/read####");

        // 데이터 요청
        Board board = boardService.select(boardNo);     // 게시글 정보

        // 게시글 선택 -> 조회 수 증가
        int count = 1;
        boardService.updateViews(count, boardNo);
        int views = boardService.searchViews(boardNo);
        board.setViews(views);

        // 모델 등록
        model.addAttribute("board", board);

        // 뷰 페이지 지정
        return "board/read";
    }

    /**
     * 게시글 쓰기
     * [GET]
     * /board/insert
     * model : ❌ 
     * @return
     */
    @GetMapping(value="/insert")
    public String insert(@ModelAttribute Board board) {
        return "board/insert";
    }
    
    /**
     * 게시글 쓰기 처리
     * [POST]
     * /board/insert
     * model : ❌
     * @param board
     * @return
     * @throws Exception
     */
    @PostMapping(value="/insert")
    public String insertPro(@ModelAttribute Board board) throws Exception {
        log.info("[GET] - /board/insert");
        // 데이터 처리
        int result = boardService.insert(board);

        // 게시글 쓰기 실패 ➡ 게시글 쓰기 화면
        if( result == 0 ) return "board/insert";

        // 뷰 페이지 지정
        return "redirect:/board/list";
    }
    
    /**
     * 게시글 수정
     * [GET]
     * /board/update
     * model : board
     * @param model
     * @param boardNo
     * @return
     * @throws Exception
     */
    @GetMapping(value="/update")
    public String update(Model model, int boardNo) throws Exception {
        log.info("[GET] - /board/update");
        // 데이터 요청
        Board board = boardService.select(boardNo);
        // 모델 등록
        model.addAttribute("board", board);
        // 뷰 페이지 지정
        return "board/update";
    }

    /**
     * 게시글 수정 처리
     * [POST]
     * /board/update
     * model : board
     * @param board
     * @return
     * @throws Exception
     */
    @PostMapping(value="/update")
    public String updatePro(Board board) throws Exception {
        log.info("[POST] - /board/update");
        // 데이터 처리
        int result = boardService.update(board);
        int boardNo = board.getBoardNo();

        // 게시글 수정 실패 ➡ 게시글 수정 화면
        if( result == 0 ) return "redirect:/board/update?boardNo=" + boardNo;
        
        // 뷰 페이지 지정
        return "redirect:/board/list";
    }

    /**
     * 게시글 삭제 처리
     * [POST]
     * /board/delete
     * model : ❌
     * @param boardNo
     * @return
     * @throws Exception
     */
    @PostMapping(value="/delete")
    public String deletePro(int boardNo) throws Exception {
        log.info("[POST] - /board/delete");

        int result = boardService.delete(boardNo);
        
        // 게시글 삭제 실패 ➡ 게시글 수정 화면
        if( result == 0 ) return "redirect:/board/update?boardNo=" + boardNo;
        
        // 뷰 페이지 지정
        return "redirect:/board/list";
    }    

    /**
     * 
     * [GET]
     * /board/page
     * model : ❌ 
     * @return
     */
    @ResponseBody
    @GetMapping("/page")
    public List<Board> page(int pageNum) throws Exception {
        int rows = 10;
        int start = rows * (pageNum - 1);

        Page page = new Page();
        page.setPageNum(pageNum);
        page.setStartPage(start);
        page.setRowsPerPage(rows);
        
        List<Board> boardList = boardService.list(page);

        return boardList;
    }

    @ResponseBody
    @GetMapping("/pagination") 
    public Page pagination(Page page) throws Exception {
        int result = boardService.count();
        page.setTotalCount(result);
		page.calc(page);
        return page;
    }
}