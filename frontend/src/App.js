import './App.css';
import {Route, Routes} from "react-router-dom";
import LoginPage from "./Page/LoginPage";
import Navbar from "./Component/Navbar";
import PostsPage from "./Page/PostsPage";
import RegisterPage from "./Page/RegisterPage";
import {useState} from "react";
import CookieMethods from "./Config/CookieMethods";
import cookieMethods from "./Config/CookieMethods";
import PageFooter from "./Component/PageFooter";
import HomePage from "./Page/HomePage";

function App() {
    return (
        <>
            <main>
                <Routes>
                    <Route path={"/"} element={<HomePage userInfo={userInfo}/>}/>
                    <Route path={'/login'} element={<LoginPage userInfoSetter={setUserInfo} getUserInfoFromCookie={checkCookieForUsername}/>}/>
                    <Route path={"/posts"} element={<PostsPage/>}/>
                    <Route path={"/register"} element={<RegisterPage/>}/>
                    {/*<Route path={"/profile"} element={<RegisterPage/>}/>*/}
                </Routes>
            </main>
        </>
    );
}

export default App;
