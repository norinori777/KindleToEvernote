package jp.norinori777.domains.Note;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InformationToObtainDownloadFile {
    private String subject;
    private String downloadLink;
    
    // Evernoteから送付されるノートの件名が「～」がノート名になっているので、それをノート名として取得する
    public String getNoteName() {
        int startIndex = subject.indexOf("「") + 1;
        int endIndex = subject.indexOf("」");
        if (startIndex >= 0 && endIndex >= 0 && endIndex > startIndex) {
            return subject.substring(startIndex, endIndex);
        }
        return null;
    }

    public String getFilename() {
        String noteName = getNoteName();
        return isFirstLetterAndSecondPriod(noteName) ? noteName.substring(2) : noteName;
    }


    public String getDownloadPath(Map<String, String> downloadPaths) {
        String noteName = getNoteName();
        String defaultPath = downloadPaths.get("0");
        String downloadPath = downloadPaths.get(noteName.substring(0, 1));
        if (downloadPath == null) {
            return defaultPath;
        } else {
            return downloadPath;
        }
    }

    private boolean isFirstLetterAndSecondPriod(String str) {
        if (str == null || str.length() < 2) {
            return false;
        }
    
        char firstChar = str.charAt(0);
        char secondChar = str.charAt(1);
    
        return Character.isAlphabetic(firstChar) && secondChar == '.';
    }
}

