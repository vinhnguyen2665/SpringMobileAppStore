package com.vinhnq.repository;

import com.vinhnq.entity.AppInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface AppInfoRepository  extends JpaRepository<AppInfo, Long> {
    AppInfo save(AppInfo appInfo);
    AppInfo saveAndFlush(AppInfo appInfo);
    Optional<AppInfo> findById(Long id);
    List<AppInfo> getAppInfoByDeleteFlgOrderByIdDesc(String deleteFlg);
}
