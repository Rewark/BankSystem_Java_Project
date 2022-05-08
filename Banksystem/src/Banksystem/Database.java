package Banksystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

public class Database {
	
	
	private Connection connt;
	
	public Database() throws ClassNotFoundException, SQLException{
		Class.forName("org.sqlite.JDBC");
		String Databasename = "jdbc:sqlite:bankSystem.sqlite";
		
		this.connt = DriverManager.getConnection(Databasename);
		
		
	}
	
	public void createTable() throws SQLException {
		Statement stmt = this.connt.createStatement();
		stmt.execute("CREATE TABLE IF NOT EXISTS Bank(ID INTEGER PRIMARY KEY, name TEXT)");
		stmt.execute("CREATE TABLE IF NOT EXISTS Kunden(ID INTEGER PRIMARY KEY, firstname TEXT, lastname TEXT, password TEXT, BankID INTEGER, FOREIGN KEY(BankID)REFERENCES Bank(ID ))");
		stmt.execute("CREATE TABLE IF NOT EXISTS Login(ID INTEGER PRIMARY KEY, password TEXT, KundenID INTEGER, FOREIGN KEY(KundenID)REFERENCES Kunden(ID) )");
		stmt.execute("CREATE TABLE IF NOT EXISTS Account(ID INTEGER PRIMARY KEY, accountNo INTEGER, balance REAL, KundenID INTEGER, FOREIGN KEY(KundenID)REFERENCES Kunden(ID))");
		stmt.execute("CREATE TABLE IF NOT EXISTS AccountDetail(ID INTEGER PRIMARY KEY, accountDetail Text, balanceBefor REAL, value REAL, balanceAfter REAL,  Datum TEXT, AccountID INTEGER, FOREIGN KEY(AccountID)REFERENCES Account(ID))");
	
		stmt.close();
		
	}
	
	public void addkundenToDatabase(Kunden k, String bank) throws SQLException {
		PreparedStatement pstmt = this.connt.prepareStatement("INSERT INTO Kunden(firstname, lastname, password, BankID )VALUES(?, ?, ?, (SELECT ID FROM Bank WHERE name=? LIMIT 1) )");
		pstmt.setString(1, k.getFirstname());
		pstmt.setString(2, k.getLastname());
		pstmt.setString(3, k.getPassword());
		pstmt.setString(4, bank);
		pstmt.executeUpdate();
		pstmt.close();
		
		
	}
	
	public void addkundenToDatabase(Kunden k, Account a, String bank) throws SQLException {
		PreparedStatement pstmt = this.connt.prepareStatement("INSERT INTO Kunden(firstname, lastname, password, BankID)VALUES(?, ?, ?, (SELECT ID FROM Bank WHERE name=? LIMIT 1))");
		pstmt.setString(1, k.getFirstname());
		pstmt.setString(2, k.getLastname());
		pstmt.setString(3, k.getPassword());
		pstmt.setString(4, bank);
		pstmt.executeUpdate();
		pstmt.close();
		
		addAccountToDatabase(a,k);
		
	}
	
	public void addBankToDatabase(Bank b) throws SQLException{
		PreparedStatement pstmt= this.connt.prepareStatement("INSERT INTO Bank(name)VALUES(?)");
		
		pstmt.setString(1, b.getName());
		pstmt.executeUpdate();
		pstmt.close();
		
	}
	
	public void addAccountToDatabase(Account a, Kunden kunde) throws SQLException {
		PreparedStatement pstmt = this.connt.prepareStatement("INSERT INTO Account(accountNo, balance, KundenID)VALUES(?, ?,(SELECT ID FROM Kunden WHERE firstname=? and lastname=? LIMIT 1))");
		
		pstmt.setLong(1, a.getAccountNo());
		pstmt.setDouble(2, a.getBalance());
		pstmt.setString(3, kunde.getFirstname());
		pstmt.setString(4, kunde.getLastname());
		
		
		
		pstmt.executeUpdate();
		pstmt.close();
		
		
	}
	//(ID INTEGER PRIMARY KEY, accountDetail Text, balanceBefor REAL, balanceAfter REAL,  Datum TEXT, AccountID INTEGER, FOREIGN KEY(AccountID)REFERENCES Account(ID))");
	public void addAccountDetailToDatabase(double v, long a, AccountDetail ad) throws SQLException {
		PreparedStatement pstmt = this.connt.prepareStatement("INSERT INTO AccountDetail(accountDetail, balanceBefor, value, balanceAfter, Datum, AccountID)VALUES(?,(SELECT balance FROM Account WHERE accountNo=?  LIMIT 1) ,?,?,?,(SELECT ID FROM Account WHERE accountNo=?  LIMIT 1))");
		
		
		pstmt.setString(1, ad.getDetail());
		pstmt.setDouble(2, a);
		pstmt.setDouble(3, v);
		pstmt.setDouble(4, ad.getBalanceAfter());
		pstmt.setString(5, ad.getDatum());
		pstmt.setLong(6, a);

		pstmt.executeUpdate();
		pstmt.close();
	
		
	}
	public LinkedList<AccountDetail> getAccountDetailFromDatabase(int AccountID) throws SQLException {
		LinkedList<AccountDetail> a = new LinkedList<>();
		PreparedStatement pstmt = this.connt.prepareStatement("SELECT * FROM AccountDetail WHERE AccountiD=(SELECT ID FROM Account WHERE id=?)");
		pstmt.setInt(1, AccountID);
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()) {
			String detail = rs.getString("accountDetail");
			double balanceBefor= rs.getDouble(2);
			double value = rs.getDouble(3);
			double balanceAfter = rs.getDouble(4);
		    a.add(new AccountDetail(detail, balanceBefor, balanceAfter ));
		    
		}
		pstmt.close();
		return a;
	}
	
	public void setAccountBalanceToDatabase(long a, double b) throws SQLException {
		PreparedStatement pstmt = this.connt.prepareStatement("UPDATE Account SET balance=(?)  WHERE ID = (SELECT ID FROM Account WHERE accountNo=?  LIMIT 1)");
		
		
		
		pstmt.setDouble(1, b);
		pstmt.setLong(2, a);

		pstmt.executeUpdate();
		pstmt.close();
		
	}
	
	
	public Kunden getKundenFromDatabase(String firstname) throws SQLException {
		Kunden k = null;
		PreparedStatement pstmt = this.connt.prepareStatement("SELECT * FROM Kunden WHERE firstname=?");
		pstmt.setString(1, firstname);
		ResultSet rs = pstmt.executeQuery();
		
		if (rs.next()) {
			String lastname = rs.getString("lastname");
			String password = rs.getString("password");
		    k = new Kunden(firstname, lastname, password);
		    pstmt.close();
		
		    return k;
			
		}
		return k;
	}
	
	public Account getAccountNo(String name) throws SQLException {
		Account a = null;
		PreparedStatement pstmt = this.connt.prepareStatement("SELECT accountNo, balance FROM Account WHERE KundenID = (SELECT ID FROM Kunden WHERE firstname=?)");
		pstmt.setString(1, name);
		ResultSet rs = pstmt.executeQuery();
		
		if (rs.next()) {
		
			long accountNo = rs.getLong(1);
			double balance = rs.getDouble(2);
			
			a = new Account(balance, accountNo);
			pstmt.close();
			
		    return a;
			
		}
		return a;
	}
	
	public LinkedList<Account> getAccountNo1(String name) throws SQLException {
		LinkedList<Account> a = new LinkedList<>();
		PreparedStatement pstmt = this.connt.prepareStatement("SELECT accountNo, balance FROM Account WHERE KundenID = (SELECT ID FROM Kunden WHERE firstname=?)");
		pstmt.setString(1, name);
		ResultSet rs = pstmt.executeQuery();
		
		long accountNo;
		double balance;
		while (rs.next()) {
			 accountNo = rs.getLong(1);
			 balance = rs.getDouble(2);
			a.add( new Account(balance, accountNo));

		}
		pstmt.close();
		return a;
	}
	

	public Account getAccount(long nummer) throws SQLException {
		Account a = null;
		PreparedStatement pstmt = this.connt.prepareStatement("SELECT * FROM Account WHERE accountNo =? ");
		pstmt.setLong(1, nummer);
		ResultSet rs = pstmt.executeQuery();
		
		if (rs.next()) {
			double balance = rs.getDouble(3);
			
			a = new Account(balance, nummer);
			pstmt.close();
			
		    return a;
			
		}
		return a;
	}
		
	
	
	public void addLoginToDatabase(Kunden k ) throws SQLException {
		PreparedStatement pstmt = this.connt.prepareStatement("INSERT INTO Login(password, KundenID)VALUES(?,(SELECT ID FROM Kunden WHERE firstname=? LIMIT 1))");
		pstmt.setString(1, k.getPassword());
		pstmt.setString(2, k.getFirstname());

		pstmt.executeUpdate();
		pstmt.close();
		
	}
	
	public Kunden getLoginFromDatabase(String name) throws SQLException {
		Kunden k = null;
		PreparedStatement pstmt = this.connt.prepareStatement("SELECT password FROM Login WHERE ID = (SELECT ID FROM Kunden WHERE firstname=?)");
		pstmt.setString(1, name);
		ResultSet rs = pstmt.executeQuery();
		
		if (rs.next()) {
			String pass = rs.getString(1);
			
			
		    k = new Kunden( name,"",pass);
		    pstmt.close();
		   
		}
		return k;
	}
	
}

	
