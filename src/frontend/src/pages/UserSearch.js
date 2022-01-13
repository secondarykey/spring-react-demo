/**
 * @fileoverview 
 * ユーザ検索用のダイアログファイル
 */
import React from "react";
import { Container, Table, Button, Form, Row, Col } from "react-bootstrap";
import Modal from "react-bootstrap/Modal";
import Select from "./components/Select"
import Paging from "./components/Paging"
import API from "../API"
import { WriteErrorMessage } from "../Layout";

/**
 * ユーザ件s買うダイアログ
 * @example
 * <UserSearch onSelect={func}/>
 */
class UserSearch extends React.Component {

  /**
   * コンストラクタ
   * @param {object} props 
   */
  constructor(props) {
    super(props);

    this.state = {
      show: false,
      mode: "view",
      data: [],
      checked: [],
      paging: {
        currentPage: 1,
        numberOfDisplay: 5
      }
    }

    this.userId = React.createRef();
    this.userName = React.createRef();
    this.date = props.date;
    this.belong = props.belong;

    this.handleClose = this.handleClose.bind(this);
    this.handleChangeDisplay = this.handleChangeDisplay.bind(this);
    this.handleSelect = this.handleSelect.bind(this);
    this.handleSearch = this.handleSearch.bind(this);
    this.search = this.search.bind(this);
  }

  /**
   * 表示初期化
   * @param {boolean} show - 表示フラグ
   * @param {string} mode - 表示モード
   * @param {Array} checked - 選択データ
   */
  clear(show, mode = "view", checked = []) {
    var limitValues = [];
    limitValues[5] = 5;
    limitValues[10] = 10;
    limitValues[100] = 100;
    this.setState({
      show: show,
      mode: mode,
      data: [],
      checked: checked,
      paging: {
        currentPage: 1,
        numberOfDisplay: 5
      },
      limitValues: limitValues
    });
  }

  /**
   * 選択データ取得
   * @returns 選択データ 
   */
  get() {
    return this.state.checked;
  }

  /**
   * ダイアログ表示
   * @param {string} mode - モード
   */
  show(mode) {
    this.clear(true, this.props.mode);
    this.search(this.state.paging);
  }

  /**
   * ダイアログ非表示
   */
  hide() {
    this.setState({ show: false });
  }

  /**
   * 閉じるイベント
   */
  handleClose() {
    this.hide();
  }

  /**
   * 検索
   * @param {object} paging - ページングデータ
   */
  search(paging) {

    if (paging === undefined) {
      paging = {
        currentPage: 1,
        numberOfDisplay: 5
      }
    }

    var id = "";
    var name = "";

    var idObj = this.userId.current;
    if (idObj !== null) {
      id = idObj.value;
    }
    var nameObj = this.userName.current;
    if (nameObj !== null) {
      name = nameObj.value;
    }

    var args = {
      id: id,
      name: name,
      paging: paging
    }

    API.post("/api/demo/users/search", resp => {
      let rtn = resp.data.result;
      this.setState({
        data: rtn.users,
        paging: rtn.paging
      })
      var rows = document.querySelector("#rowdata");
      var boxes = rows.querySelectorAll("input.userSelect");

      boxes.forEach((elm) => {
        elm.checked = false;
        this.state.checked.forEach((e) => {
          if (e.id === elm.getAttribute("data-id")) {
            elm.checked = true;
          }
        })
      })
    }, args).catch((err) => {
      if (API.isUnknownError(err)) {
        return;
      }
      WriteErrorMessage(err);
    });
  }

  /**
   * 検索クリックイベント
   */
  handleSearch() {
    this.search();
  }

  /**
   * 表示数の変更イベント
   * @param {number} p 指定表示数
   */
  handleChangeDisplay(p) {
    var paging = this.state.paging;
    paging.numberOfDisplay = p;
    paging.currentPage = 1;
    this.search(paging);
  }

  /**
   * ページ番号変更
   * @param {number} p - ページ番号
   */
  handlePageClick(p) {
    var paging = this.state.paging;
    paging.currentPage = p;

    //全選択をクリア
    var all = document.querySelector("#allSelect");
    all.checked = false;

    this.search(paging);
  }

  /**
   * 選択クリックイベント
   */
  handleSelect() {
    var func = this.props.onSelect;
    if (func !== undefined) {
      func(this.state.checked);
    }
    this.hide();
  }

  /**
   * チェックボックス選択
   */
  handleCheck(e, elm) {
    var check = this.getCheck(e.target)
    if (e.target !== check) {
      e.preventDefault();
      check.checked = !check.checked;
    }
    var checked = this.state.checked;
    if (check.checked) {
      checked.push(elm);
    } else {
      checked.filter((f) => {
        if (f.id === elm.id) {
          return false;
        }
        return true;
      })
    }
    this.setState({ checked: checked });
  }

  /**
   * 全選択チェック
   */
  handleAllCheck(e) {
    var check = this.getCheck(e.target);
    if (e.target !== check) {
      e.preventDefault();
      check.checked = !check.checked;
    }

    var rows = document.querySelector("#rowdata");
    var boxes = rows.querySelectorAll("input.userSelect");
    boxes.forEach((elm) => {
      elm.checked = check.checked;
    })

    var data = this.state.data;
    var checked = this.state.checked;
    checked = checked.filter((f) => {
      var rtn = true;
      data.forEach((g) => {
        if (g.id === f.id) {
          rtn = false;
        }
      })
      return rtn;
    });

    if (check.checked) {
      data.forEach((e) => {
        checked.push(e);
      })
    }
    this.setState({ checked: checked });
  }

  /**
   * 選択コンポーネントの取得
   * @param {element} elm - イベント発生オブジェクト 
   * @returns 選択したInputタグ
   */
  getCheck(elm) {
    var target = elm;
    while (true) {
      var input = target.querySelector("input");
      if (input === null) {
        target = target.parentElement;
      } else {
        return input;
      }
    }
  }

  /**
   * レンダリング
   * <pre>
   * ステータスにより表示、非表示を切り替える
   * 選択時、指定されているデータを呼び出し側で処理する
   * チェックデータの処理は各イベントによる
   * </pre>
   * @returns ユーザ検索用のModal 
   */
  render() {
    return (<>
      <Modal dialogClassName="UserSearch" show={this.state.show} onHide={this.handleClose} centerd="true" backdrop="static">
        <Modal.Header closeButton>
          <Modal.Title as="h6">User Search</Modal.Title>
        </Modal.Header>

        <Modal.Body>
          <Form className="d-flex">
            <Row>
              <Col xs="1" className="d-flex align-items-center">
                <Form.Label> ID: </Form.Label>
              </Col>
              <Col xs="4">
                <Form.Control type="text" ref={this.userId} />
              </Col>
              <Col xs="1" className="d-flex align-items-center">
                <Form.Label> Name: </Form.Label>
              </Col>
              <Col xs="4">
                <Form.Control type="text" ref={this.userName} />
              </Col>
              <Col xs="2">
                <Button onClick={this.handleSearch}>Search</Button>
              </Col>
            </Row>
          </Form>

          <Table className="table-striped table-hover">
            <thead>
              <tr>
                <th className="small small-line">
                  <div className="form-check">
                    <input id="allSelect" className="form-check-input" type="checkbox" onClick={(e) => this.handleAllCheck(e)} />
                  </div>
                </th>
                <th className="small small-line">ID</th>
                <th className="small small-line">Name</th>
              </tr>
            </thead>
            <tbody id="rowdata">
              {this.state.data.map((elm, idx) => {
                return (
                  <tr key={idx} onClick={(e) => this.handleCheck(e, elm)}>
                    <td className="small small-line">
                      <div className="form-check">
                        <input data-id={elm.id} className="form-check-input userSelect" type="checkbox" />
                      </div>
                    </td>
                    <td className="small small-line">{elm.id}</td>
                    <td className="small small-line">{elm.name}</td>
                  </tr>
                )
              })}
            </tbody>
          </Table>

          <Container style={{ display: "flex" }}>
            <Select className="Paging-Limit" values={this.state.limitValues} value={this.state.paging.numberOfDisplay} onChange={(v) => this.handleChangeDisplay(v)} />
            <Paging paging={this.state.paging} max={5} onClick={(p) => this.handlePageClick(p)} />
          </Container>

        </Modal.Body>

        <Modal.Footer>
          <Button variant="secondary" onClick={this.handleClose}>Close</Button>
          <Button variant="primary" onClick={this.handleSelect}>SELECT</Button>
        </Modal.Footer>

      </Modal>
    </>)
  }
}

export default UserSearch;