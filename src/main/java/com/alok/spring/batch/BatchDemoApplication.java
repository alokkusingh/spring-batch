package com.alok.spring.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@SpringBootApplication
public class BatchDemoApplication {

	@Autowired
	JobLauncher jobLauncher;

	@Autowired
	@Qualifier("EmployeeJob")
	Job employeeJob;

	@Autowired
	@Qualifier("StudentJob")
	Job studentJob;

	public static void main(String[] args) {
		SpringApplication.run(BatchDemoApplication.class, args);
	}


	@Scheduled(cron = "0 */1 * * * ?")
	public void performEmployeeLoad() throws Exception
	{
		JobParameters params = new JobParametersBuilder()
				.addString("JobID", String.valueOf(System.currentTimeMillis()))
				.toJobParameters();
		jobLauncher.run(employeeJob, params);
	}

	@Scheduled(cron = "0 */1 * * * ?")
	public void performStudentLoad() throws Exception
	{
		JobParameters params = new JobParametersBuilder()
				.addString("JobID", String.valueOf(System.currentTimeMillis()))
				.toJobParameters();
		jobLauncher.run(studentJob, params);
	}
}
