package com.zh.crm.service.impl;

import com.zh.crm.entity.Tools;
import com.zh.crm.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

public class ScheduledService {

    @Autowired
    NoticeService noticeService;

    /**
     * second(秒), minute（分）, hour（时）, day of month（日）, month（月）, day of week（周几）.
     * 0 * * * * MON-FRI
     *  【0 0/5 14,18 * * ?】 每天14点整，和18点整，每隔5分钟执行一次
     *  【0 15 10 ? * 1-6】 每个月的周一至周六10:15分执行一次
     *  【0 0 2 ? * 6L】每个月的最后一个周六凌晨2点执行一次
     *  【0 0 2 LW * ?】每个月的最后一个工作日凌晨2点执行一次
     *  【0 0 2-4 ? * 1#1】每个月的第一个周一凌晨2点到4点期间，每个整点都执行一次；
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void  expireNotice(){
        String deadline = Tools.dateToString(new Date(),"yyyy-MM-dd HH:mm:ss");
        noticeService.setExpireNotice(deadline);
    }
}
