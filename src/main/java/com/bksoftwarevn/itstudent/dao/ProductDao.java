package com.bksoftwarevn.itstudent.dao;

import com.bksoftwarevn.itstudent.model.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductDao extends BaseDao<Product> {
    List<Product> sortByCreateDate() throws SQLException;

    List<Product> sortBy(String field, boolean isASC) throws SQLException;

    List<Product> findByCategory(int idCategory) throws SQLException;

    List<Product> search(String name, String startDate, String endDate, Boolean soldOut, int guarantee, int category
            , int bought, int promotion) throws SQLException;
}
