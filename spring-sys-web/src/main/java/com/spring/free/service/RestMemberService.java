package com.spring.free.service;

import com.alibaba.fastjson.JSONObject;
import com.spring.fee.model.TWheatAccount;
import com.spring.fee.model.TWheatMember;
import com.spring.fee.service.ITWheatAccountBusiSV;
import com.spring.fee.service.ITWheatMemberBusiSV;
import com.spring.free.common.service.AsyncRestService;
import com.spring.free.common.service.SendSmsService;
import com.spring.free.domain.MemberExtend;
import com.spring.free.manager.MemberExtendManager;
import com.spring.free.util.exception.ExceptionCodeEnum;
import com.spring.free.util.exception.RestException;
import com.spring.free.util.md5.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class RestMemberService {

    @Autowired
    private ITWheatAccountBusiSV twheatAccountBusiSV;
    @Autowired
    private ITWheatMemberBusiSV twheatMemberBusiSV;
    @Autowired
    private SendSmsService sendSmsService;

    @Autowired
    private MemberExtendManager memberExtendManager;

    @Autowired
    private AsyncRestService asyncRestService;

    public JSONObject register(String phone, String smscode, String referenceId , String uuid) {
        //返回体
        JSONObject jsonObj=new JSONObject();


        if(!sendSmsService.validate(uuid,smscode)){
            //输入的手机验证码不正确！
            jsonObj.put("result","01");
            jsonObj.put("desc","输入的手机验证码不正确！");
            return jsonObj;
        }

        TWheatMember  checkMember = twheatMemberBusiSV.selectByPhone(phone);
        if(checkMember!=null){
            jsonObj.put("result","01");
            jsonObj.put("desc","手机号已注册！");
            return jsonObj;
        }
        TWheatMember  referenceIdMember = twheatMemberBusiSV.selectByMemberId(referenceId);

        if(referenceIdMember==null){
            jsonObj.put("result","01");
            jsonObj.put("desc","推荐人不存在！");
            return jsonObj;
        }

        TWheatMember member = new TWheatMember();
        member.setPhone(phone);
        member.setPassword(Md5Util.md5Hex(phone.substring(phone.length()-6,phone.length())));
        member.setReferenceId(referenceId);
        member.setCreateTime(new Date());
        member.setModifyTime(new Date());
        member.setDelFlag(0);
        member.setIsAut("0");
        member.setLevel("1");
        member = twheatMemberBusiSV.insert(member);
        member.setMemberId("010"+String.format("%08d", member.getId()));
        member = twheatMemberBusiSV.update(member);

        TWheatAccount account = new TWheatAccount();
        account.setMemberId(member.getMemberId());
        account.setTotal(0l);
        account.setAvailable(0l);
        account.setFreeze(0l);
        account.setMoneyFreeze(0l);
        account.setGranaryFreeze(0l);
        account.setGranaryIngFreeze(0l);
        account.setGranaryIngMaxFreeze(0l);

        twheatAccountBusiSV.insert(account);

        JSONObject responseJson = asyncRestService.createAddress(member.getMemberId());
        if(!responseJson.getBoolean("success")){
            throw new RestException(ExceptionCodeEnum.RESTMEM_ERROR_CODE.getCode(), ExceptionCodeEnum.RESTMEM_ERROR_CODE.getMsg()+":生成钱包地址失败！");
        }
        String msg = "恭喜您注册成功，您的会员编号为："+member.getMemberId()+"，默认登录密码为注册手机号码后6位，请登录后自行修改密码";
        sendSmsService.sendSmd(phone,msg);


//        jsonObj.put("member",member);
        jsonObj.put("result","00");
        jsonObj.put("desc","操作成功！");
        return jsonObj;

    }

    public JSONObject login( Integer type,String memberId,String password,String phone,String uuid,String smscode){
        //返回体
        JSONObject jsonObj=new JSONObject();

        System.out.println("type"+type+"----------memberId"+memberId+"-----------phone"+"-----------password"+password);

        if(type.intValue()==1){
            System.out.println("type"+type+"----------memberId"+memberId+"-----------phone"+"-----------password"+password);
            if(!sendSmsService.validate(uuid,smscode)){
                //输入的手机验证码不正确！
                jsonObj.put("result","01");
                jsonObj.put("desc","输入的手机验证码不正确！");
                return jsonObj;
            }

            TWheatMember  member = twheatMemberBusiSV.selectByPhone(phone);


            if(member!=null){
                TWheatAccount account = twheatAccountBusiSV.selectByMember(member.getMemberId());

                if (account!=null){
                    jsonObj.put("account",account);
                }
                jsonObj.put("member",member);
                jsonObj.put("result","00");
                jsonObj.put("desc","操作成功！");
                return jsonObj;
            }else{
                jsonObj.put("result","01");
                jsonObj.put("desc","用户不存在！");
                return jsonObj;
            }


        }else if(type.intValue()==2){
            System.out.println("type"+type+"----------memberId"+memberId+"-----------phone"+"-----------password"+password);
            TWheatMember  member = twheatMemberBusiSV.selectByMemberId(memberId);

            if(member!=null){

                if(member.getPassword().equals(Md5Util.md5Hex(password))){


                    TWheatAccount account = twheatAccountBusiSV.selectByMember(member.getMemberId());

                    if (account!=null){
                        jsonObj.put("account",account);
                    }
                    jsonObj.put("member",member);
                    jsonObj.put("result","00");
                    jsonObj.put("desc","操作成功！");
                    return jsonObj;
                }else{
                    jsonObj.put("result","01");
                    jsonObj.put("desc","密码不正确！");
                    return jsonObj;
                }


            }else{
                jsonObj.put("result","01");
                jsonObj.put("desc","用户不存在！");
                return jsonObj;
            }
        }else{
            System.out.println("type"+type+"----------memberId"+memberId+"-----------phone"+"-----------password"+password);

            TWheatMember  member = twheatMemberBusiSV.selectByPhone(memberId);


            if(member!=null){
                if(member.getPassword().equals(Md5Util.md5Hex(password))){


                    TWheatAccount account = twheatAccountBusiSV.selectByMember(member.getMemberId());

                    if (account!=null){
                        jsonObj.put("account",account);
                    }
                    jsonObj.put("member",member);
                    jsonObj.put("result","00");
                    jsonObj.put("desc","操作成功！");
                    return jsonObj;
                }else{
                    jsonObj.put("result","01");
                    jsonObj.put("desc","密码不正确！");
                    return jsonObj;
                }
            }else{
                jsonObj.put("result","01");
                jsonObj.put("desc","用户不存在！");
                return jsonObj;
            }

        }
    }

    public JSONObject oauth( String memberId, String realName, String idCard, String imgFront,
                            String imgBack, String bankCard, String bankName, String uuid, String smscode){
        //返回体
        JSONObject jsonObj=new JSONObject();

        if(!sendSmsService.validate(uuid,smscode)){
            //输入的手机验证码不正确！
            jsonObj.put("result","01");
            jsonObj.put("desc","输入的手机验证码不正确！");
            return jsonObj;
        }

        TWheatMember  member = twheatMemberBusiSV.selectByMemberId(memberId);
        if(member!=null){
            member.setRealName(realName);
            member.setIdCard(idCard);
            member.setImgFront(imgFront);
            member.setImgBack(imgBack);
            member.setBankCard(bankCard);
            member.setBankName(bankName);
            member.setIsAut("1");
            member.setModifyTime(new Date());
            member.setName(realName);
            twheatMemberBusiSV.update(member);

            jsonObj.put("result","00");
            jsonObj.put("desc","操作成功！");
            return jsonObj;
        }else{
            jsonObj.put("result","01");
            jsonObj.put("desc","用户不存在！");
            return jsonObj;
        }

    }

    public JSONObject updatePwd( String memberId, String newPwd, String uuid, String smscode){
        //返回体
        JSONObject jsonObj=new JSONObject();

        if(!sendSmsService.validate(uuid,smscode)){
            //输入的手机验证码不正确！
            jsonObj.put("result","01");
            jsonObj.put("desc","输入的手机验证码不正确！");
            return jsonObj;
        }

        TWheatMember  member = twheatMemberBusiSV.selectByMemberId(memberId);
        if(member!=null){
            member.setPassword(Md5Util.md5Hex(newPwd));
            member.setModifyTime(new Date());
            twheatMemberBusiSV.update(member);


            jsonObj.put("result","00");
            jsonObj.put("desc","操作成功！");
            return jsonObj;
        }else{
            jsonObj.put("result","01");
            jsonObj.put("desc","用户不存在！");
            return jsonObj;
        }

    }

    public JSONObject getAddress( String memberId){
        //返回体
        JSONObject jsonObj=new JSONObject();

        MemberExtend memberExtend = new MemberExtend();
        memberExtend.setMemberId(memberId);
        List<MemberExtend> list =  memberExtendManager.findList(memberExtend);

        if(list!=null&&list.size()>0){
            jsonObj.put("addressInfo",list.get(0));
            jsonObj.put("result","00");
            jsonObj.put("desc","操作成功！");
            return jsonObj;
        }else{
            jsonObj.put("result","01");
            jsonObj.put("desc","用户不存在！");
            return jsonObj;
        }


    }

    public JSONObject updateMemberInfo( String memberId,String image){
        //返回体
        JSONObject jsonObj=new JSONObject();

        TWheatMember  member = twheatMemberBusiSV.selectByMemberId(memberId);
        if(member!=null){
            member.setExpand5(image);
            twheatMemberBusiSV.update(member);

            jsonObj.put("result","00");
            jsonObj.put("desc","操作成功！");
            return jsonObj;
        }else{
            jsonObj.put("result","01");
            jsonObj.put("desc","用户不存在！");
            return jsonObj;
        }

    }

    public JSONObject updateMemberName( String memberId,String name){
        //返回体
        JSONObject jsonObj=new JSONObject();

        TWheatMember  member = twheatMemberBusiSV.selectByMemberId(memberId);
        if(member!=null){
            member.setName(name);
            twheatMemberBusiSV.update(member);

            jsonObj.put("result","00");
            jsonObj.put("desc","操作成功！");
            return jsonObj;
        }else{
            jsonObj.put("result","01");
            jsonObj.put("desc","用户不存在！");
            return jsonObj;
        }

    }

    public JSONObject getMemberInfo( String memberId){
        //返回体
        JSONObject jsonObj=new JSONObject();

        TWheatMember  member = twheatMemberBusiSV.selectByMemberId(memberId);
        if(member!=null){
            TWheatAccount account = twheatAccountBusiSV.selectByMember(member.getMemberId());

            if (account!=null){
                jsonObj.put("account",account);
            }
            jsonObj.put("member",member);
            jsonObj.put("result","00");
            jsonObj.put("desc","操作成功！");
            return jsonObj;
        }else{
            jsonObj.put("result","01");
            jsonObj.put("desc","用户不存在！");
            return jsonObj;
        }

    }
}
