package com.vinhnq.service.impl;

import com.vinhnq.beans.googleAuthentication.GoogleOauthResponse;
import com.vinhnq.beans.googleAuthentication.GoogleOauthToken;
import com.vinhnq.beans.googleAuthentication.GoogleTokenInfo;
import com.vinhnq.beans.googleAuthentication.GoogleUserInfo;
import com.vinhnq.common.googleoauth2.GoogleOAuth2Utils;
import com.vinhnq.entity.User;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vinhnq.beans.LoginResponse;
import com.vinhnq.beans.MailContentBeans;
import com.vinhnq.beans.UserBeans;
import com.vinhnq.common.CommonCode;
import com.vinhnq.common.CommonConst;
import com.vinhnq.common.EntityUtils;
import com.vinhnq.config.security.JwtTokenProvider;
import com.vinhnq.config.security.SecurityConfig;
import com.vinhnq.dao.UserDAO;
import com.vinhnq.repository.UserRepository;
import com.vinhnq.service.MailService;
import com.vinhnq.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Service
//@Repository
//@Transactional(rollbackOn = Exception.class)
//@EnableTransactionManagement(proxyTargetClass = true)
public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    private final SecurityConfig securityConfig;

    private final MailService mailService;

    private final UserDAO userDAO;

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider tokenProvider;

    private final Environment environment;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, SecurityConfig securityConfig, MailService mailService, UserDAO userDAO, AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider, Environment environment) {
        this.userRepository = userRepository;
        this.securityConfig = securityConfig;
        this.mailService = mailService;
        this.userDAO = userDAO;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.environment = environment;
    }

    @Override
    public User getUserInfoByName(String username) {
        try {
            User result = userRepository.findByUserName(username);
            if (result == null || (null != result && null == result.getUserName())) {
                //throw new RuntimeException("username not exist!");
                return null;
            } else {
                return result;
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new RuntimeException(ex);
        }
    }

    @Override

    public List<User> getAllUserByHibernate() {
        try {
            List<User> result = userDAO.getAllUsers();
            if (result == null) {
                //throw new RuntimeException("username not exist!");
                return null;
            } else {
                return result;
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<User> getAllUser() {
        try {
            List<User> result = userRepository.findAllByDeleteFlg(CommonConst.COMMON_STRING.NUMBER_0);
            if (result == null) {
                //throw new RuntimeException("username not exist!");
                return null;
            } else {
                return result;
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new RuntimeException(ex);
        }
    }


    @Override
    public User register(User user) {
        try {
            if (!isUserNameExist(user.getUserName())) {
                User u = new User();
                u.setUserName(user.getUserName());
                u.setPassword(securityConfig.passwordEncoder().encode(user.getPassword()));
                u.setEmail(user.getEmail());
                u.setDeleteFlg(CommonConst.DELETE_FLG.NON_DELETE);
                u.setFirstName(user.getFirstName());
                u.setLastName(user.getLastName());
                userRepository.save(u);
                return u;
            } else {
                throw new RuntimeException(CommonConst.COMMON_MESSAGE.USER_EXIST);
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new RuntimeException(ex);
        }
    }

    @Override
    public User userAuthenticated(String username, String password) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isUserNameExist(String username) {
        try {
            User result = userRepository.findByUserName(username);
            if (result == null || (null != result && null == result.getUserName())) {
                return false;
            } else {
                return true;
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new RuntimeException(ex);
        }
    }

    @Override
    public User forgotPassword(User user) {
        try {
            User data = getUserInfoByName(user.getUserName());
            if (data != null) {
                if (null != user.getEmail() && null != user.getUserName() && data.getEmail() != null && data.getEmail().equalsIgnoreCase(user.getEmail())) {
                    String generatedString = CommonCode.generatePassword();//RandomStringUtils.randomAlphanumeric(10).toUpperCase();
                    data.setPassword(securityConfig.passwordEncoder().encode(generatedString));
                    userRepository.save(data);
                    mailService.alertForgotPassword(data, new MailContentBeans());
                    data.setPassword(null);
                } else {
                    throw new RuntimeException(CommonConst.COMMON_MESSAGE.DATA_NOT_VALID);
                }
            } else {
                throw new RuntimeException(CommonConst.COMMON_MESSAGE.USER_EXIST);
            }
            return data;
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }


    @Override
    public LoginResponse authenticateUserHandler(String username, String password) {
        LoginResponse result = new LoginResponse(null, null);
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

            // Nếu không xảy ra exception tức là thông tin hợp lệ
            // Set thông tin authentication vào Security Context
            SecurityContextHolder.getContext().setAuthentication(authentication);
            org.springframework.security.core.userdetails.User authenUser = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
            User user = this.getUserInfoByName(authenUser.getUsername());
            // Trả về jwt cho người dùng.
            String jwt = tokenProvider.generateToken(user);
            logger.info("Login on: " + new Date() + " username: " + username);
            result = new LoginResponse(username, jwt);
        } catch (BadCredentialsException e) {
            logger.error(e.getMessage(), e);
            throw new BadCredentialsException(e.getMessage(), e);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new BadCredentialsException(ex.getMessage(), ex);
        }
        return result;
    }

    @Override
    public LoginResponse googleOAuth2(GoogleOauthResponse oauthResponse) {
        try {

            String clientId = environment.getProperty("spring.security.oauth2.client.registration.google.client-id", "");
            String clientSecret = environment.getProperty("spring.security.oauth2.client.registration.google.client-secret", "");
            String redirectUri = environment.getProperty("system.redirect_uri", "");
            // GoogleOauthToken oauthToken = GoogleOAuth2Utils.getToken(oauthResponse,clientId, clientSecret, redirectUri);
            GoogleOAuth2Utils accessToken = new GoogleOAuth2Utils(GoogleOAuth2Utils.BASE_URL_TOKEN_API);
            Response<GoogleOauthToken> googleOauthToken = accessToken.getRetrofitAPI().oauthTokenApi(clientSecret,
                    clientId,
                    oauthResponse.getCode(),
                    "authorization_code",
                    redirectUri).execute();
            GoogleOauthToken oauthToken = googleOauthToken.body();
            GoogleUserInfo googleUserInfo = getGoogleUserInfo(oauthToken);
            //GoogleTokenInfo googleTokenInfo = getGoogleTokenInfo(oauthToken);
            User user = new User();
            user.setUserName(googleUserInfo.getEmail().replaceAll(CommonConst.REGEX.EMAIL_DOMAIN.toString(), ""));
            user.setPassword(user.getUserName());
            user.setFirstName(googleUserInfo.getFamily_name());
            user.setLastName(googleUserInfo.getGiven_name());
            user.setAuthority(CommonConst.COMMON_ROLE.U);
            user.setCreateById(0);
            user.setCreateDate(new Timestamp(new Date().getTime()));
            user = this.register(user);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        /*accessToken.getRetrofitAPI().oauthTokenApi(clientSecret,
                clientId,
                oauthResponse.getCode(),
                "authorization_code",
                redirectUri).enqueue(new Callback<GoogleOauthToken>() {
            public void onResponse(Call<GoogleOauthToken> call, Response<GoogleOauthToken> response) {
                if (response.isSuccessful()) {
                    System.out.println(response);
                    GoogleOauthToken oauthToken = response.body();
                    getGoogleUserInfo(oauthToken);
                    getGoogleTokenInfo(oauthToken);
                } else {
                    System.out.println(response.errorBody());
                }
            }

            public void onFailure(Call<GoogleOauthToken> call, Throwable t) {
                t.printStackTrace();
            }
        });*/

        return null;
    }

    private GoogleUserInfo getGoogleUserInfo(GoogleOauthToken oauthToken) {
        try {
            GoogleOAuth2Utils userInfo = new GoogleOAuth2Utils(GoogleOAuth2Utils.BASE_URL_USER_INFO_API);
        /*userInfo.getRetrofitAPI().userInfoApi(oauthToken.getAccessToken()).enqueue(new Callback<GoogleUserInfo>() {
            public void onResponse(Call<GoogleUserInfo> call, Response<GoogleUserInfo> response) {
                if (response.isSuccessful()) {
                    GoogleUserInfo googleUserInfo = response.body();
                    System.err.println(googleUserInfo);
                } else {
                    System.out.println(response.errorBody());
                }
            }

            public void onFailure(Call<GoogleUserInfo> call, Throwable t) {
                t.printStackTrace();
            }
        });*/

            Response<GoogleUserInfo> googleUserInfo = userInfo.getRetrofitAPI().userInfoApi(oauthToken.getAccessToken()).execute();
            return googleUserInfo.body();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    private GoogleTokenInfo getGoogleTokenInfo(GoogleOauthToken oauthToken) {
        try {
            GoogleOAuth2Utils userInfo = new GoogleOAuth2Utils(GoogleOAuth2Utils.BASE_URL_TOKEN_INFO_API);
            Response<GoogleTokenInfo> googleUserInfo = userInfo.getRetrofitAPI().tokenInfoApi(oauthToken.getIdToken()).execute();
            return googleUserInfo.body();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return null;
        /*userInfo.getRetrofitAPI().tokenInfoApi(oauthToken.getIdToken()).enqueue(new Callback<GoogleTokenInfo>() {
            public void onResponse(Call<GoogleTokenInfo> call, Response<GoogleTokenInfo> response) {
                if (response.isSuccessful()) {
                    GoogleTokenInfo tokenInfo = response.body();
                    System.err.println(tokenInfo);
                } else {
                    System.out.println(response.errorBody());
                }
            }

            public void onFailure(Call<GoogleTokenInfo> call, Throwable t) {
                t.printStackTrace();
            }
        });*/
    }

    @Override
    public Map<Long, UserBeans> getUsersBeansMap() {
        Map<Long, UserBeans> result = new HashMap<>();
        try {
            List<User> lstUsers = this.getAllUser();
            if (null != lstUsers) {
                for (User user : lstUsers) {
                    UserBeans userBeans = EntityUtils.convertUserToUserBeans(user);
                    userBeans.setPassword(null);
                    result.put(userBeans.getId(), userBeans);
                }
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public LoginResponse authenticateUserActive(String username) {
        LoginResponse result = new LoginResponse(null, null);
        try {
            User user = this.getUserInfoByName(username);
            // Trả về jwt cho người dùng.
            String jwt = tokenProvider.generateToken(user);
            logger.info("Login on: " + new Date() + " username: " + username);
            result = new LoginResponse(username, jwt);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }
}
