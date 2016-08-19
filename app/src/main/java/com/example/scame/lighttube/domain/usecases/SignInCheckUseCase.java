package com.example.scame.lighttube.domain.usecases;


import com.example.scame.lighttube.data.repository.IAccountDataManager;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;

import rx.Observable;

public class SignInCheckUseCase extends UseCase<Boolean> {

    private IAccountDataManager dataManager;

    public SignInCheckUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                              IAccountDataManager dataManager) {
        super(subscribeOn, observeOn);

        this.dataManager = dataManager;
    }

    @Override
    protected Observable<Boolean> getUseCaseObservable() {
        return Observable.just(dataManager.ifTokenExists());
    }
}
