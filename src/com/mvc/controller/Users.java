import java.sql.*;
import java.lang.String;

public class Users {
	
	//private static final String MYHOST = "jbdc:mysql://127.0.01:3306/Project";//not used atm
	//private static final String Users = "localhost";//"localhost";//not used atm
	//private static final String PASS = "root";//"mysql-0327";//not used atm
	   
	private static final String TEST = "jdbc:mysql://127.0.0.1:3306/e-booking?user=root&password=June201998";
	
	//data stuff
	private int USERID;
	private String EMAIL;
	private String PASSWORD;
	private String FNAME;
	private String LNAME;
	//private String ADDRESS;
	private int PHONE;
	//private String PAYMENT; //fix this later, should be credit card information, should switch to table I think or class
	private int PROMO;//change this to boolean?
	private int STATUS; //fix this later
	private int USERTYPE;

	//container for stuff related to queries
	static Statement stmt = null;
	static ResultSet rs = null;
	static Connection con = null;
	static PreparedStatement pstmt = null;

	// Constructor for Users();
	// Initializes variables to NULL/0 as placeholder.
	// Probably best used for debugging purpose?
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

	// Overloaded constructor for Users();
	// Initializes variables to values set by Users.
	//defaults to 1 for Status, 2 for UserType since that's for registration
	public Users(String email, String pass, String first, String last, int phone, int subscription) {
		//this.USERID = 0;
		this.EMAIL = email;
		this.PASSWORD = pass;
		this.FNAME = first;
		this.LNAME = last;
		//this.ADDRESS = address;
		this.PHONE = phone;
		//this.PAYMENT = null;//Not sure how to implement this
		this.PROMO = subscription;
		this.STATUS = 1;
		this.USERTYPE = 2;
	}

	//Retrieve Users's data from database using email
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

	//should return false or true if password match
	//this need to be updated to more secure way of testing than loading the data from other functions
	//this will be changed to string email, string password with connector for security
	public boolean checkPassword(String password) {
		return this.PASSWORD.equals(password);
	}

	//secure function, use in future once everything is set up to use MD5() hashing
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

	//Adds Users to database EX: Users.addRegUsersToDB();
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

	//Deletes Users from database EX: Users.deleteRegUsersFromDB();
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
		//for now, just reset to clear Users crap
		reset();
	}

	//updates database with updated info using array crap
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
	
	//Deletes entire Registered Users table, IRREVERSIBLE
	//can be used in conjunction with timer to clear database but not necessary for this class
	//Next revision, add opt of 1 or 2? 1 for delete which starts off at last digit once cleared? 2 for trunc, etc....
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