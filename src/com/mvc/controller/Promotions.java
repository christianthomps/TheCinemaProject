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

	private static final String TEST = "jdbc:mysql://127.0.0.1:3306/e-booking?user=root&password=June201998";
	
	//container for stuff related to queries
	static Statement stmt = null;
	static ResultSet rs = null;
	static Connection con = null;
	static PreparedStatement pstmt = null;

	public Promotions() {
		this.PROMOID = 0;//should be invalid
		this.PROMOCODE = null;
		this.DISCOUNT = 0.0;
		this.EXPIREDATE = null;
	}
	
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
	
	public void reset() {
		this.PROMOID = 0;
		this.PROMOCODE = null;
		this.DISCOUNT = 0.0;
		this.EXPIREDATE = null;
	}

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
