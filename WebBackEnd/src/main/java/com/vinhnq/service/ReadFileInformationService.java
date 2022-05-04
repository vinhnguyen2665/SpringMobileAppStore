package com.vinhnq.service;

import com.vinhnq.beans.AppInfoBean;

import java.io.File;

public interface ReadFileInformationService {
    AppInfoBean read(File file);
    AppInfoBean readFileAPK(File apk);
    AppInfoBean readFileIPA(File ipa);
}
