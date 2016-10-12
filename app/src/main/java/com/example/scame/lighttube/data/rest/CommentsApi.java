package com.example.scame.lighttube.data.rest;


import com.example.scame.lighttube.data.entities.comments.requests.ThreadCommentBody;
import com.example.scame.lighttube.data.entities.comments.responses.CommentItem;
import com.example.scame.lighttube.data.entities.comments.responses.CommentThreadsEntity;
import com.example.scame.lighttube.data.entities.replies.ReplyEntity;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface CommentsApi {

    @GET("youtube/v3/commentThreads")
    Observable<CommentThreadsEntity> getCommentThreads(@Query(encoded = true, value = "part") String part,
                                                       @Query("maxResults") Integer maxResults,
                                                       @Query("pageToken") String pageToken,
                                                       @Query("textFormat") String textFormat,
                                                       @Query("videoId") String videoId,
                                                       @Query("key") String key);


    @POST("youtube/v3/commentThreads")
    Observable<CommentItem> postThreadComment(@Query("part") String part,
                                              @Query("key") String key,
                                              @Body ThreadCommentBody body);


    @GET("youtube/v3/comments")
    Observable<ReplyEntity> getReplies(@Query("part") String part,
                                       @Query("maxResults") Integer maxResults,
                                       @Query("textFormat") String textFormat,
                                       @Query("pageToken") String pageToken,
                                       @Query("parentId") String parentId,
                                       @Query("key") String key);
}
