package com.example.scame.lighttube.data.repository;


import com.example.scame.lighttube.presentation.model.VideoStatsModel;

import rx.Observable;

public interface IStatisticsDataManager {

    Observable<VideoStatsModel> getVideoStatistics(String videoId);
}
