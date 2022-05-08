import React from 'react';
import {Button, Modal, Form} from 'react-bootstrap'
import {useShowLoginPopup} from './redux/hooks';
import {useUserLogin} from "./redux/userLoginActions";

export default function LoginModal() {
    const {isShowLogin, showLoginPopup} = useShowLoginPopup();
    const {userLogin} = useUserLogin();

    /*const [isShowLogin, isShowPopup] = React.useState(false);
    const showLoginPopup = () => {
        isShowPopup(isShowLogin ? false : true)
    }*/

    return (
        <>
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
                                <Form.Control type="text" name="username" className={"username form-control"}
                                              placeholder={"Username"}></Form.Control>
                                <Form.Control type="password" name="password" className={"password form-control"}
                                              placeholder={"password"}/>
                                <Form.Control className={"btn login"} type="submit" value="Login"
                                              onClick={() => userLogin("00001", "12345678")}/>
                            </Form>
                        </Modal.Body>
                    </div>
                </div>
            </Modal>

        </>
    );
}
