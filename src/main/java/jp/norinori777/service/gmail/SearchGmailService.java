package jp.norinori777.service.gmail;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;

@Component
public class SearchGmailService {

    // メール取得
    public List<Message> getMessagesFromToday(Gmail service, String userId, String senderEmail) throws IOException {
        LocalDateTime dateTime24HoursAgo = LocalDateTime.now().minusHours(24);
        String withIn24H = dateTime24HoursAgo.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"
        ));
        String query = "from:" + senderEmail + " after:" + withIn24H;

        ListMessagesResponse response = service.users().messages().list(userId).setQ(query).execute();
        List<Message> messages = new ArrayList<>();
        while (response.getMessages() != null) {
            messages.addAll(response.getMessages());
            if (response.getNextPageToken() != null) {
                String pageToken = response.getNextPageToken();
                response = service.users().messages().list(userId).setQ(query).setPageToken(pageToken).execute();
            } else {
                break;
            }
        }
        return messages;
    }
}