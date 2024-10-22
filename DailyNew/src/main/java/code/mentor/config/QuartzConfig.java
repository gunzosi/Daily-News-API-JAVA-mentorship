package code.mentor.config;

import code.mentor.service.job.FetchRssJob;
import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

@Configuration  // Đánh dấu class này là một class cấu hình Spring
public class QuartzConfig {

    // Khởi tạo và cấu hình một JobDetail cho công việc "FetchRssJob"
    @Bean  // Định nghĩa bean để quản lý JobDetail trong Spring
    public JobDetailFactoryBean fetchRssJobDetail() {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean(); // Tạo instance của JobDetailFactoryBean để cấu hình chi tiết công việc
        factoryBean.setJobClass(FetchRssJob.class); // Thiết lập class của công việc cần thực hiện là FetchRssJob
        factoryBean.setDescription("Invoke FetchRss Job Detail"); // Mô tả công việc
        factoryBean.setDurability(true); // Thiết lập JobDetail có thể tồn tại lâu dài (không bị xóa sau khi hoàn thành)
        return factoryBean;  // Trả về đối tượng JobDetail đã cấu hình
    }

    // Cấu hình một SimpleTrigger để lên lịch cho công việc "FetchRssJob"
    @Bean  // Định nghĩa bean để quản lý Trigger trong Spring
    public SimpleTriggerFactoryBean fetchRssTrigger(JobDetail fetchRssJobDetail) {
        SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean(); // Tạo instance của SimpleTriggerFactoryBean
        factoryBean.setJobDetail(fetchRssJobDetail); // Gắn JobDetail vào trigger (tức là khi trigger kích hoạt, sẽ thực hiện công việc trong JobDetail này)
        factoryBean.setRepeatInterval(60000); // Thiết lập khoảng thời gian giữa các lần thực hiện (ở đây là 60000 ms = 1 phút)
        factoryBean.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY); // Thiết lập số lần lặp là vô hạn, tức là công việc sẽ lặp mãi mãi
        factoryBean.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT); // Thiết lập chiến lược xử lý khi có lỗi kích hoạt (misfire) - sẽ tiếp tục thực hiện với các lần còn lại
        return factoryBean;  // Trả về đối tượng SimpleTrigger đã cấu hình
    }
}

