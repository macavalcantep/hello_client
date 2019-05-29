package classes;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class ConnectDB {

	static String userLog = System.getProperty("user.name"); // Get user login
	static String version = "0.1.190501"; // Version of software, check main file too.

	private static Connection con = null;
	private static Statement stmt = null;
	private static ResultSet resultset = null;
	private static String ip = null;
	private static String server = "";
	private static String user = "";
	private static String pass = ""; // DataBase password.
	private static String driver = "com.mysql.jdbc.Driver";
	private static String urlJDBC = "jdbc:sqlite:C:\\hello\\hello.db";
	private static String logsPath = "C:\\hello\\run.log";
	private static String os = System.getProperty("os.name"); // Get osName
	private static String nextLine = "\r\n";


	public Connection OpenConnction() {

		// SQL statement for creating a new table
		String sql = "SELECT* FROM ip;";

		try (java.sql.Connection conn = DriverManager.getConnection(urlJDBC);
				java.sql.Statement stmt = conn.createStatement()) {
			
			// create a new table
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String id = rs.getString("id");
				
				if (id.equals("1")) {
					server = "jdbc:mysql://" + rs.getString("ip") + ":3306/hello_client";
					user = rs.getString("s_user");
					pass = rs.getString("s_pass");
				} else {
					JOptionPane.showMessageDialog(null, " - Error to select IP: " + rs.getString("ip"));
					String msg = "Error to select IP: " + rs.getString("ip") + nextLine;
					WriteFile wr = new WriteFile(logsPath, msg);
				}
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		try {

			Class.forName(driver).newInstance();
			setCon((Connection) DriverManager.getConnection(server, user, pass));
			setStmt((Statement) getCon().createStatement());
			System.out.println("Open connection success !" + server + "," + user + "," + pass);
			String msg = " - Open connection success !" + server + "," + user + "," + nextLine;
			WriteFile wr = new WriteFile(logsPath, msg);

		} catch (Exception e) {
			System.out.println("Open connection error !");
			String msg = " - Open connection error !" + nextLine;
			WriteFile wr = new WriteFile(logsPath, msg);

			e.printStackTrace();
		}
		return getCon();

	}

	public void CloseConnection() {
		try {
			getCon().close();
			System.out.println("Close connection susseful !");
			String msg = " - Close connection susseful !" + nextLine;
			WriteFile wr = new WriteFile(logsPath, msg);

		} catch (Exception e) {
			System.out.println("Close connection error !" + e.getMessage());
			JOptionPane.showMessageDialog(null, "Error to close conection !" + e.getMessage());
			String msg = " - Error to close conection !" + nextLine;
			WriteFile wr = new WriteFile(logsPath, msg);

		}
	}

	public static Statement getStmt() {
		return stmt;
	}

	public static void setStmt(Statement stmt) {
		ConnectDB.stmt = stmt;
	}

	public static ResultSet getResultset() {
		return resultset;
	}

	public static void setResultset(ResultSet resultset) {
		ConnectDB.resultset = resultset;
	}

	public static Connection getCon() {
		return con;
	}

	public static void setCon(Connection con) {
		ConnectDB.con = con;
	}

}
