package com.bksoftwarevn.itstudent.service;

import com.bksoftwarevn.itstudent.model.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductService {
    List<Product> sortBy(String field, boolean isASC) throws SQLException;

    List<Product> sortByCreateDate() throws SQLException;

    List<Product> findByCategory(int idCategory) throws SQLException;

    List<Product> search(String name, String startDate, String endDate, Boolean soldOut, int guarantee, int category
            , int bought, int promotion) throws SQLException;

    List<Product> findAll() throws SQLException;

    Product findById(int id) throws SQLException;

    Product insert(Product product) throws SQLException;

    boolean update(Product product) throws SQLException;

    boolean delete(int id) throws SQLException;
}
