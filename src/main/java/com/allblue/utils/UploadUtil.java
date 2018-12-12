package com.allblue.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @Description: 上传文件，配置tomcat映射物理地址，将文件上传至物理地址
 * @Author Xone
 * @Date 14:24 2018/11/7
 **/
public class UploadUtil {
    private static Logger logger = LoggerFactory.getLogger(UploadUtil.class);

    public static String fileUpload(MultipartFile mf, String path) {
        //获取图片原始名字
        String originalName = mf.getOriginalFilename();
        //上传图片
        if (originalName != null && originalName.length() > 0) {
            //生成uuid作为文件名称
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            //图片新名称
            String newPhotoName = uuid + originalName.substring(originalName.lastIndexOf("."));
            //新图片生成
            File file = new File(path, newPhotoName);
            //判断路径是否存在
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            //将内存中的图片写入磁盘
            try {
                mf.transferTo(file);
                logger.info("文件" + newPhotoName + "写入磁盘" + path + "成功！");
                return newPhotoName;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        logger.info("文件写入磁盘" + path + "失败！！！");
        return null;
    }
}
