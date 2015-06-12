package org.kembang.editor.client;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.DoubleBox;

public class DoubleTextBox extends DoubleBox {

	NumberFormat nf = NumberFormat.getFormat("#,##0.00;(#,##0.00)");
	Boolean dontProcess = false;

	public DoubleTextBox() {
		//
		addKeyPressHandler(new KeyPressHandler() {

			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (!Character.isDigit(event.getCharCode())
						&& event.getNativeEvent().getKeyCode() != KeyCodes.KEY_TAB
						&& event.getNativeEvent().getKeyCode() != KeyCodes.KEY_BACKSPACE
						&& event.getNativeEvent().getKeyCode() != KeyCodes.KEY_LEFT
						&& event.getNativeEvent().getKeyCode() != KeyCodes.KEY_RIGHT
						&& event.getNativeEvent().getKeyCode() != KeyCodes.KEY_DELETE
						&& event.getNativeEvent().getKeyCode() != KeyCodes.KEY_BACKSPACE) {
					((DoubleBox) event.getSource()).cancelKey();
				} else {
					if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_LEFT
							|| event.getNativeEvent().getKeyCode() == KeyCodes.KEY_RIGHT) {
						dontProcess = true;
					}
				}
			}
		});

		//
		addKeyUpHandler(new KeyUpHandler() {

			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (!dontProcess) {
					int index = getCursorPos();
					int indexAdded = formatValue(index);
					setCursorPos(index + indexAdded);
				} else {
					dontProcess = false;
				}
			}
		});

	}

	@Override
	public void setValue(Double value){
		setText(nf.format(value));
	}
	
	private int formatValue(int curPos) {
		int result = 0;
		String text = getText();
		int textLength = text.length();
		int dotPos = textLength;
		//
		String dotText[] = text.split("\\.");
		if (dotText.length > 2) {
			for (int i = 0; i < dotText.length; i++) {
				if (i == 0) {
					text = dotText[i] + ".";
				} else {
					text += dotText[i];
				}
			}
		}
		if (dotText.length > 1) {
			dotPos = dotText[0].length();
		}
		//
		text = getText().replace(",", "");
		//
		String textFormat = nf.format(nf.parse(text));
		int newLength = textFormat.length();
		if (textLength != 1 && dotPos >= curPos) {
			result = newLength - textLength;
		}
		setText(textFormat);
		return result;
	}
}
