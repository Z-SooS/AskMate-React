import React from 'react';
import {Link} from "react-router-dom";

function Tagbox({tagObjects}) {
    const links = tagObjects.map(tagObject=><Link className={'tag'} to={`/posts?tag=${tagObject.id}`} key={tagObject.id}>{tagObject.name}</Link>)
    return (
        <div className={'tagbox'}>
            {links.length > 0 ?
            links
            : "This post is not tagged"}
        </div>
    );
}

export default Tagbox;