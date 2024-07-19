package com.example.board.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class UserDTO {
    private Long id; // userID
    private String username; // userName
    private String password; // userPassword
}
