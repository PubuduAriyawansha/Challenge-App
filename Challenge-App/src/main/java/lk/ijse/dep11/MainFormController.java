package lk.ijse.dep11;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.dep11.db.DbConnection;
import lk.ijse.dep11.tm.Student;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MainFormController {
    public AnchorPane root;
    public TextField txtName;
    public TextField txtID;
    public TextField txtCard;
    public Button btnSave;
    public Button btnDelete;
    public Button btnNew;
    public Button btnLoadCsv;
    public TableView<Student> tblStudent;

    public void initialize(){
        tblStudent.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblStudent.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblStudent.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("card"));
        tblStudent.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("status"));

        btnDelete.setDisable(true);
        tblStudent.getSelectionModel().selectedItemProperty().addListener((o,old,cur)->{
            btnDelete.setDisable(cur==null);
            if(cur!=null){
                txtID.setText(cur.getId());
                txtName.setText(cur.getName());
                txtCard.setText(cur.getCard());
            }
        });
    }

    public void btnSaveONACtion(ActionEvent actionEvent) {
    }

    public void btnDeleteOnACtion(ActionEvent actionEvent) {
    }

    public void btnNewOnACtion(ActionEvent actionEvent) {
        txtID.clear();
        txtName.clear();
        txtCard.clear();
        txtID.requestFocus();
        tblStudent.getSelectionModel().clearSelection();
    }

    public void btnLoadCsvOnAction(ActionEvent actionEvent) {
    }
    public boolean addStudent(String id, String name, String card){
        Connection connection = DbConnection.getInstance().getConnection();
        try {
            Statement stm = connection.createStatement();
            String sql = String.format("SELECT id FROM student WHERE id = '%s'",id);
            ResultSet rst = stm.executeQuery(sql);
            if(rst.next()){
                return false;
            }
            Statement stmAdd = connection.createStatement();
            sql=String.format("INSERT INTO student (id,name,card) VALUES ('%s','%s','%s')",id,name,card);
            int affected = stmAdd.executeUpdate(sql);
            if(affected!=1){
                return false;
            } else {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean deleteStudent(String id){
        Connection connection = DbConnection.getInstance().getConnection();
        try {
            Statement stm = connection.createStatement();
            String sql = String.format("DELETE FROM student WHERE id ='%s'",id);
            int affected = stm.executeUpdate(sql);
            return affected==1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean isValid(String id){

        int currentBatch = Integer.parseInt(System.getProperty("dep batch","11"));
        if(!id.matches("\\d{2}03\\d{2}1\\d{3}")) return false;

        int year = Integer.parseInt(id.substring(0,2));
        int currentYear = Integer.parseInt(LocalDate.now().format(DateTimeFormatter.ofPattern("YY")));

        if(!(year>=18 && year<=currentYear)) return false;

        int batch = Integer.parseInt(id.substring(4,6));
        if(!(batch>0 && batch<=currentBatch)) return false;

        int studentNumber = Integer.parseInt(id.substring(id.length() - 3));
        return (studentNumber>0 && studentNumber<80);
    }

}
