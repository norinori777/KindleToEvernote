package jp.norinori777.domains.Settings;

import java.io.Serializable;
import java.util.Map;

import lombok.Data;

@Data
public class Settings implements Serializable {
    private static final long serialVersionUID = 1L;

    public Map<String, String> settings;
    public Map<String, String> OutputPdfPaths;

}
