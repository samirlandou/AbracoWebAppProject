package br.com.abracowebmanagement.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;

import com.mysql.cj.x.protobuf.MysqlxDatatypes.Scalar.String;

@ManagedBean(name = "MBLogin")
@ViewScoped
public class loginController {

	private String name;
	private String password;

	
	
	
	
	/*
	 * Getters and Setters
	 */
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
