package jif.ide.wizards;

import java.util.ArrayList;
import java.util.List;

import jif.ide.JifPlugin;
import jif.ide.JifPluginInfo;
import polyglot.ide.PluginInfo;
import polyglot.ide.common.BuildpathEntry;
import polyglot.ide.wizards.AbstractNewProjectWizard;
import polyglot.ide.wizards.LibraryResource;

public class JifNewProjectWizard extends AbstractNewProjectWizard {

  protected JifNewProjectWizardPageTwo pageTwo;

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
    pageTwo.setTitle(pluginInfo.langName() + " Settings");
    pageTwo.setDescription("Define the " + pluginInfo.langName()
        + " build settings.");

    addPage(pageTwo);
  }

  @Override
  protected List<BuildpathEntry> extraBuildpathEntries() {
    List<BuildpathEntry> result = new ArrayList<>();

    // Add entries for the classpath.
    List<LibraryResource> classpathResources = pageTwo.getClasspathEntries();
    if (classpathResources != null) {
      for (LibraryResource lr : classpathResources)
        result.add(new BuildpathEntry(BuildpathEntry.LIB, lr.getName()));
    }

    // Add entries for the sigpath.
    List<LibraryResource> sigpathResources = pageTwo.getSigpathEntries();
    if (sigpathResources != null) {
      for (LibraryResource lr : sigpathResources)
        result.add(new BuildpathEntry(JifPlugin.SIGPATH, BuildpathEntry.LIB, lr
            .getName()));
    }

    return result;
  }
}
