import React from "react";
import {CookiesProvider} from "react-cookie";
import Layout   from '../Layout';
import API from "../API";

const PagesTest = ({children}) => {

  API.testMode();

  return (<>
  <CookiesProvider> 
    <Layout>{children}</Layout>
  </CookiesProvider> 
  </>);
}

export default PagesTest;