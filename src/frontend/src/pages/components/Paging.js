/**
 * @fileoverview 
 * ページング用のコンポーネント
 */
import React from "react";
import { Pagination } from "react-bootstrap";

/**
 * ページングコンポーネント
 * <pre>
 * ページングを行う時にサーバサイドと連動しやすくする為のコンポーネント
 * </pre>
 * @example
 * <Paging />
 */
class Paging extends React.Component {

    /**
     * コンストラクタ
     * @constructor
     * @param {object} props - max,paging,onClick
     */
    constructor(props) {
        super(props)
        this.state = {
            display: props.max,
            paging: {}
        }
        this.onClick = props.onClick
    }

    /**
     * ページ数イベント
     * @param {number} num - ページ数
     */
    handleClick = (num) => {
        if (this.onClick !== undefined) {
            this.onClick(num);
        }
    }

    /**
     * レンダリング
     * <pre>
     * 指定された指定数を元にページングのページを表示
     * </pre>
     * @returns bootstrapのPagingタグ
     */
    render() {

        let paging = this.props.paging;

        if (paging.currentPage === undefined) {
            paging = {
                currentPage: 0,
                numberOfPage: 0
            }
        }

        let active = paging.currentPage;
        let last = paging.numberOfPage;
        let display = this.state.display;

        let indent = Math.floor(display / 2);

        let start = active - indent;
        if (start <= 0) {
            start = 1;
        } else if (start >= last - display) {
            start = last - display + 1;
        }

        let end = start + display - 1;
        if (end > last) {
            end = last;
        }

        var items = [];
        for (let num = start; num <= end; num++) {
            items.push(
                <Pagination.Item key={num} active={num === active} onClick={(e) => this.handleClick(num)}>
                    {num}
                </Pagination.Item>
            );
        }

        let prev = (active === 1);
        let next = (active === last);

        return (
            <Pagination>
                <Pagination.First onClick={(e) => this.handleClick(1)} />
                <Pagination.Prev disabled={prev} onClick={(e) => this.handleClick(active - 1)} />
                {items}
                <Pagination.Next disabled={next} onClick={(e) => this.handleClick(active + 1)} />
                <Pagination.Last onClick={(e) => this.handleClick(last)} />
            </Pagination>
        )
    }
}

export default Paging;