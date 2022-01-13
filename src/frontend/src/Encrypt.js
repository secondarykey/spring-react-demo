/**
 * @fileoverview 
 * このファイルは暗号化を提供します。
 */
import AES from "crypto-js/aes";
import CryptJS from "crypto-js";

/**
 * 暗号化クラス
 * <pre>
 * JSONをデータとして暗号、復号を提供する
 * 暗号、復号のアルゴリズム詳細はサーバサイド、または設計書による
 * </pre>
 */
class Encrypt {
    static SECRETKEY = 'aes-256-cbc-text';
    /**
     * 復号化
     * @param {string} buf 対象データ
     * @returns {JSON} 暗号化後のデータ(JSON)
     */
    static decode(buf) {
        if ( buf === undefined || buf === null || buf === "" ) {
            return undefined;
        }
        var bytes = AES.decrypt(buf,this.SECRETKEY);
        var obj = JSON.parse(bytes.toString(CryptJS.enc.Utf8));
        return obj;
    }

    /**
     * 暗号化
     * @param {object} obj - 対象オブジェクト
     * @returns 暗号化した文字列
     */
    static encode(obj) {
        if ( obj === undefined ) {
            return undefined;
        }
        var buf = JSON.stringify(obj);
        var ret = AES.encrypt(buf,this.SECRETKEY);
        return ret.toString();
    }
}

export default Encrypt;