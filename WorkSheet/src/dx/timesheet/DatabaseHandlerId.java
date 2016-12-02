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
                Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
                               
                con = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=developer.mdb;");
                                                  
                stmt=con.createStatement();  
             
            } catch(SQLException ex) {
                Logger.getLogger(DatabaseHandlerId.class.getName()).log(Level.SEVERE, null, ex);
            } catch(ClassNotFoundException ex) {
                Logger.getLogger(DatabaseHandlerId.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
   
    public void insertId(String id){
        try{
            String query="SELECT MAX(pId) FROM ServerId";
            rs=stmt.executeQuery(query);
            rs.next();
            int maxId=rs.getInt(1);
            System.out.println(""+maxId);
            maxId+=1;
            if(maxId==0){maxId=1;}
            String insertQuery="INSERT INTO ServerId VALUES("+id+","+maxId+");";
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
}

  
