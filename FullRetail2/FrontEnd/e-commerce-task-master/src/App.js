import { Navigate, Route, Routes } from "react-router";

import Home from "./components/Home/Home";
import Login from "./components/Login/Login";
import Manager from "./components/Manager/Manager";
import Navbar from "./components/Navbar/Navbar";
import { Spin } from "antd";

import AddProduct from "./components/Manager/AddProduct";
import Cashier from "./components/Cashier/Cashier";
import { useSelector } from "react-redux";
import DataSetting from "./components/DataSettings/DataSetting";
import "./App.css";
import ROLES from "./permissions/Permissions";

function App() {
    const isLoading = useSelector((state) => state.loaderReducer.isLoading);
    const isDbSelected = useSelector((state) => state.serviceReducer.isDbSelected);
    const role = useSelector((state) => state.authReducer.role);

    return (
        <div className="App">
            <Spin spinning={isLoading}>
                <Routes>
                    <Route path="/" element={<DataSetting />} />
                    <Route path="/" element={<Navbar />}>
                        {isDbSelected && (
                            <>
                                <Route path="/home" element={<Home />} />
                                {role == ROLES.CASHIER && (
                                    <>
                                        <Route path="/cashier" element={<Cashier />} />
                                    </>
                                )}
                                {role == ROLES.MANAGER && (
                                    <>
                                        <Route path="/manager" element={<Manager />} />
                                        <Route
                                            path="/manager/add-product/:id"
                                            element={<AddProduct />}
                                        />
                                        <Route
                                            path="/manager/add-product"
                                            element={<AddProduct />}
                                        />
                                    </>
                                )}
                            </>
                        )}
                    </Route>
                    {isDbSelected && <Route path="/login" element={<Login />} />}

                    <Route path="*" element={<Navigate replace to="/" />} />
                </Routes>
            </Spin>
        </div>
    );
}

export default App;
