//import "react-app-polyfill/stable";
import React from 'react';
import { createRoot } from 'react-dom';
import { CookiesProvider } from "react-cookie";

import { BrowserRouter as Router } from "react-router-dom";
import Layout   from './Layout';
import Util   from './Util';
import Pages from "./pages/Pages"
import reportWebVitals from './reportWebVitals';

var config = document.createElement("script");
config.src = Util.serverURL("/client/config.js");

class App extends React.Component {
  render() {
    return (
    <React.StrictMode>
      <CookiesProvider> 
        <Router basename={process.env.PUBLIC_URL}>
          <Layout> 
            <Pages></Pages>
          </Layout>
        </Router>
      </CookiesProvider> 
    </React.StrictMode>
    );
  }
}

config.onload = function() {
  const container = document.getElementById("root");
  const root = createRoot(container);
  root.render(<App/>);
  reportWebVitals();
}

config.onerror = function() {
  const container = document.getElementById("root");
  container.innerHTML = "<h1>setting load error.</h1>";
  console.log(config.src);
}

document.head.appendChild(config);