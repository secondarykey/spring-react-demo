/**
 * @fileoverview 
 * このファイルは共通的に国際化を行う為のファイルです。
 * データファイルは"locale-data"に"言語コード".jsonで存在します。
 */
import React from 'react';
import {FormattedMessage} from "react-intl";
import {createIntl, createIntlCache, RawIntlProvider} from 'react-intl';
import { instanceOf } from "prop-types";
import { withCookies,Cookies } from "react-cookie";
import Select from "./pages/components/Select";
import Package from "../package.json";

import ja from './locale-data/ja.json';
import en from './locale-data/en.json';
import zh from './locale-data/zh.json';

/**
 * メッセージデータを作成する
 * @private
 * @param {string} locale - 指定言語文字列(ja,en,zhなど)
 * @returns jsonデータ
 */
function selectMessages(locale) {
  let lang = locale;
  if ( lang === "default" ) {
     lang = Package.language;
  }
  switch(lang) {
    case 'en': return en;
    case 'ja': return ja;
    case 'zh': return zh;
    default: return en;
  }
}

/**
 * ロケールデータ作成
 * <pre>
 * selectMessages()によりJSONデータを
 * 指定した言語コードで設定する
 * </pre>
 * @param {string} locale - 言語コード
 * @returns react-intlインスタンス
 */
function create(locale) {
    const cache = createIntlCache()
    var intl = createIntl({
      locale,
      messages: selectMessages(locale)
    }, cache);
    return intl;
}

/**
 * 言語切替
 * @private 
 * @param {Event} e - Eventデータ(言語コード)
 */
function changeLanguage(e) {
  inst.setLanguage(e);
}

var inst;

/**
 * ロケールクラス
 * <pre>
 * 国際化用のクラス。現状は言語コードが主。
 * </pre>
 * @example
 * <Locale/> がLayoutに埋め込んであるため、業務で使用することはありません。
 * Locale内に国際化を行うコンポーネントを指定しておく必要があります。
 */
export class Locale extends React.Component {

  static propTypes = {
      cookies : instanceOf(Cookies).isRequired
  };

  /**
   * コンストラクタ
   * <pre>
   * クッキー内から言語を取得して、設定する。
   * 存在しない場合、言語はシステムのデフォルト値を設定しておき、
   * 画面用の設定値としてデフォルトに設定
   * 状態として言語データを設定しておく
   * </pre>
   * @constructor 
   * @param {Cookies} props - Cookieアクセスデータ
   */
  constructor(props) {
      super(props);

      inst = this;
      const { cookies } = this.props;

      let lang = cookies.get("language");
      this.selectLang = lang;
      if ( lang == null ) {
        //TODO config.jsに切り替える必要あり
        lang = Package.language;
        this.selectLang = "default";
      }
      this.state = {intl:create(lang)};
  }

  /**
   * 現在の選択言語を取得
   * @returns 現在の言語コード
   */
  getLanguage = () => {
    return this.selectLang;
  }

  /**
   * 言語クッキーの削除
   */
  remove = () => {
    const { cookies } = this.props;
    cookies.remove('language', { path: '/' });
  }

  /**
   * 言語設定
   * <pre>
   * 指定した言語コードに設定。
   * 言語コードがdefaultの場合、システムのデフォルトを設定
   * 言語コードをクッキーに設定
   * </pre>
   * @param {string} locale - 指定言語コード
   */
  setLanguage = (locale) => {
    var lang = locale;
    if ( locale === "default" ) {
      lang = Package.language;
    }

    this.setState({intl:create(lang)});
    const { cookies } = this.props;
    cookies.set('language', locale, { path: '/' });

    this.selectLang = locale;
  }

  /**
   * レンダリング
   * <pre>
   * 状態に設定されているロケールで子を描画する
   * </pre>
   * @returns 国際化を施した子のエレメント
   */
  render() {
    return  (
      <RawIntlProvider value={this.state.intl}> <> {this.props.children} </> </RawIntlProvider>
    )
  }
}

/**
 * 言語データ取得
 * @private
 * @param {string} id - ID
 * @returns 言語データ
 */
function get(id) {
  return inst.state.intl.formatMessage({id:id});
}

/**
 * メッセージ取得
 * @param {string} id - ID
 * @returns 言語データ
 */
export function GetMessage(id) {
  return get(id);
}

/**
 * ラベル取得
 * @param {string} id - ID
 * @returns 言語データ
 */
export function GetLabel(id) {
  return get(id);
}

/**
 * ラベル取得タグ
 * @param {object} props - id : 言語コード
 * @returns react-intl.FormattedMessageタグ
 * @example
 * <Message id="xxx" />
 * xxxのIDを持つ言語データを描画
 */
export function Label(props) {
  return (
    <FormattedMessage id={props.id}/>
  )
}

/**
 * メッセージ取得タグ
 * @param {object} props - id : 言語コード
 * @returns react-intl.FormattedMessageタグ
 * @example
 * <Message id="xxx" />
 * xxxのIDを持つ言語データを描画
 */
export function Message(props) {
  return (
    <FormattedMessage id={props.id}/>
  )
}

/**
 * 設定言語取得
 * @returns クッキーに設定されている言語コード
 */
export function GetLanguage() {
  return inst.getLanguage();
}

/**
 * 言語設定
 * @param {string} lang - 言語コード
 */
export function SetLanguage(lang) {
  inst.setLanguage(lang);
}

/**
 * 言語クッキー削除
 */
export function RemoveLanguage() {
  inst.remove();
}

/**
 * 言語切替コンポーネント
 * <pre>
 * システムの言語一覧を設定し、言語切替を提供する
 * </pre>
 * @returns 言語切替コンポーネント
 * @example
 * <SelectLanguage/> を行うと言語切り替えのコンポーネントを表示
 */
export function SelectLanguage() {

  let languages = {};
  //TODO config.jsに切り替える
  //TODO 実際のIDに設定を切り替える (あれ？ID化できる？)
  languages["default"] = GetLabel("PRFN00L004");
  languages["en"] = GetLabel("PRFN00L001");
  languages["zh"] = GetLabel("PRFN00L002");
  languages["ja"] = GetLabel("PRFN00L003");

  return (<>
    <FormattedMessage id="PRFN00L103"/>
    <Select values={languages} value={inst.selectLang} onChange={changeLanguage} />
  </>);
}

export default withCookies(Locale);
