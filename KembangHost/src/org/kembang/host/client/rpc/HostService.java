package org.kembang.host.client.rpc;

import java.util.List;

import org.kembang.host.shared.CheckModuleDv;
import org.kembang.host.shared.MenuDv;
import org.kembang.host.shared.UserDv;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("host")
public interface HostService extends RemoteService {
	CheckModuleDv login(String user, String password)
			throws IllegalArgumentException;

	void logout(String signature) throws IllegalArgumentException;

	UserDv getUser(String signature) throws IllegalArgumentException;

	void savePassword(String signature, String password)
			throws IllegalArgumentException;

	void saveFastMenu(String key, List<MenuDv> fastMenus)
			throws IllegalArgumentException;

	CheckModuleDv chekSignature(String signature)
			throws IllegalArgumentException;

	List<MenuDv> listFastMenu(String key) throws IllegalArgumentException;
}
