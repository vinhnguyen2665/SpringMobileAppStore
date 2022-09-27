package com.vinhnq.controller.api;

import com.vinhnq.beans.AppInfoBean;
import com.vinhnq.beans.ResponseAPI;
import com.vinhnq.beans.Version;
import com.vinhnq.common.CommonConst;
import com.vinhnq.common.NetUtils;
import com.vinhnq.common.URLConst;
import com.vinhnq.controller.BaseController;
import com.vinhnq.service.AppService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class AppControllerAPI extends BaseController {
    private static final Logger logger = LogManager.getLogger(AppControllerAPI.class);

    private final AppService appService;
    private final Environment env;

    @Autowired
    public AppControllerAPI(AppService appService, Environment env) {
        this.appService = appService;
        this.env = env;
    }

    @RequestMapping(value = {URLConst.APP_INFO.API.VERIFY_APP_VERSION}, method = RequestMethod.GET)
    //@ApiOperation(value = URLConst.APP_INFO.API.GET_APP_INFO, authorizations = {@Authorization(value = "jwtToken")})
    @ResponseBody
    public ResponseAPI<AppInfoBean> checkApp(HttpServletRequest request, ModelMap modelMap,
                                             @PathVariable(name = "packageName") String packageName,
                                             @PathVariable(name = "type") String type,
                                             @PathVariable(name = "versionCode") String versionCode) {
        try {
            AppInfoBean appInfo = this.appService.getLatestAppInfo(packageName, type,true);
            Version latestVersion = new Version(String.valueOf(appInfo.getVersionCode()));
            Version requestVersion = new Version(versionCode);
            int compare = requestVersion.compareTo(latestVersion);
            String status;
            if(compare >= 0){
                status = CommonConst.COMMON_STRING.LATEST;
            } else {
                status = CommonConst.COMMON_STRING.OLD;
            }

            String frontEndUrl = env.getProperty("system.front-end-url", "");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("statusVersion", status);
            jsonObject.put("type", type);
            jsonObject.put("latestVersion", latestVersion.get());
            jsonObject.put("yourVersion", requestVersion.get()); //http://192.168.100.123:3100/app/apk/com.meishi/
            jsonObject.put("latestUrl", frontEndUrl + "/app" + "/" + appInfo.getAppType() + "/" + appInfo.getPackageName() + "/" + appInfo.getVersionName() + "?id=" + appInfo.getId());
            jsonObject.put("downloadUrl", NetUtils.getURL(request) + "/api/app/get-app?id=" + appInfo.getId());
            return new ResponseAPI(HttpStatus.OK.value(), CommonConst.COMMON_STRING.SUCCESS, jsonObject);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResponseAPI(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    @RequestMapping(value = {URLConst.APP_INFO.API.GET_APP_INFO, URLConst.APP_INFO.API.GET_APP_INFO_LATEST}, method = RequestMethod.POST)
    //@ApiOperation(value = URLConst.APP_INFO.API.GET_APP_INFO, authorizations = {@Authorization(value = "jwtToken")})
    @ResponseBody
    public ResponseAPI<AppInfoBean> getAppInfoBean(HttpServletRequest request, ModelMap modelMap,
                                                   @PathVariable(name = "packageName") String packageName,
                                                   @PathVariable(name = "type") String type,
                                                   @PathVariable(name = "versionCode", required = false) String versionCode) {
        try {
            AppInfoBean appInfo = null;
            if (null == versionCode || CommonConst.COMMON_STRING.LATEST.equals(versionCode)) {
                appInfo = this.appService.getLatestAppInfo(packageName, type,true);
            } else {
                appInfo = this.appService.getAppInfo(packageName, versionCode, type,true);
            }
            ResponseAPI<AppInfoBean> re = new ResponseAPI(HttpStatus.OK.value(), CommonConst.COMMON_STRING.SUCCESS,appInfo);
            return re;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResponseAPI(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    @RequestMapping(value = {URLConst.APP_INFO.API.GET_LIST}, method = RequestMethod.POST)
   // @ApiOperation(value = URLConst.APP_INFO.API.GET_LIST, authorizations = {@Authorization(value = "jwtToken")})
    @ResponseBody
    public ResponseAPI<List<AppInfoBean>> getListAppInfoBean(HttpServletRequest request,
                                                             ModelMap modelMap) {
        List<AppInfoBean> list = new ArrayList<>();
        try {
            list = this.appService.getAllListAppInfo(CommonConst.DELETE_FLG.NON_DELETE, true);
            ResponseAPI<List<AppInfoBean>> re = new ResponseAPI(HttpStatus.OK.value(), CommonConst.COMMON_STRING.SUCCESS,list);
            re.setRecordsTotal(list.size());
            return re;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResponseAPI(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), list);
        }
    }

    @RequestMapping(value = {URLConst.APP_INFO.API.GET_LIST_FOR_HOME}, method = RequestMethod.POST)
    //@ApiOperation(value = URLConst.APP_INFO.API.GET_LIST_FOR_HOME, authorizations = {@Authorization(value = "jwtToken")})
    @ResponseBody
    public ResponseAPI<List<AppInfoBean>> getListAppInfoBeanForHome(HttpServletRequest request,
                                                             ModelMap modelMap) {
        List<AppInfoBean> list = new ArrayList<>();
        try {
            list = this.appService.getListAppInfoForHome(true);
            ResponseAPI<List<AppInfoBean>> re = new ResponseAPI(HttpStatus.OK.value(), CommonConst.COMMON_STRING.SUCCESS,list);
            re.setRecordsTotal(list.size());
            return re;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResponseAPI(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), list);
        }
    }

    @RequestMapping(value = {URLConst.APP_INFO.API.GET_APP_CONDITION}, method = RequestMethod.POST, produces = "application/json; charset=UTF-8" )
   // @ApiOperation(value = URLConst.APP_INFO.API.GET_APP_CONDITION, authorizations = {@Authorization(value = "jwtToken")})
    @ResponseBody
    public ResponseAPI<List<AppInfoBean>> getListAppInfoBeanCondition(@RequestBody AppInfoBean appInfo) {
        List<AppInfoBean> list = new ArrayList<>();
        try {
            list = this.appService.getListAppCondition(appInfo, true);
            ResponseAPI<List<AppInfoBean>> re = new ResponseAPI(HttpStatus.OK.value(), CommonConst.COMMON_STRING.SUCCESS,list);
            re.setRecordsTotal(list.size());
            return re;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResponseAPI(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), list);
        }
    }


    @RequestMapping(value = {URLConst.APP_INFO.API.GET_ICON}, method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<InputStreamResource> getLabel(HttpServletRequest request, Model model,
                                                        @RequestParam long id) {
        try {
/*       File file = new File("E:\\0_PROJECT\\27_Morisada\\04_SourceCode\\branches\\web\\WebServer\\src\\main\\webapp\\resources\\img\\dailogbox_warning_icon.png");
         Path path = Paths.get(file.getAbsolutePath());*/
            Optional<AppInfoBean> appInfo = this.appService.getAppInfo(id, false);
            File file = null;
            if(appInfo.isPresent()){
                file = new File(appInfo.get().getIconPath());
                InputStream inputStream = new FileInputStream(file);
                HttpHeaders headers = new HttpHeaders();
                headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
                headers.add("Pragma", "no-cache");
                headers.add("Expires", "0");
                return ResponseEntity.ok()
                        .contentLength(file.length())
                        .contentType(MediaType.parseMediaType(MediaType.IMAGE_JPEG_VALUE))
                        .body(new InputStreamResource(inputStream));
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }

    @RequestMapping(value = {URLConst.APP_INFO.API.GET_APP }, method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<InputStreamResource> getApp(HttpServletRequest request, Model model,
                                                        @RequestParam long id) {
        try {
            Optional<AppInfoBean> appInfo = this.appService.getAppInfo(id, false);
            File file = null;
            if(appInfo.isPresent()){
              file = new File(appInfo.get().getAppPath());
                InputStream inputStream = new FileInputStream(file);
                HttpHeaders headers = new HttpHeaders();
                headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
                headers.add("Pragma", "no-cache");
                headers.add("Expires", "0");
                headers.add("Content-Disposition", "attachment; filename=" + URLEncoder.encode(appInfo.get().getAppName() + "." + appInfo.get().getAppType(), "UTF-8"));
                headers.setContentType(appInfo.get().getMediaType());
                return ResponseEntity.ok()
                        .headers(headers)
                        .contentLength(file.length())
                        .contentType(appInfo.get().getMediaType())
                        .body(new InputStreamResource(inputStream));
   /*             InputStreamResource inputStreamResource = new InputStreamResource(inputStream);
                return new ResponseEntity<Resource>(inputStreamResource, null, HttpStatus.OK);*/
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }
    @RequestMapping(value = {URLConst.APP_INFO.API.GET_MANIFEST }, method = RequestMethod.GET, produces = "application/x-plist")
    public ResponseEntity<InputStreamResource> getManifest(HttpServletRequest request,
                                                @RequestParam long id) {
        try {
            Optional<AppInfoBean> appInfo = this.appService.getAppInfo(id, false);
            File file = null;
            if(appInfo.isPresent()){
       /*            HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.valueOf("application/x-plist"));
                file = new File(appInfo.get().getManifestPath());
             InputStream stream = new FileInputStream(file);
                InputStreamResource inputStreamResource = new InputStreamResource(stream);
                return new ResponseEntity<Resource>(inputStreamResource, null, HttpStatus.OK);*/

                file = new File(appInfo.get().getManifestPath());
                InputStream inputStream = new FileInputStream(file);
                HttpHeaders headers = new HttpHeaders();
                headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
                headers.add("Pragma", "no-cache");
                headers.add("Expires", "0");
                headers.add("Content-Disposition", "attachment; filename=manifest.plist");
                headers.setContentType(MediaType.valueOf("application/x-plist"));
                return ResponseEntity.ok()
                        .headers(headers)
                        .contentLength(file.length())
                        .contentType(MediaType.parseMediaType("application/x-plist"))
                        .body(new InputStreamResource(inputStream));
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }
}
