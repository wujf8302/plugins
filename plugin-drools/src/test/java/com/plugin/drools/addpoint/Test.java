package com.plugin.drools.addpoint;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import com.plugin.api.PathUtil;
import com.plugin.drools.addpoint.model.PointDomain;
import com.plugin.drools.service.RuleService;
import com.plugin.drools.service.impl.RuleServiceImpl;
/**
rule "<name>"
<attribute>*
when
<conditional element>*
then
<action>*
end
 * @author wujf

    什么时候应该使用规则引擎,这实际是一个技术选型的问题。
    
    但这个问题又似乎是一个很关键的问题（一旦返工的话，你就知道这个问题是多么重要了）。
    
    不知大家有没有过这样的经验和体会。往往在项目开始的时候，总会遇到应该选用什么技术？是不是应该使用最新的技术？或者应该选用什么技术呢
    （PS：现在计算机软件中的各种技术层出不穷，具有类似功能的技术很多）？
    
    不管怎么样，这些问题总会困扰着我。比如，这次的这个项目。项目要求是要在一些log文件中（这些log文件都是很大的应用系统所产生的，
    但由于legacy的原因，log本身的维护和规范工作一直没有得到改善，所以想借助于一些外部应用对这些log做以分析和清洗）抽取出有用的信息。
    于是，第一个想到的就是，这是一个文本挖掘类的项目。但又想，要抽取有用信息，必须得建立一些规则或pattern（模式）。
    所以，我第一个想到了规则引擎。因为这里面要建立好多规则，而这些规则可以独立于代码级别（放到一个单独的drl文件里）并可以用规则引擎去
    解析和执行。另一个重要的原因是，我原来用过，比较熟悉。这样，也可以节省开发时间吧。于是，好不犹豫的就开始做了Demo....
     但事实上，在经历了一个多星期的编码、测试后，我发现运用规则引擎实在是太笨拙了。
    （1）首先必须建立一些数据模型。通过这些模型来refer规则文件中的LHS和Action。
    （2）还要考虑规则的conflict。如果有一些规则同时被触发，就要考虑设定规则的优先级或者是设定activiation-group来保证在一个group中的
    规则只有一个规则可以被触发。
    （3）对于‘流’规则group ruleflow-group的使用。如果要控制在workingmemory中的规则被触发的顺序，则可以将这些规则分组。然后，通过规则
    建模的方式来实现。但这也添加了一定的effort。修改或者更新不大方便。
    所以，基于上述体会，我更认为规则引擎更适用于那些对非流程性规则匹配的应用。当然，Drools也支持对流程性规则的建模过程。但，这也许不是
    最好的方式。

   详细请参考：http://www.codesky.net/article/200811/118843.html
 *
 */
public class Test {
	
	public static void main(String[] args) throws Exception {

		RuleService service = new RuleServiceImpl();
		
		List<String> drls = new ArrayList<String>();
		drls.add(PathUtil.getFile("drools/addpoint.drl").getAbsolutePath());
		drls.add(PathUtil.getFile("drools/subpoint.drl").getAbsolutePath());
		
		while (true) {
			System.out.println("请输入命令：");//输入e
			InputStream is = System.in;
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String input = br.readLine();
			if (null != input && "s".equals(input)) {
				System.out.println("初始化规则引擎...");
				service.init(drls);
				System.out.println("初始化规则引擎结束.");
				
			} else if ("e".equals(input)) {
				
				final PointDomain pointDomain = new PointDomain();
				
				System.out.println("初始化规则引擎...");
				service.init(drls);
				System.out.println("初始化规则引擎结束.");
				pointDomain.setUserName("hello kity");
				pointDomain.setBackMondy(100d);
				pointDomain.setBuyMoney(500d);
				pointDomain.setBackNums(1);
				pointDomain.setBuyNums(5);
				pointDomain.setBillThisMonth(5);
				pointDomain.setBirthDay(true);
				pointDomain.setPoint(0l);
				
				service.execute(pointDomain);
				
				System.out.println("执行完毕BillThisMonth："+ pointDomain.getBillThisMonth());
				System.out.println("执行完毕BuyMoney：" + pointDomain.getBuyMoney());
				System.out.println("执行完毕BuyNums：" + pointDomain.getBuyNums());
				System.out.println("执行完毕规则引擎决定发送积分：" + pointDomain.getPoint());
				
			} else if ("r".equals(input)) {
				System.out.println("刷新规则文件...");
				service.refresh(drls);
				System.out.println("刷新规则文件结束.");
			}
		}
	}
}
