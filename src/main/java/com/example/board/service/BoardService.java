package com.example.board.service;

import com.example.board.dto.BoardDTO;
import com.example.board.dto.PageDTO;
import com.example.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    //保存
    public void save(BoardDTO boardDTO) {
        boardRepository.save(boardDTO);
    }
    //照会
    public List<BoardDTO> findAll() {
        return boardRepository.findAll();
    }
    //ビュー
    public void updateHits(Long id) {
        boardRepository.updateHits(id);
    }
    //掲示板照会
    public BoardDTO findById(Long id) {
        return boardRepository.findById(id);
    }
    //修整
    public void update(BoardDTO boardDTO) {
        boardRepository.update(boardDTO);
    }
    //削除
    public void delete(Long id) {
        boardRepository.delete(id);
    }

    //ページ目録数
    int pageLimit = 10;
    //ページング数
    int blockLimit = 5;

    //ページ
    public List<BoardDTO> pagingList(int page) {
        //ページ数
        int pagingStart = (page - 1) * pageLimit;
        Map<String, Integer> pagingParams = new HashMap<>();
        pagingParams.put("start", pagingStart);
        pagingParams.put("limit", pageLimit);
        List<BoardDTO> pagingList = boardRepository.pagingList(pagingParams);
        return pagingList;
    }

    //ページング
    public PageDTO pagingParam(int page) {
        int boardCount = boardRepository.boardCount();
        int maxPage = (int) (Math.ceil((double) boardCount / pageLimit));
        int startPage = (((int)(Math.ceil((double) page / blockLimit))) - 1) * blockLimit + 1;
        int endPage = startPage + blockLimit - 1;
        if (endPage > maxPage) {
            endPage = maxPage;
        }
        PageDTO pageDTO = new PageDTO();
        pageDTO.setPage(page);
        pageDTO.setMaxPage(maxPage);
        pageDTO.setStartPage(startPage);
        pageDTO.setEndPage(endPage);
        return pageDTO;
    }
}
