/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dx.timesheet;

/**
 *
 * @author Developer
 */
import java.sql.*;  
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseHandlerId {
    
    Statement stmt;
    Connection con;
    ResultSet rs;
    String gId;
    
    
    
    public DatabaseHandlerId(){
        try{ 
//                Class.forName("com.mysql.jdbc.Driver");  
//                con=DriverManager.getConnection("jdbc:mysql://localhost/developer","root","root");  
//                Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
//                               
//                con = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=developer.mdb;");


              String url = "jdbc:sqlite:developer.db";
//                String url = "jdbc:sqlite:/home/developer/Downloads/WorkSheet/developer.db";
 
                con = DriverManager.getConnection(url);                                  
                stmt=con.createStatement();  
             
            } catch(SQLException ex) {
                Logger.getLogger(DatabaseHandlerId.class.getName()).log(Level.SEVERE, null, ex);
            } 
    }
   
    public void insertId(String id){
        try{
            deleteId();
            String query="SELECT MAX(pId) FROM ServerId";
            rs=stmt.executeQuery(query);
            rs.next();
            int maxId=rs.getInt(1);
            System.out.println(""+maxId);
            maxId+=1;
            if(maxId==0){maxId=1;}
            String insertQuery="INSERT INTO ServerId VALUES("+id+","+maxId+");";
            //String insertQuery="INSERT INTO ServerId VALUES("+id+");";
           // System.out.println(""+insertQuery);
            stmt.executeUpdate(insertQuery);
            System.out.println("Insert Successfully");
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandlerId.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void insertNewTaskID(String task_id, String login_userId, String nTid){
        try{
            
            String getQuery="SELECT task_id FROM NewTaskID where task_id='"+task_id+"';";
            rs = stmt.executeQuery(getQuery);
            
            String insertQuery="insert into NewTaskID(task_id,login_user_id,new_task_id) values("+task_id+","+login_userId+","+nTid+");";
//            String updateQuery="UPDATE NewTaskID\n" +
//                                "SET new_task_id = 'None'\n" +
//                                "WHERE [Last Name] = 'Naman'";
            //String insertQuery="INSERT INTO ServerId VALUES("+id+");";
           
            if(rs.next()){
            }else{
                System.out.println(""+insertQuery);
                stmt.executeUpdate(insertQuery);
            }
            System.out.println("Insert Successfully");
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandlerId.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public void insertTid(String id){
        try{
            String query="SELECT MAX(pId) FROM TID";
            rs=stmt.executeQuery(query);
            rs.next();
            int maxId=rs.getInt(1);
            System.out.println(""+maxId);
            maxId+=1;
            if(maxId==0){maxId=1;}
            String insertQuery="INSERT INTO TID VALUES("+id+","+maxId+");";
            //String insertQuery="INSERT INTO ServerId VALUES("+id+");";
            stmt.executeUpdate(insertQuery);
            System.out.println("Insert Successfully");
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandlerId.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public void deleteId(){
        try{
            String insertQuery="DELETE FROM ServerId";
            stmt.executeUpdate(insertQuery);
            System.out.println("Delete Successfully");
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandlerId.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public String getId(){
        try {
            
            String getQuery="SELECT NID FROM ServerId;";
            
            rs = stmt.executeQuery(getQuery);
             //stmt.getResultSet();
            while(rs.next()){
                gId=rs.getString("NID");
                System.out.println("idddddddddddddddddddd======>>>>>>"+gId);
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandlerId.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return gId;
    }
    
    public String getTid(String tid){
        try {
            
            String getQuery="SELECT new_task_id FROM NewTaskID where task_id='"+tid+"';";
            
            rs = stmt.executeQuery(getQuery);
             //stmt.getResultSet();
            while(rs.next()){
                gId=rs.getString("new_task_id");
                System.out.println("idddddddddddddddddddd======>>>>>>"+gId);
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandlerId.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return gId;
    }
}

  
