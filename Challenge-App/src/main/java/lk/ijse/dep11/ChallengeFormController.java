package lk.ijse.dep11;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.ijse.dep11.db.DbConnection;
import lk.ijse.dep11.tm.Student;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ChallengeFormController {
    public Button btnBack;
    public Label txtName;
    public Button btnFinish;
    public Button btnStart;
    public AnchorPane root;
    public String selecetedStudent;
    public void initialize(){
        btnFinish.setDisable(true);
    }
    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane root1 = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        Scene MainScene = new Scene(root1);

        Stage stage = new Stage();
        stage.setScene(MainScene);
        stage.initOwner(root.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("Vhallenge");
        stage.show();
    }

    public void btnFinishOnAction(ActionEvent actionEvent) {
        System.out.println(selecetedStudent);
        Connection connection = DbConnection.getInstance().getConnection();
        try {
            Statement stm = connection.createStatement();
            String sql = String.format("UPDATE student SET status = TRUE WHERE name = '%s'",selecetedStudent);
            int result = stm.executeUpdate(sql);
            System.out.println(result);
            if (result==1){
                new Alert(Alert.AlertType.INFORMATION,"Successfully complete the challenge").show();
            } else {
                new Alert(Alert.AlertType.ERROR,"Something went wrong").show();
            }
            txtName.setText("");
            btnFinish.setDisable(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnStartOnACtion(ActionEvent actionEvent) {

        btnStart.setText("PICK");
        Connection connection = DbConnection.getInstance().getConnection();
        try{
            Statement stm = connection.createStatement();
            String sql = "SELECT * FROM student WHERE status ='FALSE'";
            ResultSet rst = stm.executeQuery(sql);
            List<Student>studentList=new ArrayList<>();
            while (rst.next()){
                String id =rst.getString("id");
                String name = rst.getString("name");
                String card = rst.getString("card");

                studentList.add(new Student(id,name,card,"YET TO FACE"));
            }
            Task<Void> AnimatedStudent = new Task<>() {
                @Override
                protected Void call() throws Exception {
                    while (true){
                        for(Student student: studentList){
                            if(student.getCard().isBlank()){
                                Platform.runLater(()->{
                                    txtName.setText(student.getName());
                                });
                            } else {
                                Platform.runLater(()->{
                                    txtName.setText(student.getName()+" : "+student.getCard());
                                });
                            }
                            Thread.sleep(100);
                        }
                    }
                }
            };
            new Thread(AnimatedStudent).start();

            btnStart.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
//                    txtName.setText(txtName.getText());
                    String[] split = txtName.getText().split(" : ");
                    selecetedStudent = split[0];
                    AnimatedStudent.cancel();
                    btnStart.setText("SKIP");
                    btnFinish.setDisable(false);

                    btnStart.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            btnStartOnACtion(actionEvent);
                        }
                    });

                }
            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
