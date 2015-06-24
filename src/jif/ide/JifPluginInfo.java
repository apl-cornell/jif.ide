package jif.ide;

import jif.ExtensionInfo;
import polyglot.ide.PluginInfo;

public class JifPluginInfo implements PluginInfo {

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

}
