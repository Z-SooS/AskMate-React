import './App.css';
import {Route, Routes} from "react-router-dom";
import LoginPage from "./Page/LoginPage";
import Navbar from "./Component/Navbar";

function App() {
    return (
        <>
            <main>
                <Navbar/>
                <Routes>
                    <Route path={'/'} element={<LoginPage/>}/>
                </Routes>
            </main>
        </>
    );
}

export default App;
