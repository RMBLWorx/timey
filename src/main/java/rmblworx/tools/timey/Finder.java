package rmblworx.tools.timey;

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
public class Finder extends SimpleFileVisitor<Path> {

	private final PathMatcher matcher;
	private final List<Path> result = new LinkedList<Path>();

	public Finder(final String pattern) {
		this.matcher = FileSystems.getDefault().getPathMatcher("glob:" + pattern);
	}

	private void find(final Path file) {
		final Path name = file.getFileName();
		if (name != null && this.matcher.matches(name)) {
			this.result.add(file);
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
	public FileVisitResult preVisitDirectory(final Path dir, final BasicFileAttributes attrs) {
		this.find(dir);
		return CONTINUE;
	}

	@Override
	public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) {
		this.find(file);
		return CONTINUE;
	}

	@Override
	public FileVisitResult visitFileFailed(final Path file, final IOException exc) {
		System.err.println(exc);
		return CONTINUE;
	}

}
