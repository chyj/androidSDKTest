using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class UnityTest : MonoBehaviour
{
    public Text mText;
    public void BeCallFunc(string _content)
    {
        
        setMsg(ref _content);
    }
    private void setMsg(ref string _str)
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
        // setMsg(ref ret);
        AndroidJavaClass jc = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
        AndroidJavaObject jo = jc.GetStatic<AndroidJavaObject>("currentActivity");
        jo.Call("SignIn");
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

}

//class AndroidPluginCallback : AndroidJavaProxy
//{
//    public AndroidPluginCallback() : base("com.txwySDKTest.PluginCallback") { }

//    public void onSuccess(string videoPath)
//    {
//        Debug.Log("ENTER callback onSuccess: " + videoPath);
//    }
//    public void onError(string errorMessage)
//    {
//        Debug.Log("ENTER callback onError: " + errorMessage);
//    }
//}

