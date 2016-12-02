
package dx.timesheet;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.ParentReference;


import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class DriveCommandLine2
{

    private static final String KEY_FILE_LOCATION = "myp12Key.p12";
    //Note: this is the mail from a service account in my dev console, it is different from the OAuth client I use in the previous method.**
    private static final String SERVICE_ACCOUNT_EMAIL ="test-825@poetic-genius-149312.iam.gserviceaccount.com";

    /** Application name. */
    private static final String APPLICATION_NAME =
        "Drive API Java Quickstart";

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY =
        JacksonFactory.getDefaultInstance();

    public static void main( String[] args ) throws Exception
    {
        //Drive service = getServiceManual();
        Drive service = initializeDrive();
        //insertFile(service, "Test File Drive", "This is a test file","myCompanyFolderID" , "text/plain", "./data/document.txt");
        insertFile(service, "Test File Drive", "This is a test file","" , "text/plain", "C:\\Users\\Developer\\Pictures\\abc.jpg");
      }

    /**
     * Insert new file.
     *
     * @param service Drive API service instance.
     * @param title Title of the file to insert, including the extension.
     * @param description Description of the file to insert.
     * @param parentId Optional parent folder's ID.
     * @param mimeType MIME type of the file to insert.
     * @param filename Filename of the file to insert.
     * @return Inserted file metadata if successful, {@code null} otherwise.
     */
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
        InputStream is = DriveCommandLine2.class.getClassLoader().getResourceAsStream(KEY_FILE_LOCATION);
        java.io.File f = java.io.File.createTempFile("myP12Key", ".p12");
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


}