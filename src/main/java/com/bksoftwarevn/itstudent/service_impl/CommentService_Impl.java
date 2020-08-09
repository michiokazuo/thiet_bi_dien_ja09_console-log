package com.bksoftwarevn.itstudent.service_impl;

import com.bksoftwarevn.itstudent.dao.CommentDao;
import com.bksoftwarevn.itstudent.dao_impl.CommentDaoImpl;
import com.bksoftwarevn.itstudent.model.Comment;
import com.bksoftwarevn.itstudent.service.CommentService;

import java.sql.SQLException;
import java.util.List;

public class CommentService_Impl implements CommentService {
    private CommentDao commentDao = new CommentDaoImpl();

    @Override
    public List<Comment> sortByCreateTime() throws SQLException {
        return commentDao.sortByCreateTime();
    }

    @Override
    public List<Comment> findByCategory(int idProduct) throws SQLException {
        return idProduct > 0 ? commentDao.findByProduct(idProduct) : null;
    }

    @Override
    public List<Comment> findAll() throws SQLException {
        return commentDao.findAll();
    }

    @Override
    public Comment findById(int id) throws SQLException {
        return id > 0 ? commentDao.findById(id) : null;
    }

    @Override
    public Comment insert(Comment comment) throws SQLException {
        comment.setDeleted(false);
        return commentDao.insert(comment);
    }

    @Override
    public boolean update(Comment comment) throws SQLException {
        return comment.getId() > 0 ? commentDao.update(comment) : false;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        return id > 0 ? commentDao.delete(id) : false;
    }
}
