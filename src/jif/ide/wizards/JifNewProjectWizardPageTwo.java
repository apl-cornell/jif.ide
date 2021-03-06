package jif.ide.wizards;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jif.ide.JifPlugin;

import org.eclipse.core.resources.IProject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import polyglot.ide.PluginInfo;
import polyglot.ide.common.BuildpathEntry;
import polyglot.ide.common.BuildpathUtil;
import polyglot.ide.wizards.JLNewProjectWizardPageTwo;
import polyglot.ide.wizards.LibraryResource;
import polyglot.ide.wizards.LibrarySelector;

public class JifNewProjectWizardPageTwo extends JLNewProjectWizardPageTwo {
  protected LibrarySelector sigpathSelector;

  public JifNewProjectWizardPageTwo(PluginInfo pluginInfo, String name) {
    this(pluginInfo, name, null);
  }

  public JifNewProjectWizardPageTwo(PluginInfo pluginInfo, String name,
      IProject project) {
    super(pluginInfo, name, project);
  }

  @Override
  public void addExtraBuildPathTabs(TabFolder tabFolder) {
    sigpathSelector = new LibrarySelector(tabFolder);
    if (project != null) {
      sigpathSelector.setItems(getBuildpathResources(JifPlugin.SIGPATH));
    }

    TabItem item2 = new TabItem(tabFolder, SWT.NONE);
    item2.setText("&Sigpath");
    item2.setControl(sigpathSelector);
  }

  protected List<LibraryResource> getBuildpathResources(
      BuildpathEntry.Kind kind) {
    File buildpathFile = project.getFile(BuildpathUtil.BUILDPATH_FILE_NAME)
        .getRawLocation().toFile();
    List<BuildpathEntry> entries =
        BuildpathUtil.getBuildpathEntries(pluginInfo, buildpathFile, kind);
    List<LibraryResource> items = new ArrayList<>();

    for (BuildpathEntry entry : entries) {
      items.add(new LibraryResource(entry.getPath()));
    }

    return items;
  }

  @Override
  public List<BuildpathEntry> getBuildpathEntries() {
    List<BuildpathEntry> result = super.getBuildpathEntries();
    return addBuildpathEntries(JifPlugin.SIGPATH, BuildpathEntry.LIB,
        sigpathSelector.getItems(), result);
  }
}
