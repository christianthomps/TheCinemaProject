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
import java.util.ArrayList;

public class Bookings {
	//specific for maddies comp
	//private static final String TEST = "jdbc:mysql://127.0.0.1:3306/e-booking?user=root&password=June201998";
		
	private static final String TEST = "jdbc:mysql://127.0.01:3306/e-booking?user=root&password=password";
	//data stuff
		private int BOOKINGID;
		private int USERID;
		private int SHOWINGID;
		private int PAYMENTID;
		private int NUMOFTICKETS;
		private int TOTALPRICE;
		private int PROMO;
		//container for stuff related to queries
		static Statement stmt = null;
		static ResultSet rs = null;
		static Connection con = null;
		static PreparedStatement pstmt = null;

		/**
		 * Default constructor - primarily used for retrieval of data using getters/setters
		 */
		public Bookings() {
			this.BOOKINGID = 0;
			this.USERID = 0;
			this.SHOWINGID = 0;
			this.PAYMENTID = 0;
			this.NUMOFTICKETS = 0;
			this.TOTALPRICE = 0;
			this.PROMO = 0;
		}
		
		/**
		 * Constructor used to set values with parameters
		 * @param userID
		 * @param showingID
		 * @param paymentID
		 * @param numofTickets
		 * @param total
		 * @param promo
		 */
		public Bookings(int userID, int showingID, int paymentID, int numofTickets, int total, int promo) {
			this.USERID = userID;
			this.SHOWINGID = showingID;
			this.PAYMENTID = paymentID;
			this.NUMOFTICKETS = numofTickets;
			this.TOTALPRICE = total;
			this.PROMO = promo;		
		}

		/**
		 * Updates the booking info in the database
		 */
		public void updateBookingInDB() {
			
			try {
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection(TEST);//change later to different URL if needed
							
				String query = "UPDATE Bookings SET fromUser = ?, ofShow = ?, withPayment = ?, numOfTickets = ?, totalPrice = ?, plusPromo = ? WHERE movieID = "+this.BOOKINGID;
			
				pstmt = con.prepareStatement(query);
				pstmt.setInt(1,  this.USERID);
				pstmt.setInt(2,  this.SHOWINGID);
				pstmt.setInt(3,  this.PAYMENTID);
				pstmt.setInt(4,  this.NUMOFTICKETS);
				pstmt.setInt(5, this.TOTALPRICE);
				pstmt.setInt(6, this.PROMO);
		
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
				
			
		}//updateBookingInDB
		
		/**
		 * Adds booking to bookings table in DB
		 */
		public void addBookingtoDB() {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection(TEST);//change later to different URL if needed
				String query = "INSERT into Bookings(fromUser, ofShow, withPayment, numOfTickets, totalPrice, plusPromo) values(?, ?, ?, ?, ?, ?)";
				pstmt = con.prepareStatement(query);

				pstmt.setInt(1,  USERID);
				pstmt.setInt(2,  SHOWINGID);
				pstmt.setInt(3,  PAYMENTID);
				pstmt.setInt(4,  NUMOFTICKETS);
				//pstmt.setString(5,  ADDRESS);
				pstmt.setInt(5,  TOTALPRICE);
				pstmt.setInt(6,  PROMO);
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
		 * Constructor used for internal use only
		 * @param bookingID
		 * @param userID
		 * @param showingID
		 * @param paymentID
		 * @param numofTickets Number of Tickets
		 * @param total Total Price
		 * @param promo Promo code
		 */
		private Bookings(int bookingID, int userID, int showingID, int paymentID, int numofTickets, int total, int promo) {
			this.BOOKINGID = bookingID;
			this.USERID = userID;
			this.SHOWINGID = showingID;
			this.PAYMENTID = paymentID;
			this.NUMOFTICKETS = numofTickets;
			this.TOTALPRICE = total;
			this.PROMO = promo;		
		}

		/**
		 * Returns a list of bookings associated with a certain user id
		 * @param userID
		 * @return Array List of bookings
		 */
		public ArrayList<Bookings> retrieveBookings(int userID) {
			ArrayList<Bookings> collection = new ArrayList<Bookings>();
			try {
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection(TEST);
				String query = "SELECT bookingID, ofShow, withPayment, numOfTickets, totalPrice, plusPromo FROM Bookings WHERE userID = ? ";
				pstmt = con.prepareStatement(query);
				pstmt.setInt(1,  userID);
				rs = pstmt.executeQuery();

				
				//collection = new Bookings[100];//no clue if this will work.. fuck
				while(rs.next()) {
					Bookings item = new Bookings(rs.getInt("bookingID"), userID, rs.getInt("ofShow"), rs.getInt("withPayment"), rs.getInt("numOfTickets"), rs.getInt("totalPrice"), rs.getInt("plusPromo"));
					collection.add(item);
					
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
			return collection;
		}

		//probably should help with array shit?
		public int getBookingID() {
			return BOOKINGID;
		}
		public int getUserID() {
			return USERID;
		}
		public int getShowingID() {
			return SHOWINGID;
		}
		public int getPaymentID() {
			return PAYMENTID;
		}
		public int getNumOfTickets() {
			return NUMOFTICKETS;
		}
		public int getTotalPrice() {
			return TOTALPRICE;
		}
		public int getPromo() {
			return PROMO;
		}
		public void setPromo(int p) {
			
			this.PROMO = p;
		}
	}
