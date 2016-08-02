package com.example.scame.lighttubex.data.mappers;

import com.example.scame.lighttubex.data.entities.VideoEntity;
import com.example.scame.lighttubex.data.entities.VideoEntityList;
import com.example.scame.lighttubex.presentation.model.VideoItemModel;

import java.util.ArrayList;
import java.util.List;

public class VideoListMapper {

    public List<VideoItemModel> convert(VideoEntityList entitiesList) {
        List<VideoEntity> entities = entitiesList.getItems();
        List<VideoItemModel> modelList = new ArrayList<>();

        for (VideoEntity entity : entities) {
            VideoItemModel itemModel = new VideoItemModel();
            itemModel.setThumbnails(entity.getSnippet().getThumbnails().getMedium().getUrl());
            itemModel.setTitle(entity.getSnippet().getTitle());
            itemModel.setId(entity.getId());

            modelList.add(itemModel);
        }

        return modelList;
    }
}