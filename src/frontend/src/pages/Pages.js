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
import Operation from './Operation/View';

import {ChangeTitle,ClearMessage,SetBreadcrumbs} from "../Layout";

import 'bootstrap/dist/css/bootstrap.min.css';
import "../css/Main.css";
import { useEffect } from "react";

const Pages =() => {
    return (<>
  <Routes>
    <Route path="/"            element={<Page id="LOGIN"><Login /></Page>} />
    <Route path="/pages/*"     element={<AuthenticationPages/>}/>
    <Route path="/message/:id" element={<Page id="MESSAGE"><Message type="success" /></Page>} />
    <Route path="/error/:id"   element={<Page id="ERROR"><Message type="danger" /></Page>} />
    <Route path="*" element={<Navigate to="/error/PRFN98M000"/>} />
  </Routes>
    </>);
}
const AuthenticationPages = (props) => {
    return (<>
  <Routes>
    <Route path="menu" element={
      <Page id="MENU" crumbs={[]}>
        <Menu />
      </Page>
    }/>
    <Route path="todo/" element={
      <Page id="TODO" crumbs={[]}>
        <ToDo/>
      </Page>
    }/>
    <Route path="date/" element={
      <Page id="DATE" crumbs={[]}>
        <DateView/>
      </Page>
    }/>
    <Route path="floating/" element={
      <Page id="FLOATING" crumbs={[]}>
        <FloatingView/>
      </Page>
    }/>
    <Route path="plan/" element={
      <Page id="PLAN" crumbs={[]}>
        <PlanView/>
      </Page>
    }/>
    <Route path="plan/input/:date" element={
      <Page id="PLANINPUT" crumbs={[createCrumbs("PLAN","/pages/plan/")]}>
        <PlanInput/>
      </Page>
    }/>
    <Route path="paging/" element={
      <Page id="PAGING" crumbs={[]}>
        <PagingView/>
      </Page>
    }/>
    <Route path="dialogs/" element={
      <Page id="DIALOGS" crumbs={[]}>
        <Dialogs/>
      </Page>
    }/>

    <Route path="operation/" element={
      <Page id="OPERATION" crumbs={[]}>
        <Operation/>
      </Page>
    }/>

  </Routes>
  </>);
}

const createCrumbs = (id,link) => {
  var rtn = {};
  rtn.id = id;
  rtn.link = link;
  return rtn;
}

const Page = (props) => {

  let crumbs = [createCrumbs("MENU","/pages/menu")];
  let url = document.location.href;

  //メニューかを判定
  if ( url.indexOf("/pages/menu") === -1 ) {
    let target = props.crumbs;
    if ( target !== undefined ) {
      crumbs = crumbs.concat(target);
    }
    //自分自身を設定
    crumbs.push(createCrumbs(props.id,props.link));
  }

  useEffect(() => {
    ClearMessage();
    ChangeTitle(props.id);
    SetBreadcrumbs(crumbs);
  })
  return (<>{props.children}</>)
}

export default Pages;