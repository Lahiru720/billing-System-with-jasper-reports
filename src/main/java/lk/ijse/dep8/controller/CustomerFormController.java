package lk.ijse.dep8.controller;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.dep8.util.CustomerTM;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class CustomerFormController {
    public TableView<CustomerTM> tblCustomers;
    private Connection connection;
    public void initialize() throws ClassNotFoundException {
        Properties prop = new Properties();

        try {
            prop.load(this.getClass().getResourceAsStream("/application.properties"));

        } catch (IOException e) {
            e.printStackTrace();
            Platform.runLater(()->{
                new Alert(Alert.AlertType.ERROR,"Failed to load configurations ").show();
            });
            return;
        }
        tblCustomers.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblCustomers.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblCustomers.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));

        Class.forName("com.mysql.cj.jdbc.");
        try {
            String url = String.format("jdbc:mysql://%s:%s:/%s", prop.getProperty("app.ip"), prop.getProperty("app.port"), prop.getProperty("app.database"));
            this.connection= DriverManager.getConnection(url,prop.getProperty("app.username"),prop.getProperty("app.password"));
loadCustomers();
        } catch (SQLException e) {
            e.printStackTrace();

            Platform.runLater(()->{
                new Alert(Alert.AlertType.ERROR,"failed to established the database conection").show();
            });
            return;
        }

    }

    private void loadCustomers() throws SQLException {
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery("SELECT * FROM  customer");

        while(rst.next()){
            tblCustomers.getItems().add(new CustomerTM(rst.getString("id")
            rst.getString("name")
            rst.getString("address")));
        }
    }

    public void btnShowCustomerReport_OnAction(ActionEvent event) {
    }

    public void btnLoadData_OnAction(ActionEvent event) {
    }

    public void btnClearData_OnAction(ActionEvent event) {
    }
}
