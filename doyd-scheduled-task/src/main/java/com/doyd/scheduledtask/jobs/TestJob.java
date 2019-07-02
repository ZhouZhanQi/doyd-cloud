package com.doyd.scheduledtask.jobs;

//import com.doyd.core.util.JacksonUtils;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author zhouzq
 * @date 2019/6/26
 * @desc 测试任务
 */
@Slf4j
public class TestJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
//        log.debug("data map:{}", JacksonUtils.pojoTojson(dataMap));
    }
}
