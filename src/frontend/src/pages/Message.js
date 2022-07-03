/**
 * @fileoverview 
 * メッセージ画面用のファイル
 */
import React from "react";
import { Link } from "react-router-dom";

import { withRouter, WriteMessage } from "../Layout";
import { RemoveSession } from "../Authentication";

/**
 * メッセージ画面
 * <pre>
 * メッセージ画面をメッセージのみを表示するページです。
 * 
 * 基本的にはログアウトやリフレッシュ、NotFoundなどで利用します。
 * </pre>
 */
class Message extends React.Component {

    /**
     * コンストラクタ
     * @param {object} props - id,type
     */
    constructor(props) {
        super(props);
        if (props.id !== undefined) {
            WriteMessage(props.id, props.type);
        } else if (props.params !== undefined) {
            var id = props.params.id
            WriteMessage(id, props.type);
        }
    }


    /**
     * コンポーネントマウント
     * <pre>
     * クッキーデータをすべて削除
     * </pre>
     */
    componentDidMount() {
        //Remove();
        //RemoveLanguage();
        RemoveSession();
    }

    /**
     * レンダリング
     * <pre>
     * 現状メッセージとログインのリンクを表示予定
     * </pre>
     * @returns Linkタグ
     */
    render() {
        return (<>
            <Link to="/">ログインする</Link>
        </>)
    }
}

export default withRouter(Message);
