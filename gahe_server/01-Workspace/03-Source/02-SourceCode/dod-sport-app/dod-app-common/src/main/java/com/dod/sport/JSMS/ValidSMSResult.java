package com.dod.sport.JSMS;

import com.google.gson.annotations.Expose;

import cn.jiguang.common.resp.BaseResult;


public class ValidSMSResult extends BaseResult {

	@Expose Boolean is_valid;
	
	public Boolean getIsValid() {
		return is_valid;
	}
	
}
