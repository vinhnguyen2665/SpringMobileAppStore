package com.vinhnq.service;

import com.vinhnq.beans.AppInfoBean;
import com.vinhnq.beans.FileSize;

import java.io.File;
import java.nio.file.Path;

public interface ReadFileInformationService {
    AppInfoBean read(File fileFile, FileSize size, String hostUrl);
    AppInfoBean readFileAPK(File apk, FileSize size);
    AppInfoBean readFileIPA(File ipa, FileSize size, String hostUrl);
    Path createManifestFile(AppInfoBean ipaApp, Path outDir, String hostUrl);
}
