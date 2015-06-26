package jif.ide.wizards;

import org.eclipse.core.resources.IProject;

import polyglot.ide.PluginInfo;
import polyglot.ide.wizards.JLConfigureBuildPathWizard;

public class JifConfigureBuildPathWizard extends JLConfigureBuildPathWizard {

  protected JifConfigureBuildPathWizard(PluginInfo pluginInfo, IProject project) {
    super(pluginInfo, project);
  }

  @Override
  public void addPages() {
    buildConfigurationPage =
        new JifNewProjectWizardPageTwo(pluginInfo, "buildConfigWizardPage",
            project);
    addPage(buildConfigurationPage);
  }

}
