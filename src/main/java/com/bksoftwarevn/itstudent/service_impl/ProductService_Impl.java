package com.bksoftwarevn.itstudent.service_impl;

import com.bksoftwarevn.itstudent.dao.CategoryDao;
import com.bksoftwarevn.itstudent.dao.ProductDao;
import com.bksoftwarevn.itstudent.dao_impl.CategoryDaoImpl;
import com.bksoftwarevn.itstudent.dao_impl.ProductDaoImpl;
import com.bksoftwarevn.itstudent.model.Product;
import com.bksoftwarevn.itstudent.service.ProductService;

import java.sql.SQLException;
import java.util.List;

public class ProductService_Impl implements ProductService {
    private ProductDao productDao = new ProductDaoImpl();
    private CategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    public List<Product> sortBy(String field, boolean isASC) throws SQLException {
        return field == null ? null : productDao.sortBy(field, isASC);
    }

    @Override
    public List<Product> sortByCreateDate() throws SQLException {
        return productDao.sortByCreateDate();
    }

    @Override
    public List<Product> findByCategory(int idCategory) throws SQLException {
        return idCategory > 0 ? productDao.findByCategory(idCategory) : null;
    }

    @Override
    public List<Product> search(String name, String startDate, String endDate, Boolean soldOut, int guarantee
            , int category, int bought, int promotion) throws SQLException {
        return productDao.search(name, startDate, endDate, soldOut, guarantee, category, bought, promotion);
    }

    @Override
    public List<Product> findAll() throws SQLException {
        return productDao.findAll();
    }

    @Override
    public Product findById(int id) throws SQLException {
        return id > 0 ? productDao.findById(id) : null;
    }

    @Override
    public Product insert(Product product) throws SQLException {
        product.setDeleted(false);
        return categoryDao.findById(product.getCategoryId()) == null ? null : productDao.insert(product);
    }

    @Override
    public boolean update(Product product) throws SQLException {
        return product.getId() > 0 ? (categoryDao.findById(product.getCategoryId()) == null ? false : productDao.update(product)) : false;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        return id > 0 ? productDao.delete(id) : false;
    }
}
