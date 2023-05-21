import { useEffect, useState } from "react";
import {
    Button,
    Col,
    Input,
    InputNumber,
    message,
    Modal,
    Radio,
    Row,
    Select,
    Space,
    Table,
    Tag,
} from "antd";
import { ShoppingCartOutlined } from "@ant-design/icons";
import { httpClient } from "../../config/config";
import classes from "./cashier.module.css";
import { useSelector } from "react-redux";

export default function Cashier() {
    const [products, setProducts] = useState([]);
    const [cartProducts, setCartProducts] = useState([]);
    const [render, setRender] = useState(false);
    const [totalPrice, setTotalPrice] = useState(0);
    const [isVisible, setIsVisible] = useState(false);

    const [pageIndex, setPageIndex] = useState(1);
    const [pageSize, setPageSize] = useState(10);
    const [totalSize, setTotalSize] = useState();

    const [orderId, setOrderId] = useState();
    const [change, setChange] = useState(0);
    const [selectedMethod, setSelectedMethod] = useState("CASH");

    const [customers, setCustomers] = useState([]);
    const [selectedCustomer, setSelectedCustomer] = useState();

    const [searchProducts, setSearchProducts] = useState([]);

    const userId = useSelector((state) => state.authReducer.userId);

    const getProducts = (pageSize1, pageIndex1) => {
        httpClient(
            "products?" +
                "page=" +
                `${Number(pageIndex1 ? pageIndex1 - 1 : pageIndex - 1)}` +
                "&size=" +
                `${pageSize1 ? pageSize1 : pageSize}`,
            "get"
        ).then((res) => {
            setProducts(res?.data?.content);
            setTotalSize(res.data?.totalElements);
        });
    };

    useEffect(() => {
        getProducts();
    }, []);

    const changePage = (pg) => {
        setPageSize(pg.pageSize);
        setPageIndex(pg.current);
        getProducts(pg.pageSize, pg.current);
    };

    useEffect(() => {
        let total = 0;
        cartProducts.map((item) => {
            total = total + Math.floor(item.price * item.quantity);
        });
        setTotalPrice(total);
    }, [render, cartProducts]);

    const addToCart = (record) => {
        let reserveData = cartProducts;
        let check = false;

        reserveData.map((item) => {
            if (item.key == record.key) {
                check = true;
            }
        });
        if (!check) {
            record.maxQuantity = record.quantity;
            record.quantity = 1;
            reserveData.push(record);
            setCartProducts(reserveData);
            setRender(!render);
        }else{
            message.error("Already in the cart")
        }
    };

    const handleQuantity = (value, id) => {
        let reserveData = cartProducts;
        reserveData.map((item) => {
            if (item.key == id) {
                if (value !== null) {
                    item.quantity = Number(value);
                } else {
                    item.quantity = Number(1);
                }
            }
        });
        setCartProducts(reserveData);
        setRender(!render);
    };

    const removeHandler = (id) => {
        let reserveData = cartProducts;
        let newData = reserveData.filter((item) => item.key != id);
        setCartProducts(newData);
    };

    const columns = [
        {
            title: "Name",
            dataIndex: "name",
            key: "name",
            width: "50%",
        },
        {
            title: "Description",
            dataIndex: "description",
            key: "description",
            width: "25%",
        },
        {
            title: "Price",
            dataIndex: "price",
            key: "price",
        },
        {
            title: "Discount",
            dataIndex: "discount",
            key: "discount",
        },
        {
            title: "Quantity",
            dataIndex: "quantity",
            key: "quantity",
        },
        {
            title: "Action",
            key: "action",
            render: (_, record) => (
                <Space size="middle">
                    <ShoppingCartOutlined
                        onClick={() => addToCart(record)}
                        style={{ fontSize: "20px", cursor: "pointer" }}
                    />
                </Space>
            ),
        },
    ];

    const data = products.map((item) => {
        return {
            key: item.id,
            name: item.name,
            description: item.description,
            price: item.price,
            quantity: item.quantity,
            discount: item.discount,
        };
    });

    const submitHandler = () => {
        const orderDetails = cartProducts.map((item) => {
            return {
                product: item.key,
                discount: item.discount,
                quantity: item.quantity,
            };
        });
        const data = {
            content: "sale",
            employee: userId,
            customer: selectedCustomer,
            orderDetailDto: orderDetails,
        };

        httpClient("orders/join", "put", data).then((res) => {
            setOrderId(res?.data?.id);
            setIsVisible(true);
        });
    };

    const transactionHandler = () => {
        httpClient("transactions", "post", {
            order: orderId,
            paymentType: selectedMethod,
        }).then(() => {
            message.success("Order done successfully");
            setIsVisible(false);
            setCartProducts([]);
            setChange(0);
            setOrderId();
            setSelectedMethod("CASH");
            setSelectedCustomer();
            setCustomers([]);
        });
    };

    const handleProductByName = (value) => {
        httpClient("products/like?name=" + value).then((res) => setSearchProducts(res?.data));
    };

    const handleCustomerSearch = (value) => {
        httpClient("customers/like?name=" + value).then((res) => setCustomers(res.data));
    };

    const handleProductOnChange = (id) => {
        httpClient("products/" + id).then((res) => {
            let reserveData = cartProducts;
            let check = false;
            reserveData.map((item) => {
                if (item.key == res?.data?.id) {
                    check = true;
                
                }
            });
            if (!check) {
                res.data.maxQuantity = res?.data?.quantity;
                res.data.quantity = 1;
                res.data.key = id;
                reserveData.push(res.data);
                setCartProducts(reserveData);
                setRender(!render);
            }else{
                message.error("Already in the cart")
            }
        });
    };

    return (
        <div>
            <Row gutter={20}>
                <Col span={14}>
                    <Row gutter={20}>
                        <Col span={24} style={{ marginBottom: "20px" }}>
                            <Select
                                showSearch
                                placeholder="Search products by name"
                                optionFilterProp="children"
                                onSearch={handleProductByName}
                                onChange={handleProductOnChange}
                                style={{ width: "100%" }}
                            >
                                {searchProducts.map((item) => (
                                    <Select.Option value={item.id} key={item.id}>
                                        {item.name}
                                    </Select.Option>
                                ))}
                            </Select>
                        </Col>
                        <Col span={24}>
                            <Table
                                pagination={{
                                    defaultPageSize: pageSize,
                                    showSizeChanger: true,
                                    pageSizeOptions: ["10", "20", "30", "50"],
                                    total: totalSize,
                                }}
                                onChange={changePage}
                                columns={columns}
                                dataSource={data}
                            />
                        </Col>
                    </Row>
                </Col>
                <Col span={10}>
                    <div style={{ marginBottom: "10px" }}>
                        <h4>Shopping Cart</h4>
                        <Select
                            placeholder="Search customer by name"
                            showSearch
                            optionFilterProp="children"
                            onSearch={handleCustomerSearch}
                            onChange={(e) => setSelectedCustomer(e)}
                            style={{ width: "100%" }}
                        >
                            {customers.map((item) => (
                                <Select.Option value={item.id} key={item.id}>
                                    {item.username}
                                </Select.Option>
                            ))}
                        </Select>
                    </div>
                    <div className={classes.cart_container}>
                        {cartProducts.length > 0 ? (
                            <>
                                {cartProducts.map((product) => {
                                    return (
                                        <div key={product.key} className={classes.cart_item}>
                                            <div className={classes.cart_item_details}>
                                                <h5>{product.name}</h5>
                                                <div className={classes.cart_item_price}>
                                                    <span>Price: {product.price}</span>
                                                    <span>Discount: {product.discount}</span>
                                                    <span>
                                                        Total:{" "}
                                                        {Math.floor(
                                                            product.price * product.quantity
                                                        )}
                                                    </span>
                                                </div>
                                            </div>
                                            <div className={classes.cart_item_quantity}>
                                                <div>
                                                    <span>Quantity:</span>
                                                    <InputNumber
                                                        style={{ width: "55px" }}
                                                        defaultValue={1}
                                                        type="number"
                                                        max={product.maxQuantity}
                                                        min={1}
                                                        onChange={(e) =>
                                                            handleQuantity(e, product.key)
                                                        }
                                                    />
                                                </div>
                                                <Button
                                                    danger
                                                    onClick={() => removeHandler(product.key)}
                                                >
                                                    X
                                                </Button>
                                            </div>
                                        </div>
                                    );
                                })}

                                <div className={classes.cart_button}>
                                    <div className={classes.cart_button_total}>
                                        Total: <span>${totalPrice}</span>
                                    </div>
                                    <Button disabled={!selectedCustomer} onClick={submitHandler}>
                                        Checkout
                                    </Button>
                                </div>
                            </>
                        ) : (
                            <h2 className={classes.empty_cart}>Empty cart</h2>
                        )}
                    </div>
                </Col>
            </Row>

            <Modal footer={null} open={isVisible} onCancel={() => setIsVisible(false)}>
                Select Payment method:
                <div className={classes.radio_buttons}>
                    <Radio.Group
                        onChange={(e) => {
                            setSelectedMethod(e.target.value);
                            setChange(0);
                        }}
                        className={classes.ant_radio_group_custom}
                        defaultValue={"CASH"}
                        options={[
                            { label: "CART", value: "CART" },
                            { label: "CASH", value: "CASH" },
                        ]}
                        optionType="button"
                    ></Radio.Group>
                </div>
                {selectedMethod == "CASH" && (
                    <div className={classes.pay_cash}>
                        <span>Total: {totalPrice}</span>
                        <InputNumber
                            onChange={(e) => setChange(e)}
                            style={{ width: "80px" }}
                            placeholder="0"
                            // max={totalPrice}
                        />
                        <span>
                            Change: {change > totalPrice ? (change - totalPrice).toFixed(2) : 0}
                        </span>
                    </div>
                )}
                <div className={classes.submit_button}>
                    <Button
                        disabled={!(change >= totalPrice) && selectedMethod == "CASH"}
                        type="primary"
                        onClick={transactionHandler}
                    >
                        SUBMIT
                    </Button>
                </div>
            </Modal>
        </div>
    );
}
