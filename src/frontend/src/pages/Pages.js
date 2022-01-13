/**
 * @fileoverview 
 * 各画面での遷移を記述するファイルです。
 */
import { useEffect } from "react";
import {  Routes,Route,Navigate } from "react-router-dom";

import Login    from './Login';
import Message  from './Message';
import Menu     from './Menu';

/** 以下はデモ用のページ */
import ToDo     from './Demo/Todo/View';
import DateView from './Demo/DateTime/View';
import PlanView from './Demo/Plan/View';
import PlanInput from './Demo/Plan/Input';
import FloatingView from './Demo/Floating/View';
import PagingView from './Demo/Paging/View';
import Dialogs from './Demo/Dialogs/View';
import Operation from './Demo/Operation/View';
import Users from './Demo/UserSearch/View';

import {ChangeTitle,ClearMessage,SetBreadcrumbs} from "../Layout";

import 'bootstrap/dist/css/bootstrap.min.css';
import "../css/Main.css";

/**
 * 全ページRoutes
 * <pre>
 * index.jsから呼び出されるベースのコンポーネント群
 * URLでまとめる場合は別コンポーネントにしてください。
 * </pre>
 * @namespace Pages
 * @returns Routesタグ
 * @example 
 *  Login コンポーネントに飛ばす場合
 *  id に画面IDを指定する
 *  <Route path="/"            element={<Page id="LOGIN"><Login /></Page>} />
 * 
 *  デモ用のURLを追加して他のコンポーネント群を読み込む
 *  <Route path="/pages/demo/*"     element={<DemoPages/>}/>
 * 
 *  該当しないもの（他すべて）はerrorページに飛ばす
 *  <Route path="*" element={<Navigate to="/error/PRFN98M000"/>} />
 */
const Pages =() => {
    return (<>
  <Routes>
    <Route path="/"            element={<Page id="LOGIN"><Login /></Page>} />
    <Route path="/pages/demo/*"     element={<DemoPages/>}/>
    <Route path="/message/:id" element={<Page id="MESSAGE"><Message type="success" /></Page>} />
    <Route path="/error/:id"   element={<Page id="ERROR"><Message type="danger" /></Page>} />
    <Route path="*" element={<Navigate to="/error/PRFN98M000"/>} />
  </Routes>
    </>);
}

/**
 * デモ用ページ群
 * @namespace Pages
 * @param {object} props 
 * @returns Routesタグ
 */
const DemoPages = (props) => {
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

    <Route path="users/" element={
      <Page id="USERS" crumbs={[]}>
        <Users/>
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

/**
 * Page表示
 * <pre>
 * このコンポーネントを利用すると
 * パンくずとタイトルをIDから自動設定します。
 * </pre>
 * @memberOf Pages
 * @param {object} props - 指定プロパティ
 * @returns props.children
 * @example
 *  パンくずは メニュー→真ん中→自分 になりますので
 *  createCrumbs()でidとURLを指定した配列を設定
 *  <Page id="PLANINPUT" crumbs={[createCrumbs("PLAN","/pages/plan/")]}>
 */
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