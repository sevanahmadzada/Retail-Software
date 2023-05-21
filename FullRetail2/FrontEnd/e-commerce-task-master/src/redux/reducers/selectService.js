import { createSlice } from "@reduxjs/toolkit";

const initialState = {
    isDataGenerated: false,
    isMigrated: false,
    isDbSelected: false,
    serviceType: "sql",
};

const serviceSlice = createSlice({
    name: "service",
    initialState,
    reducers: {
        sql: (state) => {
            state.serviceType = "sql";
            state.isDbSelected = true;
        },
        nosql: (state) => {
            state.serviceType = "nosql";
            state.isDbSelected = true;
        },
        generateData: (state) => {
            state.isDataGenerated = true;
        },
        migrateDb: (state) => {
            state.isMigrated = true;
        },
        serviceTypeNull: (state) => {
            state.serviceType = null;
        },
        reset: (state) => {
            state.isDbSelected = false;
            state.isDataGenerated = false;
            state.isMigrated = false;
            state.serviceType = null;
        },
    },
});

export const { sql, nosql, generateData, migrateDb, serviceTypeNull, reset } = serviceSlice.actions;
export default serviceSlice.reducer;
