package com.spring.free.common.service;/**
 * Created by hengpu on 2019/2/28.
 */

import com.aliyun.oss.OSSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import java.io.ByteArrayInputStream;
import java.util.UUID;

/**
 * @ClassName ImageUploadService
 * @Description //TODO
 * @Author hengpu
 * @Date 2019/2/28 10:07
 * @Version 1.0
 **/
@Service
public class ImageUploadService {

//    public static String Endpoint = "http://oss-cn-beijing.aliyuncs.com";
//    // 编码
//    public static String AccessKeyId = "LTAI7V3HsWkA80T7";
//    // 返回格式
//    public static String AccessKeySecret = "oHrr6MCChxN2jIY7ZHvJzK8oPsauUE";
//
//    public static String BucketName = "qmst-image";
//
//    public static String bucketCDN = "https://qmst-image.oss-cn-beijing.aliyuncs.com";

    @Value("${image.upload.Endpoint}")
    private  String Endpoint;
    @Value("${image.upload.AccessKeyId}")
    private  String AccessKeyId;
    @Value("${image.upload.AccessKeySecret}")
    private  String AccessKeySecret;
    @Value("${image.upload.BucketName}")
    private  String BucketName;
    @Value("${image.upload.bucketCDN}")
    private  String bucketCDN;


    public String upload( MultipartFile pic){
        OSSClient ossClient = new OSSClient(Endpoint, AccessKeyId, AccessKeySecret);
        // 上传文件流。
        String fileExt = getExtensionName(pic.getOriginalFilename());
        String imageName = System.nanoTime()+"";
        String fileName = imageName + "." + fileExt;
        try{
            ossClient.putObject(BucketName, fileName, pic.getInputStream());
        }catch(Exception e){

            System.out.println("上传失败，写入文件失败，"+e.getMessage());
            e.printStackTrace();
            return null;

        }

        String realpath = bucketCDN+"/"+fileName;

        // 关闭OSSClient。
        ossClient.shutdown();

        return realpath;
    }
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }

    public  String ImageUploadRest(byte[] bs,String tempFileName) throws Exception{
        try
        {
            OSSClient ossClient = new OSSClient(Endpoint, AccessKeyId, AccessKeySecret);
            // 上传文件流。
            ossClient.putObject(BucketName, tempFileName, new ByteArrayInputStream(bs));
            String realpath = bucketCDN+"/"+tempFileName;

            // 关闭OSSClient。
            ossClient.shutdown();
            return realpath;
        }catch (Exception e)
        {
            throw new Exception(e);
        }


    }

    public  String ImageUpload(String base64Data) throws Exception{
        String realpath = "";
            String dataPrix = "";
            String data = "";
            if(base64Data == null || "".equals(base64Data)){
                throw new Exception("上传失败，上传图片数据为空");
            }else{
                data=base64Data;

            }
            String suffix = ".jpg";

            String tempFileName = UUID.randomUUID().toString() + suffix;

            //因为BASE64Decoder的jar问题，此处使用spring框架提供的工具包
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] bs = decoder.decodeBuffer(data);


            try{


                realpath = ImageUploadRest(bs,tempFileName);

                return realpath;
            }catch(Exception ee){
                throw new Exception("上传失败，写入文件失败，"+ee.getMessage());
            }

    }
}
