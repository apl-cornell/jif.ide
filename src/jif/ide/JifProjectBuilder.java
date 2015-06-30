package jif.ide;

import polyglot.ide.JLProjectBuilder;
import polyglot.ide.PluginInfo;

public class JifProjectBuilder extends JLProjectBuilder {

  public JifProjectBuilder() {
    this(JifPluginInfo.INSTANCE);
  }

  public JifProjectBuilder(PluginInfo pluginInfo) {
    super(pluginInfo);
  }

}
