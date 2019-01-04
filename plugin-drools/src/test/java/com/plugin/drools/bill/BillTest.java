package com.plugin.drools.bill;

import org.drools.RuleBase;
import com.plugin.api.PathUtil;
import com.plugin.drools.bill.model.Bill;
import com.plugin.drools.utils.DroolsUtil;

//测试代码
public class BillTest {
	
    public static void main(String[] args) throws Exception{
    	
        RuleBase ruleBase = DroolsUtil.getRuleBase(PathUtil.getInputStream("billRules.txt"),"UTF-8");

        Bill bill = new Bill();

        //1,送审
        bill.setStatus("待审批");
        bill.setAction("送审");
        DroolsUtil.runAllRules(ruleBase,bill);
        
        System.out.println(bill.getStatus() + ": " + "部门经理审批".equalsIgnoreCase(bill.getStatus()));

        //2, 部门经理同意
        bill.setStatus("部门经理审批");
        bill.setAction("同意");
        bill.setAmount(10000d);
        DroolsUtil.runAllRules(ruleBase,bill);
        
        System.out.println(bill.getStatus() + ": " + "财务审核".equalsIgnoreCase(bill.getStatus()));
        
        bill.setStatus("部门经理审批");
        bill.setAction("同意");
        bill.setAmount(20000d);
        DroolsUtil.runAllRules(ruleBase,bill);
        
        System.out.println(bill.getStatus() + ": " + "总经理审批".equalsIgnoreCase(bill.getStatus()));

        //4, 总经理审批
        bill.setStatus("总经理审批");
        bill.setAction("同意");
        DroolsUtil.runAllRules(ruleBase,bill);
        
        System.out.println(bill.getStatus() + ": " + "财务审核".equalsIgnoreCase(bill.getStatus()));

        //5, 财务审核
        bill.setStatus("财务审核");
        bill.setAction("审核");
        DroolsUtil.runAllRules(ruleBase,bill);
        
        System.out.println(bill.getStatus() + ": " + "审批生效".equalsIgnoreCase(bill.getStatus()));

        //驳回
        bill.setStatus("部门经理审批");
        bill.setAction("驳回");
        DroolsUtil.runAllRules(ruleBase,bill);
        
        System.out.println(bill.getStatus() + ": " + "驳回".equalsIgnoreCase(bill.getStatus()));

        bill.setStatus("总经理审批");
        bill.setAction("驳回");
        DroolsUtil.runAllRules(ruleBase,bill);;
        
        System.out.println(bill.getStatus() + ": " + "驳回".equalsIgnoreCase(bill.getStatus()));
    }
}
