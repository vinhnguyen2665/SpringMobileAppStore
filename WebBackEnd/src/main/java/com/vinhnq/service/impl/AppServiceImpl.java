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
import java.util.Optional;

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
    public AppInfoBean getLatestAppInfo(String packageName, String type, boolean encrypt) {
        try {
            AppInfo appInfo = this.appInfoDAO.getLatestAppInfo(packageName, type,CommonConst.DELETE_FLG.NON_DELETE);
            return EntityUtils.convertAppInfoToAppInfoBean(appInfo, encrypt);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public Optional<AppInfoBean> getAppInfo(Long id, boolean encrypt) {
        try {
            Optional<AppInfo> appInfo = this.appInfoRepository.findById(id);
            if(appInfo.isPresent()){
                return Optional.of(EntityUtils.convertAppInfoToAppInfoBean(appInfo.get(), encrypt)) ;
            } else {
                return null;
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public AppInfoBean getAppInfo(String packageName, String type, String versionCode, boolean encrypt) {
        try {
            AppInfo appInfo = this.appInfoDAO.getLatestAppInfo(packageName, type,CommonConst.DELETE_FLG.NON_DELETE);
            return EntityUtils.convertAppInfoToAppInfoBean(appInfo, encrypt);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public List<AppInfoBean> getAllListAppInfo(String deleteFlg, boolean encrypt) {
        try {
            List<AppInfo> appInfos = this.appInfoRepository.getAppInfoByDeleteFlgOrderByIdDesc(deleteFlg);
            return EntityUtils.convertAppInfoToAppInfoBean(appInfos, encrypt);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public List<AppInfoBean> getListAppInfoForHome(boolean encrypt) {
        try {
            List<AppInfo> appInfos = this.appInfoDAO.getListAppInfoForHome();
            return EntityUtils.convertAppInfoToAppInfoBean(appInfos, encrypt);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public List<AppInfoBean> getListAppCondition(AppInfoBean appInfoBean, boolean encrypt) {
        try {
            List<AppInfo> appInfos = this.appInfoDAO.getListApp(appInfoBean);
            return EntityUtils.convertAppInfoToAppInfoBean(appInfos, encrypt);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
