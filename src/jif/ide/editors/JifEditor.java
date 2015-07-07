package jif.ide.editors;

import jif.ide.JifPluginInfo;
import polyglot.ide.PluginInfo;
import polyglot.ide.editors.JLEditor;

public class JifEditor extends JLEditor {

  public JifEditor() {
    this(JifPluginInfo.INSTANCE);
  }

  public JifEditor(PluginInfo pluginInfo) {
    super(pluginInfo);
  }

}
