package com.rdcentermrzhi.java.demo.quartz;

import static org.quartz.DateBuilder.futureDate;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.DateBuilder.IntervalUnit;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzPriorityExample {

	private static Logger logger = LogManager.getLogger(QuartzPriorityExample.class.getName());

	public static void run() throws SchedulerException {

		// 创建 scheduler
		SchedulerFactory sf = new StdSchedulerFactory();

		Scheduler sched = sf.getScheduler();

		JobDetail job = JobBuilder.newJob(EchoJob.class).withIdentity("TriggerEchoJob").build();

		JobDetail job2 = JobBuilder.newJob(EchoJob.class).withIdentity("TriggerEchoJob2").build();

		Date startTime = futureDate(5, IntervalUnit.SECOND);

		Trigger trigger1 = newTrigger().withIdentity("Priority7 Trigger5SecondRepeat").startAt(startTime)
				.withSchedule(simpleSchedule().withRepeatCount(1).withIntervalInSeconds(5)).withPriority(7).forJob(job)
				.build();

		Trigger trigger2 = newTrigger().withIdentity("Priority5 Trigger10SecondRepeat").startAt(startTime)
				.withPriority(5).withSchedule(simpleSchedule().withRepeatCount(1).withIntervalInSeconds(5)).forJob(job2)
				.build();

		/*
		 * Trigger trigger3 = newTrigger()
		 * .withIdentity("Priority10 Trigger15SecondRepeat") .startAt(startTime)
		 * .withSchedule(simpleSchedule().withRepeatCount(1).withIntervalInSeconds(5))
		 * .withPriority(10) .forJob(job) .build();
		 */

		// Tell quartz to schedule the job using our trigger
		sched.scheduleJob(job, trigger1);
		sched.scheduleJob(job2, trigger2);
		// sched.scheduleJob(trigger3);

		sched.start();

		logger.error("------- Waiting 30 seconds... -------------");
		try {
			Thread.sleep(30L * 1000L);
			// executing...
		} catch (Exception e) {
		}

		sched.shutdown(true);

	}

	public static void runCron() throws SchedulerException {

		SchedulerFactory sf = new StdSchedulerFactory();

		Scheduler sched = sf.getScheduler();

		JobDetail job = JobBuilder.newJob(EchoJob.class).withIdentity("TriggerEchoJob").build();
		
		CronTrigger trigger = TriggerBuilder.newTrigger()
				.withIdentity("1","2").startNow()
				.withSchedule(CronScheduleBuilder.cronSchedule("0 0 0 20 * ?")).build();
		
		
		
		JobDetail job2 = JobBuilder.newJob(EchoJob.class).withIdentity("TriggerEchoJob2").build();
		
		CronTrigger trigger2 = TriggerBuilder.newTrigger()
				.withIdentity("2","2").startNow()
				.withSchedule(CronScheduleBuilder.cronSchedule("0 0 0 20 * ?")).build();
		
		
		sched.scheduleJob(job, trigger);
		sched.scheduleJob(job2, trigger2);

		sched.start();

		logger.error("------- Waiting 30 seconds... -------------");
		try {
			Thread.sleep(30L * 1000L);
			// executing...
		} catch (Exception e) {
		}

		sched.shutdown(true);
		
	}

	public static void main(String[] args) throws Exception {
		QuartzPriorityExample example = new QuartzPriorityExample();
		//example.run();
		example.runCron();
	}
}
