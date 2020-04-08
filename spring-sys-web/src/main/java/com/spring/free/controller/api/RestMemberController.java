package com.spring.free.controller.api;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.spring.fee.model.*;
import com.spring.fee.service.ITWheatMemberBusiSV;
import com.spring.fee.service.ITableMemberBusiSV;
import com.spring.free.common.domain.AccessResponse;
import com.spring.free.config.PassToken;
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
    ITableMemberBusiSV iTableMemberBusiSV;

    @PassToken
    @RequestMapping(value = "/getMemberInfo/{memberId}")
    public @ResponseBody
    AccessResponse getMemberInfo(@PathVariable String memberId, String image,HttpServletRequest request, HttpServletResponse response){

        System.out.println("接收请求" + DateUtils.getYYYYMMDDHHMISS(DateUtils.getSysDate()));

        //返回体
        TableMember tableMember = iTableMemberBusiSV.selectByMemberId(memberId);
        JSONObject jsonObj = (JSONObject)JSON.toJSON(tableMember);
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
