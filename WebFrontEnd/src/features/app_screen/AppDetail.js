import React, {useEffect, useCallback} from 'react';
import {useAppList} from "./redux/apiAppList";
import {AppItem} from "../common/AppElement/AppItem";
import {MODE} from "./redux/constants";
import {useParams} from "react-router-dom";


export default function AppDetail() {
    const params = useParams();
    console.log(params);
    const {isLoading, appList, appListConditionApi} = useAppList();
    let c = '';
    const listApp = (list) => {
        let child = [];
        for (let i = 1; i < list.length; i++) {
            const data = list[i];
            data.mode = MODE.LIST;
            AppItem(data)
            child.push(AppItem(data));
        }
        return child;
    }
    const renderItem = (list) => {
        if(list.length > 0){
            const data = list[0];
            data.mode = MODE.DETAILS;
            data.noneLink = true;
            return AppItem(data);
        }
    }

    useEffect(() => {
        appListConditionApi(params);
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, []);

    return (
        <div className="app-list-container container col-lg-12">
            <div id="detail" className={"row"}>
                {renderItem(appList)}
            </div>
            <div id="list">
                    <div className={"row"}>
                        {listApp(appList)}
                    </div>
            </div>
        </div>
    );
}
