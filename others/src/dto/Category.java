package dto;

public class Category {
	private int cnum;
	private int mnum;
	private int pnum;
	private String cname;
	private int cprice;
	
	public Category() {
		// TODO Auto-generated constructor stub
	}

	public Category(int cnum, int mnum, int pnum, String cname, int cprice) {
		super();
		this.cnum = cnum;
		this.mnum = mnum;
		this.pnum = pnum;
		this.cname = cname;
		this.cprice = cprice;
	}

	public int getCnum() {
		return cnum;
	}

	public void setCnum(int cnum) {
		this.cnum = cnum;
	}

	public int getMnum() {
		return mnum;
	}

	public void setMnum(int mnum) {
		this.mnum = mnum;
	}

	public int getPnum() {
		return pnum;
	}

	public void setPnum(int pnum) {
		this.pnum = pnum;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public int getCprice() {
		return cprice;
	}

	public void setCprice(int cprice) {
		this.cprice = cprice;
	};
	
}