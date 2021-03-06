package com.example.scame.lighttube.presentation.adapters.player.threads;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.example.scame.lighttube.R;
import com.example.scame.lighttube.presentation.fragments.PlayerFooterFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentInputViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.comment_input) EditText commentInput;

    private PlayerFooterFragment.PostedCommentListener postedCommentListener;

    public CommentInputViewHolder(PlayerFooterFragment.PostedCommentListener postedCommentListener,
                                  View itemView) {
        super(itemView);

        this.postedCommentListener = postedCommentListener;
        ButterKnife.bind(this, itemView);
        setOnEditorActionListener();
    }

    private void setOnEditorActionListener() {
        commentInput.setOnEditorActionListener((v, actionId, event) -> {
            boolean handled = false;

            if (actionId == EditorInfo.IME_ACTION_SEND) {
                postedCommentListener.onPostCommentClick(commentInput.getText().toString());
                commentInput.setText(""); // FIXME: should happen later
                handled = true;
            }

            return handled;
        });
    }
}
