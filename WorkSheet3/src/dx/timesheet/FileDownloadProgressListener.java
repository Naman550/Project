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
import com.google.api.client.googleapis.media.MediaHttpDownloader;
import com.google.api.client.googleapis.media.MediaHttpDownloaderProgressListener;

/**
 * The File Download Progress Listener.
 *
 * @author rmistry@google.com (Ravi)
 */
public class FileDownloadProgressListener implements MediaHttpDownloaderProgressListener {

  @Override
  public void progressChanged(MediaHttpDownloader downloader) {
    switch (downloader.getDownloadState()) {
      case MEDIA_IN_PROGRESS:
        View.header2("Download is in progress: " + downloader.getProgress());
        break;
      case MEDIA_COMPLETE:
        View.header2("Download is Complete!");
        break;
    }
  }
}