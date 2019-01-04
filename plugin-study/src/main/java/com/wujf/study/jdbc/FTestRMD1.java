package com.wujf.study.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class FTestRMD1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		ResultSetMetaData rsmd=null;
		try {
			// a. 加载驱动
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// b. 建立连接 对象
			String url = "jdbc:oracle:thin:@localhost:1521:ORCL";
			conn = DriverManager.getConnection(url, "fj_qq", "fj_qq");

			// c. 建立发送sql语句的对象
			String sql="select * from t_user";
			pstmt=conn.prepareStatement(sql);

			// d. 执行sql语句,得到结果集对象
			rs=pstmt.executeQuery();
			rsmd=rs.getMetaData();
			
			System.out.println( rsmd.getColumnCount() );
			System.out.println( rsmd.getColumnName(1) );
			
			System.out.println("____________");
			for(int i=1;i<=rsmd.getColumnCount();i++){
				System.out.println( rsmd.getColumnName(i) );
			}

			

		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {

			try {
				// e. 释放资源 (b,c,d)
				conn.close();
				pstmt.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}
	}

}
