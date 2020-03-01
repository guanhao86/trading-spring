package com.spring.free.service;/**
 * Created by hengpu on 2019/2/25.
 */

import com.aliyun.oss.OSSClient;
import com.github.pagehelper.PageInfo;
import com.spring.free.domain.Fee;
import com.spring.free.manager.FeeManager;
import com.spring.free.util.OSSClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @ClassName ContentService
 * @Description //TODO
 * @Author hengpu
 * @Date 2019/2/25 15:25
 * @Version 1.0
 **/
@Service
public class FeeService {

    @Autowired
    private FeeManager manager;

    /**
     * @Author bianyx
     * @Description //TODO hengpu
     * @Date 9:30 2019/3/1
     * @Param [content, page, pageSize]
     * @return com.github.pagehelper.PageInfo<com.hengpu.national.domain.Content>
     **/
    public PageInfo<Fee> pageList(Fee fee, Integer page, Integer pageSize){
        return manager.findList(fee,page, pageSize);
    }
    public List<Fee> getList(Fee fee){
        return manager.findList(fee);
    }

    public Fee get(Long id){
        return manager.get(id);
    }

    /*更新*/
    public void update(Fee fee){
        manager.updateBs(fee);
    }
    /*public void update(Fee fee, MultipartFile postPic, Map map){
        String imge = upload(postPic);
        System.out.print(imge);
        if(imge==null){
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "更新图片信息失败", map.get(Global.URL).toString(), map);
        }
        //fee.setImage(imge);
        manager.updateBs(fee);
    }*/
    /*新增*/
    public void insert(Fee fee){
        manager.insertBs(fee);
    }
    public void delete(Fee fee){
        manager.deleteBs(fee);
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

//    public void insert(Fee fee,MultipartFile postPic,Map map){
//        String imge = upload(postPic);
//        System.out.print(imge);
//        if(imge==null){
//            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "更新图片信息失败", map.get(Global.URL).toString(), map);
//        }
//        //fee.setImage(imge);
//        manager.insertBs(fee);
//    }


    public Fee getByType(Integer type){
            Fee fee=new Fee();
            fee.setType(type);
        List<Fee> list= manager.findList(fee);
        if(list.size()>0){
            return  list.get(0);
        }else{
            return  null;
        }
    }
}
