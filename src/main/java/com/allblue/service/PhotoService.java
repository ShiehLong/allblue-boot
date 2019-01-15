package com.allblue.service;

import com.allblue.model.dto.SearchDTO;
import com.allblue.model.po.Photo;

import java.util.List;

/**
 * @Description: ...
 * @Author: Xone
 * @Date: 2019/1/14 17:03
 **/
public interface PhotoService {
    List<Photo> getPhotoListByDTO(SearchDTO searchDTO);

    List<Photo> getPhotoList();

    int add(Photo photo);

    Photo getPhotoDerail(int id);

    int update(Photo photo);

    boolean delete(int id);

    int getTotalCount(String opts);
}
