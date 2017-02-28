/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dx.timesheet;

/**
 *
 * @author naman
 */
import java.io.*;  
import java.net.*; 

public class chat {  
public static void main(String[] args){  
try{  
URL url=new URL("http://pms.designersx.us/employee/conversations/setChat/135/134/hiii/1702131202PM?1naman0.8823442011911128");  
URLConnection urlcon=url.openConnection();  

InputStream stream=urlcon.getInputStream();  
int i;  
while((i=stream.read())!=-1){  
System.out.print((char)i);  
}  
}catch(Exception e){System.out.println(e);}  
}  
}  