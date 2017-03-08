/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vjavaapplication;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author naman
 */
public class Config {
    static  String HTTP;//="http://";
    static  String DOMAIN;//="gnic.co.in/pms.greatjob.com.in/index.php/auth/userLogin";
    static  String IMAGE_DOMAIN;//="gnic.co.in/pms.greatjob.com.in/index.php/users/userScreens";
    URL obj ;
    HttpURLConnection con ;
    Map<String,String> parameters = new HashMap<String,String>();
    Map<String,String> imageParameters = new HashMap<String,String>();
    
    
    public static void setDomain(){
        try {
//            File fXmlFile = new File("C:\\Users\\naman\\Documents\\NetBeansProjects\\VJavaApplication\\src\\vjavaapplication\\domain.xml");
            File fXmlFile = new File("domain.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            
            NodeList nList = doc.getElementsByTagName("domain");
            
            for(int i=0; i<nList.getLength(); i++){
                
                Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) node;
                   /**
                    *Set the various value from XML file 
                    */
                    HTTP = getElements(eElement, "http");
                    DOMAIN = getElements(eElement, "domain_url");
                    IMAGE_DOMAIN = getElements(eElement, "image_url");
                }
            }
            
            }
            catch (IOException ex) {
                Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParserConfigurationException ex) {
           Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
       } catch (SAXException ex) {
           Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
       }
    }
    
     public static String getElements(Element element, String tag) {
        NodeList nodelist = element.getElementsByTagName(tag);
        String value = "";
        if (nodelist.getLength() > 0) {
            Element elm = (Element) nodelist.item(0);
            value = elm.getChildNodes().item(0).getNodeValue();
            System.out.println(value);
        }
        return value;
    }
    
    // HTTP POST request
	public int sendPost() throws MalformedURLException, IOException {
                
            String url = HTTP+DOMAIN;
            obj = new URL(url);
            con = (HttpURLConnection) obj.openConnection();

            //add reuqest header
            con.setRequestMethod("POST");
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            String urlParameters = getMap(parameters);
            // Send post request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + url);
            System.out.println("Post parameters : " + urlParameters);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
            }
            in.close();

            //print result
            int code=jsonData(response.toString());
            return code;

	}
        
        
        // HTTP-Image POST request
	public void sendImagePost() throws MalformedURLException, IOException {
                
            String url = HTTP+IMAGE_DOMAIN;
            obj = new URL(url);
            con = (HttpURLConnection) obj.openConnection();

            //add reuqest header
            con.setRequestMethod("POST");
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            String urlParameters = getMap(imageParameters);
            // Send post request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + url);
            System.out.println("Post parameters : " + urlParameters);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
            }
            in.close();

            //print result
            int code=jsonImageData(response.toString());
            System.out.println("lastDay -->>"+code);

	}
        
        public void setMap(String userId, String password){
             
            // Add some parameters.
            parameters.put("identity",userId);
            parameters.put("password",password);
        }
        
         public void setImageMap(String userId, String screenShot){
             
            // Add some imageParameters.
            imageParameters.clear();
            imageParameters.put("id",userId);
            imageParameters.put("screenShot",screenShot);
        }
         
         public String getMap(Map<String, String> params) throws UnsupportedEncodingException {
            StringBuilder result = new StringBuilder();
            boolean firstTime = true;

            for (String pair : params.keySet()) {
            if (firstTime)
            firstTime = false;
            else
            result.append("&");

            result.append(URLEncoder.encode(pair, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(params.get(pair), "UTF-8"));
            System.out.println(result.toString());
            }

            return result.toString();
        }
         
         /**
     *Parse the JSON data and send the string response to user
     */
    public int jsonData(String data){
    
        JSONObject obj = new JSONObject(data);
        int response = obj.getInt("response");
        System.out.println(response);
        if(response==200){
            JSONObject message = obj.getJSONObject("message");
            int id = message.getInt("id");
            String firstName = message.getString("firstname");
            String lastname = message.getString("lastname");
            System.out.println("lastname - "+lastname+" firstName "+firstName+" id "+id);

            Session.setId(id);
            Session.setFirstName(firstName);
            Session.setLastName(lastname);
        }
        
        return response;
    }
    
     /**
     *Parse the JSON data and send the string response to user
     */
    public int jsonImageData(String data){
    
        System.err.println(data);
        JSONObject obj = new JSONObject(data);
        int response = obj.getInt("response");
        
//        JSONObject message = obj.getJSONObject("message");
//        int id = message.getInt("id");
//        String firstName = message.getString("firstname");
//        String lastname = message.getString("lastname");
//        System.out.println("lastname - "+lastname+" firstName "+firstName+" id "+id);
//        
//        Session.setId(id);
//        Session.setFirstName(firstName);
//        Session.setLastName(lastname);
        
        return response;
    }   
    
}
