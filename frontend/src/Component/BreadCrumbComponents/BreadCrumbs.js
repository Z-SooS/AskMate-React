import React, {useEffect, useState} from 'react';
import BreadCrumbLink from "./BreadCrumbLink";

function BreadCrumbs({breadCrumbSetUp}) {
    const [links, setLinks] = useState(new Map([['Home', 'home']]));
    function addBreadCrumb(name, value){
        let copy = Object.assign({}, links);
        copy.set(name,value);
        setLinks(copy);
    }
    function setBreadCrumb(valuemap =new Map([['Home', 'home']])){
        setLinks(valuemap);
    }
    useEffect(() => {
        breadCrumbSetUp(setBreadCrumb, addBreadCrumb);
    }, []);
    const breadCrumb = [];
    for (let [key,value] of links) {
        if(breadCrumb.length >= 1){
            breadCrumb.push('>');
        }
        breadCrumb.push(<BreadCrumbLink name={key} link={value} key={key}/>)
    }
    return (
        <nav className={'breadcrumbs'}>
            {breadCrumb}
        </nav>
    );
}

export default BreadCrumbs;