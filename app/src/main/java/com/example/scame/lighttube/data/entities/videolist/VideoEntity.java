package com.example.scame.lighttube.data.entities.videolist;


public class VideoEntity {

    private String id;

    private String channelTitle;

    private SnippetEntity snippet;

    public void setId(String id) {
        this.id = id;
    }

    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
    }

    public void setSnippet(SnippetEntity snippet) {
        this.snippet = snippet;
    }

    public String getId() {
        return id;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public SnippetEntity getSnippet() {
        return snippet;
    }
}
