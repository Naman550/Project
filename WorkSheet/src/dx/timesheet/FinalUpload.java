/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dx.timesheet;


/**
 *
 * @author Developer
 */


import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.media.MediaHttpUploader;
import static com.google.api.client.googleapis.media.MediaHttpUploader.UploadState.INITIATION_COMPLETE;
import static com.google.api.client.googleapis.media.MediaHttpUploader.UploadState.INITIATION_STARTED;
import com.google.api.client.googleapis.media.MediaHttpUploaderProgressListener;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.InputStreamContent;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;

import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.*;
import com.google.api.services.drive.Drive;
import static dx.timesheet.PopUpLogin.task_id;
import static dx.timesheet.PopUpLogin.userId;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;
import javax.print.attribute.standard.SheetCollate;
import org.apache.http.HttpResponse;
import org.jose4j.json.internal.json_simple.JSONObject;

 public class FinalUpload implements MediaHttpUploaderProgressListener{
    /** Application name. */
    private static final String APPLICATION_NAME =
        "Other client 1";
    
   static String id="";

    /** Directory to store user credentials for this application. */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
        System.getProperty("user.home"), ".credentials/drive-java-quickstart");

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
            FinalUpload.class.getResourceAsStream("client_secret.json");
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
            
            
            
        FileList result = service.files().list().setMaxResults(30).execute();
        boolean a=true;
        String id="";
        List<File> files = result.getItems();
        if (files == null || files.size() == 0) {
            System.out.println("No files found.");
        } else {
            System.out.println("Files:");
            for (File file : files) {
                System.out.printf("%s (%s)\n", file.getTitle(), file.getId());
                if("abc".equals(file.getTitle())){
                    id=file.getId();
                    a=false;
                }
            }
        }
            
            if(a){
            
            File body = new File();
            body.setTitle("abc");
             body.setDescription("hello");
             body.setMimeType("application/vnd.google-apps.folder");
            
             File file1 = service.files().insert(body).setFields("id").execute();
             id=file1.getId();
            System.out.println("Folder ID: " + file1.getId());
            
            }
            
            
//            java.io.File mediaFile = new java.io.File("C:\\Users\\Developer\\Pictures\\abc.txt");
//            InputStreamContent mediaContent = new InputStreamContent("text/plain", new BufferedInputStream(new FileInputStream(mediaFile)));
//            mediaContent.setLength(mediaFile.length());
//
//            Drive.Files.Insert request = service.files().insert(body, mediaContent);
//            System.out.println("nmn -- "+request.getMediaHttpUploader().setProgressListener(new Final1()));
//            try{
//            request.getMediaHttpUploader().setProgressListener(new Final1());
//            request.execute();
//            } catch(Exception e){
//                System.out.println("Exception - "+e);
//            }
            
            
            
       //  insertFile(service, "developer", "testing_file",id ,"image/jpeg" ,"C:\\Users\\Developer\\Pictures\\abc.jpg");
        
        // Print the names and IDs for up to 10 files.
       
        
      
        
    }
    
     public void main(String fileName, String folderName,String percentage) throws IOException{
    
        Drive service = getDriveService();
       
        insertFile(service, fileName, "ScreenShot Tracking","0B3iqPfSeaLwEUXZlOU1uNGNsVDA" ,"image/jpeg" ,"C://Init/" + fileName + ".jpg",percentage);
        
    }
    
    
    
//    public void main(String fileName, String folderName) throws IOException{
//    
//        Drive service = getDriveService();
//        
//        
//        FileList result = service.files().list().execute();
//        boolean a=true;
//        
//        List<File> files = result.getItems();
//        
//        if (files == null || files.size() == 0) {
//            System.out.println("No files found.");
//        } else {
//            System.out.println("Files:");
//            for (File file : files) {
//                System.out.printf("%s (%s)\n", file.getTitle(), file.getId());
//                if(folderName.equals(file.getTitle())){
//                    id=file.getId();
//                    a=false;
//                    
//                }
//            }
//        }
//            
//            if(a){
//            
//            File body = new File();
//            body.setTitle(folderName);
//             body.setDescription(folderName+" Tracking Folder");
//             body.setMimeType("application/vnd.google-apps.folder");
//            
//             File file1 = service.files().insert(body).setFields("id").execute();
//             id=file1.getId();
//            System.out.println("Folder ID: " + file1.getId());
//            
//            }
//        
//        
//        insertFile(service, fileName, "ScreenShot Tracking",id ,"image/jpeg" ,"C://Init/" + fileName + ".jpg");
//        
//    }
//    
    private static File insertFile(Drive service, String title, String description,
        final String parentId, String mimeType, String filename, String percentage) {
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
        
         URL url =new URL(Config.HTTP+Config.DOMAIN+"Tasks/getScreen/"+userId+"/"+task_id+"/"+parentId+"/"+fileId+"/"+percentage);
       //  URL url =new URL(Config.HTTP+Config.DOMAIN+"Tasks/getScreen/"+userId+"/"+task_id+"/"+fileId);
        
         System.out.println("NamanScreenShotUrlIsHere  -  "+url);
         
                  InputStream in = null;

                  try {
                      in = url.openStream();
                  } catch (IOException ex) {
                      Logger.getLogger(PopUpLogin.class.getName()).log(Level.SEVERE, null, ex);
                  }
        
        
        
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

    @Override
    public void progressChanged(MediaHttpUploader uploader) throws IOException {
        switch (uploader.getUploadState()) {
      case INITIATION_STARTED:
        System.out.println("\n*********Initiation has started!*************\n\n");
        break;
      case INITIATION_COMPLETE:
        System.out.println("\n**************Initiation is complete!***************\n\n");
        break;
      case MEDIA_IN_PROGRESS:
        System.out.println(uploader.getProgress());
        break;
      case MEDIA_COMPLETE:
        System.out.println("--------------Upload is complete!----------------");
    }
    }
    
    
}