import React from 'react';
import {useFetchRedditList} from './redux/hooks';
import './RedditListPage.less';
import {
    createOAuthHeaders,
    GoogleAuth,
    GoogleAuthConsumer,
    GoogleButton,
    logOutOAuthUser,
    getAccessToken
} from "react-google-oauth2";
import {proxy} from "../../../package.json";

export default function RedditListPage() {
    const {
        redditList,
        fetchRedditList,
        fetchRedditListPending,
        fetchRedditListError,
    } = useFetchRedditList();

    const clientId = process.env.REACT_APP_GOOGLE_CLIENT_ID;
    const apiUri = proxy + "/api/google-oauth";
    const redirectUri = "https://store.zero9vn.com/manager/sign-in-sign-up";
    const options = {
        clientId: (clientId),
        //redirectUri: apiUri,
        redirectUri: redirectUri,
        //response_type: ["id_token", "permission ", "token", "code"],
        include_granted_scopes: true,
        response_type: "code",
        //state: "state_parameter",
        //ux_mode: "popup",
        fetch_basic_profile: true,
        scopes: ["profile", "email", "openid"],
        includeGrantedScopes: true,
        displayErrors: true,
        accessType: "offline",
    };
    const removeToken = () => {
        window.sessionStorage.removeItem("accessToken");
        localStorage.removeItem("accessToken");
    }
    return (
        <div className="login-wrap">
            <div className="login-html">
                <input id="tab-1" type="radio" name="tab" className="sign-in" checked/>
                <label htmlFor="tab-1" className="tab">Sign In</label>
                <input id="tab-2" type="radio" name="tab" className="sign-up"/>
                <label htmlFor="tab-2" className="tab">Sign Up</label>
                <div className="login-form">
                    <div className="sign-in-htm">
                        <section ng-app="login" ng-controller="loginCtrl" ng-class="{shake: ui.shake }">
                            <div ng-if="alert" className="info"
                                 ng-class="{error : alert.type == 'error', good: alert.type == 'good'}">
                                <p>alert.message</p></div>
                            <div className="group">
                                <label htmlFor="login" className="label">Username</label>
                                <input type="text" className="input" ng-model="login"/>
                            </div>
                            <div className="group">
                                <label htmlFor="pwd" className="label">Password</label>
                                <input type="password" ng-model="pwd" className="input" data-type="password"/>
                            </div>
                            <div className="group">
                                <input type="submit" className="button" value="Sign In"
                                       ng-click="loginaction()"/>
                            </div>
                        </section>
                        <div className="hr"></div>
                        <div className="foot-lnk">
                            <a href="#forgot">Forgot Password?</a>
                        </div>
                    </div>
                    <div className="sign-up-htm">
                        <div className="group">
                            <label htmlFor="user" className="label">Username</label>
                            <input id="user" type="text" className="input"/>
                        </div>
                        <div className="group">
                            <label htmlFor="pass" className="label">Password</label>
                            <input id="pass" type="password" className="input" data-type="password"/>
                        </div>
                        <div className="group">
                            <label htmlFor="pass" className="label">Repeat Password</label>
                            <input id="pass" type="password" className="input" data-type="password"/>
                        </div>
                        <div className="group">
                            <label htmlFor="pass" className="label">Email Address</label>
                            <input id="pass" type="text" className="input"/>
                        </div>
                        <div className="group">
                            <input type="submit" className="button" value="Sign Up"/>
                            <div>
                                <h5>skdjksaj</h5>
                                <GoogleAuth>
                                    <GoogleAuthConsumer>
                                        {({responseState, isAuthenticated}) => {
                                            console.log(responseState)
                                            console.log(isAuthenticated)
                                            console.log(getAccessToken())
                                            if (!isAuthenticated) {
                                                return <GoogleButton
                                                    placeholder="demo/search.png" // Optional
                                                    options={options}
                                                    apiUrl={apiUri}
                                                    defaultStyle={true} // Optional
                                                    displayErrors={true}>Sign in with google</GoogleButton>;
                                            } else {
                                                if (responseState.accessToken) { // You can also use isLoggedIn()
                                                    // Now send a request to your server using  createOAuthHeaders() function
                                                    console.log(responseState.accessToken)
                                                    console.log(createOAuthHeaders())
                                                    fetch(apiUri, {
                                                        headers: createOAuthHeaders(),
                                                    })
                                                        .then(data => console.log("Horay We're logged in!"))
                                                        .catch(err => console.error("Just because you have a gmail account doesn't mean you have access!"))
                                                }

                                            }
                                        }}
                                    </GoogleAuthConsumer>
                                </GoogleAuth>
                                <label onClick={() => removeToken()}>Sign out</label>
                            </div>
                        </div>
                        <div className="hr"></div>
                        <div className="foot-lnk">
                            <label htmlFor="tab-1">Already Member?</label>
                        </div>
                    </div>
                </div>
            </div>
        </div>


    );
}
