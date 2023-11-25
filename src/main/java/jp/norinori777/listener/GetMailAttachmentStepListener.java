package jp.norinori777.listener;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.norinori777.domains.Settings.Settings;
import jp.norinori777.service.Settings.SettingsService;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class GetMailAttachmentStepListener implements StepExecutionListener {
    Settings settings;
    StepExecution stepExecution;

    @Autowired
    private SettingsService settingsService;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
        System.out.println("GetMailAttachmentStepListener : beforeStep");
        
        try {
            settings = new Settings();
            settings.setSettings(settingsService.getSettings());
            settings.setOutputPdfPaths(settingsService.getOutputPdfPaths());
        } catch (Exception e) {
            log.error("GetMailAttachmentJobListener : beforeJob : error : " + e.getMessage());
            throw new RuntimeException("GetMailAttachmentJobListener : beforeJob : error : " + e.getMessage());
        }
        ExecutionContext context = stepExecution.getExecutionContext();
        context.put("settings", settings);
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        System.out.println("GetMailAttachmentStepListener : afterStep");

        return stepExecution.getExitStatus();
    }
    public StepExecution getStepExecution() {
        return stepExecution;
    }
}