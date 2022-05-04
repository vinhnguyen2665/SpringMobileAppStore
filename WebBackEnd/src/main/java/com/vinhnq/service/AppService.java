package com.vinhnq.service;

import com.vinhnq.beans.AppInfoBean;

import java.util.List;

public interface AppService {
    AppInfoBean getLatestAppInfo(String packageName, boolean encrypt);
    AppInfoBean getAppInfo(String packageName, String versionCode, boolean encrypt);
    List<AppInfoBean> getListAppInfo(String deleteFlg, boolean encrypt);
}
