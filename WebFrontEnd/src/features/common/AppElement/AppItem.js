import React from 'react';
import Moment from "react-moment";
import QRCode from "react-qr-code";
import {
    MODE,
    URL
} from '../../app_screen/redux/constants';
import {Link} from "react-router-dom";

import {proxy} from "../../../../package.json";

import {Utils} from "../Utils";


export const AppItem = (props) => {

    //const location = useLocation();
    const ipaInstall = (e, appType,plist) => {
        /*        const event = this.props;
                event && event.stopPropagation()*/
        const textInstall = appType == 'ipa' && Utils.isIOS() ? 'Installing...' : 'Downloading...';
        e.target.textContent = textInstall;
        e.target.classList.add("disabled");
        window.location.href = plist
        /* window.location.href = 'itms-services://?action=download-manifest&url=https://store.zero9vn.com/manifest/manifest.plist'*/
    }

    const downloadElement = (appInfo) => {
        if (appInfo.mode == MODE.DETAILS) {
            const textInstall = appInfo.appType == 'ipa' && Utils.isIOS() ? 'Install' : 'Download';
            const path = appInfo.appType == 'ipa' && Utils.isIOS() ? 'itms-services://?action=download-manifest&url=' + proxy + '/' + appInfo.manifestResource : proxy + '/api/app/get-app?id=' + appInfo.id;
            const linkTo = window.location.origin + '/app/' + appInfo.appType + '/' + appInfo.packageName + '/' + appInfo.versionName + '?id=' + appInfo.id;
            return <>
                <div className="qrcode" title="">
                    <QRCode width="256px" height="256px"
                            value={linkTo} title={linkTo}/>
                </div>
                {/*  <a className={"install"} target="_self" href={path}>Download</a>*/}
                <div className={"install"} onClick={(e) => ipaInstall(e, appInfo.appType,path)}>{textInstall}</div>
            </>
        }
    }
    const item = (appInfo) => {
        let iconType = '';
        let classNameStr = 'app-item col-12 col-sm-6 col-md-4 col-lg-3';
        let linkTo = '/app/' + appInfo.appType + '/' + appInfo.packageName + '/' + appInfo.versionName + '?id=' + appInfo.id;
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

    return item(props);
}
