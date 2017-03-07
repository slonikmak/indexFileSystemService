import com.sun.istack.internal.Nullable;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.stream.Stream;

import static java.nio.file.FileVisitResult.*;

/**
 * Created by Anton on 06.03.2017.
 */
public class IndexService {

    public Optional<Path> findFileByName(String name, Path dir) throws IOException {

        return findFile(dir, name).findFirst();

    }

    public Optional<Stream<Path>> findFilesByMatch(String match, Path dir) throws IOException {
        return Optional.ofNullable(findFile(dir, match));
    }

    private Stream<Path> findFile(Path dir, String match) throws IOException {
        PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:"+match);
        Stream<Path> paths = Files.find(dir, Integer.MAX_VALUE, (path, basicFileAttributes) -> matcher.matches(path.getFileName()));
        return paths;

    }

    public static void main(String[] args) throws IOException {
        IndexService service = new IndexService();

        Optional<Stream<Path>> result = service.findFilesByMatch("*.txt", Paths.get("C:/temp"));

        result.ifPresent(pathStream -> pathStream.forEach(System.out::println));
    }


}
