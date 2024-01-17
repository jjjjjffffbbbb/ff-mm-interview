import React, {Fragment, useContext, useEffect} from "react";
import {Link, Outlet} from "react-router-dom";
import "./Sidebar.css";

const Sidebar = () => {
    return (
        <>
            <div className="sidebar">
                <Link className="sidebar-link" to="/add-metric">
                    Add Metric
                </Link>
                <Link className="sidebar-link" to="/see-metrics">
                    See Metrics
                </Link>
            </div>
            <Outlet />
        </>
    );
}

export default Sidebar;