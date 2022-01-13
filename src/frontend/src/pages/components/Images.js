/**
 * @fileoverview 
 * サーバサイド画像表示用のファイル
 */
import React from "react"

import Package from "../../../package.json";

const imageURL = "images/"

/**
 * URLエンドポイント取得
 * @private
 * @returns エンドポイント
 */
function getEndpoint() {
    let home = Package.homepage;
    let endpoint = Package.proxy + home;
    return endpoint;
}

/**
 * 画像URL取得
 * <pre>
 * エンドポイントを利用してURLを作成
 * </pre>
 * @private
 * @param {string} id - 画像ID
 * @returns 画像URL
 */
function getImageURL(id) {
    var endpoint = getEndpoint();
    var url = endpoint + imageURL + id;
    return url;
}

/**
 * 画像コンポーネント
 * <pre>
 * 指定するパスはサーバサイドによる
 * </pre>
 * @example
 * <Images src="faces/AAA" />
 */
class Images extends React.Component {

    /**
     * コンストラクタ
     * @constructor
     * @param {object} props - src : 画像ID(pathイメージ)
     */
    constructor(props) {
        super(props);
        this.src = getImageURL(props.src);
    }

    /**
     * レンダリング
     * <pre>
     * 指定してあるsrc、classNameでimgタグを作成j
     * URLは内部関数getImageURL()により生成
     * </pre>
     * @returns imgタグ
     */
    render() {
        return (<>
          <img src={this.src} alt={this.props.src} className={this.props.className} />
        </>)
    }
}

/**
 * 画像URL取得
 * @param {string} id - 画像ID
 * @returns 画像URL
 */
export function GetImageURL(id) {
    return getImageURL(id);
}

export default Images;