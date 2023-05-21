import { message } from "antd";
import axios from "axios";
import { loading, ready } from "../redux/reducers/loader";
import store from "../redux/store";

console.log(store.getState().serviceReducer.serviceType)
export const httpClient = (url, method, data, headers = {}) => {
    const base_url = `https://localhost/${store.getState().serviceReducer.serviceType}/`;
    console.log(store.getState().serviceReducer.serviceType)
    const config = {
        method: method,
        url: base_url + url,
        data: data,
        headers: {
            ...{
                Accept: "application/json",
                // "Accept-Language": getCurrentLang(),
                Authorization: `Bearer ${localStorage.getItem("token")}`,
            },
            ...headers,
        },
    };

    store.dispatch(loading());
    let check = false;
    axios.interceptors.response.use(
        (response) => {
            if (!check) {
                check = true;
            }
            store.dispatch(ready());
            return new Promise((resolve, reject) => {
                resolve(response);
            });
        },
        (error) => {
            if (!check) {
                check = true;
                message.error("Something went wrong...");
                store.dispatch(ready());
            }
            return new Promise((resolve, reject) => {
                reject(error);
            });
        }
    );
    return axios(config);
};
