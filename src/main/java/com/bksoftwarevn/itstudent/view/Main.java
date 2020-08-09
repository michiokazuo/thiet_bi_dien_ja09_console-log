package com.bksoftwarevn.itstudent.view;

import com.bksoftwarevn.itstudent.dao.ProductDao;
import com.bksoftwarevn.itstudent.dao_impl.ProductDaoImpl;
import com.bksoftwarevn.itstudent.model.JsonResult;
import com.bksoftwarevn.itstudent.model.MyConnection;
import com.bksoftwarevn.itstudent.service.ProductService;
import com.bksoftwarevn.itstudent.service_impl.ProductService_Impl;
import com.google.gson.Gson;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        ProductService productService = new ProductService_Impl();
//        ProductDao productDao = new ProductDaoImpl();
//        MyConnection myConnection = new MyConnection();
//        try {
//            Connection connection = myConnection.connectDB();
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        try {
//            System.out.println(productDao.search("D",null, null, false, -1, -1, -1, -1));
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
        System.out.println("Hải Dương");
        try{
            int i = 0;
            if (i==0) throw new NullPointerException("No");
            System.out.println(111);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
