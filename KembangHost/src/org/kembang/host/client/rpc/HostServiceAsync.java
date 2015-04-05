package org.kembang.host.client.rpc;

import java.util.List;

import org.kembang.host.shared.CheckModuleDv;
import org.kembang.host.shared.MenuDv;
import org.kembang.host.shared.UserDv;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface HostServiceAsync {
	void login(String user, String password,
			AsyncCallback<CheckModuleDv> callback)
			throws IllegalArgumentException;

	void chekSignature(String signature, AsyncCallback<CheckModuleDv> callback)
			throws IllegalArgumentException;

	void logout(String signature, AsyncCallback<Void> callback)
			throws IllegalArgumentException;

	void getUser(String signature, AsyncCallback<UserDv> callback)
			throws IllegalArgumentException;

	void savePassword(String signature, String password,
			AsyncCallback<Void> callback) throws IllegalArgumentException;

	void listFastMenu(String key, AsyncCallback<List<MenuDv>> callback)
			throws IllegalArgumentException;

	void saveFastMenu(String key, List<MenuDv> fastMenus,
			AsyncCallback<Void> callback);

}
