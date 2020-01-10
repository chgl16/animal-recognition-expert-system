package chgl16.space;

import chgl16.space.pojo.ResultDto;
import chgl16.space.pojo.Rule;

import java.io.*;
import java.util.*;

/**
 * @author chgl16
 * @date 2019/12/22 12:18
 */
public class Core {

    /** 书本预设规则库 */
    private static final String RULES_FILE_PATH = "rules.txt";

    /** 目标集合 */
    public static Set<String> aims;

    /** 规则库 */
    public static List<Rule> ruleBase;

    /**
     * 系统数据初始化
     */
    public static void init() {
        // 创建目标集合
        aims = new HashSet<String>() {{
            add("虎");
            add("豹");
            add("斑马");
            add("长颈鹿");
            add("企鹅");
            add("鸵鸟");
            add("信天翁");
        }};

        // 创建规则库
        ruleBase = new ArrayList<>();

        // 不使用File读取，打包后路径问题无法解决，一般的相对路径也无效。只能Clsss.getResourceAsStream(RULES_FILE_PATH)
        // 读取初始化文件
        // File file = new File(RULES_FILE_PATH);
        String encoding = "UTF-8";
        //if (file.isFile() && file.exists()) {
            try {
                //InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file), encoding);
                InputStream inputStream = Core.class.getResourceAsStream(RULES_FILE_PATH);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, encoding));
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    String[] arr = line.split("->");
                    // 加入规则库
                    List<String> p = new ArrayList<>();
                    for (String str : arr[0].split("&"))
                        p.add(str);
                    ruleBase.add(new Rule(p, arr[1]));
                }
                inputStream.close();
                bufferedReader.close();
            } catch (Exception e) {
                System.err.println("读取初始化文件内容出错");
            }
        //} else {
          //  System.out.println("找不到指定文件");
        //}
    }

    /**
     * 推理机
     * @param dataBase 用户输入的事实检索条件，即当前数据库
     * @return
     */
    public static ResultDto reason(Set<String> dataBase) {
        ResultDto resultDto = new ResultDto();
        resultDto.setProcess("");
        int count = 0;
        while (true) {
            // 判断是否有新的推理可用
            boolean change = false;
            for (Rule rule : ruleBase) {
                // 判断数据库是否包含当前规则的所有条件
                boolean flag = true;
                for (String condition : rule.getP()) {
                    if (!dataBase.contains(condition)) {
                        flag = false;
                        break;
                    }
                }
                // 满足当前规则所有条件
                if (flag) {
                    // 将当前规则结果加入数据库
                    if (!dataBase.contains(rule.getQ())) {
                        dataBase.add(rule.getQ());
                        change = true;
                        System.out.println(++count + ".使用规则: " + rule.toString() + ", 推出了: " + rule.getQ());
                        System.out.println("当前数据库: " + dataBase);
                        resultDto.setProcess(resultDto.getProcess() + count + ".使用规则: " + rule.toString() + ", 推出了: " + rule.getQ() + "\n");
                        resultDto.setProcess(resultDto.getProcess() + "当前数据库: " + dataBase + "\n\n");
                    }

                    // 如果是目标集元素，推理结束
                    if (aims.contains(rule.getQ())) {
                        resultDto.setAnswer(rule.getQ());
                        return resultDto;
                    }
                }
            }
            // 没有新推理可用
            if (!change)
                break;
        }
        resultDto.setAnswer("无法识别具体目标动物");
        return resultDto;
    }
}







