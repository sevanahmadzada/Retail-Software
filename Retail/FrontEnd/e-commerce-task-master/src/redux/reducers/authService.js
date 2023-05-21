import { createSlice } from "@reduxjs/toolkit";

const initialState = {
    isLoggedIn: false,
    role: null, // manager, cashier
    access_token: null,
    userId: null,
};

const authService = createSlice({
    name: "auth",
    initialState,
    reducers: {
        handleLogin: (state, action) => {
            state.access_token = action.payload.access_token;
            state.role = action.payload.role;
            state.userId = action.payload.userId;
            state.isLoggedIn = true;
        },
        handleLogout: (state) => {
            state.access_token = null;
            state.role = null;
            state.isLoggedIn = false;
            state.userId = null;
        },
    },
});

export const { handleLogin, handleLogout } = authService.actions;
export default authService.reducer;
