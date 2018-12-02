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

public class Movie {
	   
	//specific for maddies comp
	//private static final String TEST = "jdbc:mysql://127.0.0.1:3306/e-booking?user=root&password=June201998";
		
	private static final String TEST = "jdbc:mysql://127.0.01:3306/e-booking?user=root&password=password";
		
	
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
	private int STATUS; //0 means coming soon 1 means now showing 
	
	private String CATEGORY;
	private String RATING;
	
	static Statement stmt = null;
	static ResultSet rs = null;
	static Connection con = null;
	static PreparedStatement pstmt = null;
	
	/**
	 * Default constructor - primarily used for retrieval of data using getters/setters
	 */
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
		this.STATUS = 0;
	}//movie
	
	/**
	 * Overloaded constructor - Used to create new addresses using parameters
	 * @param t movie title
	 * @param cast movie cast
	 * @param p producer
	 * @param dur duration of movie
	 * @param trailerP trailer pic link
	 * @param trailer movie trailer link
	 * @param d movie review
	 * @param filmRating US Rating code for movie
	 * @param stat status of movie
	 */
	public Movie(String t, String cast, String cat, String p, int dur, String trailerP, String trailer, double d, String filmRating, int stat) {
	
		this.TITLE = t;
		this.RATING= filmRating;
		this.CATEGORY = cat;		
		this.PRODUCER = p;
		this.REVIEW = d;
		this.TRAILERLINK = trailer;
		this.TRAILERPICLINK = trailerP;
		this.DURATION = dur;
		this.CAST = cast; 
		this.STATUS = stat;
	}//movie
	
	/**
	 * Retrieves all relevant data related to movie into its default variables from database
	 * @param movie ID from movie table
	 */
	public void retrieveMovieData(int id) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(TEST);
			String query = "SELECT title, cast, genre, producer, duration, trailerPicLink, trailerVidLink, review, ratingId, status FROM Movies WHERE movieID = ? ";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1,  id);
			rs = pstmt.executeQuery();
			this.MID = id;
			
			while(rs.next()) {
				
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
			
			this.RATING = getRating(RATINGID);
			this.CATEGORY = getCategory(CATEGORYID);
			
			
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
	}//retrieveMovieData
	
	
	
	/**
	 * Retrieves all relevant data related to movie into its default variables from database
	 * @param t movie title in database
	 */
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
				
				this.RATING = getRating(RATINGID);
				this.CATEGORY = getCategory(CATEGORYID);
				
				
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
		
		//constructor used for internal use only
		private Movie(int movieID, String t, String c, int gID, String p, int dur, String trailerP, String trailer, double r, int ratID, int stat) {
			
			this.MID = movieID;
			this.TITLE = t;
			this.CAST = c;
			this.CATEGORYID = gID;
			this.PRODUCER = p;
			this.DURATION = dur;
			this.TRAILERPICLINK = trailerP;
			this.TRAILERLINK = trailer;
			this.REVIEW = r;
			this.RATINGID = ratID;
			this.STATUS = stat;
			
			this.CATEGORY = getCategory(CATEGORYID);
			this.RATING = getRating(RATINGID);
			
			
			
		}//constructor for retrieving based on genre purposes
		
		
		public String getCategory(int r) {
			
			if(r == 1) {
				
				return "Romance";
			}
			else if(r==2) {
				
				return "Action";
			}
			else if(r==3) {
				
				return "Comedy";
			}
			else if(r==4) {
				
				return "Horror";
			}
			else if(r==5) {
				
				return "Family";
			}
			else if(r==6) {
				
				return "Drama";
			}
			else if(r==7) {
				
				return "Musical";
			}
			else if(r==8) {
				
				return "Sci-Fi";
			}
			return "Romance";//default
			
		}
		
		
		public String getRating(int r) {
			
			if(r == 1) {
				
				return "G";
			}
			else if(r==2) {
				
				return "PG";
			}
			else if(r==3) {
				
				return "PG-13";
			}
			else if(r==4) {
				
				return "NC-17";
			}
			else if(r==5) {
				
				return "R";
			}
			return "G";//default
		}//getRating
		
		public int getRatingID(String rating) {
			
			if(rating.equals("G")) {
				return 1;
				
			}
			else if(rating.equals("PG")) {
				return 2;
				
			}
			else if(rating.equals("PG-13")) {
				return 3;
				
			}
			else if(rating.equals("NC-17")) {
				return 4;
				
			}
			else if(rating.equals("R")) {
				return 5;
				
			}
			return 1;
			
			
		}
		
		public int getGenreID(String g) {
			
			if(g.equals("Romance")) {
				return 1;
				
			}
			else if(g.equals("Action")) {
				return 2;
				
			}
			else if(g.equals("Comedy")) {
				return 3;
				
			}
			else if(g.equals("Horror")) {
				return 4;
				
			}
			else if(g.equals("Family")) {
				return 5;
				
			}
			else if(g.equals("Drama")) {
				return 6;
				
			}
			else if(g.equals("Musical")) {
				return 7;
				
			}
			else if(g.equals("Sci-Fi")) {
				return 8;
				
			}
			
			return 1;
			
			
		}
		
		/**
		 * Returns an Array List of movies that have the specified status
		 * @param status of the movie
		 */
		public ArrayList<Movie> retrieveMoviesBasedOnStatus(int status){
			
			ArrayList<Movie> movies = new ArrayList<Movie>();
			this.STATUS = status;

			try {
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection(TEST);

				String query = "SELECT movieID, title, cast, genre, producer, duration, trailerPicLink, trailerVidLink, review, ratingID FROM Movies WHERE status = ? ";
				pstmt = con.prepareStatement(query);
				pstmt.setInt(1,  status);
				rs = pstmt.executeQuery();
		
				while(rs.next()) {
					Movie item = new Movie(rs.getInt("movieID"), rs.getString("title"), rs.getString("cast"), rs.getInt("genre"),
							rs.getString("producer"), rs.getInt("duration"), rs.getString("trailerPicLink"), rs.getString("trailerVidLink"),
							rs.getDouble("review"), rs.getInt("ratingID"), status);				
					
					movies.add(item);			
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
					
			return movies;		
		}
		
		/**
		 * Returns an Array List of movies that have the specified genre
		 * @param genre of the movie
		 */
		public ArrayList<Movie> retrieveMoviesWithGenre(String g) {//returns an array of all movies with genre g
		
			ArrayList<Movie> movies = new ArrayList<Movie>();
			
			this.CATEGORYID = getGenreID(g);
			
			try {
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection(TEST);

				String query = "SELECT movieID, title, cast, producer, duration, trailerPicLink, trailerVidLink, review, ratingID, status FROM Movies WHERE genre = ? ";
				pstmt = con.prepareStatement(query);
				pstmt.setInt(1,  CATEGORYID);
				rs = pstmt.executeQuery();
		
				while(rs.next()) {
					Movie item = new Movie(rs.getInt("movieID"), rs.getString("title"), rs.getString("cast"), this.CATEGORYID,
							rs.getString("producer"), rs.getInt("duration"), rs.getString("trailerPicLink"), rs.getString("trailerVidLink"),
							rs.getDouble("review"), rs.getInt("ratingID"), rs.getInt("status"));
					
					
					movies.add(item);			
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
			
			return movies;
			
		}
		
		/**
		 * Returns an Array List of movies that have the specified title
		 * @param title of the movie
		 */
		public ArrayList<Movie> retrieveMoviesWithTitle(String t) {//returns an array of all movies with genre g
			
			ArrayList<Movie> movies = new ArrayList<Movie>();
			
			this.TITLE = t;
			try {
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection(TEST);

				String query = "SELECT movieID, cast, genre, producer, duration, trailerPicLink, trailerVidLink, review, ratingID, status FROM Movies WHERE title = ? ";
				pstmt = con.prepareStatement(query);
				pstmt.setString(1,  t);
				rs = pstmt.executeQuery();
		
				while(rs.next()) {
					Movie item = new Movie(rs.getInt("movieID"), t, rs.getString("cast"), rs.getInt("genre"),
							rs.getString("producer"), rs.getInt("duration"), rs.getString("trailerPicLink"), rs.getString("trailerVidLink"),
							rs.getDouble("review"), rs.getInt("ratingID"), rs.getInt("status"));
					
					movies.add(item);			
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
			
			return movies;
			
		}
		
		
		
		
		/**
		 * Adds movie to movies database
		 */
		public void addMovieToDB() {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection(TEST);//change later to different URL if needed
				
				this.CATEGORYID = getGenreID(CATEGORY);
				this.RATINGID = getRatingID(RATING);
				
				String query = "INSERT into Movies(title, cast, genre, producer, duration, trailerPicLink, trailerVidLink, review, ratingID, status) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
				pstmt.setInt(10, STATUS);
				
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
		
	
		/**
		 * Deletes movie from Movies database
		 * EX: movie.deleteMovieFromDB();
		 */
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
		
		/**
		 * Resets movie variables
		 */
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
			this.STATUS = 0;
			
		}
		
		/**
		 * Completely clears Movie database of all data (UNRECOVERABLE once used)
		 */
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
		
		/**
		 * Updates movie info in Movies database
		 */
		public void updateMovieInfoInDB() {
			
			try {
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection(TEST);//change later to different URL if needed
				this.CATEGORYID = getGenreID(CATEGORY);
				this.RATINGID = getRatingID(RATING);
			
							
				String query = "UPDATE Movies SET title = ?, cast = ?, genre = ?, producer = ?, duration = ?, trailerPicLink = ?, trailerVidLink = ?, review = ?, ratingID = ?, status = ? WHERE movieID = "+this.MID;
			
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
				pstmt.setInt(10,  STATUS);
				
		
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
					+ "STATUS\t"+this.STATUS+"\n"
					;
		}
		
	
	
}