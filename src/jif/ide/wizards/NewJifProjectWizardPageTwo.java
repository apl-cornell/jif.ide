package jif.ide.wizards;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import polyglot.ide.common.ClasspathEntry;
import polyglot.ide.common.ClasspathEntry.ClasspathEntryType;
import polyglot.ide.common.ClasspathUtil;
import polyglot.ide.wizards.LibraryResource;
import polyglot.ide.wizards.LibrarySelector;
import polyglot.ide.wizards.NewJLProjectWizardPageTwo;

public class NewJifProjectWizardPageTwo extends NewJLProjectWizardPageTwo {
  private LibrarySelector sigpathSelector;

  public NewJifProjectWizardPageTwo(String name) {
    super(name);
  }

  public NewJifProjectWizardPageTwo(String name, IProject project) {
    this(name);
    this.project = project;
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
    File classpathFile =
        project.getFile(ClasspathUtil.CLASSPATH_FILE_NAME).getRawLocation()
            .toFile();
    List<ClasspathEntry> entries =
        ClasspathUtil.getClasspathEntries(classpathFile,
            ClasspathEntryType.SIGPATHENTRY);
    List<LibraryResource> items = new ArrayList<>();

    for (ClasspathEntry entry : entries)
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
