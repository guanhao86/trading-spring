package com.spring.free.mapper;


import com.spring.free.domain.MemberExtend;

import java.util.List;

public interface MemberExtendMapper {

    List<MemberExtend> findList(MemberExtend memberExtend);
}
