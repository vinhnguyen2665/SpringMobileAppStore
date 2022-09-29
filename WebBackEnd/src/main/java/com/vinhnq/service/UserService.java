package com.vinhnq.service;

import com.vinhnq.beans.googleAuthentication.GoogleOauthResponse;
import com.vinhnq.entity.User;
import com.vinhnq.beans.LoginResponse;
import com.vinhnq.beans.UserBeans;

import java.util.List;
import java.util.Map;

public interface UserService {

    /**
     *
     * @param username
     * @param password
     * @return
     */
    User userAuthenticated(String username, String password);

    /**
     *
     * @param username
     * @return
     */
    User getUserInfoByName(String username);

    List<User> getAllUser();

    List<User> getAllUserByHibernate();

    /**
     *
     * @param username
     * @return
     */
    boolean isUserNameExist(String username);

    /**
     *
     * @param user
     * @return
     */
    User forgotPassword(User user);

    /**
     *
     * @param user
     * @return
     */
    User register(User user);

    /**
     *
     * @param username
     * @return
     */
    LoginResponse authenticateUserActive(String username);

    /**
     *
     * @param username
     * @param password
     * @return
     */
    LoginResponse authenticateUserHandler(String username, String password);

    LoginResponse googleOAuth2(GoogleOauthResponse oauthResponse);

    Map<Long, UserBeans> getUsersBeansMap();
}
