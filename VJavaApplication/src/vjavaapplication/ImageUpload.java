/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vjavaapplication;


/**
 *
 * This class is used for Image upload to the GoogleDrive
 */


import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.*;
import com.google.api.services.drive.Drive;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
  
 public class ImageUpload {
    /** Application name. */
    private static final String APPLICATION_NAME =
        "Other client 1";
    
   static String id="";

    /** Directory to store user credentials for this application. */
//    private static final java.io.File DATA_STORE_DIR = new java.io.File(System.getProperty("user.home"), ".credentials/drive-java-quickstart");
   private static final java.io.File DATA_STORE_DIR = new java.io.File(".credentials/drive-java-quickstart");
   

    /** Global instance of the {@link FileDataStoreFactory}. */
    private static FileDataStoreFactory DATA_STORE_FACTORY;
    
   

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY =
        JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport HTTP_TRANSPORT;

    /** Global instance of the scopes required by this quickstart.
     *
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/drive-java-quickstart  DriveScopes.DRIVE
     */
    
    private static final List<String> SCOPES = Arrays.asList(DriveScopes.DRIVE);

    //public static final java.util.Collection<String> SCOPES = DriveScopes.all();

    
    static  GoogleAuthorizationCodeFlow flow ;

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Creates an authorized Credential object.
     * @return an authorized Credential object.
     * @throws IOException
     */
    public static Credential authorize() throws IOException {
        // Load client secrets.
        InputStream in =
            ImageUpload.class.getResourceAsStream("client_secret.json");
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        System.out.println("clientSecrets - "+clientSecrets);
        System.out.println("Scopes   --- "+SCOPES);
        
        // Build flow and trigger user authorization request.
         flow =  new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(DATA_STORE_FACTORY)
                .setAccessType("offline")
                .build();
        
        System.out.println("flow - "+flow);
        
        Credential credential = new AuthorizationCodeInstalledApp(
            flow, new LocalServerReceiver()).authorize("user");
        
        System.out.println("Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
        //System.out.println("Credentials -- "+credential.getRefreshToken());
         
        return credential;
    }

    /**
     * Build and return an authorized Drive client service.
     * @return an authorized Drive client service
     * @throws IOException
     */
    public static Drive getDriveService() throws IOException {
        Credential credential = authorize();
        
        System.out.println("Service - "+new Drive.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, credential).setApplicationName(APPLICATION_NAME).build());
        
        return new Drive.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public static void main(String[] args) throws IOException {
        // Build a new authorized API client service.
        Drive service = getDriveService();
            //image/jpeg, text/plain
          insertFile(service, "hello", "ScreenShot Tracking","0B2mfptk7yb8rWnVVcHNyRnlIWG8" ,"image/png" ,"C://Services/" + "paytm" + ".png","");
        
    }
    
     public void main(String fileName, String id) throws IOException{
    
        Drive service = getDriveService();
        insertFile(service, fileName, "ScreenShot Tracking","0B2mfptk7yb8rWnVVcHNyRnlIWG8" ,"image/jpeg" ,"C://Services/" + fileName + ".jpg",id);
        
    }
    
    
    private static File insertFile(Drive service, String title, String description,
        final String parentId, String mimeType, String filename, String id) {
      // File's metadata.
      File body = new File();
      body.setTitle(title);
      body.setDescription(description);
      body.setMimeType(mimeType);
      
        System.out.println(body.getMimeType()+"  -  "+body.getDescription());

      // Set the parent folder.
      if (parentId != null && parentId.length() > 0) {
        body.setParents(
            Arrays.asList(new ParentReference().setId(parentId)));
      }

      // File's content.
      java.io.File fileContent = new java.io.File(filename);
      //  System.out.println("file name "+fileContent.getAbsolutePath());
      FileContent mediaContent = new FileContent(mimeType, fileContent);
      //  System.out.println("mediaConent "+mediaContent + "  "+service.files());
      try {
       //   System.out.println("file  - "+service.files().insert(body, mediaContent));
        File file = service.files().insert(body, mediaContent).execute();

        // Uncomment the following line to print the File ID.
        final String fileId = file.getId();
        
//        Config config =new Config();
//        config.setImageMap(id, title);
//        try {
//            config.sendImagePost();
//        } catch (IOException ex) {
//            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
        URL url =new URL(Config.HTTP+Config.IMAGE_DOMAIN+"/"+id+"/"+fileId);
          System.out.println("Global ID-->> "+fileId);
        
         System.out.println("ImageScreenShotUrlIsHere  -  "+url);
//         
                  InputStream in = null;

                  try {
                      in = url.openStream();
                      System.out.println("ooppppss-->>>"+in);
                  } catch (IOException ex) {
                      System.out.println(ex);
                  }
//        
        
        
//        new Runnable() {
//            @Override
//            public void run() {
//                try{
//                 new PopUpLogin().uploadScreenShot(parentId, fileId);
//                }
//                catch(MalformedURLException e){
//                    System.out.println("The MalformedURLException -  "+e);
//                }
//                
//            }
//        }.run();
       
       
        return file;
      } catch (IOException e) {
        System.out.println("An error occured: " + e);
        return null; 
      }
    }

   
    
    
}