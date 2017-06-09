package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class User {
	//[M.U-0001]
	private int idUser;
	private String username;
	private String password;
	private Timestamp dateCreate;
	private Timestamp dateUpdate;
	private boolean accountActive;
	private String codeVerification;
	private String emailRecovery;
	
	//[M.U-0002]
	public User(){
		
	}
	//[M.U-0003]
	public User(int idUser, String username, String password, Timestamp dateCreate, Timestamp dateUpdate,
			boolean accountActive, String codeVerification, String emailRecovery) {
		this.idUser = idUser;
		this.username = username;
		this.password = password;
		this.dateCreate = dateCreate;
		this.dateUpdate = dateUpdate;
		this.accountActive = accountActive;
		this.codeVerification = codeVerification;
		this.emailRecovery = emailRecovery;
	}
	
	//[M.U-0004]
	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Timestamp getDateCreate() {
		return dateCreate;
	}

	public void setDateCreate(Timestamp dateCreate) {
		this.dateCreate = dateCreate;
	}

	public Timestamp getDateUpdate() {
		return dateUpdate;
	}

	public void setDateUpdate(Timestamp dateUpdate) {
		this.dateUpdate = dateUpdate;
	}

	public boolean isAccountActive() {
		return accountActive;
	}

	public void setAccountActive(boolean accountActive) {
		this.accountActive = accountActive;
	}

	public String getCodeVerification() {
		return codeVerification;
	}

	public void setCodeVerification(String codeVerification) {
		this.codeVerification = codeVerification;
	}

	public String getEmailRecovery() {
		return emailRecovery;
	}

	public void setEmailRecovery(String emailRecovery) {
		this.emailRecovery = emailRecovery;
	}

	//[M.U-0005]
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (accountActive ? 1231 : 1237);
		result = prime * result + ((codeVerification == null) ? 0 : codeVerification.hashCode());
		result = prime * result + ((dateCreate == null) ? 0 : dateCreate.hashCode());
		result = prime * result + ((dateUpdate == null) ? 0 : dateUpdate.hashCode());
		result = prime * result + ((emailRecovery == null) ? 0 : emailRecovery.hashCode());
		result = prime * result + idUser;
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}
	//[M.U-0006]
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (accountActive != other.accountActive)
			return false;
		if (codeVerification == null) {
			if (other.codeVerification != null)
				return false;
		} else if (!codeVerification.equals(other.codeVerification))
			return false;
		if (dateCreate == null) {
			if (other.dateCreate != null)
				return false;
		} else if (!dateCreate.equals(other.dateCreate))
			return false;
		if (dateUpdate == null) {
			if (other.dateUpdate != null)
				return false;
		} else if (!dateUpdate.equals(other.dateUpdate))
			return false;
		if (emailRecovery == null) {
			if (other.emailRecovery != null)
				return false;
		} else if (!emailRecovery.equals(other.emailRecovery))
			return false;
		if (idUser != other.idUser)
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	//[M.U-0007]
	@Override
	public String toString() {
		return "User [idUser=" + idUser + ", username=" + username + ", password=" + password + ", dateCreate="
				+ dateCreate + ", dateUpdate=" + dateUpdate + ", accountActive=" + accountActive + ", codeVerification="
				+ codeVerification + ", emailRecovery=" + emailRecovery + "]";
	}
	
	
	
	static{
		try{
			Class.forName("com.mysql.jdbc.Driver");
		}catch(ClassNotFoundException e){
			throw new RuntimeException();
		}
	}
	
	public Connection getConnection() throws SQLException{
		return DriverManager.getConnection("jdbc:mysql://localhost/voluntariis?user=root&password=root");
	}
	
	
	
	
	public void insertUser(){
		String sqlInsert = "INSERT INTO USERS(usernme, passwrd, dateCreate, dateUpdate, accountActive, codeVerification, emailRecovery)"
				+ "VALUES( ?, MD5(?), NOW(), NOW(), 0, MD5(?), ? )";
		
		try (Connection conn = getConnection();
				PreparedStatement stm = conn.prepareStatement(sqlInsert);){
			
			stm.setString(1, getUsername());
			stm.setString(2, getPassword());
			stm.setString(3, (""+getUsername().hashCode()));
			stm.setString(4, getEmailRecovery());
			stm.execute();
			
			String sqlQuery =  "SELECT LAST_INSERT_ID()";
			try(PreparedStatement stm2 = conn.prepareStatement(sqlQuery);
					ResultSet rs = stm2.executeQuery();){	
				if(rs.next()){
					setIdUser(rs.getInt(1));
				}
			}catch(SQLException e1){
				e1.printStackTrace();
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	
	
	
}
