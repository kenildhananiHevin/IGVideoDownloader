package com.cashloan.myapplication.igvideodownloader.brodcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.cashloan.myapplication.igvideodownloader.service.VideoLiveWallpaperIGService;


public class IgBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent service = new Intent(context, VideoLiveWallpaperIGService.class);
        context.startService(service);
    }
}
