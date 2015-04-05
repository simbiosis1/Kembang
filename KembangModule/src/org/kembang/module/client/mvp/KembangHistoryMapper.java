package org.kembang.module.client.mvp;

import org.kembang.module.client.framework.MainPlace;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;

/**
 * PlaceHistoryMapper interface is used to attach all places which the
 * PlaceHistoryHandler should be aware of. This is done via the @WithTokenizers
 * annotation or by extending PlaceHistoryMapperWithFactory and creating a
 * separate TokenizerFactory.
 */
@WithTokenizers({ MainPlace.Tokenizer.class })
public interface KembangHistoryMapper extends PlaceHistoryMapper {
}
