package com.bksoftwarevn.itstudent.dao_impl;

import com.bksoftwarevn.itstudent.dao.BillHasProductDao;
import com.bksoftwarevn.itstudent.model.BillHasProduct;
import com.bksoftwarevn.itstudent.model.MyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BillHasProductDaoImpl implements BillHasProductDao {
    public static final String NAME_BILL_HAS_PRODUCT = "bill_has_product";
    private MyConnection connection = new MyConnection();

    @Override
    public BillHasProduct getObject(ResultSet resultSet) throws SQLException {
        BillHasProduct billHasProduct = null;

        billHasProduct = new BillHasProduct(resultSet.getInt("product_id"), resultSet.getInt("bill_id")
                , resultSet.getInt("quantity"), resultSet.getDouble("product_price"));

        return billHasProduct;
    }

    @Override
    public List<BillHasProduct> getList(ResultSet resultSet) throws SQLException {
        List<BillHasProduct> billHasProducts = new ArrayList<>();

        if (resultSet.first()) {
            do {
                BillHasProduct billHasProduct = getObject(resultSet);
                if (billHasProduct != null) {
                    billHasProducts.add(billHasProduct);
                }
            } while (resultSet.next());
        }

        return billHasProducts;
    }

    @Override
    public List<BillHasProduct> findAll() throws SQLException {
        String sql = "SELECT * FROM " + NAME_BILL_HAS_PRODUCT;

        PreparedStatement preparedStatement = connection.prepare(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        return getList(resultSet);
    }

    @Override
    public BillHasProduct findById(int id) throws SQLException {
        return null;
    }

    @Override
    public BillHasProduct insert(BillHasProduct billHasProduct) throws SQLException {
        String sql = "INSERT INTO " + NAME_BILL_HAS_PRODUCT + " VALUES(?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareUpdate(sql);
        preparedStatement.setInt(1, billHasProduct.getBillId());
        preparedStatement.setInt(2, billHasProduct.getProductId());
        preparedStatement.setInt(3, billHasProduct.getQuantity());
        preparedStatement.setDouble(4, billHasProduct.getProducPrice());

        int rs = preparedStatement.executeUpdate();


        return null;
    }

    @Override
    public boolean update(BillHasProduct billHasProduct) throws SQLException {
        boolean rs = false;
        String sql = "UPDATE " + NAME_BILL_HAS_PRODUCT + " SET quantity = ?, product_price = ? WHERE bill_id = ? " +
                "AND product_id = ?";

        PreparedStatement preparedStatement = connection.prepareUpdate(sql);
        preparedStatement.setInt(3, billHasProduct.getBillId());
        preparedStatement.setInt(4, billHasProduct.getProductId());
        preparedStatement.setInt(1, billHasProduct.getQuantity());
        preparedStatement.setDouble(2, billHasProduct.getProducPrice());

        int update = preparedStatement.executeUpdate();
        if (update > 0){
            rs = true;
        }

        return rs;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        return false;
    }
}
