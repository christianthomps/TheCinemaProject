import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//this class should not need any setters as we do not want to manipulate/change this specific database class
//therefore, only getters are permitted
public class UserTypes {
	/*
	 * 1 = Admin
	 * 2 - RegisteredUser
	 * 3 = Employee
	 * 
	 * */

	//specific for maddies comp
	//private static final String TEST = "jdbc:mysql://127.0.0.1:3306/e-booking?user=root&password=June201998";
		
	private static final String TEST = "jdbc:mysql://127.0.01:3306/e-booking?user=root&password=password";
		
	
	//variables for UserStatus;
	private int TYPEID;
	private String TYPENAME;
	//container for stuff related to queries
	static Statement stmt = null;
	static ResultSet rs = null;
	static Connection con = null;
	static PreparedStatement pstmt = null;

	//this may not be necessary
	public UserTypes() {
		this.TYPEID = 0;
		this.TYPENAME = "";
	}

	int returnTypeID(String typeName) {
		TYPEID = 0;//0 should be invalid
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(TEST);
			String query = "SELECT userTypeID FROM UserTypes WHERE userTypeName = ? ";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1,  typeName);
			rs = pstmt.executeQuery();

			while(rs.next()) {
				this.TYPEID = rs.getInt("UserTypeID");
				this.TYPENAME = typeName;
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
		
		return TYPEID;
	}

	String returnTypeName(int id) {
		TYPENAME = null;//this should be invalid
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(TEST);
			String query = "SELECT userTypeName FROM UserTypes WHERE userTypeID = ? ";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1,  id);
			rs = pstmt.executeQuery();

			while(rs.next()) {
				this.TYPEID = id;
				this.TYPENAME = rs.getString("userTypeName");
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
		return TYPENAME;
	}

	//this may not be necessary as there is one above already
	int typeID() {
		return TYPEID;
	}
	//this may no be necessary as there is one above already
	String typeName() {
		return TYPENAME;
	}
}