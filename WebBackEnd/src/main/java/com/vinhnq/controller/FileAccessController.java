package com.vinhnq.controller;

import com.vinhnq.beans.AppInfoBean;
import com.vinhnq.beans.FileSize;
import com.vinhnq.beans.ResponseAPI;
import com.vinhnq.common.CommonConst;
import com.vinhnq.common.FileUtils;
import com.vinhnq.common.NetUtils;
import com.vinhnq.common.URLConst;
import com.vinhnq.service.ReadFileInformationService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Controller
public class FileAccessController {
    private static final Logger logger = LogManager.getLogger(FileAccessController.class);

    private final ReadFileInformationService readFileInformationService;

    public FileAccessController(ReadFileInformationService readFileInformationService) {
        this.readFileInformationService = readFileInformationService;
    }
   /* @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public String submit(@RequestParam("file") MultipartFile file, ModelMap modelMap) {
        modelMap.addAttribute("file", file);
        return "fileUploadView";
    }*/

    @RequestMapping(value = {URLConst.FILE_ACCESS.CONTROLLER.UPLOAD, URLConst.FILE_ACCESS.API.UPLOAD}, method = RequestMethod.POST)
    @ApiOperation(value = URLConst.FILE_ACCESS.API.UPLOAD, authorizations = {@Authorization(value = "jwtToken")})
    @ResponseBody
    public ResponseAPI<List<AppInfoBean>> uploadFile(@RequestParam(name = "file", required = false) MultipartFile file,
                                                     @RequestParam(name = "files", required = false) MultipartFile[] files,
                                                     @RequestParam(name = "update_content", required = false) String updateContent,
                                                     HttpServletRequest request,
                                                     ModelMap modelMap) {
        List<AppInfoBean> list = new ArrayList<>();
        try {
            if(null != file){
                String fileName = file.getOriginalFilename();
                File f = new File(Paths.get(CommonConst.COMMON_FILE.HOME_TMP, fileName).toString());
                saveFile(file, f);
                FileSize size = FileUtils.convertFileSize(file.getSize());
                AppInfoBean information = readFileInformationService.read(f, size, NetUtils.getHttpsURL(request), updateContent).encrypt();
                list.add(information);
            }
            if(null != files){
                for (MultipartFile multipartFile : files) {
                    String fileName = multipartFile.getOriginalFilename();
                    File f = new File(Paths.get(CommonConst.COMMON_FILE.HOME_TMP, fileName).toString());
                    saveFile(multipartFile, f);
                    FileSize size = FileUtils.convertFileSize(file.getSize());
                    AppInfoBean information = readFileInformationService.read(f, size, NetUtils.getHttpsURL(request), updateContent).encrypt();
                    list.add(information);
                }
            }
            return new ResponseAPI(HttpStatus.OK.value(), list);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResponseAPI(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
        //return new ResponseAPI(HttpStatus.INTERNAL_SERVER_ERROR.value(), "");
    }

    private void saveFile(MultipartFile multipartFile, File file){
        try {
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file));
            stream.write(multipartFile.getBytes());
            stream.close();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
