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
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.googleapis.media.MediaHttpUploaderProgressListener;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.InputStreamContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.ParentReference;
import java.io.BufferedInputStream;
import java.io.FileInputStream;


import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class Final2 implements MediaHttpUploaderProgressListener
{

    
    //Note: this is the mail from a service account in my dev console, it is different from the OAuth client I use in the previous method.**
    private static final String SERVICE_ACCOUNT_EMAIL ="poetic-genius-149312@appspot.gserviceaccount.com";

    /** Application name. */
    private static final String APPLICATION_NAME = "Other client 1";

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    public static void main( String[] args ) throws Exception
    {
        //Drive service = getServiceManual();
        Drive service = initializeDrive();
        
       
        //*******************Media Http Uploader**************************/
         /* File body = new File();
            body.setTitle("abc.txt");
             body.setDescription("hello");
             body.setMimeType("text/plain");
            
            
            java.io.File mediaFile = new java.io.File("C:\\Users\\Developer\\Pictures\\abc.txt");
            InputStreamContent mediaContent = new InputStreamContent("text/plain", new BufferedInputStream(new FileInputStream(mediaFile)));
            mediaContent.setLength(mediaFile.length());
        
            Drive.Files.Insert request = service.files().insert(body, mediaContent);
            System.out.println("nmn -- "+request.getMediaHttpUploader().setProgressListener(new Final1()));
            try{
            request.getMediaHttpUploader().setProgressListener(new Final1());
            request.execute();
            } catch(Exception e){
                System.out.println("Exception - "+e);
            }*/
        
        //insertFile(service, "Naman", "This is a test file","" , "text/plain", "./data/document.txt");
        
        File file= insertFile(service, "Naman", "This is a testing file","" , "image/jpeg", "C:\\Users\\Developer\\Pictures\\abc.jpg");
        System.out.println("Naman - "+file);
      }

   
    private static File insertFile(Drive service, String title, String description,
        String parentId, String mimeType, String filename) {
      // File's metadata.
      File body = new File();
      body.setTitle(title);
      body.setDescription(description);
      body.setMimeType(mimeType);

      // Set the parent folder.
      if (parentId != null && parentId.length() > 0) {
        body.setParents(
            Arrays.asList(new ParentReference().setId(parentId)));
      }

      // File's content.
      java.io.File fileContent = new java.io.File(filename);
      FileContent mediaContent = new FileContent(mimeType, fileContent);
      try {
          
        File file = service.files().insert(body, mediaContent).execute();

        // Uncomment the following line to print the File ID.
        System.out.println("File ID: " + file.getId());
        System.out.println("File Name: " + file.getTitle());

        return file;
      } catch (IOException e) {
        System.out.println("An error occured: " + e);
        return null;
      }
    }


    /////////////////////
    ///NEW GOOGLE ANALYTICS AUTH

    public static java.io.File convIs2File(InputStream inputStream, java.io.File file)
    {
        java.io.OutputStream outputStream = null;

        try {
            System.out.println(inputStream+"  "+file);
            // write the inputStream to a FileOutputStream
            outputStream = new java.io.FileOutputStream(file);

            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }

            System.out.println("Done!");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    // outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
        return file;
    }
    private static Drive initializeDrive() throws Exception {
        // Initializes an authorized analytics service object.

        // Construct a GoogleCredential object with the service account email
        // and p12 file downloaded from the developer console.
        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        InputStream is =  FinalUpload.class.getResourceAsStream("Key.p12");
        java.io.File f = java.io.File.createTempFile("dkey", ".p12");
        java.io.File f_used = convIs2File(is,f);
        GoogleCredential credential = new GoogleCredential.Builder()
                .setTransport(httpTransport)
                .setJsonFactory(JSON_FACTORY)
                .setServiceAccountId(SERVICE_ACCOUNT_EMAIL)
                .setServiceAccountPrivateKeyFromP12File(f_used)
                .setServiceAccountScopes(DriveScopes.all())
                .build();

        // Construct the Analytics service object.
        return new Drive.Builder(httpTransport, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME).build();
    }
    
    ////////////////////////////////////////////////////Media
    
    @Override
    public void progressChanged(MediaHttpUploader uploader) throws IOException {
        switch (uploader.getUploadState()) {
      case INITIATION_STARTED:
        System.out.println("\nInitiation has started!\n\n");
        break;
      case INITIATION_COMPLETE:
        System.out.println("\nInitiation is complete!\n\n");
        break;
      case MEDIA_IN_PROGRESS:
        System.out.println(uploader.getProgress());
        break;
      case MEDIA_COMPLETE:
        System.out.println("--------------Upload is complete!----------------");
    }
    }


}