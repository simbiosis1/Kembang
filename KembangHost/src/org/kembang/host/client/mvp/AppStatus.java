package org.kembang.host.client.mvp;

import java.util.ArrayList;
import java.util.List;

import org.kembang.host.shared.MenuDv;
import org.kembang.host.shared.UserDv;

public class AppStatus {

	boolean login;
	String cookiesPath = "/";
	String cookiesName = "sid";
	List<MenuDv> userMenuList = new ArrayList<MenuDv>();
	List<MenuDv> fastMenuList = new ArrayList<MenuDv>();
	UserDv userDv = new UserDv();

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

	/**
	 * @param userMenuList
	 */
	public void setUserMenuList(List<MenuDv> userMenuList) {
		this.userMenuList.clear();
		this.userMenuList.addAll(userMenuList);
	}

	public List<MenuDv> getUserMenuList() {
		return userMenuList;
	}

	public List<MenuDv> getFastMenuList() {
		return fastMenuList;
	}

	public void setFastMenuList(List<MenuDv> fastMenuList) {
		this.fastMenuList.clear();
		this.fastMenuList.addAll(fastMenuList);
	}

	public UserDv getUser() {
		return userDv;
	}

	public void setUser(UserDv userDv) {
		this.userDv = userDv;
	}

	public String getCookiesPath() {
		return cookiesPath;
	}

}
