/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dx.timesheet;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.ParentReference;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

 class DriveCommandLine_srive
{

    private static String CLIENT_ID = "234511064236-3s5mmd3qganjbgb16o046lp5ba1ai38r.apps.googleusercontent.com";
    private static String CLIENT_SECRET = "SUoi4dLdCXVPTPWAnFhJ4npP";

    private static String REDIRECT_URI = "http://localhost:8080";

    public static void main( String[] args ) throws IOException
    {
        HttpTransport httpTransport = new NetHttpTransport( );
        JsonFactory jsonFactory = new JacksonFactory( );

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder( httpTransport, jsonFactory, 
                CLIENT_ID, CLIENT_SECRET, Arrays.asList( DriveScopes.DRIVE ) ).setAccessType( "online" ).setApprovalPrompt( "auto" ).build( );

        System.out.println("DriveScopes : "  + DriveScopes.DRIVE);
        System.out.println("FLOW : "  + flow);

        String url = flow.newAuthorizationUrl( ).setRedirectUri( REDIRECT_URI ).build( );
        System.out.println( "Please open the following URL in your browser then type the authorization code:" );
        System.out.println( "  " + url );
        BufferedReader br = new BufferedReader( new InputStreamReader( System.in ) );
        String code = br.readLine( );
        System.out.println( " Code " + code );
        
        GoogleTokenResponse response = flow.newTokenRequest( code ).setRedirectUri( REDIRECT_URI ).execute( );
        System.out.println("Response - "+response);
        GoogleCredential credential = new GoogleCredential( ).setFromTokenResponse( response );

        // Create a new authorized API client
        Drive service = new Drive.Builder( httpTransport, jsonFactory, credential ).build( );

        File file=insertFile(service, "Naman", "This is a test file","" ,"image/jpeg" ,"C:\\Users\\Developer\\Pictures\\abc.jpg");
        System.out.println("NAman  -  "+file);

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

        System.out.println("File ID: " + file.getId());

        return file;
      } catch (IOException e) {
        System.out.println("An error occured: " + e);
        return null;
      }
    }

}