package dx.timesheet;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleRefreshTokenRequest;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.googleapis.media.MediaHttpUploaderProgressListener;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.InputStreamContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;



import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import java.io.BufferedInputStream;
import java.io.FileInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

 class Quickstart implements MediaHttpUploaderProgressListener {
    /** Application name. */
    private static final String APPLICATION_NAME =
        "Other client 1";

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
     * at ~/.credentials/drive-java-quickstart
     */
    private static final List<String> SCOPES =
        Arrays.asList(DriveScopes.DRIVE_METADATA_READONLY);

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
            Quickstart.class.getResourceAsStream("client_secret.json");
        GoogleClientSecrets clientSecrets =
            GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(DATA_STORE_FACTORY)
                .setAccessType("offline")
                .build();
        Credential credential = new AuthorizationCodeInstalledApp(
            flow, new LocalServerReceiver()).authorize("user");
        System.out.println(
                "Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
        return credential;
    }

    /**
     * Build and return an authorized Drive client service.
     * @return an authorized Drive client service
     * @throws IOException
     */
    public static Drive getDriveService() throws IOException {
        Credential credential = authorize();
        return new Drive.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }
    
     @Override
  public void progressChanged(MediaHttpUploader uploader) throws IOException {
    switch (uploader.getUploadState()) {
      case INITIATION_STARTED:
        System.out.println("Initiation has started!");
        break;
      case INITIATION_COMPLETE:
        System.out.println("Initiation is complete!");
        break;
      case MEDIA_IN_PROGRESS:
        System.out.println(uploader.getProgress());
        break;
      case MEDIA_COMPLETE:
        System.out.println("Upload is complete!");
    }
  }




    public static void main(String[] args) throws IOException {
        // Build a new authorized API client service.
        ArrayList<String> imageName = new ArrayList<String>();
        
        Drive service = getDriveService();

//         Print the names and IDs for up to 10 files.

//        FileList result;
//        result = service.files().list().setPageSize(10).setFields("nextPageToken, files(id, name)").execute();
//        List<File> files = result.getFiles();
//        
//        if (files == null || files.size() == 0) {
//            System.out.println("No files found.");
//        } else {
//            System.out.println("Files:");
//            for (File file : files) {
//                imageName.add("imageName "+file.getName());
//                imageName.add("imageId "+file.getId());
//                imageName.add(",");
//                System.out.printf("%s (%s)\n", file.getName(), file.getId());
//            }
//        }
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////      
        
//        TokenResponse response =
//          new GoogleRefreshTokenRequest(new NetHttpTransport(), new JacksonFactory(),
//              "tGzv3JOkF0XG5Qx2TlKWIA", "234511064236-3s5mmd3qganjbgb16o046lp5ba1ai38r.apps.googleusercontent.com", "SUoi4dLdCXVPTPWAnFhJ4npP").execute();
//      System.out.println("Access token: " + response.getAccessToken());
        
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////   
    
//        java.io.File mediaFile = new java.io.File("C:\\Users\\Developer\\Pictures\\Screenshots\\abc.jpg");
//        InputStreamContent mediaContent = new InputStreamContent("image/jpeg",new BufferedInputStream(new FileInputStream(mediaFile)));
//        mediaContent.setLength(mediaFile.length());
//        
//        System.out.println("media - "+mediaContent.setLength(mediaFile.length())+"  -- "+mediaFile.length());
//        
//        File file = driveService.files().create(fileMetadata, mediaContent)
//        .setFields("id")
//        .execute();

//      ///////////////////////////////////////////////////////////////////////////////////////////////////////////////     
        
        //  Drive.Files.Insert request = drive.files().insert(fileMetadata, mediaContent);
//        request.getMediaHttpUploader().setProgressListener(new CustomProgressListener());
//        request.execute();


//        MediaHttpUploader uploader = new MediaHttpUploader(mediaContent, transport, httpRequestInitializer);
//        uploader.setProgressListener(new CustomProgressListener());
//        HttpResponse response = uploader.upload(requestUrl);
//        if (response.isSuccessStatusCode()) {
//            throw GoogleJsonResponseException(jsonFactory, response);
//        }
        
   }

   
   

}