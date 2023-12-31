package com.zzu.video.entity;

import lombok.Data;

import java.util.Date;

/**
 * 注释
 *
 * @author Coutana
 * @since 2.9.0
 */
@Data
public class Video {
    private int vid;
    private int uid;
    private String url;
    private String cover;
    private int duration;
    private String tag;
    private String description;
    private Date createTime;
}
