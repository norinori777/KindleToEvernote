package jp.norinori777.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class GetMailAttachmentJobListener implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {
        System.out.println("GetMailAttachmentJobListener : beforeJob");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        System.out.println("GetMailAttachmentJobListener : afterJob");
    }
}