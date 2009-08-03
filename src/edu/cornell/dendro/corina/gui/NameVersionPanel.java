package edu.cornell.dendro.corina.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import edu.cornell.dendro.corina.formats.Metadata;
import edu.cornell.dendro.corina.sample.Sample;
import edu.cornell.dendro.corina.tridasv2.LabCode;
import edu.cornell.dendro.corina.tridasv2.LabCodeFormatter;

public class NameVersionPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	/** The name of the series */
	private JTextComponent seriesName;
	/** The version to be applied to the new series */
	private JTextComponent versionName;
	/** The justification */
	private JTextComponent justification;
	
	/**
	 * Create a new panel for the given sample, without a justification box
	 * @param sample
	 */
	public NameVersionPanel(Sample sample) {
		this(sample, false);
	}
	
	/**
	 * Create a new panel for the given sample
	 * 
	 * @param sample
	 * @param showJustification true if we have a justification box
	 */
	public NameVersionPanel(Sample sample, boolean showJustification) {
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(2, 2, 5, 5));

		// Create name components
		JLabel l = new JLabel("Series code:  ");

		JLabel prefix = new JLabel("C-XXX-X-X-X-");

		// make the prefix more relevant if we have a labcode
		if (sample.hasMeta(Metadata.LABCODE)) {
			prefix.setText(LabCodeFormatter.getSeriesPrefixFormatter().format(
					sample.getMeta(Metadata.LABCODE, LabCode.class))
					+ "- ");
		}

		JTextField name = new JTextField(sample.getSeries().getTitle());
		name.setColumns(10);
		seriesName = name;
		prefix.setLabelFor(seriesName);

		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new BorderLayout());
		titlePanel.add(prefix, BorderLayout.WEST);
		titlePanel.add(seriesName, BorderLayout.CENTER);

		// Create version components
		JLabel l2 = new JLabel("Version:");
		JTextField version = new JTextField("1");
		version.setColumns(20);
		versionName = version;
		l.setLabelFor(versionName);

		// Add items to panel
		p.add(l);
		p.add(titlePanel);
		p.add(l2);
		p.add(versionName);

		// kludge to make this panel resize nicely
		setLayout(new BorderLayout());
		add(p, BorderLayout.NORTH);
		
		if(showJustification)
			add(getJustificationPanel(), BorderLayout.CENTER);
	}
	
	private JPanel getJustificationPanel() {
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
		
		JLabel l = new JLabel("Justification:");
		JTextArea text = new JTextArea("", 2, 0);
		text.setLineWrap(true);
		text.setWrapStyleWord(true);

		justification = text;
		
		JPanel topLabel = new JPanel();
		topLabel.setLayout(new BoxLayout(topLabel, BoxLayout.Y_AXIS));
		topLabel.add(l);
		topLabel.add(Box.createVerticalGlue());
		
		p.add(topLabel);
		p.add(Box.createHorizontalStrut(8));
		p.add(new JScrollPane(text));

		p.setBorder(BorderFactory.createEmptyBorder(6, 0, 6, 0));
		return p;
	}
	
	/**
	 * @return true if the series name box has characters in it
	 */
	public boolean hasName() {
		return seriesName.getText().length() > 0;
	}

	/**
	 * @return true if the series version box has characters in it
	 */
	public boolean hasVersion() {
		return versionName.getText().length() > 0;
	}
	
	/**
	 * @return true if a justificaiton exists and has characters in it
	 */
	public boolean hasJustification() {
		return justification != null && justification.getText() != null && justification.getText().length() > 0;
	}

	/**
	 * Get the name of the series
	 * @return The name of the series
	 */
	public String getName() {
		return seriesName.getText();
	}
	
	/**
	 * @return The version of the series
	 */
	public String getVersion() {
		return versionName.getText();
	}
	
	/**
	 * @return the justification
	 */
	public String getJustification() {
		return justification.getText();
	}
	
	/**
	 * Change the focus to the name panel
	 */
	public void focusName() {
		seriesName.requestFocusInWindow();
	}
}
