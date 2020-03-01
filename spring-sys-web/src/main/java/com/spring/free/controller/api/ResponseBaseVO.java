package com.spring.free.controller.api;

public class ResponseBaseVO {

    private String result;

    private String desc;

    public ResponseBaseVO(){
        this.result = "00";
        this.desc = "成功";
    }

    public ResponseBaseVO(String result, String desc){
        this.result = result;
        this.desc = desc;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
