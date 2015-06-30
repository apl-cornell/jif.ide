package jif.ide;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IProject;

import jif.ExtensionInfo;
import polyglot.ide.JLPluginInfo;
import polyglot.ide.common.BuildpathUtil;

public class JifPluginInfo extends JLPluginInfo {

  @SuppressWarnings("hiding")
  public static final JifPluginInfo INSTANCE = new JifPluginInfo();

  @Override
  public String pluginID() {
    return "jif.ide";
  }

  @Override
  public String langName() {
    return "Jif";
  }

  @Override
  public String langShortName() {
    return "Jif";
  }

  @Override
  public ExtensionInfo makeExtInfo() {
    return new ExtensionInfo();
  }

  @Override
  public String natureID() {
    return "jif.ide.jifnature";
  }

  @Override
  public String builderId() {
    return "jif.ide.jifBuilder";
  }

  @Override
  protected List<String> baseClasspath() {
    return new ArrayList<>(Arrays.asList(getPluginPath("/lib/jif-rt/"),
        getPluginPath("/lib/jifrt.jar"), getPluginPath("/lib/jif-lib/"),
        getPluginPath("/lib/jiflib.jar")));
  }

  protected List<String> baseSigpath() {
    return new ArrayList<>(Arrays.asList(getPluginPath("/lib/jif-sig/"),
        getPluginPath("/lib/jifsig.jar")));
  }

  @Override
  public List<String> addCompilerArgs(boolean validateOnly, IProject project,
      Collection<String> sourceFiles, List<String> result) {
    File buildpathFile = BuildpathUtil.buildpathFile(project);
    List<String> sigpath =
        BuildpathUtil.parse(this, buildpathFile, JifPlugin.SIGPATH);

    // Append base sigpath.
    sigpath.addAll(baseSigpath());

    result.add("-e");

    if (!sigpath.isEmpty()) {
      result.addAll(
          Arrays.asList("-sigcp", BuildpathUtil.flattenPath(sigpath)));
    }
    
    return super.addCompilerArgs(validateOnly, project, sourceFiles, result);
  }

}
