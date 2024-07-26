package com.example.board.controller;

import com.example.board.dto.BoardDTO;
import com.example.board.dto.CommentDTO;
import com.example.board.dto.PageDTO;
import com.example.board.service.BoardService;
import com.example.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor

public class BoardController {
    private final BoardService boardService;
    private final CommentService commentService;

    // セーブ
    @GetMapping("/save")
    public String save() {
        return "save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute BoardDTO boardDTO) {
        // board test
        // System.out.println("boardDTO = " + boardDTO);
        boardService.save(boardDTO);
        return "redirect:/list";
    }

    // リスト
    @GetMapping("/list")
    public String findAll(Model model) {
        List<BoardDTO> boardDTOList = boardService.findAll();
        model.addAttribute("boardList", boardDTOList);
        // list test
        // System.out.println("boardDTOList = " + boardDTOList);
        return "list";
    }

    // 内容
    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        // 照会数処理
        boardService.updateHits(id);
        // 詳細内容
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board", boardDTO);
        // detail test
        // System.out.println("boardDTO = " + boardDTO);
        List<CommentDTO> commentDTOList = commentService.findAll(id);
        model.addAttribute("commentList", commentDTOList);
        return "detail";
    }

    // 修整
    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, Model model) {
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board", boardDTO);
        return "update";
    }

    @PostMapping("/update/{id}")
    public String update(BoardDTO boardDTO, @PathVariable("id") Long id, Model model) {
        boardService.update(boardDTO);
        BoardDTO dto = boardService.findById(boardDTO.getId());
        model.addAttribute("board", dto);
        return "redirect:/list";
    }

    // 削除
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        boardService.delete(id);
        return "redirect:/list";
    }

    //ページング
    @GetMapping("/paging")
    public String pagingList(Model model, @RequestParam(value = "page", required = false, defaultValue = "1")int page) {
        // System.out.println("page = " + page);
        List<BoardDTO> pagingList = boardService.pagingList(page);
        // System.out.println("pagingList = " + pagingList);
        PageDTO PageDTO = boardService.pagingParam(page);
        model.addAttribute("boardList", pagingList);
        model.addAttribute("paging", PageDTO);
        return "paging";
    }
}
