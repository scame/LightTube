package com.example.scame.lighttube.presentation.di.components;

import android.app.Application;

import com.example.scame.lighttube.data.di.DataModule;
import com.example.scame.lighttube.data.repository.ISearchDataManager;
import com.example.scame.lighttube.data.repository.IAccountDataManager;
import com.example.scame.lighttube.data.repository.IVideoListDataManager;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;
import com.example.scame.lighttube.presentation.di.modules.ApplicationModule;
import com.example.scame.lighttube.presentation.navigation.Navigator;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

@Singleton
@Component(modules = {ApplicationModule.class, DataModule.class})
public interface ApplicationComponent {

    Application getApp();

    Retrofit getRetrofit();

    ObserveOn getObserveOn();

    SubscribeOn getSubscribeOn();

    Navigator getNavigator();

    IAccountDataManager getSignInDataManager();

    IVideoListDataManager getVideoListDataManager();

    ISearchDataManager getSearchDataManager();
}
