package jif.ide;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import jif.ide.common.DefaultClasspathResolver;
import polyglot.ide.JLProjectBuilder;
import polyglot.ide.PluginInfo;
import polyglot.ide.common.BuildpathUtil;

public class JifProjectBuilder extends JLProjectBuilder {

  public JifProjectBuilder() {
    this(JifPluginInfo.INSTANCE);
  }

  public JifProjectBuilder(PluginInfo pluginInfo) {
    super(pluginInfo);
  }

  @Override
  protected List<String> compilerArgs(Set<String> filesToCompile) {
    File buildpathFile = buildpathFile();
    String classpath =
        DefaultClasspathResolver.getDefaultClasspath() + File.pathSeparator
        + BuildpathUtil.parse(pluginInfo, buildpathFile, "");
    String sigpath =
        DefaultClasspathResolver.getDefaultSigpath() + File.pathSeparator
        + BuildpathUtil.parse(pluginInfo, buildpathFile, JifPlugin.SIGPATH, "");
    String binPath = outputLocation();

    List<String> result =
        new ArrayList<>(Arrays.asList("-d", binPath, "-classpath", classpath,
            "-sigcp", sigpath));
    result.addAll(filesToCompile);

    return result;
  }
}
