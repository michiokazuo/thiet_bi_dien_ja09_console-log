package com.bksoftwarevn.itstudent.dao_impl;

import com.bksoftwarevn.itstudent.dao.CommentDao;
import com.bksoftwarevn.itstudent.model.Comment;
import com.bksoftwarevn.itstudent.model.MyConnection;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommentDaoImpl implements CommentDao {
    public static final String NAME_COMMENT = "comment";
    private MyConnection connection = new MyConnection();

    @Override
    public List<Comment> sortByCreateTime() throws SQLException {
        String sql = "SELECT * FROM " + NAME_COMMENT + " WHERE deleted = false AND accepted = 1 ORDER BY create_time DESC";

        PreparedStatement preparedStatement = connection.prepare(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        return getList(resultSet);
    }

    @Override
    public List<Comment> findByProduct(int idProduct) throws SQLException {
        String sql = "SELECT CMT.* FROM " + NAME_COMMENT + " CMT JOIN " + ProductDaoImpl.NAME_PRODUCT + " P " +
                "ON P.id = CMT.product_id WHERE P.deleted = false AND CMT.deleted = false AND CMT.accepted = 1 AND P.id = ?";

        PreparedStatement preparedStatement = connection.prepare(sql);
        preparedStatement.setInt(1, idProduct);
        ResultSet resultSet = preparedStatement.executeQuery();

        return getList(resultSet);
    }

    @Override
    public Comment getObject(ResultSet resultSet) throws SQLException {
        Comment comment = null;

        comment = new Comment(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("comment")
                , resultSet.getInt("product_id"), resultSet.getInt("accepted")
                , resultSet.getBoolean("deleted"), resultSet.getDate("create_time").toLocalDate());

        return comment;
    }

    @Override
    public List<Comment> getList(ResultSet resultSet) throws SQLException {
        List<Comment> commentList = new ArrayList<>();

        if (resultSet.first()) {
            do {
                Comment comment = getObject(resultSet);
                if (comment != null) {
                    commentList.add(comment);
                }
            } while (resultSet.next());
        }

        return commentList;
    }

    @Override
    public List<Comment> findAll() throws SQLException {
        String sql = "SELECT CMT.* FROM " + NAME_COMMENT + " CMT JOIN " + ProductDaoImpl.NAME_PRODUCT + " P " +
                "ON P.id = CMT.product_id WHERE P.deleted = false AND CMT.deleted = false AND CMT.accepted = 1";

        PreparedStatement preparedStatement = connection.prepare(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        return getList(resultSet);
    }

    @Override
    public Comment findById(int id) throws SQLException {
        Comment comment = null;
        String sql = "SELECT CMT.* FROM " + NAME_COMMENT + " CMT JOIN " + ProductDaoImpl.NAME_PRODUCT + " P " +
                "ON P.id = CMT.product_id WHERE P.deleted = false AND CMT.deleted = false AND CMT.accepted = 1 AND id = ?";

        PreparedStatement preparedStatement = connection.prepare(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.first()) {
            comment = getObject(resultSet);
        }

        return comment;
    }

    @Override
    public Comment insert(Comment comment) throws SQLException {
        Comment new_comment = null;
        String sql = "INSERT INTO " + NAME_COMMENT + " (name, comment, product_id, accepted, deleted, create_time) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareUpdate(sql);
        preparedStatement.setString(1, comment.getName());
        preparedStatement.setString(2, comment.getComment());
        preparedStatement.setInt(3, comment.getProductId());
        preparedStatement.setInt(4, comment.getAccepted());
        preparedStatement.setBoolean(5, comment.isDeleted());
        preparedStatement.setDate(6, Date.valueOf(comment.getCreateTime()));

        int rs = preparedStatement.executeUpdate();
        if (rs > 0) {
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.first()) {
                new_comment = findById((int) resultSet.getLong(1));
            }
        }
        return new_comment;
    }

    @Override
    public boolean update(Comment comment) throws SQLException {
        Boolean rs = false;
        String sql = "UPDATE " + NAME_COMMENT + " SET name = ?, comment = ?, product_id = ?, accepted = ?, deleted = ?," +
                " create_time = ? WHERE id = ?";

        PreparedStatement preparedStatement = connection.prepareUpdate(sql);
        preparedStatement.setString(1, comment.getName());
        preparedStatement.setString(2, comment.getComment());
        preparedStatement.setInt(3, comment.getProductId());
        preparedStatement.setInt(4, comment.getAccepted());
        preparedStatement.setBoolean(5, comment.isDeleted());
        preparedStatement.setDate(6, Date.valueOf(comment.getCreateTime()));
        preparedStatement.setInt(7, comment.getId());

        int update = preparedStatement.executeUpdate();
        if (update > 0) {
            rs = true;
        }
        return rs;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        Boolean rs = false;
        String sql = "UPDATE " + NAME_COMMENT + " SET deleted = false WHERE id = ?";

        PreparedStatement preparedStatement = connection.prepareUpdate(sql);
        preparedStatement.setInt(1, id);

        int delete = preparedStatement.executeUpdate();
        if (delete > 0) {
            rs = true;
        }

        return rs;
    }
}
