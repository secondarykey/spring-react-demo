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

function create(locale) {
    const cache = createIntlCache()
    var intl = createIntl({
      locale,
      messages: selectMessages(locale)
    }, cache);
    return intl;
}

var inst;
function changeLanguage(e) {
  inst.setLanguage(e);
}

export class Locale extends React.Component {

  static propTypes = {
      cookies : instanceOf(Cookies).isRequired
  };

  constructor(props) {

      super(props);

      inst = this;
      const { cookies } = this.props;

      let lang = cookies.get("language");
      this.selectLang = lang;
      if ( lang == null ) {
        lang = Package.language;
        this.selectLang = "default";
      }
      this.state = {intl:create(lang)};
  }

  getLanguage = () => {
    return this.selectLang;
  }

  remove = () => {
    const { cookies } = this.props;
    cookies.remove('language', { path: '/' });
  }

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

  render() {
    return  (
      <RawIntlProvider value={this.state.intl}> <> {this.props.children} </> </RawIntlProvider>
    )
  }
}

function get(id) {
  return inst.state.intl.formatMessage({id:id});
}

export function GetMessage(id) {
  return get(id);
}

export function GetLabel(id) {
  return get(id);
}

export function Label(props) {
  return (
    <FormattedMessage id={props.id}/>
  )
}

export function Message(props) {
  return (
    <FormattedMessage id={props.id}/>
  )
}

export function GetLanguage() {
  return inst.getLanguage();
}

export function SetLanguage(lang) {
  return inst.setLanguage(lang);
}

export function RemoveLanguage() {
  return inst.remove();
}

export function SelectLanguage() {

  let languages = {};
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
