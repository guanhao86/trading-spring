package com.spring.free.common.service;/**
 * Created by hengpu on 2019/3/26.
 */

import com.spring.free.common.util.HttpResultEntity;
import com.spring.free.common.util.WeChatLoginUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;


/**
 * @ClassName MicroMsgService
 * @Description //TODO
 * @Author hengpu
 * @Date 2019/3/26 9:50
 * @Version 1.0
 **/
@Slf4j
@Service
public class MicroMsgService {

    /**微信登录**/
    public JSONObject sub_login_wechat(String code, HttpServletRequest request, HttpServletResponse response){
        JSONObject obj = new JSONObject();
        String secret = "ac690e1af5d48c9835a6e068d7582d6d";
        String appid = "wxf85a031b9b102e68";
        //好的
        HttpResultEntity hre = null;
        try {
            hre = WeChatLoginUtil.getWechatUserInfo(appid, secret, code);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            obj.put("code","1");
            obj.put("desc",e.getMessage());
            return obj;
        }

        if(hre==null||!hre.getSuccess()){
            obj.put("code","1");
            obj.put("desc",hre.getMsg());
            return obj;
        }else{

            JSONObject json=hre.getData();
            String unionid=json.getString("unionid");
            json.put("code","0");
            json.put("desc",hre.getMsg());

            return json;

        }
    }


}
