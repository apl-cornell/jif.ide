package jif.ide.wizards;

import java.util.ArrayList;
import java.util.List;

import jif.ide.JifPlugin;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.wizard.Wizard;

import polyglot.ide.common.BuildpathEntry;
import polyglot.ide.common.BuildpathUtil;
import polyglot.ide.common.ErrorUtil;
import polyglot.ide.common.ErrorUtil.Level;
import polyglot.ide.common.ErrorUtil.Style;
import polyglot.ide.wizards.LibraryResource;

public class JifConfigureBuildPathWizard extends Wizard {
  IProject project;
  NewJifProjectWizardPageTwo buildConfigurationPage;

  JifConfigureBuildPathWizard(IProject project) {
    this.project = project;
  }

  @Override
  public void addPages() {
    buildConfigurationPage =
        new NewJifProjectWizardPageTwo("buildConfigWizardPage", project);
    buildConfigurationPage.setTitle("Jif Settings");
    buildConfigurationPage.setDescription("Define the Jif build settings.");
    addPage(buildConfigurationPage);
  }

  @Override
  public boolean performFinish() {
    updateBuildpathFile();
    try {
      project.refreshLocal(IResource.DEPTH_ONE, new NullProgressMonitor());
      project.build(IncrementalProjectBuilder.FULL_BUILD,
          new NullProgressMonitor());

      return true;
    } catch (CoreException e) {
      return false;
    }
  }

  private boolean updateBuildpathFile() {
    List<BuildpathEntry> entries = new ArrayList<>();

    // src is default classpath entry
    entries.add(new BuildpathEntry(BuildpathEntry.SRC, "src"));

    if (buildConfigurationPage.getClasspathEntries() != null)
      for (LibraryResource libraryResource : buildConfigurationPage
          .getClasspathEntries())
        entries.add(new BuildpathEntry(BuildpathEntry.LIB, libraryResource
            .getName()));

    // bin is default classpath entry
    entries.add(new BuildpathEntry(BuildpathEntry.OUTPUT, "bin"));

    if (buildConfigurationPage.getSigpathEntries() != null)
      for (LibraryResource libraryResource : buildConfigurationPage
          .getSigpathEntries())
        entries.add(new BuildpathEntry(JifPlugin.SIGPATH,
            BuildpathEntry.LIB, libraryResource.getName()));

    try {
      BuildpathUtil.createBuildpathFile(project, entries);
      return true;
    } catch (Exception e) {
      ErrorUtil.handleError(Level.WARNING, "jif.ide",
          "Error updating dot-classpath file. Please check file permissions",
          e.getCause(), Style.BLOCK);
      return false;
    }
  }
}
