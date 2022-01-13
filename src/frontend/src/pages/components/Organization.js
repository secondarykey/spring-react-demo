/**
 * @fileoverview 
 * 組織選択用のファイルです。
 */
import React from "react";
import Tree from 'rc-tree';
import {Container} from "react-bootstrap";

import FloatInput from "./FloatInput";

/**
 * 組織選択コンポーネント
 * <pre>
 * FloatInputを作成し、指定したツリーデータを描画
 * ツリー選択した値を取得できる
 * </pre>
 * @example
 * 
 * <Organization/>
 * 
 */
class Organization extends React.Component {

  /**
   * コンストラクタ
   * @param {object} props - 指定なし
   */
  constructor(props) {
    super(props);
    this.treeRef = React.createRef();
    this.treeInput = React.createRef();
    this.state = {
      tree : []
    }
  }

  /**
   * ツリー設定
   * <pre>
   * ツリーデータを作成します。
   * </pre>
   * @param {object} tree 
   */
  set = (tree) => {
    this.setState({
      tree : [tree],
      selectKey : tree.key
    });
  }

  /**
   * 選択ID取得
   * @returns 選択ID
   */
  get = () => {
    return this.treeInput.current.ID();
  }

  /**
   * キー設定
   * @param {string} key - ツリーの選択ID
   */
  setKey = (key) => {
    this.setState({
      selectKey : key
    });
  }

  /**
   * ツリー選択時イベント
   * <pre>
   * ツリーデータから選択したデータから取得し
   * 選択したデータの名称をテキストコンポーネントに表示
   * 
   * ツリーを非表示にする
   * </pre>
   * @param {Event} e - イベント
   */
  handleSelect = (e) => {

    var treeData = this.state.tree;
    //TODO データ検索をしやすくしておいた方がいい
    console.log("start:" + e[0]);
    let obj = this.searchTree(treeData,e[0]);
    if ( obj === null ) {
      //ない
      return;
    }


    //親を選択して終了があるならこの条件はいらない
    if ( obj.children === null ){
      var current = this.treeInput.current;
      this.setKey(obj.key);
      current.setValue(obj.key,obj.title);
      current.hide();
    }
  }

  /**
   * ツリー検索(再帰)
   * @param {Array} children - データ配列
   * @param {string} id - 検索IDに
   * @returns 検索したIDのオブジェクト
   */
  searchTree = (children,id) => {
    if ( children === undefined ) {
      return null;
    }
    for ( let idx in children ) {
      var child = children[idx];

      if ( child.key === id ) {
        return child;
      }
      let obj = this.searchTree(child.children,id)
      if ( obj !== null ) {
        return obj;
      }
    }
    return null;
  }

  /**
   * レンダリング
   * <pre>
   * FloatInputを表示し、指定したツリーデータを表示
   * </pre>
   * @returns ツリーデータを表示するFloatInputコンポーネント
   */
  render() {
    return <>
    <FloatInput ref={this.treeInput}>
      <Container style={ { minHeight:"200px"} }>
        <Tree 
          ref={this.treeRef} 
          treeData={this.state.tree} 
          onSelect={(e) => this.handleSelect(e) }
          defaultExpandedKeys={[this.state.selectKey]}
          defaultSelectedKeys={[this.state.selectKey]}
        />
      </Container>
    </FloatInput> 
    </>}
}

export default Organization;