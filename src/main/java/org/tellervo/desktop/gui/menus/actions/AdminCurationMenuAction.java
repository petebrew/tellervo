package org.tellervo.desktop.gui.menus.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.tellervo.desktop.admin.SampleCuration;
import org.tellervo.desktop.admin.SetPasswordUI;
import org.tellervo.desktop.admin.view.PermissionByEntityDialog;
import org.tellervo.desktop.gui.widgets.TridasEntityPickerDialog;
import org.tellervo.desktop.gui.widgets.TridasEntityPickerPanel.EntitiesAccepted;
import org.tellervo.desktop.ui.Builder;
import org.tellervo.desktop.util.labels.ui.LabelPrintingDialog;
import org.tridas.interfaces.ITridas;
import org.tridas.schema.TridasSample;

public class AdminCurationMenuAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	
	public AdminCurationMenuAction() {
        super("Curation Menu", Builder.getIcon("curation.png.png", 22));
		putValue(SHORT_DESCRIPTION, "Curation Menu");
        //putValue(MNEMONIC_KEY,I18n.getMnemonic("menus.file.new")); 
        //putValue(ACCELERATOR_KEY, I18n.getKeyStroke("menus.file.new"));

    }

	@Override
	public void actionPerformed(ActionEvent e) {
		//TODO Auto-generated method stub
	}		
}
