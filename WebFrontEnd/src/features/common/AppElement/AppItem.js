import React from 'react';
import Moment from "react-moment";
import QRCode from "react-qr-code";
import {
    MODE,
    URL
} from '../../app_screen/redux/constants';
import {Link, useLocation, useParams} from "react-router-dom";

import {proxy} from "../../../../package.json";


export const AppItem = (props) => {

    // const location = useLocation();
    const downloadElement = (appInfo) => {
        if (appInfo.mode == MODE.DETAILS) {
            const path = proxy + '/api/app/get-app?id=' + appInfo.id;

            return <>
                <div className="qrcode" title="">
                    <QRCode width="256px" height="256px"
                            value={path} title={path}/>
                </div>
                <a className={"install"} target="_self" href={path}>Download</a>
            </>
        }
    }
    const item = () => {
        const appInfo = props;
        let iconType = '';
        let classNameStr = 'app-item col-12 col-sm-6 col-md-4 col-lg-3';
        let linkTo = '/app/' + appInfo.appType + '/' + appInfo.packageName + '/' + appInfo.versionName;
        if (appInfo.mode == MODE.DETAILS) {
            classNameStr = 'app-item';
        }
        switch (appInfo.appType) {
            case 'apk': {
                iconType = "/img/android.svg";
                break;
            }
            case 'ipa': {
                iconType = "/img/ios.svg";
                break;
            }
            default: {
                iconType = "/img/default.svg";
                break;
            }
        }

        return <>
            <div className={classNameStr}>
                <Link className={"app-item"} to={linkTo} target="_self">
                    <img className="icon" src={URL.GET_ICON + appInfo.id} alt=""/>
                    <div className="name">{appInfo.appName}</div>
                    <div>{appInfo.versionName + ' - ' + appInfo.appSize + appInfo.appSizeUnit}</div>
                    <div className="date">
                        <img className="type" src={iconType}/>
                        <span className="title">Upload Date:  <Moment date={appInfo.createDate}
                                                                      format={'YYYY/MM/DD HH:MM:SS A'}/></span>
                        {/* <span onClick="onClickDelete('gbhUmhjrVnkdPMQxuAPoDR')" className="delete">Delete</span>*/}
                    </div>

                </Link>
                {downloadElement(appInfo)}
            </div>
        </>;
    }

    return item();
}
