package com.vinhnq.controller.api;

import com.vinhnq.beans.AppInfoBean;
import com.vinhnq.beans.ResponseAPI;
import com.vinhnq.beans.Version;
import com.vinhnq.common.CommonConst;
import com.vinhnq.common.URLConst;
import com.vinhnq.controller.BaseController;
import com.vinhnq.service.AppService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AppControllerAPI extends BaseController {
    private static final Logger logger = LogManager.getLogger(AppControllerAPI.class);

    private final AppService appService;

    @Autowired
    public AppControllerAPI(AppService appService) {
        this.appService = appService;
    }

    @RequestMapping(value = {URLConst.APP_INFO.API.CHECK_APP_VERSION}, method = RequestMethod.POST)
    //@ApiOperation(value = URLConst.APP_INFO.API.GET_APP_INFO, authorizations = {@Authorization(value = "jwtToken")})
    @ResponseBody
    public ResponseAPI<AppInfoBean> checkApp(HttpServletRequest request, ModelMap modelMap,
                                             @PathVariable(name = "packageName") String packageName,
                                             @PathVariable(name = "versionCode") String versionCode) {
        try {
            AppInfoBean appInfo = this.appService.getLatestAppInfo(packageName, true);
            Version latestVersion = new Version(String.valueOf(appInfo.getVersionCode()));
            Version requestVersion = new Version(versionCode);
            int compare = requestVersion.compareTo(latestVersion);
            String status;
            if(compare >= 0){
                status = CommonConst.COMMON_STRING.LATEST;
            } else {
                status = CommonConst.COMMON_STRING.OLD;
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("statusVersion", status);
            jsonObject.put("latestVersion", latestVersion.get());
            jsonObject.put("yourVersion", requestVersion.get());
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
                                                   @PathVariable(name = "versionCode", required = false) String versionCode) {
        try {
            AppInfoBean appInfo = null;
            if (null == versionCode || CommonConst.COMMON_STRING.LATEST.equals(versionCode)) {
                appInfo = this.appService.getLatestAppInfo(packageName, true);
            } else {
                appInfo = this.appService.getAppInfo(packageName, versionCode, true);
            }

            return new ResponseAPI(HttpStatus.OK.value(), CommonConst.COMMON_STRING.SUCCESS,appInfo);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResponseAPI(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    @RequestMapping(value = {URLConst.APP_INFO.API.GET_LIST}, method = RequestMethod.POST)
    @ApiOperation(value = URLConst.APP_INFO.API.GET_LIST, authorizations = {@Authorization(value = "jwtToken")})
    @ResponseBody
    public ResponseAPI<List<AppInfoBean>> getListAppInfoBean(HttpServletRequest request,
                                                             ModelMap modelMap) {
        List<AppInfoBean> list = new ArrayList<>();
        try {
            list = this.appService.getListAppInfo(CommonConst.DELETE_FLG.NON_DELETE, true);
            return new ResponseAPI(HttpStatus.OK.value(), CommonConst.COMMON_STRING.SUCCESS,list);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResponseAPI(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), list);
        }
    }
}
