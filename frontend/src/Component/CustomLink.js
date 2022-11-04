import React from 'react';
import {Link, useMatch, useResolvedPath} from "react-router-dom";

function CustomLink({to,children}) {
    const resolvedPath = useResolvedPath(to);
    const isActive = useMatch({path: resolvedPath.pathname, end: true})
    return (
        <li className={isActive && "active"}>
            <Link to={to}>{children}</Link>
        </li>
    );
}

export default CustomLink;