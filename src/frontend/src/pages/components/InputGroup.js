/**
 * @fileoverview 
 * 入力グループを表示するコンポーネント用のファイルです。
 * 入力グループとは
 * 複数選択のデータをテキストボックス内に表示するような場合に利用するものになります。
 */
import React from "react";

import {Container,Row,Col} from "react-bootstrap";

import "../../css/Main.css";

/**
 * インプットグループ
 * <pre>
 * key,value形式のオブジェクトを渡すことで一覧表示を行います。
 * </pre>
 * @example
 * <InputGroup/>
 * 
 * とすることでテキストボックスのような描画になります。
 * set()で対象オブジェクトを個別にボックス表示します。
 * 個々のデータを削除することもできますし、内容のデータを取得することもできます。
 * get(),set()
 */
class InputGroup extends React.Component {

    /**
     * コンストラクタ
     * <pre>
     * dataを空にする
     * </pre>
     * @param {object} props - classNameのみ
     */
    constructor(props) {
        super(props);
        this.state = {
            data: []
        }
    }

    /**
     * データ取得
     * @returns {Array} データの配列
     */
    get = () => {
        var rtn = [];
        this.state.data.forEach( (elm) => {
            rtn.push(elm.key);
        });
        return rtn;
    }

    /**
     * データ設定
     * @param {Array} obj - データ配列
     */
    set = (obj) => {
        this.setState({data:obj});
    }

    /**
     * データの追加
     * @param {string} id - ID(key)
     * @param {string} name - 名称(value)
     */
    add = (id,name) => {
        var data = this.state.data;
        data.push({
            key:id,
            value:name
        });
        this.setState({data:data});
    }

    /**
     * データ削除
     * @param {string} id - 指定ID
     */
    remove = (id) => {
        var data = this.state.data;
        var n = data.filter( (obj) => {
            return id !== obj.key;
        });
        if ( data.length === n.length ) {
            console.log("not found:" + id);
        }
        this.setState({data:n});
    }

    /**
     * レンダリング
     * <pre>
     * divを作成し、内部にデータを元にしたオブジェクトを描画
     * データのボタンクリックを押すとそのデータのみ削除する
     * </pre>
     * @returns input.textライクなdivタグ(className指定)
     */
    render() {
        return (<>
        <div className={this.props.className}>
          <Container>
            <Row>

          { this.state.data.map( (elm,idx) => {
              return <Col key={idx} className="InputGroup-Data">
              <div className="InputGroup-Inner">
              <div className="InputGroup-Value"> {elm.value}({elm.key}) </div>
              <button type="button" className="btn-close InputGroup-Close" aria-label="Close" onClick={() => this.remove(elm.key)}></button>
              </div>
              </Col>
          })
          }
            </Row>
          </Container>
        </div>
        </>);
    }
}

export default InputGroup;