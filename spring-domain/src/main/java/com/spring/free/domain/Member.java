package com.spring.free.domain;/**
 * Created by hengpu on 2019/2/25.
 */

import com.spring.free.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Table;

/**
 * @ClassName Member
 * @Description //会员
 * @Author hengpu
 * @Date 2019/2/25 14:02
 * @Version 1.0
 **/
@Getter
@Setter
@ToString
@Table(name = "t_nat_member")
public class Member extends BaseEntity<Member> {
    /**
     *用户头像
     */
    private String avatar;
    /**
     *用户昵称
     */
    private String userName;
    /**
     *微信登录授权id
     */
    private String openid;
    /**
     *unionid
     */
    private String unionid;
    /**
     *手机号
     */
    private String phone;
    /**
     *手机绑定状态
     */
    private Integer phoneStatus;
    /**
     *会员编号
     */
    private String memberId;
    /**
     *推荐人id
     */
    private String refereeId;
    /**
     *会员等级
     */
    private String memberLevel;
    /**
     *预留字段
     */
    private String expand1;
    private String expand2;
    private String expand3;
    private String expand4;
    private String expand5;







}
