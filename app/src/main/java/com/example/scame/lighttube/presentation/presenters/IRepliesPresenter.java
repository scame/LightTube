package com.example.scame.lighttube.presentation.presenters;


import com.example.scame.lighttube.presentation.model.ReplyModel;
import com.example.scame.lighttube.presentation.model.ThreadCommentModel;

import java.util.List;

public interface IRepliesPresenter<T> extends Presenter<T> {

    interface RepliesView {

        void displayReplies(List<ReplyModel> repliesList);

        void onDeletedComment(String deletedCommentId);

        void onMarkedAsSpam(String markedAsSpamId);

        void onUpdatedReply(ReplyModel replyModel);

        void onUpdatedPrimaryComment(ThreadCommentModel threadCommentModel);
    }

    void getRepliesList(String parentId);

    void deleteComment(String replyId);

    void markAsSpam(String replyId);

    void updateReply(String replyId, String updatedText, String userIdentifier);

    void updatePrimaryComment(String commentId, String updatedText);
}
