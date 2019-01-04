package com.plugin.drools.bill.model;

//报销单
public class Bill {
	// 状态
	private String _status;
	// 动作
	private String _action;
	
	// 金额
	private Double _amount;

	public String getStatus() {
		return _status;
	}

	public void setStatus(String value) {
		_status = value;
	}
	
	public String getAction() {
		return _action;
	}

	public void setAction(String value) {
		_action = value;
	}

	public Double getAmount() {
		return _amount;
	}

	public void setAmount(Double value) {
		_amount = value;
	}
}
