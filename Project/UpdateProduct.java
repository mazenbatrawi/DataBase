package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class UpdateProduct {

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
    private TextField barcode;

    @FXML
    private TextField company_id;

    @FXML
    private TextField expire_date;

    @FXML
    private TextField name;

    @FXML
    private TextField price;

    @FXML
    private TextField production_date;

    @FXML
    private TextField quantity;

    @FXML
    private TextField sbarcode;

    @FXML
    private TextField scompany_id;

    @FXML
    private TextField section_id;

    @FXML
    private TextField selling_price;

    @FXML
    private TextField sexpire_date;

    @FXML
    private TextField sname;

    @FXML
    private TextField sprice;

    @FXML
    private TextField sproduction_date;

    @FXML
    private TextField squantity;

    @FXML
    private TextField ssection_id;

    @FXML
    private TextField sselling_price;

    @FXML
    private ImageView update_image;

    @FXML
    void back(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("sample_product.fxml"));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Products");
        stage.show();
    }

    @FXML
    void update(ActionEvent event) {
        if(sbarcode.getText().isEmpty() && sname.getText().isEmpty() && scompany_id.getText().isEmpty() && ssection_id.getText().isEmpty() &&
                sprice.getText().isEmpty() && squantity.getText().isEmpty() && sproduction_date.getText().isEmpty() &&
                sexpire_date.getText().isEmpty() && sselling_price.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please fill at least one field for your update criterion");
            alert.showAndWait();
        }
        else if(barcode.getText().isEmpty() && name.getText().isEmpty() && company_id.getText().isEmpty() && section_id.getText().isEmpty() &&
                price.getText().isEmpty() && quantity.getText().isEmpty() && production_date.getText().isEmpty() &&
                expire_date.getText().isEmpty() && selling_price.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please fill at least one field for your new values");
            alert.showAndWait();
        }
        else if(!sproduction_date.getText().isEmpty() && (sproduction_date.getText().length() != 10 ||
                (sproduction_date.getText().length() == 10 &&
                        (sproduction_date.getText().charAt(2) != '-' || sproduction_date.getText().charAt(5) != '-')))){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Check The Production Date Format (dd-mm-yyyy)");
            alert.showAndWait();
        }
        else if(!sexpire_date.getText().isEmpty() && (sexpire_date.getText().length() != 10 ||
                (sexpire_date.getText().length() == 10 &&
                        (sexpire_date.getText().charAt(2) != '-' || sexpire_date.getText().charAt(5) != '-')))){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Check The Expire Date Format (dd-mm-yyyy)");
            alert.showAndWait();
        }
        else if(!production_date.getText().isEmpty() && (production_date.getText().length() != 10 ||
                (production_date.getText().length() == 10 &&
                        (production_date.getText().charAt(2) != '-' || production_date.getText().charAt(5) != '-')))){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Check The Production Date Format (dd-mm-yyyy)");
            alert.showAndWait();
        }
        else if(!expire_date.getText().isEmpty() && (expire_date.getText().length() != 10 ||
                (expire_date.getText().length() == 10 &&
                        (expire_date.getText().charAt(2) != '-' || expire_date.getText().charAt(5) != '-')))){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Check The Expire Date Format (dd-mm-yyyy)");
            alert.showAndWait();
        }
        else{
            query = "update product set ";
            if (!barcode.getText().isEmpty()) query += "barcode = \"" + barcode.getText() + "\", ";
            if (!name.getText().isEmpty()) query += "product_name = \"" + name.getText() + "\", ";
            if (!company_id.getText().isEmpty()) query += "company_id = " + company_id.getText() + ", ";
            if (!section_id.getText().isEmpty()) query += "section_id = " + section_id.getText() + ", ";
            if (!price.getText().isEmpty()) query += "price = " + price.getText() + ", ";
            if (!quantity.getText().isEmpty()) query += "total_quantity = " + quantity.getText() + ", ";
            if (!production_date.getText().isEmpty()) query += "production_date = \"" + production_date.getText() + "\", ";
            if (!expire_date.getText().isEmpty()) query += "expire_date = \"" + expire_date.getText() + "\", ";
            if (!selling_price.getText().isEmpty()) query += "selling_price = " + selling_price.getText() + ", ";

            query = query.substring(0, query.length() - 2) + " where ";

            if (!sbarcode.getText().isEmpty()) query += "barcode = \"" + sbarcode.getText() + "\" and ";
            if (!sname.getText().isEmpty()) query += "product_name = \"" + sname.getText() + "\" and ";
            if (!scompany_id.getText().isEmpty()) query += "company_id = " + scompany_id.getText() + " and ";
            if (!ssection_id.getText().isEmpty()) query += "section_id = " + ssection_id.getText() + " and ";
            if (!sprice.getText().isEmpty()) query += "price = " + sprice.getText() + " and ";
            if (!squantity.getText().isEmpty()) query += "total_quantity = " + squantity.getText() + " and ";
            if (!sproduction_date.getText().isEmpty()) query += "production_date = '" + sproduction_date.getText() + "' and ";
            if (!sexpire_date.getText().isEmpty()) query += "expire_date = '" + sexpire_date.getText() + "' and ";
            if (!sselling_price.getText().isEmpty()) query += "selling_price = " + sselling_price.getText() + " and ";

            query = query.substring(0, query.length() - 5);

            try {
                connectDB();
                Statement stmt = con.createStatement();
                stmt.executeUpdate(query);

                barcode.setText(null);
                name.setText(null);
                company_id.setText(null);
                expire_date.setText(null);
                section_id.setText(null);
                price.setText(null);
                quantity.setText(null);
                production_date.setText(null);
                selling_price.setText(null);

                sbarcode.setText(null);
                sname.setText(null);
                scompany_id.setText(null);
                ssection_id.setText(null);
                sprice.setText(null);
                squantity.setText(null);
                sproduction_date.setText(null);
                sexpire_date.setText(null);
                sselling_price.setText(null);

                con.close();
            }
            catch (SQLException | ClassNotFoundException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("An error happened, please check your data!");
                alert.showAndWait();
                ex.printStackTrace();
            }
        }
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
