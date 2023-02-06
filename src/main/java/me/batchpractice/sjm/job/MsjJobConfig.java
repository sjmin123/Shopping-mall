package me.batchpractice.sjm.job;

import lombok.RequiredArgsConstructor;
import me.batchpractice.sjm.dto.request.DailyPaymentBillRequest;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class MsjJobConfig {

    PlatformTransactionManager platformTransactionManager;
    private final DataSource dataSource;
    private final int chunkSize = 10;

    @Bean
    public Job payInfoJob(JobRepository jobRepository) {
        //JobBuilder 로 바뀜. factory 못씀.
        return new JobBuilder("payInfoJob", jobRepository)
                .start(step(jobRepository, platformTransactionManager))
                .build();
    }

    @Bean
    @JobScope
    public Step step(JobRepository jobRepository,
                     PlatformTransactionManager platformTransactionManager) {
        //StepBuilder 로 바뀜. factory 못씀.
        return new StepBuilder("payInfoStep", jobRepository)
                .<DailyPaymentBillRequest, DailyPaymentBillRequest>chunk(chunkSize, platformTransactionManager)
                .reader(reader())
                .writer(writer())
                .build();
    }

    @Bean
    @StepScope
    public JdbcCursorItemReader<DailyPaymentBillRequest> reader() {
        return new JdbcCursorItemReaderBuilder<DailyPaymentBillRequest>()
                //chunkSize 와 같게하자.
                .fetchSize(chunkSize)
                .dataSource(dataSource)
                .name("reader")
                //rowMapper 를 이용하여 원하는 객체 형식으로 반환할 수 있음
                .rowMapper(new BeanPropertyRowMapper<>(DailyPaymentBillRequest.class))
                .sql("select users.id, sum(a.price)daily_payment from (select pay_info.pay_user, product.price \n" +
                        "from pay_info left join product on pay_info.product_id = product.id) a \n" +
                        "right join users on a.pay_user=users.id group by users.id;")
                .build();
    }

    @Bean
    @StepScope
    public ItemWriter<DailyPaymentBillRequest> writer() {
        List<DailyPaymentBillRequest> req = new ArrayList<>();
        //list 가 chunk 형이기 때문에 리스트를 따로 만들어 foreach 로 하나하나 넣어서 그 객체를 반환함.
        return list -> {
            list.forEach(r -> req.add(r));
            new RestTemplate().postForObject("http://localhost:8080/api/batch/mail", req, Object.class);
        };
    }

}
