package com.example.board.service;

import com.example.board.dto.BoardDTO;
import com.example.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
