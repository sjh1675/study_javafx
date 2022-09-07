package dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Sales {
	private int snum;
	private LocalDateTime sdate;
	private int cnum;
	private int sprice;
	public Sales() {
		// TODO Auto-generated constructor stub
	}
	public Sales(int snum, LocalDateTime date, int cnum, int sprice) {
		super();
		this.snum = snum;
		this.sdate = date;
		this.cnum = cnum;
		this.sprice = sprice;
	}
	public int getSnum() {
		return snum;
	}
	public void setSnum(int snum) {
		this.snum = snum;
	}
	public LocalDateTime getSdate() {
		return sdate;
	}
	public void setSdate(LocalDateTime sdate) {
		this.sdate = sdate;
	}
	public int getCnum() {
		return cnum;
	}
	public void setCnum(int cnum) {
		this.cnum = cnum;
	}
	public int getSprice() {
		return sprice;
	}
	public void setSprice(int sprice) {
		this.sprice = sprice;
	}
	
}