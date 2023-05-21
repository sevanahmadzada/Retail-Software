import { useEffect, useState } from "react";
import { Button, Table } from "antd";
import { ShopOutlined, RiseOutlined, SolutionOutlined } from "@ant-design/icons";
import { httpClient } from "../../config/config";
import classes from "./home.module.css";
import { useSelector } from "react-redux";
import store from "../../redux/store";
import { generateData, migrateDb, nosql, sql } from "../../redux/reducers/selectService";
import axios from "axios";
import { loading, ready } from "../../redux/reducers/loader";

export default function Home() {
    const [products, setProducts] = useState([]);
    const [employees, setEmployees] = useState([]);
    const [customerNumber, setCustomerNumber] = useState();
    const [sumOfProducts, setSumOfProducts] = useState();
    const [monthlyTurnover, setMonthlyTurnover] = useState();

    const isDbSelected = useSelector((state) => state.serviceReducer.isDbSelected);
    const serviceType = useSelector((state) => state.serviceReducer.serviceType);

    useEffect(() => {
        store.dispatch(loading())
        if (isDbSelected && serviceType != null) {
            console.log(serviceType);
            const getTop10 = httpClient("products/top10", "get");
            const getTop5Employees = httpClient("employees/top5", "get");
            const getCustomers = httpClient("customers/number", "get");
            const getSumOfProducts = httpClient("products/sumOfProducts", "get");
            const getTransactionsMonthlyTurnover = httpClient(
                "transactions/monthlyTurnover",
                "get"
            );
            Promise.all([
                getTop10,
                getCustomers,
                getSumOfProducts,
                getTransactionsMonthlyTurnover,
                getTop5Employees,
            ]).then((res) => {
                setProducts(res[0].data);
                setCustomerNumber(res[1].data);
                setSumOfProducts(res[2].data);
                setMonthlyTurnover(res[3].data);
                setEmployees(res[4].data);
                store.dispatch(ready())
            });
        }
    }, [isDbSelected]);

    const EmpColumns = [
        {
            title: "Name",
            dataIndex: "name",
            key: "name",
        },
        {
            title: "Surname",
            dataIndex: "surname",
            key: "surname",
        },
        {
            title: "Department Name",
            dataIndex: "departmentName",
            key: "departmentName",
        },
    ];

    const columns = [
        {
            title: "Name",
            dataIndex: "name",
            key: "name",
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
    ];
    const data = products.map((item) => {
        return {
            key: item.id,
            name: item.productName,
            description: item.description,
            price: item.price,
        };
    });
    const empData = employees.map((item) => {
        return {
            key: item.id,
            name: item.name,
            surname: item.surname,
            departmentName: item.departmentName,
        };
    });
    console.log(serviceType);

    return (
        <div className={classes.home_main}>
            <div className={classes.top_bar}>
                <div className={classes.bar_item}>
                    <div className={classes.bar_item_text}>
                        <span>Sum of Product Price</span> <span>$ {sumOfProducts}</span>
                    </div>
                    <ShopOutlined className={classes.bar_item_icon} />
                </div>
                <div className={classes.bar_item}>
                    <div className={classes.bar_item_text}>
                        <span>Montly Turnover</span> <span>$ {monthlyTurnover}</span>
                    </div>
                    <RiseOutlined className={classes.bar_item_icon} />
                </div>
                <div className={classes.bar_item}>
                    <div className={classes.bar_item_text}>
                        <span>Total Customer</span> <span>{customerNumber}</span>
                    </div>
                    <SolutionOutlined className={classes.bar_item_icon} />
                </div>
            </div>

            <div className={classes.table_area}>
                <div>
                    <h3>TOP 10 Best Selling Products</h3>
                    <Table columns={columns} dataSource={data} pagination={false} />
                </div>
                <div>
                    <h3>TOP 5 Employees</h3>
                    <Table columns={EmpColumns} dataSource={empData} pagination={false} />
                </div>
            </div>
        </div>
    );
}
