package chgl16.space.controller;

import chgl16.space.Core;
import chgl16.space.Main;
import chgl16.space.pojo.ResultDto;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class IndexController implements Initializable {

    @FXML
    private TextArea userInputComponent;

    @FXML
    private TextField resultComponent;

    @FXML
    private TextArea processComponent;

    public void initialize(URL location, ResourceBundle resources) {

    }

    public void goReason() {
        // 获取用户输入的事实转为set
        Set<String> dataBase = new HashSet<>();
        String[] arr = userInputComponent.getText().split("\n");
        for (String str : arr) {
            dataBase.add(str);

        }

        ResultDto resultDto = Core.reason(dataBase);
        System.out.println(dataBase);
        resultComponent.setText(resultDto.getAnswer());
        processComponent.setText(resultDto.getProcess());
        System.out.println("推理结果为: " + resultDto.getAnswer());
    }

    public void goRuleView() throws IOException {
        // 打开新窗口
        Parent root = FXMLLoader.load(Main.class.getResource("view/rules.fxml"));
        Stage stage = new Stage();
        stage.setTitle("动物识别专家系统");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
