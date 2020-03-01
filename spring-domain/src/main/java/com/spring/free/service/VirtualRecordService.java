package com.spring.free.service;/**
 * Created by hengpu on 2019/2/25.
 */

import com.aliyun.oss.OSSClient;
import com.github.pagehelper.PageInfo;
import com.spring.fee.constants.InvestConstants;
import com.spring.fee.service.ITWheatAccountBusiSV;
import com.spring.free.domain.VirtualRecord;
import com.spring.free.manager.VirtualRecordManager;
import com.spring.free.util.OSSClientUtil;
import com.spring.free.util.constraints.Global;
import com.spring.free.util.exception.ExceptionCodeEnum;
import com.spring.free.util.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @ClassName ContentService
 * @Description //TODO
 * @Author hengpu
 * @Date 2019/2/25 15:25
 * @Version 1.0
 **/
@Slf4j
@Service
@Transactional
public class VirtualRecordService {

    @Autowired
    private VirtualRecordManager manager;
    @Autowired
    ITWheatAccountBusiSV itWheatAccountBusiSV;

    /**
     * @Author bianyx
     * @Description //TODO hengpu
     * @Date 9:30 2019/3/1
     * @Param [content, page, pageSize]
     * @return com.github.pagehelper.PageInfo<com.hengpu.national.domain.Content>
     **/
    public PageInfo<VirtualRecord> pageList(VirtualRecord virtualRecord, Integer page, Integer pageSize){
        return manager.findList(virtualRecord,page, pageSize);
    }
    public List<VirtualRecord> getList(VirtualRecord virtualRecord){
        return manager.findList(virtualRecord);
    }

    public VirtualRecord get(Long id){
        return manager.get(id);
    }

    /*更新*/
    public void update(VirtualRecord virtualRecord){
        manager.updateBs(virtualRecord);
    }
    public void update(VirtualRecord virtualRecord, MultipartFile postPic, Map map){
        String imge = upload(postPic);
        System.out.print(imge);
        if(imge==null){
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "更新图片信息失败", map.get(Global.URL).toString(), map);
        }
        virtualRecord.setImage(imge);
        manager.updateBs(virtualRecord);
    }
    /*新增*/
    public void insert(VirtualRecord virtualRecord){
        manager.insertBs(virtualRecord);
        //提币申请 冻结可用金额
        if(virtualRecord.getType()==2){
            itWheatAccountBusiSV.modifyAcct(virtualRecord.getMmeberId(),
                    virtualRecord.getQcCoin().longValue()*1000,
                    InvestConstants.Account.DETAIL_OPERTYPE_FREEZE,
                    InvestConstants.Account.DETAIL_TYPE_OTHER,
                    null,
                    "提币审核通过");
        }else if(virtualRecord.getType()==1&&virtualRecord.getExpand1().equals("2")){
            itWheatAccountBusiSV.modifyAcct(virtualRecord.getMmeberId(),
                    virtualRecord.getQcCoin().longValue()*1000,
                    InvestConstants.Account.DETAIL_OPERTYPE_ADD,
                    InvestConstants.Account.DETAIL_TYPE_OTHER,
                    null,
                    "管理员充值");
        }
    }
    public void delete(VirtualRecord virtualRecord){
        manager.deleteBs(virtualRecord);
    }

    public String upload( MultipartFile pic){
        OSSClient ossClient = new OSSClient(OSSClientUtil.Endpoint, OSSClientUtil.AccessKeyId, OSSClientUtil.AccessKeySecret);
        // 上传文件流。
        String fileExt = getExtensionName(pic.getOriginalFilename());
        String imageName = System.nanoTime()+"";
        String fileName = imageName + "." + fileExt;
        try{
            ossClient.putObject(OSSClientUtil.BucketName, fileName, pic.getInputStream());
        }catch(Exception e){

            System.out.println("上传失败，写入文件失败，"+e.getMessage());
            e.printStackTrace();
            return null;

        }

        String realpath = OSSClientUtil.bucketCDN+"/"+fileName;

        // 关闭OSSClient。
        ossClient.shutdown();

        return realpath;
    }

    /**
     * Java文件操作 获取文件扩展名
     */
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }

    public void insert(VirtualRecord virtualRecord,MultipartFile postPic,Map map){
        String imge = upload(postPic);
        System.out.print(imge);
        if(imge==null){
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "更新图片信息失败", map.get(Global.URL).toString(), map);
        }
        virtualRecord.setImage(imge);
        manager.insertBs(virtualRecord);
        if(virtualRecord.getType()==2){
            itWheatAccountBusiSV.modifyAcct(virtualRecord.getMmeberId(),
                    virtualRecord.getQcCoin().longValue()*1000,
                    InvestConstants.Account.DETAIL_OPERTYPE_FREEZE,
                    InvestConstants.Account.DETAIL_TYPE_OTHER,
                    null,
                    "提币审核通过");
        }else if(virtualRecord.getType()==1&&virtualRecord.getExpand1().equals("2")){
            itWheatAccountBusiSV.modifyAcct(virtualRecord.getMmeberId(),
                    virtualRecord.getQcCoin().longValue()*1000,
                    InvestConstants.Account.DETAIL_OPERTYPE_ADD,
                    InvestConstants.Account.DETAIL_TYPE_OTHER,
                    null,
                    "管理员充值");
        }

    }

    public void audit(VirtualRecord virtualRecord){
        manager.updateBs(virtualRecord);
        //提币审核通过 先解冻再扣可用金额
        if(virtualRecord.getState()==1&&virtualRecord.getType()==2){
            itWheatAccountBusiSV.modifyAcct(virtualRecord.getMmeberId(),
                    virtualRecord.getQcCoin().longValue()*1000,
                    InvestConstants.Account.DETAIL_OPERTYPE_UNFREEZE,
                    InvestConstants.Account.DETAIL_TYPE_OTHER,
                    null,
                    "提币审核通过");
            itWheatAccountBusiSV.modifyAcct(virtualRecord.getMmeberId(),
                    virtualRecord.getQcCoin().longValue()*1000,
                    InvestConstants.Account.DETAIL_OPERTYPE_WITHDRAW,
                    InvestConstants.Account.DETAIL_TYPE_OTHER,
                    null,
                    "提币审核通过");
            //提币审核不通过 解冻
        }else if(virtualRecord.getState()==2&&virtualRecord.getType()==2){
            itWheatAccountBusiSV.modifyAcct(virtualRecord.getMmeberId(),
                    virtualRecord.getQcCoin().longValue()*1000,
                    InvestConstants.Account.DETAIL_OPERTYPE_UNFREEZE,
                    InvestConstants.Account.DETAIL_TYPE_OTHER,
                    null,
                    "提币审核不通过");
        }
    }
}
