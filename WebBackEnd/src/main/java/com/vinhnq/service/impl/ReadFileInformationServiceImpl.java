package com.vinhnq.service.impl;


import com.dd.plist.NSDictionary;
import com.dd.plist.NSString;
import com.dd.plist.PropertyListParser;
import com.vinhnq.beans.AppInfoBean;
import com.vinhnq.beans.FileCustom;
import com.vinhnq.beans.FileSize;
import com.vinhnq.common.*;

import com.vinhnq.entity.AppInfo;
import com.vinhnq.kylinworks.IPngConverter;
import com.vinhnq.repository.AppInfoRepository;
import com.vinhnq.service.ReadFileInformationService;
import net.dongliu.apk.parser.ApkFile;
import net.dongliu.apk.parser.bean.*;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;


@Service
public class ReadFileInformationServiceImpl implements ReadFileInformationService {
    private static final Logger logger = LogManager.getLogger(ReadFileInformationServiceImpl.class);

    private final AppInfoRepository appInfoRepository;


    public ReadFileInformationServiceImpl() {
        this.appInfoRepository = null;
    }

    @Autowired
    public ReadFileInformationServiceImpl(AppInfoRepository appInfoRepository) {
        this.appInfoRepository = appInfoRepository;
    }


    @Override
    public AppInfoBean read(File file, FileSize size, String hostUrl, String updateContent) {
        String extension = FilenameUtils.getExtension(file.getPath());
        AppInfoBean result = null;
        if (AppInfoBean.APK.equals(extension.toLowerCase())) {
            result = readFileAPK(file, size);
        } else if (AppInfoBean.IPA.equals(extension.toLowerCase())) {
            result = readFileIPA(file, size, hostUrl);
        }
        AppInfo appInfo = EntityUtils.convertApplicationInformationToAppInfo(result);
        appInfo.setDeleteFlg(CommonConst.DELETE_FLG.NON_DELETE);
        appInfo.setCreateDate(new Timestamp(new Date().getTime()));
        appInfo.setId(0);
        appInfo.setUpdateContent(updateContent);
        AppInfo app = this.appInfoRepository.saveAndFlush(appInfo);

        if (AppInfoBean.IPA.equals(app.getAppType())) {
            Path manifestPath = createManifestFile(app, result.getOutDir(), hostUrl);
            appInfo.setManifestPath(manifestPath.toString());
            app = this.appInfoRepository.saveAndFlush(appInfo);
        }
        AppInfoBean re = EntityUtils.convertAppInfoToAppInfoBean(app);
        return re;
    }

    @Override
    public AppInfoBean readFileAPK(File apk, FileSize size) {
        try {
            String currentTimeString = CommonConst.SIMPLE_DATE_FORMAT.formatterYYYYMMDD_HHMMSS_SSS.format(new Date());
            ApkFile apkFile = new ApkFile(apk);
            ApkMeta apkMeta = apkFile.getApkMeta();
            /*            for (UseFeature feature : apkMeta.getUsesFeatures()) {
                System.out.println(feature.getName());
            }*/

            List<IconFace> iconFace = apkFile.getAllIcons();
            List<Icon> iconList = new ArrayList();
            for (IconFace icon : iconFace) {
                List<Icon> ic = parseApkIconInfo(icon);
                if (!ic.isEmpty()) {
                    iconList.addAll(ic);
                }
            }

            Icon maxDensity = iconList.stream()
                    .filter(icon -> !icon.getPath().contains("foreground"))
                    .max(Comparator.comparing(Icon::getDensity))
                    .orElse(null);

            if ("xml".equals(FilenameUtils.getExtension(maxDensity.getPath()))) {
                maxDensity = iconList.stream()
                        .filter(icon -> (!"xml".equals(FilenameUtils.getExtension(icon.getPath()))))
                        .max(Comparator.comparing(Icon::getDensity))
                        .orElse(null);
            }

            AppInfoBean app = new AppInfoBean(AppInfoBean.APK);
            app.setAppName(apkMeta.getLabel());
            app.setPackageName(apkMeta.getPackageName());
            app.setVersionCode(apkMeta.getVersionCode());
            app.setVersionCodeString(String.valueOf(apkMeta.getVersionCode()));
            app.setVersionName(apkMeta.getVersionName());
            app.setAppSize(size.getValueFormat());
            app.setAppSizeUnit(size.getUnit());

            Path outDir = Paths.get(CommonConst.COMMON_FILE.HOME_APK_RESOURCE, app.getPackageName(), app.getVersionName() + "_" + currentTimeString);
            FileUtils.createDirectoryIfNotExists(outDir.toString());
            Path outApkPath = Paths.get(outDir.toString(), apk.getName());

            Files.copy(Paths.get(apk.getPath()), outApkPath, StandardCopyOption.REPLACE_EXISTING);
            app.setOutDir(outDir);
            app.setAppPath(outApkPath.toString());
            String iconPath = null;
            if (null != maxDensity) {
                Path path = Paths.get(maxDensity.getPath());

                FileUtils.createDirectoryIfNotExists(outDir.toString());
                Path outPath = Paths.get(outDir.toString(), String.valueOf(path.getFileName()));

                iconPath = outPath.toString();
                Files.write(outPath, maxDensity.getData());
            }
            app.setIconPath(iconPath);
            return app;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    private List<Icon> parseApkIconInfo(IconFace iconFace) {
        Object i = iconFace;
        Icon background = null;
        Icon foreground = null;
        List<Icon> result = new ArrayList<>();
        if (i.getClass().getSimpleName().equals("AdaptiveIcon")) {
            AdaptiveIcon i2 = (AdaptiveIcon) i;
            background = i2.getBackground();
            foreground = i2.getForeground();
        } else if (i.getClass().getSimpleName().equals("Icon")) {
            background = (Icon) i;
        }
        if (null != background) {
            result.add(new Icon(background.getPath(), background.getDensity(), background.getData()));
        }
        if (null != foreground) {
            result.add(new Icon(foreground.getPath(), foreground.getDensity(), foreground.getData()));
        }
        return result;
    }

    @Override
    public AppInfoBean readFileIPA(File ipaFile, FileSize size, String hostUrl) {
        try {

            String currentTimeString = CommonConst.SIMPLE_DATE_FORMAT.formatterYYYYMMDD_HHMMSS_SSS.format(new Date());
            AppInfoBean app = new AppInfoBean(AppInfoBean.IPA);

            Pattern newIconRegular = Pattern.compile("^Payload\\/.*\\.app\\/AppIcon-?_?\\w*(\\d+(\\.\\d+)?)x(\\d+(\\.\\d+)?)(@\\dx)?(~ipad)?\\.png$");
            Pattern oldIconRegular = Pattern.compile("^Payload\\/.*\\.app\\/Icon-?_?\\w*(\\d+(\\.\\d+)?)?.png$");
            Pattern assetRegular = Pattern.compile("^Payload\\/.*\\.app/Assets.car$");
            Pattern infoPlistRegular = Pattern.compile("^Payload\\/.*\\.app/Info.plist$");

            Path outTmpDir = Paths.get(CommonConst.COMMON_FILE.HOME_TMP);

            FileUtils.createDirectoryIfNotExists(outTmpDir.toString());
            List<FileCustom> iconList = new ArrayList<>();
            FileCustom fileInfo = null;

            ZipInputStream zis = new ZipInputStream(new FileInputStream(ipaFile));
            ZipEntry zipEntry = zis.getNextEntry();
            while (zipEntry != null) {
                if (newIconRegular.matcher(zipEntry.getName()).matches()) {
                    File f = extractFile(outTmpDir, zis, zipEntry);
                    if (null != f) {
                        iconList.add((FileCustom) f);
                    }
                }
                if (oldIconRegular.matcher(zipEntry.getName()).matches()) {
                    File f = extractFile(outTmpDir, zis, zipEntry);
                    if (null != f) {
                        iconList.add((FileCustom) f);
                    }
                }
                if (infoPlistRegular.matcher(zipEntry.getName()).matches()) {
                    fileInfo = extractFile(outTmpDir, zis, zipEntry);
                }

                zipEntry = zis.getNextEntry();
            }
            zis.closeEntry();
            zis.close();


            Map<String, String> map = new HashMap<String, String>();
            //Need third-party jar package dd-plist
            if (null != fileInfo) {
                NSDictionary rootDict = (NSDictionary) PropertyListParser.parse(fileInfo);
                //Application package name
                NSString parametersCFBundleIdentifier = (NSString) rootDict.objectForKey("CFBundleIdentifier");
                map.put("CFBundleIdentifier", parametersCFBundleIdentifier.toString());
                //Application name (NSString)rootDict.objectForKey("CFBundleName");
                NSString parametersCFBundleName = (NSString) rootDict.objectForKey("CFBundleName");
                map.put("CFBundleName", parametersCFBundleName.toString());
                //Application version parameters = (NSString)rootDict.objectForKey("CFBundleVersion");
                NSString parametersCFBundleVersion = (NSString) rootDict.objectForKey("CFBundleVersion");
                map.put("CFBundleVersion", parametersCFBundleVersion.toString());
                //Application display name parameters = (NSString)rootDict.objectForKey("CFBundleDisplayName");
                NSString parametersCFBundleDisplayName = (NSString) rootDict.objectForKey("CFBundleDisplayName");
                if(null == parametersCFBundleDisplayName){
                    parametersCFBundleDisplayName = (NSString) rootDict.objectForKey("CFBundleName");
                }
                map.put("CFBundleDisplayName", parametersCFBundleDisplayName.toString());
                //The minimum version of IOS required by the application parameters = (NSString)rootDict.objectForKey("MinimumOSVersion");
                NSString parametersMinimumOSVersion = (NSString) rootDict.objectForKey("MinimumOSVersion");
                map.put("MinimumOSVersion", parametersMinimumOSVersion.toString());

                NSString parametersCFBundleExecutable = (NSString) rootDict.objectForKey("CFBundleExecutable");
                map.put("CFBundleExecutable", parametersCFBundleExecutable.toString());

                /*NSDictionary parametersCFBundleIcon = (NSDictionary) rootDict.objectForKey("CFBundleIcons");
                NSDictionary parametersCFBundlePrimaryIcon = (NSDictionary) parametersCFBundleIcon.objectForKey("CFBundlePrimaryIcon");
                NSString parametersCFBundleIconName = (NSString) parametersCFBundlePrimaryIcon.objectForKey("CFBundleIconName");
                map.put("CFBundleIconName", parametersCFBundleIconName.toString());
                NSArray parametersCFBundleIconFiles = (NSArray) parametersCFBundlePrimaryIcon.objectForKey("CFBundleIconFiles");
                map.put("CFBundleIconFiles", parametersCFBundleIconFiles[0].toString());*/

                NSString parametersCFBundleShortVersionString = (NSString) rootDict.objectForKey("CFBundleShortVersionString");
                map.put("CFBundleShortVersionString", parametersCFBundleShortVersionString.toString());
                /*NSArray parametersCFBundleSupportedPlatforms = (NSArray) rootDict.objectForKey("CFBundleSupportedPlatforms");
                map.put("CFBundleSupportedPlatforms", parametersCFBundleSupportedPlatforms.toString());*/
            }


            app.setPackageName(map.get("CFBundleIdentifier"));
            app.setVersionCode(null == map.get("CFBundleVersion") ? null : Long.valueOf(map.get("CFBundleVersion").replaceAll("\\.", "")));
            app.setVersionCodeString(map.get("CFBundleVersion"));
            app.setVersionName(map.get("CFBundleShortVersionString"));
            app.setAppName(map.get("CFBundleDisplayName"));
            app.setAppSize(size.getValueFormat());
            app.setAppSizeUnit(size.getUnit());

            Path outDir = Paths.get(CommonConst.COMMON_FILE.HOME_IPA_RESOURCE, app.getPackageName(), app.getVersionName() + "_" + currentTimeString);
            FileUtils.createDirectoryIfNotExists(outDir.toString());

            Path ipaOutPath = Paths.get(String.valueOf(outDir), ipaFile.getName());
            Files.copy(Paths.get(ipaFile.toString()), ipaOutPath, StandardCopyOption.REPLACE_EXISTING);

            app.setOutDir(outDir);
            app.setAppPath(ipaOutPath.toString());

            FileCustom iconMaxSize = iconList.stream()
                    .max(Comparator.comparing(FileCustom::getSize))
                    .orElse(null);

            Path outPath = Paths.get(outDir.toString(), iconMaxSize.getName());
            IPngConverter pngConverter = new IPngConverter(iconMaxSize.getFile(), outPath.toFile());
            pngConverter.convert();

            if (null != fileInfo) fileInfo.getFile().deleteOnExit();
            for (File f : iconList) {
                f.deleteOnExit();
            }

            app.setIconPath(outPath.toString());
            return app;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public Path createManifestFile(AppInfo ipaApp, Path outDir, String hostUrl) {
        try {
            VelocityEngine velocityEngine = new VelocityEngine();
            velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
            velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
            velocityEngine.init();

            Template t = velocityEngine.getTemplate(TemplateConst.MANIFEST_PLIST_TEMPLATE);

            VelocityContext context = new VelocityContext();
            context.put("ipaUrl", hostUrl + URLConst.APP_INFO.API.GET_APP + "?id=" + ipaApp.getId());
            context.put("iconPath", hostUrl + URLConst.APP_INFO.API.GET_ICON + "?id=" + ipaApp.getId());
            context.put("packageName", ipaApp.getPackageName());
            context.put("versionName", ipaApp.getVersionName());
            context.put("appName", ipaApp.getAppName());

            StringWriter writer = new StringWriter();
            t.merge(context, writer);

            Path outPath = Paths.get(outDir.toString(), "manifest.plist");
            FileWriter fw = new FileWriter(outPath.toString());
            fw.write(writer.toString());
            fw.close();

            return outPath;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static FileCustom extractFile(Path outDir, ZipInputStream zis, ZipEntry zipEntry) throws IOException {
        byte[] buffer = new byte[1024];
        FileCustom newFile = newFile(outDir.toFile(), zipEntry);
        if (zipEntry.isDirectory()) {
            if (!newFile.isDirectory() && !newFile.mkdirs()) {
                throw new IOException("Failed to create directory " + newFile);
            }
        } else {
            // fix for Windows-created archives
            File parent = newFile.getParentFile();
            if (!parent.isDirectory() && !parent.mkdirs()) {
                throw new IOException("Failed to create directory " + parent);
            }

            // write file content
            FileOutputStream fos = new FileOutputStream(newFile);
            int len;
            while ((len = zis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
            fos.close();
        }
        return newFile;
    }

    public static FileCustom newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        FileCustom destFile = new FileCustom(destinationDir, zipEntry.getName());

        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }

        return destFile;
    }


    private String getAppDirectoryName(ZipFile zipFile) {
        String appDirectoryName = "";
        try {
            Pattern regexp = Pattern.compile("^Payload/([\\w\\. -]+\\.app)/");
            for (Enumeration<? extends ZipEntry> e = zipFile.entries(); e.hasMoreElements(); ) {
                ZipEntry zipEntry = e.nextElement();
                String name = zipEntry.getName();
                Matcher matcher = regexp.matcher(name);
                if (matcher.find()) {
                    appDirectoryName = matcher.group(1);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appDirectoryName;
    }


}
