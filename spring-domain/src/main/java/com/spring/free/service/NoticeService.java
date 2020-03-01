package com.spring.free.service;


import com.aliyun.oss.OSSClient;
import com.github.pagehelper.PageInfo;
import com.spring.free.domain.Notice;
import com.spring.free.manager.NoticeManager;
import com.spring.free.util.OSSClientUtil;
import com.spring.free.util.constraints.Global;
import com.spring.free.util.exception.ExceptionCodeEnum;
import com.spring.free.util.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
public class NoticeService {

    @Autowired
    private NoticeManager manager;

    public PageInfo<Notice> pageList(Notice notice, Integer page, Integer pageSize){
        return manager.findList(notice,page, pageSize);
    }

    public List<Notice> getList(Notice notice){
        return manager.findList(notice);
    }

    public Notice get(Long id){
        return manager.get(id);
    }

    /*更新*/
    public void update(Notice notice){
        manager.updateBs(notice);
    }
    public void update(Notice notice, MultipartFile postPic, Map map){
        String imge = upload(postPic);
        System.out.print(imge);
        if(imge==null){
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "更新图片信息失败", map.get(Global.URL).toString(), map);
        }
        notice.setImage(imge);
        manager.updateBs(notice);
    }
    /*新增*/
    public void insert(Notice notice){
        manager.insertBs(notice);
    }
    public void insert(Notice notice,MultipartFile postPic,Map map){
        String imge = upload(postPic);
        System.out.print(imge);
        if(imge==null){
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "更新图片信息失败", map.get(Global.URL).toString(), map);
        }
        notice.setImage(imge);
        manager.insertBs(notice);
    }

    public void delete(Notice notice){
        manager.deleteBs(notice);
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
}
