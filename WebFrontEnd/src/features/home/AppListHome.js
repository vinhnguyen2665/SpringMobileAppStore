import React from 'react';
import AppList from "../app_screen/AppList";
import {Link} from "react-router-dom";

export default function AppListHome() {
  return (
      <>
        <Link to="/manager">Go to manager page</Link>
      <AppList/>
      </>
  );
}
