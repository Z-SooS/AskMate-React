import React from 'react';
import CustomLink from "./CustomLink";

function Navbar(props) {
    return (
        <nav id={"navbar"}>
            <ul>
                <CustomLink to={"/"}>Home</CustomLink>
                {/*<CustomLink to={"/profile"}>Profile(WIP)</CustomLink>*/}
                {/*<CustomLink to={"/users"}>Users</CustomLink>*/}
            </ul>
        </nav>
    );
}

export default Navbar;