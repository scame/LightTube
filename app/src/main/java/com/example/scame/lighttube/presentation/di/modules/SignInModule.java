package com.example.scame.lighttube.presentation.di.modules;

import android.app.Activity;

import com.example.scame.lighttube.PrivateValues;
import com.example.scame.lighttube.data.repository.AccountRepository;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;
import com.example.scame.lighttube.domain.usecases.CheckLoginUseCase;
import com.example.scame.lighttube.domain.usecases.SignInUseCase;
import com.example.scame.lighttube.domain.usecases.SignOutUseCase;
import com.example.scame.lighttube.presentation.di.PerActivity;
import com.example.scame.lighttube.presentation.fragments.LoginFragment;
import com.example.scame.lighttube.presentation.presenters.LoginPresenter;
import com.example.scame.lighttube.presentation.presenters.LoginPresenterImp;
import com.example.scame.lighttube.presentation.presenters.SubscriptionsHandler;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;

import dagger.Module;
import dagger.Provides;

@Module
public class SignInModule {

    private Activity activity;

    public SignInModule(Activity activity) {
        this.activity = activity;
    }

    @PerActivity
    @Provides
    GoogleApiClient.Builder provideGoogleApiClientBuilder(GoogleSignInOptions gso) {
        return new GoogleApiClient.Builder(activity)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso);
    }

    @PerActivity
    @Provides
    GoogleSignInOptions provideGoogleSignInOptions() {
        return new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(
                        new Scope(LoginFragment.YOUTUBE_UPLOAD_SCOPE),
                        new Scope(LoginFragment.YOUTUBE_FORCE_SSL_SCOPE))
                .requestServerAuthCode(PrivateValues.CLIENT_ID, true)
                .requestEmail()
                .build();
    }

    @PerActivity
    @Provides
    SignInUseCase provideSignInUseCase(AccountRepository dataManager,
                                       ObserveOn observeOn, SubscribeOn subscribeOn) {
        return new SignInUseCase(dataManager, subscribeOn, observeOn);
    }

    @PerActivity
    @Provides
    SignOutUseCase provideSignOutUseCase(AccountRepository dataManager,
                                         ObserveOn observeOn, SubscribeOn subscribeOn) {
        return new SignOutUseCase(dataManager, subscribeOn, observeOn);
    }

    @PerActivity
    @Provides
    CheckLoginUseCase provideSignInCheckUseCase(AccountRepository dataManager,
                                                ObserveOn observeOn, SubscribeOn subscribeOn) {
        return new CheckLoginUseCase(subscribeOn, observeOn, dataManager);
    }

    @PerActivity
    @Provides
    SubscriptionsHandler provideSubscriptionsHandler(SignInUseCase signInUseCase, SignOutUseCase signOutUseCase,
                                                     CheckLoginUseCase checkLoginUseCase) {

        return new SubscriptionsHandler(signInUseCase, signOutUseCase, checkLoginUseCase);
    }

    @PerActivity
    @Provides
    LoginPresenter<LoginPresenter.LoginView> provideSignInPresenter(SignInUseCase signInUseCase,
                                                                    SignOutUseCase signOutUseCase,
                                                                    CheckLoginUseCase checkLoginUseCase,
                                                                    SubscriptionsHandler subscriptionsHandler) {
        return new LoginPresenterImp<>(signInUseCase, signOutUseCase, checkLoginUseCase, subscriptionsHandler);
    }
}
