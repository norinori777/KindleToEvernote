package jp.norinori777.config;

import java.util.Iterator;
import java.util.List;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;

import jp.norinori777.domains.Settings.Settings;
import jp.norinori777.listener.GetMailAttachmentStepListener;
import jp.norinori777.service.gmail.GmailService;
import jp.norinori777.service.gmail.SearchGmailService;

@Component
@StepScope
public class MailReader implements ItemReader<Message> {
    private Iterator<Message> messageIterator;
    // private GetMailAttachmentStepListener getMailAttachmentStepListener;

    private Settings settings;
    private GetMailAttachmentStepListener getMailAttachmentStepListener;

    @Autowired
    public MailReader(GetMailAttachmentStepListener getMailAttachmentStepListener) {
        this.getMailAttachmentStepListener = getMailAttachmentStepListener;
    }
    
    @Override
    public Message read() throws Exception {
        settings = (Settings) getMailAttachmentStepListener.getStepExecution().getExecutionContext().get("settings");

        String userId = settings.getSettings().get("MAIL_USER_ID");
        String senderEmail = settings.getSettings().get("SENDER_EMAIL");
        GmailService gmailService = new GmailService(settings);
        Gmail service = gmailService.getService();

        if(messageIterator == null) {
            SearchGmailService searchGmailService = new SearchGmailService();
            List<Message> messages = searchGmailService.getMessagesFromToday(service, userId, senderEmail);
            messageIterator = messages.iterator();
        }
        if(messageIterator.hasNext()) {
            return messageIterator.next();
        } else {
            return null;
        }
    }
}