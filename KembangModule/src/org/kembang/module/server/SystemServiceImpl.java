package org.kembang.module.server;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;

import org.kembang.module.client.rpc.SystemService;
import org.kembang.module.shared.CheckModuleDv;
import org.kembang.module.shared.MenuDv;
import org.kembang.module.shared.SimpleBranchDv;
import org.kembang.module.shared.UserDv;
import org.simbiosis.bp.system.ISystemBp;
import org.simbiosis.system.BranchDto;
import org.simbiosis.system.MenuDto;
import org.simbiosis.system.SessionDto;
import org.simbiosis.system.UserDto;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class SystemServiceImpl extends RemoteServiceServlet implements
		SystemService {

	@EJB(lookup = "java:global/SystemBpEar/SystemBpEjb/SystemBp")
	ISystemBp system;

	@Override
	public CheckModuleDv chekSignature(String signature, String place)
			throws IllegalArgumentException {
		CheckModuleDv result = new CheckModuleDv();
		SessionDto session = system.getSession(signature);
		if (session != null) {
			UserDto user = system.getUser(session.getUserId());
			//
			result.setSuccess(true);
			//
			UserDv userDv = new UserDv();
			userDv.setName(session.getUserName());
			userDv.setRealName(session.getUserRealName());
			userDv.setSignature(session.getName());
			userDv.setLogin(true);
			userDv.setLevel(user.getLevel());
			result.setUser(userDv);
			//
			List<MenuDto> menus = system.listUserMenuByModule(signature, place);
			for (MenuDto menuDto : menus) {
				MenuDv menuDv = new MenuDv();
				menuDv.setId(menuDto.getId());
				menuDv.setTitle(menuDto.getGrandParentTitle() + " - "
						+ menuDto.getParentTitle() + " - " + menuDto.getTitle());
				menuDv.setPlace(menuDto.getPlace());
				result.getMenus().add(menuDv);
			}
			//
		}
		return result;
	}

	@Override
	public List<SimpleBranchDv> listSimpleBranch(String session)
			throws IllegalArgumentException {
		List<SimpleBranchDv> result = new ArrayList<SimpleBranchDv>();
		//
		List<BranchDto> branches = system.listBranchByLevel(session);
		for (BranchDto branch : branches) {
			result.add(new SimpleBranchDv(branch.getId(), branch.getCode(),
					branch.getName()));
		}
		return result;
	}
}
