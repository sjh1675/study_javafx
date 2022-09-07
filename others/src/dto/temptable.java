package dto;

import java.time.LocalDateTime;

public class temptable {
	private int mnum; // 기계번호 
	private String cname;
	private int mamount;
	private String mphone;
	private String mtemperature;
	private String mdegree;
	private int inputprice;
	private int wholeprice;
	
	public temptable() {
		super();
	}
	public temptable(int mnum) {
		super();
		this.mnum=mnum;
	}
	public temptable(String cname) {
		super();
		this.cname=cname;
	}
	public temptable(int mnum,String cname) {
		super();
		this.mnum = mnum;
		this.cname = cname;
	}
	public temptable(int mnum, int mamount) {
		super();
		this.mnum = mnum;
		this.mamount = mamount;
	}
	public temptable(int mnum, String cname, int mamount) {
		super();
		this.mnum = mnum;
		this.cname = cname;
		this.mamount = mamount;
	}
	public temptable(int mnum, String cname, int mamount, String mphone, String mtemperature, String mdegree,
			int inputprice, int wholeprice) {
		super();
		this.mnum = mnum;
		this.cname = cname;
		this.mamount = mamount;
		this.mphone = mphone;
		this.mtemperature = mtemperature;
		this.mdegree = mdegree;
		this.inputprice = inputprice;
		this.wholeprice = wholeprice;
	}
	public int getMnum() {
		return mnum;
	}
	public void setMnum(int mnum) {
		this.mnum = mnum;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public int getMamount() {
		return mamount;
	}
	public void setMamount(int mamount) {
		this.mamount = mamount;
	}
	public String getMphone() {
		return mphone;
	}
	public void setMphone(String mphone) {
		this.mphone = mphone;
	}
	public String getMtemperature() {
		return mtemperature;
	}
	public void setMtemperature(String mtemperature) {
		this.mtemperature = mtemperature;
	}
	public String getMdegree() {
		return mdegree;
	}
	public void setMdegree(String mdegree) {
		this.mdegree = mdegree;
	}
	public int getInputprice() {
		return inputprice;
	}
	public void setInputprice(int inputprice) {
		this.inputprice = inputprice;
	}
	public int getWholeprice() {
		return wholeprice;
	}
	public void setWholeprice(int wholeprice) {
		this.wholeprice = wholeprice;
	}
	
}