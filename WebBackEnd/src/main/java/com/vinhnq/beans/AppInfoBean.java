package com.vinhnq.beans;

import com.vinhnq.common.RSAUtil;
import com.vinhnq.common.Utils;
import lombok.Data;
import lombok.NonNull;

import java.sql.Timestamp;

@Data
public class AppInfoBean {
    public static final String APK = "apk";
    public static final String IPA = "ipa";

    boolean encrypt = false;
    private Long id;
    @NonNull
    private String type;
    private String appName;
    private String packageName;
    private Long versionCode;
    private String versionCodeString;
    private String versionName;
    private String iconPath;

    private String appPath;
    private String deleteFlg;
    private Timestamp createDate;
    private Long createById;
    private Timestamp updateDate;
    private Long updateById;

    public AppInfoBean encrypt(){
        if(!this.encrypt){
            if(!Utils.isEmpty(this.iconPath)){
                setIconPath(RSAUtil.encrypt(this.iconPath));
            }
            if(!Utils.isEmpty(this.appPath)){
                setAppPath(RSAUtil.encrypt(this.appPath));
            }
        }
        this.encrypt = true;
        return this;
    }
    public AppInfoBean decrypt(){
        if(this.encrypt) {
            if (!Utils.isEmpty(this.iconPath)) {
                setIconPath(RSAUtil.decrypt(this.iconPath));
            }
            if (!Utils.isEmpty(this.appPath)) {
                setAppPath(RSAUtil.decrypt(this.appPath));
            }
        }
        this.encrypt = false;
        return this;
    }
}
