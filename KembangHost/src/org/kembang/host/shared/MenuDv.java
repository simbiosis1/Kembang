package org.kembang.host.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MenuDv implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8326205699678456335L;
	Integer nr;
	Boolean status;
	Long id;
	Long fastMenuId;
	Boolean fastMenu;
	String title;
	String parentTitle;
	String grandParentTitle;
	String link;
	String place;
	Boolean needClearView;
	List<MenuDv> children = new ArrayList<MenuDv>();

	public MenuDv() {
		id = 0L;
		nr = 0;
		status = false;
		fastMenu = false;
	}

	public Integer getNr() {
		return nr;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public void setNr(Integer nr) {
		this.nr = nr;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFastMenuId() {
		return fastMenuId;
	}

	public void setFastMenuId(Long fastMenuId) {
		this.fastMenuId = fastMenuId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getParentTitle() {
		return parentTitle;
	}

	public void setParentTitle(String parentTitle) {
		this.parentTitle = parentTitle;
	}

	public String getGrandParentTitle() {
		return grandParentTitle;
	}

	public void setGrandParentTitle(String grandParentTitle) {
		this.grandParentTitle = grandParentTitle;
	}

	public List<MenuDv> getChildren() {
		return children;
	}

	public void setChildren(List<MenuDv> children) {
		this.children = children;
	}

	public Boolean getNeedClearView() {
		return needClearView;
	}

	public void setNeedClearView(Boolean needClearView) {
		this.needClearView = needClearView;
	}

	public Boolean getFastMenu() {
		return fastMenu;
	}

	public void setFastMenu(Boolean fastMenu) {
		this.fastMenu = fastMenu;
	}
}
