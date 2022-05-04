package com.vinhnq.dao;

import com.vinhnq.entity.AppInfo;

public interface AppInfoDAO {
    AppInfo getLatestAppInfo(String packageName, String deleteFlg);
    AppInfo getLatestAppInfo(String packageName,String versionCode, String deleteFlg);
}
