package com.bksoftwarevn.itstudent.dao;

import com.bksoftwarevn.itstudent.model.Comment;

import java.sql.SQLException;
import java.util.List;

public interface CommentDao extends BaseDao<Comment> {
    List<Comment> sortByCreateTime() throws SQLException;

    List<Comment> findByProduct(int idProduct) throws SQLException;
}
