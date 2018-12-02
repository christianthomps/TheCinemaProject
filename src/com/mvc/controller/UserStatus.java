/**
 * PATCH NOTES:
 * September 20 2018: Skeleton code created
 * October 19 2018: Created working code - untested
 * October 20 2018: Tested code, patched bugged code
 * November 29 2018: Added JavaDoc comments
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//this class should not need any setters as we do not want to manipulate/change this specific database class
//therefore, only getters are permitted
public class UserStatus {
	/*
	 * 1 = Active
	 * 2 - Inactive
	 * 3 = Suspended
	 * 
	 * */

	//specific for maddies comp
	//private static final String TEST = "jdbc:mysql://127.0.0.1:3306/e-booking?user=root&password=June201998";
		
	private static final String TEST = "jdbc:mysql://127.0.01:3306/e-booking?user=root&password=password";
		
	
	//variables for UserStatus;
	private int STATUSID;
	private String STATUSTYPE;
	//container for stuff related to queries
	static Statement stmt = null;
	static ResultSet rs = null;
	static Connection con = null;
	static PreparedStatement pstmt = null;

	/**
	 * Default constructor - primarily used for retrieval of data using getters/setters
	 */
	public UserStatus() {
		this.STATUSID = 0;
		this.STATUSTYPE = "";
	}

	/**
	 * Returns status ID
	 * @param statusType Status type name (Active, Inactive, Suspended)
	 */

	int returnStatus(String statusType) {
		STATUSID = 0;//0 should be invalid
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(TEST);
			String query = "SELECT statusID FROM UserStatus WHERE statusType = ? ";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1,  statusType);
			rs = pstmt.executeQuery();

			while(rs.next()) {
				this.STATUSID = rs.getInt("statusID");
				this.STATUSTYPE = statusType;
			}

			rs.close();
			pstmt.close();
			con.close();
		}catch(SQLException se) {
			se.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt!=null)
					pstmt.close();
			}catch(SQLException se2) {
			}
			try {
				if(con!=null)
					con.close();
			}catch(SQLException se) {
				se.printStackTrace();
			}
		}
		
		return STATUSID;
	}

	/**
	 * Returns status type
	 * @param status Use 1, 2, or 3 (1 = Active, 2 = Inactive, 3 = Suspended)
	 */
	String returnStatusType(int status) {
		STATUSTYPE = null;//this should be invalid
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(TEST);
			String query = "SELECT statusType FROM UserStatus WHERE statusID = ? ";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1,  status);
			rs = pstmt.executeQuery();

			while(rs.next()) {
				this.STATUSID = status;
				this.STATUSTYPE = rs.getString("statusType");
			}

			rs.close();
			pstmt.close();
			con.close();
		}catch(SQLException se) {
			se.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt!=null)
					pstmt.close();
			}catch(SQLException se2) {
			}
			try {
				if(con!=null)
					con.close();
			}catch(SQLException se) {
				se.printStackTrace();
			}
		}
		return STATUSTYPE;
	}

	//this may not be necessary as there is one above already
	int statusID() {
		return STATUSID;
	}
	//this may no be necessary as there is one above already
	String statusType() {
		return STATUSTYPE;
	}
}
