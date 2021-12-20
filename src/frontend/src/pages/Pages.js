import {  Routes,Route,Navigate } from "react-router-dom";

import Login    from './Login';
import Message  from './Message';
import Menu     from './Menu';
import ToDo     from './Todo/View';
import DateView from './DateTime/View';
import PlanView from './Plan/View';
import PlanInput from './Plan/Input';
import FloatingView from './Floating/View';
import PagingView from './Paging/View';
import Dialogs from './Dialogs/View';

import 'bootstrap/dist/css/bootstrap.min.css';
import "../css/Main.css";

const Pages =() => {
    return (<>
  <Routes>
    <Route path="/"     element={<Login/>} props={ { title:"LoginPageID" } } />
    <Route path="/pages/menu" element={<Menu/>} title="MenuPageID"/>
    <Route path="/pages/todo/" element={<ToDo/>} title="ToDoPageID"/>
    <Route path="/pages/date/" element={<DateView/>} title="SampleDateID"/>
    <Route path="/pages/floating/" element={<FloatingView/>} title="Floating"/>
    <Route path="/pages/plan/" element={<PlanView/>} title="Paging"/>
    <Route path="/pages/plan/input/:date" element={<PlanInput/>} title="Paging"/>
    <Route path="/pages/paging/" element={<PagingView/>} title="Paging"/>
    <Route path="/pages/dialogs/" element={<Dialogs/>} title="Dialogs"/>
    <Route path="/message/:id" element={<Message type="success" />} title="Message"/>
    <Route path="/error/:id" element={<Message type="danger" />} title="Error"/>
    <Route path="*" element={<Navigate to="/error/PRFN98M000"/>} title="NotFound"/>
  </Routes>
    </>);
}

export default Pages;