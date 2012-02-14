package edu.cornell.dendro.corina.setupwizard;


public class WizardFirstRunWelcome extends AbstractWizardPanel {


	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public WizardFirstRunWelcome() {
		super("Welcome to Corina", 
				"This appears to be the first time that you have run " +
				"Corina on this computer.  To help you get up and running quickly " +
				"this wizard will take you through the steps that are " +
				"necessary to configure Corina.  You may close this wizard at " +
				"any time and configure Corina manually in the preferences windows, " +
				"but until the Corina server details are supplied the majority of " +
				"functions will be unavailable to you.");
		
		


	}

}