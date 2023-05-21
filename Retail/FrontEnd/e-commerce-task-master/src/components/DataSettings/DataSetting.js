import { Button, message } from "antd";
import axios from "axios";
import React, { useEffect } from "react";
import { useSelector } from "react-redux";
import { Link } from "react-router-dom";
import { httpClient } from "../../config/config";
import { loading, ready } from "../../redux/reducers/loader";
import { generateData, migrateDb, nosql, reset, sql } from "../../redux/reducers/selectService";
import store from "../../redux/store";
import classes from "./dataSetting.module.css";

export default function DataSetting() {
    const isDataGenerated = useSelector((state) => state.serviceReducer.isDataGenerated);
    const isMigrated = useSelector((state) => state.serviceReducer.isMigrated);

    useEffect(() => {
        store.dispatch(loading());
        httpClient("employees/top5").then((res) => {
            if (res?.data?.length > 0) {
                store.dispatch(generateData());

                axios
                    .get("https://localhost/nosql/transactions/monthlyTurnover")
                    .then(() => {
                        store.dispatch(migrateDb());
                        store.dispatch(ready());
                    })
                    .catch(() => store.dispatch(ready()));
            }
        });
    }, []);

    const handleDataGenerate = () => {
        store.dispatch(loading());
        axios
            .get("https://localhost/data/generation")
            .then(() => {
                store.dispatch(generateData());
                store.dispatch(ready());
            })
            .catch(() => {
                message.error("Something went wrong :(");
                store.dispatch(ready());
            });
    };
    const handleMigrate = () => {
        store.dispatch(loading());
        axios
            .post("https://localhost/data/migration")
            .then(() => {
                store.dispatch(migrateDb());
                store.dispatch(ready());
            })
            .catch(() => {
                message.error("Something went wrong :(");
                store.dispatch(ready());
            });
    };

    const handleReset = () => {
        store.dispatch(reset());
    };

    return (
        <div className={classes.initial_page}>
            <div className={classes.bg_image}></div>
            <div className={classes.mid}>
                <h1>Data Settings</h1>
                <div>
                    <Button
                        onClick={handleDataGenerate}
                        type="primary"
                        className={classes.initial_page_buttons}
                        disabled={isDataGenerated}
                    >
                        Generate Data
                    </Button>
                    <Button
                        disabled={!isDataGenerated }
                        type="primary"
                        className={classes.initial_page_buttons}
                        onClick={handleMigrate}
                    >
                        Migrate DB
                    </Button>
                </div>
                <h3>Continue with:</h3>
                <div>
                    <Link to={!isDataGenerated ? "" : "/home"}>
                        <Button
                            onClick={() => store.dispatch(sql())}
                            disabled={!isDataGenerated}
                            className={classes.initial_page_buttons1}
                        >
                            SQL
                        </Button>
                    </Link>
                    <Link to={!isMigrated ? "" : "/home"}>
                        <Button
                            onClick={() => store.dispatch(nosql())}
                            disabled={!isMigrated}
                            className={classes.initial_page_buttons1}
                        >
                            NO-SQL
                        </Button>
                    </Link>
                </div>
                <Button style={{ marginTop: "20px" }} onClick={handleReset}>
                    RESET
                </Button>
            </div>
        </div>
    );
}
