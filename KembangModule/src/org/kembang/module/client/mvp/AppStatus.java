package org.kembang.module.client.mvp;

import java.util.ArrayList;
import java.util.List;

import org.kembang.module.shared.MenuDv;
import org.kembang.module.shared.UserDv;

public class AppStatus {

	boolean login;
	String cookiesPath = "/";
	String cookiesName = "sid";
	String appPackage = "";
	UserDv userDv = null;
	List<MenuDv> menuList = new ArrayList<MenuDv>();

	public AppStatus() {
		login = false;
	}

	public boolean isLogin() {
		return login;
	}

	public void setLogin(boolean login) {
		this.login = login;
	}

	public String getCookiesName() {
		return cookiesName;
	}

	public UserDv getUser() {
		return userDv;
	}

	public void setUser(UserDv userDv) {
		this.userDv = userDv;
	}

	public List<MenuDv> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<MenuDv> menuList) {
		this.menuList = menuList;
	}

	public String getCookiesPath() {
		return cookiesPath;
	}

	public String getAppPackage() {
		return appPackage;
	}

	public void setAppPackage(String appPackage) {
		this.appPackage = appPackage;
	}

}
