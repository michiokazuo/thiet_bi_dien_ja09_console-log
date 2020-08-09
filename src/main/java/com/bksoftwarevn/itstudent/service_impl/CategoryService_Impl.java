package com.bksoftwarevn.itstudent.service_impl;

import com.bksoftwarevn.itstudent.dao.CategoryDao;
import com.bksoftwarevn.itstudent.dao_impl.CategoryDaoImpl;
import com.bksoftwarevn.itstudent.model.Category;
import com.bksoftwarevn.itstudent.service.CategoryService;

import java.sql.SQLException;
import java.util.List;

public class CategoryService_Impl implements CategoryService {
    private CategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    public List<Category> findAll() throws SQLException {
        return categoryDao.findAll();
    }

    @Override
    public Category findById(int id) throws SQLException {
        return id > 0 ? categoryDao.findById(id) : null;
    }

    @Override
    public Category insert(String name) throws SQLException {
        Category category = new Category(name, false);
        return categoryDao.insert(category);
    }

    @Override
    public boolean update(Category category) throws SQLException {
        return category.getId() > 0 ? categoryDao.update(category) : false;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        return id > 0 ? categoryDao.delete(id) : false;
    }
}
