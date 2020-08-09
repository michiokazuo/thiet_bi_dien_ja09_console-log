package com.bksoftwarevn.itstudent.dao_impl;

import com.bksoftwarevn.itstudent.dao.CategoryDao;
import com.bksoftwarevn.itstudent.model.Category;
import com.bksoftwarevn.itstudent.model.MyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDaoImpl implements CategoryDao {
    public static final String NAME_CATEGORY = "category";
    private MyConnection connection = new MyConnection();

    @Override
    public Category getObject(ResultSet resultSet) throws SQLException {
        Category category = null;

        category = new Category(resultSet.getInt("id"), resultSet.getString("name")
                , resultSet.getBoolean("deleted"));

        return category;
    }

    @Override
    public List<Category> getList(ResultSet resultSet) throws SQLException {
        List<Category> categoryList = new ArrayList<>();

        if (resultSet.first()) { // tro den ban ghi dau tien -- neu co tra ve true -- khong tra ve false
            do {
                Category category = getObject(resultSet);
                if (category != null) {
                    categoryList.add(category);
                }
            } while (resultSet.next());
        }

        return categoryList;
    }

    @Override
    public List<Category> findAll() throws SQLException {
        List<Category> categoryList = new ArrayList<>();
        String sql = "SELECT * FROM " + NAME_CATEGORY + " WHERE deleted = false";

        PreparedStatement preparedStatement = connection.prepare(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        return getList(resultSet);
    }

    @Override
    public Category findById(int id) throws SQLException {
        Category category = null;
        String sql = "SELECT * FROM " + NAME_CATEGORY + " WHERE deleted = false AND id = ?";

        PreparedStatement preparedStatement = connection.prepare(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.first()) {
            category = getObject(resultSet);
        }

        return category;
    }

    @Override
    public Category insert(Category category) throws SQLException {
        Category new_category = null;
        String sql = "INSERT INTO " + NAME_CATEGORY + " (name, deleted) VALUES (?, ?)";

        PreparedStatement preparedStatement = connection.prepareUpdate(sql);
        preparedStatement.setString(1, category.getName());
        preparedStatement.setBoolean(2, category.isDeleted());

        int rs = preparedStatement.executeUpdate();
        if (rs > 0) {
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.first()) {
                new_category = findById((int) resultSet.getLong(1));
            }
        }

        return new_category;
    }

    @Override
    public boolean update(Category category) throws SQLException {
        Boolean rs = false;
        String sql = "UPDATE " + NAME_CATEGORY + " SET name = ? WHERE id = ?";

        PreparedStatement preparedStatement = connection.prepareUpdate(sql);
        preparedStatement.setString(1, category.getName());
        preparedStatement.setInt(2, category.getId());

        int update = preparedStatement.executeUpdate();
        if (update > 0) {
            rs = true;
        }

        return rs;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        Boolean rs = false;
        String sql = "UPDATE " + NAME_CATEGORY + " SET deleted = true WHERE id = ?";

        PreparedStatement preparedStatement = connection.prepareUpdate(sql);
        preparedStatement.setInt(1, id);

        int delete = preparedStatement.executeUpdate();
        if (delete > 0) {
            rs = true;
        }

        return rs;
    }
}
