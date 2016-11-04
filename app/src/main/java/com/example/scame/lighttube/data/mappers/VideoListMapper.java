package com.example.scame.lighttube.data.mappers;

import com.example.scame.lighttube.data.entities.videolist.VideoEntity;
import com.example.scame.lighttube.data.entities.videolist.VideoEntityList;
import com.example.scame.lighttube.presentation.model.VideoModel;

import java.util.ArrayList;
import java.util.List;

public class VideoListMapper {

    public List<VideoModel> convert(VideoEntityList entitiesList) {
        List<VideoModel> modelList = new ArrayList<>();

        for (VideoEntity entity : entitiesList.getItems()) {
            VideoModel itemModel = new VideoModel();

            itemModel.setImageUrl(entity.getSnippet().getThumbnails().getHigh().getUrl());
            itemModel.setTitle(entity.getSnippet().getTitle());
            itemModel.setVideoId(entity.getId());
            itemModel.setChannelTitle(entity.getSnippet().getChannelTitle());
            itemModel.setPublishedAt(entity.getSnippet().getPublishedAt());
            itemModel.setCategory(entity.getSnippet().getCategoryId());
            itemModel.setDescription(entity.getSnippet().getDescription());

            modelList.add(itemModel);
        }

        return modelList;
    }
}
