package com.example.scame.lighttube.presentation.di.modules;

import com.example.scame.lighttube.data.repository.IRecentVideosDataManager;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;
import com.example.scame.lighttube.domain.usecases.OrderByDateUseCase;
import com.example.scame.lighttube.domain.usecases.RecentVideosUseCase;
import com.example.scame.lighttube.domain.usecases.SubscriptionsUseCase;
import com.example.scame.lighttube.presentation.di.PerActivity;
import com.example.scame.lighttube.presentation.presenters.IRecentVideosPresenter;
import com.example.scame.lighttube.presentation.presenters.RecentVideosPresenterImp;

import dagger.Module;
import dagger.Provides;

import static com.example.scame.lighttube.presentation.presenters.IRecentVideosPresenter.RecentVideosView;

@Module
public class RecentVideosModule {

    @PerActivity
    @Provides
    IRecentVideosPresenter<RecentVideosView> provideRecentVideosPresenter(SubscriptionsUseCase subscriptionsUseCase,
                                                                          RecentVideosUseCase recentVideosUseCase,
                                                                          OrderByDateUseCase orderUseCase) {

        return new RecentVideosPresenterImp<>(subscriptionsUseCase, recentVideosUseCase, orderUseCase);
    }

    @PerActivity
    @Provides
    SubscriptionsUseCase provideSubscriptionsUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                                     IRecentVideosDataManager recentVideosDataManager) {

        return new SubscriptionsUseCase(subscribeOn, observeOn, recentVideosDataManager);
    }

    @PerActivity
    @Provides
    RecentVideosUseCase provideRecentVideosUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                                   IRecentVideosDataManager dataManager) {

        return new RecentVideosUseCase(subscribeOn, observeOn, dataManager);
    }

    @PerActivity
    @Provides
    OrderByDateUseCase provideOrderByDateUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                                 IRecentVideosDataManager dataManager) {

        return new OrderByDateUseCase(subscribeOn, observeOn, dataManager);
    }
}