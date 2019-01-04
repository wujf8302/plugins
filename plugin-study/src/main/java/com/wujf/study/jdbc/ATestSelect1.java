package com.wujf.study.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * 
 */
public class ATestSelect1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {

			// a. 加载驱动
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// b. 建立连接 对象
			String url="jdbc:oracle:thin:@localhost:1521:ORCL";
			Connection conn=
				DriverManager.getConnection(url,"fj_qq","fj_qq");
			
			// c. 建立发送sql语句的对象
			Statement stmt=conn.createStatement();
			
			// d. 执行sql语句,得到结果集对象
			String sql="select  id,name,password,dept," +
					"sex,age,address,isonline," +
					"regtime " +
					"from t_user ";
			ResultSet rs=stmt.executeQuery(sql);
			
			
			// e. 对结果集对象操作
			while ( rs.next() ){
				String id= rs.getString("id");
				String name= rs.getString("name");
				int age= rs.getInt("age");
				//String namge=rs.getString("age");
				//double aa=rs.getDouble("");
				
				System.out.print(id);
				System.out.print("   ");
				System.out.print(name);
				System.out.print("   ");
				System.out.print(age);
				System.out.print("   ");
				System.out.println();
			}
			
			
			// f. 释放资源 (b,c,d)
			conn.close();
			stmt.close();
			rs.close();
			
		} catch (Exception ex) {
			
			ex.printStackTrace();
			
		} finally {

		}
	}

}
