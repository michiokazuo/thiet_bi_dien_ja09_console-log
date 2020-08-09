package com.bksoftwarevn.itstudent.dao_impl;

import com.bksoftwarevn.itstudent.dao.CategoryDao;
import com.bksoftwarevn.itstudent.dao.ProductDao;
import com.bksoftwarevn.itstudent.model.MyConnection;
import com.bksoftwarevn.itstudent.model.Product;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl implements ProductDao {
    public static final String NAME_PRODUCT = "product";
    private MyConnection connection = new MyConnection();
    private CategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    public List<Product> sortByCreateDate() throws SQLException {
        List<Product> productList = new ArrayList<>();
        String sql = "SELECT * FROM " + NAME_PRODUCT + " WHERE deleted = false ORDER BY create_date DESC";

        PreparedStatement preparedStatement = connection.prepare(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        productList = getList(resultSet);

        return productList;
    }

    @Override
    public List<Product> sortBy(String field, boolean isASC) throws SQLException {
        String sql = "SELECT * FROM " + NAME_PRODUCT + " WHERE deleted = false ORDER BY " + field + " " +
                (isASC ? "ASC" : "DESC");

        PreparedStatement preparedStatement = connection.prepare(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        return getList(resultSet);
    }

    @Override
    public List<Product> findByCategory(int idCategory) throws SQLException {
// c1 dung categoryDao
//        if (categoryDao.findById(idCategory) == null) {
//            return null;
//        }

        String sql = "SELECT P.* FROM " + NAME_PRODUCT + " P JOIN " + CategoryDaoImpl.NAME_CATEGORY + " C " +
                "ON P.category_id = C.id WHERE P.deleted = false AND C.deleted = false AND P.category_id = ?";

        PreparedStatement preparedStatement = connection.prepare(sql);
        preparedStatement.setInt(1, idCategory);
        ResultSet resultSet = preparedStatement.executeQuery();

        return getList(resultSet);
    }

    @Override
    public List<Product> search(String name, String startDate, String endDate, Boolean soldOut, int guarantee, int category, int bought, int promotion) throws SQLException {
        String sql = "SELECT P.* FROM " + NAME_PRODUCT + " P JOIN " + CategoryDaoImpl.NAME_CATEGORY + " C " +
                "ON P.category_id = C.id WHERE P.deleted = false AND C.deleted = false " +
                "AND P.name LIKE ? " +
                "AND (? IS NULL OR P.create_date >= ?) " +
                "AND (? IS NULL OR P.create_date <= ?) " +
                "AND (? IS NULL OR P.sold_out = ?) " +
                "AND (? = -1 OR P.guarantee = ?) " +
                "AND (? = -1 OR C.id = ?) " +
                "AND (? = -1 OR P.bought = ?) " +
                "AND (? = -1 OR P.promotion = ?)";

        PreparedStatement preparedStatement = connection.prepare(sql);
        preparedStatement.setString(1, "%" + name + "%");
        preparedStatement.setString(2, startDate);
        preparedStatement.setString(3, startDate == null ? "0000-01-01" : startDate);
        preparedStatement.setString(4, endDate);
        preparedStatement.setString(5, endDate == null ? "9999-12-31" : endDate);
        if (soldOut == null) {
            preparedStatement.setString(6, null);
            preparedStatement.setBoolean(7, true);
        } else {
            preparedStatement.setString(6, "");
            preparedStatement.setBoolean(7, soldOut);
        }
        preparedStatement.setInt(8, guarantee);
        preparedStatement.setInt(9, guarantee);
        preparedStatement.setInt(10, category);
        preparedStatement.setInt(11, category);
        preparedStatement.setInt(12, bought);
        preparedStatement.setInt(13, bought);
        preparedStatement.setInt(14, promotion);
        preparedStatement.setInt(15, promotion);

        ResultSet resultSet = preparedStatement.executeQuery();

        return getList(resultSet);
    }

    @Override
    public Product getObject(ResultSet resultSet) throws SQLException {
        Product product = null;

        product = new Product(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getDouble("price")
                , resultSet.getDate("create_Date"), resultSet.getBoolean("deleted")
                , resultSet.getString("image"), resultSet.getString("introduction")
                , resultSet.getString("specification"), resultSet.getBoolean("sold_out")
                , resultSet.getInt("guarantee"), resultSet.getInt("category_id")
                , resultSet.getInt("bought"), resultSet.getInt("promotion"));

        return product;
    }

    @Override
    public List<Product> getList(ResultSet resultSet) throws SQLException {
        List<Product> productList = new ArrayList<>();

        if (resultSet.first()) {
            do {
                Product product = getObject(resultSet);
                if (product != null) {
                    productList.add(product);
                }
            } while (resultSet.next());
        }

        return productList;
    }

    @Override
    public List<Product> findAll() throws SQLException {
        String sql = "SELECT * FROM " + NAME_PRODUCT + " P JOIN " + CategoryDaoImpl.NAME_CATEGORY + " C" +
                " ON P.category_id = C.id WHERE P.deleted = false AND C.deleted = false";

        PreparedStatement preparedStatement = connection.prepare(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        return getList(resultSet);
    }

    @Override
    public Product findById(int id) throws SQLException {
        Product product = null;
        String sql = "SELECT * FROM " + NAME_PRODUCT + " P JOIN " + CategoryDaoImpl.NAME_CATEGORY + " C" +
                " ON P.category_id = C.id WHERE P.deleted = false AND P.id = ? AND C.deleted = false";

        PreparedStatement preparedStatement = connection.prepare(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.first()) {
            product = getObject(resultSet);
        }

        return product;
    }

    @Override
    public Product insert(Product product) throws SQLException {
        Product new_product = null;
        String sql = "INSERT INTO " + NAME_PRODUCT + " (name, price, create_date, deleted, image, introduction" +
                ", specification, sold_out, guarantee, category_id, bought, promotion) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareUpdate(sql);
        preparedStatement.setString(1, product.getName());
        preparedStatement.setDouble(2, product.getPrice());
//        preparedStatement.setDate(3, convertDateUtil_DateSQL(product.getCreateDate()));
        preparedStatement.setDate(3, new Date(new java.util.Date().getTime()));
        preparedStatement.setBoolean(4, product.isDeleted());
        preparedStatement.setString(5, product.getImage());
        preparedStatement.setString(6, product.getIntroduction());
        preparedStatement.setString(7, product.getSpecification());
        preparedStatement.setBoolean(8, product.isSoldOut());
        preparedStatement.setInt(9, product.getGuarantee());
        preparedStatement.setInt(10, product.getCategoryId());
        preparedStatement.setInt(11, product.getBought());
        preparedStatement.setInt(12, product.getPromotion());

        int rs = preparedStatement.executeUpdate();
        if (rs > 0) {
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.first()) {
                new_product = findById((int) resultSet.getLong(1));
            }
        }
        return new_product;
    }

    @Override
    public boolean update(Product product) throws SQLException {
        Boolean rs = false;
        String sql = "UPDATE " + NAME_PRODUCT + " SET name = ?, price = ?, create_date = ?, deleted = ?, image = ?" +
                ", introduction = ?,specification = ?, sold_out = ?, guarantee = ?, category_id = ?, bought = ?" +
                ", promotion = ? WHERE id = ?";

        PreparedStatement preparedStatement = connection.prepareUpdate(sql);
        preparedStatement.setString(1, product.getName());
        preparedStatement.setDouble(2, product.getPrice());
        preparedStatement.setDate(3, new Date(product.getCreateDate().getTime()));
        preparedStatement.setBoolean(4, product.isDeleted());
        preparedStatement.setString(5, product.getImage());
        preparedStatement.setString(6, product.getIntroduction());
        preparedStatement.setString(7, product.getSpecification());
        preparedStatement.setBoolean(8, product.isSoldOut());
        preparedStatement.setInt(9, product.getGuarantee());
        preparedStatement.setInt(10, product.getCategoryId());
        preparedStatement.setInt(11, product.getBought());
        preparedStatement.setInt(12, product.getPromotion());
        preparedStatement.setInt(13, product.getId());

        int update = preparedStatement.executeUpdate();
        if (update > 0) {
            rs = true;
        }
        return rs;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        Boolean rs = false;
        String sql = "UPDATE " + NAME_PRODUCT + " SET deleted = false WHERE id = ?";

        PreparedStatement preparedStatement = connection.prepareUpdate(sql);
        preparedStatement.setInt(1, id);

        int delete = preparedStatement.executeUpdate();
        if (delete > 0) {
            rs = true;
        }

        return rs;
    }

    private Date convertDateUtil_DateSQL(java.util.Date utilDate) {
        Date sqlDate = null;

        if (utilDate != null) {
            sqlDate = Date.valueOf(utilDate.toInstant().atZone(ZoneId.of("Africa/Tunis")).toLocalDate());
        }

        return sqlDate;
    }
}
