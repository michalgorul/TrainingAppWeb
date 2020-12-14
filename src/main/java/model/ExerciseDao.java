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

    /**
     * This constructor will create new tables in database
     */
    public ExerciseDao(){
        Connection conn = null;
        Statement stmt = null;

        try {
            // STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //STEP 2: Open a connection
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            //STEP 3: Execute a query
            stmt = conn.createStatement();
            String sql =  "CREATE TABLE EXERCISES " +
                    "(exerciseID identity not null primary key, " +
                    " category integer, " +
                    " comment varchar(80), " +
                    " date varchar(80), " +
                    " distance double," +
                    " duration double)";
            stmt.executeUpdate(sql);

            sql =  "CREATE TABLE CATEGORIES " +
                    "(categoryID integer, " +
                    " name varchar(30), " +
                    " primary key (categoryID))";
            stmt.executeUpdate(sql);

            sql =  "CREATE TABLE BMI " +
                    "(categoryID identity not null primary key, " +
                    " height double," +
                    " weight double," +
                    " bmi double)";
            stmt.executeUpdate(sql);

            sql =  "alter table exercises " +
                    "add foreign key (category) " +
                    " references categories(categoryID)";
            stmt.executeUpdate(sql);

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
        } catch(Exception se) {
            //Handle errors for JDBC
            se.printStackTrace();
        }//Handle errors for Class.forName
        finally {
            //finally block used to close resources
            try{
                if(stmt!=null) stmt.close();
            } catch(SQLException ignored) {
            } // nothing we can do
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se){
                se.printStackTrace();
            } //end finally try
        } //end try
    }

    /**
     * This method will add an exercise to database
     * @param exercise exercise to add
     * @throws ClassNotFoundException thrown when an application tries to load in a class that doesnt exist
     */
    public void registerExercise(Exercise exercise) throws ClassNotFoundException{

        Connection conn = null;
        PreparedStatement stmt = null;

        String INSERT_EXERCISE_SQL = "INSERT INTO exercises " +
                "(category, comment, date, distance, duration) VALUES " +
                " ( ?, ?, ?, ?, ?);";

        try {
            // STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // STEP 2: Open a connection
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            // STEP 3: Execute a query
            stmt = conn.prepareStatement(INSERT_EXERCISE_SQL);

            stmt.setInt(1, exercise.getExerciseNameInt());
            stmt.setString(2, exercise.getComment());
            stmt.setString(3, exercise.getExerciseDate());
            stmt.setDouble(4, exercise.getDistance());
            stmt.setDouble(5, exercise.getDuration());

            stmt.executeUpdate();
            System.out.println(stmt);

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

    }

    /**
     *
     * @param height saved height
     * @param weight saved weight
     * @param bmi saved bmi
     */
    public void registerBmi(Double height, Double weight, Double bmi){

        Connection conn = null;
        PreparedStatement stmt = null;

        String INSERT_EXERCISE_SQL = "INSERT INTO bmi " +
                "(height, weight, bmi) VALUES " +
                " ( ?, ?, ?);";

        try {
            // STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // STEP 2: Open a connection
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            // STEP 3: Execute a query
            stmt = conn.prepareStatement(INSERT_EXERCISE_SQL);

            stmt.setDouble(1, height);
            stmt.setDouble(2, weight);
            stmt.setDouble(3, bmi);

            stmt.executeUpdate();
            System.out.println(stmt);

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

    }

    /**
     * This method will put whole history to vector of exercises
     * @return vector of exercises
     */
    public Vector<Exercise> readHistory(){

        Vector<Exercise> out = new Vector<>();
        Connection conn = null;
        Statement stmt = null;
        try {
            // STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // STEP 2: Open a connection
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            // STEP 3: Execute a query
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
                Double distance = rs.getDouble("distance");
                Double duration = rs.getDouble("duration");

                out.add(new Exercise(name, comment, date, distance, duration));

            }
            // STEP 5: Clean-up environment
            rs.close();
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

        return out;
    }

    /**
     * This method will put whole history to vector of exercises
     * @return vector of exercises
     */
    public Vector<Bmi> readHistoryBmi(){

        Vector<Bmi> out = new Vector<>();
        Connection conn = null;
        Statement stmt = null;
        try {
            // STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // STEP 2: Open a connection
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            // STEP 3: Execute a query
            stmt = conn.createStatement();
            String sql = "select height, weight, bmi from BMI";
            ResultSet rs = stmt.executeQuery(sql);

            // STEP 4: Extract data from result set
            while(rs.next()) {
                // Retrieve by column name
                Double height = rs.getDouble("height");
                Double weight = rs.getDouble("weight");
                Double bmi = rs.getDouble("bmi");

                out.add(new Bmi(height, weight, bmi));

            }
            // STEP 5: Clean-up environment
            rs.close();
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

        return out;
    }

    /**
     * This method will remove whole history of exercises
     */
    public void deleteHistory(){

        Connection conn = null;
        Statement stmt = null;
        try {
            // STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // STEP 2: Open a connection
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            // STEP 3: Execute a query
            stmt = conn.createStatement();
            String sql = "DELETE FROM exercises " + "WHERE exerciseID > 0";
            stmt.executeUpdate(sql);

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
    }

    /**
     * This method will remove whole history of exercises
     */
    public void deleteHistoryBmi(){

        Connection conn = null;
        Statement stmt = null;
        try {
            // STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // STEP 2: Open a connection
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            // STEP 3: Execute a query
            stmt = conn.createStatement();
            String sql = "DELETE FROM bmi " + "WHERE bmiID > 0";
            stmt.executeUpdate(sql);

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
    }



}