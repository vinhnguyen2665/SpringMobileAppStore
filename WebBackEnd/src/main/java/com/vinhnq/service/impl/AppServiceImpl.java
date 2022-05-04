package com.vinhnq.service.impl;

import com.vinhnq.beans.AppInfoBean;
import com.vinhnq.common.CommonConst;
import com.vinhnq.common.EntityUtils;
import com.vinhnq.dao.AppInfoDAO;
import com.vinhnq.entity.AppInfo;
import com.vinhnq.repository.AppInfoRepository;
import com.vinhnq.service.AppService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppServiceImpl implements AppService {
    private static final Logger logger = LogManager.getLogger(AppServiceImpl.class);

    private final AppInfoDAO appInfoDAO;
    private final AppInfoRepository appInfoRepository;

    @Autowired
    public AppServiceImpl(AppInfoDAO appInfoDAO, AppInfoRepository appInfoRepository) {
        this.appInfoDAO = appInfoDAO;
        this.appInfoRepository = appInfoRepository;
    }

    @Override
    public AppInfoBean getLatestAppInfo(String packageName, boolean encrypt) {
        try {
            AppInfo appInfo = this.appInfoDAO.getLatestAppInfo(packageName, CommonConst.DELETE_FLG.NON_DELETE);
            return EntityUtils.convertAppInfoToAppInfoBean(appInfo, encrypt);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public AppInfoBean getAppInfo(String packageName, String versionCode, boolean encrypt) {
        try {
            AppInfo appInfo = this.appInfoDAO.getLatestAppInfo(packageName, CommonConst.DELETE_FLG.NON_DELETE);
            return EntityUtils.convertAppInfoToAppInfoBean(appInfo, encrypt);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public List<AppInfoBean> getListAppInfo(String deleteFlg, boolean encrypt) {
        try {
            List<AppInfo> appInfos = this.appInfoRepository.getAppInfoByDeleteFlg(deleteFlg);
            return EntityUtils.convertAppInfoToAppInfoBean(appInfos, encrypt);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
