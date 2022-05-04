package com.vinhnq.dao.impl;

import com.vinhnq.common.CommonCode;
import com.vinhnq.common.CommonConst;
import com.vinhnq.common.CommonSql;
import com.vinhnq.common.FileUtils;
import com.vinhnq.dao.AppInfoDAO;
import com.vinhnq.entity.AppInfo;
import com.vinhnq.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository(value = "AppInfoDAO")
@Transactional(transactionManager = CommonConst.CONFIG.HibernateTransactionManager)
@EnableTransactionManagement
public class AppInfoDAOImpl implements AppInfoDAO {
    private static final Logger logger = LogManager.getLogger(AppInfoDAOImpl.class);

    private final SessionFactory sessionFactory;

    public AppInfoDAOImpl(@Qualifier(CommonConst.CONFIG.HibernateSessionFactory) SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public AppInfo getLatestAppInfo(String packageName, String deleteFlg) {
        try {
            AppInfo appInfo = new AppInfo();
            Session session = this.sessionFactory.getCurrentSession();

            String content = FileUtils.readSqlFile(CommonSql.getLatestAppInfoByPackageNameAndDeleteFlg);
            Query<AppInfo> query = session.createSQLQuery(content).addEntity("a", AppInfo.class);
            query.setParameter("packageName", packageName);
            query.setParameter("deleteFlg", deleteFlg);
            List<AppInfo> result = query.list();

            if (result.size() > 0) {
                appInfo = result.get(0);
            }
            return appInfo;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public AppInfo getLatestAppInfo(String packageName, String versionCode, String deleteFlg) {
        try {
            AppInfo appInfo = new AppInfo();
            Session session = this.sessionFactory.getCurrentSession();

            String content = FileUtils.readSqlFile(CommonSql.getLatestAppInfoByPackageNameAndVersionCodeAndDeleteFlg);
            Query<AppInfo> query = session.createSQLQuery(content).addEntity("a", AppInfo.class);
            query.setParameter("packageName", packageName);
            query.setParameter("versionCode", versionCode);
            query.setParameter("deleteFlg", deleteFlg);
            List<AppInfo> result = query.list();

            if (result.size() > 0) {
                appInfo = result.get(0);
            }
            return appInfo;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
