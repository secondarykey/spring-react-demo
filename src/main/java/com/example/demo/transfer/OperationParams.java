package com.example.demo.transfer;

import java.util.Date;

public interface OperationParams {
	/**
	 * 操作ユーザのID
	 * @return 操作ユーザのID
	 */
	public String getOperationId();
	
	/**
	 * 操作ユーザの言語
	 * @return 言語コード
	 */
	public String getLanguage();

	/**
	 * システムののデフォルト言語取得
	 * @return システム
	 */
	public String getDefaultLanguage();
	/**
	 * 超権限持ちかどうか？
	 * @return 超権限の場合true
	 */
	public boolean isSuper();

	/**
	 * クライアント時刻
	 * @return クライアント時刻
	 */
	public Date getClientDate();
}
