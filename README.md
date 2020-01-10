# 动物识别专家系统
    用产生式系统设计的一个简单动物识别专家系统，正向推理，支持规则增删改查
      

## 1. 开发环境
![Depfu](https://img.shields.io/depfu/chgl16/animal-recognition-expert-system?color=green&label=Java&logo=V&logoColor=green&style=flat-square)
&nbsp; ![Depfu](https://img.shields.io/depfu/chgl16/animal-recognition-expert-system?color=red&label=SceneBuilder&logo=V&logoColor=blue&style=flat-square)  

## 2. 运行效果  
```bash
java -jar out/artifacts/expertSystem/expertSystem.jar
```  

![程序一次推理过程展示](https://i.loli.net/2020/01/10/FGVZhpw2c1v6UbI.png)  

![规则的增删改查操作界面](https://i.loli.net/2020/01/10/1b7fIXpsP3w9heK.png)  

## 3. 设计实现  
#### 步骤一：规则库、目标集、数据库的建立
1.	规则库ruleBase采用全局变量方式声明，类型为List<Rule>，泛型的Rule对象是包括规则前提条件P和结论Q的POJO。使用List也是为了方便JavaFX表格单元的索引操作问题。
2.	数据库Set<String> dataBase即用户输入的事实、规则前提，作为输入参数传入推理机推理。Set使用HashSet实现类，内部的即HashMap哈斯表结构，无冲突情况下可以在O(1)时间复杂度内命中规则前提P。
3.	目标集Set<String> aims也为全局变量，元素是要求识别的动物：虎、豹、斑马等。Set也是使用HashSet实现类，更快判断aims.contains(rule.Q)。

#### 步骤二：推理机的设计和实现
推理机的推理逻辑过程如下：
1.	遍历规则库中的所有规则，对于每条规则Ri，判断当前数据库是否包括Ri的所有前提P。如果是，把Ri的结论Q加入数据库。
2.	多次循环遍历，直到遍历的规则Ri的结论Q属于目标集aims，推理结束，识别动物为Q，或者所有规则遍历结束后，数据库也不再变化，推理结束，无法识别具体动物。  

```java
/**
 * 推理机
 * @param dataBase 用户输入的事实检索条件，即当前数据库
 * @return 推理过程和结果对象ResultDto
 */
public static ResultDto reason(Set<String> dataBase) {
    ResultDto resultDto = new ResultDto();
    resultDto.process = "";
    int count = 0;
    while (true) {
        // 判断是否有新的推理可用
        boolean change = false;
        for (Rule rule : ruleBase) {
            // 判断数据库是否包含当前规则的所有条件
            boolean flag = true;
            for (String condition : rule.p) {
                if (!dataBase.contains(condition)) {
                    flag = false;
                    break;
                }
            }
            // 满足当前规则所有条件
            if (flag) {
                // 将当前规则结果加入数据库
                if (!dataBase.contains(rule.q)) {
                    dataBase.add(rule.q);
                    change = true;
                    resultDto.process += count + ".使用规则: "
                            + rule.toString() + ", 推出了: " 
+ rule.q + "\n";
                    resultDto.process += "当前数据库: " 
+ dataBase + "\n\n";
                }

                // 如果是目标集元素，推理结束
                if (aims.contains(rule.q)) {
                    resultDto.answer = rule.q;
                    return resultDto;
                }
            }
        }
        // 没有新推理可用
        if (!change)
            break;
    }
    resultDto.answer = "无法识别具体目标动物";
    return resultDto;
}
```  

## 4. 问题总结  
[1. JavaFX开发总结](http://chgl16.space/2019/12/10/java/javafx-chang-yong-zong-jie/)