/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vjavaapplication;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

/**
 *
 * @author naman
 */
public class Config {
    static final String HTTP="http://";
    static final String DOMAIN="";
    URL obj ;
    HttpURLConnection con ;
    Map<String,String> parameters = new HashMap<String,String>();
    
    
    // HTTP POST request
	public int sendPost() throws MalformedURLException, IOException {
                
            String url = HTTP+"greatjob.co.in/pms.greatjob.com.in/index.php/auth/userLogin";
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
        
        public void setMap(String userId, String password){
             
            // Add some parameters.
            parameters.put("identity",userId);
            parameters.put("password",password);
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
        
        JSONObject message = obj.getJSONObject("message");
        int id = message.getInt("id");
        String firstName = message.getString("firstname");
        String lastname = message.getString("lastname");
        System.out.println("lastname - "+lastname+" firstName "+firstName+" id "+id);
        
        Session.setId(id);
        Session.setFirstName(firstName);
        Session.setLastName(lastname);
        
        return response;
    }
        
    
}
