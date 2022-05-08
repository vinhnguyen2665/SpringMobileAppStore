import axios from 'axios';
import {useCallback} from 'react';
import {useDispatch, useSelector, shallowEqual} from 'react-redux';
import {
    TYPES,
    URL
} from './constants';
import {toast} from "react-toastify";

export function useAppList() {

    const dispatch = useDispatch();

/*    dispatch({
        type: TYPES.APP_LIST_BEGIN,
    });*/

    const {isLoading, appList} = useSelector(
        state => ({
            isLoading: state.app_screen.isLoading,
            appList: state.app_screen.appList,
        }),
        shallowEqual,
    );
    const getAppListAction = useCallback(() => {
            dispatch(getAppList());
        },
        [dispatch],
    );
    const getAppListConditionAction = useCallback((props) => {
            dispatch(getAppListCondition(props));
        },
        [dispatch],
    );

    return {
        isLoading: isLoading,
        appList: appList,
        appListApi: getAppListAction,
        appListConditionApi: getAppListConditionAction,
    };
}

export function getAppList() {
    return dispatch => {
        const promise = new Promise((resolve, reject) => {
            const doRequest = axios.post(URL.APP_LIST_FOR_HOME);
            doRequest.then(
                res => {
                    const type = TYPES.APP_LIST_SUCCESS;
                    const data = res.data;
                    if (data.status == 200) {
                        dispatch({
                            type: type,
                            data: res.data.data,
                        });
                        toast.success(data.message, {
                            position: "top-right",
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
                    const type = TYPES.APP_LIST_FAILURE;
                    const data = {error: err};
                    dispatch({
                        type: type,
                        data: {error: err},
                    });
                    reject(err);
                    return {type: type, data: data};
                },
            );
        });
    };
}

export function getAppListCondition(props) {
    return dispatch => {
        const promise = new Promise((resolve, reject) => {
            const data = props;
            const config = {"headers" : {
                'Content-Type': 'application/json',
            }};
            const doRequest = axios.post(URL.GET_LIST_CONDITION, data, config);
            doRequest.then(
                res => {
                    const type = TYPES.APP_LIST_SUCCESS;
                    const data = res.data;
                    if (data.status == 200) {
                        dispatch({
                            type: type,
                            data: res.data.data,
                        });
                        toast.success(data.message, {
                            position: "top-right",
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
                    const type = TYPES.APP_LIST_FAILURE;
                    const data = {error: err};
                    dispatch({
                        type: type,
                        data: {error: err},
                    });
                    reject(err);
                    return {type: type, data: data};
                },
            );
        });
    };
}

export function reducer(state, action) {
    switch (action.type) {
        case TYPES.APP_LIST_BEGIN:
            return {
                ...state,
                isLoading: true,
                appList: [],
            };
        case TYPES.APP_LIST_SUCCESS:
            return {
                ...state,
                isLoading: false,
                appList: action.data,
            };

        case TYPES.APP_LIST_FAILURE:
            // The request is failed
            return {
                ...state,
                isLoading: false,
                appList: [],
            };
        default:
            return state;
    }
}

