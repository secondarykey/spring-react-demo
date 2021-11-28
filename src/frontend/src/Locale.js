import React from 'react';
import {FormattedMessage} from "react-intl";
import {createIntl, createIntlCache, RawIntlProvider} from 'react-intl';
import { instanceOf } from "prop-types";
import { withCookies,Cookies } from "react-cookie";

import Dropdown  from 'react-bootstrap/Dropdown';

import ja from './locale-data/ja.json';
import en from './locale-data/en.json';
import zh from './locale-data/zh.json';

function selectMessages(locale) {
  switch(locale) {
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
  inst.set(e);
}

class Locale extends React.Component {

  static propTypes = {
      cookies : instanceOf(Cookies).isRequired
  };

  constructor(props) {
      super(props);
      inst = this;
      const { cookies } = this.props;
      let lang = cookies.get("language");
      if ( lang == null ) {
        lang = navigator.language;
      }

      this.state = {intl:create(lang)};
  }

  set = (locale) => {
      this.setState({intl:create(locale)});
      const { cookies } = this.props;
      cookies.set('language', locale, { path: '/' });
  }

  render() {
    return  (
      <RawIntlProvider value={this.state.intl}> <> {this.props.children} </> </RawIntlProvider>
    )
  }
}

export function SelectLanguage() {
    return (
<Dropdown onSelect={changeLanguage}>
  <Dropdown.Toggle variant="secondary" id="dropdown-basic">
    <FormattedMessage id="PRFN00L103"/>
  </Dropdown.Toggle>
          
  <Dropdown.Menu>
    <Dropdown.Item eventKey="en">
      <FormattedMessage id="PRFN00L001"/>
    </Dropdown.Item>
    <Dropdown.Item eventKey="zh">
      <FormattedMessage id="PRFN00L002"/>
    </Dropdown.Item>
    <Dropdown.Item eventKey="ja">
      <FormattedMessage id="PRFN00L003"/>
    </Dropdown.Item>
  </Dropdown.Menu>
</Dropdown>
);
}

export default withCookies(Locale);
