/**
 * @fileoverview 
 * 日付指定用のコンポーネント用のファイル
 */
import React from "react";
import DatePicker from "react-datepicker";

import Select from "./Select";
import Util from "../../Util";

import "react-datepicker/dist/react-datepicker.css"
import "../../css/Main.css";

/**
 * 日付時刻コンポーネント
 * <pre>
 * カレンダー指定による
 * </pre>
 * @example
 * 日付時刻指定
 * <DateTime/>
 * 日付指定
 * <DateTime type="date"/>
 * 時刻指定
 * <DateTime type="time"/>
 * 値を指定
 * <DateTime value="2009-11-10 00:10"/>
 * 時刻時の指定(日付がないことを0で表現)
 * <DateTime value="0 00:10"/>
 * 分数の指定15分刻み
 * <DateTime step="15"/>
 * 空指定を可能にする場合
 * <DateTime empty="true"/>
 */
class DateTime extends React.Component {

    /**
     * コンストラクタ
     * <pre>
     * 指定されたtype,step,emptyにより、stateを設定
     * </pre>
     * @constructor
     * @param {object} props - type,step,empty
     */
    constructor(props) {

        super(props);

        let showDay = true;
        let showTime = true;
        this.step = 1;
        this.empty = false;

        var t = props.type;
        if (t !== undefined) {
            if (t === "date") {
                showTime = false;
            } else if (t === "time") {
                showDay = false;
            }
        }

        if (props.step !== undefined) {
            this.step = parseInt(props.step);
        }

        var day = null;
        var hour = 0;
        var minute = 0;
        if (props.value !== undefined) {
            day = new Date(props.value);
            hour = day.getHours();
            minute = day.getMinutes();
        } else {
            if (props.empty !== undefined) {
                this.empty = true;
                hour = "";
                minute = "";
            }
        }

        this.state = {
            hour: hour,
            minute: minute,
            day: day,
            styles: [],
            showDay: showDay,
            showTime: showTime
        }
    }

    /**
     * スタイル設定
     * <pre>
     * 日付によるスタイルの設定を行います。
     * </pre>
     * @param {Array} days - day,value形式のオブジェクト配列
     */
    setStyles(days) {
        var obj = {};

        days.forEach((day) => {
            let buf = day.day;
            let val = day.value;
            let arr = obj[val];
            if (arr === undefined) {
                arr = [];
            }
            arr.push(new Date(buf));
            obj[val] = arr;
        })

        var styles = [];
        let keys = Object.keys(obj);
        keys.forEach((val) => {
            let days = obj[val];
            let name = "Day-" + val;
            let style = {};
            style[name] = days;
            styles.push(style);
        });
        this.setState({ styles: styles });
    }

    /**
     * 日付指定イベント
     * @param {Date} day - カレンダーで指定した日付
     */
    handleDay(day) {
        this.setState({
            day: day
        })
    }

    /**
     * 時刻選択イベント
     * @param {number} hour - 指定した時刻
     */
    handleHour(hour) {
        this.setState({
            hour: hour
        })
    }

    /**
     * 分選択イベント
     * @param {number} minute - 指定した分
     */
    handleMinute(minute) {
        this.setState({
            minute: minute
        })
    }

    /**
     * 時刻値の設定
     * <pre>
     * 時刻をオブジェクトで設定します。
     * </pre>
     * @param {Date} date - 時刻データ
     */
    set(date) {
        let hour = date.getHours();
        let minute = date.getMinutes();
        this.setState({
            day: date,
            hour: hour,
            minute: minute
        })
    }

    /**
     * データ取得
     * <pre>
     * コンポーネントに指定してあるデータを設定
     * </pre>
     * @returns 指定してある時刻データ
     */
    get() {
        let rtn = "";
        let day = this.state.day;
        let hour = Util.zeroPadding(this.state.hour, 2);
        let minute = Util.zeroPadding(this.state.minute, 2);

        if (this.state.showDay) {
            if (day != null) {
                rtn = day.getFullYear() + "-" + (day.getMonth() + 1) + "-" + day.getDate();
            } else {
                return undefined;
            }
        }

        if (this.state.showTime) {
            if (rtn !== "") {
                rtn += " ";
            }
            if (this.empty) {
                if (hour === "" || minute === "") {
                    if (hour !== "" || minute !== "") {
                        if (rtn === "") return null;
                        return undefined;
                    }
                }
            }

            rtn += hour;
            rtn += ":" + minute;
        }

        return rtn;
    }

    /**
     * レンダリング関数
     * <pre>
     * 指定されたタイプによりレンダリング
     * </pre>
     * @returns "DateTime"クラスを指定したdivタグ
     */
    render() {
        let hours = []
        for (let i = 0; i < 24; ++i) {
            hours[i] = i;
        }

        let minutes = []
        for (let i = 0; i < 60; i = i + this.step) {
            minutes[i] = i;
        }

        var now = new Date();
        var next = new Date();
        next.setMonth(next.getMonth() + 1);
        now.setMonth(now.getMonth() - 3);

        return (
            <>
                <div className="DateTime">
                    {this.state.showDay &&
                        <DatePicker
                            dateFormat="yyyy-MM-dd"
                            highlightDates={this.state.styles}
                            selected={this.state.day}
                            onChange={(date) => this.handleDay(date)}
                            minDate={now}
                            maxDate={next}
                            readOnly={false}
                            disabledKeyboardNavigation
                            showDisabledMonthNavigation
                        />
                    }

                    {this.state.showTime &&
                        <>
                            <Select values={hours} className="DateTime-Hour" value={this.state.hour} empty={this.empty} onChange={(h) => this.handleHour(h)} />
                            <Select values={minutes} className="DateTime-Minute" value={this.state.minute} empty={this.empty} onChange={(m) => this.handleMinute(m)} />
                        </>
                    }
                </div>
            </>
        )
    }
}

export default DateTime;