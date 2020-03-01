package com.spring.free.manager;

import com.spring.free.domain.MemberExtend;
import com.spring.free.mapper.MemberExtendMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberExtendManager implements MemberExtendMapper {
    @Autowired
    MemberExtendMapper memberExtendMapper;

    public List<MemberExtend> findList(MemberExtend memberExtend){
        return  memberExtendMapper.findList(memberExtend);
    }
}
