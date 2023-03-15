package com.model2.mvc.service.domain;

public class KakaoPayReadyResponse {

	//field
	private String tid;
	private String next_redirect_mobile_url; // 모바일 웹의 경우 받는 결제 페이지
	private String next_redirect_pc_url; // PC환경에서 받는 결제 페이지
	private String created_at;
	
	// Constructor
	public KakaoPayReadyResponse() {
		System.out.println("KakaoPayReadyResponse constructor call...");
	}
	
	
	//Getter, Setter Method
	public String getTid() {
		return tid;
	}

	public String getNext_redirect_mobile_url() {
		return next_redirect_mobile_url;
	}

	public String getNext_redirect_pc_url() {
		return next_redirect_pc_url;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public void setNext_redirect_mobile_url(String next_redirect_mobile_url) {
		this.next_redirect_mobile_url = next_redirect_mobile_url;
	}

	public void setNext_redirect_pc_url(String next_redirect_pc_url) {
		this.next_redirect_pc_url = next_redirect_pc_url;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	
	
	
}
