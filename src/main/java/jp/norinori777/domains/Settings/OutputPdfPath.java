package jp.norinori777.domains.Settings;

import java.io.Serializable;

import lombok.Data;

@Data
public class OutputPdfPath implements Serializable {
    private static final long serialVersionUID = 1L;
    public String initial;
    public String path;
}