package model;

import java.sql.SQLException;

import util.DBUtil;

public class UserDAO {
	public static void insertUser(String description,String userName,String password)throws SQLException, ClassNotFoundException {
		String sql="insert into users values('"+description+"','"+userName+"','"+password+"')";
		try {
			DBUtil.dbExecuteQuery(sql);
			
		} catch (SQLException e) {
			System.out.println("Error inserting record"+e);
			e.printStackTrace();
			throw e; 
		}
	}

}
