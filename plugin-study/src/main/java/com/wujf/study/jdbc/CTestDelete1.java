package com.wujf.study.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class CTestDelete1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Connection conn = null;
		Statement stmt = null;
		int count=0;
		try {
			// a. 加载驱动
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// b. 建立连接 对象
			String url = "jdbc:oracle:thin:@localhost:1521:ORCL";
			conn = DriverManager.getConnection(url, "fj_qq", "fj_qq");

			// c. 建立发送sql语句的对象
			stmt = conn.createStatement();

			// d. 执行sql语句,得到结果集对象
			String sql = "delete from t_user where id='00008'";
			count=stmt.executeUpdate(sql);

			System.out.println(count);

		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {

			try {
				// e. 释放资源 (b,c,d)
				conn.close();
				stmt.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}
	}

}
