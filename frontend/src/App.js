import './App.css';
import {Route, Routes} from "react-router-dom";
import LoginPage from "./Page/LoginPage";

function App() {
    return (
        <>
            <main>
                <Routes>
                    <Route path={'/'} element={<LoginPage/>}/>
                </Routes>
            </main>
        </>
    );
}

export default App;
