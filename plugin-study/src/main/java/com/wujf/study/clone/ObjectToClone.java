package com.wujf.study.clone;

/**
 * 待clone的对象，必须实现Cloneable这个tag interface
 */
public class ObjectToClone implements Cloneable{
   private int i=0;
 
   public void setNum(int i){
       this.i=i;
   }
 
   public int getNum(){
       return this.i;
   }
   
   public void tellNum(){
       System.out.println("the number is "+i);
   }
   // 重写Object的clone
   public Object clone() throws CloneNotSupportedException {
        return (ObjectToClone)super.clone();
    }
}
