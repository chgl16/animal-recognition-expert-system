package chgl16.space.pojo;

/**
 * 推理返回过程和结果
 * @author chgl16
 * @date 2019/12/22 22:36
 */
public class ResultDto {
    /** 过程 */
    private String process;

    /** 结果 */
    private String answer;

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public ResultDto(String process, String answer) {
        this.process = process;
        this.answer = answer;
    }

    public ResultDto() {
    }

    @Override
    public String toString() {
        return "ResultDto{" +
                "process='" + process + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}