package org.kembang.host.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;

import org.kembang.host.client.rpc.HostService;
import org.kembang.host.shared.CheckModuleDv;
import org.kembang.host.shared.MenuDv;
import org.kembang.host.shared.UserDv;
import org.simbiosis.bp.system.ISystemBp;
import org.simbiosis.system.BranchDto;
import org.simbiosis.system.CompanyDto;
import org.simbiosis.system.FastMenuDto;
import org.simbiosis.system.MenuDto;
import org.simbiosis.system.SessionDto;
import org.simbiosis.system.UserDto;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class HostServiceImpl extends RemoteServiceServlet implements
		HostService {

	@EJB(lookup = "java:global/SystemBpEar/SystemBpEjb/SystemBp")
	ISystemBp system;

	UserDv createUserDvFromDto(UserDto userDto, String sessionName,
			String companyName, String branchName) {
		UserDv userDv = new UserDv();
		userDv.setName(userDto.getName());
		userDv.setRealName(userDto.getRealName());
		userDv.setSignature(sessionName);
		userDv.setStrCompany(companyName);
		userDv.setStrBranch(branchName);
		userDv.setLogin(true);
		return userDv;
	}

	MenuDv createMenuDvFromDto(MenuDto menuDto) {
		MenuDv menuDv = new MenuDv();
		menuDv.setId(menuDto.getId());
		menuDv.setTitle(menuDto.getTitle());
		menuDv.setParentTitle(menuDto.getParentTitle());
		menuDv.setGrandParentTitle(menuDto.getGrandParentTitle());
		menuDv.setLink(menuDto.getLink());
		menuDv.setNeedClearView(true);
		return menuDv;
	}

	@Override
	public CheckModuleDv login(String user, String password) {
		Map<Long, MenuDv> fastMenuMap = new HashMap<Long, MenuDv>();
		CheckModuleDv result = new CheckModuleDv();
		SessionDto session = system.login(user, password);
		if (session != null) {
			result.setSuccess(true);
			UserDto userDto = system.getUser(session.getUserId());
			CompanyDto companyDto = system.getCompany(userDto.getCompany());
			BranchDto branchDto = system.getBranch(userDto.getBranch());
			result.setUser(createUserDvFromDto(userDto, session.getName(),
					companyDto.getName(), branchDto.getName()));
			//
			List<MenuDto> menus = system.listUserMenu(session.getName());
			for (MenuDto menuDto : menus) {
				MenuDv menuDv = createMenuDvFromDto(menuDto);
				result.getMenus().add(menuDv);
				fastMenuMap.put(menuDto.getId(), menuDv);
			}
			//
			List<FastMenuDto> listMenu = system.listUserFastMenu(session
					.getName());
			for (FastMenuDto fastMenu : listMenu) {
				MenuDv menu = fastMenuMap.get(fastMenu.getMenuId());
				if (menu != null) {
					menu.setFastMenuId(fastMenu.getId());
					menu.setFastMenu(true);
				}
			}
		}
		return result;
	}

	@Override
	public CheckModuleDv chekSignature(String signature) {
		Map<Long, MenuDv> fastMenuMap = new HashMap<Long, MenuDv>();
		CheckModuleDv result = new CheckModuleDv();
		SessionDto session = system.getSession(signature);
		if (session != null) {
			//
			result.setSuccess(true);
			//
			UserDto userDto = system.getUser(session.getUserId());
			CompanyDto companyDto = system.getCompany(userDto.getCompany());
			BranchDto branchDto = system.getBranch(userDto.getBranch());
			result.setUser(createUserDvFromDto(userDto, session.getName(),
					companyDto.getName(), branchDto.getName()));
			//
			List<MenuDto> menus = system.listUserMenu(signature);
			for (MenuDto menuDto : menus) {
				MenuDv menuDv = createMenuDvFromDto(menuDto);
				result.getMenus().add(menuDv);
				fastMenuMap.put(menuDto.getId(), menuDv);
			}
			//
			List<FastMenuDto> listMenu = system.listUserFastMenu(signature);
			for (FastMenuDto fastMenu : listMenu) {
				MenuDv menu = fastMenuMap.get(fastMenu.getMenuId());
				if (menu != null) {
					menu.setFastMenuId(fastMenu.getId());
					menu.setFastMenu(true);
				}
			}
		}
		return result;
	}

	@Override
	public void logout(String signature) {
		system.logout(signature);
	}

	@Override
	public UserDv getUser(String signature) {
		UserDto user = system.getUserFromSession(signature);
		UserDv result = new UserDv();
		result.setId(user.getId());
		result.setName(user.getName());
		result.setRealName(user.getRealName());
		result.setCompleteName(user.getName() + " (" + user.getRealName() + ")");
		return result;
	}

	@Override
	public void savePassword(String signature, String password) {
		UserDto user = system.getUserFromSession(signature);
		user.setChangePassword(true);
		user.setPassword(password);
		system.saveUser(signature, user);
	}

	@Override
	public List<MenuDv> listFastMenu(String key)
			throws IllegalArgumentException {
		List<MenuDv> result = new ArrayList<MenuDv>();
		List<FastMenuDto> listMenu = system.listAllFastMenu(key);
		for (FastMenuDto fastMenu : listMenu) {
			MenuDv menu = new MenuDv();
			menu.setId(fastMenu.getMenuId());
			menu.setFastMenuId(fastMenu.getId());
			menu.setTitle(fastMenu.getLongTitle());
			menu.setStatus(fastMenu.getActive() == 1);
			result.add(menu);
		}
		Collections.sort(result, new Comparator<MenuDv>() {

			@Override
			public int compare(MenuDv arg0, MenuDv arg1) {
				return arg0.getTitle().compareTo(arg1.getTitle());
			}
		});
		int nr = 1;
		for (MenuDv menu : result) {
			menu.setNr(nr++);
		}
		return result;
	}

	@Override
	public void saveFastMenu(String key, List<MenuDv> fastMenus)
			throws IllegalArgumentException {
		for (MenuDv fastMenu : fastMenus) {
			if (fastMenu.getFastMenuId() == 0 && fastMenu.getStatus()) {
				FastMenuDto dto = new FastMenuDto();
				dto.setMenuId(fastMenu.getId());
				system.saveFastMenu(key, dto);
			}
			if (fastMenu.getFastMenuId() != 0 && !fastMenu.getStatus()) {
				system.deleteFastMenu(fastMenu.getFastMenuId());
			}
		}

	}

}
