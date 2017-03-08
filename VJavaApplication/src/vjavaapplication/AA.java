/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vjavaapplication;

/**
 *
 * @author naman
 */
import java.net.InetAddress;
import java.net.UnknownHostException;

public class AA {
    


    public static void main(String[] args) {
        String hostname = "Unknown";
        try{
            InetAddress addr;
            addr = InetAddress.getLocalHost();
            System.out.println(addr);
            hostname = addr.getHostName();
            System.out.println("Hostname"+hostname);
        }
        catch (UnknownHostException ex)
        {
            System.out.println("Hostname can not be resolved");
        }
    }
}
