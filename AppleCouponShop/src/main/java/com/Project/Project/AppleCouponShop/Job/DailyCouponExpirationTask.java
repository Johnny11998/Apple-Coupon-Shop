package com.Project.Project.AppleCouponShop.Job;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableAsync //for enabling async task
@EnableScheduling //enable scheduling
public class DailyCouponExpirationTask {
    @Async
    @Scheduled(fixedRate = 30_000)
    public void sheldonZeev()  {
        System.out.println("\nBuy Penny Stocks and be RICH tomorrow !!!!");
    }

    @Async
    //0 sec , 30 min, 1 hour, day , month , time zone
    @Scheduled(cron = "0 30 1 * * ?",zone="Asia/Jerusalem")
    public void eraseCoupon(){
        System.out.println("Delete coupon");
    }
}
