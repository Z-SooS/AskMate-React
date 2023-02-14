import React, {useEffect} from 'react';
import CustomLink from "../Component/CustomLink";
import "../ComponentStyle/HomePage.css"

function HomePage({userInfo, breadCrumbSetFunc}) {
    useEffect(()=>{
        breadCrumbSetFunc(new Map([['Home','home']]));
    },[])
    return (
        <>
            <section id={"home-page-title"}>
                AskMatyi
            </section>
            <nav id={"home-page-options"}>
                {userInfo != null ?
                    <>
                        <CustomLink to={"/posts"}>See posts</CustomLink>
                        <CustomLink to={"/profile"}>Profile</CustomLink>
                    </>
                :
                    <>
                        <CustomLink to={"/register"}>Create new account</CustomLink>
                        <CustomLink to={"/login"}>Already have an account</CustomLink>
                    </>
                }
            </nav>
        </>
    );
}

export default HomePage;