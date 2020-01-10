package chgl16.space.pojo;

import java.util.List;

/**
 * 规则类
 * @author chgl16
 * @date 2019/12/22 22:44
 */
public class Rule {
    /** 条件 */
    private List<String> p;

    /** 结果 */
    private String q;

    public Rule(List<String> p, String q) {
        this.p = p;
        this.q = q;
    }

    public Rule() {
    }

    public List<String> getP() {
        return p;
    }

    public void setP(List<String> p) {
        this.p = p;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    @Override
    public String toString() {
        return "Rule{" +
                "p=" + p +
                ", q='" + q + '\'' +
                '}';
    }
}

