package com.txwySDKTest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import com.txwy.passport.sdk.SDKTxwyPassportInfo;
import com.unity3d.player.*;
import com.txwy.passport.sdk.SDKTxwyPassport;


public class MainActivity extends UnityPlayerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void Init() {
        String appID = "162014";
        String appKey = "f52fe97476fcfe97af59903eafd4f397";
        String fuid = "android_tw_wll";
        String language = "tw";

        SDKTxwyPassport.setAppInfo(this, appID, appKey, fuid, language);
        ShowToast("init");
    }

    public void ShowToast(final String mStr2Show) {
        // 同样需要在UI线程下执行
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), mStr2Show, Toast.LENGTH_LONG).show();
            }
        });
    }

    //初始化SDK
    public void InitSDK(String _appID, String _appKey, String _fuid, String _language) {
        SDKTxwyPassport.setAppInfo(this, _appID, _appKey, _fuid, _language);
        ShowToast("InitSDK");
    }

    //登录
    public void SignIn(final String _objName, final String _signInCallback) {
        ShowToast("Sign in");
        final Activity curActivity = this;
        SDKTxwyPassport.signIn(this, new SDKTxwyPassport.SignInDelegete() {
            @Override
            public void txwyDidPassport() {
                // 登录完成
                SDKTxwyPassportInfo passport = SDKTxwyPassport.getPassportInfo(curActivity);
                // passport.uid 用户ID
                // passport.uname 用户名
                // passport.sid 用户登录凭证
                // passport.isGuest = true 则为游客身份登录
                // passport.isBindPhoneNum = true 则为用户已绑定手机

                if (passport == null) {
                    // 帐号已登出
                    UnityPlayer.UnitySendMessage(_objName, _signInCallback, "0");
                    ShowToast("Sign in faild");
                    return;
                }
//                SetPlayerInfo(passport);
                ShowToast("Sign in successful  " +  passport.sid);
                UnityPlayer.UnitySendMessage(_objName, _signInCallback, passport.sid);
                // 帐号已成功登录通行证
                // 将 passport.sid 传递给服务器，服务器通过通行证接口验证sid，以确保登录账号合法。
            }
        });
    }

    //登出
    public void SignOut() {
        SDKTxwyPassport.signOut(this);
    }

    //用户中心
    public void UserCenter(String _serverID, String _playerName, String _clientVision) {
        SDKTxwyPassport.userCenter(this, _serverID, _playerName, _clientVision);
    }

    //客服中心
    public void BugReport(String _serverID, String _playerName, String _clientVision) {
        //若未登录游戏服务器，服务器ID 请传入 "1"
        //若游戏角色未登录，角色昵称 请传入 "未登入"
        SDKTxwyPassport.bugReport(this, _serverID, _playerName, _clientVision);
    }

    //评分接口
    public void StoreReview(final String _objName, final String _reviewFuncStr, final String _waitFuncStr, final String _refuseFuncStr) {
        SDKTxwyPassport.storeReview(this, new SDKTxwyPassport.storeReviewListener() {
            @Override
            public void onClickReview() {
                //点击评分按钮
                UnityPlayer.UnitySendMessage(_objName, _reviewFuncStr, "");
            }

            @Override
            public void onClickWait() {
                //点击下次评分按钮
                UnityPlayer.UnitySendMessage(_objName, _waitFuncStr, "");
            }

            @Override
            public void onClickRefuse() {
                //用户点击拒绝评分按钮
                UnityPlayer.UnitySendMessage(_objName, _refuseFuncStr, "");
            }
        });
    }

    //系统回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Pass on the activity result to the helper for handling
        if (!SDKTxwyPassport.handleActivityResult(this, requestCode, resultCode, data)) {
            // not handled, so handle it ourselves (here's where you'd
            // perform any handling of activity results not related to in-app
            // billing...
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions
            , @NonNull int[] grantResults) {
        SDKTxwyPassport.onRequestPermissionsResult(this , requestCode , permissions , grantResults);
    }

    //充值


    //Facebook 分享
    public void ShowImgToFacebook(Bitmap _bitmap, final String _objName, final String _shareCallBack)
    {
        SDKTxwyPassport.feedWithImage(this, _bitmap, new SDKTxwyPassport.feedDelegete() {
            @Override
            public void txwyDidFeed(String error) {
                if (error.equals("success"))
                {
                    //分享成功
                    UnityPlayer.UnitySendMessage(_objName, _shareCallBack, "1");
                }
                else
                {
                    //分享失敗
                    UnityPlayer.UnitySendMessage(_objName, _shareCallBack, "0");
                }
            }
        });
    }
}