package com.example.file.transfer.assistant.config;

import android.content.Context;
import android.os.Environment;

import com.yanzhenjie.andserver.annotation.Config;
import com.yanzhenjie.andserver.framework.config.WebConfig;
import com.yanzhenjie.andserver.framework.website.AssetsWebsite;
import com.yanzhenjie.andserver.framework.website.FileBrowser;
import com.yanzhenjie.andserver.framework.website.StorageWebsite;

import java.io.File;

/**
 * // 增加一个位于/sdcard/Download/AndServer/目录的网站
 * File rootPath = Environment.getExternalStorageDirectory();
 * File file = new File(rootPath, "Download/AndServer");
 * StorageWebsite storageWebsite = new StorageWebsite(file.getPath());
 * delegate.addWebsite(storageWebsite);
 */
@Config
public class AppConfig implements WebConfig {

    @Override
    public void onConfig(Context context, Delegate delegate) {
        // 增加一个位于assets的web目录的网站
        delegate.addWebsite(new AssetsWebsite(context, "/web"));
        // 添加一个文件浏览器网站
        File rootPath = Environment.getExternalStorageDirectory();
        FileBrowser fileBrowser = new FileBrowser(rootPath.getPath());
        delegate.addWebsite(fileBrowser);
    }
}
