/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dx.timesheet;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 *
 * @author Me
 */
public class EncryptPwd {
    
     public String encrypt(String source) {
String md5 = null;
try {
    MessageDigest mdEnc = MessageDigest.getInstance("MD5"); // Encryption algorithm
    mdEnc.update(source.getBytes(), 0, source.length());
    md5 = new BigInteger(1, mdEnc.digest()).toString(16); // Encrypted string
} catch (Exception ex) {
    return null;
}
return md5;
}
}
