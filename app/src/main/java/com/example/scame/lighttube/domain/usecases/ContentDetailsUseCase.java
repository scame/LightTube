package com.example.scame.lighttube.domain.usecases;


import com.example.scame.lighttube.data.mappers.DurationsCombiner;
import com.example.scame.lighttube.data.repository.IContentDetailsDataManager;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;
import com.example.scame.lighttube.presentation.model.VideoModel;

import java.util.List;

import rx.Observable;

public class ContentDetailsUseCase extends UseCase<List<VideoModel>> {

    private IContentDetailsDataManager dataManager;

    private List<VideoModel> videoModels;

    public ContentDetailsUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                 IContentDetailsDataManager dataManager) {

        super(subscribeOn, observeOn);
        this.dataManager = dataManager;
    }

    @Override
    protected Observable<List<VideoModel>> getUseCaseObservable() {
        DurationsCombiner combiner = new DurationsCombiner();

        return dataManager.getContentDetails(videoModels)
                .map(contentEntity -> combiner.combine(contentEntity, videoModels)); // combine old video models and durations
    }

    public void setVideoModels(List<VideoModel> videoModels) {
        this.videoModels = videoModels;
    }
}