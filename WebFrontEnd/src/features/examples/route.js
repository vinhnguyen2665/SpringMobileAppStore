// This is the JSON way to define React Router rules in a Rekit app.
// Learn more from: http://rekit.js.org/docs/routing.html

import { WelcomePage, CounterPage, RedditListPage, Layout } from './';

export default {
  path: '',
  component: Layout,
  childRoutes: [
    { path: '', component: WelcomePage, isIndex: true, requiredAuthenticated: false},
    { path: 'counter', component: CounterPage, requiredAuthenticated: false},
    { path: 'reddit', component: RedditListPage,  requiredAuthenticated: true},
  ],
};
