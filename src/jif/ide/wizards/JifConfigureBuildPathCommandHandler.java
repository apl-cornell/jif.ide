package jif.ide.wizards;

import jif.ide.JifPluginInfo;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.wizard.Wizard;

import polyglot.ide.PluginInfo;
import polyglot.ide.wizards.JLConfigureBuildPathCommandHandler;

public class JifConfigureBuildPathCommandHandler extends
    JLConfigureBuildPathCommandHandler {

  /**
   * A hook for Eclipse to instantiate this class.
   */
  public JifConfigureBuildPathCommandHandler() {
    this(JifPluginInfo.INSTANCE);
  }

  /**
   * A hook for extensions to instantiate this class.
   */
  public JifConfigureBuildPathCommandHandler(PluginInfo pluginInfo) {
    super(pluginInfo);
  }

  @Override
  protected Wizard getWizard(IProject project) {
    return new JifConfigureBuildPathWizard(pluginInfo, project);
  }
}
