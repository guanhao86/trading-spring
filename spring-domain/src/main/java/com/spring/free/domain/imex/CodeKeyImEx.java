package com.spring.free.domain.imex;/**
 * Created by hengpu on 2019/5/6.
 */


import com.spring.free.utils.excel.annotation.ExcelField;

/**
 * @ClassName CodeKeyImEx
 * @Description //TODO
 * @Author hengpu
 * @Date 2019/5/6 14:58
 * @Version 1.0
 **/
public class CodeKeyImEx {
    private String codeKey;
    private String memberId;
    private String state;
    private String useMemebrId;

    @ExcelField(title="激活码",  align=2, sort=1)
    public String getCodeKey() {
        return codeKey;
    }

    public void setCodeKey(String codeKey) {
        this.codeKey = codeKey;
    }

    @ExcelField(title="持有会员编号",  align=2, sort=2)
    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    @ExcelField(title="状态",  align=2, sort=3)
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @ExcelField(title="使用会员编号",  align=2, sort=4)
    public String getUseMemebrId() {
        return useMemebrId;
    }

    public void setUseMemebrId(String useMemebrId) {
        this.useMemebrId = useMemebrId;
    }

}
