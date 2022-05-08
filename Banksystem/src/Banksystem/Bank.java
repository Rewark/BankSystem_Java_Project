package Banksystem;

import java.util.LinkedList;

public class Bank {
	private String name;
	private LinkedList<Kunden> kList;
	
	
	public String getName() {
		return name;
	}

	
	public void setName(String name) {
		this.name = name;
	}


	public Bank(String name) {
		this.name = name;
		this.kList = new LinkedList<>();
	}

	public LinkedList<Kunden> getKList() {
		return this.kList;
	}

	public void setKList(Kunden List) {
		this.kList.add(List);
	}
	
	@Override
	public String toString() {
		return this.name + ""+this.kList;
	}
	
	

}
