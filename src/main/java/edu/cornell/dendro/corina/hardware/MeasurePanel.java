/*******************************************************************************
 * Copyright (C) 2010 Lucas Madar and Peter Brewer
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 *     Lucas Madar
 *     Peter Brewer
 ******************************************************************************/
package edu.cornell.dendro.corina.hardware;

import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import net.miginfocom.swing.MigLayout;

import org.tridas.schema.NormalTridasUnit;

import edu.cornell.dendro.corina.core.App;
import edu.cornell.dendro.corina.prefs.Prefs.PrefKey;
import edu.cornell.dendro.corina.ui.Alert;
import edu.cornell.dendro.corina.ui.Builder;
import edu.cornell.dendro.corina.ui.I18n;
import edu.cornell.dendro.corina.util.SoundUtil;


public abstract class MeasurePanel extends JPanel implements MeasurementReceiver {

	private static final long serialVersionUID = 1L;
	
	/* audioclips to play... */
	protected AudioClip measure_one;
	protected AudioClip measure_dec;
	protected AudioClip measure_error;
	
	protected JButton btnReset;
	protected JButton btnRecord;
	protected JButton btnQuit;
	protected JLabel txtCurrentValue;
	protected JLabel lastMeasurement;
	protected AbstractSerialMeasuringDevice dev;
	private JPanel panel;
	
	public MeasurePanel(final AbstractSerialMeasuringDevice device) {
		setBorder(null);
			
		dev = device;

		
		setLayout(new MigLayout("insets 0", "[grow][87.00,grow][146.00px][20.00]", "[][]"));
		
		measure_one = SoundUtil.getMeasureSound();
		measure_dec = SoundUtil.getMeasureDecadeSound();
		measure_error = SoundUtil.getMeasureErrorSound();
		
		SoundUtil.playMeasureInitSound();

		
		panel = new JPanel();
		add(panel, "cell 0 0,grow");
		
		btnQuit = new JButton(I18n.getText("menus.edit.stop_measuring"));
		btnQuit.setIcon(Builder.getIcon("stop.png", 22));
		panel.add(btnQuit);
		
				
				btnReset = new JButton("Zero");
				btnReset.setIcon(Builder.getIcon("zero.png", 22));
				panel.add(btnReset);
				
				btnRecord = new JButton("Record");
				btnRecord.setIcon(Builder.getIcon("record.png", 22));
				panel.add(btnRecord);
				btnRecord.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						dev.requestMeasurement();	
					}
				});
				btnReset.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						dev.zeroMeasurement();
						
					}
				});
				
		txtCurrentValue = new JLabel();
		txtCurrentValue.setFont(new Font("Synchro LET", Font.BOLD, 20));
		txtCurrentValue.setHorizontalAlignment(SwingConstants.RIGHT);
		txtCurrentValue.setText("-");
		txtCurrentValue.setBackground(Color.WHITE);
		txtCurrentValue.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		add(txtCurrentValue, "cell 2 0,growx");
		
		
		lastMeasurement = new JLabel("[Last measurement: ]");
		add(lastMeasurement, "cell 0 1 3 1,alignx right,aligny center");
				
				

		
		// Set the device to zero to start with
		
		if(dev!=null)
		{
			dev.setMeasurementReceiver(this);
		
			if(dev.isRequestDataCapable())
			{
				dev.zeroMeasurement();
			}
			// Show/hide data request buttons depending on platform abilities
			btnRecord.setVisible(dev.isRequestDataCapable());
			btnReset.setVisible(dev.isRequestDataCapable());
			txtCurrentValue.setVisible(dev.isCurrentValueCapable());
		}
		else
		{
			btnRecord.setVisible(false);
			btnReset.setVisible(false);
			txtCurrentValue.setVisible(false);
		}
	}
		
	public void receiverUpdateStatus(String status) {
		lastMeasurement.setText(status);
	}
	
	protected Boolean checkNewValueIsValid(Integer value)
	{
		if(value.intValue() == 0) 
		{
			// Value was zero so must be an error
			if(measure_error != null)
				measure_error.play();
			
			lastMeasurement.setText("Error: measurement was zero");

			return false;
		}
		else if (value.intValue() >= 50000)
		{
			// Value was over 5cm so warn user
			if(measure_error != null)
				measure_error.play();
			
			Alert.message("Warning", "This measurement was over 5cm so it will be disregarded!");
			
			lastMeasurement.setText("Error: measurement was too big: " + value +"\u03bc"+"m");

			return false;
			
		}
		else if (value.intValue() < 0)
		{
			// Value was negative so warn user
			if(measure_error != null)
				measure_error.play();
			
			Alert.message("Warning", "This measurement was negative so it will be disregarded!");
			
			lastMeasurement.setText("Error: measurement was negative: " + value +"\u03bc"+"m");

			return false;
			
		}
		
		return true;
	}
	
	public abstract void receiverNewMeasurement(Integer value);
	
	public void cleanup() {
		dev.close();
	}

	@Override
	public void receiverUpdateCurrentValue(Integer value) {
		
		NormalTridasUnit displayUnits = NormalTridasUnit.valueOf(App.prefs.getPref(PrefKey.DISPLAY_UNITS, NormalTridasUnit.HUNDREDTH_MM.value().toString()));
		
		if(displayUnits.equals(NormalTridasUnit.MICROMETRES))
		{
			txtCurrentValue.setText(String.valueOf(value)+" \u03bc"+"m");	
		}
		else if (displayUnits.equals(NormalTridasUnit.HUNDREDTH_MM))
		{
			txtCurrentValue.setText(String.valueOf(value/10));	
		}
	}
	
	public void setDefaultFocus()
	{
		btnRecord.requestFocusInWindow();
	}
	

}



