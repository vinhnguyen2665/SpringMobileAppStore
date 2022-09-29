package com.vinhnq.service;

import com.vinhnq.beans.AppInfoBean;
import com.vinhnq.beans.FileSize;
import com.vinhnq.entity.AppInfo;

import java.io.File;
import java.nio.file.Path;

public interface ReadFileInformationService {
    AppInfoBean read(File fileFile, FileSize size, String hostUrl, String updateContent);
    AppInfoBean readFileAPK(File apk, FileSize size);
    AppInfoBean readFileIPA(File ipa, FileSize size, String hostUrl);
    Path createManifestFile(AppInfo ipaApp, Path outDir, String hostUrl);
}
