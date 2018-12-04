package com.txwySDKTest;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Toast;

import com.txwy.passport.sdk.SDKTxwyPassportInfo;
import com.unity3d.player.*;
import com.txwy.passport.sdk.SDKTxwyPassport;


public class MainActivity extends UnityPlayerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        InitSDK("162014", "f52fe97476fcfe97af59903eafd4f397", "android_tw_wll", "tw");
//        ShowToast("InitSDK1111");
    }

    public void Init()
    {
        String appID  = "162014";
        String appKey = "f52fe97476fcfe97af59903eafd4f397";
        String fuid   = "android_tw_wll";
        String language   = "tw";

        SDKTxwyPassport.setAppInfo(this, appID, appKey, fuid , language);
        ShowToast("init");
    }

    public void ShowToast(final String mStr2Show){
        // 同样需要在UI线程下执行
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(),mStr2Show, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void InitSDK(String _appID, String _appKey, String _fuid, String _language)
    {
        ShowToast("InitSDK11" + _appID + _appKey + _fuid + _language);
        SDKTxwyPassport.setAppInfo(this, _appID, _appKey, _fuid, _language);
//        SDKTxwyPassport.setLanguage(this, _language);
        ShowToast("InitSDK");
    }

    //    public void SetSDKLanguage(String _language)
//    {
//        SDKTxwyPassport.setLanguage(this, _language);
//    }
//
    public void SignIn()
    {
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
//                     帐号已登出
                    return;
                }
//                SetPlayerInfo(passport);
                ShowToast("Sign in successful");
                // 帐号已成功登录通行证
                // 将 passport.sid 传递给服务器，服务器通过通行证接口验证sid，以确保登录账号合法。
            }
        });
    }
//
//    public void SignOut()
//    {
//        SDKTxwyPassport.signOut(this);
//    }
//
//    SDKTxwyPassportInfo playerPassport = new SDKTxwyPassportInfo();
//    private void SetPlayerInfo(SDKTxwyPassportInfo _passport)
//    {
//        playerPassport = _passport;
//    }
//
//    public SDKTxwyPassportInfo GetPlayerInfo()
//    {
//        return playerPassport;
//    }
//
}

