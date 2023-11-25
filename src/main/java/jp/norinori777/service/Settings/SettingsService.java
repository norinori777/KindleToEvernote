package jp.norinori777.service.Settings;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.norinori777.domains.Settings.OutputPdfPath;
import jp.norinori777.domains.Settings.Setting;
import jp.norinori777.infrastructure.datasource.OutputPdfPathsMapper;
import jp.norinori777.infrastructure.datasource.SettingsMapper;

@Service
public class SettingsService {
    @Autowired
    SettingsMapper settingsMapper;
    @Autowired
    OutputPdfPathsMapper outputPdfPathsMapper;

    public Map<String, String> getSettings(){
        List<Setting> listSettings = settingsMapper.selectSettings();
        Map<String, String> settingsMap = new HashMap<String, String>();
        listSettings.forEach(setting -> {
            settingsMap.put(setting.getName(), setting.getValue());
        });
        return settingsMap;
    }

    public Map<String, String> getOutputPdfPaths(){
        List<OutputPdfPath> listOutputPdfPaths = outputPdfPathsMapper.selectOutputPdfPaths();
        Map<String, String> outputPdfPathsMap = new HashMap<String, String>();
        listOutputPdfPaths.forEach(outputPdfPath -> {
            outputPdfPathsMap.put(outputPdfPath.getInitial(), outputPdfPath.getPath());
        }); 
        return outputPdfPathsMap;
    }

}