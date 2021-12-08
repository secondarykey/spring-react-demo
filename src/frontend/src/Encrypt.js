import AES from "crypto-js/aes";
import CryptJS from "crypto-js";

class Encrypt {
    static SECRETKEY = 'aes-256-cbc-text';
    static decode(buf) {
        if ( buf === undefined || buf === null || buf === "" ) {
            return undefined;
        }
        var bytes = AES.decrypt(buf,this.SECRETKEY);
        var obj = JSON.parse(bytes.toString(CryptJS.enc.Utf8));
        console.log(obj);
        return obj;
    }
    static encode(obj) {
        if ( obj === undefined ) {
            return undefined;
        }
        var buf = JSON.stringify(obj);
        var ret = AES.encrypt(buf,this.SECRETKEY);
        console.log(ret.toString())
        return ret.toString();
    }
}

export default Encrypt;