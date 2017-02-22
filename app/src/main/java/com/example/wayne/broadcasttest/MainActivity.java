package com.example.wayne.broadcasttest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.ThumbnailUtils;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends BaseActivity {
    private NetworkChangeReceiver mNetworkChangeReceiver;
    private LocalReceiver mLocalReceiver;
    private LocalBroadcastManager mLocalBroadcastManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
//        mNetworkChangeReceiver = new NetworkChangeReceiver();
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
//        registerReceiver(mNetworkChangeReceiver,intentFilter);
        //获取本地广播管理的实例
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
        mLocalReceiver = new LocalReceiver();
        IntentFilter intentFilter1 = new IntentFilter();
        intentFilter1.addAction("com.example.wayne.broadcasttest.LOCAL_BROADCAST");
        //使用本地管理器注册广播
        mLocalBroadcastManager.registerReceiver(mLocalReceiver,intentFilter1);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(mNetworkChangeReceiver);
        mLocalBroadcastManager.unregisterReceiver(mLocalReceiver);
    }
    //点击事件
    public void sendBroadCast(View view) {
//        Intent intent = new Intent();
//        intent.setAction("com.example.wayne.broadcasttest.MY_BROADCAST");
////        sendBroadcast(intent);
//        sendOrderedBroadcast(intent,null);
        Intent intent = new Intent("com.example.wayne.broadcasttest.LOCAL_BROADCAST");
        mLocalBroadcastManager.sendBroadcast(intent);//发送本地广播

    }

    public void force_offline(View view) {
        Intent intent = new Intent("com.example.wayne.broadcastpractice.FORCE_OFFLINE");
        sendBroadcast(intent);
    }

    class LocalReceiver extends  BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "received is localcast", Toast.LENGTH_SHORT).show();
        }
    }

    class NetworkChangeReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager  conn = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = conn.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isAvailable()){
                Toast.makeText(context, "network is available", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context, "network is unavailable", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
