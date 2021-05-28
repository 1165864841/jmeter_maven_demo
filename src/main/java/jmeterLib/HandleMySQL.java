package jmeterLib;

import java.sql.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 
 * TODO:数据源 数据库
 *
 * @author Joe-Tester
 * @time 2021年5月28日
 * @file HandleMySQL.java
 */
public class HandleMySQL implements Iterator<Object[]> {
	ResultSet rs;
	ResultSetMetaData rd;

	public HandleMySQL(String ip, String port, String baseName,
			String userName, String password, String sql)
			throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		String url = String.format("jdbc:mysql://%s:%s/%s", ip, port, baseName);
		Connection conn = DriverManager.getConnection(url, userName, password);
		Statement createStatement = conn.createStatement();
		rs = createStatement.executeQuery(sql);
		rd = rs.getMetaData();
		System.out.println(rd.getColumnCount());
	}

	/**
	 * 
	 */
	@Override
	public boolean hasNext() {
		boolean flag = false;
		try {
			flag = rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 
	 */
	@Override
	public Object[] next() {
		Map<String, String> data = new HashMap<String, String>();
		try {
			for (int i = 1; i <= rd.getColumnCount(); i++) {
				data.put(rd.getColumnName(i), rs.getString(i));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Object r[] = new Object[1];
		r[0] = data;
		return r;
	}

	/**
	 * 
	 */
	@Override
	public void remove() {
		try {
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}