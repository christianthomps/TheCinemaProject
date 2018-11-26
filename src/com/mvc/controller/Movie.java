import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Movie {
	   
	private static final String TEST = "jdbc:mysql://127.0.0.1:3306/e-booking?user=root&password=June201998";
	
	private int MID;
	private String TITLE;
	private int RATINGID;
	private int CATEGORYID; 
	private String PRODUCER;
	private double REVIEW;
	private String TRAILERLINK;
	private String TRAILERPICLINK;
	private String CAST;
	private int DURATION;
	
	private String CATEGORY;
	private String RATING;
	
	static Statement stmt = null;
	static ResultSet rs = null;
	static Connection con = null;
	static PreparedStatement pstmt = null;
	
	public Movie(){
		
		this.MID = 0;
		this.TITLE = null;
		this.RATINGID = 0;
		this.CATEGORYID = 0;
		this.PRODUCER = null;
		this.REVIEW = 0.0;
		this.TRAILERLINK = null;
		this.TRAILERPICLINK = null;	
		this.DURATION = 0;
		this.CATEGORY = null;
		this.RATING = null;
		this.CAST = null;
		
	}//movie
	
	public Movie(String t, String filmRating, String cat, String p, double d, String trailer, String trailerP, int dur, String cast) {
	
		this.TITLE = t;
		this.RATING= filmRating;
		this.CATEGORY = cat;		
		this.PRODUCER = p;
		this.REVIEW = d;
		this.TRAILERLINK = trailer;
		this.TRAILERPICLINK = trailerP;
		this.DURATION = dur;
		this.CAST = cast; 
	}//movie
	
	
	
	//Retrieve movie's data from database using title
		public void retrieveMovieDataUsingTitle(String t) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection(TEST);
				String query = "SELECT movieID, title, cast, genre, producer, duration, trailerPicLink, trailerVidLink, review, ratingId, status FROM Movies WHERE title = ? ";
				pstmt = con.prepareStatement(query);
				pstmt.setString(1,  t);
				rs = pstmt.executeQuery();

				while(rs.next()) {
					this.MID = rs.getInt("movieID");
					this.TITLE = rs.getString("title");
					this.CAST = rs.getString("cast");
					this.CATEGORYID= rs.getInt("genre"); //how can we get enums from database
					this.PRODUCER = rs.getString("producer");
					this.DURATION = rs.getInt("duration");
					this.TRAILERPICLINK = rs.getString("trailerPicLink");
					this.TRAILERLINK = rs.getString("trailerVidLink");
					this.REVIEW = rs.getDouble("review");
					this.RATINGID = rs.getInt("ratingId");
					
				}//get from Movie table
				
				//now get the actual rating using the foreign rating id 
				query = "SELECT ratingCode FROM US_Ratings WHERE usRatingID = ? ";
				pstmt = con.prepareStatement(query);
				pstmt.setInt(1,  RATINGID);
				rs = pstmt.executeQuery();
				while(rs.next()) {
				
					this.RATING = rs.getString("ratingCode");
				}
				
				//now get the actual category/genre using the foreign key
				query = "SELECT genreName FROM Genres WHERE genreID = ? ";
				pstmt = con.prepareStatement(query);
				pstmt.setInt(1,  CATEGORYID);
				rs = pstmt.executeQuery();
				while(rs.next()) {
				
					this.CATEGORY = rs.getString("genreName");
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
		}//retrieveMovieDataUsingTitle
		
		public void retrieveMovieDataUsingCategory(String c) {
			
			try {
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection(TEST);//change later to different URL if needed
				
				this.CATEGORY = c;
				//first get the correct foreign key from category table using category string 
				String query = "SELECT genreID FROM Genres WHERE genreName = ? ";
				pstmt = con.prepareStatement(query);
				pstmt.setString(1,  c);
				rs = pstmt.executeQuery();
				while(rs.next()) {
				
					this.CATEGORYID = rs.getInt("genreID");
				}
				
				query = "SELECT movieID, title, cast, genre, producer, duration, trailerPicLink, trailerVidLink, review, ratingId, status FROM Movies WHERE genre = ? ";
				pstmt = con.prepareStatement(query);
				pstmt.setInt(1,  this.CATEGORYID);
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					this.MID = rs.getInt("movieID");
					this.TITLE = rs.getString("title");
					this.CAST = rs.getString("cast");
					this.CATEGORYID= rs.getInt("genre"); 
					this.PRODUCER = rs.getString("producer");
					this.DURATION = rs.getInt("duration");
					this.TRAILERPICLINK = rs.getString("trailerPicLink");
					this.TRAILERLINK = rs.getString("trailerVidLink");
					this.REVIEW = rs.getDouble("review");
					this.RATINGID = rs.getInt("ratingId");
					
				}//get from Movie table
				
				//now get the actual rating using the foreign rating id 
				query = "SELECT ratingCode FROM US_Ratings WHERE usRatingID = ? ";
				pstmt = con.prepareStatement(query);
				pstmt.setInt(1,  RATINGID);
				rs = pstmt.executeQuery();
				while(rs.next()) {
				
					this.RATING = rs.getString("ratingCode");
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
		
		
		public void addMovieToDB() {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection(TEST);//change later to different URL if needed
				
				//first get the correct foreign key from genre table using the genre string
				String query = "SELECT genreID FROM Genres WHERE genreName = ? ";
				pstmt = con.prepareStatement(query);
				pstmt.setString(1,  CATEGORY);
				rs = pstmt.executeQuery();
				while(rs.next()) {
				
					this.CATEGORYID = rs.getInt("genreID");
				}
				
				//also get correct foreign key for rating table using the rating string
				query = "SELECT usRatingID FROM US_Ratings WHERE ratingCode = ? ";
				pstmt = con.prepareStatement(query);
				pstmt.setString(1,  RATING);
				rs = pstmt.executeQuery();
				while(rs.next()) {
				
					this.RATINGID = rs.getInt("usRatingID");
				}
				
				query = "INSERT into Movies(title, cast, genre, producer, duration, trailerPicLink, trailerVidLink, review, ratingID, status) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				pstmt = con.prepareStatement(query);

				pstmt.setString(1,  TITLE);
				pstmt.setString(2,  CAST);
				pstmt.setInt(3,  CATEGORYID);
				pstmt.setString(4,  PRODUCER);
				pstmt.setInt(5,  DURATION);
				pstmt.setString(6,  TRAILERPICLINK);
				pstmt.setString(7, TRAILERLINK);
				pstmt.setDouble(8, REVIEW);
				pstmt.setInt(9, RATINGID);	
				pstmt.setInt(10, 1);
				
				pstmt.executeUpdate();

				//now get correct movie id where it was inserted
				query = "SELECT movieID FROM Movies WHERE title = ? ";
				pstmt = con.prepareStatement(query);
				pstmt.setString(1,  TITLE);
				rs = pstmt.executeQuery();
				while(rs.next()) {
				
					this.MID = rs.getInt("movieID");
				}
				
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
		}//addMovieToDB
		
		//Deletes movie from database
		public void deleteMovieFromDB() {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection(TEST);
				
				String query = "SET FOREIGN_KEY_CHECKS=0";
				pstmt = con.prepareStatement(query);
				pstmt.executeUpdate();
		
				query = "DELETE FROM Movies WHERE title = ?";
				pstmt = con.prepareStatement(query);

				pstmt.setString(1, TITLE);
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
			
			reset();

		}//deleteMovieFromDB
		
		public void reset() {
			this.MID = 0;
			this.TITLE = null;
			this.CAST = null;
			this.CATEGORYID = 0;
			this.PRODUCER = null;
			this.DURATION = 0;
			this.TRAILERPICLINK = null;
			this.TRAILERLINK = null;
			this.REVIEW = 0.0;
			this.RATINGID = 0;
			this.RATING = null;
			this.CATEGORY = null;
			
		}
		
		//Deletes entire Movies table, IRREVERSIBLE
		//can be used in conjunction with timer to clear database but not necessary for this class
		//Next revision, add opt of 1 or 2? 1 for delete which starts off at last digit once cleared? 2 for trunc, etc....
		public void deleteMoviesTable() {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection(TEST);
				String query = "SET FOREIGN_KEY_CHECKS=0";//Truncate resets AUTO_INCREMENT to 1 unlike DELETE which does not reset AUTO_INCREMENT and will resume at last digit.
				pstmt = con.prepareStatement(query);
				pstmt.executeUpdate();
				
				query = "TRUNCATE TABLE Movies";
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
		}//deleteMoviesTable
		
		public void updateMovieInfoInDB() {
			
			try {
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection(TEST);//change later to different URL if needed
				
				//first get accurate genreID based on genre
				String query = "SELECT genreID FROM Genres WHERE genreName = ? ";
				pstmt = con.prepareStatement(query);
				pstmt.setString(1,  CATEGORY);
				rs = pstmt.executeQuery();
				while(rs.next()) {
				
					this.CATEGORYID = rs.getInt("genreID");
				}
							
				//then get accurate rating id based on rating 
				
				query = "SELECT usRatingID FROM US_Ratings WHERE ratingCode = ? ";
				pstmt = con.prepareStatement(query);
				pstmt.setString(1,  RATING);
				rs = pstmt.executeQuery();
				while(rs.next()) {
				
					this.RATINGID = rs.getInt("usRatingID");
				}
				
							
				query = "UPDATE Movies SET title = ?, cast = ?, genre = ?, producer = ?, duration = ?, trailerPicLink = ?, trailerVidLink = ?, review = ?, ratingID = ? WHERE movieID = "+this.MID;
			
				pstmt = con.prepareStatement(query);
				pstmt.setString(1,  TITLE);
				pstmt.setString(2,  CAST);
				pstmt.setInt(3,  CATEGORYID);
				pstmt.setString(4,  PRODUCER);
				pstmt.setInt(5,  DURATION);
				pstmt.setString(6,  TRAILERPICLINK);
				pstmt.setString(7,  TRAILERLINK);
				pstmt.setDouble(8,  REVIEW);
				pstmt.setInt(9,  RATINGID);
		
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
				
			
		}//updateMovieInfoInDB
	

		
		
		public int getMID() {
			
			return this.MID;
		}
		
		public void setMID(int i) {
			
			this.MID = i;
		}
		
		public String getTitle() {
			
			return this.TITLE;
		}
		
		public void setTitle(String s) {
			
			this.TITLE = s;
		}
		
		public String getCast() {
			
			return this.CAST;
		}
		
		public void setCast(String s) {
			
			this.CAST = s;
		}
		
		public String getCategory() {
			
			return this.CATEGORY;
		}
		
		public void setCategory(String s) {
			
			this.CATEGORY = s;
		}
		
		public String getProducer() {
			
			return this.PRODUCER;
		}
		
		public void setProducer(String s) {
			
			this.PRODUCER = s;
		}
		
		public int getDuration() {
			
			return this.DURATION;
		}
		
		public void setDuration(int d) {
			
			this.DURATION = d;
		}
		
		public String getTrailerPicLink() {
			
			return this.TRAILERPICLINK;
		}
		
		public void setTrailerPicLink(String s) {
			
			this.TRAILERPICLINK = s;
		}
		
		public String getTrailerLink() {
			
			return this.TRAILERLINK;
		}
		
		public void setTrailerLink(String s) {
			
			this.TRAILERLINK = s;
		}
		
		public double getReview() {
			
			return this.REVIEW;
		}
		
		public void setReview(double d) {
			
			this.REVIEW = d;
		}
		
		public String getRating() {
			
			return this.RATING;
		}
		
		public void setRating(String s) {
			
			this.RATING = s;
		}
		
		@Override
		public String toString() {
			return "Current User data:\n"
					+ "MID: \t" + MID + "\n"
					+ "TITLE: \t" + TITLE + "\n"
					+ "PRODUCTION: \t"+ this.PRODUCER + "\n"
					+ "DURATION: \t" + this.DURATION + "\n"
					+ "REVIEW\t" + this.REVIEW+ "\n"
					+ "TRAILER LINK\t" + this.TRAILERLINK+ "\n"
					+ "TRAILER PIC LINK\t" + this.TRAILERPICLINK+ "\n"
					+ "RATING\t" + this.RATING + "\n"
					+ "GENRE\t" + this.CATEGORY+ "\n"
					+ "CAST\t" + this.CAST+ "\n"
					;
		}
		
	
	
}