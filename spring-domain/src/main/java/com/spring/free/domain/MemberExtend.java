package com.spring.free.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class MemberExtend implements Serializable {

    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_wheat_member.member_id
     *
     * @mbggenerated
     */
    private String memberId;

    private String address;

    private String delFlag;


}
