import React from 'react';
import {Link} from 'react-router-dom';
import {Button, Modal, Form} from 'react-bootstrap'
import { GoogleLogin } from 'react-google-login';
import {useShowLoginPopup} from './redux/hooks';
import {useUserLogin} from "./redux/userLoginActions";
import 'react-toastify/dist/ReactToastify.css';

function LoginLogoutElement(props) {
    if (!props.isAuthenticated) {
        return <a className="login-trigger" href="#" data-target="#login" data-toggle="modal"
                  onClick={props.showLoginPopup}>Login</a>;
    } else {
        return <a className="login-trigger" href="#" data-target="#logout"
                  onClick={props.userLogout}>Logout</a>;
    }
}

export default function SidePanel() {
    const clientId = process.env.REACT_APP_GOOGLE_CLIENT_ID;

    const {isShowLogin, showLoginPopup} = useShowLoginPopup();
    const {isAuthenticated, loginFailure, userLogin, userLogout} = useUserLogin();
    const logout = () => {
        userLogout();
        window.location.reload();
    }

    const onGoogleSuccess = (res) => {
        console.log('Login Success: currentUser:', res.profileObj);
        alert(
            `Login Success`
        );
       // refreshTokenSetup(res);
    };

    const onGoogleFailure = (res) => {
        console.log('Login failed: res:', res);
        alert(
            `Failed to login.`
        );
    };

    /*const [isShowLogin, isShowPopup] = React.useState(false);
    const showLoginPopup = () => {
        isShowPopup(isShowLogin ? false : true)
    }*/
    const [username, setUsername] = React.useState('');
    const [password, setPassword] = React.useState('');

    return (
        <>
            <div className="examples-side-panel">
                <ul className={"col-12"}>
                    <li>
                        <Link to="/manager">Welcome</Link>
                    </li>
                    <li>
                        <Link to="/manager/counter">Counter Demo</Link>
                    </li>
                    <li>
                        <Link to="/manager/counter">Reddit API Demo</Link>
                    </li>
                    <li>
                        <LoginLogoutElement isAuthenticated={isAuthenticated} showLoginPopup={showLoginPopup}
                                            userLogout={logout}/>
                    </li>
                    <li>
                        <Link to="/">Back to start page</Link>
                    </li>
                </ul>
                <div className="memo">
                    © 2022 All Rights Reserved
                </div>

            </div>

            {/*<LoginModal/>*/}
            <Modal id="login" className={"modal fade"} role="dialog" show={isShowLogin}>
                <div className={"login-container"}>
                    {/*<Modal.Header></Modal.Header>*/}
                    <div className={"modal-content"}>
                        <Modal.Body>
                            <div>
                                <Button data-dismiss={"model"} className={"close "}
                                        onClick={showLoginPopup}>&times;</Button>
                                <h4>Login</h4>

                            </div>

                            <Form>
                                <Form.Control id="username" type="text" name="username"
                                              className={"username form-control"}
                                              placeholder={"Username"}
                                              value={username}
                                              onChange={e => setUsername(e.target.value)}></Form.Control>
                                <Form.Control id="password" type="password" name="password"
                                              className={"password form-control"}
                                              placeholder={"password"}
                                              value={password} onChange={e => setPassword(e.target.value)}/>
                                <Form.Control className={"btn login"} type="button" value="Login"
                                              onClick={() => userLogin(username, password)}/>
                            </Form>
                            {/*<div>
                                <GoogleLogin
                                    clientId={clientId}
                                    buttonText="Login"
                                    onSuccess={() => onGoogleSuccess}
                                    onFailure={() => onGoogleFailure}
                                    cookiePolicy={'single_host_origin'}
                                    style={{ marginTop: '100px' }}
                                    isSignedIn={true}
                                />
                            </div>*/}
                        </Modal.Body>
                    </div>
                </div>
            </Modal>
        </>
    );
}
