package com.wujf.study.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ATestSelect2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			// a. 加载驱动
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// b. 建立连接 对象
			String url = "jdbc:oracle:thin:@localhost:1521:ORCL";
			conn = DriverManager.getConnection(url, "fj_qq", "fj_qq");

			// c. 建立发送sql语句的对象
			stmt = conn.createStatement();

			// d. 执行sql语句,得到结果集对象
			String sql = "select " + "id,name,password,dept,"
					+ "sex,age,address,isonline," + "regtime "
					+ "from t_user ";
			rs = stmt.executeQuery(sql);

			// e. 对结果集对象操作
			while (rs.next()) {

				String id = rs.getString(1);
				String name = rs.getString(2);
				int age = rs.getInt(6);
				// String namge=rs.getString("nage");
				// double aa=rs.getDouble("");

				System.out.print(id);
				System.out.print("   ");
				System.out.print(name);
				System.out.print("   ");
				System.out.print(age);
				System.out.print("   ");
				System.out.println();
			}

		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {

			try {
				// f. 释放资源 (b,c,d)
				conn.close();
				stmt.close();
				rs.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}
	}

}
