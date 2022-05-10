import React, {useEffect, useCallback} from 'react';
import {useAppList} from "./redux/apiAppList";
import {AppItem} from "../common/AppElement/AppItem";
import {MODE} from "./redux/constants";



export default function AppList() {
    const {isLoading, appList, appListApi} = useAppList();
    let c = '';
    const renderItem = (list) => {
        let child = [];
        for (let i in list) {
            const data = list[i];
            data.mode = MODE.LIST;
            child.push(AppItem(data));
        }
        return child;
    }

    useEffect(() => {
        appListApi();
        // eslint-disable-next-line react-hooks/exhaustive-deps
        //c = renderItem();
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, []);



    // appListApi();
    return (
        <div className="app-list-container container col-lg-12">
            <div className={"row"}>
                {renderItem(appList)}
            </div>
        </div>
    );
}
