package Banksystem;


import java.util.LinkedList;



public class  Kunden{
	private String firstname;
	private String lastname;
	private String password;
	private LinkedList<Account> list = new LinkedList<Account>();
	
	
	public Kunden (String firstname, String lastname, String password) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.password = password;
//		this.list.add( new Account(0) );
		System.out.println("1");
	}	
	
	public Kunden (String firstname, String lastname, String password,Account a) {
		this( firstname, lastname, password);
		this.list.add(a);
	}

	public void setAccount(Account a) {
		this.list.add(a);
	}
	public LinkedList<Account> getList() {
		return list;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	


	@Override
	public String toString() {
		return this.firstname +" "+ this.lastname +" "+ this.password +" "+ this.list;
				
	}

	public void setList(Account a) {
		this.list.add(a);
	}
	
	public LinkedList<Account> setlist(LinkedList<Account> list){
		return list;
	}
	

}
