import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Theatre {

	private String NAME;
	private int TID;
	   
	private static final String TEST = "jdbc:mysql://127.0.0.1:3306/e-booking?user=root&password=June201998";
	
	static Statement stmt = null;
	static ResultSet rs = null;
	static Connection con = null;
	static PreparedStatement pstmt = null;
	
	public Theatre() {
		
		this.TID = 0;
		this.NAME = null;
	}
	public Theatre(String name) {
		
		this.NAME = name;
	}
	
	public void addThetreToDB() {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(TEST);//change later to different URL if needed
			
			String query = "INSERT into Halls(hallName) values(?)";
			pstmt = con.prepareStatement(query);

			pstmt.setString(1,  NAME);
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
	}//addTheatreToDB
	
	
		public void retrieveTheatreData(String t) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection(TEST);
				String query = "SELECT hallID, hallName FROM Hall WHERE hallName = ? ";
				pstmt = con.prepareStatement(query);
				pstmt.setString(1,  t);
				rs = pstmt.executeQuery();

				while(rs.next()) {
					this.TID = rs.getInt("hallID");
					this.NAME = rs.getString("hallName");
				}
				
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
		
	}//retrieveTheatreFromDB
	
	public void deleteTheatreFromDB() {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(TEST);
			String query = "DELETE FROM Hall WHERE hallName = ?";
			pstmt = con.prepareStatement(query);

			pstmt.setString(1, this.NAME);
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
		
		reset();
		
	}//deleteTheatreFromDB
	
	public void reset() {
		
		this.TID = 0;
		this.NAME = null;
	}//reset
	
	public int getTID() {
		
		return this.TID;
	}
	
	public String getName() {
		
		return this.NAME;
	}
	
	public void setTID(int id) {
		
		this.TID = id;
	}
	
	public void setName(String name) {
		
		this.NAME = name;
	}
	
	
	
}
