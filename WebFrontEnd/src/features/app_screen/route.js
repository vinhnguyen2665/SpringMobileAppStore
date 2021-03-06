// This is the JSON way to define React Router rules in a Rekit app.
// Learn more from: http://rekit.js.org/docs/routing.html

import { AppList, AppDetail,CounterPage, RedditListPage, Layout } from './';

export default {
  path: 'manager',
  component: Layout,
  childRoutes: [
    { path: '', component: AppList, isIndex: true, requiredAuthenticated: true},
    { path: 'app/:appType/:packageName/:versionName', component: AppDetail, requiredAuthenticated: true},
    { path: 'counter', component: CounterPage, requiredAuthenticated: true},
    { path: 'counter', component: CounterPage, requiredAuthenticated: true},
    { path: 'reddit', component: RedditListPage,  requiredAuthenticated: true},
  ],
};
