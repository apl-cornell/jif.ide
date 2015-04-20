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
	
	public static String getDefaultClasspath() {
		StringBuilder defaultClasspath = new StringBuilder();

		Bundle bundle = Platform.getBundle(JifPlugin.PLUGIN_ID);
		Path[] paths = { new Path("/lib/jifrt.jar"), new Path("/lib/jiflib.jar") };

		for (Path path : paths) {
			URL url = FileLocator.find(bundle, path, null);

			if (url != null) {
				try {
					URI uri = FileLocator.resolve(url).toURI();
					defaultClasspath.append(uri.getPath()).append(
							File.pathSeparator);
				} catch (Exception e) {
					ErrorUtil.handleError(Level.WARNING, "jif.ide",
							"Unable to include default classpath entries.",
							e.getCause(), Style.BLOCK);
				}
			}
		}

		return defaultClasspath.toString();
	}

	public static String getDefaultSigpath() {
		StringBuilder defaultSigpath = new StringBuilder();

		Bundle bundle = Platform.getBundle(JifPlugin.PLUGIN_ID);
		Path path = new Path("/lib/jifsig.jar");
		URL url = FileLocator.find(bundle, path, null);

		if (url != null) {
			try {
				URI uri = FileLocator.resolve(url).toURI();
				defaultSigpath.append(uri.getPath()).append(
						File.pathSeparator);
			} catch (Exception e) {
				ErrorUtil.handleError(Level.WARNING, "jif.ide",
						"Unable to include default sigpath entries.",
						e.getCause(), Style.BLOCK);
			}
		}

		return defaultSigpath.toString();
	}
}
