package jif.ide.wizards;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.wizard.Wizard;

import polyglot.ide.wizards.ConfigureBuildPathCommandHandler;

public class JifConfigureBuildPathCommandHandler extends
		ConfigureBuildPathCommandHandler {
	
	@Override
	protected Wizard getWizard(IProject project) {
		return new JifConfigureBuildPathWizard(project);
	}
}
