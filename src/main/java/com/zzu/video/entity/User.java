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
public class User {
    private int uid;
    private String username;
    private String password;
    private String salt;
    private UserInfo userInfo;
    private Date createTime;
}
