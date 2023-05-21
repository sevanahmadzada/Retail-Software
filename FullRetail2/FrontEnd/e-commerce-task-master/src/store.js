import { createContext, useState } from "react";

const Context = createContext({});

export const ContextProvider = (props) => {
    const contextData = {};

    return <Context.Provider value={contextData}>{props.children}</Context.Provider>;
};

export default Context;
