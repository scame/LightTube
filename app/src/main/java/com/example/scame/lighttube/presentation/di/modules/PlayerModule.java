package com.example.scame.lighttube.presentation.di.modules;

import com.example.scame.lighttube.data.repository.ICommentsDataManager;
import com.example.scame.lighttube.data.repository.IRatingDataManager;
import com.example.scame.lighttube.domain.schedulers.ObserveOn;
import com.example.scame.lighttube.domain.schedulers.SubscribeOn;
import com.example.scame.lighttube.domain.usecases.PostThreadCommentUseCase;
import com.example.scame.lighttube.domain.usecases.RateVideoUseCase;
import com.example.scame.lighttube.domain.usecases.RetrieveCommentsUseCase;
import com.example.scame.lighttube.domain.usecases.RetrieveRatingUseCase;
import com.example.scame.lighttube.domain.usecases.RetrieveRepliesUseCase;
import com.example.scame.lighttube.presentation.di.PerActivity;
import com.example.scame.lighttube.presentation.presenters.IRepliesPresenter;
import com.example.scame.lighttube.presentation.presenters.PlayerFooterPresenterImp;
import com.example.scame.lighttube.presentation.presenters.IPlayerFooterPresenter;
import com.example.scame.lighttube.presentation.presenters.IPlayerHeaderPresenter;
import com.example.scame.lighttube.presentation.presenters.PlayerHeaderPresenterImp;
import com.example.scame.lighttube.presentation.presenters.RepliesPresenterImp;
import com.example.scame.lighttube.presentation.presenters.SubscriptionsHandler;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

import static com.example.scame.lighttube.presentation.presenters.IPlayerFooterPresenter.*;
import static com.example.scame.lighttube.presentation.presenters.IPlayerHeaderPresenter.*;

import static com.example.scame.lighttube.presentation.presenters.IRepliesPresenter.*;

@Module
public class PlayerModule {

    @Provides
    @PerActivity
    RetrieveRatingUseCase provideRetrieveRatingUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                                       IRatingDataManager ratingsDataManager) {

        return new RetrieveRatingUseCase(subscribeOn, observeOn, ratingsDataManager);
    }

    @Provides
    @PerActivity
    RateVideoUseCase provideRateVideoUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                             IRatingDataManager ratingsDataManager) {

        return new RateVideoUseCase(subscribeOn, observeOn, ratingsDataManager);
    }

    @Provides
    @PerActivity
    RetrieveCommentsUseCase provideRetrieveCommentsUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                                           ICommentsDataManager commentsDataManager) {

        return new RetrieveCommentsUseCase(subscribeOn, observeOn, commentsDataManager);
    }

    @Provides
    @PerActivity
    RetrieveRepliesUseCase provideRetrieveRepliesUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                                         ICommentsDataManager commentsDataManager) {
        return new RetrieveRepliesUseCase(subscribeOn, observeOn, commentsDataManager);
    }

    @Provides
    @PerActivity
    PostThreadCommentUseCase providePostCommentUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                                       ICommentsDataManager commentsDataManager) {
        return new PostThreadCommentUseCase(subscribeOn, observeOn, commentsDataManager);
    }


    @Provides
    @PerActivity
    IRepliesPresenter<RepliesView> provideRepliesPresenter(RetrieveRepliesUseCase retrieveRepliesUseCase,
                                                           @Named("replies")SubscriptionsHandler subscriptionsHandler) {
        return new RepliesPresenterImp<>(retrieveRepliesUseCase, subscriptionsHandler);
    }

    @Provides
    @PerActivity
    IPlayerHeaderPresenter<PlayerView> providePlayerPresenter(RetrieveRatingUseCase retrieveRatingUseCase,
                                                              RateVideoUseCase rateVideoUseCase,
                                                              @Named("header")SubscriptionsHandler subscriptionsHandler) {

        return new PlayerHeaderPresenterImp<>(retrieveRatingUseCase, rateVideoUseCase, subscriptionsHandler);
    }

    @Provides
    @PerActivity
    IPlayerFooterPresenter<CommentsView> provideCommentsPresenter(RetrieveCommentsUseCase retrieveCommentsUseCase,
                                                                  PostThreadCommentUseCase postCommentUseCase,
                                                                  @Named("comments")SubscriptionsHandler subscriptionsHandler) {

        return new PlayerFooterPresenterImp<>(retrieveCommentsUseCase, postCommentUseCase, subscriptionsHandler);
    }

    @Provides
    @Named("header")
    @PerActivity
    SubscriptionsHandler provideHeaderSubscriptionsHandler(RetrieveRatingUseCase retrieveRatingUseCase,
                                                     RateVideoUseCase rateVideoUseCase) {

        return new SubscriptionsHandler(retrieveRatingUseCase, rateVideoUseCase);
    }

    @Provides
    @Named("comments")
    @PerActivity
    SubscriptionsHandler provideCommentsSubscriptionsHandler(RetrieveCommentsUseCase retrieveCommentsUseCase,
                                                             PostThreadCommentUseCase postCommentUseCase) {
        return new SubscriptionsHandler(retrieveCommentsUseCase, postCommentUseCase);
    }

    @Provides
    @Named("replies")
    @PerActivity
    SubscriptionsHandler provideRepliesSubscriptionsHandler(RetrieveRepliesUseCase retrieveRepliesUseCase) {
        return new SubscriptionsHandler(retrieveRepliesUseCase);
    }
}
