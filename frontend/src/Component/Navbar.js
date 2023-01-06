import CustomLink from "./CustomLink";
import LogoutButton from "./LogoutButton";

function Navbar({userInfo, userInfoSetter}) {
    function nullUserInfo(){
        userInfoSetter(null);
    }
    return (
        <nav id={"navbar"}>
            <ul>
                <CustomLink to={"/"}>Home</CustomLink>
                {userInfo != null ?
                    <>
                        <CustomLink to={"/posts"}>Posts</CustomLink>
                        <CustomLink to={"/profile"}>{userInfo.sub}</CustomLink>
                        <LogoutButton userInfoNuller={nullUserInfo}/>
                    </>
                    : <>
                        <CustomLink to={"/login"}>Log in</CustomLink>
                        <CustomLink to={"/register"}>Registration</CustomLink>
                    </>
                }
            </ul>
        </nav>
    );
}

export default Navbar;