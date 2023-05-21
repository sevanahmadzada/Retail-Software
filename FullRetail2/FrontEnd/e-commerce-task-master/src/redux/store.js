import { configureStore } from "@reduxjs/toolkit";
import loaderReducer from "./reducers/loader";
import serviceReducer from "./reducers/selectService";
import authReducer from "./reducers/authService";

export default configureStore({
    reducer: { loaderReducer, serviceReducer, authReducer },
});
