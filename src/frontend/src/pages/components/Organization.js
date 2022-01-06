import React from "react";
import Tree from 'rc-tree';
import {Container} from "react-bootstrap";

import FloatInput from "./FloatInput";

class Organization extends React.Component {

  constructor(props) {
    super(props);
    this.treeRef = React.createRef();
    this.treeInput = React.createRef();
    this.state = {
      tree : []
    }
  }

  set = (tree) => {
    this.setState({
      tree : [tree],
      selectKey : tree.key
    });
  }

  setKey = (key) => {
    this.setState({
      selectKey : key
    });
  }

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