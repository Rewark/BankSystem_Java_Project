package Banksystem;

import java.sql.SQLException;
import java.util.Scanner;

public class BankSystem {
	public static long counter = 2214451;
	public static boolean start = true;
	public static String name;
	public static boolean scan(Database db ,String name,String pass) throws SQLException, NullPointerException{
		//System.out.println(db.getLoginFromDatabase("rewar"));
		Kunden k =  db.getLoginFromDatabase(name);
		if(k!=null) {return pass.equals(k.getPassword()) ;}
		else return false;
		
		
		//if(pass.equals(db.getLoginFromDatabase(name))) {return true;}
		//return false;
	}
	
	public static void main(String[] args) throws SQLException, ClassNotFoundException{
		
		Database db = new Database();
		
		db.createTable();
		Bank b = new Bank("cbm");
		db.addBankToDatabase(b);
		
		Scanner  sc = new Scanner(System.in);
		int input = 0;
		do {
			System.out.println("[1] Anmelden");
			System.out.println("[2] Registrieren mit Konto");
			System.out.println("Eingabe:");
			try {
				input = Integer.valueOf(sc.nextLine());
				if(input == 1) {login();}
				else if(input == 2) {creatKundeWithAccount();}
				else {System.out.println("bitte eine von den beiden Nummern eingeben: "); }
			}catch(NumberFormatException e) {System.err.println("Bitte eine Nummer eingeben -__-");}
		}while(input !=0 );

	}
	public static void login()throws SQLException, ClassNotFoundException{
		Scanner  sc = new Scanner(System.in);
		Database db = new Database();
		String fName = null;
		String pass = null;
		int input = 0;
		int xx = 0;
		System.out.println("Anmeldung\n");
		System.out.println("Vorname: ");
		fName = sc.nextLine();
		System.out.println("Passwort: ");
		pass = sc.nextLine();		
		if( fName == null || pass == null) {
			System.out.println("Bitte Name und Passwort eingeben: ");
			
		}
		else {
				
			if( scan( db ,fName, pass)) {
				name =db.getKundenFromDatabase(fName).getFirstname();
	     		System.out.println("===============================================\n===============================================");
	     		System.out.println("Wilkommen" +" "+  name+"\n");
	     		start();				
			}
			else {
				System.out.println("Der Name oder das Passwort ist falsch");}
		}		
	}
		
//	addAccountDetailToDatabase(long a, AccountDetail ad)	
	
//	Geld vom Konto abheben und auch  auf das Konto einzahlen
	public static void geldEinzahlen(int x) throws SQLException, ClassNotFoundException{
		Scanner  sc = new Scanner(System.in);
		Database db = new Database();
		Account a = db.getAccountNo(name);
		System.out.println("Aktuell verfügbar: "+a.getBalance()+"€");
		double balance = 0;
	
		System.out.println("Betrag: ");
		balance = (double)(Double.valueOf(sc.nextLine())* x);
		boolean transaction = true;
		if(balance <= -1) {
			if(balance >= db.getAccountNo(name).getBalance() || balance < 0 &&   -balance > db.getAccountNo(name).getBalance()) {
				System.out.println("Auf Ihrem Konto ist nicht Genug Geld vorhanden");
				transaction = false;

			}else {
				db.addAccountDetailToDatabase(balance ,a.getAccountNo(), new AccountDetail(x == 1 ?  "einzahlen" :"Abheben",  (a.getBalance()+balance) , a.getBalance()));
				db.setAccountBalanceToDatabase(a.getAccountNo(), (a.getBalance()+balance));
			}
			
		}else {
			db.addAccountDetailToDatabase(balance ,a.getAccountNo(), new AccountDetail(x == 1 ?  "einzahlen" :"Abheben",  (a.getBalance()+balance) , a.getBalance()));
			db.setAccountBalanceToDatabase(a.getAccountNo(), (a.getBalance()+balance));
		}
		
		if(transaction)
			System.out.println("Dem Konto mit der Nummer"+" "+ a.getAccountNo() + " wurde "+balance+"€"+ (balance<0 ? " abgehoben" : " gutgeschrieben ") + "\n");	
 		System.out.println("===============================================\n===============================================");
 		start();
	}
//	Geld Überweisung.
	public static void geldUeberweisen() throws ClassNotFoundException, SQLException {
		Scanner  sc = new Scanner(System.in);
		Database db = new Database();
		double balance = 0;
		long accountno = 0;
		long zielacountno = 0;

		try {
			System.out.println("deine Kontonummer eingeben");
			accountno = Long.valueOf(sc.nextLine());
			System.out.println("Ziel Kontonummer eingeben");
			zielacountno = Long.valueOf(sc.nextLine());
			System.out.println("Betrag: ");
			balance = (double)(Double.valueOf(sc.nextLine()));
			Account a = db.getAccount(accountno);
			Account aa = db.getAccount(zielacountno);
			db.addAccountDetailToDatabase(balance ,accountno, new AccountDetail("Überweisung",  (a.getBalance()-balance) , a.getBalance()));
			db.setAccountBalanceToDatabase(a.getAccountNo(), (a.getBalance()-balance));
			db.addAccountDetailToDatabase(balance, zielacountno, new AccountDetail("Überweisung",  (aa.getBalance()+balance) , aa.getBalance()));
			db.setAccountBalanceToDatabase(zielacountno, (aa.getBalance()+balance));
			System.out.println(db.getAccount(accountno).getBalance());
			System.out.println("Geld überwiesen");
     		System.out.println("===============================================\n===============================================");
     		start();
		}catch(NumberFormatException e) {
			System.out.println("Bitte Nummer eingeben -__- :");}
	}
	
	
	//Ein neues Konto erstellen.
	public static void creatAccount()throws SQLException, ClassNotFoundException{
		Scanner  sc = new Scanner(System.in);
		String fName = null;
		String lName = null;
		double balance = 0;
		Database db = new Database();
		System.out.println("Vorname: ");
		fName = sc.nextLine();
		System.out.println("Nachname: ");
		lName = sc.nextLine();
		System.out.println("Guthaben: ");
		balance = Double.valueOf(sc.nextLine());
		
		Kunden k = db.getKundenFromDatabase(fName);
		Account  a = new Account(balance, counter);
		db.addAccountToDatabase(a, k);
		counter++;
		System.out.println("Neu Konto erfolgreich angelegt\n");
 		System.out.println("===============================================\n===============================================");
 		start();
	}
	
	
	//neu Kunde mit ein Bank-Konto  
	public static void creatKundeWithAccount()throws SQLException, ClassNotFoundException  {
		Scanner  sc = new Scanner(System.in);
		
		Database db = new Database();
		String fName = null;
		String lName = null;
		String pass = null;
		double balance = 0;
		int input = 0;
		System.out.println("Bitte Ihr information eingeben: \n");
		System.out.println("Vorname: ");
		fName = sc.nextLine();
		System.out.println("Nachname: ");
		lName = sc.nextLine();
		System.out.println("Passwort: ");
		pass = sc.nextLine();
		System.out.println("Guthaben: ");
		try {
		balance = Double.valueOf(sc.nextLine());
		}catch(NumberFormatException e) {System.err.println("Bitt Nummer eingeben -__- :");}
		try {
			Kunden kundenDatenDatenbank = db.getKundenFromDatabase(fName);
			
			if(kundenDatenDatenbank != null && kundenDatenDatenbank.getFirstname().equalsIgnoreCase(fName)) {
				System.out.println("Der Name existiert bereits: ");
		
			}
			else {
				Bank b = new Bank("cbm");
				//db.addBankToDatabase(b);
				Kunden kk = new Kunden(fName, lName, pass);
				db.addkundenToDatabase(kk, b.getName());
				Account a = new Account(balance, counter);
				counter ++;
				db.addAccountToDatabase(a, kk);
				db.addLoginToDatabase( kk);
				System.out.println("Konto erfolgreich eingelegt:\n ");
	     		System.out.println("===============================================\n===============================================");
				System.out.println("[1] Zuruk zur Anmelden\n ");
				System.out.println("Eingabe: ");
				try {
					input = Integer.valueOf(sc.nextLine());
					if(input  == 1) {login();}
					
				}catch(NumberFormatException e) {System.err.println("Bitte Nummer eingeben -__- :");}
		
			 }
		}catch (NullPointerException e) {System.err.println("Die Name ist nicht vorhanden: " + e.getMessage());}

	}
//	konto Zustand
	public static void kontoZustand()throws SQLException, ClassNotFoundException {
		Database db = new Database();
		if(db.getAccountNo1(name).size() > 0) {
		for(Account a : db.getAccountNo1(name)) {		System.out.println("Deine Konto Nummer: "+ a.getAccountNo() );
		System.out.println("Auf dem konto sind: "+ a.getBalance()+"€\n");
 		System.out.println("===============================================\n===============================================");}}
		
		start();
		
	}

	public static void start()throws SQLException, ClassNotFoundException {
		Scanner  sc = new Scanner(System.in);
		
		int input2 = 0;

		do {
			System.out.println("[1] Überweisung");
			System.out.println("[2] Geld einzahlen");
			System.out.println("[3] Geld Abheben");
			System.out.println("[4] Neu Konto erofnen");
			System.out.println("[5] Konto Zustand");
			System.out.println("[6] Abmelden");
			
			
			try {
				System.out.println("Eingabe: ");
				input2 = Integer.valueOf((sc.nextLine()));
				
			}
			catch(NumberFormatException e) {
				System.err.println("Bitte eine Nummer eingeben -__-: ");
			}
			switch(input2) {
				case 1:
					geldUeberweisen();
					 break;
				case 2:
					geldEinzahlen(1);
					break;
				case 3:
					geldEinzahlen(-1);
					break;
				case 4:
					creatAccount();
					break;
				case 5:
					kontoZustand();
					break;
				case 6:
					System.out.println("-____-");
			 		System.out.println("===============================================\n===============================================");
					start = false;
					break;
				default:
					System.out.println("Bitte eine Nummer von der Liste eingeben");
			}
		}while(start);
	}
	
}
