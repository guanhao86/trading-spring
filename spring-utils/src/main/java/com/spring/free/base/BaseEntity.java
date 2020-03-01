package com.spring.free.base;


import com.spring.free.common.domain.BaseDomain;
import lombok.ToString;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 * 基础Entity
 *
 * @author Memory
 * @version 1.0
 */

@ToString
public class BaseEntity<T> extends BaseDomain implements Serializable {

    private static final long serialVersionUID = -7130389491889353241L;

    /**
     * 自增长ID
     */
    protected Long id;
    /**
     * 备注
     */
    @Transient
    protected String remarks;
    /**
     * 删除标记（0：正常；1：删除；2：审核）
     */
    protected String delFlag;
    /**
     * 排序字段
     */
    @Transient
    protected String orderByString;
    /**
     * 用来判断提示信息参数
     */
    @Transient
    protected String paramMsg;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {

        this.delFlag = delFlag;
    }

    public String getParamMsg() {
        return paramMsg;
    }

    public void setParamMsg(String paramMsg) {
        this.paramMsg = paramMsg;
    }

    public void setInsertDefault() {
        if (super.getModifyTime() == null) {
            super.setModifyTime(new Date());
        }
        if (delFlag == null || "".equals(delFlag)) {
            this.delFlag = DEL_FLAG_NORMAL;
        }
        if (super.getCreateTime() == null) {
            super.setCreateTime(new Date());
        }
    }

    public void setUpdateDefault() {
        if (super.getModifyTime() == null) {
            super.setModifyTime(new Date());
        }
        if (delFlag == null || "".equals(delFlag)) {
            this.delFlag = DEL_FLAG_NORMAL;
        }

    }

    /**
     * 删除标记（0：正常；1：删除；2：审核；）
     */
    public static final String DEL_FLAG_NORMAL = "0";
    public static final String DEL_FLAG_DELETE = "1";
    public static final String DEL_FLAG_AUDIT = "2";


}
