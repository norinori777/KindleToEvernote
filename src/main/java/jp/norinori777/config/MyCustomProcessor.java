package jp.norinori777.config;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;

import jp.norinori777.domains.Note.InformationToObtainDownloadFile;
import jp.norinori777.domains.Settings.Settings;
import jp.norinori777.listener.GetMailAttachmentStepListener;
import jp.norinori777.service.gmail.GmailService;
import jp.norinori777.service.gmail.MailContentService;

@Component
@StepScope
public class MyCustomProcessor implements ItemProcessor<Message, InformationToObtainDownloadFile> {

    @Value("#{stepExecutionContext['settings']}")
    private Settings settings;
    private GetMailAttachmentStepListener getMailAttachmentStepListener;

    @Autowired
    public MyCustomProcessor(GetMailAttachmentStepListener getMailAttachmentStepListener) {
        this.getMailAttachmentStepListener = getMailAttachmentStepListener;
    }
    

    @Override
    public InformationToObtainDownloadFile process(Message data) {
        settings = (Settings) getMailAttachmentStepListener.getStepExecution().getExecutionContext().get("settings");
        
        MailContentService mailContentService = new MailContentService();
        String subject = "";
        String downloadUrl = "";
        String userId = settings.getSettings().get("MAIL_USER_ID");

        
        try {
            GmailService gmailService = new GmailService(settings);
            Gmail service = gmailService.getService();
            // メール情報取得
            Message fullMessage = service.users().messages().get(userId, data.getId()).setFormat("full").execute();

            // 件名取得
            subject = mailContentService.getMailSubject(fullMessage);

            // ダウンロードリンク取得
            downloadUrl = mailContentService.getDownLoadLinkFromMailHtmlContent(fullMessage);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if(subject.equals("") || downloadUrl.equals("")) {
            return null;
        }
        return new InformationToObtainDownloadFile(subject, downloadUrl);
    }
}