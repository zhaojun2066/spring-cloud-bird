package com.bird.cloud.monitor.example.cat;

import com.dianping.cat.Cat;
import org.springframework.stereotype.Component;

/**
 * @program: spring-cloud-bird
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2021-01-19 20:23
 **/
@Component
public class BusinesMertricTest {



    public void increOrderCount(){
        //用于记录一个指标值出现的次数
        Cat.logMetricForCount("create_order_count");
    }

    public void increMemberCount(){
        //用于记录一个指标值出现的次数
        Cat.logMetricForCount("register_member_count",10);
    }

    public void avg(){
        //用于记录一个指标出现的平均值
        Cat.logMetricForDuration("register_member_time_avg",10000);
    }

    public void sum(){
        //用于记录一个指标出现的平均值
        Cat.logMetricForDuration("order_gmv",(int)(Math.random()*100)+1);
    }
}
