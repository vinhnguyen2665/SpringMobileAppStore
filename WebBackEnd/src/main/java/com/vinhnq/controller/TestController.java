package com.vinhnq.controller;

import com.vinhnq.common.URLConst;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;


@Controller
public class TestController extends BaseController {


    @RequestMapping(value = {URLConst.TEST}, method = RequestMethod.GET)
    public String test(HttpServletRequest request, Model model) {
        return "test";
    }
}
