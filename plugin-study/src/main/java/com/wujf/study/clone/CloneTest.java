package com.wujf.study.clone;
/**
 * 测试clone是否成功
 */
public class CloneTest {
    public static void main(String[] args) {
        ObjectToClone otc = new ObjectToClone();
        otc.setNum(888);
       
        // 开始clone了！
        ObjectToClone cloneObject = null;
        try {
            cloneObject = (ObjectToClone) otc.clone();
        } catch (CloneNotSupportedException ex) {
            System.out.println("Sorry,Clone Not Supported!");
        }
       
        // 测试clone是否成功
        if(cloneObject!=null){
            System.out.println("before clone");
            System.out.println("ObjectToClone get number : " + otc.getNum()+"\n");
       
            System.out.println("after clone");
            System.out.println("ObjectToClone get number : " + otc.getNum());
           
            cloneObject.setNum(999);
            System.out.println("CloneObject get number : " + cloneObject.getNum());
       
            System.out.println("after clone");
            System.out.println("ObjectToClone get number : " + otc.getNum());
        }       
    }
}
