package com.zzu.video.service;

import com.alibaba.fastjson.JSONObject;
import com.zzu.video.dao.VideoMapper;
import com.zzu.video.entity.UserInfo;
import com.zzu.video.entity.Video;
import com.zzu.video.utils.RedisKeyUtil;
import com.zzu.video.vo.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 注释
 *
 * @author Coutana
 * @since 2.9.0
 */
@Service
public class VideoService {

    private final VideoMapper videoMapper;
    private final UserService userService;
    private final LikeService likeService;
    private final ShareService shareService;
    private final CommentService commentService;
    @Autowired
    public VideoService(VideoMapper videoMapper,UserService userService,
                        LikeService likeService,ShareService shareService,CommentService commentService) {
        this.videoMapper = videoMapper;
        this.userService = userService;
        this.likeService = likeService;
        this.shareService = shareService;
        this.commentService = commentService;
    }

    public int addVideo(Video video) {
        video.setCreateTime(new Date());
        return videoMapper.insertVideo(video);
    }

    public Video findVideoById(int id) {
        return videoMapper.selectById(id);
    }

    public List<Video> findVideoByUserId(int userId) {
        return videoMapper.selectByUserId(userId);
    }

    public UserInfo findUserInfoByVideoId(int videoId) {
        int userId = videoMapper.selectUserIdByVideoId(videoId);
        return userService.findUserInfoById(userId);
    }
    public List<Video> findVideoByTag(String tag,int offset,int limit) {
        return videoMapper.selectByTag(tag,offset,limit);
    }

    public List<JSONObject> getVideoResponseData(List<Video> videos,int userId) {
        List<JSONObject> list = new ArrayList<>();
        for(Video video:videos) {
            JSONObject jsonObject = new JSONObject();
            Long likeCount = likeService.findVideoLikeCount(video.getVid());
            boolean likeStatus = userId!=0&&likeService.findVideoLikeStatus(userId,video.getVid());
            UserInfo userInfo = userService.findUserInfoById(video.getUid());
            Integer shareCount = shareService.findVideoShareCount(video.getVid());
            int commentCount = commentService.findCommentCountByVideoId(video.getVid());
            jsonObject.put("userInfo",userInfo);
            jsonObject.put("video",video);
            jsonObject.put("likeNum",likeCount);
            jsonObject.put("shareNum",shareCount);
            jsonObject.put("commentNum",commentCount);
            jsonObject.put("status",likeStatus);
            list.add(jsonObject);
        }
        return list;
    }

    public JSONObject getVideoDetail(int id,int userId) {
        Video video = findVideoById(id);
        UserInfo userInfo = findUserInfoByVideoId(id);
        Long likeCount = likeService.findVideoLikeCount(id);
        Integer shareCount = shareService.findVideoShareCount(id);
        int commentCount = commentService.findCommentCountByVideoId(id);
        boolean likeStatus = likeService.findVideoLikeStatus(userId,video.getVid());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("videoInfo",video);
        jsonObject.put("userInfo",userInfo);
        jsonObject.put("likeNum",likeCount);
        jsonObject.put("shareNum",shareCount);
        jsonObject.put("commentNum",commentCount);
        jsonObject.put("status",likeStatus);
        return jsonObject;
    }

}
