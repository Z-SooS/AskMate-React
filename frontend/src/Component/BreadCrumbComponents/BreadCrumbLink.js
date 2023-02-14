import React from 'react';
import {Link} from "react-router-dom";

function BreadCrumbLink({link, name}) {
    return (
        <Link className={'breadcrumb-link'} to={`/${link}`}>{name}</Link>
    );
}

export default BreadCrumbLink;