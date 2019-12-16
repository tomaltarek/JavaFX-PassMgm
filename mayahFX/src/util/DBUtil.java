/*
 * This class establishes connections and provide methods to DAO to be used.
 * Ideally DBUtil class in MVC model is a helper class, it should have at least below methods
 * 1. A database connection method
 * 2. A database disconnection method
 * 3. A databse sql execute method for insert, update and delete, this mehtod will use above 
 *    connection and disconnection method
 * 4. A database sql query method, usually return result of query via resultset, theis mehtod
 *    will use connection and disconnection method stated in 1 and 2. 
 *    
 *    Ideally methods are static. 
 */

package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.sun.rowset.CachedRowSetImpl;

public class DBUtil {
	private static final String JDBC_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
	private static final String connStr = "jdbc:derby:tomaldb";
	private static Connection connection = null;

	public static void dbConnect() throws SQLException, ClassNotFoundException {
		try {
			Class.forName(JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
			System.out.println("Seems to be issue");
			e.printStackTrace();
			throw e;
		}
		System.out.println("JDBC for derby has been registered");
		try {
			connection = DriverManager.getConnection(connStr);

		} catch (SQLException e) {
			System.out.println("Connection failed check console" + e);
			throw e;
		}

	}

	public static void dbDisconnect() throws SQLException {

		try {
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}

		} catch (Exception e) {
			throw e;
		}
	}

	// for insert/delete/update operation
	public static void dbExecuteQuery(String sqlStmt) throws SQLException, ClassNotFoundException {
		Statement statement = null;
		try {
			dbConnect();
			statement = connection.createStatement();
			statement.executeUpdate(sqlStmt);

		} catch (Exception e) {
			System.out.println("Problem found while executing statement");
			throw e;
		}

		finally {
			if (statement != null) {
				statement.close();

			}
			dbDisconnect();

		}

	}

	// for query
	public static ResultSet dbExecute(String sqlQuery) throws SQLException, ClassNotFoundException {
		Statement statement = null;
		ResultSet rs = null;
		CachedRowSetImpl crs = null;

		try {
			dbConnect();
			statement = connection.createStatement();
			rs = statement.executeQuery(sqlQuery);

			if (!rs.next()) {
				System.out.println("no data");
			}
			crs = new CachedRowSetImpl();
			crs.populate(rs);

		} catch (Exception e) {
			System.out.println("Hit error while running dbExecute" + e);
			throw e;
		} finally {
			if (rs != null)
				rs.close();
			if (statement != null)
				statement.close();
		}
		dbDisconnect();

		return crs;

	}
	//this is kind of out of place, should be in DAO though
	public static String dbGetPin() throws SQLException, ClassNotFoundException {
		Statement statement = null;
		ResultSet rs = null;
		String sqlQuery = "select pin from pin";
		String value = null;

		try {
			dbConnect();
			statement = connection.createStatement();
			rs = statement.executeQuery(sqlQuery);

			if (rs.next()) {
				value = rs.getString("pin");
			}

		} catch (Exception e) {
			System.out.println("Error getting pin" + e);
			throw e;
		} finally {
			if (rs != null)
				rs.close();
			if (statement != null)
				statement.close();
		}
		dbDisconnect();
		return value;

	}

}
