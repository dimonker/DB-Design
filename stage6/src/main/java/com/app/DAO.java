package com.app;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

public class DAO {

    public Double getIncome(LocalDate date1, LocalDate date2) throws SQLException{
        Double income = 0.0;
        try(Statement statement = SQLConnection.getConnection().createStatement()){
            String sql = String.format("select sum(price_per_piece * weight_in_grams / 1000) " +
                    "from invoice where weight_in_grams IS NOT NULL " +
                    "AND date_and_time_of_delivery BETWEEN '%s' AND '%s'",
                        date1, date2.plusDays(1));
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()){
                income += resultSet.getDouble(1);
            }

            sql = String.format("select sum(price_per_piece * number) " +
                    "from invoice " +
                    "where number IS NOT NULL " +
                    "AND date_and_time_of_delivery BETWEEN '%s' AND '%s'",
                        date1, date2.plusDays(1));
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()){
                income += resultSet.getDouble(1);
            }
        }catch (SQLException e){
            throw new SQLException(e);
        }
        return income;
    }

    public Double getExpenses(LocalDate date1, LocalDate date2) throws SQLException {
        Double expenses = 0.0;

        try(Statement statement = SQLConnection.getConnection().createStatement()){
            String sql = String.format("Select sum(order_price) " +
                            "from orders " +
                            "WHERE date_and_time_of_receipt_of_order " +
                            "BETWEEN '%s' AND '%s'",
                                date1, date2.plusDays(1));
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()){
                expenses += resultSet.getDouble(1);
            }
        }catch (SQLException e){
            throw new SQLException(e);
        }

        return expenses;
    }

    public ArrayList<Product> getProducts() throws SQLException{
        ArrayList<Product> products = new ArrayList<>();
        try(Statement statement = SQLConnection.getConnection().createStatement()){
            String sql = "SELECT * FROM product_name_and_price";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                products.add(new Product(resultSet.getString(1), resultSet.getDouble(2)));
            }
        }catch (SQLException e){
            throw new SQLException(e);
        }
        return products;
    }

    public String getIngredients(String name) throws SQLException {
        String result = "";
        try (Statement statement = SQLConnection.getConnection().createStatement()){
            String sql = String.format("SELECT * FROM ingredients_for_pizza WHERE name = '%s'",name);
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                result = resultSet.getString(2);
            }
        }catch (SQLException e){
            throw new SQLException(e);
        }
        return result;
    }
}
