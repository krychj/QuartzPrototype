package com.jk;

import java.util.Date;

import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import static org.quartz.CronScheduleBuilder.*;

public class QuartzMain {

	public static void main(String[] args) throws InterruptedException {
		try {
			JobDetail job = JobBuilder.newJob(HelloJob.class)
				    .withIdentity("job1", "group1")
				    .build();
			
			
			Trigger trigger = TriggerBuilder.newTrigger()
				    .withIdentity("trigger1", "group1")
				    .startAt(new Date(System.currentTimeMillis() + 1000))
				    .build();
			
			CronTrigger trigger2 = TriggerBuilder.newTrigger()
				    .withIdentity("trigger1", "group1")
				    .withSchedule(cronSchedule("0/5 * * * * ?"))
				    .build();
	    	
	    	// Grab the Scheduler instance from the Factory
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.scheduleJob(job, trigger2);
			// and start it off
			scheduler.start();
			
			Thread.sleep(90L * 1000L);
			scheduler.shutdown();

		} catch (SchedulerException se) {
			se.printStackTrace();
		}
	}

}
