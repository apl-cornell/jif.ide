package jif.ide;

import polyglot.ide.JLPlugin;
import polyglot.ide.common.BuildpathEntry;

/**
 * Plug-in class for Jif language support in Eclipse.
 */
public class JifPlugin extends JLPlugin {

  public static final BuildpathEntry.Kind SIGPATH = BuildpathEntry.Kind.get(
      JifPlugin.class, "sigpath");

  // The plug-in ID
  public static final String PLUGIN_ID = "jif.ide"; //$NON-NLS-1$

}
