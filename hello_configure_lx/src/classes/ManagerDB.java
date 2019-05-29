package classes;

//import java.io.BufferedWriter;
//import java.io.FileWriter;
import java.io.IOException;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

import classes.WriteFile;

public class ManagerDB {

	// Variables
	private static String user = System.getProperty("user.name"); // Get user login
	//private static String os = System.getProperty("os.name"); // Get osName
	private static String urlJDBC = "jdbc:sqlite:/home/"+user+"/hello/hello.db";
	private static String logsPath = "/home/"+user+"/hello/configure.log";
	private static String nextLine = "\r\n";
	

	// Get system date.
	static SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	static Date nowDate = new Date(System.currentTimeMillis());
	static String date = sd.format(nowDate);
	

	public static String msg = " - Selected OS path: " + urlJDBC + nextLine;
	WriteFile wr = new WriteFile(logsPath, msg);

	public static void CreateDB() throws IOException {

		System.out.println(msg);

		// Create database
		try (Connection conn = DriverManager.getConnection(urlJDBC)) {
			if (conn != null) {
				@SuppressWarnings("unused")
				DatabaseMetaData meta = conn.getMetaData();
				System.out.println("DB created in: " + urlJDBC);

				String msg = " - Data Base create on: " + urlJDBC + nextLine;
				WriteFile wr = new WriteFile(logsPath, msg);

			}

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}

	}

	// Create table to insert configurations
	public static void CreateTable() throws IOException {

		// SQL statement for creating a new table
		String sql = "CREATE TABLE IF NOT EXISTS ip (id integer PRIMARY KEY, ip varchar(30) NOT NULL,"
				+ " s_user varchar(20), s_pass varchar(8));";

		try (Connection conn = DriverManager.getConnection(urlJDBC); Statement stmt = conn.createStatement()) {
			// create a new table
			stmt.execute(sql);
			System.out.println("Table created in: " + urlJDBC);

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	public static void InsertTable(String ip, String s_user, String s_pass) throws IOException {

		String sqld = "DELETE FROM ip WHERE id = 1;";
		String sql = "INSERT INTO ip VALUES (1, '" + ip + "', '" + s_user + "', '" + s_pass + "');";

		try (Connection conn = DriverManager.getConnection(urlJDBC); Statement stmt = conn.createStatement()) {

			stmt.execute(sqld);
			stmt.execute(sql);
			System.out.println("Inserted data in table: " + urlJDBC);

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}

		String msg = " - Table inserted: " + urlJDBC + nextLine;
		WriteFile wr = new WriteFile(logsPath, msg);

	}

	public static void SelectTable() throws IOException {

		// SQL statement for select table
		String sql = "SELECT* FROM ip;";

		try (Connection conn = DriverManager.getConnection(urlJDBC); Statement stmt = conn.createStatement()) {

			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				System.out.println(rs.getString("ip"));
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		String msg = " - Selected table: " + urlJDBC + nextLine;
		WriteFile wr = new WriteFile(logsPath, msg);

	}

}
