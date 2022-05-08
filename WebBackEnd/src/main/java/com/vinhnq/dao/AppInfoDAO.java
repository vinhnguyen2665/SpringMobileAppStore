package com.vinhnq.dao;

import com.vinhnq.beans.AppInfoBean;
import com.vinhnq.entity.AppInfo;

import java.util.List;

public interface AppInfoDAO {
    AppInfo getLatestAppInfo(String packageName, String type, String deleteFlg);
    AppInfo getLatestAppInfo(String packageName, String type,String versionCode, String deleteFlg);
    List<AppInfo> getListAppInfoForHome();
    List<AppInfo> getListApp(AppInfoBean appInfo);
}
