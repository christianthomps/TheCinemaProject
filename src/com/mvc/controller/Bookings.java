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
		private int TOTALPRICE;//shouldn't this be double??? Especially if promo is applied?
		private int PROMO;
		//container for stuff related to queries
		static Statement stmt = null;
		static ResultSet rs = null;
		static Connection con = null;
		static PreparedStatement pstmt = null;

		public Bookings() {
			this.BOOKINGID = 0;
			this.USERID = 0;
			this.SHOWINGID = 0;
			this.PAYMENTID = 0;
			this.NUMOFTICKETS = 0;
			this.TOTALPRICE = 0;
			this.PROMO = 0;
		}

		public Bookings(int userID, int showingID, int paymentID, int numofTickets, int total, int promo) {
			this.USERID = userID;
			this.SHOWINGID = showingID;
			this.PAYMENTID = paymentID;
			this.NUMOFTICKETS = numofTickets;
			this.TOTALPRICE = total;
			this.PROMO = promo;		
		}

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
		//for internal use only! The only difference between the overloaded public constructor is that this has bookingID parameter
		//only meant for retrieval purpose.
		private Bookings(int bookingID, int userID, int showingID, int paymentID, int numofTickets, int total, int promo) {
			this.BOOKINGID = bookingID;
			this.USERID = userID;
			this.SHOWINGID = showingID;
			this.PAYMENTID = paymentID;
			this.NUMOFTICKETS = numofTickets;
			this.TOTALPRICE = total;
			this.PROMO = promo;		
		}

		public ArrayList<Bookings> retrieveBookings(int userID) {
			ArrayList<Bookings> collection = new ArrayList<Bookings>();
			try {
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection(TEST);
				String query = "SELECT bookingID, ofShow, withPayment, numOfTickets, totalPrice, plusPromo FROM Bookings WHERE userID = ? ";
				pstmt = con.prepareStatement(query);
				pstmt.setInt(1,  userID);
				rs = pstmt.executeQuery();

				int count = 0;
				//collection = new Bookings[100];//no clue if this will work.. fuck
				while(rs.next()) {
					Bookings item = new Bookings(rs.getInt("bookingID"), userID, rs.getInt("ofShow"), rs.getInt("withPayment"), rs.getInt("numOfTickets"), rs.getInt("totalPrice"), rs.getInt("plusPromo"));
					collection.add(item);
					count++;
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
	}
