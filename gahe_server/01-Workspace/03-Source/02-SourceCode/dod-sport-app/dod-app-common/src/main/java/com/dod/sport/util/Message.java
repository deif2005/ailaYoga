package com.dod.sport.util;

import java.io.Serializable;

public class Message implements Serializable {

	private static final long serialVersionUID = -2999571571280318844L;

	private ReturnResult result;
	private Object datas;

	public ReturnResult getResult() {
		return result;
	}

	public void setResult(ReturnResult result) {
		this.result = result;
	}

	public Object getDatas() {
		return datas;
	}

	public void setDatas(Object datas) {
		this.datas = datas;
	}

}
