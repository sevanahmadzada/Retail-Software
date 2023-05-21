import { Form, Input, Button, Popover, message } from "antd";
import { UserOutlined, LockOutlined, InfoCircleOutlined } from "@ant-design/icons";
import classes from "./login.module.css";
import axios from "axios";
import jwtDecode from "jwt-decode";
import store from "../../redux/store";
import { handleLogin } from "../../redux/reducers/authService";
import ROLES from "../../permissions/Permissions";
import { useNavigate } from "react-router";
import { Link } from "react-router-dom";
import { useEffect, useState } from "react";

export default function Login() {
    const [users, setUsers] = useState([]);

    const [form] = Form.useForm();
    const navigate = useNavigate();

    useEffect(() => {
        let hasManager = false;
        let hasCashier = false;
        let tempdata = [];
        axios.get("https://localhost/sql/employees").then((res) => {
            res.data?.map((item) => {
                if (item?.info?.rollName == ROLES.MANAGER && !hasManager) {
                    tempdata.push(item);
                    hasManager = true;
                    return;
                }
                if (item?.info?.rollName == ROLES.CASHIER && !hasCashier) {
                    tempdata.push(item);
                    hasCashier = true;
                    return;
                }
            });
            setUsers(tempdata);
        });
    }, []);
    console.log(users);

    const onFinish = (value) => {
        const bodyData = {
            username: value.username,
            password: value.password,
        };
        axios.post("https://localhost/api/auth/login", bodyData).then((res) => {
            const decodedToken = jwtDecode(res?.data?.accessToken);

            store.dispatch(
                handleLogin({
                    access_token: res?.data?.accessToken,
                    userId: res?.data?.userId,
                    role: decodedToken.roles[0].rollName,
                })
            );

            if (decodedToken.roles[0].rollName == ROLES.CASHIER) {
                navigate("/cashier");
            } else if (decodedToken.roles[0].rollName == ROLES.MANAGER) {
                navigate("/manager");
            }
        }).catch(()=>message.error("Username or password are wrong"));
    };

    const popoverContent = (
        <div className={classes.popover}>
            {users.map((item) => {
                if (item.info.rollName == ROLES.MANAGER) {
                    return (
                        <div>
                            <h4>Manager</h4>
                            <div>
                                <span>username: {item?.info?.username}</span>
                                <span>password: 1234</span>
                            </div>
                        </div>
                    );
                } else if (item.info.rollName == ROLES.CASHIER) {
                    return (
                        <div>
                            <h4>Cashier</h4>
                            <div>
                                <span>username: {item?.info?.username}</span>
                                <span>password: 1234</span>
                            </div>
                        </div>
                    );
                }
            })}
        </div>
    );

    return (
        <div className={classes.login_form_area}>
            <div className={classes.login_form}>
                <div className={classes.login_top}>
                    <Link to="/home">
                        <Button>Home</Button>
                    </Link>
                    <Popover content={popoverContent}>
                        <Button>
                            <InfoCircleOutlined />
                        </Button>
                    </Popover>
                </div>
                <h3>Shop login</h3>
                <Form
                    name="normal_login"
                    initialValues={{
                        remember: true,
                    }}
                    style={{ width: "100%" }}
                    onFinish={onFinish}
                    form={form}
                >
                    <Form.Item
                        name="username"
                        rules={[
                            {
                                required: true,
                                message: "Please input your Username!",
                            },
                        ]}
                    >
                        <Input
                            prefix={<UserOutlined className={classes.form_input} />}
                            placeholder="Username"
                        />
                    </Form.Item>
                    <Form.Item
                        name="password"
                        rules={[
                            {
                                required: true,
                                message: "Please input your Password!",
                            },
                        ]}
                    >
                        <Input
                            prefix={<LockOutlined className={classes.form_input} />}
                            type="password"
                            placeholder="Password"
                        />
                    </Form.Item>

                    <Form.Item>
                        <Button type="primary" htmlType="submit" className={classes.form_button}>
                            Log in
                        </Button>
                    </Form.Item>
                </Form>
            </div>
        </div>
    );
}
