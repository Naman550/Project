/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dx.timesheet;


import java.awt.AWTException;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.net.ssl.HttpsURLConnection;
import javax.swing.JProgressBar;
import javax.swing.Painter;
import javax.swing.UIDefaults;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Me
 */
public class HardwareDetails {

    Sigar sigar = new Sigar();
    SimpleDateFormat formatter = new SimpleDateFormat("yyMMddhhmma");
    SimpleDateFormat formatEndDate = new SimpleDateFormat("dd-MMM-yyyy");
    org.hyperic.sigar.CpuInfo[] infos;
    org.hyperic.sigar.CpuInfo info;
    String fileName;
    String app_id = "39964";
    String api_key = "mby35aj5cnb64cy51td74yfhz70yh5hwkzbrm18n";
    
    String user_name = "developers.dx@gmail.com";
    String password = "developer@123";
    String signatureString = user_name + password + app_id + api_key;
    String selectedPath = "", serverResponseMessage;
    int serverResponseCode;
    static int i;
    String mySessionToken = "";
    FileInputStream fileInputStream = null;
    HttpURLConnection connection = null;
    DataOutputStream outputStream = null;
    DataInputStream inputStream = null;
    String pathToOurFile = "h://screenshot1.jpg";
    String lineEnd = "\r\n";
    String twoHyphens = "--";
    String boundary = "*****";
    int bytesRead, bytesAvailable, bufferSize;
    byte[] buffer;
    int maxBufferSize = 1 * 1024 * 1024;
    String pollUrl = "http://www.mediafire.com/api/upload/poll_upload.php";

    ReadXml rd=new ReadXml();
    //  String app_id1 = "1200";
    //   String api_key1 = "c1d62nzqu7z4rig15axe72jvczib53682kapu7lk";
    //   String user_name1 = "name@domain.com";
    //  String password1 = "123456";
    public HardwareDetails() {
    }

    public void createScreenshotDirectory() {
        
        File theDir = new File("C:\\xdata");
        if (!theDir.exists()) {
            System.out.println("creating directory: " + "TimesheetFiles");
            boolean result = theDir.mkdir();
            //  if(result) {    
            //     System.out.println("DIR created");  
            //  }else{
            //       System.out.println("DIR created"); 
            //   }
        }
        deleteAllScreenshots();
    }

    public long getCurrentTimestamp() {
        long unixTime = System.currentTimeMillis() / 1000L;
        System.out.println("Unix time>>" + unixTime);
        return unixTime;
    }

    public long getDifference(String deadline,String newdate) throws ParseException
{
  //  SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy");
  //  Date d = sdf.parse("Mon May 27 11:46:15 IST 2013");
 //   Calendar c = Calendar.getInstance();
  //  c.setTime(d);
  //  long time = c.getTimeInMillis();
  //  long curr = System.currentTimeMillis();
  //  long diff = curr - time;    //Time difference in milliseconds
  //  return diff/1000;
      long deadline1=convertDateToMillis(deadline);
      long newdate1=convertDateToMillis(newdate);
     
     long time=newdate1 - deadline1;
     return time;
}
    
    public long convertDateToMillis(String date) throws ParseException{
    //  String someDate = "05.10.2011";
      SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
      Date date1 = sdf.parse(date);
      System.out.println(date1.getTime());
      Calendar c = Calendar.getInstance();
      c.setTime(date1);
      long time = c.getTimeInMillis();
      return time;
    }
    
    public String printDate(String endTime) {
        Long endMillis = Long.valueOf(endTime);
        java.util.Date time = new java.util.Date((long) endMillis * 1000);
        //  System.out.println(tag+"time is is>> " + time);
        String str = formatEndDate.format(time);
        //    System.out.println(tag+" is>> " + str);
        //   Calendar tzCal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        //  System.out.println("Current UTC time is"+tzCal.getTimeInMillis());
        return str;
    }
    public Long getDateInMillis(String endTime){
        Long endMillis = Long.valueOf(endTime);
        return endMillis;
    }

    class MyPainter implements Painter<JProgressBar> {

        private final Color color;

        public MyPainter(Color c1) {
            this.color = c1;
        }

        @Override
        public void paint(Graphics2D gd, JProgressBar t, int width, int height) {
            gd.setColor(color);
            gd.fillRect(0, 0, width, height);
        }
    }

    public void setProgressBackground(Color color) {                               //change progressbar background
        UIDefaults defaults = new UIDefaults();
        defaults.put("ProgressBar[Enabled].foregroundPainter", new MyPainter(color));
        defaults.put("ProgressBar[Enabled+Finished].foregroundPainter", new MyPainter(color));
        PanelPlayPause.taskProgressbar.putClientProperty("Nimbus.Overrides.InheritDefaults", Boolean.TRUE);
        PanelPlayPause.taskProgressbar.putClientProperty("Nimbus.Overrides", defaults);
    }
    //  public void setDefaultBackground(){
    //       UIDefaults defaults = new UIDefaults();
    //             defaults.put("ProgressBar[Enabled].foregroundPainter", new MyPainter(color));
    //             defaults.put("ProgressBar[Enabled+Finished].foregroundPainter", new MyPainter(color));
    //             PanelPlayPause.taskProgressbar.putClientProperty("Nimbus.Overrides.InheritDefaults", Boolean.TRUE);
    //             PanelPlayPause.taskProgressbar.putClientProperty("Nimbus.Overrides", defaults);
    //  }

    public Double getTaskProgress(String start, String end, String current1) {                  //admin@gmail.com         //admin123
        Long startMillis = Long.valueOf(start);
        Long endMillis = Long.valueOf(end);
        Long current = Long.valueOf(current1);
        System.out.println("startMillis>> " + startMillis);
        System.out.println("endMillis>> " + endMillis);
        //   System.out.println("current - start>> "+(current-startMillis));
        //    System.out.println("endMillis - start>> "+(endMillis-startMillis));
        System.out.println("current>> " + current);



        Double upper = Double.valueOf(current - startMillis);
        Double lower = Double.valueOf(endMillis - startMillis);
        System.out.println("upper>> " + upper);
        System.out.println("lower>> " + lower);
        Double percentage = (upper / lower) * 100;
        //  Double dblupper=Double.valueOf(lower);
        System.out.println("division>> " + (upper / lower));
        //  float division=upper/lower;
        //   Double percentage=Double.valueOf(((current-startMillis)/(endMillis-startMillis))*100);
        System.out.println("Long Percentage is>> " + percentage);
        //      int a=percentage.intValue();
        //      System.out.println("Percentage is>> "+a);
        //   printDate(current.toString());


        return percentage;
    }

    public String processorModel() throws SigarException {
        infos = this.sigar.getCpuInfoList();
        info = infos[0];
        String pModel = null;
        String pVendor = info.getVendor();
        String pShortModel = info.getModel();
        int totalCores = info.getTotalCores();
        String pGhz;
        int gHz, gHz1, gHz2;
        gHz = info.getMhz() / 1000;
        gHz1 = info.getMhz() % 1000;
        gHz2 = gHz1 / 10;

        pGhz = gHz + "." + gHz2 +"GHz";

        pModel = pVendor + "," + pShortModel + "," + pGhz + "," + totalCores + "CPUs";
        //    System.out.println("Ghz............" + gHz+"."+gHz2+"GHz");



        return pModel;

    }

    public String getRamSize() throws SigarException {
        long ram = sigar.getMem().getRam();
        String ramString = ram +"MB";

        return ramString;
    }

    public String getOsName() {
        String os_name = "os.name";
        //      System.out.println(System.getProperty(os_name));
        String os = System.getProperty(os_name);
        os=os.replaceAll("\\s+", "");
        return os;

    }

    public String getHarddisk() throws SigarException {
        //      long harddisk=mem.getTotal();
        long actualSize = 0;
        File[] f = File.listRoots();
        for (int i = 0; i < f.length; i++) {
            //      System.out.println("Drive: " + f[i]);
            String drive = String.valueOf(f[i]);
            //        System.out.println("Total space: " + f[i].getTotalSpace());
            //       System.out.println("Usable space: " + f[i].getUsableSpace());
            long diskSize = new File(drive).getTotalSpace();
            actualSize = actualSize + diskSize;
        }
        actualSize = actualSize / 1024 / 1024 / 1024;
        //    String f1="C://";
        //     FileSystemUsage f=sigar.getFileSystemUsage(f1);
        //     double hdd=f.getTotal();
        String hhdd1 = actualSize +"GB";
        //    String harddiskString=harddisk/ 1024 / 1024 + " MB";
        //    return hhdd1;
        return hhdd1;
    }

    public void getScreenShot(String userid,String percentage) throws AWTException {
        try {
            Calendar now = Calendar.getInstance();
            BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
      //      image = resize(image, 1024, 768);
       //     image = resize(image, 1280, 1024);
            
            image = resize(image, 1280, 960);
            String str = formatter.format(now.getTime());
            ImageIO.write(image, "jpg", new File("C://xdata/" + str + ".jpg"));
            //   upload(new URL("www.asd.com"),new File());
            System.out.println("Screnshot taken");
            System.out.println("Session token is>> (" + mySessionToken + ")");
            
            
           mySessionToken="";
            mySessionToken = getSessionToken();
            //     } else {
            //          mySessionToken = renewSessionToken(mySessionToken);
            //     }
         //   showInfoDialog("Session present");
            if(!mySessionToken.equals("")){
                   uploadImageToMediafire(mySessionToken, new File("C://xdata//" + str + ".jpg"), userid + "_" + str + ".jpg", percentage);
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(HardwareDetails.class.getName()).log(Level.SEVERE, null, ex);
            }
            //        deleteScreenshot(new File("h://Screenshots/"+str+".jpg"));
        } catch (Exception ex) {
       //     showInfoDialog("Exception in certificates");
      //      ex.printStackTrace();
            PrintWriter writer = null;
            try {
                writer = new PrintWriter("c://error_log.txt", "UTF-8");
            } catch (    FileNotFoundException | UnsupportedEncodingException ex1) {
                Logger.getLogger(HardwareDetails.class.getName()).log(Level.SEVERE, null, ex1);
            }
            ex.printStackTrace(writer);
          
          //  writer.println("The first line");
        //    writer.println("The second line");
            writer.close();
        } 
    }

    public void deleteAllScreenshots() {
        File file = new File("C:\\xdata");
        String[] myFiles;
        if (file.isDirectory()) {
            myFiles = file.list();
            for (int i = 0; i < myFiles.length; i++) {
                File myFile = new File(file, myFiles[i]);
                myFile.delete();
            }
        }
    }

    public void deleteScreenshot(File file) {
        if (file.delete()) {
            System.out.println(file.getName() + " is deleted!");
        } else {
            System.out.println("Delete operation is failed.");
        }

    }

    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage dimg = new BufferedImage(newW, newH, img.getType());
        Graphics2D g = dimg.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(img, 0, 0, newW, newH, 0, 0, w, h, null);
        g.dispose();
        return dimg;
    }

    public static void upload(URL url, File file) throws IOException, URISyntaxException {
        HttpClient client = new DefaultHttpClient(); //The client object which will do the upload
        HttpPost httpPost = new HttpPost(url.toURI()); //The POST request to send
        FileBody fileB = new FileBody(file);
        MultipartEntity request = new MultipartEntity(); //The HTTP entity which will holds the different body parts, here the file
        request.addPart("file", fileB);
        httpPost.setEntity(request);
        HttpResponse response = client.execute(httpPost); //Once the upload is complete (successful or not), the client will return a response given by the server
        if (response.getStatusLine().getStatusCode() == 200) { //If the code contained in this response equals 200, then the upload is successful (and ready to be processed by the php code)
            System.out.println("Upload successful !");
        } else {
            System.out.println("Upload Failed !");
        }
    }

    public String getSessionToken() throws  Exception {
        String token = null;
    //    try {
            byte[] sig = encrypt(signatureString);
            String signature = byteArrayToHexString(sig);
            System.out.println("Signature is>> " + signature);
            //     String urlSessionToken = ;
            URL urlSessionToken = new URL("https://www.mediafire.com/api/1.5/user/get_session_token.php?email=" +
                    user_name + "&password=" + password + "&application_id=" + app_id + "&signature=" + signature+"&token_version=2");

            //Login token code
            System.out.println("Url for session token is>> " + urlSessionToken);
            InputStream is = null;
         //   HttpsURLConnection
            URLConnection con=urlSessionToken.openConnection();
            HttpsURLConnection conn1 = null;
       /*     if (con instanceof HttpsURLConnection) {
                System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<HTTPS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> ");
             conn1 = (HttpsURLConnection) urlSessionToken.openConnection();

             conn1.setHostnameVerifier(new HostnameVerifier() {
             public boolean verify(String hostname, SSLSession session) {
               return true;
             }
            });
            }*/
            
            is = con.getInputStream();
            token=readDocument(is, "session_token");
            System.out.println("Token is>> " + token);
      //  } catch (Exception ex) {
      //      mySessionToken = "";
     //       Logger.getLogger(HardwareDetails.class.getName()).log(Level.SEVERE, null, ex);
      //  }
        return token;
    }

    public String renewSessionToken(String oldtoken) {
        String token = null;
        try {
            //   String urlToRenewToken="http://www.mediafire.com/api/user/renew_session_token.php?session_token="+oldtoken;
            URL urlToRenewToken = new URL("https://www.mediafire.com/api/user/renew_session_token.php?session_token=" + oldtoken);
            InputStream is = null;
            is = urlToRenewToken.openStream();
            token=readDocument(is, "session_token");
            System.out.println("Token is>> " + token);

        } catch (IOException ex) {
            mySessionToken = "";
            Logger.getLogger(HardwareDetails.class.getName()).log(Level.SEVERE, null, ex);
        }
        return token;
    }

    public void uploadImageToMediafire(String token, File file, String fllename,String percentage) throws IOException {

        try {
            // Upload
            String urlServer = "http://www.mediafire.com/api/1.5/upload/upload.php?session_token="+ token;
            fileInputStream = new FileInputStream(file);
            URL url2 = new URL(urlServer);
            
          //  System.out.println("Url"+url2);
            
            connection = (HttpURLConnection) url2.openConnection();
            // Allow Inputs & Outputs
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            // Enable POST method
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Content-Type",
                    "multipart/form-data;boundary=" + boundary);
            outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.writeBytes(twoHyphens + boundary + lineEnd);
            outputStream.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\""
                    + fllename + "\"" + lineEnd);
            outputStream.writeBytes(lineEnd);
            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];
            // Read file
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            while (bytesRead > 0) {
                //     Log.w("----while (bytesRead > 0)---", " ");

                outputStream.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }
            outputStream.writeBytes(lineEnd);
            outputStream.writeBytes(twoHyphens + boundary + twoHyphens
                    + lineEnd);
            // Responses from the server (code and message)
            serverResponseCode = connection.getResponseCode();
            serverResponseMessage = connection.getResponseMessage();
            System.out.println("serverResponseCode is>> " + serverResponseCode);
            System.out.println("serverResponseMessage is>> " + serverResponseMessage);
            fileInputStream.close();
            outputStream.flush();
            outputStream.close();
            InputStream is2 = connection.getInputStream();
            System.out.println("namanS"+is2);
            Document doc = parse(is2);
            System.out.println("namanS"+doc);
            doc.getDocumentElement().normalize();
            NodeList listOfCodes = doc.getElementsByTagName("response");
            System.out.println("namanS"+listOfCodes);
            int totalTags = listOfCodes.getLength();
            
            NodeList nodelist_uploadkey = doc.getElementsByTagName("key");
            System.out.println("namanS9999"+nodelist_uploadkey);
            
            Node node_uploadkey = nodelist_uploadkey.item(0);
            System.out.println("namanS9999"+node_uploadkey);
            String uploadkey = node_uploadkey.getFirstChild().getNodeValue().toString();
            System.out.println("uploadkey is>> " + uploadkey);
            //    mySessionToken=renewSessionToken(token);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(HardwareDetails.class.getName()).log(Level.SEVERE, null, ex);
            }
            String quickKey = getQuickKey(uploadkey, mySessionToken, fllename);

            //   System.out.println("Quick key is>> "+quickKey);
            //      StringBuilder response = new StringBuilder();
            //      byte[] respBuffer = new byte[4096];
            //     while (is2.read(respBuffer) >= 0) {
            //        response.append(new String(respBuffer).trim());
            //     }

            String link=getFileLink(quickKey, mySessionToken);
            
            String[] part=link.split(".com/");
            String part2=part[1];
            System.out.println("Broken url is>> " + part2);
            
            part2=part2.replaceAll("\\/", "^");
            
            is2.close();
            DatabaseHandler dbHandler=new DatabaseHandler();
            String timeid="",taskid = "",userid = "";
            try {
             //   dbHandler.connect();
                 timeid=PopUpLogin.dbHandler.getTimeId();
                taskid=PopUpLogin.dbHandler.getTaskId();
               userid=PopUpLogin.dbHandler.getUserId();
            
           
            } catch (SQLException ex) {
                Logger.getLogger(HardwareDetails.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        //    showInfoDialog("us"+userid+"/ta"+taskid+"/ti"+timeid);
             rd.sendLink(userid,taskid,part2,percentage);
            //      System.out.println(response.toString());
        }  finally {
            try {
                fileInputStream.close();
            } catch (IOException ex) {
                Logger.getLogger(HardwareDetails.class.getName()).log(Level.SEVERE, null, ex);
            }
        }



    }

   public void showInfoDialog(String Info) {
        InfoDialog inf = new InfoDialog(null, true);
        inf.setLocationRelativeTo(new TaskPanel());
        inf.setInfo(Info);
        inf.setVisible(true);
    }
    
    public String getQuickKey(String upload_key, String session_token, String name) throws IOException {
        URL urlQuickKey = new URL("http://www.mediafire.com/api/upload/poll_upload.php?session_token=" + session_token + "&key=" + upload_key + "&filename=" + name);
        System.out.println("SUrl is>> " + urlQuickKey);

        connection = (HttpURLConnection) urlQuickKey.openConnection();

        InputStream is = connection.getInputStream();
        String quickkey=readDocument(is, "quickkey");

        //      StringBuilder response = new StringBuilder();
        //      byte[] respBuffer = new byte[4096];
        //      while (is.read(respBuffer) >= 0) {
        //         response.append(new String(respBuffer).trim());
        //      }
        //      System.out.println("response is>> " + response.toString());
        is.close();
        return quickkey;
    }

    public String getFileLink(String quick_key, String session_token) throws IOException {
        URL urlQuickKey = new URL("http://www.mediafire.com/api/file/get_links.php?session_token=" + session_token + "&quick_key=" + quick_key + "&link_type=" + "view");
        System.out.println("ssUrl is>> " + urlQuickKey);

        connection = (HttpURLConnection) urlQuickKey.openConnection();

        InputStream is = connection.getInputStream();
        String link=readDocument(is, "view");
        //    System.out.println("Link is>> " + quickkey);
        //      StringBuilder response = new StringBuilder();
        //      byte[] respBuffer = new byte[4096];
        //      while (is.read(respBuffer) >= 0) {
        //         response.append(new String(respBuffer).trim());
        //      }
        //      System.out.println("response is>> " + response.toString());
        is.close();
        return link;
    }
    public String readDocument(InputStream is,String tagname){
        Document doc = parse(is);
        doc.getDocumentElement().normalize();
        NodeList listOfCodes = doc.getElementsByTagName("response");
        int totalTags = listOfCodes.getLength();
        NodeList login_token_tag = doc.getElementsByTagName(tagname);
        //     int totalIds = listOfId.getLength();
        //          System.out.println("Total no of UserIds : " + totalIds);
        Node login_token = login_token_tag.item(0);
        String key = login_token.getFirstChild().getNodeValue().toString();
        System.out.println("Link is>> " + key);
        return key;
    }
    
    public static Document parse(InputStream is) {
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
            //          System.out.println("unable to load XML: " + ex);
        }
        return ret;
    }

    public static String byteArrayToHexString(byte[] b) {
        String result = "";
        for (int i = 0; i < b.length; i++) {
            result +=
                    Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
        }
        return result;
    }

    public static byte[] encrypt(String x) throws Exception {
        java.security.MessageDigest digest = null;
        digest = java.security.MessageDigest.getInstance("SHA-1");
        digest.reset();
        digest.update(x.getBytes("UTF-8"));
        return digest.digest();
    }

  

    public void processorDetails() throws SigarException {
       
        int gHz, gHz1, gHz2;
        gHz = info.getMhz() / 1000;
        gHz1 = info.getMhz() % 1000;
        gHz2 = gHz1 / 10;
     
    }
   
}
