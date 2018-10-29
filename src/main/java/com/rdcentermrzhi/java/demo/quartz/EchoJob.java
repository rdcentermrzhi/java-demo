package com.rdcentermrzhi.java.demo.quartz;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class EchoJob implements Job {
	private static Logger logger = LogManager.getLogger(EchoJob.class.getName());

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		
		//System.out.println("-----------------------------");
		logger.error("echo " + arg0 +System.currentTimeMillis() );
	}

}
