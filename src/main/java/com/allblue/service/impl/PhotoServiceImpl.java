package com.allblue.service.impl;

import com.allblue.mapper.PhotoMapper;
import com.allblue.model.dto.SearchDTO;
import com.allblue.model.po.Photo;
import com.allblue.service.PhotoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
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

    @Autowired
    private RedisTemplate redisTemplate;

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
        //从缓存中获取数据
        String key = "photo_" + id;
        ValueOperations<String, Photo> operations = redisTemplate.opsForValue();
        //判断是否命中缓存
        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            logger.info("读取redis缓存数据！");
            Photo photo = operations.get(key);
            return photo;
        }
        //从DB中获取数据
        Photo photo = photoMapper.getPhotoDetail(id);
        //插入缓存
        operations.set(key, photo);
        return photo;
    }

    @Override
    public int update(Photo photo) {
        int count = photoMapper.update(photo);
        String key = "photo_" + photo.getId();
        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            logger.info("更新数据,删除缓存！");
            redisTemplate.delete(key);
        }
        return count;
    }

    @Override
    public boolean delete(int id) {
        boolean flag = photoMapper.delete(id);
        String key = "photo_" + id;
        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            logger.info("删除数据,删除缓存");
            redisTemplate.delete(key);
        }
        return flag;
    }

    @Override
    public int getTotalCount(String opts) {
        int count = photoMapper.getTotalCount(opts);
        return count;
    }
}
