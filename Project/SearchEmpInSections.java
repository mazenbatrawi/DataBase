package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class SearchEmpInSections {

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
    String query = null;

    @FXML
    private ImageView back_image;

    @FXML
    private ImageView search_image;

    @FXML
    private CheckBox hire_date_button;

    @FXML
    private CheckBox password_button;

    @FXML
    private CheckBox id_button;

    @FXML
    private CheckBox name_button;

    @FXML
    private CheckBox national_id_button;

    @FXML
    private CheckBox phone_button;

    @FXML
    private CheckBox salary_button;

    @FXML
    private CheckBox section_id_button;

    @FXML
    private CheckBox section_name_button;

    @FXML
    private CheckBox shift_button;

    @FXML
    private TextField sid;

    @FXML
    private TextField sname;

    @FXML
    private TableView<Employee> table;

    @FXML
    private TableView<Section> table1;

    ObservableList<Employee> list_emp = FXCollections.observableArrayList();
    ObservableList<Section> list_sec = FXCollections.observableArrayList();

    @FXML
    void backClick(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("sample_section.fxml"));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Sections");
        stage.show();
    }

    @FXML
    void searchClick(ActionEvent event) throws SQLException, ClassNotFoundException {

        if(sid.getText().isEmpty() && sname.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please fill at least one field for your search criterion");
            alert.showAndWait();
        }

        else if(!id_button.isSelected() && !national_id_button.isSelected() && !hire_date_button.isSelected() && !name_button.isSelected() &&
                !salary_button.isSelected() && !shift_button.isSelected() && !section_id_button.isSelected() && !phone_button.isSelected()
                && !section_name_button.isSelected() && !password_button.isSelected()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Select at least 1 column to show from the check boxes");
            alert.showAndWait();
        }
        else{
            query = "select ";

            if(section_id_button.isSelected()) query += "Section.section_id, ";
            if(section_name_button.isSelected()) query += "Section.section_name, ";
            if(id_button.isSelected()) query += "employee.id, ";
            if(national_id_button.isSelected()) query += "employee.national_id, ";
            if(name_button.isSelected()) query += "employee.emp_name, ";
            if(phone_button.isSelected()) query += "employee.phone, ";
            if(shift_button.isSelected()) query += "employee.shift, ";
            if(salary_button.isSelected()) query += "employee.salary, ";
            if(hire_date_button.isSelected()) query += "employee.hire_date, ";
            if(password_button.isSelected()) query += "employee.password, ";

            query = query.substring(0, query.length() - 2) + " from employee, Section where ";

            if(!sid.getText().isEmpty()) query += "section.section_id = " + sid.getText() + " and ";
            if(!sname.getText().isEmpty()) query += "section.section_name = \"" + sname.getText() + "\" and ";

            query = query.substring(0, query.length() - 5);
            query += " and section.section_id = employee.section_id";

            connectDB();
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            ArrayList<String> arrayList = new ArrayList<>();

            table.getItems().clear();
            table.getColumns().clear();
            table1.getItems().clear();
            table1.getColumns().clear();

            if(section_id_button.isSelected()) {addColumnSec("Section ID", "section_id"); arrayList.add("section_id");}
            if(section_name_button.isSelected()) {addColumnSec("Section Name", "section_name"); arrayList.add("section_name");}
            if(id_button.isSelected()) {addColumnEmp("Employee ID", "id"); arrayList.add("id");}
            if(national_id_button.isSelected()) {addColumnEmp("National ID", "national_id"); arrayList.add("national_id");}
            if(name_button.isSelected()) {addColumnEmp("Employee Name", "name"); arrayList.add("name");}
            if(phone_button.isSelected()) {addColumnEmp("Phone", "phone"); arrayList.add("phone");}
            if(shift_button.isSelected()) {addColumnEmp("Shift", "shift"); arrayList.add("shift");}
            if(salary_button.isSelected()){
                TableColumn<Employee, Double> column = new TableColumn<>();
                column.setText("Salary");
                column.setMinWidth(130);
                column.setCellValueFactory(new PropertyValueFactory<Employee, Double>("salary"));
                table.getColumns().add(column);
                arrayList.add("salary");
            }
            if(hire_date_button.isSelected()) {addColumnEmp("Hire Date", "hire_date"); arrayList.add("hire_date");}
            if(password_button.isSelected()) {addColumnEmp("Password", "password"); arrayList.add("password");}

            while(resultSet.next()){
                Employee emp = new Employee();
                Section sec = new Section();
                boolean section = false;
                for(int i = 0; i < arrayList.size(); i++){
                    switch (arrayList.get(i)) {
                        case "section_id":
                            sec.setSection_id(resultSet.getString(i + 1));
                            section = true;
                            break;
                        case "password":
                            emp.setPassword(resultSet.getString(i + 1));
                            break;
                        case "section_name":
                            sec.setSection_name(resultSet.getString(i + 1));
                            section = true;
                            break;
                        case "id":
                            emp.setId(resultSet.getString(i + 1));
                            break;
                        case "national_id":
                            emp.setNational_id(resultSet.getString(i + 1));
                            break;
                        case "name":
                            emp.setName(resultSet.getString(i + 1));
                            break;
                        case "phone":
                            emp.setPhone(resultSet.getString(i + 1));
                            break;
                        case "salary":
                            emp.setSalary(resultSet.getDouble(i + 1));
                            break;
                        case "hire_date":
                            emp.setHire_date(resultSet.getString(i + 1));
                            break;
                        case "shift":
                            emp.setShift(resultSet.getString(i + 1));
                            break;
                        default:
                            assert false;
                            break;
                    }
                }
                if(section){
                    list_sec.add(sec);
                }
                list_emp.add(emp);
            }
            table.setItems(list_emp);
            table1.setItems(list_sec);
            con.close();
        }
    }

    @FXML
    private void addColumnEmp(String Name, String name) {
        TableColumn<Employee, String> column = new TableColumn<>();
        column.setText(Name);
        column.setMinWidth(130);
        column.setCellValueFactory(new PropertyValueFactory<Employee, String>(name));
        table.getColumns().add(column);
    }

    @FXML
    private void addColumnSec(String Name, String name) {
        TableColumn<Section, String> column = new TableColumn<>();
        column.setText(Name);
        column.setMinWidth(130);
        column.setCellValueFactory(new PropertyValueFactory<Section, String>(name));
        table1.getColumns().add(column);
    }

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

}
