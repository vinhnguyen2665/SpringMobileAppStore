package com.vinhnq.beans;

import com.vinhnq.common.RSAUtil;
import com.vinhnq.common.Utils;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;

import java.nio.file.Path;
import java.sql.Timestamp;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class AppInfoBean {
    public static final String APK = "apk";
    public static final String IPA = "ipa";

    boolean encrypt = false;
    private Long id;
    @NonNull
    private String appType;
    private String appName;
    private String packageName;
    private Long versionCode;
    private String versionCodeString;
    private String versionName;
    private String iconPath;

    private String appPath;
    private Double appSize;
    private String appSizeUnit;
    private String deleteFlg;
    private Timestamp createDate;
    private Long createById;
    private Timestamp updateDate;
    private Long updateById;
    private Path outDir;
    private MediaType mediaType;
    private MediaType apkMediaType = MediaType.parseMediaType("application/vnd.android.package-archive");
    private MediaType ipaMediaType = MediaType.parseMediaType("application/octet-stream");

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

    public MediaType getMediaType() {
        if(null == mediaType){
            if(APK.equals(appType)){
                mediaType = apkMediaType;
            } else if(IPA.equals(appType)){
                mediaType = ipaMediaType;
            }
        }
        return mediaType;
    }

    public void setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }
}
