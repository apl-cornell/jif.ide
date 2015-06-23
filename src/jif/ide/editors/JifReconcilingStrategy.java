package jif.ide.editors;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;

import jif.ide.common.DefaultClasspathResolver;
import jif.ide.natures.JifNature;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;

import polyglot.frontend.ExtensionInfo;
import polyglot.ide.common.ClasspathEntry.ClasspathEntryType;
import polyglot.ide.common.ClasspathUtil;
import polyglot.ide.common.ErrorUtil;
import polyglot.ide.common.ErrorUtil.Level;
import polyglot.ide.common.ErrorUtil.Style;
import polyglot.ide.editors.Editor;
import polyglot.ide.editors.ReconcilingStrategy;
import polyglot.main.Options;
import polyglot.main.UsageError;

public class JifReconcilingStrategy extends ReconcilingStrategy {

	public JifReconcilingStrategy(Editor editor) {
		super(editor);
	}

	@Override
	protected void setupCompilerOptions(ExtensionInfo extInfo) {
		IProject project = editor.getFile().getProject();
		File classpathFile = project.getFile(ClasspathUtil.CLASSPATH_FILE_NAME)
				.getRawLocation().toFile();
		String classpath = DefaultClasspathResolver.getDefaultClasspath() + File.pathSeparator + ClasspathUtil.parse(classpathFile);
		String sourcepath = project.getFile("src").getRawLocation().toFile()
				.toString();
		String sigpath = DefaultClasspathResolver.getDefaultSigpath() + File.pathSeparator + ClasspathUtil.parse(classpathFile,
				ClasspathEntryType.SIGPATHENTRY);

		try {
			// TODO Need a better way of setting up these options.
			Options options = extInfo.getOptions();
			Options.global = options;
			options.parseCommandLine(new String[] { "-e", "-d", "/tmp", "/dev/null",
					"-classpath", classpath, "-sigcp", sigpath, "-sourcepath",
					sourcepath }, new HashSet<String>());
		} catch (UsageError e) {
			ErrorUtil.handleError(Level.ERROR, "polyglot.ide",
					"Compiler error",
					"An error occurred while configuring the compiler.", e,
					Style.LOG);
		}
	}

	protected boolean checkNature(IProject project) {
		try {
			if (Arrays.asList(project.getDescription().getNatureIds())
					.contains(JifNature.NATURE_ID))
				return true;
		} catch (CoreException e) {
			e.printStackTrace();
		}

		return false;
	}
}
