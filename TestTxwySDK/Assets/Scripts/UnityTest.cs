using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class SDKTxwyPassportInfo 
{
    public int uid;                    //通行证ID，玩家唯一数字标识
    public string uname;                 //用户名
    public string sid;                  //通行证登录凭证
    public bool isGuest;                //标识是否游客身份登录
    public bool isBindPhoneNum;         //标识用户是否已经绑定手机
}

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

    //获取用户信息
    public SDKTxwyPassportInfo GetPassportInfo()
    {
        SDKTxwyPassportInfo info = new SDKTxwyPassportInfo();

        AndroidJavaClass unityPlayer = new AndroidJavaClass ("com.unity3d.player.UnityPlayer");
		AndroidJavaObject currentActivity = unityPlayer.GetStatic<AndroidJavaObject> ("currentActivity");
        AndroidJavaObject passwordInfo = currentActivity.Call<AndroidJavaObject> ("GetPassportInfo");

        setMsg("get info");
        setMsg("get info 11 " + passwordInfo.Get<int>("uid"));
        info.uid = passwordInfo.Get<int>("uid");
        info.uname = passwordInfo.Get<string>("uname");
        info.sid = passwordInfo.Get<string>("sid");
        info.isGuest = passwordInfo.Get<bool>("isGuest");
        info.isBindPhoneNum = passwordInfo.Get<bool>("isBindPhoneNum");

        return info;
    }

    public void Btn_GetInfo()
    {
        SDKTxwyPassportInfo info = GetPassportInfo();
        setMsg("sid : " + info.sid +"/n uname: "+ info.uname + "/n isGuest :" + info.isGuest + "/n isBind" + info.isBindPhoneNum);
    }

    public void GetInfo()
    {
        AndroidJavaClass jc = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
        AndroidJavaObject jo = jc.GetStatic<AndroidJavaObject>("currentActivity");
        jo.Call("GetPassportInfo");
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
        jo.Call("SignIn", m_objName, "SignInSuccessBackFunc", "SignOutSccessCallBack");
        //AndroidJavaClass unityPlayer = new AndroidJavaClass ("com.unity3d.player.UnityPlayer");
        //AndroidJavaObject currentActivity = unityPlayer.GetStatic<AndroidJavaObject> ("currentActivity");
        //AndroidJavaClass androidCall = new AndroidJavaClass ("com.txwy.passport.sdk.SDKTxwyPassport");

        //androidCall.CallStatic ("signIn" , currentActivity , signInDelegete);
    }

    public void SignInSuccessBackFunc(string msg)
    { 
        setMsg("SignIn " + msg);
    }

    public void SignOutSccessCallBack()
    {
        setMsg("Signout");
    }

    public void SignOut()
    {
        AndroidJavaClass jc = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
        AndroidJavaObject jo = jc.GetStatic<AndroidJavaObject>("currentActivity");
        jo.Call("SignOut");
    }

    public void OpenUserCenter()
    {
        SDKTxwyPassportInfo info = GetPassportInfo();
        string _serverID = "998";
        string _playerName = info.uname;
        string _clientVision = "1.1.1";
        UserCenter(_serverID, _playerName, _clientVision);
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
