package com.model2.mvc.service.domain;

public class KakaoPayApprove {
	
	//field
	private String aid;
	private String cid; //가맹점 코드
	private String tid; //결제 번호
	private String sid;
	private String partner_order_id;
	private String partner_user_id;
	private String payment_method_type;
	private String item_name;
	private String item_code;
	private int quantity;
	private String created_at;
	private String approved_at;
	private String payload;
	private Amount amount;
	
	//constructor
	public KakaoPayApprove() {
		// TODO Auto-generated constructor stub
	}
	
	
	//getter, setter method
	public String getAid() {
		return aid;
	}

	public String getCid() {
		return cid;
	}

	public String getTid() {
		return tid;
	}

	public String getSid() {
		return sid;
	}

	public String getPartner_order_id() {
		return partner_order_id;
	}

	public String getPartner_user_id() {
		return partner_user_id;
	}

	public String getPayment_method_type() {
		return payment_method_type;
	}

	public String getItem_name() {
		return item_name;
	}

	public String getItem_code() {
		return item_code;
	}

	public int getQuantity() {
		return quantity;
	}

	public String getCreated_at() {
		return created_at;
	}

	public String getApproved_at() {
		return approved_at;
	}

	public String getPayload() {
		return payload;
	}

	public Amount getAmount() {
		return amount;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public void setPartner_order_id(String partner_order_id) {
		this.partner_order_id = partner_order_id;
	}

	public void setPartner_user_id(String partner_user_id) {
		this.partner_user_id = partner_user_id;
	}

	public void setPayment_method_type(String payment_method_type) {
		this.payment_method_type = payment_method_type;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public void setApproved_at(String approved_at) {
		this.approved_at = approved_at;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public void setAmount(Amount amount) {
		this.amount = amount;
	}
	

	
	

}
