package com.example.board.repository;

import com.example.board.dto.UserDTO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {

    private final SqlSessionTemplate sql;

    public UserRepository(SqlSessionTemplate sql) {
        this.sql = sql;
    }

    public void save(UserDTO userDTO) {
        sql.insert("User.save", userDTO);
    }

    public UserDTO findByUsername(String username) {
        return (UserDTO) sql.selectOne("User.findByUsername", username);
    }

    public List<UserDTO> findAll() {
        return sql.selectList("User.findAll");
    }
}
