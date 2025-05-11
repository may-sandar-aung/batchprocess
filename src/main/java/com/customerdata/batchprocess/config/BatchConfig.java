package com.customerdata.batchprocess.config;

import com.customerdata.batchprocess.model.TransactionDto;
import com.customerdata.batchprocess.processor.TransactionProcessor;
import com.customerdata.batchprocess.writer.TransactionWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfig {

    @Bean
    public FlatFileItemReader<TransactionDto> reader() {
        return new FlatFileItemReaderBuilder<TransactionDto>()
                .name("transactionItemReader")
                .resource(new ClassPathResource("dataSource.txt"))
                .linesToSkip(1)
                .lineMapper(new DefaultLineMapper<>() {{
                    setLineTokenizer(new DelimitedLineTokenizer("|") {{
                        setNames("accountNumber", "trxAmount", "description", "trxDate", "trxTime", "customerId");
                    }});
                    setFieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                        setTargetType(TransactionDto.class);
                    }});
                }})
                .build();
    }

    @Bean
    public ItemProcessor<? super TransactionDto, ? extends TransactionDto> processor() {
        return new TransactionProcessor();
    }

    @Bean
    public TransactionWriter writer() {
        return new TransactionWriter();
    }

    @Bean
    public Job transactionJob(JobRepository jobRepository, Step transactionStep) {
        return new JobBuilder("transactionJob", jobRepository)
                .start(transactionStep)
                .build();
    }

    @Bean
    public Step transactionStep(JobRepository jobRepository,
                                PlatformTransactionManager transactionManager) {
        return new StepBuilder("transactionStep", jobRepository)
                .<TransactionDto, TransactionDto>chunk(10, transactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .allowStartIfComplete(true)
                .build();
    }
}
