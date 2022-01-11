package sample;

import java.io.IOException;
import java.net.URL;
import java.sql.*;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.*;

import java.util.*;

import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Properties;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class EmployeeInterface implements Initializable {
    private String dbURL;
    private String dbUsername = "root";
    private String dbPassword = "16062001";
    private String URL = "127.0.0.1";
    private String port = "3306";
    private String dbName = "fostoqa";
    private Connection con;
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TableColumn<Employee, String> hire_date;

    @FXML
    private TableColumn<Employee, String> password;

    @FXML
    private TableColumn<Employee, String> id;

    @FXML
    private TableColumn<Employee, String> name;

    @FXML
    private TableColumn<Employee, String> national_id;

    @FXML
    private TableColumn<Employee, String> phone;

    @FXML
    private TableColumn<Employee, Double> salary;

    @FXML
    private TableColumn<Employee, String> section_id;

    @FXML
    private TableColumn<Employee, String> shift;

    @FXML
    private TableView<Employee> table;

    @FXML
    private ImageView image_add;

    @FXML
    private ImageView home_page_image;

    @FXML
    private ImageView image_search;

    @FXML
    private ImageView image_update;

    ObservableList<Employee> list = FXCollections.observableArrayList();

    private void connectDB() throws ClassNotFoundException, SQLException {
        dbURL = "jdbc:mysql://" + URL + ":" + port + "/" + dbName + "?verifyServerCertificate=false";
        Properties p = new Properties();
        p.setProperty("user", dbUsername);
        p.setProperty("password", dbPassword);
        p.setProperty("useSSL", "false");
        p.setProperty("autoReconnect", "true");
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection (dbURL, p);
    }

    @FXML
    public void readData(){
        try {
            connectDB();
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from employee order by id asc");
            while(resultSet.next()){
                list.add(new Employee(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        Double.parseDouble(resultSet.getString(6)),
                        resultSet.getString(7),
                        resultSet.getString(8),
                        resultSet.getString(9)
                ));
            }
            con.close();
        }
        catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(java.net.URL url, ResourceBundle rb) {
        readData();
        id.setCellValueFactory(new PropertyValueFactory<Employee, String>("id"));
        national_id.setCellValueFactory(new PropertyValueFactory<Employee,String>("national_id"));
        name.setCellValueFactory(new PropertyValueFactory<Employee,String>("name"));
        phone.setCellValueFactory(new PropertyValueFactory<Employee,String>("phone"));
        section_id.setCellValueFactory(new PropertyValueFactory<Employee,String>("section_id"));
        shift.setCellValueFactory(new PropertyValueFactory<Employee,String>("shift"));
        salary.setCellValueFactory(new PropertyValueFactory<Employee, Double>("salary"));
        hire_date.setCellValueFactory(new PropertyValueFactory<Employee, String>("hire_date"));
        password.setCellValueFactory(new PropertyValueFactory<Employee, String>("password"));
        table.setItems(list);
    }

    @FXML
    public void insertClick(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("InsertEmployeeController.fxml"));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Add New Employees");
        stage.show();
    }

    @FXML
    public void updateClick(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("UpdateEmployeeController.fxml"));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Update Employees");
        stage.show();
    }

    @FXML
    public void searchClick(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("SearchEmployeeController.fxml"));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Search Employees");
        stage.show();
    }

    @FXML
    void homePage(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("main.fxml"));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Home Page");
        stage.show();
    }
}