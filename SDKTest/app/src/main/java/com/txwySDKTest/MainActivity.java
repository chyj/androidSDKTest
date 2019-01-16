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
import com.txwy.passport.sdk.billing.SkuDetails;
import com.unity3d.player.*;
import com.txwy.passport.sdk.SDKTxwyPassport;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


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
    }

    //登录
    public void SignIn(final String _objName, final String _signInSuccessCallback, final String _signOutSuccessCallBack) {
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
                    UnityPlayer.UnitySendMessage(_objName, _signOutSuccessCallBack, "0");
//                    ShowToast("Sign in faild");
                    return;
                }
//                SetPlayerInfo(passport);
//                ShowToast("Sign in successful  " +  passport.sid);
                UnityPlayer.UnitySendMessage(_objName, _signInSuccessCallback, passport.sid);
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

    class ProductsPrice
    {
        String productID;
        String price;
    }

    //查询
    public void QueryWithProducts(String[] _products, final String _objName, final String _queryFuncStr)
    {
        List<String> list = Arrays.asList(_products);
        SDKTxwyPassport.queryWithProducts(this, list, new SDKTxwyPassport.ProductQueringListener() {
            @Override
            public void onQuery(List<SkuDetails> skus) {
                //查询失败
                if (null == skus)
                    return;
                List<ProductsPrice> producesPrice = new ArrayList<ProductsPrice>();
                for (int i = 1; i <= skus.toArray().length; i++){
                    SkuDetails sku = (SkuDetails) skus.toArray()[i-1];
                    if (sku!=null){
                        //sku.getSku()		     	-- 产品ID
                        //sku.getAmount()	     	-- 价格
                        //sku.getCur()		     	-- 货币
                        //sku.getDisplayPrice()		-- 显示价格
                        ProductsPrice tmp = new ProductsPrice();
                        tmp.productID = sku.getSku();
                        tmp.price = sku.getDisplayPrice();
                        producesPrice.add(tmp);
                    }
                }

                JSONArray jsonArray = new JSONArray();
                JSONObject tmpObj = null;
                int count = producesPrice.size();
                for(int i = 0; i < count; i++)
                {
                    tmpObj = new JSONObject();
                    try {
                        tmpObj.put("productID", producesPrice.get(i).productID);
                        tmpObj.put("price", producesPrice.get(i).price);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    jsonArray.put(tmpObj);
                    tmpObj = null;

                }
                String json = jsonArray.toString(); // 将JSONArray转换得到String
                UnityPlayer.UnitySendMessage(_objName, _queryFuncStr, json);
            }
        });
    }

    //充值
    public void payWithProductID(final String _produceID, final String _serverID, final String _mark, final String _objName, final String _paySuccessFunc, final String _payCancleFunc)
    {
        SDKTxwyPassport.payWithProductID(this, _produceID, _serverID, _mark, 0, new SDKTxwyPassport.PaymentListener() {
            @Override
            public void onPayment(SkuDetails skuDetail) {
                // 平台充值成功，游戏服务器可能还没有收到平台的充值请求。
                UnityPlayer.UnitySendMessage(_objName, _paySuccessFunc, "");
            }

            @Override
            public void onCancel() {
                //出错或用户取消
                UnityPlayer.UnitySendMessage(_objName, _payCancleFunc, "");
            }
        });
    }


    //Facebook 分享
    public void ShowImgToFacebook(Bitmap _bitmap, final String _objName, final String _shareSuccessCallBack, final String _shareFaildCallBack )
    {
        SDKTxwyPassport.feedWithImage(this, _bitmap, new SDKTxwyPassport.feedDelegete() {
            @Override
            public void txwyDidFeed(String error) {
                if (error.equals("success"))
                {
                    //分享成功
                    UnityPlayer.UnitySendMessage(_objName, _shareSuccessCallBack, "1");
                }
                else
                {
                    //分享失敗
                    UnityPlayer.UnitySendMessage(_objName, _shareFaildCallBack, "0");
                }
            }
        });
    }

    public SDKTxwyPassportInfo GetPassportInfo()
    {
        return SDKTxwyPassport.getPassportInfo(this);
    }

}

