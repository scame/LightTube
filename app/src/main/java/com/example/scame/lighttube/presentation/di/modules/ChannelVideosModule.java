package com.example.scame.lighttube.presentation.di.modules;

import com.example.scame.lighttube.data.repository.IChannelVideosDataManager;
import com.example.scame.lighttube.data.repository.IContentDetailsDataManager;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;
import com.example.scame.lighttube.domain.usecases.ChannelVideosUseCase;
import com.example.scame.lighttube.domain.usecases.ContentDetailsUseCase;
import com.example.scame.lighttube.presentation.di.PerActivity;
import com.example.scame.lighttube.presentation.presenters.ChannelVideosPresenterImp;
import com.example.scame.lighttube.presentation.presenters.IChannelVideosPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class ChannelVideosModule {

    @Provides
    @PerActivity
    ChannelVideosUseCase provideChannelsUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                                IChannelVideosDataManager videosDataManager) {

        return new ChannelVideosUseCase(subscribeOn, observeOn, videosDataManager);
    }

    @Provides
    @PerActivity
    ContentDetailsUseCase provideContentDetailsUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                                       IContentDetailsDataManager dataManager) {

        return new ContentDetailsUseCase(subscribeOn, observeOn, dataManager);
    }

    @Provides
    @PerActivity
    IChannelVideosPresenter<IChannelVideosPresenter.ChannelsView> provideChannelsPresenter(ChannelVideosUseCase videosUseCase,
                                                                                           ContentDetailsUseCase detailsUseCase) {
        return new ChannelVideosPresenterImp<>(videosUseCase, detailsUseCase);
    }
}
