package com.example.scame.lighttube.presentation.model;


import java.util.List;

public class RepliesWrapper {

    private List<ReplyModel> replyModels;

    private int page;

    public RepliesWrapper(List<ReplyModel> replyModels, int page) {
        this.replyModels = replyModels;
        this.page = page;
    }

    public List<ReplyModel> getReplyModels() {
        return replyModels;
    }

    public int getPage() {
        return page;
    }
}
