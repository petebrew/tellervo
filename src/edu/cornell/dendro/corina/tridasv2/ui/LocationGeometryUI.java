package edu.cornell.dendro.corina.tridasv2.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import org.jdesktop.layout.GroupLayout;
import org.jdesktop.layout.LayoutStyle;

/*
 * LocationGeometry.java
 *
 * Created on August 3, 2009, 11:01 AM
 */



/**
 *
 * @author  peterbrewer
 */
public class LocationGeometryUI extends javax.swing.JPanel {
	private static final long serialVersionUID = 1L;
	
	/** Creates new form LocationGeometry */
    public LocationGeometryUI() {
        initComponents();
     
        // change to degrees using unicode!
        lblLatDeg.setText("\u00B0");
        lblLatDeg1.setText("\u00B0");
        lblLongDeg.setText("\u00B0");
        lblLatDeg2.setText("\u00B0");
        
        // make doubley spinners have a min of 3 decimal places shown and a max of 5 (no more max makes sense) 
        ((JSpinner.NumberEditor) spnDDLat.getEditor()).getFormat().setMinimumFractionDigits(4);
        ((JSpinner.NumberEditor) spnDDLat.getEditor()).getFormat().setMaximumFractionDigits(6);
        ((JSpinner.NumberEditor) spnDDLong.getEditor()).getFormat().setMinimumFractionDigits(4);
        ((JSpinner.NumberEditor) spnDDLong.getEditor()).getFormat().setMaximumFractionDigits(6);
        
        
        ((JSpinner.NumberEditor) spnDMSLatMin.getEditor()).getFormat().setMinimumFractionDigits(0);
        ((JSpinner.NumberEditor) spnDMSLatMin.getEditor()).getFormat().setMaximumFractionDigits(2);
        ((JSpinner.NumberEditor) spnDMSLatSec.getEditor()).getFormat().setMinimumFractionDigits(4);
        ((JSpinner.NumberEditor) spnDMSLatSec.getEditor()).getFormat().setMaximumFractionDigits(6);
        ((JSpinner.NumberEditor) spnDMSLongMin.getEditor()).getFormat().setMinimumFractionDigits(0);
        ((JSpinner.NumberEditor) spnDMSLongMin.getEditor()).getFormat().setMaximumFractionDigits(2);
        ((JSpinner.NumberEditor) spnDMSLongSec.getEditor()).getFormat().setMinimumFractionDigits(4);
        ((JSpinner.NumberEditor) spnDMSLongSec.getEditor()).getFormat().setMaximumFractionDigits(6);
       
        spnDMSLatMin.setModel(new SpinnerNumberModel(0.0d, 0.0d, 59.99d, 0.1));
        spnDMSLatSec.setModel(new SpinnerNumberModel(0.0d, 0.0d, 59.99d, 0.1));
        spnDMSLongMin.setModel(new SpinnerNumberModel(0.0d, 0.0d, 59.99d, 0.1));
        spnDMSLongSec.setModel(new SpinnerNumberModel(0.0d, 0.0d, 59.99d, 0.1));

    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        panelDMS = new JPanel();
        lblLong = new JLabel();
        lblLat = new JLabel();
        spnDMSLatDeg = new JSpinner();
        lblLatDeg = new JLabel();
        spnDMSLatMin = new JSpinner();
        lblLatMin = new JLabel();
        spnDMSLatSec = new JSpinner();
        lblLatSec = new JLabel();
        spnDMSLongDeg = new JSpinner();
        lblLongDeg = new JLabel();
        spnDMSLongMin = new JSpinner();
        lblLongMin = new JLabel();
        spnDMSLongSec = new JSpinner();
        lblLongSec = new JLabel();
        cboLatLongStyle = new JComboBox();
        lblFormat = new JLabel();
        txtGPSFilename = new JTextField();
        lblGPSFilename = new JLabel();
        btnGPSBrowse = new JButton();
        cboDatum = new JComboBox();
        lblDatum = new JLabel();
        cboWaypoint = new JComboBox();
        lblWaypoint = new JLabel();
        radGPS = new JRadioButton();
        radManual = new JRadioButton();
        panelButton = new JPanel();
        btnOK = new JButton();
        btnCancel = new JButton();
        btnViewMap = new JButton();
        lblRadio = new JLabel();
        panelDecDeg = new JPanel();
        lblDDLong = new JLabel();
        lblDDLat = new JLabel();
        spnDDLat = new JSpinner();
        lblLatDeg1 = new JLabel();
        spnDDLong = new JSpinner();
        lblLatDeg2 = new JLabel();

        setMaximumSize(new Dimension(650, 500));
        setMinimumSize(new Dimension(650, 350));

        lblLong.setText("Longitude:");

        lblLat.setText("Latitude:");

        spnDMSLatDeg.setModel(new SpinnerNumberModel(0, -90, 90, 1));

        lblLatDeg.setFont(new Font("Lucida Grande", 0, 18));
        lblLatDeg.setText("''");

        spnDMSLatMin.setModel(new SpinnerNumberModel(0, 0, 59, 1));

        lblLatMin.setFont(new Font("Lucida Grande", 0, 18));
        lblLatMin.setText("'");

        spnDMSLatSec.setModel(new SpinnerNumberModel(0, 0, 59, 1));

        lblLatSec.setFont(new Font("Lucida Grande", 0, 18));
        lblLatSec.setText("''");

        spnDMSLongDeg.setModel(new SpinnerNumberModel(0, -180, 180, 1));

        lblLongDeg.setFont(new Font("Lucida Grande", 0, 18));
        lblLongDeg.setText("''");

        spnDMSLongMin.setModel(new SpinnerNumberModel(0, 0, 59, 1));

        lblLongMin.setFont(new Font("Lucida Grande", 0, 18));
        lblLongMin.setText("'");

        spnDMSLongSec.setModel(new SpinnerNumberModel(0, 0, 59, 1));

        lblLongSec.setFont(new Font("Lucida Grande", 0, 18));
        lblLongSec.setText("''");

        GroupLayout panelDMSLayout = new GroupLayout(panelDMS);
        panelDMS.setLayout(panelDMSLayout);
        panelDMSLayout.setHorizontalGroup(
            panelDMSLayout.createParallelGroup(GroupLayout.LEADING)
            .add(panelDMSLayout.createSequentialGroup()
                .addContainerGap()
                .add(panelDMSLayout.createParallelGroup(GroupLayout.LEADING)
                    .add(lblLat)
                    .add(lblLong))
                .add(76, 76, 76)
                .add(panelDMSLayout.createParallelGroup(GroupLayout.LEADING)
                    .add(panelDMSLayout.createSequentialGroup()
                        .add(spnDMSLatDeg, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(lblLatDeg, GroupLayout.PREFERRED_SIZE, 7, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.UNRELATED)
                        .add(spnDMSLatMin, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(lblLatMin, GroupLayout.PREFERRED_SIZE, 4, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.UNRELATED)
                        .add(spnDMSLatSec, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(lblLatSec))
                    .add(panelDMSLayout.createSequentialGroup()
                        .add(spnDMSLongDeg, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(lblLongDeg)
                        .addPreferredGap(LayoutStyle.UNRELATED)
                        .add(spnDMSLongMin, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(lblLongMin, GroupLayout.PREFERRED_SIZE, 4, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.UNRELATED)
                        .add(spnDMSLongSec, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(lblLongSec)))
                .addContainerGap(132, Short.MAX_VALUE))
        );

        panelDMSLayout.linkSize(new Component[] {spnDMSLatDeg, spnDMSLatMin, spnDMSLatSec, spnDMSLongDeg, spnDMSLongMin, spnDMSLongSec}, GroupLayout.HORIZONTAL);

        panelDMSLayout.linkSize(new Component[] {lblLatDeg, lblLongDeg}, GroupLayout.HORIZONTAL);

        panelDMSLayout.linkSize(new Component[] {lblLatMin, lblLongMin}, GroupLayout.HORIZONTAL);

        panelDMSLayout.linkSize(new Component[] {lblLatSec, lblLongSec}, GroupLayout.HORIZONTAL);

        panelDMSLayout.setVerticalGroup(
            panelDMSLayout.createParallelGroup(GroupLayout.LEADING)
            .add(panelDMSLayout.createSequentialGroup()
                .addContainerGap()
                .add(panelDMSLayout.createParallelGroup(GroupLayout.BASELINE)
                    .add(lblLat)
                    .add(spnDMSLatDeg, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .add(lblLatDeg)
                    .add(spnDMSLatMin, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .add(lblLatMin, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
                    .add(lblLatSec, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
                    .add(spnDMSLatSec, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.RELATED)
                .add(panelDMSLayout.createParallelGroup(GroupLayout.BASELINE)
                    .add(spnDMSLongDeg, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .add(lblLongDeg)
                    .add(lblLongMin, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
                    .add(lblLongSec, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
                    .add(lblLong)
                    .add(spnDMSLongMin, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .add(spnDMSLongSec, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        cboLatLongStyle.setModel(new DefaultComboBoxModel(new String[] { "Decimal degrees", "Degrees, minutes and seconds" }));

        lblFormat.setText("Format:");

        lblGPSFilename.setText("GPS filename:");

        btnGPSBrowse.setText("Browse");

        cboDatum.setModel(new DefaultComboBoxModel(new String[] { "WGS84" }));

        lblDatum.setText("Datum:");

        lblWaypoint.setText("Waypoint:");

        radGPS.setText("using waypoint from GPS");

        radManual.setText("manually");

        btnOK.setText("OK");

        btnCancel.setText("Cancel");

        btnViewMap.setText("View on map");

        GroupLayout panelButtonLayout = new GroupLayout(panelButton);
        panelButton.setLayout(panelButtonLayout);
        panelButtonLayout.setHorizontalGroup(
            panelButtonLayout.createParallelGroup(GroupLayout.LEADING)
            .add(GroupLayout.TRAILING, panelButtonLayout.createSequentialGroup()
                .addContainerGap()
                .add(btnViewMap)
                .addPreferredGap(LayoutStyle.RELATED, 437, Short.MAX_VALUE)
                .add(btnCancel)
                .addPreferredGap(LayoutStyle.RELATED)
                .add(btnOK)
                .addContainerGap())
        );
        panelButtonLayout.setVerticalGroup(
            panelButtonLayout.createParallelGroup(GroupLayout.LEADING)
            .add(GroupLayout.TRAILING, panelButtonLayout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .add(panelButtonLayout.createParallelGroup(GroupLayout.BASELINE)
                    .add(btnOK)
                    .add(btnCancel)
                    .add(btnViewMap))
                .addContainerGap())
        );

        lblRadio.setText("Set location:");

        lblDDLong.setText("Longitude:");

        lblDDLat.setText("Latitude:");
        lblDDLat.setPreferredSize(new Dimension(85, 16));

        spnDDLat.setModel(new SpinnerNumberModel(0.0d, -90.0d, 90.0d, 0.1));

        lblLatDeg1.setFont(new Font("Lucida Grande", 0, 18)); // NOI18N
        lblLatDeg1.setText("''");

        spnDDLong.setModel(new SpinnerNumberModel(0.0d, -180.0d, 180.0d, 0.1));

        lblLatDeg2.setFont(new Font("Lucida Grande", 0, 18)); // NOI18N
        lblLatDeg2.setText("''");

        GroupLayout panelDecDegLayout = new GroupLayout(panelDecDeg);
        panelDecDeg.setLayout(panelDecDegLayout);
        panelDecDegLayout.setHorizontalGroup(
            panelDecDegLayout.createParallelGroup(GroupLayout.LEADING)
            .add(panelDecDegLayout.createSequentialGroup()
                .addContainerGap()
                .add(panelDecDegLayout.createParallelGroup(GroupLayout.LEADING)
                    .add(lblDDLong)
                    .add(lblDDLat, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE))
                .add(panelDecDegLayout.createParallelGroup(GroupLayout.TRAILING)
                    .add(panelDecDegLayout.createSequentialGroup()
                        .add(66, 66, 66)
                        .add(spnDDLat, GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE))
                    .add(panelDecDegLayout.createSequentialGroup()
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(spnDDLong, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(LayoutStyle.RELATED)
                .add(panelDecDegLayout.createParallelGroup(GroupLayout.LEADING)
                    .add(lblLatDeg1, GroupLayout.PREFERRED_SIZE, 7, GroupLayout.PREFERRED_SIZE)
                    .add(lblLatDeg2, GroupLayout.PREFERRED_SIZE, 7, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(164, Short.MAX_VALUE))
        );

        panelDecDegLayout.linkSize(new Component[] {spnDDLat, spnDDLong}, GroupLayout.HORIZONTAL);

        panelDecDegLayout.setVerticalGroup(
            panelDecDegLayout.createParallelGroup(GroupLayout.LEADING)
            .add(panelDecDegLayout.createSequentialGroup()
                .addContainerGap()
                .add(panelDecDegLayout.createParallelGroup(GroupLayout.BASELINE)
                    .add(lblDDLat, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .add(lblLatDeg1)
                    .add(spnDDLat, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .add(10, 10, 10)
                .add(panelDecDegLayout.createParallelGroup(GroupLayout.BASELINE)
                    .add(lblDDLong)
                    .add(lblLatDeg2)
                    .add(spnDDLong, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(148, 148, 148)
                .add(radManual)
                .add(18, 18, 18)
                .add(radGPS, GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
                .add(101, 101, 101))
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(lblRadio, GroupLayout.PREFERRED_SIZE, 617, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(41, Short.MAX_VALUE))
            .add(layout.createSequentialGroup()
                .add(panelDecDeg, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .add(panelButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .add(GroupLayout.TRAILING, layout.createSequentialGroup()
                .add(layout.createParallelGroup(GroupLayout.TRAILING)
                    .add(GroupLayout.LEADING, panelDMS, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(layout.createSequentialGroup()
                        .addContainerGap()
                        .add(layout.createParallelGroup(GroupLayout.LEADING)
                            .add(lblGPSFilename, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)
                            .add(lblWaypoint, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)
                            .add(lblDatum, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)
                            .add(lblFormat, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE))
                        .add(10, 10, 10)
                        .add(layout.createParallelGroup(GroupLayout.LEADING)
                            .add(cboLatLongStyle, 0, 447, Short.MAX_VALUE)
                            .add(cboDatum, 0, 447, Short.MAX_VALUE)
                            .add(cboWaypoint, 0, 447, Short.MAX_VALUE)
                            .add(layout.createSequentialGroup()
                                .addPreferredGap(LayoutStyle.RELATED)
                                .add(txtGPSFilename, GroupLayout.DEFAULT_SIZE, 447, Short.MAX_VALUE)))))
                .addPreferredGap(LayoutStyle.RELATED)
                .add(btnGPSBrowse)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(GroupLayout.BASELINE)
                    .add(lblRadio)
                    .add(radManual)
                    .add(radGPS))
                .add(18, 18, 18)
                .add(layout.createParallelGroup(GroupLayout.BASELINE)
                    .add(lblGPSFilename)
                    .add(btnGPSBrowse)
                    .add(txtGPSFilename, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.RELATED)
                .add(layout.createParallelGroup(GroupLayout.BASELINE)
                    .add(lblWaypoint)
                    .add(cboWaypoint, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.RELATED)
                .add(layout.createParallelGroup(GroupLayout.BASELINE)
                    .add(lblDatum)
                    .add(cboDatum, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.RELATED)
                .add(layout.createParallelGroup(GroupLayout.BASELINE)
                    .add(lblFormat)
                    .add(cboLatLongStyle, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.RELATED)
                .add(panelDMS, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.RELATED)
                .add(panelDecDeg, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.RELATED)
                .add(panelButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>


    
    // Variables declaration - do not modify
    protected JButton btnCancel;
    protected JButton btnGPSBrowse;
    protected JButton btnOK;
    protected JButton btnViewMap;
    protected JComboBox cboDatum;
    protected JComboBox cboLatLongStyle;
    protected JComboBox cboWaypoint;
    protected JLabel lblDDLat;
    protected JLabel lblDDLong;
    protected JLabel lblDatum;
    protected JLabel lblFormat;
    protected JLabel lblGPSFilename;
    protected JLabel lblLat;
    protected JLabel lblLatDeg;
    protected JLabel lblLatDeg1;
    protected JLabel lblLatDeg2;
    protected JLabel lblLatMin;
    protected JLabel lblLatSec;
    protected JLabel lblLong;
    protected JLabel lblLongDeg;
    protected JLabel lblLongMin;
    protected JLabel lblLongSec;
    protected JLabel lblRadio;
    protected JLabel lblWaypoint;
    protected JPanel panelButton;
    protected JPanel panelDMS;
    protected JPanel panelDecDeg;
    protected JRadioButton radGPS;
    protected JRadioButton radManual;
    protected JSpinner spnDDLat;
    protected JSpinner spnDDLong;
    protected JSpinner spnDMSLatDeg;
    protected JSpinner spnDMSLatMin;
    protected JSpinner spnDMSLatSec;
    protected JSpinner spnDMSLongDeg;
    protected JSpinner spnDMSLongMin;
    protected JSpinner spnDMSLongSec;
    protected JTextField txtGPSFilename;
    // End of variables declaration
    
}
