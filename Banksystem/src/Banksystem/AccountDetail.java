
package Banksystem;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AccountDetail{
	public String getDatum() {
		return datum;
	}

	private String detail;
	private String datum;
	private DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");


	private double balanceAfter;
	private double value;
	private double balanceBefor;
	
	public AccountDetail(String detail, double balanceAfter, double balanceBefor) {
		super();
		this.detail = detail;
		this.datum =  LocalDateTime.now().format(myFormatObj);
		this.balanceAfter = balanceAfter;
		this.value = value;
		this.balanceBefor = balanceBefor;
	}

	@Override
	public String toString() {
		return   detail + "\n " + datum
				+ " " + balanceAfter + "\n "+ value +"\n "+ balanceBefor ;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}



	public double getBalanceAfter() {
		return balanceAfter;
	}

	public void setBalanceAfter(double balanceAfter) {
		this.balanceAfter = balanceAfter;
	}

	public double getBalanceBefor() {
		return balanceBefor;
	}

	public void setBalanceBefor(double balanceBefor) {
		this.balanceBefor = balanceBefor;
	}
	
}