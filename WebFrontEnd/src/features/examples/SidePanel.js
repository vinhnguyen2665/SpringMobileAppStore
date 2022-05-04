import React from 'react';
import {Link} from 'react-router-dom';
import {Button, Modal, Form} from 'react-bootstrap'
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
    const {isShowLogin, showLoginPopup} = useShowLoginPopup();
    const {isAuthenticated, loginFailure, userLogin, userLogout} = useUserLogin();
    console.log(loginFailure);
    const logout = () => {
        userLogout();
        window.location.reload();
    }

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
                        <Link to="/">Welcome</Link>
                    </li>
                    <li>
                        <Link to="/counter">Counter Demo</Link>
                    </li>
                    <li>
                        <Link to="/reddit">Reddit API Demo</Link>
                    </li>
                    <li>
                        <LoginLogoutElement isAuthenticated={isAuthenticated} showLoginPopup={showLoginPopup}
                                            userLogout={logout}/>
                    </li>
                    <li>
                        <Link to="/home">Back to start page</Link>
                    </li>
                </ul>
                <div className="memo">
                    This is a Rekit feature that contains some examples for you to quick learn how Rekit works.
                    To remove it just delete the feature.
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
                        </Modal.Body>
                    </div>
                </div>
            </Modal>
        </>
    );
}
