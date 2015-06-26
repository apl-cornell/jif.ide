package jif.ide.wizards;

import jif.ide.JifPluginInfo;
import polyglot.ide.PluginInfo;
import polyglot.ide.wizards.JLNewProjectWizard;

public class JifNewProjectWizard extends JLNewProjectWizard {

  /**
   * A hook for Eclipse to instantiate this class.
   */
  public JifNewProjectWizard() {
    this(JifPluginInfo.INSTANCE);
  }

  /**
   * A hook for extensions to instantiate this class.
   */
  public JifNewProjectWizard(PluginInfo pluginInfo) {
    super(pluginInfo);
  }

  @Override
  public void addExtraPages() {
    pageTwo =
        new JifNewProjectWizardPageTwo(pluginInfo, "new"
            + pluginInfo.langShortName() + "ProjectPageTwo");
    addPage(pageTwo);
  }
}
