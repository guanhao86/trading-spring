package com.spring.free.service;

import com.aliyun.oss.OSSClient;
import com.github.pagehelper.PageInfo;
import com.spring.free.domain.News;
import com.spring.free.manager.NewsManager;
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
public class NewsService {

    @Autowired
    private NewsManager manager;

    public PageInfo<News> pageList(News news, Integer page, Integer pageSize){
        return manager.findList(news,page, pageSize);
    }

    public List<News> getList(News news){
        return manager.findList(news);
    }

    public News get(Long id){
        return manager.get(id);
    }

    /*更新*/
    public void update(News news){
        manager.updateBs(news);
    }
    public void update(News news, MultipartFile bigGhPic1,
                       MultipartFile ghPic1,MultipartFile ghPic2,MultipartFile ghPic3 ,Map map){

        if(bigGhPic1!=null){
            String bigImge = upload(bigGhPic1);
            System.out.print(bigImge);
            if(bigImge==null){
                throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "更新图片信息失败", map.get(Global.URL).toString(), map);
            }
            news.setBigImage(bigImge);
        }

        if(ghPic1!=null){
            String imge1 = upload(ghPic1);
            System.out.print(imge1);
            if(imge1==null){
                throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "更新图片信息失败", map.get(Global.URL).toString(), map);
            }
            news.setImage1(imge1);
        }

        if(ghPic2!=null){
            String imge2 = upload(ghPic2);
            System.out.print(imge2);
            if(imge2==null){
                throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "更新图片信息失败", map.get(Global.URL).toString(), map);
            }
            news.setImage2(imge2);
        }

        if(ghPic3!=null){
            String imge3 = upload(ghPic3);
            System.out.print(imge3);
            if(imge3==null){
                throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "更新图片信息失败", map.get(Global.URL).toString(), map);
            }
            news.setImage3(imge3);
        }





        manager.updateBs(news);
    }
    /*新增*/
    public void insert(News news){
        manager.insertBs(news);
    }
    public void insert(News news,MultipartFile bigGhPic1,
                       MultipartFile ghPic1,MultipartFile ghPic2,MultipartFile ghPic3 ,Map map){
        String bigImge = upload(bigGhPic1);
        System.out.print(bigImge);
        if(bigImge==null){
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "更新图片信息失败", map.get(Global.URL).toString(), map);
        }
        String imge1 = upload(ghPic1);
        System.out.print(imge1);
        if(imge1==null){
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "更新图片信息失败", map.get(Global.URL).toString(), map);
        }
        String imge2 = upload(ghPic2);
        System.out.print(imge2);
        if(imge2==null){
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "更新图片信息失败", map.get(Global.URL).toString(), map);
        }
        String imge3 = upload(ghPic3);
        System.out.print(imge3);
        if(imge3==null){
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "更新图片信息失败", map.get(Global.URL).toString(), map);
        }
        news.setBigImage(bigImge);
        news.setImage1(imge1);
        news.setImage2(imge2);
        news.setImage3(imge3);
        manager.insertBs(news);
    }

    public void delete(News news){
        manager.deleteBs(news);
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
