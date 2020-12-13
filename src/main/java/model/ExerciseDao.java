package model;

import java.sql.*;
import java.util.Vector;

/**
 * This class can be used to initialize the database connection
 */
public class ExerciseDao {

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:~/TrainingDatabase";

    // Database credentials
    static final String USER = "root";
    static final String PASS = "root";


    public ExerciseDao(){
        Connection conn = null;
        Statement stmt = null;

        try {
            // STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //STEP 2: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            //STEP 3: Execute a query
            System.out.println("Creating tables in given database...");
            stmt = conn.createStatement();
            String sql =  "CREATE TABLE EXERCISES " +
                    "(exerciseID identity not null primary key, " +
                    " category integer, " +
                    " comment varchar(80), " +
                    " date varchar(80), " +
                    " distance double," +
                    " duration double)";
            stmt.executeUpdate(sql);
            System.out.println("Created table in given database...");

            sql =  "CREATE TABLE CATEGORIES " +
                    "(categoryID integer, " +
                    " name varchar(30), " +
                    " primary key (categoryID))";
            stmt.executeUpdate(sql);
            System.out.println("Created table in given database...");

            sql =  "alter table exercises " +
                    "add foreign key (category) " +
                    " references categories(categoryID)";
            stmt.executeUpdate(sql);
            System.out.println("Created table in given database...");


            sql = "INSERT INTO categories " + "VALUES  (1, 'Running')";
            stmt.executeUpdate(sql);
            sql = "INSERT INTO categories " + "VALUES  (2, 'Cycling')";
            stmt.executeUpdate(sql);
            sql = "INSERT INTO categories " + "VALUES  (3, 'Swimming')";
            stmt.executeUpdate(sql);
            sql = "INSERT INTO categories " + "VALUES  (4, 'Walking')";
            stmt.executeUpdate(sql);
            sql = "INSERT INTO categories " + "VALUES  (5, 'Nordic walking')";
            stmt.executeUpdate(sql);
            sql = "INSERT INTO categories " + "VALUES  (6, 'Winter sports')";
            stmt.executeUpdate(sql);
            sql = "INSERT INTO categories " + "VALUES  (7, 'Other')";
            stmt.executeUpdate(sql);

            // STEP 4: Clean-up environment
            conn.close();
        } catch(SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch(Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try{
                if(stmt!=null) stmt.close();
            } catch(SQLException se2) {
            } // nothing we can do
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se){
                se.printStackTrace();
            } //end finally try
        } //end try
        System.out.println("Goodbye!");
    }

    public void registerExercise(Exercise exercise) throws ClassNotFoundException{

        Connection conn = null;
        PreparedStatement stmt = null;

        String INSERT_EXERCISE_SQL = "INSERT INTO exercises " +
                "(category, comment, date, distance, duration) VALUES " +
                " ( ?, ?, ?, ?, ?);";

        int result = 0;

        try {
            // STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // STEP 2: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            // STEP 3: Execute a query
            System.out.println("Connected database successfully...");
            stmt = conn.prepareStatement(INSERT_EXERCISE_SQL);

            stmt.setInt(1, exercise.getExerciseNameInt());
            stmt.setString(2, exercise.getComment());
            stmt.setString(3, exercise.getExerciseDate());
            stmt.setDouble(4, exercise.getDistance());
            stmt.setDouble(5, exercise.getDuration());

            System.out.println(stmt);

            result = stmt.executeUpdate();
            // STEP 4: Clean-up environment
            stmt.close();
            conn.close();
        } catch(Exception se) {
            // Handle errors for JDBC
            se.printStackTrace();
        }// Handle errors for Class.forName
        finally {
            // finally block used to close resources
            try {
                if(stmt!=null) stmt.close();
            } catch(SQLException ignored) {
            } // nothing we can do
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
            } // end finally try
        } // end try
        System.out.println("Goodbye!");

    }

    public Vector<Exercise> readHistory(){

        Vector<Exercise> out = new Vector<>();
        Connection conn = null;
        Statement stmt = null;
        try {
            // STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // STEP 2: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            // STEP 3: Execute a query
            System.out.println("Connected database successfully...");
            stmt = conn.createStatement();
            String sql = "select name, comment, date, distance, duration from exercises, categories\n" +
                    "where categoryID = category";
            ResultSet rs = stmt.executeQuery(sql);

            // STEP 4: Extract data from result set
            while(rs.next()) {
                // Retrieve by column name
                String name = rs.getString("name");
                String comment = rs.getString("comment");
                String date = rs.getString("date");
                Double distance  = rs.getDouble("distance");
                Double duration  = rs.getDouble("duration");

                out.add(new Exercise(name, comment, date, distance, duration));

            }
            // STEP 5: Clean-up environment
            rs.close();
        } catch(SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch(Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            // finally block used to close resources
            try {
                if(stmt!=null) stmt.close();
            } catch(SQLException se2) {
            } // nothing we can do
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
            } // end finally try
        } // end try
        System.out.println("Goodbye!");

        return out;
    }


}