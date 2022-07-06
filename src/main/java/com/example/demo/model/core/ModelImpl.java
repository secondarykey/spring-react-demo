package com.example.demo.model.core;

import java.time.OffsetDateTime;

import org.springframework.data.annotation.Transient;

import com.example.demo.transfer.OperationParams;
import com.example.demo.util.DateUtil;

/**
 * モデル実装
 * <pre>
 * Persistable用のisNew()のみ実装
 * </pre>
 * @author p230985
 */
public abstract class ModelImpl implements Model {
	/**
	 * 登録フラグ
	 */
	@Transient
	private boolean register = false;
	
	/**
	 * 登録フラグの設定
	 * @param flag 新規レコードの場合trueを設定
	 */
	public void setRegister(boolean flag) {
		this.register = flag;
	}
	/**
	 * 新規レコード取得
	 * @return 新規レコードの場合true
	 */
	public boolean isNew() {
		return register;
	}

	/**
	 * 作成ユーザIDの設定
	 * @param id 指定ID
	 */
	//public abstract void setCreatedUser(String id);
	public  void setCreatedUser(String id) {
	}

	/**
	 * 作成日付の設定
	 * @param date 日付
	 */
	//public abstract void setCreatedDate(OffsetDateTime date);
	public void setCreatedDate(OffsetDateTime date) {
	}

	/**
	 * 更新ユーザIDの設定
	 * @param id 指定ID
	 */
	//public abstract void setUpdatedUser(String id);
	public void setUpdatedUser(String id) {
	}
	/**
	 * 更新日付の設定
	 * @param date 日付
	 */
	//public abstract void setUpdatedDate(OffsetDateTime date);
	public void setUpdatedDate(OffsetDateTime date) {
	}

	/**
	 * 作成データの設定
	 * @param params オペレーション引数
	 */
	public void setCreated(OperationParams params) {

		String id = params.getOperationId();

		OffsetDateTime now = DateUtil.now();

		setCreatedUser(id);
		setCreatedDate(now);
		setUpdatedUser(id);
		setUpdatedDate(now);
	}

	/**
	 * 更新データの設定
	 * @param params オペレーション引数
	 */
	public void setUpdated(OperationParams params) {
		String id = params.getOperationId();
		OffsetDateTime now = DateUtil.now();
		setUpdatedUser(id);
		setUpdatedDate(now);
	}
}
