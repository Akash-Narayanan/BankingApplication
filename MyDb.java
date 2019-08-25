
import static java.lang.System.out;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author AKASH
 */
public class MyDb {
    Connection con;
    public Connection getCon()
    {
        try{
          con=  DriverManager.getConnection("jdbc:derby://localhost:1527/akash","root","akash21");     
        }catch (SQLException ex) {
            System.out.println(ex);
            Logger.getLogger(MyDb.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Logger.getLogger(MyDb.class.getName()).log(Level.SEVERE, null, ex);
        
        
        return con;
    }
}

