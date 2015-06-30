package jif.ide.editors;

import polyglot.ide.editors.ColorManager;
import polyglot.ide.editors.Editor;
import polyglot.ide.editors.SourceViewerConfiguration;

public class JifSourceViewerConfiguration extends SourceViewerConfiguration {

  public JifSourceViewerConfiguration(Editor editor, ColorManager colorManager) {
    super(editor, colorManager);
  }

  @Override
  public JifReconcilingStrategy getReconcilingStrategy() {
    return new JifReconcilingStrategy(editor);
  }
}
