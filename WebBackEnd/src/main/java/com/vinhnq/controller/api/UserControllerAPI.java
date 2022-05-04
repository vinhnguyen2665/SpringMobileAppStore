package com.vinhnq.controller.api;

import com.vinhnq.entity.User;
import com.vinhnq.beans.ExceptionResponse;
import com.vinhnq.beans.ResponseAPI;
import com.vinhnq.common.CommonConst;
import com.vinhnq.config.security.SecurityConfig;
import com.vinhnq.controller.BaseController;
import com.vinhnq.service.MailService;
import com.vinhnq.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@Controller
public class UserControllerAPI extends BaseController {
	@Autowired
	UserService userService;

	@Autowired
	LoginControllerAPI loginControllerAPI;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	SecurityConfig securityConfig;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	MailService mailService;

	private static final Logger logger = LogManager.getLogger(UserControllerAPI.class);

	@RequestMapping(value = {"/api/user/register" }, method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public @ResponseBody ResponseAPI<Object> registerPostAjax(@RequestBody User data) {
		ResponseAPI<Object> result = new ResponseAPI<Object>();
		try {
			if (null == data || (null != data && data.getUserName() == null && data.getPassword() == null)) {
				throw new RuntimeException(messageSource.getMessage(CommonConst.COMMON_MESSAGE.DATA_NOT_VALID, null, null));
			}
			if (data.getUserName() == null) {
				throw new RuntimeException(messageSource.getMessage(CommonConst.COMMON_MESSAGE.USERMAME_IS_REQUIRED, null, null));
			}
			if (data.getPassword() == null) {
				throw new RuntimeException(messageSource.getMessage(CommonConst.COMMON_MESSAGE.PASSWORD_IS_REQUIRED, null, null));
			}

			result.setData(userService.register(data));
			result.setStatus(CommonConst.COMMON_RESPONSE.OK);

		} catch (Exception e) {
			result.setStatus(CommonConst.COMMON_RESPONSE.EXCEPTION);
			result.setData(new ExceptionResponse(e.getMessage()));
			logger.error(e.getMessage(), e);
		}
		return result;
	}

	@RequestMapping(value = { "/api/user", "/api/users/current"}, method = RequestMethod.POST)
	@ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
	public @ResponseBody ResponseAPI<Object> getUserLogin() {
		ResponseAPI<Object> result = new ResponseAPI<Object>();
		try {
			Authentication authen = SecurityContextHolder.getContext().getAuthentication();
			if (null != authen && !authen.getPrincipal().equals("anonymousUser")) {
				org.springframework.security.core.userdetails.User authenUser = (org.springframework.security.core.userdetails.User) authen
						.getPrincipal();
				result.setStatus(CommonConst.COMMON_RESPONSE.OK);
				result.setData(userService.getUserInfoByName(authenUser.getUsername()));
			} else {
				result.setStatus(CommonConst.COMMON_RESPONSE.NON_AUTH);
				result.setData(messageSource.getMessage(CommonConst.COMMON_MESSAGE.USER_NOT_LOGIN, null, null));
			}
		} catch (Exception e) {
			result.setStatus(CommonConst.COMMON_RESPONSE.EXCEPTION);
			result.setData(new ExceptionResponse(e.getMessage()));
			logger.error(e.getMessage(), e);
		}
		return result;
	}
}
