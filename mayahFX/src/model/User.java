package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User {
	private SimpleStringProperty descriptionProperty;
	private StringProperty userNameProperty;
	private StringProperty passwordProperty; 
	
	public User() {
		this.descriptionProperty=new SimpleStringProperty();
		this.userNameProperty=new SimpleStringProperty();
		this.passwordProperty=new SimpleStringProperty(); 
	}

	public String getDescription() {
		return descriptionProperty.get();
	}

	public void setDescription(String desc) {
		this.descriptionProperty.set(desc);
	}

	public String getUserName() {
		return userNameProperty.get();
	}

	public void setUserName(String usr) {
		this.userNameProperty.set(usr);
	}

	public String getPassword() {
		return passwordProperty.get();
	}

	public void setPassword(String pwd) {
		this.passwordProperty.set(pwd);
	}
	
	

}
