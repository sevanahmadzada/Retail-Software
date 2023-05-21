import { useEffect, useState } from "react";
import { Space, Table, Tag, Button, Popconfirm } from "antd";
import { Link } from "react-router-dom";
import { httpClient } from "../../config/config";
import { ProfileOutlined } from "@ant-design/icons";
import classes from "./manager.module.css";

export default function Manager() {
    const [products, setProducts] = useState([]);

    const [pageIndex, setPageIndex] = useState(1);
    const [pageSize, setPageSize] = useState(10);
    const [totalSize, setTotalSize] = useState();

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

    const deleteHandler = (id) => {
        httpClient("products/" + id, "delete").then(() => getProducts());
    };

    const columns = [
        {
            title: "Name",
            dataIndex: "name",
            key: "name",
            render: (text) => <a>{text}</a>,
        },
        {
            title: "Type",
            dataIndex: "description",
            key: "description",
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
                    <Link to={`/manager/add-product/${record.key}`}>EDIT</Link>
                    <Popconfirm
                        title={`Delete ${record.name}?`}
                        onConfirm={() => deleteHandler(record?.key)}
                    >
                        <a style={{ color: "red" }}>DELETE</a>
                    </Popconfirm>
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

    return (
        <>
            <div className={classes.top_bar}>
                <h3>Products</h3>

                <Link to="add-product">
                    <Button>
                        Add new products <ProfileOutlined />
                    </Button>
                </Link>
            </div>

            <div>
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
            </div>
        </>
    );
}
