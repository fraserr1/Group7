/** Reference
 * https://www.tutorialspoint.com/sqlite/sqlite_java.htm*/

import javafx.util.Pair;

import java.sql.*;

public class DBManager {

    /** db */
    public static void insertHighScore(String name, int score){
        insertScore(name,score,delete());
    }

    private static void insertScore(String name, int score, int id){
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "INSERT INTO SCORES (ID,NAME,SCORE)" +
                    "VALUES ("+id+", '"+name+"', "+score+");";
            stmt.executeUpdate(sql);

            stmt.close();
            c.commit();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Records created successfully");
    }

    private static int delete(){
        Connection c = null;
        Statement stmt = null;
        int id = 0;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();

            ResultSet rs = stmt.executeQuery( "SELECT * FROM SCORES ORDER BY SCORE ASC LIMIT 1;" );

            while ( rs.next() ) {
                id = rs.getInt("id");

                System.out.println( "ID = " + id );
            }
            rs.close();

            String sql = "DELETE from SCORES where ID=" + id + ";";
            stmt.executeUpdate(sql);
            c.commit();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation done successfully");
        return id;
    }

    private static void createDB(){
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "CREATE TABLE SCORES " +
                    "(ID INT PRIMARY KEY     NOT NULL," +
                    " NAME           TEXT    NOT NULL, " +
                    " SCORE          INT     NOT NULL)";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Table created successfully");
    }

    public static Pair<String[],int[]> getScores() {

        Connection c = null;
        Statement stmt = null;
        String[] names = new String[5];
        int[] scores = new int[5];
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();


            ResultSet rs = stmt.executeQuery("SELECT * FROM SCORES ORDER BY SCORE DESC LIMIT 5;");
            int i = 0;
            while (rs.next()) {
                names[i] = rs.getString("name");
                scores[i] = rs.getInt("score");
                i++;
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Operation done successfully");
        return new Pair<>(names,scores);
    }

    private static void showDB() {

        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();


            ResultSet rs = stmt.executeQuery("SELECT * FROM SCORES ORDER BY SCORE DESC LIMIT 5;");

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int age = rs.getInt("score");

                System.out.println("ID = " + id);
                System.out.println("NAME = " + name);
                System.out.println("SCORE = " + age);
                System.out.println();
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Operation done successfully");

    }
    public static void main(String[] args){
        showDB();
    }
}
