package com.example.scame.lighttube.data.repository;


import android.support.annotation.StringDef;

import com.example.scame.lighttube.PrivateValues;
import com.example.scame.lighttube.data.mappers.CommentListMapper;
import com.example.scame.lighttube.data.mappers.ReplyListMapper;
import com.example.scame.lighttube.data.mappers.ReplyPostBuilder;
import com.example.scame.lighttube.data.mappers.ReplyResponseMapper;
import com.example.scame.lighttube.data.mappers.ReplyUpdateBuilder;
import com.example.scame.lighttube.data.mappers.ThreadPostBuilder;
import com.example.scame.lighttube.data.mappers.ThreadResponseMapper;
import com.example.scame.lighttube.data.mappers.ThreadUpdateBuilder;
import com.example.scame.lighttube.data.rest.CommentsApi;
import com.example.scame.lighttube.presentation.model.RepliesWrapper;
import com.example.scame.lighttube.presentation.model.ReplyModel;
import com.example.scame.lighttube.presentation.model.ThreadCommentModel;
import com.example.scame.lighttube.presentation.model.ThreadCommentsWrapper;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import rx.Observable;

public class CommentsRepositoryImp implements CommentsRepository {

    public static final String RELEVANCE_ORDER = "relevance";

    public static final String TIME_ORDER = "time";

    private static final String SNIPPET_AND_REPLIES_PART = "snippet%2Creplies";

    private static final String SNIPPET_PART = "snippet";

    private static final int MAX_RES = 50;

    private static final String TEXT_FORMAT = "plainText";

    private CommentsApi commentsApi;

    private CommentListMapper commentListMapper;

    private ReplyListMapper replyListMapper;

    private ThreadResponseMapper threadResponseMapper;

    private ThreadPostBuilder threadPostBuilder;

    private ReplyPostBuilder replyPostBuilder;

    private ReplyResponseMapper replyResponseMapper;

    private ReplyUpdateBuilder replyUpdateBuilder;

    private ThreadUpdateBuilder threadUpdateBuilder;

    private PaginationUtility commentsPaginationUtility;

    private PaginationUtility repliesPaginationUtility;

    @StringDef ({RELEVANCE_ORDER, TIME_ORDER})
    @Retention(RetentionPolicy.SOURCE)
    public @interface CommentsOrders { }

    public CommentsRepositoryImp(CommentsApi commentsApi, CommentListMapper commentListMapper,
                                 ReplyListMapper replyListMapper, ThreadResponseMapper threadResponseMapper,
                                 ThreadPostBuilder threadPostBuilder, ReplyPostBuilder replyPostBuilder,
                                 ReplyResponseMapper replyResponseMapper, ReplyUpdateBuilder replyUpdateBuilder,
                                 ThreadUpdateBuilder threadUpdateBuilder, PaginationUtility commentsPaginationUtility,
                                 PaginationUtility repliesPaginationUtility) {
        this.commentListMapper = commentListMapper;
        this.replyListMapper = replyListMapper;
        this.commentsApi = commentsApi;
        this.threadResponseMapper = threadResponseMapper;
        this.threadPostBuilder = threadPostBuilder;
        this.replyPostBuilder = replyPostBuilder;
        this.replyResponseMapper = replyResponseMapper;
        this.replyUpdateBuilder = replyUpdateBuilder;
        this.threadUpdateBuilder = threadUpdateBuilder;

        this.commentsPaginationUtility = commentsPaginationUtility;
        this.repliesPaginationUtility = repliesPaginationUtility;
    }

    @Override
    public Observable<ThreadCommentsWrapper> getCommentList(String videoId, @CommentsOrders String order, int page) {
        return commentsApi.getCommentThreads(SNIPPET_AND_REPLIES_PART, order, MAX_RES,
                commentsPaginationUtility.getNextPageToken(page), TEXT_FORMAT, videoId, PrivateValues.API_KEY)
                .doOnNext(commentThreadsEntity -> {
                    commentsPaginationUtility.saveCurrentPage(page);
                    commentsPaginationUtility.saveNextPageToken(commentThreadsEntity.getNextPageToken());
                }).map(commentThreadsEntity -> commentListMapper.convert(commentThreadsEntity, order));
    }

    @Override
    public Observable<RepliesWrapper> getReplyList(String parentId, int page) {
        return commentsApi.getReplies(SNIPPET_PART, MAX_RES, TEXT_FORMAT, repliesPaginationUtility.getNextPageToken(page),
                parentId, PrivateValues.API_KEY)
                .doOnNext(replyEntity -> {
                    repliesPaginationUtility.saveCurrentPage(page);
                    repliesPaginationUtility.saveNextPageToken(replyEntity.getNextPageToken());
                }).map(replyEntity -> replyListMapper.convert(replyEntity, page));
    }

    @Override
    public Observable<ThreadCommentModel> postThreadComment(String text, String videoId) {
        return commentsApi.postThreadComment(SNIPPET_PART, PrivateValues.API_KEY, threadPostBuilder.build(text, videoId))
                .map(threadResponseMapper::convert);
    }

    @Override
    public Observable<ReplyModel> postReply(String replyText, String parentId) {
        return commentsApi.postReply(SNIPPET_PART, PrivateValues.API_KEY, replyPostBuilder.build(parentId, replyText))
                .map(replyResponseMapper::convert);
    }

    @Override
    public Observable<String> deleteComment(String commentId) {
        return commentsApi.deleteComment(commentId, PrivateValues.API_KEY).map(aVoid -> commentId);
    }

    @Override
    public Observable<ReplyModel> updateReply(String replyText, String replyId) {
        return commentsApi.updateReply(SNIPPET_PART, PrivateValues.API_KEY,
                replyUpdateBuilder.build(replyText, replyId))
                .map(replyResponseMapper::convert);
    }

    @Override
    public Observable<ThreadCommentModel> updateThreadComment(String updatedText, String commentId) {
        return commentsApi.updateThreadComment(SNIPPET_PART, PrivateValues.API_KEY,
                threadUpdateBuilder.build(updatedText, commentId))
                .map(threadResponseMapper::convert);
    }

    @Override
    public Observable<String> markAsSpam(String commentId) {
        return commentsApi.markAsSpam(commentId, PrivateValues.API_KEY).map(aVoid -> commentId);
    }
}
