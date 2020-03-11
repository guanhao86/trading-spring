package com.spring.free.controller.api;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.spring.fee.model.*;
import com.spring.fee.service.ITWheatMemberBusiSV;
import com.spring.fee.service.ITableMemberBusiSV;
import com.spring.free.common.domain.AccessResponse;
import com.spring.free.service.RestMemberService;
import com.spring.free.util.DateUtils;
import com.spring.free.utils.velocity.DictUtils;
import com.spring.free.vo.MemberReqVO;
import com.spring.free.vo.TreeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api/member")
@Controller
public class RestMemberController {


    @Autowired
    private RestMemberService restMemberService;

    @Autowired
    private ITWheatMemberBusiSV itWheatMemberBusiSV;
    @Autowired
    private ITWheatMemberBusiSV twheatMemberBusiSV;

    @Autowired
    ITableMemberBusiSV iTableMemberBusiSV;

    @RequestMapping(value = "/register/{smscode}")
    public @ResponseBody AccessResponse register(@PathVariable String smscode,String uuid,String phone,String referenceId){
        //返回体
        JSONObject jsonObj=new JSONObject();
        jsonObj = restMemberService.register(phone,smscode,referenceId,uuid);

        return AccessResponse.builder().data(jsonObj).success(true).rspcode(200).message("服务端处理请求成功。").build();
    }

    @RequestMapping(value = "/login/{type}")
    public @ResponseBody AccessResponse login(@PathVariable Integer type,String memberId,String password,String phone,String uuid,String smscode){
        //返回体
        JSONObject jsonObj=new JSONObject();
        jsonObj = restMemberService.login( type,memberId,password,phone,uuid,smscode);

        return AccessResponse.builder().data(jsonObj).success(true).rspcode(200).message("服务端处理请求成功。").build();
    }

    @RequestMapping(value = "/oauth/{memberId}")
    public @ResponseBody AccessResponse oauth(@PathVariable String memberId,String realName,String idCard,String imgFront,
                                              String imgBack,String bankCard,String bankName,String uuid,String smscode){
        //返回体
        JSONObject jsonObj=new JSONObject();
        jsonObj = restMemberService.oauth(memberId,realName,idCard,imgFront,
                imgBack,bankCard,bankName,uuid,smscode);

        return AccessResponse.builder().data(jsonObj).success(true).rspcode(200).message("服务端处理请求成功。").build();
    }

    @RequestMapping(value = "/updatePwd/{memberId}")
    public @ResponseBody AccessResponse updatePwd(@PathVariable String memberId,String newPwd,String uuid,String smscode){
        //返回体
        JSONObject jsonObj=new JSONObject();
        jsonObj = restMemberService.updatePwd(memberId,newPwd,uuid,smscode);

        return AccessResponse.builder().data(jsonObj).success(true).rspcode(200).message("服务端处理请求成功。").build();
    }

    /**
     * 获取直推人数
     * @param memberId
     * @return
     */
    @RequestMapping(value = "/getFirstChildCount/{memberId}")
    public @ResponseBody AccessResponse getFirstChildCount(@PathVariable String memberId){
        //返回体
        JSONObject jsonObj=new JSONObject();
        List<TWheatMember> tWheatMemberList = this.itWheatMemberBusiSV.queryChildList(memberId);
        if (tWheatMemberList != null) {
            jsonObj.put("count", tWheatMemberList.size());
            return AccessResponse.builder().data(jsonObj).success(true).rspcode(200).message("服务端处理请求成功。").build();
        }

        return AccessResponse.builder().data(jsonObj).success(true).rspcode(200).message("服务端处理请求成功。").build();
    }

    /**
     * 获取直推人数(级别分组)
     * @param memberReqVO
     * @return
     */
    @RequestMapping(value = "/getFirstChildCountByLevel")
    public @ResponseBody AccessResponse getFirstChildCountByLevel(@RequestBody MemberReqVO memberReqVO){

        if (StringUtils.isEmpty(memberReqVO.getMemberId())) {
            return AccessResponse.builder().data(getFail("会员ID不能为空")).success(true).rspcode(200).message("服务端处理请求成功。").build();
        }

        //返回体
        JSONObject jsonObject = new JSONObject();
        //当天直推人数
        List<TWheatMemberGroupDZ> tWheatMemberList1 = this.itWheatMemberBusiSV.getFirstChildCountByLevel(memberReqVO.getMemberId());
        this.setStatisticMemberCount(jsonObject,tWheatMemberList1, "direct");
        //当日直推
        List<TWheatMemberGroupDZ> tWheatMemberList2 = this.itWheatMemberBusiSV.getFirstChildCountByLevelToday(memberReqVO.getMemberId());
        this.setStatisticMemberCount(jsonObject,tWheatMemberList2, "directToday");
        TWheatMemberDZ tWheatMemberDZ = new TWheatMemberDZ();
        tWheatMemberDZ.setReferenceId(memberReqVO.getMemberId());
        PageInfo<TWheatMember> pageInfo = this.itWheatMemberBusiSV.queryPage(tWheatMemberDZ, memberReqVO.getPageNum(), memberReqVO.getPageSize());
        //团队会员列表
        jsonObject.put("member_list", pageInfo);
        return AccessResponse.builder().data(jsonObject).success(true).rspcode(200).message("服务端处理请求成功。").build();
    }

    /**
     * 获取直推人数(级别分组).,当天
     * @param memberId
     * @return
     */
    @RequestMapping(value = "/getFirstChildCountByLevelToday/{memberId}")
    public @ResponseBody AccessResponse getFirstChildCountByLevelToday(@PathVariable String memberId){
        //返回体
        List<TWheatMemberGroupDZ> tWheatMemberList = this.itWheatMemberBusiSV.getFirstChildCountByLevelToday(memberId);
        if (tWheatMemberList != null) {
            return AccessResponse.builder().data(tWheatMemberList).success(true).rspcode(200).message("服务端处理请求成功。").build();
        }

        return AccessResponse.builder().data(getFail("失败")).success(true).rspcode(200).message("服务端处理请求成功。").build();
    }

    /**
     * 获取团队人数
     * @param memberId
     * @return
     */
    @RequestMapping(value = "/getAllChildCount/{memberId}")
    public @ResponseBody AccessResponse getAllChildCount(@PathVariable String memberId){
        //返回体
        JSONObject jsonObj=new JSONObject();
        List<TWheatMember> tWheatMemberList = this.itWheatMemberBusiSV.queryAllChildList(memberId);
        if (tWheatMemberList != null) {
            jsonObj.put("count", tWheatMemberList.size());
            return AccessResponse.builder().data(jsonObj).success(true).rspcode(200).message("服务端处理请求成功。").build();
        }

        return AccessResponse.builder().data(jsonObj).success(true).rspcode(200).message("服务端处理请求成功。").build();
    }

    /**
     * 获取团队人数(级别分组)
     * @param pageVO
     * @return
     */
    @RequestMapping(value = "/getAllChildCountByLevel")
    public @ResponseBody AccessResponse getAllChildCountByLevel(@RequestBody MemberReqVO pageVO){

        if (StringUtils.isEmpty(pageVO.getMemberId())) {
            return AccessResponse.builder().data(getFail("会员ID不能为空")).success(true).rspcode(200).message("服务端处理请求成功。").build();
        }

        JSONObject jsonObject = new JSONObject();
        List<String> tWheatMemberList = this.itWheatMemberBusiSV.queryAllChildMemberIdList(pageVO.getMemberId());
        //tWheatMemberList.add(pageVO.getMemberId());
        //团队人数
        List<TWheatMemberGroupDZ> tWheatMemberList1 = this.itWheatMemberBusiSV.getAllChildCountByLevel(tWheatMemberList);
        this.setStatisticMemberCount(jsonObject,tWheatMemberList1, "team");
        //当天推荐团队人数
        List<TWheatMemberGroupDZ> tWheatMemberList3 = this.itWheatMemberBusiSV.getAllChildCountByLevelToday(tWheatMemberList);
        this.setStatisticMemberCount(jsonObject,tWheatMemberList3, "teamToday");

        TWheatMemberDZ tWheatMember = new TWheatMemberDZ();
        tWheatMember.setMemberList(tWheatMemberList);
        PageInfo<TWheatMember> allList = this.itWheatMemberBusiSV.queryPage(tWheatMember, pageVO.getPageNum(), pageVO.getPageSize());
        //团队会员列表
        jsonObject.put("member_list", allList);
        return AccessResponse.builder().data(jsonObject).success(true).rspcode(200).message("服务端处理请求成功。").build();
    }

//    /**
//     * 获取团队人数(级别分组)当天
//     * @param memberId
//     * @return
//     */
//    @RequestMapping(value = "/getAllChildCountByLevelToday/{memberId}")
//    public @ResponseBody AccessResponse getAllChildCountByLevelToday(@PathVariable String memberId){
//        //返回体
//        List<TWheatMemberGroupDZ> list = this.itWheatMemberBusiSV.getAllChildCountByLevelToday(memberId);
//        if (list != null) {
//            return AccessResponse.builder().data(list).success(true).rspcode(200).message("服务端处理请求成功。").build();
//        }
//
//        return AccessResponse.builder().data(null).success(true).rspcode(200).message("服务端处理请求成功。").build();
//    }

    /**
     * 统计会员人数
     * direct 直推人数
     * directToday 当日直推
     * team 团队人数
     * teamToday 当天推荐团队人数
     * @param memberId
     * @return
     */
    @RequestMapping(value = "/statisticMemberCount/{memberId}")
    public @ResponseBody AccessResponse statisticMemberCount(@PathVariable String memberId){
        JSONObject jsonObject = new JSONObject();
        List<String> tWheatMemberList = this.itWheatMemberBusiSV.queryAllChildMemberIdList(memberId);
        tWheatMemberList.add(memberId);
        //当天直推人数
        List<TWheatMemberGroupDZ> tWheatMemberList1 = this.itWheatMemberBusiSV.getFirstChildCountByLevel(memberId);
        this.setStatisticMemberCount(jsonObject,tWheatMemberList1, "direct");
        //当日直推
        List<TWheatMemberGroupDZ> tWheatMemberList2 = this.itWheatMemberBusiSV.getFirstChildCountByLevelToday(memberId);
        this.setStatisticMemberCount(jsonObject,tWheatMemberList2, "directToday");
        //团队人数
        List<TWheatMemberGroupDZ> tWheatMemberList3 = this.itWheatMemberBusiSV.getAllChildCountByLevel(tWheatMemberList);
        this.setStatisticMemberCount(jsonObject,tWheatMemberList3, "team");
        //当天推荐团队人数
        List<TWheatMemberGroupDZ> tWheatMemberList4 = this.itWheatMemberBusiSV.getAllChildCountByLevelToday(tWheatMemberList);
        this.setStatisticMemberCount(jsonObject,tWheatMemberList4, "teamToday");
        return AccessResponse.builder().data(jsonObject).success(true).rspcode(200).message("服务端处理请求成功。").build();
    }

    private void setStatisticMemberCount(JSONObject jsonObject, List<TWheatMemberGroupDZ> tWheatMemberList, String title){
        int count = 0;
        for (TWheatMemberGroupDZ tWheatMemberGroupDZ : tWheatMemberList) {
            count += tWheatMemberGroupDZ.getCount();
        }
        jsonObject.put(title, count);
        jsonObject.put(title+"_list", tWheatMemberList);
    }

    @RequestMapping(value = "/referenceId/{memberId}")
    public @ResponseBody
    AccessResponse referenceId(@PathVariable String memberId, HttpServletRequest request, HttpServletResponse response){

        JSONObject jsonObj = new JSONObject();
        TWheatMember referenceIdMember = twheatMemberBusiSV.selectByMemberId(memberId);
        if(referenceIdMember!=null){
            jsonObj.put("referenceId",referenceIdMember.getReferenceId());
        }
        return AccessResponse.builder().data(jsonObj).success(true).rspcode(200).message("服务端处理请求成功。").build();
    }
    @RequestMapping(value = "/address/{memberId}")
    public @ResponseBody
    AccessResponse getAddress(@PathVariable String memberId, HttpServletRequest request, HttpServletResponse response){

        //返回体
        JSONObject jsonObj=new JSONObject();
        jsonObj = restMemberService.getAddress(memberId);

        return AccessResponse.builder().data(jsonObj).success(true).rspcode(200).message("服务端处理请求成功。").build();
    }

    @RequestMapping(value = "/updateMemberInfo/{memberId}")
    public @ResponseBody
    AccessResponse updateMemberInfo(@PathVariable String memberId, String image,HttpServletRequest request, HttpServletResponse response){

        //返回体
        JSONObject jsonObj=new JSONObject();
        jsonObj = restMemberService.updateMemberInfo(memberId,image);

        return AccessResponse.builder().data(jsonObj).success(true).rspcode(200).message("服务端处理请求成功。").build();
    }

    @RequestMapping(value = "/updateMemberName/{memberId}")
    public @ResponseBody
    AccessResponse updateMemberName(@PathVariable String memberId, String name,HttpServletRequest request, HttpServletResponse response){

        //返回体
        JSONObject jsonObj=new JSONObject();
        jsonObj = restMemberService.updateMemberName(memberId,name);

        return AccessResponse.builder().data(jsonObj).success(true).rspcode(200).message("服务端处理请求成功。").build();
    }

    @RequestMapping(value = "/getMemberInfo/{memberId}")
    public @ResponseBody
    AccessResponse getMemberInfo(@PathVariable String memberId, String image,HttpServletRequest request, HttpServletResponse response){

        System.out.println("接收请求" + DateUtils.getYYYYMMDDHHMISS(DateUtils.getSysDate()));

        //返回体
        JSONObject jsonObj=new JSONObject();
        jsonObj = restMemberService.getMemberInfo(memberId);

        return AccessResponse.builder().data(jsonObj).success(true).rspcode(200).message("服务端处理请求成功。").build();
    }

    public net.sf.json.JSONObject getFail(String desc){
        net.sf.json.JSONObject jsonObject = new net.sf.json.JSONObject();
        jsonObject.put("desc", desc);
        jsonObject.put("result", "01");
        return jsonObject;
    }

    /**
     * 获取会员树
     * @param memberId
     * @param image
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/getMemberTree/{memberId}")
    public @ResponseBody
    AccessResponse getMemberTree(@PathVariable String memberId, String image, HttpServletRequest request, HttpServletResponse response){

        //返回体
        JSONObject jsonObj=new JSONObject();
        TableMemberTree tWheatMemberTree = new TableMemberTree();
        tWheatMemberTree.setMemberId(memberId);

        TableMember tableMember = this.iTableMemberBusiSV.selectByMemberId(memberId);
        if (tableMember == null) {
            return AccessResponse.builder().data(null).success(true).rspcode(200).message("服务端处理请求成功。").build();
        }

        tWheatMemberTree = this.iTableMemberBusiSV.queryAllChildTree(tWheatMemberTree);
        tWheatMemberTree.setPhone(tableMember.getPhone());
        tWheatMemberTree.setLevel(tableMember.getLevel());
        TreeVO treeVO = new TreeVO();
        this.setTree(tWheatMemberTree, treeVO);

        return AccessResponse.builder().data(JSON.toJSON(treeVO)).success(true).rspcode(200).message("服务端处理请求成功。").build();
    }

    private void setTree(TableMemberTree tWheatMemberTree, TreeVO treeVO){
        if (tWheatMemberTree != null && treeVO != null) {
            String level = DictUtils.getDictLabel(String.valueOf(tWheatMemberTree.getLevel()),"level","");
            treeVO.setName(tWheatMemberTree.getMemberId()+"\n("+level+":"+tWheatMemberTree.getPhone()+")");
            if (!CollectionUtils.isEmpty(tWheatMemberTree.getChildList())) {
                List<TreeVO> treeVOList = new ArrayList<>();
                for (TableMemberTree tmp : tWheatMemberTree.getChildList()) {
                    TreeVO child = new TreeVO();
                    treeVOList.add(child);
                    this.setTree(tmp, child);
                }
                treeVO.setChildren(treeVOList);
            }
        }
    }
}
