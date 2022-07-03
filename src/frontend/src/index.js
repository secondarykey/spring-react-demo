//import "react-app-polyfill/stable";
import React from 'react';
import ReactDOM from 'react-dom';
import { CookiesProvider } from "react-cookie";

import { BrowserRouter as Router } from "react-router-dom";
import Layout   from './Layout';
import Pages from "./pages/Pages"
import reportWebVitals from './reportWebVitals';

ReactDOM.render(
  <React.StrictMode>
    <CookiesProvider> 
      <Router basename={process.env.PUBLIC_URL}>
        <Layout> 
          <Pages></Pages>
        </Layout>
      </Router>
    </CookiesProvider> 
  </React.StrictMode>,
  document.getElementById('root')
);

reportWebVitals();
