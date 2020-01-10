package chgl16.space.pojo;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * JavaFX TableView的ObservableList<RuleProperty>记录列表泛型对象，对应Rule
 * @author chgl16
 * @date 2019/12/23 9:24
 */
public class RuleProperty {
    private SimpleIntegerProperty id;

    private SimpleStringProperty condition;

    private SimpleStringProperty result;

    public RuleProperty(Integer id, List condition, String result) {
        this.id = new SimpleIntegerProperty(id);
        this.condition = new SimpleStringProperty(condition.toString());
        this.result = new SimpleStringProperty(result);
    }

    public Integer getId() {
        return id.get();
    }

    public void setId(Integer id) {
        this.id.set(id);
    }

    public String getCondition() {
        return condition.get();
    }

    public void setCondition(List condition) {
        this.condition.set(condition.toString());
    }

    public String getResult() {
        return result.get();
    }

    public void setResult(String result) {
        this.result.set(result);
    }
}
