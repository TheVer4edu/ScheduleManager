package ru.thever4.iit.shedulemanager.data;

import ru.thever4.iit.shedulemanager.util.Pair;

import javax.swing.*;
import java.nio.file.NoSuchFileException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private String path;
    private Connection connection;

    public Database(String path) throws NoSuchFileException {
        this.path = path;
        this.connection = establishConnection();
        if(this.connection == null)
            throw new NoSuchFileException(this.path);
        /*ResultSet set = makeSqlAppealWithResult("SELECT * from halls");
        while (true) {
            try {
                if (!set.next()) break;
                JOptionPane.showMessageDialog(null, set.getString(2));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }*/
    }

    public ResultSet select(String table, List<String> keysList, String sqlCondition) {
        String keysSeparated = String.join(", ", keysList);
        String query = "SELECT " + keysSeparated + " FROM " + table;
        if(sqlCondition != null)
            query.concat(" WHERE " + sqlCondition);
        return this.makeSqlAppealWithResult(query);
    }

    public ResultSet select(String table, List<String> keys) {
        return this.select(table, keys, null);
    }

    public void insert(String table, List<Pair<String, Object>> keyValuePairs) {
        List<String> keysList = new ArrayList<>(),
                valuesList = new ArrayList<>();
        for (Pair<String, Object> keyValuePair : keyValuePairs) {
            keysList.add(keyValuePair.x);
            valuesList.add(keyValuePair.y instanceof String ? "\'" + keyValuePair.y + "\'" : keyValuePair.y.toString());
        }
        String keysSeparated = String.join(", ", keysList),
                valuesSeparated = String.join(", ", valuesList);
        String query = "INSERT INTO " + table + " (" + keysSeparated + ") VALUES (" + valuesSeparated + ")";
        this.makeSqlAppeal(query);
    }

    public void update(String table, List<Pair<String, String>> keyValuePairs, String sqlCondition) {
        List<String> equalitiesList = new ArrayList<>();
        for (Pair<String, String> keyValuePair: keyValuePairs)
            equalitiesList.add(keyValuePair.x + " = " + keyValuePair.y);
        String equalitiesSeparated = String.join(", ", equalitiesList);
        String query = "UPDATE " + table + " SET " + equalitiesSeparated;
        if(sqlCondition != null)
            query.concat(" WHERE " + sqlCondition);
        this.makeSqlAppeal(query);
    }

    public void update(String table, List<Pair<String, String>> keyValuePairs) {
        this.update(table, keyValuePairs, null);
    }

    public void delete(String table, String sqlCondition) {
        String query = "DELETE FROM " + table;
        if(sqlCondition != null)
            query.concat(" WHERE " + sqlCondition);
        this.makeSqlAppeal(query);
    }

    public void delete(String table) {
        this.delete(table, null);
    }

    private Connection establishConnection() {
        Connection connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + path);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
    
    private ResultSet makeSqlAppealWithResult(String query) {
        ResultSet result = null;
        try {
            result = this.connection.prepareStatement(query).executeQuery();
        } catch (SQLException throwables) {
            JOptionPane.showMessageDialog(null, throwables.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return result;
    }

    private void makeSqlAppeal(String query) {
        Statement statement = null;
        try {
            statement = this.connection.createStatement();
            statement.execute(query);
            statement.close();
        } catch (SQLException throwables) {
            JOptionPane.showMessageDialog(null, throwables.getMessage() + "\n" + query, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}