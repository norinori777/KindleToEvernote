package jp.norinori777.Service.Settings;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import jp.norinori777.service.Settings.SettingsService;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;


@Sql("SettingsServiceTest.sql")
@Transactional
@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class SettingsServiceTest {
    
    @Autowired
    private SettingsService settingsService;
    
    @Test
    public void 設定情報が取得できること() {
        Map<String, String> settingsMap = settingsService.getSettings();
        assertEquals("value1", settingsMap.get("setting1"));
        assertEquals("value2", settingsMap.get("setting2"));
        assertEquals("value3", settingsMap.get("setting3"));
    }

    @Test
    public void 出力先情報が取得できること() {
        Map<String, String> outputPdfPathsMap = settingsService.getOutputPdfPaths();
        assertEquals("/path/to/fileA", outputPdfPathsMap.get("A"));
        assertEquals("/path/to/fileB", outputPdfPathsMap.get("B"));
        assertEquals("/path/to/fileC", outputPdfPathsMap.get("C"));
    }
}