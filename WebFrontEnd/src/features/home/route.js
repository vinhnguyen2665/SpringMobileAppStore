import { WelcomePage } from './';

export default {
  path: 'home',
  childRoutes: [{ path: 'welcome-page', component: WelcomePage, isIndex: true, requiredAuthenticated: false }],
};
