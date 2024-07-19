package com.example.board.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class BoardDTO {
    private Long id;// id
    private String boardWriter; // 作成者
    private String boardPass; // 掲示文パスワード
    private String boardTitle; // 題目
    private String boardContents; // 内容
    private int boardHits; // ビュー
    private String createdAt; // 作成時間

}
