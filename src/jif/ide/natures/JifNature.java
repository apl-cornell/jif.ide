package jif.ide.natures;

import jif.ide.JifPluginInfo;
import polyglot.ide.PluginInfo;
import polyglot.ide.natures.JLNature;

public class JifNature extends JLNature {

  /**
   * A hook for Eclipse to instantiate this class.
   */
  public JifNature() {
    this(JifPluginInfo.INSTANCE);
  }

  /**
   * A hook for extensions to instantiate this class.
   */
  public JifNature(PluginInfo pluginInfo) {
    super(pluginInfo);
  }

}
