package com.aone.menurandomchoice.views.ownerlogin;

import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;

import com.aone.menurandomchoice.GlobalApplication;
import com.aone.menurandomchoice.R;
import com.aone.menurandomchoice.repository.model.LoginData;
import com.aone.menurandomchoice.repository.model.UserAccessInfo;
import com.aone.menurandomchoice.repository.oauth.KakaoLoginError;
import com.aone.menurandomchoice.repository.oauth.OnKakaoLoginListener;
import com.aone.menurandomchoice.repository.remote.NetworkResponseListener;
import com.aone.menurandomchoice.repository.remote.response.JMTErrorCode;
import com.aone.menurandomchoice.views.base.BasePresenter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class OwnerLoginPresenter extends BasePresenter<OwnerLoginContract.View>
        implements OwnerLoginContract.Presenter {

    @Override
    public void handlingDeviceKaKaoAccountLogin() {
        showProgressBarOfView();
        getRepository().executeDeviceKakaoAccountLogin(onKakaoLoginListener);
    }

    @Override
    public void handlingOtherKaKaoAccountLogin() {
        showProgressBarOfView();
        getRepository().executeOtherKakaoAccountLogin(onKakaoLoginListener);
    }

    @Override
    public boolean isNeedKakaoSDKLoginScreen(int requestCode, int resultCode, @Nullable Intent data) {
        return getRepository().isNeedKakaoSDKLoginScreen(requestCode, resultCode, data);
    }

    private void requestSignedUpCheck(final long userId) {
        showProgressBarOfView();
        getRepository().requestSignedUpCheck(userId, new NetworkResponseListener<LoginData>() {
            @Override
            public void onReceived(@NonNull LoginData loginData) {
                hideProgressBarOfView();

                if(getRepository().getStoreDetail() == null) {
                    getRepository().addDefaultStoreDetail();
                }

                moveToOwnerStoreActivity(loginData);
            }

            @Override
            public void onError(JMTErrorCode errorCode) {
                hideProgressBarOfView();

                if (errorCode == JMTErrorCode.NO_SIGNED_UP_INFO) {
                    moveToSignUpActivity(userId);
                } else {
                    sendMessageToView(errorCode.getStringResourceId());
                }
            }
        });
    }

    private void moveToOwnerStoreActivity(LoginData loginData) {
        if(isAttachView()) {
            getView().moveToOwnerStoreActivity(new UserAccessInfo(loginData.getStoreIdx(), true));
        }
    }

    private void moveToSignUpActivity(long userId) {
        if(isAttachView()) {
            getView().moveToSignUpActivity(userId);
        }
    }

    private OnKakaoLoginListener onKakaoLoginListener = new OnKakaoLoginListener() {

        @Override
        public void onKakaoLoginSuccess(long userId) {
            hideProgressBarOfView();
            requestSignedUpCheck(userId);
        }

        @Override
        public void onFail(@NonNull KakaoLoginError kakaoLoginError) {
            hideProgressBarOfView();
            sendMessageToView(kakaoLoginError.getStringResourceId());
        }

    };

}
