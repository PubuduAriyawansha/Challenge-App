package lk.ijse.dep11;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.dep11.tm.Student;

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
    }

    public void btnLoadCsvOnAction(ActionEvent actionEvent) {
    }
}
