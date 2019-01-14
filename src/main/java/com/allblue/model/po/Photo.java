package com.allblue.model.po;

/**
 * @Description: ...
 * @Author: Xone
 * @Date: 2019/1/14 16:35
 **/
public class Photo {
    private int id;
    private String shootingTitle;
    private String shootingLocation;
    private String shootingTime;
    private String shootingPhoto;
    private String description;
    private String video;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShootingTitle() {
        return shootingTitle;
    }

    public void setShootingTitle(String shootingTitle) {
        this.shootingTitle = shootingTitle;
    }

    public String getShootingLocation() {
        return shootingLocation;
    }

    public void setShootingLocation(String shootingLocation) {
        this.shootingLocation = shootingLocation;
    }

    public String getShootingTime() {
        return shootingTime;
    }

    public void setShootingTime(String shootingTime) {
        this.shootingTime = shootingTime;
    }

    public String getShootingPhoto() {
        return shootingPhoto;
    }

    public void setShootingPhoto(String shootingPhoto) {
        this.shootingPhoto = shootingPhoto;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "id=" + id +
                ", shootingTitle='" + shootingTitle + '\'' +
                ", shootingLocation='" + shootingLocation + '\'' +
                ", shootingTime='" + shootingTime + '\'' +
                ", shootingPhoto='" + shootingPhoto + '\'' +
                ", description='" + description + '\'' +
                ", video='" + video + '\'' +
                '}';
    }
}
