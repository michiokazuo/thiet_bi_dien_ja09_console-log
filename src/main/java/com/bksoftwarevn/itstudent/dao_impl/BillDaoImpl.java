package com.bksoftwarevn.itstudent.dao_impl;

import com.bksoftwarevn.itstudent.dao.BillDao;
import com.bksoftwarevn.itstudent.model.Bill;
import com.bksoftwarevn.itstudent.model.MyConnection;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BillDaoImpl implements BillDao {
    public static final String NAME_BILL = "bill";
    private MyConnection connection = new MyConnection();

    @Override
    public Bill getObject(ResultSet resultSet) throws SQLException {
        Bill bill = null;

        bill = new Bill(resultSet.getInt("id"), resultSet.getString("customer")
                , resultSet.getString("phone"), resultSet.getString("email")
                , resultSet.getString("address"), resultSet.getDate("create_time").toLocalDate()
                , resultSet.getInt("status"), resultSet.getBoolean("deleted"));

        return bill;
    }

    @Override
    public List<Bill> getList(ResultSet resultSet) throws SQLException {
        List<Bill> billList = new ArrayList<>();

        if (resultSet.first()) {
            do {
                Bill bill = getObject(resultSet);
                if (bill != null) {
                    billList.add(bill);
                }
            } while (resultSet.next());
        }

        return billList;
    }

    @Override
    public List<Bill> findAll() throws SQLException {
        String sql = "SELECT * FROM " + NAME_BILL + " WHERE deleted = false";

        PreparedStatement preparedStatement = connection.prepare(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        return getList(resultSet);
    }

    @Override
    public Bill findById(int id) throws SQLException {
        Bill bill = null;
        String sql = "SELECT * FROM " + NAME_BILL + " WHERE deleted = false AND id = ?";

        PreparedStatement preparedStatement = connection.prepare(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.first()) {
            bill = getObject(resultSet);
        }

        return bill;
    }

    @Override
    public Bill insert(Bill bill) throws SQLException {
        Bill new_bill = null;
        String sql = "INSERT INTO " + NAME_BILL + " (customer, phone, email, address, create_time, status, deleted) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareUpdate(sql);
        preparedStatement.setString(1, bill.getCustomerName());
        preparedStatement.setString(2, bill.getPhoneNumber());
        preparedStatement.setString(3, bill.getEmail());
        preparedStatement.setString(4, bill.getAddress());
        preparedStatement.setDate(5, Date.valueOf(bill.getCreateTime()));
        preparedStatement.setInt(6, bill.getStatus());
        preparedStatement.setBoolean(7, bill.isDeleted());

        int rs = preparedStatement.executeUpdate();
        if (rs > 0) {
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.first()) {
                new_bill = findById((int) resultSet.getLong(1));
            }
        }
        return new_bill;
    }

    @Override
    public boolean update(Bill bill) throws SQLException {
        Boolean rs = false;
        String sql = "UPDATE " + NAME_BILL + " SET customer = ?, phone = ?, email = ?, address = ?, create_time = ?" +
                ", status = ?, deleted = ? WHERE id = ?";

        PreparedStatement preparedStatement = connection.prepareUpdate(sql);
        preparedStatement.setString(1, bill.getCustomerName());
        preparedStatement.setString(2, bill.getPhoneNumber());
        preparedStatement.setString(3, bill.getEmail());
        preparedStatement.setString(4, bill.getAddress());
        preparedStatement.setDate(5, Date.valueOf(bill.getCreateTime()));
        preparedStatement.setInt(6, bill.getStatus());
        preparedStatement.setBoolean(7, bill.isDeleted());
        preparedStatement.setInt(8, bill.getId());

        int update = preparedStatement.executeUpdate();
        if (update > 0) {
            rs = true;
        }
        return rs;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        Boolean rs = false;
        String sql = "UPDATE " + NAME_BILL + " SET deleted = false WHERE id = ?";

        PreparedStatement preparedStatement = connection.prepareUpdate(sql);
        preparedStatement.setInt(1, id);

        int delete = preparedStatement.executeUpdate();
        if (delete > 0) {
            rs = true;
        }

        return rs;
    }
}
