/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dx.timesheet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * this class perform basic operation of database
 */
public class DatabaseHandler {
    
    Connection con;
    Statement stmt;
    ResultSet rs;
    private String name;
    private String user_name;
    private String imgUrl;
    private String user_id;
    private boolean loginStatus;
    private String timesheetId;
    private int countRecords;
    public DatabaseHandler() {
    
    }
    /**
    * this function is used for Load the database driver and get connection with database here the database is SQLite
    */
     public void connect() throws ClassNotFoundException, SQLException {
       
        // Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
       // con = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ="
       //         + "LoginDetails.mdb");
         
         
        String url = "jdbc:sqlite:E:/WorkSheet/LoginDetails.db";
//        String url = "jdbc:sqlite:LoginDetails.db";
        con = DriverManager.getConnection(url);              
        stmt = con.createStatement();
    }
    
     /**
    * This function is used for company Alias. this part is temporary deleted
    */
     public void saveCompanyAlias(String alias) throws SQLException {
        PreparedStatement pstmt = con.prepareStatement("insert into Alias(alias) values (?);");
        pstmt.setString(1, alias);
        pstmt.execute(); 
    }
     /**
    * This function is used to get company Alias. this part is temporary deleted
    */
     public String getAlias() throws SQLException {
        String alias = "";
        stmt.executeQuery("SELECT * FROM Alias");
        ResultSet rs = stmt.getResultSet();
        while (rs.next()) {
            alias = rs.getString("alias");
        }
        rs.close();
        return alias;
    }
    /**
    * This function is used for insert TimeSheetId
    */
   public void insertTimesheetId(String timesheetid) throws SQLException {
        String userid = getUserId();
        System.out.println(userid);
        System.out.println(timesheetid);
        PreparedStatement pstmt = con.prepareStatement("UPDATE LoggedInUser " + "  SET timeId = ? " + "WHERE userid = ? ");
        System.out.println("Inserting time id in db");
        pstmt.setString(1, timesheetid);
        pstmt.setString(2, userid);
        pstmt.execute();
    }
    /**
    * This function is used for set TimeSheetId
    */
 public void setTimeId(String id,String userid,String taskid) throws SQLException{
       stmt.executeQuery("SELECT * FROM User");
       ResultSet rs = stmt.getResultSet();
       int recordCount = 0;
        while (rs.next()) {
            recordCount += 1;
            String time_id = rs.getString("timeid");
        }
        if(recordCount==0){
            PreparedStatement pstmt = con.prepareStatement("insert into User(timeid,userid,taskid) values (?,?,?);");
            pstmt.setString(1, id);
            pstmt.setString(2, userid);
            pstmt.setString(3, taskid);
            pstmt.executeUpdate();
            System.out.println("Record count is>> "+recordCount);
            System.out.println("Inserted time id>> "+id);
        }
        else if(recordCount==1){
            PreparedStatement pstmt = con.prepareStatement("UPDATE User " + " SET timeId = ?,userid = ?,taskid = ? " );
            pstmt.setString(1, id);
            pstmt.setString(2, userid);
            pstmt.setString(3, taskid);
            pstmt.execute();
            System.out.println("Record count is>> "+recordCount);
            System.out.println("Inserted time id>> "+id);
        //    showInfoDialog("id:"+id+"userid:"+userid+"taskid:"+taskid);
        }else if(recordCount>1){
            deleteAllRows();
            System.out.println("Record count is>> "+recordCount);
            setTimeId(id,userid,taskid);
            
        }
   }
    /**
    * This function is used for show message info dialog
    */
   public void showInfoDialog(String Info) {
        InfoDialog inf = new InfoDialog(null, true);
        inf.setLocationRelativeTo(new TaskPanel());
        inf.setInfo(Info);
        inf.setVisible(true);
    }
    /**
    * This function is used for get Time-Id
    */
    public String getTimeId() throws SQLException {
        String timeid = "";
        stmt.executeQuery("SELECT * FROM User");
        ResultSet rs = stmt.getResultSet();
        while (rs.next()) {
            timeid = rs.getString("timeid");
        }
        rs.close();
        System.out.println("Retrieved time id>> "+timeid);
        return timeid;
    }
    /**
    * This function is used for get Task-Id
    */
    public String getTaskId() throws SQLException {
        String taskid = "";
        stmt.executeQuery("SELECT * FROM User");
        ResultSet rs = stmt.getResultSet();
        while (rs.next()) {
            taskid = rs.getString("taskid");
        }
        rs.close();
        System.out.println("Retrieved task id>> "+taskid);
        return taskid;
    }
    /**
    * This function is used for get User-Id
    */
      public String getUserId() throws SQLException {
        String userid = "";
        stmt.executeQuery("SELECT * FROM User");
        ResultSet rs = stmt.getResultSet();
        while (rs.next()) {
            userid = rs.getString("userid");
        }
        rs.close();
        System.out.println("Retrieved task id>> "+userid);
        return userid;
    }
    /**
    * This function is used for delete all data
    */
   public void deleteAllRows() throws SQLException{
           String sql = "DELETE FROM User";
        int delete = stmt.executeUpdate(sql);
        if (delete == 0) {
            System.out.println("All rows are completelly deleted!");
      }
      }
   
   
   /**
    * This function is used for delete all data
    */
    public void deleteRecord() throws SQLException{
        String sql = "DELETE FROM LoggedInUser";
        int delete = stmt.executeUpdate(sql);
        if (delete == 0) {
            System.out.println("All rows are completelly deleted!");
        }
    }
    
    /**
    * This function is used for Update user data
    */
    public void updateLoggedInUserTable(String userid, String name, String username, String imgurl) throws SQLException {
        stmt.executeQuery("SELECT * FROM LoggedInUser");
        ResultSet rs = stmt.getResultSet();
        int logCount = 0;
        while (rs.next()) {
            logCount += 1;
        }
        if (logCount == 0) {
            PreparedStatement pstmt = con.prepareStatement("insert into LoggedInUser (userid,name,username,ImageUrl,timeId) values (?, ?, ?, ?, ?);");
            pstmt.setString(1, userid);
            pstmt.setString(2, name);
            pstmt.setString(3, username);
            pstmt.setString(4, imgurl);
            pstmt.setString(5, "");
            pstmt.executeUpdate();
        }
    }
    /**
    * This function is used for check Loging Session
    */
    public void checkLoginSession() throws SQLException {
        stmt.executeQuery("SELECT * FROM LoggedInUser");
        ResultSet rs = stmt.getResultSet();
        int logCount = 0;
        while (rs.next()) {
            logCount += 1;
            String user_id = rs.getString("userid");
            PopUpLogin.name = rs.getString("name");
            PopUpLogin.user_name = rs.getString("username");
            PopUpLogin.imgUrl = rs.getString("ImageUrl");
        }
        if (logCount == 1) {
            PopUpLogin.loginStatus = true;
        } else {
            PopUpLogin.loginStatus = false;
        }
    }
    /**
    * This function is used for close the connection 
    */
    public void closeConnection(){
        try {
            if(!con.isClosed()){
            con.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(PopUpLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
    * This function is used for get TimeSheet ID in database 
    */
    public String getTimesheetId() throws SQLException {
        String timeid = null;
        stmt.executeQuery("SELECT * FROM LoggedInUser");
        ResultSet rs = stmt.getResultSet();
        while (rs.next()) {
            timeid = rs.getString("timeid");
        }
        rs.close();
        return timeid;
    }
    /**
    * This function is used for delete TimeSheet ID in database 
    */
    public void deleteTimeSheetId() throws SQLException {
        String userid = getUserId();
        PreparedStatement pstmt = con.prepareStatement("UPDATE LoggedInUser " + "  SET timeId = ? " + "WHERE userid = ? ");
        pstmt.setString(1, "");
        pstmt.setString(2, userid);
        pstmt.execute();
    }
    /**
    * This function is used for set Login Status in database 
    */
    public boolean setLoginStatus() throws SQLException{
        checkLoginSession();
        return loginStatus;
    }
    /**
    * This function is used for get UserName in database 
    */
    public String getUserName() throws SQLException {
        String username = null;
        stmt.executeQuery("SELECT * FROM LoggedInUser");
        ResultSet rs = stmt.getResultSet();
        while (rs.next()) {
            username = rs.getString("username");
        }
        return username;
    }
    /**
    * This function is used for get UserID in database 
    */
     public String getUserId1() throws SQLException {
        String userid = null;
        stmt.executeQuery("SELECT * FROM LoggedInUser");
        ResultSet rs = stmt.getResultSet();
        while (rs.next()) {
            userid = rs.getString("userid");
        }
        return userid;
    }
     /**
    * This function is used for get UserID in database 
    */
     public String getUserId2() throws SQLException{
        String userid = null;
        stmt.executeQuery("SELECT * FROM APP.TBLSESSION");
        ResultSet rs = stmt.getResultSet();
          while (rs.next()) {
             userid = rs.getString("userid");
        }
          rs.close();
          return userid;
    }
    public void updateLoggedInUserTable1(String userid, String name, String username, String imgurl) throws SQLException {
        stmt.executeQuery("SELECT * FROM APP.TBLSESSION");
        ResultSet rs = stmt.getResultSet();
        int logCount = 0;
        while (rs.next()) {
            logCount += 1;
        }
        if (logCount == 0) {
            PreparedStatement pstmt = con.prepareStatement("insert into APP.TBLSESSION (userid,name,username,ImageUrl) values (?, ?, ?, ?);");
            pstmt.setString(1, userid);
            pstmt.setString(2, name);
            pstmt.setString(3, username);
            pstmt.setString(4, imgurl);
            pstmt.executeUpdate();
        }
        rs.close();
    }
    /**
    * This function is used for check Login session in database 
    */
      public void checkLoginSession1() throws SQLException {
        stmt.executeQuery("SELECT * FROM APP.TBLSESSION");
        ResultSet rs = stmt.getResultSet();
        int logCount = 0;
        while (rs.next()) {
            logCount += 1;
            user_id = rs.getString("userid");
            name = rs.getString("name");
            user_name = rs.getString("username");
            imgUrl = rs.getString("ImageUrl");
        }
        if (logCount == 1) {
            loginStatus = true;
        }
        else{
            loginStatus = false;
        }
        rs.close();
    }
      /**
    * This function is used for delete table in database 
    */
      public void deleteTable() throws SQLException{
           String sql = "DELETE FROM APP.TBLSESSION";
        int delete = stmt.executeUpdate(sql);
        if (delete == 0) {
            System.out.println("All rows are completelly deleted!");
      }
      }
      
    /**
    * This function is used for close the database connection
    */
      public void closeDatabase() throws SQLException{
          con.close();
          stmt.close();
          rs.close();
          
      }
    /**
    * This function is used for insertLoginDetails in database 
    */
       public void insertLoginDetails(String userid) throws SQLException {
        stmt.executeQuery("SELECT * FROM TABLELOGIN");
        ResultSet rs = stmt.getResultSet();
        boolean userExists = false;
        while (rs.next()) {
            String user_id = rs.getString("userid");
            if (user_id.equals(userid)) {
                userExists = true;
            }
        }
        if (!userExists) {
            boolean login = true;
            //    String s1 = "INSERT INTO TABLELOGIN(username, login) VALUES('" + email + "','" + true + "')";
            PreparedStatement pstmt = con.prepareStatement("insert into TABLELOGIN (userid,username) values (?, ?);");
            pstmt.setString(1, userid);
            pstmt.setString(2, user_name);
            
            pstmt.execute();
            
        } else {
            }
    }
    /**
    * This function is used for chekLoginDetails in database 
    */
       public void chekLoginDetails() throws SQLException {
        stmt.executeQuery("SELECT * FROM TABLELOGIN");
        ResultSet rs = stmt.getResultSet();

        while (rs.next()) {
            String user_id = rs.getString("userid");
            String user_name = rs.getString("username");
            //         Boolean login_status = rs.getBoolean("login");
            System.out.println("id = " + user_id
                    + "name = " + user_name);
            ++countRecords;
        }
        System.out.println(countRecords + " rows were inserted");
        rs.close();
        //    stmt.close ();
    }
}
