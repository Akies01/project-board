package com.example.board.controller;

import com.example.board.dto.BoardDTO;
import com.example.board.dto.CommentDTO;
import com.example.board.dto.PageDTO;
import com.example.board.dto.UserDTO;
import com.example.board.service.BoardService;
import com.example.board.service.CommentService;
import jakarta.servlet.http.HttpSession;
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
    private final HttpSession httpSession; // HttpSession 주입

    // セーブ
    @GetMapping("/save")
    public String save() {
        return "save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute BoardDTO boardDTO) {
        boardService.save(boardDTO);
        return "redirect:/paging";
    }

    // リスト
    @GetMapping("/list")
    public String findAll(Model model) {
        List<BoardDTO> boardDTOList = boardService.findAll();
        model.addAttribute("boardList", boardDTOList);
        return "list";
    }

    // 内容
    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {

        boardService.updateHits(id);

        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board", boardDTO);


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
        return "redirect:/paging";
    }

    // 削除
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, Model model) {
        // 現在ログインしているユーザー情報を取得
        UserDTO loggedInUser = (UserDTO) httpSession.getAttribute("loggedInUser");

        // 投稿情報を取得
        BoardDTO boardDTO = boardService.findById(id);

        // ログインしているユーザーが投稿者と一致しているか確認
        if (loggedInUser != null && boardDTO.getBoardWriter().equals(loggedInUser.getUsername())) {
            // 投稿者と一致すれば削除を実行
            boardService.delete(id);
            return "redirect:/paging";
        } else {
            // 投稿者と一致しない場合、権限なし処理
            model.addAttribute("errorMessage", "削除権限がありません。");
            return "error/unauthorized";
        }
    }

    // ページング
    @GetMapping("/paging")
    public String pagingList(Model model, @RequestParam(value = "page", required = false, defaultValue = "1") int page) {
        List<BoardDTO> pagingList = boardService.pagingList(page);
        PageDTO PageDTO = boardService.pagingParam(page);
        model.addAttribute("boardList", pagingList);
        model.addAttribute("paging", PageDTO);
        return "paging";
    }
}