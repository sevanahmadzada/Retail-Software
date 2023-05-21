import { useEffect } from "react";
import { useNavigate, useParams } from "react-router";
import { Link } from "react-router-dom";
import { ArrowLeftOutlined } from "@ant-design/icons";
import { Button, Col, Form, Input, Row } from "antd";
import { httpClient } from "../../config/config";
import classes from "./manager.module.css";
import { useSelector } from "react-redux";

export default function AddProduct() {
    let navigate = useNavigate();
    const [form] = Form.useForm();

    const userId = useSelector((state) => state.authReducer.userId);

    const id = useParams()?.id;

    useEffect(() => {
        if (id) {
            httpClient("products/" + id, "get").then((res) => {
                form.setFieldsValue({
                    ["name"]: res?.data?.name,
                    ["description"]: res?.data?.description,
                    ["price"]: res?.data?.price,
                    ["quantity"]: res?.data?.quantity,
                    ["discount"]: res?.data?.discount,
                });
            });
        }
    }, [id]);

    const onFinish = (value) => {
        const data = {
            description: value.description,
            name: value.name,
            price: value.price,
            quantity: value.quantity,
            discount: value.discount,
            addedBy: userId,
        };

        if (id) {
            httpClient("products/" + id, "put", data).then(() => navigate("/manager"));
        } else {
            httpClient("products", "post", data).then(() => navigate("/manager"));
        }
    };
    console.log(form.getFieldInstance("name"));

    return (
        <div>
            <Link to="/manager">
                <Button style={{ marginBottom: "40px" }}>
                    Go back <ArrowLeftOutlined />
                </Button>
            </Link>

            <Form
                name="normal_login"
                initialValues={{
                    remember: true,
                }}
                layout={"vertical"}
                style={{ width: "100%" }}
                onFinish={onFinish}
                form={form}
            >
                <Row gutter={50} justify="center">
                    <Col span={12}>
                        <Form.Item
                            name="name"
                            label="Name"
                            rules={[
                                {
                                    required: true,
                                    message: "Please input product name!",
                                },
                            ]}
                        >
                            <Input />
                        </Form.Item>

                        <Form.Item
                            name="description"
                            label="Description"
                            rules={[
                                {
                                    required: true,
                                    message: "Please input product description!",
                                },
                            ]}
                        >
                            <Input />
                        </Form.Item>

                        <Form.Item
                            name="price"
                            label="Price"
                            rules={[
                                {
                                    required: true,
                                    message: "Please input product price!",
                                },
                            ]}
                        >
                            <Input />
                        </Form.Item>

                        <Form.Item
                            name="quantity"
                            label="Quantity"
                            rules={[
                                {
                                    required: true,
                                    message: "Please input product quantity!",
                                },
                            ]}
                        >
                            <Input />
                        </Form.Item>

                        <Form.Item
                            name="discount"
                            label="Discount"
                            rules={[
                                {
                                    required: true,
                                    message: "Please input product discount!",
                                },
                            ]}
                        >
                            <Input />
                        </Form.Item>
                    </Col>
                </Row>

                <Form.Item>
                    <Button type="primary" htmlType="submit" className={classes.form_button}>
                        Add
                    </Button>
                </Form.Item>
            </Form>
        </div>
    );
}
