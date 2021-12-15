import React from 'react';
import ReactDOM from 'react-dom';
import { CookiesProvider } from "react-cookie";
import { BrowserRouter as Router, Routes,Route,Navigate } from "react-router-dom";

import 'bootstrap/dist/css/bootstrap.min.css';
import "./css/Main.css";

import Login    from './pages/Login';
import Message  from './pages/Message';
import Menu     from './pages/Menu';
import ToDo     from './pages/Todo/View';
import DateView from './pages/DateTime/View';
import PlanView  from './pages/Plan/View';
import PlanInput from './pages/Plan/Input';
import Paging from './pages/Paging/View';

import Layout   from './Layout';

import reportWebVitals from './reportWebVitals';

ReactDOM.render(
  <React.StrictMode>
    <CookiesProvider> 
      <Layout> 
        <Router basename={process.env.PUBLIC_URL}>
          <Routes>
            <Route path="/"     element={<Login/>} />
            <Route path="/pages/menu" element={<Menu/>} />
            <Route path="/pages/todo/" element={<ToDo/>} />
            <Route path="/pages/date/" element={<DateView/>} />
            <Route path="/pages/plan/" element={<PlanView/>} />
            <Route path="/pages/plan/input/:date" element={<PlanInput/>} />
            <Route path="/pages/paging/" element={<Paging/>} />
            <Route path="/message/:id" element={<Message type="success" />} />
            <Route path="/error/:id" element={<Message type="danger" />} />
            <Route path="*" element={<Navigate to="/error/PRFN98M000"/>} />
          </Routes>
        </Router>
      </Layout>
    </CookiesProvider> 
  </React.StrictMode>,

  document.getElementById('root')
);

reportWebVitals();
