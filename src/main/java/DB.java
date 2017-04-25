import java.sql.*;
import java.util.ArrayList;

/**
 * Created by samagbeh on 4/25/17.
 */
public class DB {

    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";        //Configure the driver needed
    private static final String DB_CONNECTION_URL = "jdbc:mysql://localhost/cubes?useLegacyDatetimeCode=false&serverTimezone=America/Chicago";
    private static final String USER = "sam";   //TODO replace with your username
    private static final String PASSWORD = "agbeh";   //TODO replace with your password
    private static final String TABLE_NAME = "cubes";
    private static final String NAME_COL = "Cube_Solver";
    private static final String RECORD_COL = "Solve_Time";

    DB() {

        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Can't instantiate driver class; check you have drives and classpath configured correctly?");
            cnfe.printStackTrace();
            System.exit(-1);  //No driver? Need to fix before anything else will work. So quit the program
        }
    }

    void createTable() {

        try (Connection conn = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASSWORD);
             Statement statement = conn.createStatement()) {

            //You should have already created a database via terminal/command prompt

            //Create a table in the database, if it does not exist already
            //Can use String formatting to build this type of String from constants coded in your program
            //Don't do this with variables with data from the user!! That's what ParameterisedStatements are, and that's for queries, updates etc. , not creating tables.
            // You shouldn't make database schemas from user input anyway.
            String createTableSQLTemplate = "CREATE TABLE IF NOT EXISTS %s (%s VARCHAR (100), %s DOUBLE)";
            String createTableSQL = String.format(createTableSQLTemplate, TABLE_NAME, NAME_COL, RECORD_COL);

            statement.executeUpdate(createTableSQL);
            System.out.println("Created cubes table");

            statement.close();
            conn.close();

        } catch (SQLException se) {
            se.printStackTrace();
        }
    }


    void addRecord(CubesRecord cubesRecord)  {

        try (Connection conn = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASSWORD)) {

            String addCubesRecordSQL = "INSERT INTO " + TABLE_NAME + " VALUES ( ? , ? ) " ;
            PreparedStatement addCubesRecordPS = conn.prepareStatement(addCubesRecordSQL);
            addCubesRecordPS.setString(1, cubesRecord.name);
            addCubesRecordPS.setDouble(2, cubesRecord.recordTime);

            addCubesRecordPS.execute();

            System.out.println("Added CubesRecord object for " + cubesRecord);

            addCubesRecordPS.close();
            conn.close();

        } catch (SQLException se) {
            se.printStackTrace();
        }

    }

    ArrayList<CubesRecord> fetchAllRecords() {

        ArrayList<CubesRecord> allRecords = new ArrayList();

        try (Connection conn = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASSWORD);
             Statement statement = conn.createStatement()) {

            String selectAllSQL = "SELECT * FROM " + TABLE_NAME;
            ResultSet rsAll = statement.executeQuery(selectAllSQL);

            while (rsAll.next()) {
                String name = rsAll.getString(NAME_COL);
                double recordTime = rsAll.getDouble(RECORD_COL);
                CubesRecord cubesRecord = new CubesRecord(name, recordTime);
                allRecords.add(cubesRecord);
            }

            rsAll.close();
            statement.close();
            conn.close();

            return allRecords;    //If there's no data, this will be empty

        } catch (SQLException se) {
            se.printStackTrace();
            return null;  //since we have to return something.
        }
    }

    void delete(CubesRecord cubesRecord) {

        try (Connection conn = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASSWORD)) {

            String deleteSQLTemplate = "DELETE FROM %s WHERE %s = ? AND %s = ?";
            String deleteSQL = String.format(deleteSQLTemplate, TABLE_NAME, NAME_COL, RECORD_COL);
            System.out.println("The SQL for the prepared statement is " + deleteSQL);
            PreparedStatement deletePreparedStatement = conn.prepareStatement(deleteSQL);
            deletePreparedStatement.setString(1, cubesRecord.name);
            deletePreparedStatement.setDouble(2, cubesRecord.recordTime);
            //For debugging - displays the actual SQL created in the PreparedStatement after the data has been set
            System.out.println(deletePreparedStatement.toString());

            //Delete
            deletePreparedStatement.execute();

            //And close everything.
            deletePreparedStatement.close();;
            conn.close();

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }


    void updateRecord(CubesRecord cubesRecord) {

        try (Connection conn = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASSWORD)) {

            String sqlUpdate = "UPDATE cubes SET Solve_Time= ? WHERE Cube_Solver= ?";
            PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate);
            psUpdate.setString(2, cubesRecord.name);
            psUpdate.setDouble(1, cubesRecord.recordTime);

            psUpdate.executeUpdate();

            psUpdate.close();
            conn.close();

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }
}
