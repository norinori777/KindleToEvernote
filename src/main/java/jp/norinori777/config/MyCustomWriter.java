package jp.norinori777.config;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jp.norinori777.domains.Note.InformationToObtainDownloadFile;
import jp.norinori777.domains.Settings.Settings;
import jp.norinori777.listener.GetMailAttachmentStepListener;
import jp.norinori777.service.Web.DownloadFileService;

@Component
@StepScope
public class MyCustomWriter implements ItemWriter<InformationToObtainDownloadFile> {
    @Value("#{stepExecutionContext['settings']}")
    private Settings settings;  
    
    private GetMailAttachmentStepListener getMailAttachmentStepListener;

    @Autowired
    public MyCustomWriter(GetMailAttachmentStepListener getMailAttachmentStepListener) {
        this.getMailAttachmentStepListener = getMailAttachmentStepListener;
    }
    
    @Override
    public void write(Chunk<? extends InformationToObtainDownloadFile> chunk) {
        settings = (Settings) getMailAttachmentStepListener.getStepExecution().getExecutionContext().get("settings");
    
        DownloadFileService downloadFileService = new DownloadFileService();

        for (InformationToObtainDownloadFile data : chunk) {
            String downloadFile = data.getFilename() + ".pdf";
            String downloadUrl = data.getDownloadLink();
            String downloadPath = data.getDownloadPath(settings.getOutputPdfPaths()); 
            // ファイルダウンロード処理
            downloadFileService.downloadFile(downloadUrl, downloadFile, downloadPath);
            System.out.println("MyCustomWriter    : Writing data    : " + data.getSubject());
        }
        System.out.println("MyCustomWriter    : Writing data    : completed");
    }
}