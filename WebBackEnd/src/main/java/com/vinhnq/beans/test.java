package com.vinhnq.beans;


import com.vinhnq.service.ReadFileInformationService;
import com.vinhnq.service.impl.ReadFileInformationServiceImpl;

import java.io.File;

public class test {
    public static void main(String[] args) {
        ReadFileInformationService readFileInformationService = new ReadFileInformationServiceImpl();
        readFileInformationService.readFileAPK(new File("C:\\Users\\vinhn\\Downloads\\材料RFIDシステム（仙台製造所） (1).apk"), new FileSize(15D, "MB"));
    }


}
