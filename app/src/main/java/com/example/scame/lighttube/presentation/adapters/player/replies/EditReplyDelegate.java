package com.example.scame.lighttube.presentation.adapters.player.replies;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.scame.lighttube.R;
import com.example.scame.lighttube.presentation.adapters.player.AdapterDelegate;
import com.example.scame.lighttube.presentation.fragments.CommentActionListener;
import com.example.scame.lighttube.presentation.model.ReplyListModel;
import com.example.scame.lighttube.presentation.model.ReplyModel;

import static com.example.scame.lighttube.presentation.adapters.player.replies.RepliesDelegatesManager.*;

public class EditReplyDelegate implements AdapterDelegate<ReplyListModel> {

    private CommentActionListener commentActionListener;

    public EditReplyDelegate (CommentActionListener commentActionListener) {
        this.commentActionListener = commentActionListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_input_item, parent, false);
        return new EditCommentViewHolder(itemView, commentActionListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ReplyListModel items, int position, @NonNull RecyclerView.ViewHolder holder) {
        if (holder instanceof EditCommentViewHolder) {
            Pair<Integer, Integer> replyIndex = new Pair<>(-1, position);
            ReplyModel replyModel = items.getReplyModel(position);

            ((EditCommentViewHolder) holder).bindCommentEditorHolder(replyIndex, replyModel.getTextDisplay(),
                    replyModel.getCommentId());
        }
    }

    @Override
    public int getViewType() {
        return VIEW_TYPE_EDIT_REPLY;
    }
}
