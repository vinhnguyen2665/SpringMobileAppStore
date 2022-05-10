package com.vinhnq.common;

import java.io.File;

public class URLConst {

    private static final String api = "/api";
    private static final String COMMON = "/common";
    public static final String LOGIN_FAIL = "/login?error=true";

    public static final String lang_support_regex = "/en/|/ja/|/vi/";
    public static final String lang_support = "en|ja|vi";
    private static final String lang = "/{locale:" + lang_support + "}";
    public static final String loginSucess = "/";
    public static final String ROOT = "/";
    public static final String ROOT_LANG = lang + "/";
    public static final String TEST = lang + "/test";

    public static class LOGIN {
        public static final String HOME = "/login";
        public static final String LOGOUT = "/logout";

        public static class CONTROLLER {
            public static final String HOME = lang + LOGIN.HOME;
            public static final String LOGOUT = lang + "/logout";
        }
    }

    public static final String PROFILE = "/profile";

    public static class USERS {
        public static final String ROOT = "/users";

        public static class CONTROLLER {
            public static final String HOME = lang + USERS.ROOT;
            public static final String PROFILE = USERS.ROOT + "/profile";
        }

        public static class API {
            public static final String HOME = api + USERS.ROOT;
        }
    }

    public static class DASHBOARD {
        public static final String ROOT = "/dashboard";

        public static class CONTROLLER {
            public static final String HOME = lang + DASHBOARD.ROOT;
        }

        public static class API {
            public static final String HOME = api + DASHBOARD.ROOT;
        }
    }

    public static class FILE_ACCESS {
        public static final String ROOT = "/file";

        public static class CONTROLLER {
            public static final String ROOT = lang + FILE_ACCESS.ROOT;
            public static final String UPLOAD = ROOT + "/upload-file";
        }

        public static class API {
            public static final String ROOT = api + FILE_ACCESS.ROOT;
            public static final String UPLOAD = ROOT + "/upload-file";
        }
    }

    public static class APP_INFO {
        public static final String ROOT = "/app";
        public static class CONTROLLER {
            public static final String ROOT = lang + APP_INFO.ROOT;

        }

        public static class API {
            public static final String ROOT = api + APP_INFO.ROOT;

            /**
             * <p>Spring considers that anything behind the last dot is a file extension</p>
             * <p>One way to solve this inconvenience is to modify our @PathVariable definition by adding a regex mapping.</p>
             * <p>Thereby any dot, including the last one, will be considered as part of our parameter</p>
             */
            public static final String GET_APP_INFO_LATEST = ROOT + "/{packageName:.+}/{type}";
            public static final String GET_APP_INFO = ROOT + "/{packageName:.+}/{type}/{versionCode:.+}";
            public static final String VERIFY_APP_VERSION = ROOT + "/verify/{packageName:.+}/{type}/{versionCode:.+}";
            public static final String GET_LIST = ROOT + "/list";
            public static final String GET_LIST_FOR_HOME = ROOT + "/list-for-home";
            public static final String GET_ICON = ROOT + "/get-icon";
            public static final String GET_APP = ROOT + "/get-app";
            public static final String GET_MANIFEST = ROOT + "/get-manifest";
            public static final String GET_APP_CONDITION = ROOT + "/get-list-condition";
        }
    }
}
