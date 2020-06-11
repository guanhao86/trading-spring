package com.spring.free.controller.api;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.spring.fee.model.*;
import com.spring.fee.service.ITWheatMemberBusiSV;
import com.spring.fee.service.ITableBroadcastInfoBusiSV;
import com.spring.fee.service.ITableMemberBusiSV;
import com.spring.fee.service.ITableMemberGoodsBusiSV;
import com.spring.free.common.domain.AccessResponse;
import com.spring.free.common.util.PythonUtil3;
import com.spring.free.config.PassToken;
import com.spring.free.config.TokenUtil;
import com.spring.free.domain.UserInfo;
import com.spring.free.service.RestMemberService;
import com.spring.free.system.UserService;
import com.spring.free.util.DateUtils;
import com.spring.free.util.md5.Md5Util;
import com.spring.free.utils.principal.BaseGetPrincipal;
import com.spring.free.utils.velocity.DictUtils;
import com.spring.free.vo.*;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.visitor.ExceptionTableSensitiveMethodVisitor;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RequestMapping("/api/member")
@Controller
@Slf4j
public class RestMemberController {

    @Value("${python.path}")
    public String python_path;

    @Autowired
    ITableMemberBusiSV iTableMemberBusiSV;

    @Autowired
    ITableMemberGoodsBusiSV iTableMemberGoodsBusiSV;

    @Autowired
    UserService userService;

    @Autowired
    ITableBroadcastInfoBusiSV iTableBroadcastInfoBusiSV;

    @PassToken
    @RequestMapping(value = "/getMemberInfoAjax/{memberId}")
    public @ResponseBody
    AccessResponse getMemberInfoAjax(@PathVariable String memberId, String image,HttpServletRequest request, HttpServletResponse response){

        System.out.println("接收请求" + DateUtils.getYYYYMMDDHHMISS(DateUtils.getSysDate()));

        //返回体
        TableMember tableMember = iTableMemberBusiSV.selectByMemberId(memberId);
        JSONObject jsonObj = (JSONObject)JSON.toJSON(tableMember);
        return AccessResponse.builder().data(jsonObj).success(true).rspcode(200).message("服务端处理请求成功。").build();
    }

    /**
     * 会员信息
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/getMemberInfo")
    public @ResponseBody
    AccessResponse getMemberInfo(HttpServletRequest request, HttpServletResponse response){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        System.out.println("接收请求" + DateUtils.getYYYYMMDDHHMISS(DateUtils.getSysDate()));

        String memberId = TokenUtil.getUserId(request);

        //返回体
        TableMember tableMember = iTableMemberBusiSV.selectByMemberId(memberId);

        MemberRspVO vo = new MemberRspVO();
        BeanUtils.copyProperties(tableMember, vo);
        vo.setMRankDesc(DictUtils.getDictLabel(String.valueOf(tableMember.getmRank()), "mRank", ""));
        vo.setLevelDesc(DictUtils.getDictLabel(String.valueOf(tableMember.getLevel()), "level", ""));

        //取金鸡数量(有效金鸡数量)
        TableMemberGoods tableMemberGoods = new TableMemberGoods();
        tableMemberGoods.setMemberId(memberId);
        List<TableMemberGoods> listValid = this.iTableMemberGoodsBusiSV.getListValid(tableMemberGoods);
        int jjCount = 0;
        for (TableMemberGoods tmp : listValid) {
            jjCount += tmp.getAmount();
        }
        vo.setJjCount(jjCount);

        stopWatch.stop();
        System.out.println("耗时：" + stopWatch.getTotalTimeSeconds());
        return AccessResponse.builder().data(vo).success(true).rspcode(ResponseConstants.ResponseCode.SUCCESS).message("服务端处理请求成功。").build();
    }

    /**
     * 获取会员树
     * @param memberId
     * @param image
     * @param request
     * @param response
     * @return
     */
    @PassToken
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

        Map<String, List<TableMember>> map = this.iTableMemberBusiSV.queryArrangeListMap(new TableMember());
        tWheatMemberTree = this.iTableMemberBusiSV.queryAllChildTree(tWheatMemberTree, map);
        tWheatMemberTree.setPhone(tableMember.getPhone());
        tWheatMemberTree.setLevel(tableMember.getLevel());
        TreeVO treeVO = new TreeVO();
        this.setTree(tWheatMemberTree, treeVO);

        return AccessResponse.builder().data(JSON.toJSON(treeVO)).success(true).rspcode(200).message("服务端处理请求成功。").build();
    }

    private void setTree(TableMemberTree tWheatMemberTree, TreeVO treeVO){
        if (tWheatMemberTree != null && treeVO != null) {
            String level = DictUtils.getDictLabel(String.valueOf(tWheatMemberTree.getLevel()),"level","");
            String name = tWheatMemberTree.getReallyName();
            treeVO.setName(tWheatMemberTree.getMemberId()+"\n("+name+":"+tWheatMemberTree.getPhone()+")");
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


    /**
     * 登录
     */
    @PassToken
    @RequestMapping(value = "/login")
    public @ResponseBody
    AccessResponse login(@RequestBody TableMember tableMember,HttpServletRequest request, HttpServletResponse response){
        log.info("登录{}", JSON.toJSONString(tableMember));
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        ResponseBaseVO responseBaseVO = new ResponseBaseVO();

        //查询会员信息
        TableMember origTableMember = this.iTableMemberBusiSV.selectByMemberId(tableMember.getMemberId());
        if (origTableMember == null) {
            return AccessResponse.builder().data(responseBaseVO).success(true).rspcode(ResponseConstants.ResponseCode.FAIL).message("会员不存在。").build();
        }

        UserInfo userInfo = this.userService.getUserByUserName(tableMember.getMemberId());
        if (userInfo == null) {
            return AccessResponse.builder().data(responseBaseVO).success(true).rspcode(ResponseConstants.ResponseCode.FAIL).message("用户数据错误。").build();
        }
        if (!"1".equals(userInfo.getLoginFlag())) {
            return AccessResponse.builder().data(responseBaseVO).success(true).rspcode(ResponseConstants.ResponseCode.FAIL).message("当前状态不允许登陆。").build();
        }

        if (!origTableMember.getPassword().equals(Md5Util.md5Hex(tableMember.getPassword()))) {
            return AccessResponse.builder().data(responseBaseVO).success(true).rspcode(ResponseConstants.ResponseCode.FAIL).message("密码错误。").build();
        }

        //获取token
        String token = TokenUtil.createJWT(tableMember.getMemberId(), origTableMember.getReallyName(), "",  24 * 3600 * 1000);
        responseBaseVO.setToken(token);

        String tokenRefresh = TokenUtil.createJWT(tableMember.getMemberId(), origTableMember.getReallyName(), "",  48 * 3600 * 1000);
        responseBaseVO.setTokenRefresh(tokenRefresh);

        //返回体
        stopWatch.stop();
        log.info("耗时：" + stopWatch.getTotalTimeSeconds());
        return AccessResponse.builder().data(responseBaseVO).success(true).rspcode(ResponseConstants.ResponseCode.SUCCESS).message("服务端处理请求成功。").build();
    }

    /**
     * 刷新token
     */
    @RequestMapping(value = "/refreshToken")
    public @ResponseBody
    AccessResponse refreshToken(HttpServletRequest request, HttpServletResponse response){
        log.info("刷新token{}");
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        ResponseBaseVO responseBaseVO = new ResponseBaseVO();
        String memberId = TokenUtil.getUserId(request);
        TableMember tableMember = this.iTableMemberBusiSV.selectByMemberId(memberId);

        //获取token
        String token = TokenUtil.createJWT(tableMember.getMemberId(), tableMember.getReallyName(), "",  24 * 3600 * 1000);
        responseBaseVO.setToken(token);

        //返回体
        stopWatch.stop();
        log.info("耗时：" + stopWatch.getTotalTimeSeconds());
        return AccessResponse.builder().data(responseBaseVO).success(true).rspcode(ResponseConstants.ResponseCode.SUCCESS).message("服务端处理请求成功。").build();
    }

    /**
     * 注册
     */
    @PassToken
    @RequestMapping(value = "/register")
    public @ResponseBody
    AccessResponse register(@RequestBody TableMember tableMember,HttpServletRequest request, HttpServletResponse response){
        log.info("注册{}", JSON.toJSONString(tableMember));
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try {
            //registerFrom = 1 前端注册
            tableMember = this.iTableMemberBusiSV.regist(tableMember, 1);
        }catch (Exception e) {
            return AccessResponse.builder().data(null).success(true).rspcode(ResponseConstants.ResponseCode.FAIL).message(e.getMessage()).build();
        }

        //返回体
        stopWatch.stop();
        log.info("耗时：" + stopWatch.getTotalTimeSeconds());
        return AccessResponse.builder().data(tableMember).success(true).rspcode(ResponseConstants.ResponseCode.SUCCESS).message("服务端处理请求成功。").build();
    }

    /**
     * 首页
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/indexPage")
    public @ResponseBody
    AccessResponse indexPage(HttpServletRequest request, HttpServletResponse response){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        System.out.println("接收请求" + DateUtils.getYYYYMMDDHHMISS(DateUtils.getSysDate()));

        String memberId = TokenUtil.getUserId(request);
        TableMember tabelMember = iTableMemberBusiSV.selectByMemberId(memberId);

        IndexPageRspVO vo = new IndexPageRspVO();
        //取公告
        TableBroadcastInfo tableBroadcastInfo = new TableBroadcastInfo();
        tableBroadcastInfo.setMemberLevel(tabelMember.getLevel());
        tableBroadcastInfo.setMrankLevel(tabelMember.getmRank());
        PageInfo<TableBroadcastInfo> tableBroadcastInfoPageInfo = this.iTableBroadcastInfoBusiSV.queryListPage(tableBroadcastInfo, 1, 1000000, null);
        vo.setTableBroadcastInfoList(tableBroadcastInfoPageInfo.getList());
        //左右区业绩
        //调用python获取在客户端我的粉丝这个界面，最上面加两行，左区业绩： 右区业绩
        //调用我这个函数获得数据就可以了，传参数是字符串类型的，member_id
        //String result = "(1,2)";
//        String result = PythonUtil3.runPy(python_path, "get_child_achievement.py", memberId, "");
//
//        if (StringUtils.isNotEmpty(result) && result.indexOf("(") >= 0 && result.indexOf(")")>=0) {
//            result = result.substring(1, result.length()-1);
//        }
//
//        System.out.println("左区业绩： 右区业绩："+result);
//
//        String left = "0";
//        String right = "0";
//        String[] results = result.split(",");
//
//        if (results != null && results.length == 2) {
//            left = results[0];
//            right = results[1];
//        }
        String left = tabelMember.getLeftAmount() == null ? "0" : String.valueOf(tabelMember.getLeftAmount());
        String right = tabelMember.getRightAmount() == null ? "0" : String.valueOf(tabelMember.getRightAmount());
        vo.setLeftAmount(left);
        vo.setRightAmount(right);

        Date today = DateUtils.getDateZero(DateUtils.getSysDate());
        Date tomorrow = DateUtils.getDateZero(DateUtils.getNextDate(DateUtils.getSysDate()));

        //今日推荐
        TableMember queryMember = new TableMember();
        queryMember.setReferenceId(memberId);
        Map<String, Object> map = new HashMap<>();
        map.put("REGISTER_TIME_START", today);
        map.put("REGISTER_TIME_END", tomorrow);
        String todayCount = String.valueOf(this.iTableMemberBusiSV.queryList(queryMember, map).size());
        vo.setTodayCount(todayCount);

        //累计推荐
        queryMember = new TableMember();
        queryMember.setReferenceId(memberId);
        String allCount = String.valueOf(this.iTableMemberBusiSV.queryList(queryMember, null).size());
        vo.setAllCount(allCount);


        TableMemberTree tWheatMemberTree = new TableMemberTree();
        tWheatMemberTree.setMemberId(memberId);
        tWheatMemberTree = this.iTableMemberBusiSV.queryAllChildTree(tWheatMemberTree);
        List<TableMemberTree> all = new ArrayList<>();
        List<TableMemberTree> day = new ArrayList<>();
        getCount(tWheatMemberTree, all, day, today, tomorrow);

        //今日团队
        String todayTeam = String.valueOf(day.size());
        vo.setTodayTeam(todayTeam);
        //累计团队
        String allTeam = String.valueOf(all.size());
        vo.setAllTeam(allTeam);

        stopWatch.stop();
        System.out.println("耗时：" + stopWatch.getTotalTimeSeconds());
        return AccessResponse.builder().data(vo).success(true).rspcode(ResponseConstants.ResponseCode.SUCCESS).message("服务端处理请求成功。").build();
    }

    /**
     * @param tWheatMemberTree
     * @return
     */
    public void getCount(TableMemberTree tWheatMemberTree, List<TableMemberTree> all, List<TableMemberTree> day, Date today, Date tomorrow) {
        if (tWheatMemberTree != null) {
            all.add(tWheatMemberTree);
            if (tWheatMemberTree.getRegisterTime() != null && tWheatMemberTree.getRegisterTime().after(today) && tWheatMemberTree.getRegisterTime().before(tomorrow)) {
                day.add(tWheatMemberTree);
            }
            if (null != tWheatMemberTree.getChildList()) {
                for(TableMemberTree child : tWheatMemberTree.getChildList()) {
                    getCount(child, all, day, today, tomorrow);
                }
            }
        }
    }

    /**
     * 密码修改
     */
    @PassToken
    @RequestMapping(value = "/changePwd")
    public @ResponseBody
    AccessResponse changePwd(@RequestBody QueryReqVO queryReqVO, HttpServletRequest request, HttpServletResponse response){
        log.info("密码修改{}", JSON.toJSONString(queryReqVO));
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try {
            String memberId = TokenUtil.getUserId(request);
            TableMember tableMember = new TableMember();
            tableMember.setMemberId(memberId);
            tableMember.setPassword(queryReqVO.getNewPassword());
            this.iTableMemberBusiSV.changePwd(tableMember, queryReqVO.getOldPassword());

        }catch (Exception e) {
            return AccessResponse.builder().data(null).success(true).rspcode(ResponseConstants.ResponseCode.FAIL).message(e.getMessage()).build();
        }

        //返回体
        stopWatch.stop();
        log.info("耗时：" + stopWatch.getTotalTimeSeconds());
        return AccessResponse.builder().data(null).success(true).rspcode(ResponseConstants.ResponseCode.SUCCESS).message("服务端处理请求成功。").build();
    }

    /**
     * 我的粉丝
     */
    @RequestMapping(value = "/myChildlist")
    public @ResponseBody
    AccessResponse myChildlist(@RequestBody QueryReqVO queryReqVO, HttpServletRequest request, HttpServletResponse response){
        log.info("我的粉丝{}", JSON.toJSONString(queryReqVO));
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        PageInfo<TableMember> pageInfo;
        try {
            String memberId = TokenUtil.getUserId(request);
            pageInfo = this.iTableMemberBusiSV.queryAllChildPage(memberId, queryReqVO.getPageNum(), queryReqVO.getPageSize(), null);

        }catch (Exception e) {
            return AccessResponse.builder().data(null).success(true).rspcode(ResponseConstants.ResponseCode.FAIL).message(e.getMessage()).build();
        }

        //返回体
        stopWatch.stop();
        log.info("耗时：" + stopWatch.getTotalTimeSeconds());
        return AccessResponse.builder().data(pageInfo).success(true).rspcode(ResponseConstants.ResponseCode.SUCCESS).message("服务端处理请求成功。").build();
    }

    /**
     * 我的领导
     */
    @RequestMapping(value = "/getArrangeMember")
    public @ResponseBody
    AccessResponse myChildlist(HttpServletRequest request, HttpServletResponse response){
        log.info("我的领导{}");
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        TableMember arrangeMember ;
        try {
            String memberId = TokenUtil.getUserId(request);
            TableMember tableMember = this.iTableMemberBusiSV.selectByMemberId(memberId);
            arrangeMember = this.iTableMemberBusiSV.selectByMemberId(tableMember.getArrangeId());

        }catch (Exception e) {
            return AccessResponse.builder().data(null).success(true).rspcode(ResponseConstants.ResponseCode.FAIL).message(e.getMessage()).build();
        }

        //返回体
        stopWatch.stop();
        log.info("耗时：" + stopWatch.getTotalTimeSeconds());
        return AccessResponse.builder().data(arrangeMember).success(true).rspcode(ResponseConstants.ResponseCode.SUCCESS).message("服务端处理请求成功。").build();
    }

    /**
     * 修改会员信息
     */
    @RequestMapping(value = "/modify")
    public @ResponseBody
    AccessResponse modify(@RequestBody TableMember tableMember, HttpServletRequest request, HttpServletResponse response){
        log.info("修改会员信息{}", JSON.toJSONString(tableMember));
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try {
            String memberId = TokenUtil.getUserId(request);
            tableMember.setMemberId(memberId);
            this.iTableMemberBusiSV.update(tableMember, false);

        }catch (Exception e) {
            return AccessResponse.builder().data(null).success(true).rspcode(ResponseConstants.ResponseCode.FAIL).message(e.getMessage()).build();
        }

        //返回体
        stopWatch.stop();
        log.info("耗时：" + stopWatch.getTotalTimeSeconds());
        return AccessResponse.builder().data(tableMember).success(true).rspcode(ResponseConstants.ResponseCode.SUCCESS).message("服务端处理请求成功。").build();
    }

    /**
     * 我的金鸡
     */
    @RequestMapping(value = "/myChickenList")
    public @ResponseBody
    AccessResponse myChickenList(@RequestBody QueryReqVO queryReqVO, HttpServletRequest request, HttpServletResponse response){
        log.info("我的金鸡{}", JSON.toJSONString(queryReqVO));
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        PageInfo<TableMemberGoods> pageInfo;
        try {
            String memberId = TokenUtil.getUserId(request);
            TableMemberGoods tableMemberGoods = new TableMemberGoods();
            tableMemberGoods.setMemberId(memberId);
            pageInfo = this.iTableMemberGoodsBusiSV.queryListPage(tableMemberGoods, queryReqVO.getPageNum(), queryReqVO.getPageSize(), null);

        }catch (Exception e) {
            return AccessResponse.builder().data(null).success(true).rspcode(ResponseConstants.ResponseCode.FAIL).message(e.getMessage()).build();
        }

        //返回体
        stopWatch.stop();
        log.info("耗时：" + stopWatch.getTotalTimeSeconds());
        return AccessResponse.builder().data(pageInfo).success(true).rspcode(ResponseConstants.ResponseCode.SUCCESS).message("服务端处理请求成功。").build();
    }
}
