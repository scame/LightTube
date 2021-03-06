package com.example.scame.lighttube.presentation.presenters;


import android.util.Log;

import com.example.scame.lighttube.domain.usecases.DefaultSubscriber;
import com.example.scame.lighttube.domain.usecases.DeleteCommentUseCase;
import com.example.scame.lighttube.domain.usecases.GetRepliesUseCase;
import com.example.scame.lighttube.domain.usecases.MarkAsSpamUseCase;
import com.example.scame.lighttube.domain.usecases.UpdateReplyUseCase;
import com.example.scame.lighttube.domain.usecases.UpdateCommentUseCase;
import com.example.scame.lighttube.presentation.model.RepliesWrapper;
import com.example.scame.lighttube.presentation.model.ReplyModel;
import com.example.scame.lighttube.presentation.model.ThreadCommentModel;

public class RepliesPresenterImp<T extends RepliesPresenter.RepliesView> implements RepliesPresenter<T> {

    private GetRepliesUseCase getRepliesUseCase;

    private DeleteCommentUseCase deleteCommentUseCase;

    private MarkAsSpamUseCase markAsSpamUseCase;

    private UpdateReplyUseCase updateReplyUseCase;

    private UpdateCommentUseCase updatePrimaryUseCase;

    private SubscriptionsHandler subscriptionsHandler;

    private String userIdentifier;

    private T view;

    public RepliesPresenterImp(GetRepliesUseCase getRepliesUseCase,
                               DeleteCommentUseCase deleteCommentUseCase,
                               MarkAsSpamUseCase markAsSpamUseCase,
                               UpdateReplyUseCase updateReplyUseCase,
                               UpdateCommentUseCase updatePrimaryUseCase,
                               SubscriptionsHandler subscriptionsHandler) {
        this.markAsSpamUseCase = markAsSpamUseCase;
        this.updatePrimaryUseCase = updatePrimaryUseCase;
        this.updateReplyUseCase = updateReplyUseCase;
        this.getRepliesUseCase = getRepliesUseCase;
        this.deleteCommentUseCase = deleteCommentUseCase;
        this.subscriptionsHandler = subscriptionsHandler;
    }

    @Override
    public void getRepliesList(String parentId, int page) {
        getRepliesUseCase.setParentId(parentId);
        getRepliesUseCase.setPage(page);
        getRepliesUseCase.execute(new RepliesSubscriber());
    }

    @Override
    public void deleteComment(String replyId) {
        deleteCommentUseCase.setCommentId(replyId);
        deleteCommentUseCase.execute(new DeletionSubscriber());
    }

    @Override
    public void markAsSpam(String replyId) {
        markAsSpamUseCase.setCommentId(replyId);
        markAsSpamUseCase.execute(new SpamSubscriber());
    }

    @Override
    public void updateReply(String replyId, String updatedText, String userIdentifier) {
        this.userIdentifier = userIdentifier;
        updateReplyUseCase.setReplyId(replyId);
        updateReplyUseCase.setUpdatedText(updatedText);
        updateReplyUseCase.execute(new UpdateSubscriber());
    }

    @Override
    public void updatePrimaryComment(String commentId, String updatedText) {
        updatePrimaryUseCase.setCommentId(commentId);
        updatePrimaryUseCase.setUpdatedText(updatedText);
        updatePrimaryUseCase.execute(new UpdatePrimarySubscriber());
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

    private final class UpdatePrimarySubscriber extends DefaultSubscriber<ThreadCommentModel> {

        @Override
        public void onNext(ThreadCommentModel threadCommentModel) {
            super.onNext(threadCommentModel);

            if (view != null) {
                view.onUpdatedPrimaryComment(threadCommentModel);
            }
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            Log.i("onxUpdatePrimaryErr", e.getLocalizedMessage());
        }
    }

    private final class RepliesSubscriber extends DefaultSubscriber<RepliesWrapper> {

        @Override
        public void onNext(RepliesWrapper repliesWrapper) {
            super.onNext(repliesWrapper);

            if (view != null) {
                view.displayReplies(repliesWrapper.getReplyModels(), repliesWrapper.getPage());
            }
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);

            Log.i("onxRepliesError", e.getLocalizedMessage());
        }
    }

    private final class DeletionSubscriber extends DefaultSubscriber<String> {

        @Override
        public void onNext(String deletedCommentId) {
            super.onNext(deletedCommentId);

            if (view != null) {
                view.onDeletedComment(deletedCommentId);
            }
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            Log.i("onxDeletionErr", e.getLocalizedMessage());
        }
    }

    private final class SpamSubscriber extends DefaultSubscriber<String> {

        @Override
        public void onNext(String markedCommentId) {
            super.onNext(markedCommentId);

            if (view != null) {
                view.onMarkedAsSpam(markedCommentId);
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
                view.onUpdatedReply(replyModel);               // doesn't contain authorChannelId, so excluding setting it by hand
            }                                                  // we can get this info only by making an additional request
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            Log.i("onxUpdateErr", e.getLocalizedMessage());
        }
    }
}
