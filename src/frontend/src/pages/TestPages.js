/**
 * @fileoverview 
 * テスト用のコンポーネントファイル
 */
import React from "react";
import { CookiesProvider } from "react-cookie";
import Layout from '../Layout';
import API from "../API";

/**
 * テスト用のコンポーネント
 * @param {element} children - テスト用のページ
 * @returns クッキーとレイアウトをできるタグを返す
 */
const PagesTest = ({ children }) => {

  API.testMode();
  return (<>
    <CookiesProvider>
      <Layout>{children}</Layout>
    </CookiesProvider>
  </>);
}

export default PagesTest;