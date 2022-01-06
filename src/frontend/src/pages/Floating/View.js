import React, { createRef } from "react";
import Tree from 'rc-tree';
import {Container} from "react-bootstrap";

import FloatInput from "../components/FloatInput";
import 'rc-tree/assets/index.css';

const treeData = [
  {
    key: '0-0',
    title: 'parent 1',
    children: [
      { key: '0-0-0', title: 'parent 1-1', children: [{ key: '0-0-0-0', title: 'parent 1-1-0' }] },
      {
        key: '0-0-1',
        title: 'parent 1-2',
        children: [
          { key: '0-0-1-0', title: 'parent 1-2-0', disableCheckbox: true },
          { key: '0-0-1-1', title: 'parent 1-2-1' },
          { key: '0-0-1-2', title: 'parent 1-2-2' },
          { key: '0-0-1-3', title: 'parent 1-2-3' },
          { key: '0-0-1-4', title: 'parent 1-2-4' },
          { key: '0-0-1-5', title: 'parent 1-2-5' },
          { key: '0-0-1-6', title: 'parent 1-2-6' },
          { key: '0-0-1-7', title: 'parent 1-2-7' },
          { key: '0-0-1-8', title: 'parent 1-2-8' },
          { key: '0-0-1-9', title: 'parent 1-2-9' },
          { key: 1128, title: 1128 },
        ],
      },
    ],
  },
];

class View extends React.Component {

    constructor(props) {
        super(props)
        this.treeRef = createRef();
        this.treeInput =createRef();
    }


    handleSelect = (e) => {

        //TODO データ検索をしやすくしておいた方がいい
        console.log("start");
        let obj = this.searchTree(treeData,e[0]);
        if ( obj === null ) {
            //ない
            return;
        }

        //親を選択して終了があるならこの条件はいらない
        if ( obj.children === undefined ){
            var current = this.treeInput.current;
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

        return (<>
        <FloatInput>
            <Container>
                AAa <br/> BBB
            </Container>
        </FloatInput>

        <div>Other Component</div>

        <FloatInput ref={this.treeInput}>
          <Container style={ { minHeight:"200px"} }>
              <Tree 
                ref={this.treeRef} 
                treeData={treeData} 
                onSelect={(e) => this.handleSelect(e) }
                defaultExpandedKeys={["0-0"]}
                defaultSelectedKeys={["0-0"]}
                />
          </Container>
        </FloatInput>

        </>)
    }
}

export default View;