import com.fasterxml.jackson.databind.ObjectMapper;
import dtos.DependencyDto;
import lombok.SneakyThrows;
import services.NewPythonReadService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    @SneakyThrows
    public static void main(String[] args) {
        NewPythonReadService pythonReadService = new NewPythonReadService();
        System.out.println("Reading files...");
        List<DependencyDto> pythonDependencies = pythonReadService.read("D:\\dxworks\\repos\\example\\repos");
        Map<String, List<DependencyDto>> mappedDeps = pythonDependencies.stream().collect(Collectors.groupingBy(DependencyDto::getFile));

        System.out.println("Writing result...");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File("results\\il-pypi-deps.json"), mappedDeps);

        System.out.println("\nPyMi (PyPI Miner) finished successfully!");
    }
}
