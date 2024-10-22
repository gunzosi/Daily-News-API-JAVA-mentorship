package code.mentor.service.job;

import code.mentor.service.RssFeedServiceImpl;
import io.micrometer.common.lang.NonNullApi;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Component
public class FetchRssJob extends QuartzJobBean implements Job {
    private final RssFeedServiceImpl rssFeedService;

    @Autowired
    public FetchRssJob(RssFeedServiceImpl rssFeedService) {
        this.rssFeedService = rssFeedService;
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        rssFeedService.scheduledRssFeedUpdate();
    }
}
