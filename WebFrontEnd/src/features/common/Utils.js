export class Utils {

    static isAndroid = () => {
        return navigator.userAgent.match(/Android/i);
    };
    static isBlackBerry = () => {
        return navigator.userAgent.match(/BlackBerry/i);
    };
    static isIOS = () => {
        return navigator.userAgent.match(/iPhone|iPad|iPod/i) || (navigator.platform === 'MacIntel' && navigator.maxTouchPoints > 1)
    };
    static isOpera = () => {
        return navigator.userAgent.match(/Opera Mini/i);
    };
    static isWindows = () => {
        return navigator.userAgent.match(/IEMobile/i) || navigator.userAgent.match(/WPDesktop/i);
    };
    static isAnyMobile = () => {
        return (Utils.isAndroid() || Utils.isBlackBerry() || Utils.isIOS() || Utils.isOpera() || Utils.isWindows());
    }

    /*
    // Opera 8.0+
        var
        isOpera = (!!window.opr && !!opr.addons) || !!window.opera || navigator.userAgent.indexOf(' OPR/') >= 0;

    // Firefox 1.0+
        var
        isFirefox = typeof InstallTrigger !== 'undefined';

    // Safari 3.0+ "[object HTMLElementConstructor]"
        var
        isSafari = /constructor/i.test(window.HTMLElement) || (function (p) {
            return p.toString() === "[object SafariRemoteNotification]";
        })(!window['safari'] || (typeof safari !== 'undefined' && safari.pushNotification));

    // Internet Explorer 6-11
        var
        isIE = /!*@cc_on!@*!/false || !!document.documentMode;

    // Edge 20+
        var
        isEdge = !isIE && !!window.StyleMedia;

    // Chrome 1 - 71
        var
        isChrome = !!window.chrome && (!!window.chrome.webstore || !!window.chrome.runtime);

    // Blink engine detection
        var
        isBlink = (isChrome || isOpera) && !!window.CSS;

        var
        detectBrowser = function () {
            let browser = '';
            switch (true) {
                case isChrome:
                    browser = "Chrome";
                    break;
                case isEdge:
                    browser = "Edge";
                    break;
                case isIE:
                    browser = "IE";
                    break;
                case isSafari:
                    browser = "Safari";
                    break;
                case isFirefox:
                    browser = "Firefox";
                    break;
                case isOpera:
                    browser = "Opera";
                    break;
                default:
                    browser = "undefined";
            }
            return browser;
        }*/
}