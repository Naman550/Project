/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dx.timesheet;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.codec.binary.Base64;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Me
 */
public class ReadXml {

    SimpleDateFormat formatter = new SimpleDateFormat("E dd MMM yyyy 'at' hh:mm a");
    Random random = new Random();
    static int k=0;
    static boolean isPresent = false;
    DatabaseHandler dbhandler = new DatabaseHandler();
    private final String DOMAIN = "http://pms.trimonks.com/";
    String server_response, response;
    static String response1 = "";
    Activity usermap = new Activity();
    String[] users;
    String[] u;
    DefaultListModel listmodel = new DefaultListModel();
    ArrayList<String> s = new ArrayList<String>();
    public static ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
    ReviewDialog rd;
    String task_id;
    String id;

    public void getUserList() {

        try {
            double i=random.nextDouble()+random.nextDouble();
            k+=1;
            listmodel.clear();
            mylist.clear();
            InputStream in = null;
            URL userUrl = new URL(DOMAIN + "users/getusers"+"?"+k+"naman"+i);
            System.out.println("abcdefgh - "+userUrl);
            in = userUrl.openStream();
            Document doc = parse(in);
            doc.getDocumentElement().normalize();
            NodeList listOfUsers = doc.getElementsByTagName("user");
            System.out.println("Total users : " + listOfUsers.getLength());
            users = new String[listOfUsers.getLength()];
            for (int temp = 0; temp < listOfUsers.getLength(); temp++) {
                Node nNode = listOfUsers.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    NodeList node_id = eElement.getElementsByTagName("id");
                    Element e_id = (Element) node_id.item(0);
                    final String id = e_id.getChildNodes().item(0).getNodeValue();
                    System.out.println("user id is>>" + id);

                    NodeList node_name = eElement.getElementsByTagName("name");
                    Element e_name = (Element) node_name.item(0);
                    final String name = e_name.getChildNodes().item(0).getNodeValue();
                    System.out.println("user name is>>" + name);

                    mylist.add(new HashMap<String, String>() {
                        {
                            put(id, name);
                        }
                    });
                    //   rd.setValuesinList(new HashMap<String,String>(){{put(id,name);}},temp);
                    //   usermap.addUserToList(id, name);
                    //       usermap.addUserToModel(name);
                    //  users[temp]=name;
                    //     addUser(name);
                    listmodel.addElement(name);
                    //   System.out.println("Listmodel is"+s.get(temp));
                }
            }

            System.out.println("Length s is>>" + s.size());
            String[] u = new String[s.size()];
            //    u = s.toArray(u);
            //    System.out.println("Length s is>>"+s.size());
            //    System.out.println("Length u is>>"+u.length);
            //  setUsers(s);

            //      u = new String[s.size()];
            //      u = s.toArray(u);
            //      System.out.println("Length s is>>"+u.length);
        } catch (IOException ex) {
            Logger.getLogger(ReadXml.class.getName()).log(Level.SEVERE, null, ex);
        }

        //  return u;
        //  rd=new ReviewDialog(null, true);
        printHashmap();
        //    printHashmap();
        ReviewDialog.listUserReview.setModel(listmodel);

        //  rd.setValuesinList(mylist);
    }

    public void sendLink(String userid, String taskid, String link, String percentage) throws MalformedURLException, IOException {
        URL userUrl = new URL(DOMAIN + "tasks/getScreen/" + userid + "/" + taskid + "/" + link + "/" + percentage);
        System.out.print("Link url is>> " + userUrl);
        InputStream in = null;

        in = userUrl.openStream();

        StringBuilder response = new StringBuilder();
        byte[] respBuffer = new byte[4096];
        while (in.read(respBuffer) >= 0) {
            response.append(new String(respBuffer).trim());
        }
        System.out.print("Link response is>> " + response);
    }

    public String sendDeadlineRequest(String userid, String taskid, String date) throws IOException {
        URL url = new URL(DOMAIN + "tasks/extend/" + userid + "/" + taskid + "/" + date);
        System.out.println("extend url is>> " + url);

        StringBuilder response_builder = getStreamResponse(url);
        return response_builder.toString();
    }

    public String getUser() {
        String userid = null;
        try {
            dbhandler.connect();
            userid = dbhandler.getUserId();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ReadXml.class.getName()).log(Level.SEVERE, null, ex);
        }
        return userid;
    }

    public String getBase64String(String input) {
        byte[] encodedBytes = Base64.encodeBase64(input.getBytes());
        String newString = new String(encodedBytes);
        newString = newString.replaceAll("/", "_");
        System.out.println("encodedBytes " + newString);
        return newString;
    }

    public StringBuilder getStreamResponse(URL url) throws IOException {
        InputStream in = url.openStream();
        DataInputStream dis = new DataInputStream(new BufferedInputStream(in));
        String s;
        StringBuilder responseString = new StringBuilder("");
        while ((s = dis.readLine()) != null) {
            responseString.append(s);
        }
        System.out.println("Naman - responseString - "+responseString);
        return responseString;
    }

    public int getStatusCode(URL url) {
        //  URL url = new URL("");
        int code = 0;
        URLConnection connection = null;
        try {
            connection = url.openConnection();
        } catch (IOException ex) {
            code=0;
            Logger.getLogger(ReadXml.class.getName()).log(Level.SEVERE, null, ex);
        }
        try{
              connection.connect();
        }catch(IOException ex){
            code=5;
        }
        HttpURLConnection httpConnection = (HttpURLConnection) connection;
        try {
            code = httpConnection.getResponseCode();
        } catch (IOException ex) {
          // code=5;
            Logger.getLogger(ReadXml.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        return code;
    }

    public void sendTrackNetInfo(String content_type, String host, String request_url, String referer, String user_agent) throws IOException {
        if (!"application/ocsp-request".equals(content_type) && !user_agent.contains("Java/")) {
            Calendar now = Calendar.getInstance();
            String str = formatter.format(now.getTime());
            //   str=str.replaceAll("\\/","^");
            str = getBase64String(str);
            if (host != null) {
                host = getBase64String(host);
            }
            if (request_url != null) {
                request_url = getBase64String(request_url);
            }
            if (referer != null) {
                referer = getBase64String(referer);
            }

            URL userUrl = new URL(DOMAIN + "UserUrls/add/" + PopUpLogin.main_userid + "/" + host + "/" + request_url + "/" + referer + "/" + str);
            System.out.print("Track url is>> " + userUrl);
            StringBuilder response_builder = getStreamResponse(userUrl);
            System.out.print("response is>> " + response_builder.toString());
            System.out.println("*************************************************Sending data as parameters are correct**************************************");
        } else {
            System.out.println("**********************************************Data not sent because of incorrect parameters***********************************");
        }

    }

    public void printHashmap() {
        for (int i = 0; i < mylist.size(); i++) {
            System.out.println("Value in hashmap is>> " + this.mylist.get(i).keySet());
        }
    }

    public ListModel getListModel() {
        for (int i = 0; i < listmodel.size(); i++) {
            System.out.println("Listmodel elements>>" + listmodel.get(i));
        }
        return listmodel;
    }

    public void printValues(int index) {
        System.out.println("Value is>>" + this.mylist.get(index).keySet());
    }

    public String[] getUsers() {
        System.out.println("Length u in getUsers is>>" + u.length);
        return u;
    }

    public int readTaskLength(String usrname, String pwd) throws MalformedURLException, IOException {
        int totalTasks = 0;
        double i=random.nextDouble()+random.nextDouble();
        k+=1;
        InputStream in = null;
        System.out.println("Reading tasks for " + usrname);
        //   String username=dbhandler.getUserName();
        URL xmlUrl = new URL(DOMAIN + "users/getXml/" + usrname + "/" + pwd+"?"+k+"naman"+i);
        StringBuilder response_builder = getStreamResponse(xmlUrl);
        System.out.print("response is>> " + response_builder.toString());
        server_response = response_builder.toString();
        if ("sorry".equals(server_response)) {
            totalTasks = 0;
        } else {
            in = xmlUrl.openStream();
            Document doc = parse(in);
            doc.getDocumentElement().normalize();
            NodeList listOfTasks = doc.getElementsByTagName("tasks");
            totalTasks = listOfTasks.getLength();
            System.out.println(totalTasks);
        }
        return totalTasks;

    }

    public Document parse(InputStream is) {
        Document ret = null;
        DocumentBuilderFactory domFactory;
        DocumentBuilder builder;
        try {
            domFactory = DocumentBuilderFactory.newInstance();
            domFactory.setValidating(false);
            domFactory.setNamespaceAware(false);
            builder = domFactory.newDocumentBuilder();
            ret = builder.parse(is);
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            System.out.println("unable to load XML: " + ex);
        }
        return ret;
    }

    public String sendPauseRequest(String userid, String taskid, String status) throws MalformedURLException, IOException, SQLException {
        double i=random.nextDouble()+random.nextDouble();
        k+=1;
        String u = DOMAIN + "TimeSheets/getSheet/" + userid + "/" + taskid + "/" + status+"?"+k+"naman"+i;
        System.out.println(u);
        URL url = new URL(u);
        System.out.println(u);
        StringBuilder response_builder = getStreamResponse(url);

        response = response_builder.toString();
        //    timesheet_response=responseString.toString();
        // main_timeid=timesheet_id;
        //       System.out.println("Before Inserting time id in db");
        //     main_timeid = timesheet_id;
        //    dbHandler.insertTimesheetId(main_timeid);

        //      System.out.println(main_timeid);
        //       System.out.println("After Inserting time id in db");
        //      System.out.println(responseString);
        return response;
    }

    public String sendDataForTimesheet(String userid, String taskid, String status, String timesheetid, String comment) throws MalformedURLException, IOException {
        
        String u = DOMAIN + "TimeSheets/getSheet/" + userid + "/" + taskid + "/" + status + "/" + timesheetid + "/" + comment;
        System.out.print(u);
        URL url = new URL(u);
        System.out.println(u);
        StringBuilder response_builder = getStreamResponse(url);
        response = response_builder.toString();
        //       System.out.println(responseString);
        return response;
    }

    public String sendReview(String user_id, String task_id) throws MalformedURLException, IOException {
        String u = DOMAIN + "projectReviews/add/" + user_id + "/" + task_id;
        System.out.println("projectReviews1 - "+u);
        URL url = new URL(u);
        System.out.println("projectReviews2 - "+u);
        StringBuilder response_builder = getStreamResponse(url);
        response = response_builder.toString();
        response1 = response;
        setResponse(response);
        return response;
    }

    public void setResponse(String res) {
        response1 = res;
        System.out.println("Response is>> " + response1);
    }

    public String getResponse() {
        System.out.println("Response is>> " + response1);
        return response1;
    }
}
