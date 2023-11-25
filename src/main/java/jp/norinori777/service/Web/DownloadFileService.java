package jp.norinori777.service.Web;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class DownloadFileService {
    public void downloadFile(String downloadUrl, String filename, String downloadPath){
        FileOutputStream fos;
        try{
            URL url = new URL(downloadUrl);
            InputStream in = url.openStream();
            ReadableByteChannel rbc = Channels.newChannel(in);
            fos = new FileOutputStream(downloadPath + filename);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}