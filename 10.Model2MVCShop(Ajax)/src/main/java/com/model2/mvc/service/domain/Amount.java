package com.model2.mvc.service.domain;

public class Amount {
	
	//field
	private int total;
	private int tax_free;
	private int vat;
	private int point;
	private int discount;
	
	//constructor
	public Amount() {
		// TODO Auto-generated constructor stub
	}

	public int getTotal() {
		return total;
	}

	public int getTax_free() {
		return tax_free;
	}

	public int getVat() {
		return vat;
	}

	public int getPoint() {
		return point;
	}

	public int getDiscount() {
		return discount;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public void setTax_free(int tax_free) {
		this.tax_free = tax_free;
	}

	public void setVat(int vat) {
		this.vat = vat;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

}
