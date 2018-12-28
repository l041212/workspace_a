package com.zh.crm.Quartz;

import com.zh.crm.entity.Tools;
import com.zh.crm.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

public class NoticeQuartz {
    @Autowired
    NoticeService noticeService;

    /**
     * second(秒), minute（分）, hour（时）, day of month（日）, month（月）, day of week（周几）.
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void  expireNotice(){
        String deadline = Tools.dateToString(new Date(),"yyyy-MM-dd HH:mm:ss");
        noticeService.setExpireNotice(deadline);
    }
}
