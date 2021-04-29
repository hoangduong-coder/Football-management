package project2021;
import java.io.Serializable;
//This class is used to get and display the date inputed by the user.
public class Date implements Serializable {
	private static final long serialVersionUID = 1L;
	private int dd;
	private int mm;
	private int yy;
	public Date(int dd, int mm, int yy) {
		this.dd = dd;
		this.mm = mm;
		this.yy = yy;
	}
	public int getDd() {
		return dd;
	}
	public void setDd(int dd) {
		this.dd = dd;
	}
	public int getMm() {
		return mm;
	}
	public void setMm(int mm) {
		this.mm = mm;
	}
	public int getYy() {
		return yy;
	}
	public void setYy(int yy) {
		this.yy = yy;
	}
	public Date(String testDate) {
		String[] date = testDate.split("\\.");
		this.dd = Integer.parseInt(date[0]);
		this.mm = Integer.parseInt(date[1]);
		this.yy = Integer.parseInt(date[2]);
		if(this.mm > 12 || this.dd >31 || this.dd < 1 || this.mm < 1) {
			this.dd = 0;
			this.mm = 0;
			this.yy = 0;
		}
		else {
			if(dd == 29 && mm == 2 && yy % 4 != 0) { 
				this.dd = 0;
				this.mm = 0;
				this.yy = 0;
			}
			else if(dd == 30 && mm == 2) {
				this.dd = 0;
				this.mm = 0;
				this.yy = 0;
			}
			else if(dd == 31 && (mm == 2 || mm == 4 || mm == 6 || mm == 9 || mm == 11)) {
				this.dd = 0;
				this.mm = 0;
				this.yy = 0;
			}
		}
	}
	@Override
	public String toString() {
		if(dd != 0 && mm != 0) {
			return dd + "." + mm + "." + yy;
		}
		else return "Unknown";
	}
}
