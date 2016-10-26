package com.example.scame.lighttube.presentation.presenters;


import android.util.Log;

import com.example.scame.lighttube.domain.usecases.DefaultSubscriber;
import com.example.scame.lighttube.domain.usecases.DeleteCommentUseCase;
import com.example.scame.lighttube.domain.usecases.MarkAsSpamUseCase;
import com.example.scame.lighttube.domain.usecases.RetrieveRepliesUseCase;
import com.example.scame.lighttube.domain.usecases.UpdateReplyUseCase;
import com.example.scame.lighttube.presentation.model.ReplyListModel;
import com.example.scame.lighttube.presentation.model.ReplyModel;

public class RepliesPresenterImp<T extends IRepliesPresenter.RepliesView> implements IRepliesPresenter<T> {

    private RetrieveRepliesUseCase retrieveRepliesUseCase;

    private DeleteCommentUseCase deleteCommentUseCase;

    private MarkAsSpamUseCase markAsSpamUseCase;

    private UpdateReplyUseCase updateReplyUseCase;

    private SubscriptionsHandler subscriptionsHandler;

    private String userIdentifier;

    // TODO: indexes should be assigned to each type of use cases, otherwise can be overwritten
    private int index;

    private T view;

    public RepliesPresenterImp(RetrieveRepliesUseCase retrieveRepliesUseCase,
                               DeleteCommentUseCase deleteCommentUseCase,
                               MarkAsSpamUseCase markAsSpamUseCase,
                               UpdateReplyUseCase updateReplyUseCase,
                               SubscriptionsHandler subscriptionsHandler) {
        this.markAsSpamUseCase = markAsSpamUseCase;
        this.updateReplyUseCase = updateReplyUseCase;
        this.retrieveRepliesUseCase = retrieveRepliesUseCase;
        this.deleteCommentUseCase = deleteCommentUseCase;
        this.subscriptionsHandler = subscriptionsHandler;
    }

    @Override
    public void getRepliesList(String parentId) {
        retrieveRepliesUseCase.setParentId(parentId);
        retrieveRepliesUseCase.execute(new RepliesSubscriber());
    }

    @Override
    public void deleteReply(String replyId, int position) {
        index = position;
        deleteCommentUseCase.setCommentId(replyId);
        deleteCommentUseCase.execute(new DeletionSubscriber());
    }

    @Override
    public void markAsSpam(String replyId, int position) {
        index = position;
        markAsSpamUseCase.setCommentId(replyId);
        markAsSpamUseCase.execute(new SpamSubscriber());
    }

    @Override
    public void updateReply(String replyId, String updatedText, int position, String userIdentifier) {
        index = position;
        this.userIdentifier = userIdentifier;
        updateReplyUseCase.setReplyId(replyId);
        updateReplyUseCase.setUpdatedText(updatedText);
        updateReplyUseCase.execute(new UpdateSubscriber());
    }

    @Override
    public void setView(T view) {
        this.view = view;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        view = null;
        subscriptionsHandler.unsubscribe();
    }

    private final class RepliesSubscriber extends DefaultSubscriber<ReplyListModel> {

        @Override
        public void onNext(ReplyListModel replyListModel) {
            super.onNext(replyListModel);

            if (view != null) {
                view.displayReplies(replyListModel);
            }
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);

            Log.i("onxRepliesError", e.getLocalizedMessage());
        }
    }

    private final class DeletionSubscriber extends DefaultSubscriber<Void> {

        @Override
        public void onCompleted() {
            super.onCompleted();

            if (view != null) {
                view.onDeletedReply(index);
            }
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            Log.i("onxDeletionErr", e.getLocalizedMessage());
        }
    }

    private final class SpamSubscriber extends DefaultSubscriber<Void> {

        @Override
        public void onCompleted() {
            super.onCompleted();

            if (view != null) {
                view.onMarkedAsSpam(index);
            }
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            Log.i("onxSpamErr", e.getLocalizedMessage());
        }
    }

    private final class UpdateSubscriber extends DefaultSubscriber<ReplyModel> {

        @Override
        public void onNext(ReplyModel replyModel) {
            super.onNext(replyModel);

            if (view != null) {
                replyModel.setAuthorChannelId(userIdentifier); // again, thanks to Youtube Data API, reply update response
                view.onUpdatedReply(index, replyModel);        // doesn't contain authorChannelId, so except setting it by hand
            }                                                  // we can get this info only by making an additional request
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            Log.i("onxUpdateErr", e.getLocalizedMessage());
        }
    }
}
