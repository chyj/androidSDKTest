using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class UnityTest : MonoBehaviour
{
    public Text mText;
    private string m_objName;

    private void Start() {
        m_objName = gameObject.name;
        setMsg(m_objName);
    }
    public void BeCallFunc(string _content)
    {
        
        setMsg(_content);
    }
    private void setMsg(string _str)
    {
        mText.text = _str;
    }

    public void Btn_Dialog()
    {
        // Java Interface Of Android
        //AndroidJavaClass jc = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
        //AndroidJavaObject jo = jc.GetStatic<AndroidJavaObject>("currentActivity");
        // Parameters 
        //string[] mObject = new string[2];
        // mObject[0] = "Jar4Android";
        // mObject[1] = "Wow,Amazing!It's worked!";
        // Func 
        //string ret = jo.Call<string>("ShowDialog", mObject);
        // setMsg(ret);
        SignIn();
    }
    public void Btn_Toast()
    {
        AndroidJavaClass jc = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
        AndroidJavaObject jo = jc.GetStatic<AndroidJavaObject>("currentActivity");
        jo.Call("ShowToast", "Showing on Toast");
    }

    public void Btn_Vibrate()
    {
        AndroidJavaClass jc = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
        AndroidJavaObject jo = jc.GetStatic<AndroidJavaObject>("currentActivity");
        jo.Call("ShowToast", "Init");
        jo.Call("InitSDK", "162014", "f52fe97476fcfe97af59903eafd4f397", "android_tw_wll", "tw");
 
    }

    public void InitSDK()
    {
        AndroidJavaClass jc = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
        AndroidJavaObject jo = jc.GetStatic<AndroidJavaObject>("currentActivity");

        //以下信息由天下网游提供
        string appID = "162014";
        string appKey = "f52fe97476fcfe97af59903eafd4f397";
        string fuid = "android_tw_wll";
        string language = "tw";

        jo.Call("InitSDK", appID, appKey, fuid, language);
    }

    public void SignIn()
    {
        AndroidJavaClass jc = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
        AndroidJavaObject jo = jc.GetStatic<AndroidJavaObject>("currentActivity");
        jo.Call("SignIn", m_objName, "SignCallBackFunc");
        //AndroidJavaClass unityPlayer = new AndroidJavaClass ("com.unity3d.player.UnityPlayer");
        //AndroidJavaObject currentActivity = unityPlayer.GetStatic<AndroidJavaObject> ("currentActivity");
        //AndroidJavaClass androidCall = new AndroidJavaClass ("com.txwy.passport.sdk.SDKTxwyPassport");

        //androidCall.CallStatic ("signIn" , currentActivity , signInDelegete);
    }

    public void SignCallBackFunc(string msg)
    {
        if (msg == "0")
        {
            //登录失败
            setMsg("SignIn faild");
        }
        else
        {
            //登录成功，msg = userID
            setMsg("SignIn succsseful! id ：" + msg);
        }
    }

    public void SignOut()
    {
        AndroidJavaClass jc = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
        AndroidJavaObject jo = jc.GetStatic<AndroidJavaObject>("currentActivity");
        jo.Call("SignOut");
    }
    
    //用户中心
    public void UserCenter(string _serverID, string _playerName, string _clientVision)
    {
        AndroidJavaClass jc = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
        AndroidJavaObject jo = jc.GetStatic<AndroidJavaObject>("currentActivity");
        jo.Call("UserCenter", _serverID, _playerName, _clientVision);
    }

    //客服中心
    public void BugReport(string _serverID, string _playerName, string _clientVision)
    {
        AndroidJavaClass jc = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
        AndroidJavaObject jo = jc.GetStatic<AndroidJavaObject>("currentActivity");
        jo.Call("BugReport", _serverID, _playerName, _clientVision);
    }

    //Facebook分享
    public void ShareToFacebook()
    {
        AndroidJavaClass jc = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
        AndroidJavaObject jo = jc.GetStatic<AndroidJavaObject>("currentActivity");
        jo.Call("ShowImgToFacebook", m_objName, "ShareSuccessCallBack");
    }

    //分享回调函数
    public void ShareSuccessCallBack(string code)
    {
        if (code == "1")
        {
            //分享成功
        }
        else
        {
            //分享失败
        }
    }

    //评分接口
    public void StoreReview()
    {
        AndroidJavaClass jc = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
        AndroidJavaObject jo = jc.GetStatic<AndroidJavaObject>("currentActivity");
        jo.Call("StoreReview", m_objName, "ReviewFunc", "WaitFunc", "RefuseFunc");
    }

    //用户点击评分按钮
    public void ReviewFunc()
    {
        setMsg("ReviewFunc");
    }

    //用户点击下次按钮
    public void WaitFunc()
    {
        setMsg("WaitFunc");
    }

    //用户点击拒绝按钮
    public void RefuseFunc()
    {
        setMsg("WaitFunc");
    }
}