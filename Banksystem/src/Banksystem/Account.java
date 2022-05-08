package Banksystem;

import java.util.ArrayList;

public class Account {
	private long accountNo;
	private double balance;
	private ArrayList<AccountDetail> aDetail = new ArrayList<>();
	private AccountDetail detali;

	public Account() {
		
		this.balance = 0;
		this.accountNo = 0;
		
		System.out.println("2");
	}
	public Account(long accountNo) {
		this.accountNo = accountNo;
		//this.balance = getBalance();
	}
	
	public Account(double balance, long accountNo) {
		this.balance = balance;
		this.accountNo = accountNo;
		
		
	}
	
	public AccountDetail getDetali() {
		return detali;
	}

	public void setDetali(AccountDetail detali) {
		this.detali = detali;
	}

	
	public void setAccountNo(long accountNo) {
		this.accountNo = accountNo;
	}



	@Override
	public String toString() {
		return this.accountNo +" "+ this.balance;
	}
	
	public boolean deposit(double amount) {
		if(amount !=0) {
		balance += amount;
		  return true;
		}else {
			return false;
		}
	}
	
	public boolean withDraw(double amount) {
		if (amount > balance) {
			return false;
		}
		else {
			balance -= amount;
			return true;
		}
	}
	
	
	public ArrayList<AccountDetail> getaDetail() {
		return aDetail;
	}

	public void setaDetail(AccountDetail aDetail) {
		this.aDetail.add(aDetail);
	}

	public long getAccountNo() {
		return this.accountNo;
		
	}
	public double getBalance() {
		return this.balance;
	}
	

}
