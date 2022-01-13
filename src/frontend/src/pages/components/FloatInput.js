/**
 * @fileoverview 
 * テキストコンポーネントからポップアップして表示するコンポーネント用のファイル
 */
import React from "react";

import {Form} from "react-bootstrap";

import "../../css/Main.css";

/**
 * 浮動入力コンポーネント
 * <pre>
 * テキストを描画し、テキストを入力する時に指定したコンポーネントを描画する
 * </pre>
 * @example
 * <FloatInput>
 * aaa
 * </FloatInput>
 * とした場合、描画されたテキストを選択すると「aaa」が表示される
 */
class FloatInput extends React.Component {

    /**
     * コンストラクタ
     * <pre>
     * 使用するコンポーネントを初期化
     * </pre>
     * @constructor
     * @param {object} props - value,values
     */
    constructor(props) {
        super(props);
        this.state = {
            float : false
        }
        this.input = React.createRef();
        this.float = React.createRef();
        this.inputId = props.value;
        this.firstShow = false;
    }

    /**
     * 設定
     * @param {string} id - ID
     * @param {string} name - 表示名称
     */
    setValue = (id,name)  => {
        this.input.current.value = name;
        this.inputId = id;
    }

    /**
     * ID取得
     * @returns 指定されているID
     */
    ID = ()  => {
        return this.inputId;
    }

    /**
     * 浮動コンポーネント表示
     * <pre>
     * テキスト下のコンポーネントを表示状態にする
     * document.clickを監視
     * </pre>
     */
    show() {
        this.setState({
            float:true
        })
        this.firstShow = true;
        document.addEventListener("click",this.handleDocumentClick);
    }

    /**
     * 浮動コンポーネント非表示
     * <pre>
     * 浮動コンポーネントを非表示にし、
     * 表示を取り消すように準備したイベントを取り消す
     * </pre>
     */
    hide() {
        this.setState({
            float:false
        })
        document.removeEventListener("click",this.handleDocumentClick);
    }

    /**
     * 入力用のテキストイベント
     * <pre>
     * 浮動コンポーネントを表示する
     * </pre>
     * @param {Event} e - クリックイベント
     */
    handleInputFocus = (e) => {
        this.show();
    }

    /**
     * <pre>
     * その他コンポーネントをクリックした場合、
     * 浮動コンポーネントじゃない場合、非表示にする
     * ※最初の描画の場合はOKにする
     * </pre>
     * @param {Event} e - クリックイベント 
     */
    handleDocumentClick = (e) => {
        if ( this.float.current.contains(e.target) ) {
            return;
        }
        if ( this.firstShow && this.input.current.contains(e.target) ) {
            this.firstShow = false;
            return;
        }
        this.hide();
    }

    /**
     * レンダリング
     * <pre>
     * 入力用のテキストと
     * 非表示状態の浮動コンポーネント（内部のコンポーネント）を作成
     * show()などで表示する。
     * 
     * 浮動コンポーネント側からhide()などを呼び出してコントロールする
     * </pre>
     * @returns 入力用Form.Control
     */
    render() {

        let floatStyle = {}
        //TODO 本当はボーダーもコピーしたい
        if ( this.state.float ) {
          floatStyle = {
            width: this.input.current.offsetWidth,
            zIndex: this.input.current.style.zIndex + 10
          };
        }
        
        return (<>
        <Form.Control type="text" readOnly placeholder={this.props.placeholder} ref={this.input} onFocus={ (e) => this.handleInputFocus(e)}/>
        { this.state.float &&
        <div className="FloatComponent" style={floatStyle} ref={this.float}>
            {this.props.children}
        </div>
        }
        </>)
    }
}

export default FloatInput;