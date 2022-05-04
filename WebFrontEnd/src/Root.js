/* This is the Root component mainly initializes Redux and React Router. */

import React from 'react';
import {Provider} from 'react-redux';
import {Switch, Route} from 'react-router-dom';
import {ConnectedRouter} from 'connected-react-router';
import {hot, setConfig} from 'react-hot-loader';
import store from './common/store';
import routeConfig from './common/routeConfig';
import history from './common/history';
import RedirectLoginPage from "./features/home/RedirectLoginPage";
import {useUserLogin} from "./features/examples/redux/userLoginActions";
import { ToastContainer, toast } from 'react-toastify';


setConfig({
    logLevel: 'debug',
});

function renderRouteConfigV3(routes, contextPath, isAuthenticated) {

    // Resolve route config object in React Router v3.
    const children = []; // children component list

    const renderRoute = (item, routeContextPath) => {
        let newContextPath;
        if (/^\//.test(item.path)) {
            newContextPath = item.path;
        } else {
            newContextPath = `${routeContextPath}/${item.path}`;
        }
        newContextPath = newContextPath.replace(/\/+/g, '/');
        if (item.component && item.childRoutes) {
            const childRoutes = renderRouteConfigV3(item.childRoutes, newContextPath, isAuthenticated);
            if (!check(item.requiredAuthenticated)) {
                children.push(
                    <Route
                        key={newContextPath}
                        render={props => <item.component {...props}>{childRoutes}</item.component>}
                        path={newContextPath}
                    />,
                );
            } else {
                if (check(isAuthenticated)) {
                    children.push(
                        <Route
                            key={newContextPath}
                            render={props => <item.component {...props}>{childRoutes}</item.component>}
                            path={newContextPath}
                        />,
                    );
                } else {
                    children.push(
                        <Route key={newContextPath} render={RedirectLoginPage} path={newContextPath} exact/>,
                    );
                }

            }
        } else if (item.component) {
            if (!check(item.requiredAuthenticated)) {
                children.push(
                    <Route key={newContextPath} component={item.component} path={newContextPath} exact/>,
                );
            } else {
                if (check(isAuthenticated)) {
                    children.push(
                        <Route key={newContextPath} component={item.component} path={newContextPath} exact/>,
                    );
                } else {
                    children.push(
                        //<Route key={newContextPath} component={<Redirect to='/home'/>} path={newContextPath} exact />,
                        <Route key={newContextPath} component={RedirectLoginPage} path={newContextPath} exact/>,
                    );
                }

            }
        } else if (item.childRoutes) {
            item.childRoutes.forEach(r => renderRoute(r, newContextPath));
        }
    };

    routes.forEach(item => renderRoute(item, contextPath));

    // Use Switch so that only the first matched route is rendered.
    return <Switch>{children}</Switch>;
}

function check(input) {
    return input && typeof input !== 'undefined'
}

const RootWrapper = () => {
    return (
        <Provider store={store}> {/* Set context*/}
            <Root /> {/* Now App has access to context*/}
            <ToastContainer />
        </Provider>
    )
}

function Root() {
    const {isAuthenticated} = useUserLogin();
    const children = renderRouteConfigV3(routeConfig, '/', isAuthenticated);
    return (
        <Provider store={store}>
            <ConnectedRouter history={history}>{children}</ConnectedRouter>
        </Provider>
    );
}


export default hot(module)(RootWrapper);
