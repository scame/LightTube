package com.example.scame.lighttube.presentation.di.components;

import android.content.Context;

import com.example.scame.lighttube.data.di.DataModule;
import com.example.scame.lighttube.data.repository.AccountRepository;
import com.example.scame.lighttube.data.repository.CategoryRepository;
import com.example.scame.lighttube.data.repository.ChannelVideosRepository;
import com.example.scame.lighttube.data.repository.ChannelVideosRepositoryImp;
import com.example.scame.lighttube.data.repository.CommentsRepository;
import com.example.scame.lighttube.data.repository.CommentsRepositoryImp;
import com.example.scame.lighttube.data.repository.ContentDetailsRepository;
import com.example.scame.lighttube.data.repository.HomeVideosRepository;
import com.example.scame.lighttube.data.repository.HomeVideosRepositoryImp;
import com.example.scame.lighttube.data.repository.RatingRepository;
import com.example.scame.lighttube.data.repository.RecentVideosRepository;
import com.example.scame.lighttube.data.repository.SearchRepository;
import com.example.scame.lighttube.data.repository.SearchRepositoryImp;
import com.example.scame.lighttube.data.repository.StatisticsRepository;
import com.example.scame.lighttube.data.repository.UserChannelRepository;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;
import com.example.scame.lighttube.presentation.di.modules.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

@Singleton
@Component(modules = {ApplicationModule.class, DataModule.class})
public interface ApplicationComponent {

    void inject(CommentsRepositoryImp commentsDataManager);

    void inject(ChannelVideosRepositoryImp channelVideosRepository);

    void inject(SearchRepositoryImp searchDataManager);

    void inject(HomeVideosRepositoryImp homeVideosRepository);

    Retrofit getRetrofit();

    Context getContext();

    ObserveOn getObserveOn();

    SubscribeOn getSubscribeOn();

    StatisticsRepository getStatsDataManager();

    AccountRepository getSignInDataManager();

    HomeVideosRepository getVideoListDataManager();

    SearchRepository getSearchDataManager();

    CategoryRepository getCategoryDataManager();

    RecentVideosRepository getRecentVideosDataManager();

    ChannelVideosRepository getChannelVideosDataManager();

    ContentDetailsRepository getContentDetailsDataManager();

    RatingRepository getRatingDataManager();

    CommentsRepository getCommentsDataManager();

    UserChannelRepository getUserChannelDataManager();
}
