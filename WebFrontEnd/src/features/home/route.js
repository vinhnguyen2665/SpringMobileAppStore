import { AppListHome, AppDetailHome } from './';

export default {
  path: '',
  childRoutes: [
      { path: 'welcome-page', component: AppListHome, isIndex: true, requiredAuthenticated: false },
    { path: 'app/:appType/:packageName/:versionName', component: AppDetailHome, requiredAuthenticated: false},
  ],
};
