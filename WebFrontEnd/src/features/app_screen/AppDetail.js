import React, {useEffect, useCallback} from 'react';
import {useAppList} from "./redux/apiAppList";
import {AppItem} from "../common/AppElement/AppItem";
import {MODE} from "./redux/constants";
import {useParams, useLocation} from "react-router-dom";


export default function AppDetail() {

    function useQuery() {
        const {search} = useLocation();

        return React.useMemo(() => new URLSearchParams(search), [search]);
    }

    const params = useParams();
    const query = useQuery();

    const {isLoading, appList, appListConditionApi} = useAppList();
    let c = '';
    const listApp = (list, appIdSelected) => {
        let child = [];
        if(appIdSelected){
            list = list.filter(app => app.id != appIdSelected)
        } else {
            list = list.filter(app => app.id != list[0].id)
        }
        for (let i = 0; i < list.length; i++) {
            const data = list[i];
            data.mode = MODE.LIST;
            AppItem(data)
            child.push(AppItem(data));
        }
        return child;
    }
    const renderItem = (list, appIdSelected) => {
        if (list.length > 0) {
            let data = {};
            if (appIdSelected) {
                data = list.filter(app => app.id == appIdSelected)[0];
            } else {
                data = list[0];
            }
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
                {renderItem(appList, query.get("id"))}
            </div>
            <div id="list">
                <div className={"row"}>
                    {listApp(appList, query.get("id"))}
                </div>
            </div>
        </div>
    );
}
