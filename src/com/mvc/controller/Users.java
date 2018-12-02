/**
 * PATCH NOTES:
 * September 20 2018: Skeleton code created
 * October 3 2018: Created working code - untested
 * October 13 2018: Tested code, patched bugged code
 * October 19 2018: Added getters/setters code
 * November 30 2018: Added JavaDoc comments
 */
import java.sql.*;
import java.lang.String;

public class Users {
	   	
	private static final String TEST = "jdbc:mysql://127.0.01:3306/e-booking?user=root&password=password";
		
	//data stuff
	private int USERID;
	private String EMAIL;
	private String PASSWORD;
	private String FNAME;
	private String LNAME;
	private int PHONE;
	private int PROMO;
	private int STATUS;
	private int USERTYPE;

	//container for stuff related to queries
	static Statement stmt = null;
	static ResultSet rs = null;
	static Connection con = null;
	static PreparedStatement pstmt = null;

	/**
	 * Default constructor - primarily used for retrieval of data using getters/setters
	 */
	public Users() {
		//this.USERID = 0;
		this.EMAIL = null;
		this.PASSWORD = null;
		this.FNAME = null;
		this.LNAME = null;
		//this.ADDRESS = null;
		this.PHONE = 0;
		//this.PAYMENT = null;
		this.PROMO = 0;
		this.STATUS = 1;
		this.USERTYPE = 1;
	}

	/**
	 * Overloaded constructor - Used to create new Users using parameters
	 * @param email Email address
	 * @param pass Password
	 * @param first First Name
	 * @param last Last Name
	 * @param phone Phone Number
	 * @param subscription code
	 */
	public Users(String email, String pass, String first, String last, int phone, int subscription) {	
		this.EMAIL = email;
		this.PASSWORD = pass;
		this.FNAME = first;
		this.LNAME = last;		
		this.PHONE = phone;
		this.PROMO = subscription;
		this.STATUS = 1;
		this.USERTYPE = 2;
	}

	/**
	 * Retrieves user info based on email
	 * @param email 
	 */
	public void retrieveUsersData(String email) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(TEST);
			String query = "SELECT userID, password, firstName, lastName, phone, promoSub FROM Users WHERE email = ? ";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1,  email);
			rs = pstmt.executeQuery();

			while(rs.next()) {
				this.USERID = rs.getInt("userID");
				this.EMAIL = email;
				this.PASSWORD = rs.getString("password");
				this.FNAME = rs.getString("firstName");
				this.LNAME = rs.getString("lastName");
				//this.ADDRESS = rs.getString("address");
				this.PHONE = rs.getInt("phone");
				this.PROMO = rs.getInt("promoSub");
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
	}//retrieveUsersData(String email)

	/**
	 * Checks if password matches user
	 * @param password
	 * @return boolean whether the password matches 
	 */
	public boolean checkPassword(String password) {
		return this.PASSWORD.equals(password);
	}

	/**
	 * Checks password against email
	 * @param email
	 * @param password
	 * @return boolean whether the password matches the email address
	 */
	public boolean checkPassword(String email, String password) /*throws SQLException*/ {
		
		String pw_test = "";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(TEST);

			String query = "SELECT password FROM Users WHERE email = ? ";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			
			while(rs.next())
				pw_test = rs.getString("password");

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
		return pw_test.equals(password);
	}
	
	boolean login(String email, String password) {
		if(checkPassword(email, password)) {
			retrieveUsersData(email);
			return true;
		}
		else
			return false;
	}

	
/**
 * Adds users to Users table
 */
	public void addRegUsersToDB() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(TEST);//change later to different URL if needed
			String query = "INSERT into Users(email, password, firstName, lastName, phone, status, promoSub, userType) values(?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(query);

			pstmt.setString(1,  EMAIL);
			pstmt.setString(2,  PASSWORD);
			pstmt.setString(3,  FNAME);
			pstmt.setString(4,  LNAME);
			//pstmt.setString(5,  ADDRESS);
			pstmt.setInt(5,  PHONE);
			pstmt.setInt(6,  STATUS);
			pstmt.setInt(7, PROMO);
			pstmt.setInt(8,  USERTYPE);
			pstmt.executeUpdate();

			pstmt.close();
			con.close();
			
			//System.out.println("ADDED to DB successfully");
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
	}//addRegUsersToDB


	/**
	 * Deletes the user from Users table
	 */
	public void deleteRegUsersFromDB() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(TEST);
			String query = "DELETE FROM Users WHERE email = ?";
			pstmt = con.prepareStatement(query);

			pstmt.setString(1, EMAIL);
			pstmt.executeUpdate();

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
		//clears the attributes to allow same object to be used.
		reset();

	}//deleteRegUsersFromDB
/**
 * Resets the users variables	
 */
	public void reset() {
		this.USERID = 0;
		this.EMAIL = null;
		this.PASSWORD = null;
		this.FNAME = null;
		this.LNAME = null;
		//this.ADDRESS = null;
		this.PHONE = 0;
		//this.PAYMENT = null;
		this.PROMO = 0;
		this.STATUS = 0;
		this.USERTYPE = 0;
	}
	
	public void logout() {
		reset();
	}

	/**
	 * Updates the user info in the database
	 * @param email of which user to update
	 * @param infoType 
	 * @param update
	 */
	public void updateUserInfo(String email, String infoType, String update) {
		//update where email = ?//DO NOT ALLOW EMAIL BE CHANGED!
		//the following variables should not be touched by reg-users
		//email, status, and usertype
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(TEST);//change later to different URL if needed
			
			String type = "";
			switch(infoType) {
			case "PW": type = "password"; break;
			case "FN": type = "firstName"; break;
			case "LN": type = "lastName"; break;
			case "PN": type = "phone"; break;
			case "PS": type = "promoSub"; break;
			}
			
			String query = "UPDATE Users set " + type + " = ? WHERE email = ? ";
			pstmt = con.prepareStatement(query);
			int num = 0;
			
			switch(type) {
			case "password": pstmt.setString(1,  update); break;
			case "firstName": pstmt.setString(1,  update); break;
			case "lastName": pstmt.setString(1,  update); break;
			case "phone": num = Integer.parseInt(update); pstmt.setInt(1, num); break;
			case "promosub": num = Integer.parseInt(update); pstmt.setInt(1, num); break;
			}
			
			pstmt.setString(2,  email);
			
			pstmt.executeUpdate();
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
	}
	
/**
 * Deletes entire users table -- UNRECOVERABLE
 */
	public void deleteUsersTable() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(TEST);
			String query = "SET FOREIGN_KEY_CHECKS=0";//Truncate resets AUTO_INCREMENT to 1 unlike DELETE which does not reset AUTO_INCREMENT and will resume at last digit.
			pstmt = con.prepareStatement(query);
			pstmt.executeUpdate();
			
			query = "TRUNCATE TABLE Users";
			pstmt = con.prepareStatement(query);
			pstmt.executeUpdate();
			
			query = "SET FOREIGN_KEY_CHECKS=1";
			pstmt = con.prepareStatement(query);
			pstmt.executeUpdate();

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
	}
	/**
	 * Gets User id based on email
	 * @param email
	 * @return id of user associated with email
	 */
	public int getIDNum(String email) {
		int idtoReturn = 0;//invalid if 0

		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(TEST);

			String query = "SELECT userID FROM Users WHERE email = ? ";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			
			while(rs.next())
				idtoReturn = rs.getInt("userID");

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
		return idtoReturn;
	}

	@Override
	public String toString() {
		return "Current Users data:\n"
				+ "UID: \t" + USERID + "\n"
				+ "EMAIL: \t" + EMAIL + "\n"
				+ "FULL NAME:\t" + FNAME + " " + LNAME + "\n"
				//+ "ADDRESS: \t" + ADDRESS + "\n"
				+ "PHONE #\t" + PHONE + "\n"
				+ "PROMOTION SUBSCRIPTION:\t" + (PROMO==1 ? "YES\n":"NO\n");
	}
}//Users