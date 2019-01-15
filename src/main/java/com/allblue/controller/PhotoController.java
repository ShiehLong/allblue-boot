package com.allblue.controller;

import com.allblue.model.dto.ResultInfo;
import com.allblue.model.dto.SearchDTO;
import com.allblue.model.po.Photo;
import com.allblue.service.PhotoService;
import com.allblue.utils.UploadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Description:
 * @Author Xone
 * @Date 17:26 2018/8/31
 **/
@RestController
@RequestMapping("/photo")
public class PhotoController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${allblue.PhotoPath}")
    private String PhotoPath;

    @Autowired
    private PhotoService photoService;


    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResultInfo list(
            @RequestParam(value = "searchContext", required = false) String opts,
            @RequestParam(value = "sort", required = false) String sort,
            @RequestParam(value = "order", required = false) String order,
            @RequestParam(value = "offset", required = false) Integer offset,
            @RequestParam(value = "limit", required = false) Integer limit) {

        if(opts == null){
            opts = "";
        }
        //获取数量
        int totalCount = photoService.getTotalCount(opts);
        if (totalCount > 0) {
            if (offset == null || limit == null || sort == null || order == null) {
                List<Photo> list = photoService.getPhotoList();
                return ResultInfo.success(Integer.toString(totalCount), list);
            } else {
                //设置参数
                SearchDTO searchDTO = new SearchDTO();
                searchDTO.setOffset(offset);
                searchDTO.setLimit(limit);
                searchDTO.setSearchContext(opts);
                searchDTO.setSortName(sort);
                searchDTO.setSortOrder(order);
                //获取信息列表
                List<Photo> list = photoService.getPhotoListByDTO(searchDTO);
                return ResultInfo.success(Integer.toString(totalCount), list);
            }
        }
        return ResultInfo.error("无数据");

    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResultInfo upload(
            @RequestParam(value = "uploadImage", required = false) MultipartFile photo) {
        //上传照片
        String newName = UploadUtil.fileUpload(photo, PhotoPath);
        String url = "/photos/photo/" + newName;
        return ResultInfo.success(url);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResultInfo save(@RequestBody Photo photo) {

        boolean isSave = false;
        //入参判断
        if (photo.getShootingTitle() == null || "".equals(photo.getShootingTitle()) ||
                photo.getShootingLocation() == null || "".equals(photo.getShootingLocation()) ||
                photo.getShootingTime() == null || "".equals(photo.getShootingTime()) ||
                photo.getShootingPhoto() == null || "".equals(photo.getShootingPhoto())) {
            return ResultInfo.error("信息不完整");
        }
        if (photo.getId() == 0) {
            int id = photoService.add(photo);
            if (id != 0)
                isSave = true;
        } else {
            int count = photoService.update(photo);
            if (count != 0)
                isSave = true;
        }
        if (isSave) {
            return ResultInfo.success("保存照片成功");
        } else {
            return ResultInfo.error("保存照片失败");
        }
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public ResultInfo detail(@PathVariable("id") int id) {
        //判断用户ID
        if (id == 0) {
            return ResultInfo.error("ID错误");
        }
        Photo PhotoInfo = photoService.getPhotoDerail(id);
        logger.info("查询到信息如下：" + PhotoInfo);
        if (StringUtils.isEmpty(PhotoInfo)) {
            return ResultInfo.error("信息为空");
        }
        return ResultInfo.success("success", PhotoInfo);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ResultInfo PhotoDelete(@PathVariable("id") int id) {
        if (id == 0) {
            return ResultInfo.error("ID错误");
        }
        boolean flag = photoService.delete(id);
        if (flag) {
            return ResultInfo.success("删除成功");
        } else {
            return ResultInfo.error("删除失败");
        }
    }

}
