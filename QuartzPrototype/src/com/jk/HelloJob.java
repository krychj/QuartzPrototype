package com.jk;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;

@DisallowConcurrentExecution
public class HelloJob implements Job {
	String jobSays;
	float myFloatValue;
	List<Date> state = new ArrayList<Date>();

	Stage inputDataStage;
	Stage processingDataStage;
	Stage outputDataStage;

	public HelloJob() {
		setJobSays("Job 123 ...");
		setMyFloatValue(3.14f);
		setState(new Date());
		
		inputDataStage = new CollectInput();
		processingDataStage = new ProcessData();
		outputDataStage = new SaveData();		
	}

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException{
		System.out.println("Hello Quartz!");
		JobKey jobKey = context.getJobDetail().getKey();
		JobDataMap dataMap = context.getMergedJobDataMap();
		String[] keys = dataMap.getKeys();
		for(String key : keys) {
			String value = (String) dataMap.get(key);
			System.out.println("Key: " + key + " value: " + value);
		}
		

		System.err.println("Instance " + jobKey + " of HelloJob says: " + jobSays
				+ ", and val is: " + myFloatValue);

		inputDataStage.execute();
		processingDataStage.execute();
		outputDataStage.execute();
		/*System.out.println("");
		try {
			Thread.sleep(20L * 1000L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

	public void setJobSays(String jobSays) {
		this.jobSays = jobSays;
	}

	public void setMyFloatValue(float myFloatValue) {
		this.myFloatValue = myFloatValue;
	}

	public void setState(Date date) {
		state.add(date);
	}
}
