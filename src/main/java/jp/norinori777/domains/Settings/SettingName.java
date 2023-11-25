package jp.norinori777.domains.Settings;


public enum SettingName {
    APPLICATION_NAME("APPLICATION_NAME"),
    TOKENS_DIRECTORY_PATH("TOKENS_DIRECTORY_PATH"),
    CREDENTIAL_JSON("CREDENTIAL_JSON"),
    MAIL_USER_ID("MAIL_USER_ID"),
    SENDER_EMAIL("SENDER_EMAIL");
    
    private final String text;

    SettingName(String text) {
        this.text = text;
    }
    public String getText(){
        return this.text;
    }
}