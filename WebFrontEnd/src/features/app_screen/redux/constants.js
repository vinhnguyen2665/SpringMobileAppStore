export const EXAMPLES_COUNTER_PLUS_ONE = 'EXAMPLES_COUNTER_PLUS_ONE';
export const EXAMPLES_COUNTER_MINUS_ONE = 'EXAMPLES_COUNTER_MINUS_ONE';
export const EXAMPLES_COUNTER_RESET = 'EXAMPLES_COUNTER_RESET';
export const EXAMPLES_FETCH_REDDIT_LIST_BEGIN = 'EXAMPLES_FETCH_REDDIT_LIST_BEGIN';
export const EXAMPLES_FETCH_REDDIT_LIST_SUCCESS = 'EXAMPLES_FETCH_REDDIT_LIST_SUCCESS';
export const EXAMPLES_FETCH_REDDIT_LIST_FAILURE = 'EXAMPLES_FETCH_REDDIT_LIST_FAILURE';
export const EXAMPLES_FETCH_REDDIT_LIST_DISMISS_ERROR = 'EXAMPLES_FETCH_REDDIT_LIST_DISMISS_ERROR';
export const SHOW_LOGIN = 'SHOW_LOGIN';

export class MODE {
    static LIST = 'list';
    static DETAILS = 'details';
}
export class TYPES {
    static LOGIN_SUCCESS = 'LOGIN_SUCCESS';
    static LOGIN_FAILURE = 'LOGIN_FAILURE';
    static LOGOUT_SUCCESS = 'LOGOUT_SUCCESS';

    static APP_LIST_BEGIN = 'APP_LIST_BEGIN';
    static APP_LIST_SUCCESS = 'APP_LIST_SUCCESS';
    static APP_LIST_FAILURE = 'LAPP_LIST_FAILURE';
}

export class URL {
    static BASE_URL = '/api';
    //static BASE_URL = 'http://localhost:8088/api/';
    static LOGIN = URL.BASE_URL + '/login';
    static APP_LIST_FOR_HOME = URL.BASE_URL + '/app/list-for-home';
    static GET_LIST_CONDITION = URL.BASE_URL + '/app/get-list-condition';
    static GET_ICON = URL.BASE_URL + '/app/get-icon?id=';
    static GET_APP = URL.BASE_URL + '/app/get-app?id=';
}