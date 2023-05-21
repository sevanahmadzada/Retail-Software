import { createSlice } from "@reduxjs/toolkit";

const initialState = {
    isLoading: false,
};

const loaderSlice = createSlice({
    name: "loader",
    initialState,
    reducers: {
        loading: (state) => {
            state.isLoading = true;
        },
        ready: (state) => {
            state.isLoading = false;
        },
    },
});

export const { loading, ready } = loaderSlice.actions;
export default loaderSlice.reducer;
