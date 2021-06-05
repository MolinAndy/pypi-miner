package services;

import dtos.DependencyDto;
import lombok.SneakyThrows;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public abstract class ReadService {

    public abstract List<DependencyDto> read(String project);

    @SneakyThrows
    protected List<File> getFilesFromProject(String project, String fileName) {
        return Files.walk(Paths.get(project))
                .filter(Files::isRegularFile)
                .filter(file -> file.getFileName().endsWith(fileName))
                .map(Path::toFile)
                .collect(Collectors.toList());
    }
}
