/**
 * PATCH NOTES:
 * September 20 2018: Skeleton code created
 * October 6 2018: Created working code - untested
 * October 16 2018: Tested code, patched bugged code
 * November 29 2018: Added JavaDoc comments
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Promotions {

	private int PROMOID;
	private String PROMOCODE;
	private double DISCOUNT;
	private String EXPIREDATE;//should this be date type???
	//specific for maddies comp
	//private static final String TEST = "jdbc:mysql://127.0.0.1:3306/e-booking?user=root&password=June201998";
	private static final String TEST = "jdbc:mysql://127.0.01:3306/e-booking?user=root&password=password";
		
	//container for stuff related to queries
	static Statement stmt = null;
	static ResultSet rs = null;
	static Connection con = null;
	static PreparedStatement pstmt = null;

	/**
	 * Default constructor - primarily used for retrieval of data using getters/setters
	 */
	public Promotions() {
		this.PROMOID = 0;//should be invalid
		this.PROMOCODE = null;
		this.DISCOUNT = 0.0;
		this.EXPIREDATE = null;
	}
	
	/**
	 * Overloaded constructor - Used to create new promotions using parameters
	 * @param code Name of promotion
	 * @param discount Amount of discount
	 * @param expiration Expiration date of promotion
	 */
	public Promotions(String code, double discount, String expiration) {
		this.PROMOCODE = code;
		this.DISCOUNT = discount;
		this.EXPIREDATE = expiration;
	}
	
	public String getCodeName() {
		return PROMOCODE;
	}
	
	public String getExpiration() {
		return EXPIREDATE;
	}
	
	public double getDiscount() {
		return DISCOUNT;
	}
	
	public int getPromoID() {
		return PROMOID;
	}
	
	public void setCodeName(String code) {
		this.PROMOCODE = code;
	}
	
	public void setDiscount(double discount) {
		this.DISCOUNT = discount;
	}
	
	public void setExpiration(String expiration) {
		this.EXPIREDATE = expiration;
	}
	
	/**
	 * Retrieves all relevant data related to promotion into its default variables from database
	 * @param code Name of promotion to retrieve data of
	 */
	public void retrievePromoInfo(String code) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(TEST);
			String query = "SELECT promoID, discountPercentage, expireDate FROM Promotions WHERE promoCode = ? ";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1,  code);
			rs = pstmt.executeQuery();

			while(rs.next()) {
				this.PROMOID = rs.getInt("promoID");
				this.PROMOCODE = code;
				this.DISCOUNT = rs.getDouble("discountPercentage");
				this.EXPIREDATE = rs.getString("expireDate");
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
	 * Adds promotion to Promotions database
	 */

	public void addPromotionToDB() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(TEST);//change later to different URL if needed
			String query = "INSERT into Promotions(promoCode, discountPercentage, expireDate) values(?, ?, ?)";
			pstmt = con.prepareStatement(query);

			pstmt.setString(1,  PROMOCODE);
			pstmt.setDouble(2,  DISCOUNT);
			pstmt.setString(3,  EXPIREDATE);

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
	 * Deletes promotion from Promotions database
	 * EX: promo.deletePromotionFromDB();
	 */
	public void deletePromotionFromDB() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(TEST);
			String query = "DELETE FROM Promotions WHERE promoCode = ?";
			pstmt = con.prepareStatement(query);

			pstmt.setString(1, PROMOCODE);
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
	 * Resets Promotion attributes
	 */
	public void reset() {
		this.PROMOID = 0;
		this.PROMOCODE = null;
		this.DISCOUNT = 0.0;
		this.EXPIREDATE = null;
	}

	/**
	 * Completely clears Promotions database of all data (UNRECOVERABLE once used)
	 */
	public void deletePromotionsTable() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(TEST);
			String query = "SET FOREIGN_KEY_CHECKS=0";//Truncate resets AUTO_INCREMENT to 1 unlike DELETE which does not reset AUTO_INCREMENT and will resume at last digit.
			pstmt = con.prepareStatement(query);
			pstmt.executeUpdate();
			
			query = "TRUNCATE TABLE Promotions";
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
	
	@Override
	public String toString(){
		return "Current Promotion data:\n"
				+ "PROMO ID: \t" + PROMOID + "\n"
				+ "PROMO CODE: \t" + PROMOCODE + "\n"
				+ "DISCOUNT PERCENTAGE: \t" + DISCOUNT + "\n"
				+ "EXPIRATION DATE: \t" + EXPIREDATE + "\n";
	}

}
