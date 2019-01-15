package com.allblue.service.impl;

import com.allblue.mapper.PhotoMapper;
import com.allblue.model.dto.SearchDTO;
import com.allblue.model.po.Photo;
import com.allblue.service.PhotoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: ...
 * @Author: Xone
 * @Date: 2019/1/14 17:03
 **/
@Service
public class PhotoServiceImpl implements PhotoService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PhotoMapper photoMapper;

    @Override
    public List<Photo> getPhotoListByDTO(SearchDTO searchDTO) {
        List<Photo> list = photoMapper.getPhotoListByDTO(searchDTO);
        return list;
    }

    @Override
    public List<Photo> getPhotoList() {
        List<Photo> list = photoMapper.getPhotoList();
        return list;
    }

    @Override
    public int add(Photo photo) {
        int id = photoMapper.add(photo);
        return id;
    }

    @Override
    public Photo getPhotoDerail(int id) {
        Photo photo = photoMapper.getPhotoDetail(id);
        return photo;
    }

    @Override
    public int update(Photo photo) {
        int count = photoMapper.update(photo);
        return count;
    }

    @Override
    public boolean delete(int id) {
        boolean flag = photoMapper.delete(id);
        return flag;
    }

    @Override
    public int getTotalCount(String opts) {
        int count = photoMapper.getTotalCount(opts);
        return count;
    }
}
