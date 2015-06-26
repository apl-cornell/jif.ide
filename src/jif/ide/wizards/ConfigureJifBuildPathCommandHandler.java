package jif.ide.wizards;

import jif.ide.JifPluginInfo;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.wizard.Wizard;

import polyglot.ide.PluginInfo;
import polyglot.ide.wizards.ConfigureJLBuildPathCommandHandler;

public class ConfigureJifBuildPathCommandHandler extends
    ConfigureJLBuildPathCommandHandler {

  /**
   * A hook for Eclipse to instantiate this class.
   */
  public ConfigureJifBuildPathCommandHandler() {
    this(JifPluginInfo.INSTANCE);
  }

  /**
   * A hook for extensions to instantiate this class.
   */
  public ConfigureJifBuildPathCommandHandler(PluginInfo pluginInfo) {
    super(pluginInfo);
  }

  @Override
  protected Wizard getWizard(IProject project) {
    return new ConfigureJifBuildPathWizard(pluginInfo, project);
  }
}
