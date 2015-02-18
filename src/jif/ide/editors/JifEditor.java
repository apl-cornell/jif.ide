package jif.ide.editors;

import polyglot.frontend.ExtensionInfo;
import polyglot.ide.editors.JLEditor;

public class JifEditor extends JLEditor {
	@Override
	  public ExtensionInfo extInfo() {
	    return new jif.ExtensionInfo();
	  }
}
