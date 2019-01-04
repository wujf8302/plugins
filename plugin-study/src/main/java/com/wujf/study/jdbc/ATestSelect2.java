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
			// a. ��������
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// b. �������� ����
			String url = "jdbc:oracle:thin:@localhost:1521:ORCL";
			conn = DriverManager.getConnection(url, "fj_qq", "fj_qq");

			// c. ��������sql���Ķ���
			stmt = conn.createStatement();

			// d. ִ��sql���,�õ����������
			String sql = "select " + "id,name,password,dept,"
					+ "sex,age,address,isonline," + "regtime "
					+ "from t_user ";
			rs = stmt.executeQuery(sql);

			// e. �Խ�����������
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
				// f. �ͷ���Դ (b,c,d)
				conn.close();
				stmt.close();
				rs.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}
	}

}
