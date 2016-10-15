package com.example.scame.lighttube.domain.usecases;


import com.example.scame.lighttube.data.repository.ICommentsDataManager;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;
import com.example.scame.lighttube.presentation.model.ReplyModel;

import rx.Observable;

public class UpdateReplyUseCase extends UseCase<ReplyModel> {

    private ICommentsDataManager dataManager;

    private String updatedText;

    private String replyId;

    public UpdateReplyUseCase(SubscribeOn subscribeOn, ObserveOn observeOn, ICommentsDataManager dataManager) {
        super(subscribeOn, observeOn);
        this.dataManager = dataManager;
    }

    @Override
    protected Observable<ReplyModel> getUseCaseObservable() {
        return dataManager.updateReply(updatedText, replyId);
    }

    public void setUpdatedText(String updatedText) {
        this.updatedText = updatedText;
    }

    public void setReplyId(String replyId) {
        this.replyId = replyId;
    }
}
