import { Button } from "antd";
import { useSelector } from "react-redux";
import { Link, Outlet } from "react-router-dom";
import ROLES from "../../permissions/Permissions";
import { handleLogout } from "../../redux/reducers/authService";
import { nosql, serviceTypeNull, sql } from "../../redux/reducers/selectService";
import store from "../../redux/store";
import classes from "./navbar.module.css";

export default function Navbar() {
    const isDbSelected = useSelector((state) => state.serviceReducer.isDbSelected);
    const serviceType = useSelector((state) => state.serviceReducer.serviceType);
    const role = useSelector((state) => state.authReducer.role);
    const isLoggedIn = useSelector((state) => state.authReducer.isLoggedIn);

    return (
        <>
            {isDbSelected && (
                <div className={classes.navbar}>
                    <Link to="/home">
                        <Button>Home</Button>
                    </Link>
                    {role && (
                        <Link to={`${role === ROLES.CASHIER ? "/cashier" : "/manager"}`}>
                            <Button type="primary">{role}</Button>
                        </Link>
                    )}
                    <Link to="/">
                        <Button type="primary">
                            {/* {serviceType?.toUpperCase() === "SQL" ? "NO-SQL" : "SQL"} */}
                            Data Settings
                        </Button>
                    </Link>
                    {isLoggedIn ? (
                        <Link
                            onClick={() => store.dispatch(handleLogout())}
                            to="/login"
                            className={classes.login}
                        >
                            Log out
                        </Link>
                    ) : (
                        <Link to="/login" className={classes.login}>
                            Log in
                        </Link>
                    )}
                </div>
            )}

            <div className={classes.container}>
                <Outlet />
            </div>
        </>
    );
}
