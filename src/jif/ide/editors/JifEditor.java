package jif.ide.editors;

import jif.ExtensionInfo;
import polyglot.ide.editors.AbstractEditor;
import polyglot.ide.editors.SourceViewerConfiguration;

public class JifEditor extends AbstractEditor {
	
	@Override
	protected SourceViewerConfiguration createSourceViewerConfiguration() {
	    return new JifSourceViewerConfiguration(this, colorManager);
	}
	
	@Override
	public ExtensionInfo extInfo() {
		return new jif.ExtensionInfo();
	}
}
