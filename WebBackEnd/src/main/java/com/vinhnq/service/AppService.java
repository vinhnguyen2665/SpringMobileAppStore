package com.vinhnq.service;

import com.vinhnq.beans.AppInfoBean;

import java.util.List;
import java.util.Optional;

public interface AppService {
    AppInfoBean getLatestAppInfo(String packageName, String type,boolean encrypt);
    Optional<AppInfoBean> getAppInfo(Long id, boolean encrypt);
    AppInfoBean getAppInfo(String packageName, String type, String versionCode, boolean encrypt);
    List<AppInfoBean> getAllListAppInfo(String deleteFlg, boolean encrypt);
    List<AppInfoBean> getListAppInfoForHome(boolean encrypt);
    List<AppInfoBean> getListAppCondition(AppInfoBean appInfoBean, boolean encrypt);
}
