/**
 * @fileoverview 
 * このファイルは共通的なプログレスバーを描画する為のものです。
 * 主にAPIから呼び出しが行われます。
 */
import React from "react";

import Toast from 'react-bootstrap/Toast';
import Spinner from 'react-bootstrap/Spinner';

var inst;

/**
 * Progressクラス 
 * <pre>
 * プログレスバーを設定するクラスです。
 * 
 * 外部からShow(),Hide()を呼び出します。
 * </pre>
 */
class Progress extends React.Component {

  /**
   * コンストラクタ
   * <pre>
   * 状態を非表示に設定する
   * </pre>
   * @param {object} props - 指定なし
   * @example
   * Layout上にすでに
   *   <Progress/> 
   * て宣言してある為、Show(),Hide()で呼び出すのみです。
   */
  constructor(props) {
    super(props)
    this.state = {
      show : false
    }
    inst = this;
  }

  /**
   * レンダリング関数
   * <pre>
   * スタイルクラス"Progress"の位置にToastを表示する。
   * Spinnerのanimationはborderを指定。
   * </pre>
   * @returns プログレスバー(Toast)
   */
  render() {
return (<>
  <Toast className="Progress" show={this.state.show}>
    <Toast.Body>
      <strong>Loading...</strong>
      <Spinner animation="border"/>
    </Toast.Body>
  </Toast>
</>);
    }
}

/**
 * 表示関数
 * <pre>
 * プログレスバーを表示する
 * </pre>
 * @memberOf Progress
 */
export function Show() {
    inst.setState({show:true})
}
/**
 * 非表示関数
 * <pre>
 * プログレスバーを非表示にする
 * </pre>
 * @memberOf Progress
 */
export function Hide() {
    inst.setState({show:false})
}

export default Progress;
