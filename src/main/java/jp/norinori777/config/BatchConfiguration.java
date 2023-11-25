package jp.norinori777.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.google.api.services.gmail.model.Message;

import jp.norinori777.domains.Note.InformationToObtainDownloadFile;
import jp.norinori777.listener.GetMailAttachmentJobListener;
import jp.norinori777.listener.GetMailAttachmentStepListener;

@Configuration
public class BatchConfiguration  {

    @Autowired
    private GetMailAttachmentJobListener GetMailAttachmentJobListener;

    @Autowired
    private GetMailAttachmentStepListener GetMailAttachmentStepListener;

    @Bean
    public Job createJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("GetMailAttachmentJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(GetMailAttachmentJobListener)
                .flow(GetMailAttachmentStep(jobRepository, transactionManager))
                .end()
                .build();
    }

    @Bean
    public Step GetMailAttachmentStep(JobRepository jobRepository,  PlatformTransactionManager transactionManager) {

        return new StepBuilder("GetMailAttachmentStep", jobRepository)
                .<Message, InformationToObtainDownloadFile>chunk(1, transactionManager)
                .reader(new MailReader(GetMailAttachmentStepListener))
                .processor(new MyCustomProcessor(GetMailAttachmentStepListener))
                .writer(new MyCustomWriter(GetMailAttachmentStepListener))
                .listener(GetMailAttachmentStepListener)
                .build();
    }
}
