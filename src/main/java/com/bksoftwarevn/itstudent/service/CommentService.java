package com.bksoftwarevn.itstudent.service;

import com.bksoftwarevn.itstudent.model.Comment;

import java.sql.SQLException;
import java.util.List;

public interface CommentService {
    List<Comment> sortByCreateTime() throws SQLException;

    List<Comment> findByCategory(int idProduct) throws SQLException;

    List<Comment> findAll() throws SQLException;

    Comment findById(int id) throws SQLException;

    Comment insert(Comment comment) throws SQLException;

    boolean update(Comment comment) throws SQLException;

    boolean delete(int id) throws SQLException;
}
