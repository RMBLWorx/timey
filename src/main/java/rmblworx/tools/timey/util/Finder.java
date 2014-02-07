package rmblworx.tools.timey.util;

import static java.nio.file.FileVisitResult.CONTINUE;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Ein {@code FileVisitor} welcher alle Dateien findet, die zum anzugebenen
 * glob-Pattern passen.
 * 
 * @see {@code http://docs.oracle.com/javase/javatutorials/tutorial/essential/io/fileOps.html#glob}
 * @author mmatthies
 */
public final class Finder extends SimpleFileVisitor<Path> {

	private final PathMatcher matcher;
	private List<Path> result = new LinkedList<Path>();

	public Finder(String pattern) {
		matcher = FileSystems.getDefault().getPathMatcher("glob:" + pattern);
	}

	private void find(Path file) {
		Path name = file.getFileName();
		if (name != null && matcher.matches(name)) {
			result.add(file);
			System.out.println(file);
		}
	}

	/**
	 * Liefert die gefundenen Dateien.
	 * 
	 * @return unveraenderliche Liste mit gefundenen Dateien oder eine leere
	 *         Liste.
	 */
	public List<Path> getResult() {
		return Collections.unmodifiableList(this.result);
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
		find(file);
		return CONTINUE;
	}

	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
		find(dir);
		return CONTINUE;
	}

	@Override
	public FileVisitResult visitFileFailed(Path file, IOException exc) {
		System.err.println(exc);
		return CONTINUE;
	}
}
