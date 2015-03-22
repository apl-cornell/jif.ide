package jif.ide.wizards;

import java.util.ArrayList;
import java.util.List;

import jif.ide.natures.JifNature;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;

import polyglot.ide.common.ClasspathEntry;
import polyglot.ide.common.ClasspathEntry.ClasspathEntryKind;
import polyglot.ide.common.ClasspathEntry.ClasspathEntryType;
import polyglot.ide.common.ClasspathUtil;
import polyglot.ide.common.ErrorUtil;
import polyglot.ide.common.ErrorUtil.Level;
import polyglot.ide.common.ErrorUtil.Style;
import polyglot.ide.wizards.LibraryResource;
import polyglot.ide.wizards.NewJLProjectWizard;

public class NewJifProjectWizard extends NewJLProjectWizard {

	@Override
	protected String getTitle() {
		return "New Jif Project";
	}
	
	@Override
	protected String getNature() {
	    return JifNature.NATURE_ID;
	}
	
	@Override
	public void addPages() {
		pageOne = new WizardNewProjectCreationPage("newJifProjectPageOne") {
			@Override
			public void createControl(Composite parent) {
				super.createControl(parent);
				createWorkingSetGroup(
						(Composite) getControl(),
						selection,
						new String[] { "org.eclipse.ui.resourceWorkingSetPage" });
				Dialog.applyDialogFont(getControl());
			}
		};
		pageOne.setTitle("Create a Jif Project");
		pageOne.setDescription("Enter a project name.");
		addPage(pageOne);

		pageTwo = new NewJifProjectWizardPageTwo("newJifProjectPageTwo");
		pageTwo.setTitle("Jif Settings");
		pageTwo.setDescription("Define the Jif build settings.");

		addPage(pageTwo);
	}

	@Override
	protected void createClasspathFile() {
		List<ClasspathEntry> entries = new ArrayList<>();

		// src is default classpath entry
		entries.add(new ClasspathEntry(ClasspathEntryKind.SRC, "src"));

		if (pageTwo.getClasspathEntries() != null)
			for (LibraryResource libraryResource : pageTwo
					.getClasspathEntries())
				entries.add(new ClasspathEntry(ClasspathEntryKind.LIB,
						libraryResource.getName()));

		// bin is default classpath entry
		entries.add(new ClasspathEntry(ClasspathEntryKind.OUTPUT, "bin"));

		if (((NewJifProjectWizardPageTwo) pageTwo).getSigpathEntries() != null)
			for (LibraryResource libraryResource : ((NewJifProjectWizardPageTwo) pageTwo)
					.getSigpathEntries())
				entries.add(new ClasspathEntry(ClasspathEntryKind.LIB,
						libraryResource.getName(),
						ClasspathEntryType.SIGPATHENTRY));

		try {
			ClasspathUtil.createClasspathFile(project, entries);
		} catch (Exception e) {
			ErrorUtil
					.handleError(
							Level.WARNING,
							"jif.ide",
							"Error creating dot-classpath file. Please check file permissions",
							e.getCause(), Style.BLOCK);
		}
	}

	@Override
	protected String getBuilderId() {
		return "jif.ide.builder.jifBuilder";
	}
}