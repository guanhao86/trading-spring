package com.spring.free.service;/**
 * Created by hengpu on 2019/2/25.
 */

import com.aliyun.oss.OSSClient;
import com.github.pagehelper.PageInfo;
import com.spring.free.domain.CommissionRecord;
import com.spring.free.manager.CommissionRecordManager;
import com.spring.free.util.OSSClientUtil;
import com.spring.free.util.constraints.Global;
import com.spring.free.util.exception.ExceptionCodeEnum;
import com.spring.free.util.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
@Service
public class CommissionRecordService {

    @Autowired
    private CommissionRecordManager manager;

    /**
     * @Author bianyx
     * @Description //TODO hengpu
     * @Date 9:30 2019/3/1
     * @Param [content, page, pageSize]
     * @return com.github.pagehelper.PageInfo<com.hengpu.national.domain.Content>
     **/
    public PageInfo<CommissionRecord> pageList(CommissionRecord commissionRecord, Integer page, Integer pageSize){
        return manager.findList(commissionRecord,page, pageSize);
    }
    public List<CommissionRecord> getList(CommissionRecord commissionRecord){
        return manager.findList(commissionRecord);
    }

    public CommissionRecord get(Long id){
        return manager.get(id);
    }

    /*更新*/
    public void update(CommissionRecord commissionRecord){
        manager.updateBs(commissionRecord);
    }
    public void update(CommissionRecord commissionRecord, MultipartFile postPic, Map map){
        String imge = upload(postPic);
        System.out.print(imge);
        if(imge==null){
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "更新图片信息失败", map.get(Global.URL).toString(), map);
        }
        commissionRecord.setImage(imge);
        manager.updateBs(commissionRecord);
    }
    /*新增*/
    public void insert(CommissionRecord commissionRecord){
        manager.insertBs(commissionRecord);
    }
    public void delete(CommissionRecord commissionRecord){
        manager.deleteBs(commissionRecord);
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

    public void insert(CommissionRecord commissionRecord,MultipartFile postPic,Map map){
        String imge = upload(postPic);
        System.out.print(imge);
        if(imge==null){
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "更新图片信息失败", map.get(Global.URL).toString(), map);
        }
        commissionRecord.setImage(imge);
        manager.insertBs(commissionRecord);

    }
}
