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
import BreadCrumbs from "./Component/BreadCrumbComponents/BreadCrumbs";

function App() {
    function checkCookieForUsername(){
        const headerPayloadValue = CookieMethods.getJwtPayload();
        if (headerPayloadValue == null) {
            return null;
        }
        return cookieMethods.parseJwt(headerPayloadValue);
    }
    const [userInfo, setUserInfo] = useState(checkCookieForUsername());

    return (
        <>
                <Navbar userInfo={userInfo} userInfoSetter={setUserInfo}/>
            <BreadCrumbs/>
            <main>
                <Routes>
                    <Route path={"/"} element={<HomePage userInfo={userInfo}/>}/>
                    <Route path={"/home"} element={<HomePage userInfo={userInfo}/>}/>
                    <Route path={"/posts"}>
                        <Route path={':pageFromPath'} element={<PostsPage/>}/>
                        <Route path={''} element={<PostsPage/>}/>
                    </Route>
                    <Route path={"/register"} element={<RegisterPage/>}/>
                    <Route path={'/login'} element={<LoginPage userInfoSetter={setUserInfo} getUserInfoFromCookie={checkCookieForUsername}/>}/>
                    {/*<Route path={"/profile"} element={<RegisterPage/>}/>*/}
                </Routes>
            </main>
            <PageFooter/>
        </>
    );
}

export default App;
