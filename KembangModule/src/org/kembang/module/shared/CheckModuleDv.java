package org.kembang.module.shared;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class CheckModuleDv implements IsSerializable {
	Boolean success;
	UserDv user;
	List<MenuDv> menus = new ArrayList<MenuDv>();

	public CheckModuleDv() {
		success = false;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public UserDv getUser() {
		return user;
	}

	public void setUser(UserDv user) {
		this.user = user;
	}

	public List<MenuDv> getMenus() {
		return menus;
	}

	public void setMenus(List<MenuDv> menus) {
		this.menus = menus;
	}

}
