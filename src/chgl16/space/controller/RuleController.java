package chgl16.space.controller;

import chgl16.space.Core;
import chgl16.space.pojo.Rule;
import chgl16.space.pojo.RuleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author chgl16
 * @date 2019/12/22 20:26
 */
public class RuleController implements Initializable {

    @FXML
    private TableView<RuleProperty> tableViewComponent;

    @FXML
    private Button addButtonComponent;

    @FXML
    private Button deleteButtonComponent;

    @FXML
    private TableColumn<RuleProperty, Integer> idTableColumnComponent;

    @FXML
    private TableColumn<RuleProperty, String> conditionTableColumnComponent;

    @FXML
    private TableColumn<RuleProperty, String> resultTableColumnComponent;

    @FXML
    private TextField resultTextFieldComponent;

    @FXML
    private TextField conditionTextFieldComponent;

    @FXML
    private TextField idTextFieldComponent;

    @FXML
    private RadioButton isAddToAimsRadioButtonComponent;

    /**
     * 加载初始化
     * @param location
     * @param resources
     */
    public void initialize(URL location, ResourceBundle resources) {
        // 填充表格数据
        ObservableList<RuleProperty> data = FXCollections.observableArrayList();
        System.out.println("规则库数量: " + Core.ruleBase.size());
        for (int i = 0; i < Core.ruleBase.size(); ++i) {
            data.add(new RuleProperty(i + 1, Core.ruleBase.get(i).getP(), Core.ruleBase.get(i).getQ()));
        }

        // 字段数据绑定
        // 后面的String泛型是列字段类型，最后的condition是映射到RuleProperty对象的condition字段
        conditionTableColumnComponent.setCellValueFactory(new PropertyValueFactory<RuleProperty, String>("condition"));
        idTableColumnComponent.setCellValueFactory(new PropertyValueFactory("id"));
        resultTableColumnComponent.setCellValueFactory(new PropertyValueFactory<RuleProperty, String>("result"));

        // 字段数据可编辑
        conditionTableColumnComponent.setCellFactory(TextFieldTableCell.<RuleProperty>forTableColumn());
        resultTableColumnComponent.setCellFactory(TextFieldTableCell.<RuleProperty>forTableColumn());
        // 编辑提交触发函数
        conditionTableColumnComponent.setOnEditCommit(
                (TableColumn.CellEditEvent<RuleProperty, String> t) -> {
                    // 修改行数，0起
                    int row = t.getTablePosition().getRow();
                    // 修改后内容
                    String newValue = t.getNewValue();
                    System.out.println("修改了第" + (row + 1) + "行体条件，新内容为：" + newValue);
                    // List toString的样子：[鸟, 会游泳, 不会飞, 有黑白色]， 现在反转
                    List<String> list = new ArrayList<>();
                    for (String str : newValue.substring(1, newValue.length() - 1).split(", ")) {
                        list.add(str);
                    }
                    // 更新到规则库
                    Rule rule = Core.ruleBase.get(row);
                    rule.setP(list);
                    Core.ruleBase.set(row, rule);
                    System.out.println("修改后规则库为: " + Core.ruleBase);
                });
        resultTableColumnComponent.setOnEditCommit(
                (TableColumn.CellEditEvent<RuleProperty, String> t) -> {
                    // 修改行数，0起
                    int row = t.getTablePosition().getRow();
                    // 修改后内容
                    String newValue = t.getNewValue();
                    System.out.println("修改了第" + (row + 1) + "行结果，新内容为：" + newValue);
                    // 更新到规则库
                    Rule rule = Core.ruleBase.get(row);
                    rule.setQ(newValue);
                    Core.ruleBase.set(row, rule);
                    // 更新目标值
                    if (Core.aims.contains(t.getOldValue())) {
                        Core.aims.remove(t.getOldValue());
                        Core.aims.add(newValue);
                    }
                    System.out.println("修改后规则库为: " + Core.ruleBase);
                });


        // 加入数据展示
        tableViewComponent.setEditable(true);
        tableViewComponent.setItems(data);

        // 添加按钮事件绑定
        addButtonComponent.setOnAction((ActionEvent e) -> {
            String condition = conditionTextFieldComponent.getText();
            String result = resultTextFieldComponent.getText();

            if (condition.equals("")) {
                System.err.println("添加内容不允许为空");
                conditionTextFieldComponent.setText("添加内容不允许为空");
                return;
            }
            if (result.equals("")) {
                System.err.println("添加内容不允许为空");
                resultTextFieldComponent.setText("添加内容不允许为空");
                return;
            }

            List<String> p = new ArrayList<>();
            for (String str : condition.split("&")) {
                p.add(str);
            }
            // 添加到规则库
            Core.ruleBase.add(new Rule(p, result));
            // 是否添加到目标集
            if (isAddToAimsRadioButtonComponent.isSelected())
                Core.aims.add(result);
            System.out.println(Core.aims);
            // table添加局部刷新
            data.add(new RuleProperty(Core.ruleBase.size(), p, result));
            conditionTextFieldComponent.clear();
            resultTextFieldComponent.clear();
        });

        // 添加按钮事件绑定
        deleteButtonComponent.setOnAction((ActionEvent e) -> {
            // 获取删除的规则编号
            if (idTextFieldComponent.getText().equals("")) {
                System.err.println("编号不允许为空");
                idTextFieldComponent.setText("编号不允许为空");
                //idTextFieldComponent.setStyle(" -fx-text-fill:RED");
                return;
            }
            Integer id = null;
            try {
                id = Integer.valueOf(idTextFieldComponent.getText());
            } catch (NumberFormatException ex) {
                System.err.println("非法字符");
                idTextFieldComponent.setText("非法字符");
                return;
            }
            if (id > Core.ruleBase.size() || id <= 0) {
                System.err.println("非法边界");
                idTextFieldComponent.setText("非法边界");
                return;
            }
            Core.ruleBase.remove(id - 1);
            // data.remove(id - 1);
            // 刷新保证不会中断编号
            initialize(location, resources);
            idTextFieldComponent.clear();
        });
    }
}
