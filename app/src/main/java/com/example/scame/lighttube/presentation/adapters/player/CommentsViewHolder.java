package com.example.scame.lighttube.presentation.adapters.player;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.scame.lighttube.R;
import com.example.scame.lighttube.presentation.model.ThreadCommentModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class CommentsViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.thread_comment_tv) TextView threadCommentTv;

    @BindView(R.id.first_reply_tv) TextView firstReplyTv;

    @BindView(R.id.second_reply_tv) TextView secondReplyTv;

    @BindView(R.id.all_replies_tv) TextView allRepliesTv;

    CommentsViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }

    void bindThreadCommentView(int position, List<ThreadCommentModel> comments) {
        threadCommentTv.setText(comments.get(position - 1).getTextDisplay());

        firstReplyTv.setVisibility(View.GONE);
        secondReplyTv.setVisibility(View.GONE);
        allRepliesTv.setVisibility(View.GONE);
    }

    void bindOneReplyView(int position, List<ThreadCommentModel> comments) {
        int replyCount = comments.get(position - 1).getReplyCount();

        threadCommentTv.setText(comments.get(position - 1).getTextDisplay());
        firstReplyTv.setText(comments.get(position - 1).getReplies().get(replyCount - 1).getTextDisplay());

        secondReplyTv.setVisibility(View.GONE);
        allRepliesTv.setVisibility(View.GONE);
    }

    void bindTwoRepliesView(int position, List<ThreadCommentModel> comments) {
        int replyCount = comments.get(position - 1).getReplyCount();

        threadCommentTv.setText(comments.get(position - 1).getTextDisplay());
        firstReplyTv.setText(comments.get(position - 1).getReplies().get(replyCount - 2).getTextDisplay());
        secondReplyTv.setText(comments.get(position - 1).getReplies().get(replyCount - 1).getTextDisplay());

        allRepliesTv.setVisibility(View.GONE);
    }

    void bindAllRepliesView(int position, List<ThreadCommentModel> comments) {
        int replyCount = comments.get(position -1).getReplyCount();

        threadCommentTv.setText(comments.get(position - 1).getTextDisplay());
        firstReplyTv.setText(comments.get(position - 1).getReplies().get(replyCount - 2).getTextDisplay());
        secondReplyTv.setText(comments.get(position - 1).getReplies().get(replyCount - 1).getTextDisplay());
        allRepliesTv.setText("SHOW ALL REPLIES");
    }
}