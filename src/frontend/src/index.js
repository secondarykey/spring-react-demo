import React from 'react';
import reportWebVitals from './reportWebVitals';
import ReactDOM from 'react-dom';
import { CookiesProvider } from "react-cookie";
import { BrowserRouter as Router, Routes,Route } from "react-router-dom";

import 'bootstrap/dist/css/bootstrap.min.css';
import "./css/Main.css";

import Login  from './pages/Login';
import Menu   from './pages/Menu';
import ToDo   from './pages/Todo/View';
import Layout from './Layout';
import Authentication from './Authentication';

ReactDOM.render(
  <React.StrictMode>
    <CookiesProvider> 
      <Authentication />
      <Layout> 
        <Router>
          <Routes>
            <Route path="/"     element={<Login/>} />
            <Route path="/menu" element={<Menu/>} />
            <Route path="/todo" element={<ToDo/>} />
          </Routes>
        </Router>
      </Layout>
    </CookiesProvider> 
  </React.StrictMode>,

  document.getElementById('root')
);

reportWebVitals();
