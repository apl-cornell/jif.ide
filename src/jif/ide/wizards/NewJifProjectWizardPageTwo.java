package jif.ide.wizards;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jif.ide.JifPlugin;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import polyglot.ide.PluginInfo;
import polyglot.ide.common.BuildpathEntry;
import polyglot.ide.common.BuildpathUtil;
import polyglot.ide.wizards.LibraryResource;
import polyglot.ide.wizards.LibrarySelector;
import polyglot.ide.wizards.NewJLProjectWizardPageTwo;

public class NewJifProjectWizardPageTwo extends NewJLProjectWizardPageTwo {
  private LibrarySelector sigpathSelector;

  public NewJifProjectWizardPageTwo(PluginInfo pluginInfo, String name) {
    super(pluginInfo, name);
  }

  public NewJifProjectWizardPageTwo(PluginInfo pluginInfo, String name,
      IProject project) {
    super(pluginInfo, name, project);
  }

  @Override
  public void createControl(Composite parent) {
    Composite composite = new Composite(parent, SWT.NONE);
    composite.setFont(parent.getFont());

    GridLayout layout = new GridLayout();
    layout.marginWidth = 0;
    layout.marginHeight = 0;
    layout.numColumns = 1;
    composite.setLayout(layout);

    final TabFolder tabFolder = new TabFolder(composite, SWT.BORDER);
    tabFolder.setLayoutData(new GridData(GridData.FILL_BOTH));
    tabFolder.setFont(composite.getFont());

    classpathSelector = new LibrarySelector(tabFolder);
    if (project != null) classpathSelector.setItems(extractClasspathEntries());

    TabItem item1 = new TabItem(tabFolder, SWT.NONE);
    item1.setText("&Classpath");
    item1.setControl(classpathSelector);

    sigpathSelector = new LibrarySelector(tabFolder);
    if (project != null) sigpathSelector.setItems(extractSigpathEntries());

    TabItem item2 = new TabItem(tabFolder, SWT.NONE);
    item2.setText("&Sigpath");
    item2.setControl(sigpathSelector);

    Dialog.applyDialogFont(composite);
    setControl(composite);
  }

  private List<LibraryResource> extractSigpathEntries() {
    File buildpathFile =
        project.getFile(BuildpathUtil.BUILDPATH_FILE_NAME).getRawLocation()
        .toFile();
    List<BuildpathEntry> entries =
        BuildpathUtil
        .getBuildpathEntries(pluginInfo, buildpathFile, JifPlugin.SIGPATH);
    List<LibraryResource> items = new ArrayList<>();

    for (BuildpathEntry entry : entries)
      items.add(new LibraryResource(entry.getPath()));

    return items;
  }

  @Override
  public List<LibraryResource> getClasspathEntries() {
    return classpathSelector.getItems();
  }

  public List<LibraryResource> getSigpathEntries() {
    return sigpathSelector.getItems();
  }
}
