package org.kembang.module.client.mvp;

import org.kembang.module.client.framework.MainActivity;
import org.kembang.module.client.framework.MainPlace;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

public abstract class KembangActivityMapper implements ActivityMapper {

	private KembangClientFactory clientFactory;

	/**
	 * AppActivityMapper associates each Place with its corresponding
	 * {@link Activity}
	 * 
	 * @param clientFactory
	 *            Factory to be passed to activities
	 */
	public KembangActivityMapper(KembangClientFactory clientFactory) {
		super();
		this.clientFactory = clientFactory;
	}

	/**
	 * @return
	 */
	private MainActivity createBlankActivity() {
		return new MainActivity(new MainPlace(""), clientFactory);
	}

	/**
	 * Map each Place to its corresponding Activity. This would be a great use
	 * for GIN.
	 */
	@Override
	public Activity getActivity(Place place) {
		//
		if (place instanceof MainPlace) {
			return new MainActivity((MainPlace) place, clientFactory);
		} else {
			//
			if (clientFactory.isUserAllowedGoto(place)) {
				return createActivity(place);
			} else {
				return createBlankActivity();
			}
			//
		}
	}

	public KembangClientFactory getClientFactory() {
		return clientFactory;
	}

	public abstract Activity createActivity(Place place);

}
