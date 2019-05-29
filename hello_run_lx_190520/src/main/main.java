package main;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.File;

import org.hyperic.sigar.Cpu;
import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.NetInterfaceStat;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

import com.mysql.jdbc.Statement;

import classes.ConnectDB;
import classes.WriteFile;

public class main {

	private static String logsPath = "C:\\hello\\run.log";
	private static String user = System.getProperty("user.name"); // Get user login
	private static String version = "0.1.190501"; // Software version, check connectDB file too.
	private static String os = System.getProperty("os.name"); // Get Operational system
	private static String nextLine = "\r\n";
	private static String cpuModel= null;
	private static String showRam = null;
	

			String msg = " - OS Selected: Windows" + nextLine;
			WriteFile wr = new WriteFile(logsPath, msg);
			
			
//			// GET CPU INFO
//			public static void getCpuInfo() throws SigarException {
//				
//			Sigar sigar = new Sigar();
//			
//			CpuInfo[] infos = sigar.getCpuInfoList();
//			CpuInfo info = infos[0];
//	
//			cpuModel = info.getVendor() +" "+ info.getModel();
//			
//			}
			
			
			
//			//GET RAM MEMORY
//			public static void getRamMemory() throws SigarException {
//				Sigar sigar = new Sigar();
//				
//				Mem mem = sigar.getMem();
//				long totalRam  = mem.getRam();
//				showRam = Long.toString(totalRam) + " MB";		
//			}
//			
			

			
	public static void main(String[] args) throws SQLException, IOException, SigarException {

	
//		getCpuInfo();
//		getRamMemory();
		
		
		// Get system date.
		SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy");
		Date nowDate = new Date(System.currentTimeMillis());
		String date = sd.format(nowDate);

		// Get system date.
		SimpleDateFormat sdd = new SimpleDateFormat("dd");
		Date nowDay = new Date(System.currentTimeMillis());
		String day = sdd.format(nowDay);

		// Get User
		String host = null;
		String ip = null;

		// Get host-name
		try {
			host = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {

			e.printStackTrace();
		}

		// Get IP
		ip = InetAddress.getLocalHost().getHostAddress();

		ConnectDB db = new ConnectDB();
		db.OpenConnction();

		String query0 = "SELECT * FROM macs WHERE hostname='" + host + "'";
		ResultSet rst = null;

		try {
			db.setResultset(db.getStmt().executeQuery(query0));
			rst = db.getStmt().executeQuery(query0);
		} catch (SQLException e1) {

			e1.printStackTrace();
		}

		if (rst.next()) {

			System.out.println("host exists, will be updated");
			String msg = " - host " + host + " exists, will be updated" + nextLine;
			WriteFile wr = new WriteFile(logsPath, msg);

			db.setStmt((Statement) db.getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY));

			String query;
			query = "UPDATE macs SET date='" + date + "',vClient='" + version + "', ip='" + ip + "', user='" + user
					+ "', status='" + day + "' where hostname='" + host + "';";

			db.getStmt().execute(query);

		} else {

			System.out.println("host not exists, will be inserted!");
			String msg = " - host not exists, will be inserted" + nextLine;
			WriteFile wr = new WriteFile(logsPath, msg);

			db.setStmt((Statement) db.getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY));

			String query;
			query = "INSERT INTO macs VALUES (null, '" + host + "','" + ip + "','" + os + "','" + user + "','" + date
					+ "','" + day + "','" + version + "','" + cpuModel + "','" + showRam + "','" + null + "','" + null + "','" +
					null + "','" + null +"');";
					

			db.getStmt().execute(query);

		}

		String msg = " - User loged: " + user + nextLine;
		WriteFile wr = new WriteFile(logsPath, msg);
		db.CloseConnection();
		
		boolean status = true;
		
	}

}
