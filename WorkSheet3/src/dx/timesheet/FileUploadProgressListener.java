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
import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.googleapis.media.MediaHttpUploaderProgressListener;

import java.io.IOException;
import java.text.NumberFormat;

/**
 * The File Upload Progress Listener.
 *
 * @author rmistry@google.com (Ravi)
 */
public class FileUploadProgressListener implements MediaHttpUploaderProgressListener {

  @Override
  public void progressChanged(MediaHttpUploader uploader) throws IOException {
    switch (uploader.getUploadState()) {
      case INITIATION_STARTED:
        View.header2("Upload Initiation has started.");
        break;
      case INITIATION_COMPLETE:
        View.header2("Upload Initiation is Complete.");
        break;
      case MEDIA_IN_PROGRESS:
        View.header2("Upload is In Progress: "
            + NumberFormat.getPercentInstance().format(uploader.getProgress()));
        break;
      case MEDIA_COMPLETE:
        View.header2("Upload is Complete!");
        break;
    }
  }
}