package jif.ide;

import java.io.File;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import polyglot.ide.common.ClasspathEntry.ClasspathEntryType;
import polyglot.ide.common.ClasspathUtil;
import polyglot.main.Main;
import polyglot.main.Main.TerminationException;
import polyglot.util.SilentErrorQueue;

public class JifProjectBuilder extends IncrementalProjectBuilder {
	
	@Override
	protected IProject[] build(int kind, Map<String, String> args,
			IProgressMonitor monitor) throws CoreException {

		File classpathFile = getProject()
				.getFile(ClasspathUtil.CLASSPATH_FILE_NAME).getRawLocation()
				.toFile();
		String classpath = ClasspathUtil.parse(classpathFile);
		jif.ExtensionInfo extInfo = new jif.ExtensionInfo();
		String sigpath = ClasspathUtil.parse(classpathFile, ClasspathEntryType.SIGPATHENTRY);
		
		SilentErrorQueue eq = new SilentErrorQueue(100, "compiler");

		String binPath = getProject().getFile("bin").getRawLocation()
				.toString();
		Set<String> filesToCompile = new HashSet<>();
		File src = getProject().getFile("src").getRawLocation().toFile();
		collectAllFiles(src, filesToCompile, extInfo);

		if (filesToCompile.isEmpty())
			return null;

		String[] compilerArgs = new String[filesToCompile.size() + 6];
		System.arraycopy(
				new String[] { "-d", binPath,
				          "-classpath", classpath, "-sigcp", sigpath }, 0,
				compilerArgs, 0, 6);
		int curIdx = 6;
		for (String srcFile : filesToCompile) {
			if (extension(srcFile).equals(extInfo.defaultFileExtension()))
				compilerArgs[curIdx++] = srcFile;
		}

		Main main = new Main();

		try {
			main.start(compilerArgs, extInfo, eq);
		} catch (TerminationException e) {
			// ignore this one
		}

		return null;
	}

	private void collectAllFiles(File baseDir, Set<String> files, jif.ExtensionInfo extInfo) {
		for (File file : baseDir.listFiles()) {
			if (file.isDirectory())
				collectAllFiles(file, files, extInfo);
			else if (extension(file.getName()).equals(extInfo.defaultFileExtension()) && file.length() != 0)
				files.add(file.toString());
		}
	}
	
	private String extension(String filename) {
		String extension = "";

		int i = filename.lastIndexOf('.');
		if (i > 0) {
		    extension = filename.substring(i+1);
		}
		
		return extension;
	}
}
