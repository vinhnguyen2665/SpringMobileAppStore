package com.vinhnq.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "app_info", schema = "app_store", catalog = "")
public class AppInfo {
    private long id;
    private String appType;
    private String appName;
    private String packageName;
    private long versionCode;
    private String versionCodeString;
    private String versionName;
    private String iconPath;
    private String appPath;
    private String manifestPath;
    private Double appSize;
    private String appSizeUnit;
    private String deleteFlg;
    private String updateContent;
    private Timestamp createDate;
    private long createById;
    private Timestamp updateDate;
    private Long updateById;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "app_type", nullable = false, length = 20)
    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    @Basic
    @Column(name = "app_name", nullable = false, length = 255)
    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    @Basic
    @Column(name = "package_name", nullable = false, length = 255)
    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    @Basic
    @Column(name = "version_code", nullable = false)
    public long getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(long versionCode) {
        this.versionCode = versionCode;
    }

    @Basic
    @Column(name = "version_code_string", nullable = false, length = 50)
    public String getVersionCodeString() {
        return versionCodeString;
    }

    public void setVersionCodeString(String versionCodeString) {
        this.versionCodeString = versionCodeString;
    }

    @Basic
    @Column(name = "version_name", nullable = false, length = 50)
    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    @Basic
    @Column(name = "icon_path", nullable = true, length = 255)
    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    @Basic
    @Column(name = "app_path", nullable = true, length = 255)
    public String getAppPath() {
        return appPath;
    }

    public void setAppPath(String appPath) {
        this.appPath = appPath;
    }

    @Basic
    @Column(name = "manifest_path", nullable = true, length = 255)
    public String getManifestPath() {
        return manifestPath;
    }

    public void setManifestPath(String manifestPath) {
        this.manifestPath = manifestPath;
    }

    @Basic
    @Column(name = "app_size", nullable = true)
    public Double getAppSize() {
        return appSize;
    }

    public void setAppSize(Double appSize) {
        this.appSize = appSize;
    }

    @Basic
    @Column(name = "app_size_unit", nullable = true, length = 20)
    public String getAppSizeUnit() {
        return appSizeUnit;
    }

    public void setAppSizeUnit(String appSizeUnit) {
        this.appSizeUnit = appSizeUnit;
    }

    @Basic
    @Column(name = "delete_flg", nullable = false, length = 1)
    public String getDeleteFlg() {
        return deleteFlg;
    }

    public void setDeleteFlg(String deleteFlg) {
        this.deleteFlg = deleteFlg;
    }

    @Basic
    @Column(name = "create_date", nullable = false)
    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    @Basic
    @Column(name = "create_by_id", nullable = false)
    public long getCreateById() {
        return createById;
    }

    public void setCreateById(long createById) {
        this.createById = createById;
    }

    @Basic
    @Column(name = "update_date", nullable = true)
    public Timestamp getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    @Basic
    @Column(name = "update_by_id", nullable = true)
    public Long getUpdateById() {
        return updateById;
    }

    public void setUpdateById(Long updateById) {
        this.updateById = updateById;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppInfo appInfo = (AppInfo) o;

        if (id != appInfo.id) return false;
        if (versionCode != appInfo.versionCode) return false;
        if (createById != appInfo.createById) return false;
        if (appType != null ? !appType.equals(appInfo.appType) : appInfo.appType != null) return false;
        if (appName != null ? !appName.equals(appInfo.appName) : appInfo.appName != null) return false;
        if (packageName != null ? !packageName.equals(appInfo.packageName) : appInfo.packageName != null) return false;
        if (versionCodeString != null ? !versionCodeString.equals(appInfo.versionCodeString) : appInfo.versionCodeString != null)
            return false;
        if (versionName != null ? !versionName.equals(appInfo.versionName) : appInfo.versionName != null) return false;
        if (iconPath != null ? !iconPath.equals(appInfo.iconPath) : appInfo.iconPath != null) return false;
        if (appPath != null ? !appPath.equals(appInfo.appPath) : appInfo.appPath != null) return false;
        if (deleteFlg != null ? !deleteFlg.equals(appInfo.deleteFlg) : appInfo.deleteFlg != null) return false;
        if (createDate != null ? !createDate.equals(appInfo.createDate) : appInfo.createDate != null) return false;
        if (updateDate != null ? !updateDate.equals(appInfo.updateDate) : appInfo.updateDate != null) return false;
        if (updateById != null ? !updateById.equals(appInfo.updateById) : appInfo.updateById != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (appType != null ? appType.hashCode() : 0);
        result = 31 * result + (appName != null ? appName.hashCode() : 0);
        result = 31 * result + (packageName != null ? packageName.hashCode() : 0);
        result = 31 * result + (int) (versionCode ^ (versionCode >>> 32));
        result = 31 * result + (versionCodeString != null ? versionCodeString.hashCode() : 0);
        result = 31 * result + (versionName != null ? versionName.hashCode() : 0);
        result = 31 * result + (iconPath != null ? iconPath.hashCode() : 0);
        result = 31 * result + (appPath != null ? appPath.hashCode() : 0);
        result = 31 * result + (deleteFlg != null ? deleteFlg.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (int) (createById ^ (createById >>> 32));
        result = 31 * result + (updateDate != null ? updateDate.hashCode() : 0);
        result = 31 * result + (updateById != null ? updateById.hashCode() : 0);
        return result;
    }

    @Basic
    @Column(name = "update_content")
    public String getUpdateContent() {
        return updateContent;
    }

    public void setUpdateContent(String updateContent) {
        this.updateContent = updateContent;
    }
}
