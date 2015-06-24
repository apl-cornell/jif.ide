package jif.ide.editors;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;

import jif.ide.JifPlugin;
import jif.ide.common.DefaultClasspathResolver;
import jif.ide.natures.JifNature;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;

import polyglot.frontend.ExtensionInfo;
import polyglot.ide.common.BuildpathUtil;
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
    File buildpathFile =
        project.getFile(BuildpathUtil.BUILDPATH_FILE_NAME).getRawLocation()
            .toFile();
    String classpath =
        DefaultClasspathResolver.getDefaultClasspath() + File.pathSeparator
            + BuildpathUtil.parse(buildpathFile, "");
    String sourcepath =
        project.getFile("src").getRawLocation().toFile().toString();
    String sigpath =
        DefaultClasspathResolver.getDefaultSigpath() + File.pathSeparator
            + BuildpathUtil.parse(buildpathFile, JifPlugin.SIGPATH, "");

    try {
      // TODO Need a better way of setting up these options.
      Options options = extInfo.getOptions();
      Options.global = options;
      options.parseCommandLine(
          new String[] { "-e", "-d", "/tmp", "/dev/null", "-classpath",
              classpath, "-sigcp", sigpath, "-sourcepath", sourcepath },
          new HashSet<String>());
    } catch (UsageError e) {
      ErrorUtil.handleError(Level.ERROR, "polyglot.ide", "Compiler error",
          "An error occurred while configuring the compiler.", e, Style.LOG);
    }
  }

  @Override
  protected boolean checkNature(IProject project) {
    try {
      if (Arrays.asList(project.getDescription().getNatureIds()).contains(
          JifNature.NATURE_ID)) return true;
    } catch (CoreException e) {
      e.printStackTrace();
    }

    return false;
  }
}
