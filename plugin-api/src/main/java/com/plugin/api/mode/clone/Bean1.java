package com.plugin.api.mode.clone;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.Serializable;

public class Bean1 implements Cloneable,Serializable{

	public int x;
	public static int y;
	
	public Bean2 bean2;
	
	public Bean1(int x,int y){
	   this.x = x;
	   this.y = y;
	}
	
	public Bean1(int x,int y,Bean2 bean2){
	   this.x = x;
	   this.y = y;
	   this.bean2 = bean2;
	}
	
	//浅复制
	public Object qclone() {
		Object clone = null;
		try {
			clone = super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return clone;
	}
	
	//深复制
	public Object sclone() {
		Object clone = null;
		try {
			clone = super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		((Bean1)clone).bean2 = (Bean2)bean2.clone();
		
		return clone;
	}
	
	public Object deepClone(){
		try {
			 //将对象写到流里
			 ByteArrayOutputStream bo = new ByteArrayOutputStream();
			 ObjectOutputStream oo = new ObjectOutputStream(bo);
			 oo.writeObject(this);
			 //从流里读出来
			 ByteArrayInputStream bi = new ByteArrayInputStream(bo.toByteArray());
			 ObjectInputStream oi = new ObjectInputStream(bi);
			 return (oi.readObject());
		}catch(IOException e) {
			e.printStackTrace();
		}catch(ClassNotFoundException e) {	
			e.printStackTrace();
		}
		return null;
   }
}
