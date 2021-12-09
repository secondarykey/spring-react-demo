class Util {

  static formatTime(date) {
    let d = new Date(Date.parse(date));
    return this.zeroPadding(d.getHours(),2) + ":" + 
              this.zeroPadding(d.getMinutes(),2);
  }

  static formatDate(date) {
    return date.getFullYear() + "-" + 
              this.zeroPadding((date.getMonth() + 1),2) + "-" + 
              this.zeroPadding(date.getDate(),2);
  }

  static nowString() {
    return this.formatDate(new Date());
  }
 
  static zeroPadding(num,len) {
    return ( Array(len).join("0") + num ).slice(-1 * len);
  }

  //文字列のGlob判定(*のみ)
  static matchStringGlob(pattern,v) {

    let strs = pattern.split("*")

    if ( strs.length === 1 ) {
      if ( pattern === v ) {
        return true;
      }
      return false;
    }

    let idx = 0;
    for (let i = 0; i < strs.length; i++ ) {
      let key = strs[i];
      if ( key === "" ) {
        continue;
      }
      let p = v.indexOf(key);
      if ( idx <= p ) {
        idx = p + key.length;
      } else {
          return false;
      }
    }

    return true;
  }

  // patternsからvalが存在するかを判定
  static match(val,patterns) {

    if ( !Array.isArray(patterns) ) {
        return undefined;
    }

    for (let i = 0; i < patterns.length; i++ ) {
      let key = patterns[i];
      if ( this.matchStringGlob(key,val) ) {
          return true;
      }
    }
    return false;
  }

}

export default Util;