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
public class Session {
    static String firstName;
    static String lastName;
    static int id;

    public static String getFirstName() {
        return firstName;
    }

    public static void setFirstName(String firstName) {
        Session.firstName = firstName;
    }

    public static String getLastName() {
        return lastName;
    }

    public static void setLastName(String lastName) {
        Session.lastName = lastName;
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        Session.id = id;
    }
    
    
}
