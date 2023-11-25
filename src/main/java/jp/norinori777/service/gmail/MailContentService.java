package jp.norinori777.service.gmail;

import java.util.List;

import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.MessagePart;
import com.google.api.services.gmail.model.MessagePartHeader;

public class MailContentService {

    public String getMailSubject(Message message) throws Exception{
        List<MessagePartHeader> headers = message.getPayload().getHeaders();
        for (MessagePartHeader header : headers) {
            if (header.getName().equals("Subject")) {
                return header.getValue();
            }
        }
        throw new Exception("件名が見つかりませんでした。");
    }

    public String getDownLoadLinkFromMailHtmlContent(Message message) throws Exception{
        MessagePart payload = message.getPayload();
        if (payload.getMimeType().equals("multipart/alternative")) {
            List<MessagePart> parts = payload.getParts();
            for (MessagePart part : parts) {
                if (part.getMimeType().equals("text/html")) {
                    String html = new String(part.getBody().decodeData());
                    org.jsoup.nodes.Document doc = org.jsoup.Jsoup.parse(html);
                    org.jsoup.select.Elements links = doc.select("a[href]");
                    for (org.jsoup.nodes.Element link : links) {
                        return link.attr("abs:href");
                    }
                }
            }
        }
        if(payload.getMimeType().equals("text/html")){
            String html = new String(payload.getBody().decodeData());
            org.jsoup.nodes.Document doc = org.jsoup.Jsoup.parse(html);
            org.jsoup.select.Elements links = doc.select("a[href]");
            for (org.jsoup.nodes.Element link : links) {
                return link.attr("abs:href");
            }
        }
        // TODO: カスタム例外を作成する
        throw new Exception("ダウンロードリンクが見つかりませんでした。");
    }
    
}