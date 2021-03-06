package com.example.scame.lighttube.domain.usecases;


import com.example.scame.lighttube.data.repository.CommentsRepository;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;

import rx.Observable;

public class DeleteCommentUseCase extends UseCase<String> {

    private CommentsRepository dataManager;

    private String commentId;

    public DeleteCommentUseCase(SubscribeOn subscribeOn, ObserveOn observeOn, CommentsRepository dataManager) {
        super(subscribeOn, observeOn);
        this.dataManager = dataManager;
    }

    @Override
    protected Observable<String> getUseCaseObservable() {
        return dataManager.deleteComment(commentId);
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getCommentId() {
        return commentId;
    }
}
