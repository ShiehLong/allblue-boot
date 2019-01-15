package com.allblue.mapper;

import com.allblue.model.dto.SearchDTO;
import com.allblue.model.po.Photo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: ...
 * @Author: Xone
 * @Date: 2019/1/14 17:10
 **/
@Repository
public interface PhotoMapper {
    List<Photo> getPhotoList();

    int add(Photo photo);

    Photo getPhotoDetail(int id);

    int update(Photo photo);

    boolean delete(int id);

    int getTotalCount(String opts);

    List<Photo> getPhotoListByDTO(SearchDTO searchDTO);
}
