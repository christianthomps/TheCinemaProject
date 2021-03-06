/**
 * PATCH NOTES:
 * September 20 2018: Skeleton code created
 * October 3 2018: Created working code - untested
 * October 13 2018: Tested code, patched bugged code
 * October 19 2018: Added getters/setters code
 * November 30 2018: Added JavaDoc comments
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Addresses {

	private int ADDRESSID;
	private int RESIDENTID;
	private String STREET;
	private String CITY;
	private String STATE;
	private String COUNTRY;
	private int POSTCODE;

	//container for stuff related to queries
	static Statement stmt = null;
	static ResultSet rs = null;
	static Connection con = null;
	static PreparedStatement pstmt = null;
	
	//specific for maddies comp
	//private static final String TEST = "jdbc:mysql://127.0.0.1:3306/e-booking?user=root&password=June201998";
	
	
	
	private static final String TEST = "jdbc:mysql://127.0.01:3306/e-booking?user=root&password=password";
		
	/**
	 * Default constructor - primarily used for retrieval of data using getters/setters
	 */
	public Addresses(){
		this.RESIDENTID = 0;
		this.STREET = null;
		this.CITY = null;
		this.STATE = null;
		this.COUNTRY = null;
		this.POSTCODE = 0;
	}

	/**
	 * Overloaded constructor - Used to create new addresses using parameters
	 * @param residentID Owner ID from User table/user.java
	 * @param street Street
	 * @param city City
	 * @param state State
	 * @param country Country
	 * @param postCode Postal Code
	 */

	public Addresses(int residentID, String street, String city, String state, String country, int postCode) {
		this.RESIDENTID = residentID;
		this.STREET = street;
		this.CITY = city;
		this.STATE = state;
		this.COUNTRY = country;
		this.POSTCODE = postCode;
	}
	/**
	 * Retrieves all relevant data related to address into its default variables from database
	 * @param residentID ID of owner from User table/User.java
	 */
	public void retrieveAddressInfo(int residentID) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(TEST);
			String query = "SELECT adressID, street, city, state, country, postcode FROM Addresses WHERE residentID = ? ";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, residentID);
			rs = pstmt.executeQuery();

			while(rs.next()) {
				this.RESIDENTID = residentID;
				this.ADDRESSID = rs.getInt("adressID");
				this.STREET = rs.getString("street");
				this.CITY = rs.getString("city");
				this.STATE = rs.getString("state");
				this.COUNTRY = rs.getString("country");
				this.POSTCODE = rs.getInt("postcode");
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
	}


	/**
	 * Adds address to Addresses database
	 */
	public void addAddressToDB() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(TEST);//change later to different URL if needed
			String query = "INSERT into Addresses(residentID, street, city, state, country, postcode) values(?, ?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(query);

			pstmt.setInt(1,  RESIDENTID);
			pstmt.setString(2,  STREET);
			pstmt.setString(3,  CITY);
			pstmt.setString(4,  STATE);
			pstmt.setString(5, COUNTRY);
			pstmt.setInt(6, POSTCODE);
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
	}
	
	/**
	 * Deletes address from Addresses database
	 * EX: address.deleteAddressFromDB();
	 */
	public void deleteAddressFromDB() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(TEST);
			String query = "DELETE FROM Addresses WHERE residentID = ?";
			pstmt = con.prepareStatement(query);

			pstmt.setInt(1, RESIDENTID);
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
	}

	/**
	 * Resets Accounts variables
	 */
	private void reset() {
		this.RESIDENTID = 0;
		this.STREET = null;
		this.CITY = null;
		this.STATE = null;
		this.COUNTRY = null;
		this.POSTCODE = 0;
	}
	
	/**
	 * Completely clears Addresses database of all data (UNRECOVERABLE once used)
	 */
	public void deleteAddressesTable() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(TEST);
			String query = "SET FOREIGN_KEY_CHECKS=0";//Truncate resets AUTO_INCREMENT to 1 unlike DELETE which does not reset AUTO_INCREMENT and will resume at last digit.
			pstmt = con.prepareStatement(query);
			pstmt.executeUpdate();
			
			query = "TRUNCATE TABLE Addresses";
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
	
	public int getAddressID() {
		return ADDRESSID;
	}
	
	public String getAddressStreet() {
		return STREET;
	}
	
	public String getAddressCity() {
		return CITY;
	}
	
	public String getAddressState() {
		return STATE;
	}
	
	public String getAddressCountry() {
		return COUNTRY;
	}
	
	public int getAddressPostCode() {
		return POSTCODE;
	}
	
	public void setResidentID(int residentID) {
		this.RESIDENTID = residentID;
	}
	
	public void setAddressStreet(String street) {
		this.STREET = street;
	}
	
	public void setAddressCity(String city) {
		this.CITY = city;
	}
	
	public void setAddressState(String state) {
		this.STATE = state;
	}
	
	public void setAddressCountry(String country) {
		this.COUNTRY = country;
	}
	
	public void setAddressPostCode(int postcode) {
		this.POSTCODE = postcode;
	}
	
	@Override
	public String toString(){
		return "Current Users data:\n"
				+ "ADDRESS ID: \t" + ADDRESSID + "\n"
				+ "RESIDENT ID: \t" + RESIDENTID + "\n"
				+ "STREET: \t" + STREET + "\n"
				+ "CITY: \t" + CITY + "\n"
				+ "STATE: \t" + STATE + "\n"
				+ "COUNTRY: \t" + COUNTRY + "\n"
				+ "POSTCODE: \t" + POSTCODE + "\n";
	}
}
