package com.bird.cloud.common.dto;

/**
 * @program: ce-microservice
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2020-02-09 21:38
 **/
public enum ResponseCode {
    //系统相关状态码
    SYS0000("操作成功")
    ,SYS0001("系统错误")
    ,SYS0002("业务错误")
    ,SYS0003("认证失败");

    private String desc;

    ResponseCode(String desc){
        this.desc = desc;
    }

    public String getDesc(){
        return desc;
    }

    public String getCode(){
        return this.toString();
    }
}
