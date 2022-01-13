/**
 * @fileoverview 
 * このファイルは共通のダイアログを提供します。
 */
import React from "react";

import Button from "react-bootstrap/Button";
import Modal from "react-bootstrap/Modal";

var inst;

/**
 * 情報表示
 * @param {string} title タイトル
 * @param {string} message メッセージ
 * @returns {Promise} 押下ボタンのPromise
 */
export function ShowInformation(title,message) {
	return inst.handleShow(title,message,"information");
}
/**
 * 問い合わせ表示
 * @param {string} title タイトル
 * @param {string} message メッセージ
 * @returns {Promise} 押下ボタンのPromise
 */
export function ShowConfirm(title,message) {
	return inst.handleShow(title,message,"confirm");
}

/**
 * ダイアログクラス
 * <pre>
 * 共通的に処理を行う情報（OKのみ）、問い合わせを行うダイアログを提供する。
 * </pre>
 * @example
 * Layoutに埋め込んである為、通常は使用しない。
 * <Dialog/>
 */
class Dialog extends React.Component {

	/**
	 * コンストラクタ
	 * <pre>
	 * 非表示状態で初期化
	 * </pre>
	 * @constructor 
	 * @param {object} props - 特になし
	 */
	constructor(props) {
		super(props)
		inst = this;
		this.state = {
			show : false,
		}
	}

	/**
	 * ダイアログ表示
	 * <pre>
	 * 各ステータスを引数により設定し、表示状態にする
	 * </pre>
	 * @param {string} title タイトル
	 * @param {string} message メッセージ
	 * @param {string} t - ダイアログタイプ
	 * @returns {Promise} Noの場合RejectするPromiseインスタンス
	 */
	handleShow = (title,message,t) => {
		let rtn = new Promise((resolve,reject) => {
		  this.setState({
			  promise : {
				  resolve : resolve,
				  reject : reject,
			  },
			  type:t,
			  title:title,
			  message:message,
			  show:true,
		  });
		})
		return rtn;
	}

	/**
	 * 否定イベント
	 * <pre>
	 * No側を押した場合に発生Promiseのrejectを行い、非表示にする
	 * </pre>
	 */
	handleClose = () => {
		this.state.promise.reject();
		this.setState({show:false})
	}

	/**
	 * 肯定イベント 
	 * <pre>
	 * Yes側を押した場合に発生。Promiseをresolve()する
	 * </pre>
	 */
	handleYes = () => {
		this.state.promise.resolve();
		this.handleClose();
	}

	/**
	 * レンダリング
	 * <pre>
	 * BootstrapのModalを設定
	 * state.titleがタイトル、state.messageに表示文言
	 * state.typeにより、情報、選択の指定、
	 * state.showにより表示を切り替える。
	 * </pre>
	 * @returns Modalタグ
	 */
	render = () => {
	  return (<>
<Modal show={this.state.show} onHide={this.handleClose} centered>

  <Modal.Header closeButton>
	  <Modal.Title>{this.state.title}</Modal.Title>
  </Modal.Header>
	  <Modal.Body>{this.state.message}</Modal.Body>
  <Modal.Footer>

	{ this.state.type === "information" &&
    <Button variant="secondary" onClick={this.handleClose}>
		とじる
    </Button>
	}	

	{ this.state.type === "confirm" &&
	<>
    <Button variant="secondary" onClick={this.handleClose}>
		いいえ
    </Button>
    <Button variant="primary" onClick={this.handleYes}>
		はい
    </Button>
	</>
	}
  </Modal.Footer>
</Modal>
		</>
	)
  }
}  

export default Dialog;