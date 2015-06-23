package jif.ide.common;

import java.io.File;
import java.net.URI;
import java.net.URL;

import jif.ide.JifPlugin;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

import polyglot.ide.common.ErrorUtil;
import polyglot.ide.common.ErrorUtil.Level;
import polyglot.ide.common.ErrorUtil.Style;

public class DefaultClasspathResolver {

  private static String getPath(String filename) {
    Bundle bundle = Platform.getBundle(JifPlugin.PLUGIN_ID);
    URL url = FileLocator.find(bundle, new Path(filename), null);

    if (url != null) {
      try {
        URI uri = FileLocator.toFileURL(url).toURI();
        return uri.getPath();
      } catch (Exception e) {
        ErrorUtil.handleError(Level.WARNING, "jif.ide",
            "Unable to include default classpath entries.", e.getCause(),
            Style.BLOCK);
      }
    }

    return "";
  }

  public static String getDefaultClasspath() {
    return getPath("/lib/jif-rt/") + File.pathSeparator
        + getPath("/lib/jifrt.jar") + File.pathSeparator
        + new Path("/lib/jif-lib/") + File.pathSeparator
        + new Path("/lib/jiflib.jar");
  }

  public static String getDefaultSigpath() {
    return getPath("/lib/jif-sig/") + File.pathSeparator
        + getPath("/lib/jifsig.jar");
  }
}
