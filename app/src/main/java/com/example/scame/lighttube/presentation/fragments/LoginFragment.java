package com.example.scame.lighttube.presentation.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.scame.lighttube.R;
import com.example.scame.lighttube.presentation.activities.TabActivity;
import com.example.scame.lighttube.presentation.presenters.LoginPresenter;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginFragment extends BaseFragment implements LoginPresenter.LoginView,
                                    GoogleApiClient.OnConnectionFailedListener {

    private static final int RC_SIGN_IN = 1000;

    private static final String SIGN_IN_TAG = "sign_in_log";

    public static final String YOUTUBE_UPLOAD_SCOPE = "https://www.googleapis.com/auth/youtube.upload";
    public static final String YOUTUBE_FORCE_SSL_SCOPE = "https://www.googleapis.com/auth/youtube.force-ssl";

    private View fragmentView;

    private LoginListener loginListener;

    @BindView(R.id.status_tv) TextView statusTextView;

    @BindView(R.id.sign_in_toolbar) Toolbar toolbar;

    @Inject
    LoginPresenter<LoginPresenter.LoginView> loginPresenter;

    @Inject GoogleApiClient.Builder googleApiClientBuilder;
    private GoogleApiClient googleApiClient;

    public interface LoginListener {
        void signedIn();

        void signedOut();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof LoginListener) {
            loginListener = (LoginListener) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inject();
        loginPresenter.setView(this);

        googleApiClient = googleApiClientBuilder.enableAutoManage(getActivity(), this).build();
        googleApiClient.connect();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    }

    private void inject() {
        if (getActivity() instanceof TabActivity) {
            TabActivity tabActivity = (TabActivity) getActivity();
            tabActivity.getSignInComponent().inject(this);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(SIGN_IN_TAG, "onConnectionFailed");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.signin_fragment, container, false);

        loginPresenter.isSignedIn();
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @OnClick(R.id.sign_in_button)
    public void signInClick() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            loginPresenter.handleSignInResult(result);
        }
    }

    @OnClick(R.id.sign_out_button)
    public void signOutClick() {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
                status -> loginPresenter.signOutClick(status)
        );
    }

    @Override
    public void updateUI(Boolean signedIn) {
        if (signedIn) {
            fragmentView.findViewById(R.id.sign_in_button).setVisibility(View.GONE);
            fragmentView.findViewById(R.id.sign_out_button).setVisibility(View.VISIBLE);
        } else {
            statusTextView.setText(R.string.signed_out);

            fragmentView.findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
            fragmentView.findViewById(R.id.sign_out_button).setVisibility(View.GONE);
        }
    }

    @Override
    public void setStatusTextView(String serverAuthCode) {
        statusTextView.setText(getString(R.string.signed_in_fmt, serverAuthCode));
    }

    @Override
    public void signOut() {
        loginListener.signedOut();
    }

    @Override
    public void signIn() {
        loginListener.signedIn();
    }

    @Override
    public void onResume() {
        super.onResume();
        loginPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        loginPresenter.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        googleApiClient.stopAutoManage(getActivity());
        googleApiClient.disconnect();

        loginPresenter.destroy();
    }

    @Override
    public void showError(String error) {
        Toast.makeText(getContext(), "Error: " + error, Toast.LENGTH_LONG).show();
    }
}
