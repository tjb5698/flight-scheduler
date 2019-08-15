import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;


public class Database {
   // private static String dbURL = "jdbc:derby:C:/Users/Trishita Bhattachary/Documents/FlightSchedulerDBTrishitatjb5698/FlightScheduler";
    private static String flight_table = "FLIGHT";
   // private static String username = "java";
   // private static String password = "java";
    
    private static Connection connection = null;
    private static Statement statement = null;
    
    private static ArrayList flightlist;
    private static ArrayList datelist;
    private static ArrayList flightNames;
    private static ArrayList availableDates;
    private static PreparedStatement seatstuff;
    private static PreparedStatement selectAllFlight;
    private static PreparedStatement selectAllDate;
    private static PreparedStatement flightstuff;
    
    public Database()
    {
        try{
            flightlist = new ArrayList();
            datelist = new ArrayList();
            connection = DriverManager.getConnection("jdbc:derby:FlightSchedulerDBTrishitatjb5698;user=java;password=java");
            
            selectAllDate = connection.prepareStatement("SELECT * FROM DATE");
            selectAllDate.execute();
            ResultSet allDates = selectAllDate.getResultSet();
            
            int n = 0;
            while(allDates.next()){
                datelist.add(allDates.getString("DATE"));
                n++;
            }
            
            selectAllFlight = connection.prepareStatement("SELECT * FROM FLIGHT");
            selectAllFlight.execute();
            ResultSet allFlights = selectAllFlight.getResultSet();
            
            int m = 0;
            while(allFlights.next()){
                flightlist.add(allFlights.getString("NAME"));
                m++;
            }
            
        }
        catch (SQLException e){
            e.printStackTrace();
            System.exit(1);
        }
    }
        public static void createConnection()
        {
        try
        {
            Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
            //Get a connection
            connection = DriverManager.getConnection("jdbc:derby:FlightSchedulerDBTrishitatjb5698;user=java;password=java"); 
        }
        catch (Exception except)
        {
            except.printStackTrace();
        }
        }
    
    
    public ArrayList getFlightList(){
        createConnection();
        flightNames = new ArrayList();
        try{
            selectAllFlight.execute();
            ResultSet rs1 = selectAllFlight.getResultSet();
            
            while(rs1.next()){
                flightNames.add(rs1.getString("NAME"));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            System.exit(1);
        }
        
        return flightNames;
    }
    
    public ArrayList getDatesList(){
        createConnection();
        availableDates = new ArrayList();
        try{
            selectAllDate.executeQuery();
            ResultSet rs2 = selectAllDate.getResultSet();
            while(rs2.next()){
                availableDates.add(rs2.getString("DATE"));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            System.exit(1);
        }
        return availableDates;
    }
 
    public static void shutdown(){
        try
        {
            if (statement != null)
            {
                statement.close();
            }
            if (connection != null)
            {
                DriverManager.getConnection("jdbc:derby:FlightSchedulerDBTrishitatjb5698" + ";shutdown=true");
                connection.close();
            }           
        }
        catch (SQLException sqlExcept)
        {
            sqlExcept.printStackTrace();
        }

    }
    public int getMaxSeats(String flightID){
        createConnection();
        try {
            seatstuff = connection.prepareStatement("SELECT * FROM FLIGHT");
            ResultSet rs = seatstuff.executeQuery();
            
            while(rs.next()){
                String selectedFlight = rs.getString("NAME");
                int maxSeats = rs.getInt("SEAT");
                
                if(selectedFlight.equals(flightID)){
                    //System.out.println(maxSeats);
                    return maxSeats;
                }
            }
        }
        catch (Exception except){
                except.printStackTrace();
        }
        return 0;
    }
    
    public void addFlight(String flightID, int maxSeats) {
        createConnection();
        String sql = "insert into FLIGHT(NAME, SEAT) VALUES(?,?)";
        
        try{
            flightstuff = connection.prepareStatement(sql);

            flightstuff.setString(1, flightID);
            flightstuff.setInt(2, maxSeats);

            flightstuff.executeUpdate();
            flightstuff = null;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
    }

    void deleteFlight(String flightID) {
        createConnection();
        String sql = "DELETE from FLIGHT where NAME = ?";
        
        try{
            flightstuff = connection.prepareStatement(sql);

            flightstuff.setString(1, flightID);

            flightstuff.executeUpdate();
            flightstuff = null;
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }    
}