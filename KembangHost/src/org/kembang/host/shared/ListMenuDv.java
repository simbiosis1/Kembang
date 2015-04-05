package org.kembang.host.shared;

import java.util.ArrayList;
import java.util.List;

public class ListMenuDv {
	List<MenuDv> menus;

	public ListMenuDv() {
		menus = new ArrayList<MenuDv>();
	}

	public ListMenuDv(List<MenuDv> menus) {
		this.menus = menus;
	}

	public List<MenuDv> getMenus() {
		return menus;
	}

	public void setMenus(List<MenuDv> menus) {
		this.menus = menus;
	}
}
