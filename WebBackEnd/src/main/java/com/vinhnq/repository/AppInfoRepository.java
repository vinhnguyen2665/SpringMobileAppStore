package com.vinhnq.repository;

import com.vinhnq.entity.AppInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface AppInfoRepository  extends JpaRepository<AppInfo, Long> {
    AppInfo save(AppInfo appInfo);
    AppInfo saveAndFlush(AppInfo appInfo);
    List<AppInfo> getAppInfoByDeleteFlg(String deleteFlg);
}
