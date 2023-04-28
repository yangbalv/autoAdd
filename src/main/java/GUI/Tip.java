package GUI;

import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public  class Tip implements FocusListener{
	private String hindentext;
	private JTextField textField;
	public Tip(JTextField jtextfield, String hindentext) {
		this.hindentext = hindentext;
		this.textField = jtextfield;
		jtextfield.setText(hindentext);
	}
	

	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		String temp = textField.getText();
		if(temp.equals(hindentext)) {
			textField.setText("");
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
		String temp = textField.getText();
		if(temp.equals("")) {
			textField.setText(hindentext);
		}
	}
}
