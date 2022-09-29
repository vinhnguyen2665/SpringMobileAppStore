package com.vinhnq.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.vinhnq.common.CommonConst;
import com.vinhnq.common.RSAUtil;
import com.vinhnq.common.Utils;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;

import java.io.File;
import java.nio.file.Path;
import java.sql.Timestamp;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class AppInfoBean {

    public static final String APK = "apk";
    public static final String IPA = "ipa";

    @JsonIgnore
    public static final String IPA_RESOURCE = "app-resource/ipa/";
    @JsonIgnore
    public static final String APK_RESOURCE = "app-resource/apk/";

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
    private String manifestPath;
    private String manifestResource;
    private Double appSize;
    private String appSizeUnit;
    private String deleteFlg;
    private String updateContent;
    private Timestamp createDate;
    private Long createById;
    private Timestamp updateDate;
    private Long updateById;
    private Path outDir;
    @JsonIgnore
    private MediaType mediaType;
    @JsonIgnore
    private MediaType apkMediaType = MediaType.parseMediaType("application/vnd.android.package-archive");
    @JsonIgnore
    private MediaType ipaMediaType = MediaType.parseMediaType("application/octet-stream");

    public AppInfoBean encrypt(){
        if(!this.encrypt){
            if(!Utils.isEmpty(this.iconPath)){
                setIconPath(RSAUtil.encrypt(this.iconPath));
            }
            if(!Utils.isEmpty(this.appPath)){
                setAppPath(RSAUtil.encrypt(this.appPath));
            }
            if(!Utils.isEmpty(this.manifestPath)){
                setManifestPath(RSAUtil.encrypt(this.manifestPath));
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
            if (!Utils.isEmpty(this.manifestPath)) {
                setManifestPath(RSAUtil.decrypt(this.manifestPath));
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

    public String getManifestResource() {
        if(null == manifestResource && null != manifestPath){
            this.manifestResource = convertDir2Resource(this.manifestPath);
        }
        return manifestResource;
    }

    private String convertDir2Resource(String dataPath){
        String dir = "";
        String res = "";
        if(isEncrypt()){
            dataPath = RSAUtil.decrypt(dataPath);
        }
        if(APK.equals(appType)){
            dir = CommonConst.COMMON_FILE.HOME_APK_RESOURCE;
            res = APK_RESOURCE;
        } else if(IPA.equals(appType)){
            dir = CommonConst.COMMON_FILE.HOME_IPA_RESOURCE;
            res = IPA_RESOURCE;
        }
        return dataPath.replace(dir, res).replace(File.separator, "/");
    }
}
