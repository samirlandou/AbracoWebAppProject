package br.com.abracowebmanagement.loginController;

import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;

@ManagedBean(name = "MBLogin")
@ViewScoped
public class loginController {

	private String name;
	private String password;
	
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
	
	public String teste() {
		
		System.out.println("this is a teste ");
		return "teste";		
	}
}
