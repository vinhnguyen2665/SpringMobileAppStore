import axios from 'axios';
import {useCallback} from 'react';
import {useDispatch, useSelector, shallowEqual} from 'react-redux';
import {
    TYPES,
    URL
} from './constants';
import {toast} from "react-toastify";

export function useUserLogin() {

    const dispatch = useDispatch();
    const token = getToken();
    const {isAuthenticated, loginFailure, accessToken} = useSelector(
        state => ({
            isAuthenticated: token ? true : state.app_screen.isAuthenticated,
            loginFailure: state.app_screen.loginFailure,
            accessToken: token ? token : state.app_screen.accessToken,
        }),
        shallowEqual,
    );
    const loginAction = useCallback(
        (username, password) => {
            dispatch(userLogin(username, password));
        },
        [dispatch],
    );

    const logoutAction = useCallback(
        () => {
            dispatch(userLogout());
        },
        [dispatch],
    );

    return {
        isAuthenticated: isAuthenticated,
        loginFailure: loginFailure,
        accessToken: accessToken,
        userLogin: loginAction,
        userLogout: logoutAction,

    };
}

export const getToken = () => {
    const token = localStorage.getItem("accessToken") || "";
    axios.defaults.headers.common['Authorization'] = token;
    return token;
};

// Rekit uses redux-thunk for async actions by default: https://github.com/gaearon/redux-thunk
// If you prefer redux-saga, you can use rekit-plugin-redux-saga: https://github.com/supnate/rekit-plugin-redux-saga
export function userLogin(username, password) {
    return dispatch => {
        const promise = new Promise((resolve, reject) => {
            // doRequest is a placeholder Promise. You should replace it with your own logic.
            // See the real-word example at:  https://github.com/supnate/rekit/blob/master/src/features/home/redux/fetchRedditReactjsList.js
            // args.error here is only for test coverage purpose.
            //http://test.nsmp-system.com/SensorSystem/api/login
            const doRequest = axios.post(URL.LOGIN, {
                "username": username,
                "password": password
            });
            doRequest.then(
                res => {
                    const type = TYPES.LOGIN_SUCCESS;
                    const data = res.data;
                    if (data.data.accessToken) {
                        dispatch({
                            type: TYPES.LOGIN_SUCCESS,
                            data: res.data.data,
                        });
                        toast.success(res.data.message, {
                            position: "top-right",
                            autoClose: 5000,
                            hideProgressBar: false,
                            closeOnClick: true,
                            pauseOnHover: true,
                            draggable: true,
                            progress: undefined,
                        });
                        resolve(res);
                    } else {
                        dispatch({
                            type: TYPES.LOGIN_FAILURE,
                            data: {error: data.message},
                        });
                        toast.error(res.data.data.message, {
                            position: "top-center",
                            autoClose: 5000,
                            hideProgressBar: false,
                            closeOnClick: true,
                            pauseOnHover: true,
                            draggable: true,
                            progress: undefined,
                        });
                        resolve(res);
                    }
                    return {type: type, data: data};
                },
                // Use rejectHandler as the second argument so that render errors won't be caught.
                err => {
                    const type = TYPES.LOGIN_FAILURE;
                    const data = {error: err};
                    dispatch({
                        type: TYPES.LOGIN_FAILURE,
                        data: {error: err},
                    });
                    reject(err);
                    return {type: type, data: data};
                },
            );
        });
    };
}

export function userLogout() {
    return {type: TYPES.LOGOUT_SUCCESS};
}

export function reducer(state, action) {
    switch (action.type) {
        case TYPES.LOGIN_SUCCESS:
            // The request is success
            const id_token = action.data.accessToken;
            localStorage.setItem("accessToken", id_token);
            return {
                ...state,
                isAuthenticated: true,
                loginFailure: null,
                isShowLogin: false,
                accessToken: id_token
            };

        case TYPES.LOGIN_FAILURE:
            // The request is failed
            return {
                ...state,
                isAuthenticated: false,
                loginFailure: true,
                isShowLogin: true,
                accessToken: ""
            };

        case TYPES.LOGOUT_SUCCESS:
            // Dismiss the request failure error
            localStorage.removeItem("accessToken");
            return {
                ...state,
                isAuthenticated: false,
                loginFailure: false,
                isShowLogin: false,
                accessToken: ""
            };
        default:
            return state;
    }
}

