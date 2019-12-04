package model;

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
	
	public static void updatetUser(String description,String userName,String password, String old_desc)throws SQLException, ClassNotFoundException {
		String sql="update users set description='"+description+"',username='"+userName+"',password='"+password+"' where description='"+old_desc+"' ";
		try {
			DBUtil.dbExecuteQuery(sql);
			
		} catch (SQLException e) {
			System.out.println("Error updating record"+e);
			e.printStackTrace();
			throw e; 
		}
	}
	
	public static void deletetUser(String description)throws SQLException, ClassNotFoundException {
		String sql="delete from users where description='"+description+"'";
		try {
			DBUtil.dbExecuteQuery(sql);
			
		} catch (SQLException e) {
			System.out.println("Error deleting record"+e);
			e.printStackTrace();
			throw e; 
		}
	}
	
//showing records
	public static ObservableList<User> getAllRecords() throws ClassNotFoundException,SQLException{
		String sql="select * from users";
		try {
			ResultSet rSet=DBUtil.dbExecute(sql);
			ObservableList<User> userList=getUserObjects(rSet);			
			return userList;
			
		} catch (Exception e) {
			System.out.println("Error at fetching records"+e);
			e.printStackTrace();
			throw e; 
		}
		
	
	}

private static ObservableList<User> getUserObjects(ResultSet rSet) throws SQLException,ClassNotFoundException {
	try {
		ObservableList<User> userlist=FXCollections.observableArrayList();
		while (rSet.next()) {
			User user=new User();
			user.setDescription(rSet.getString("description"));			
			user.setUserName(rSet.getString("username"));
			user.setPassword(rSet.getString("password"));
			userlist.add(user);
			
		}
		
		return userlist;
		
		
	} catch (Exception e) {
		System.out.println("Error at fetching records"+e);
		e.printStackTrace();
		throw e; 
	}
	
	
}

}
