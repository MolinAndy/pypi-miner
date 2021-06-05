package services;

import dtos.DependencyDto;
import dtos.PythonDependency;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NewPythonReadService extends ReadService {

    @SneakyThrows
    public List<DependencyDto> read(String project) {
        List<File> pythonFiles = getFilesFromProject(project, "requirements.txt");
        List<PythonDependency> pythonDependencies = new ArrayList<>();

        for(File file : pythonFiles) {
            FileReader input = new FileReader(file);
            BufferedReader bufRead = new BufferedReader(input);
            String line;

            while ((line = bufRead.readLine()) != null)
            {
                if(line.contains(".txt") || line.isEmpty() || line.contains("#") || line.contains("http") || line.contains("-e .") || line.equals(".") || line.contains("--")) {
                    continue;
                }

                String[] splitLine;
                String[] comparators = new String[]{"==", "!=", "<=", ">=", "<", ">", "===", "~="};

                if(Arrays.stream(comparators).noneMatch(line::contains)) {
                    pythonDependencies.add(new PythonDependency(line.replaceAll("\\[.*?]",""), "", file));
                    continue;
                }

                splitLine = line.split("==|!=|<=|>=|<|>|===|~=");
                String name = splitLine[0].replaceAll(" |python_version|;|\\[.*?]|platform_system|sys_platform","");
                String version = splitLine[1].replaceAll(" |'|\"|;|python_version|sys_platform|platform_system","");
                if (version.toLowerCase().contains("win")) {
                    continue;
                }
                pythonDependencies.add(new PythonDependency(name, version, file));
            }
        }

        return transformPythonDependencies(pythonDependencies);
    }

    private List<DependencyDto> transformPythonDependencies(List<PythonDependency> pythonDependencies) {
        List<DependencyDto> dependencies = new ArrayList<>();

        for(PythonDependency d : pythonDependencies) {
            dependencies.add(new DependencyDto(d.getName(),
                    d.getVersion(),
                    "pypi",
                    new File("D:\\dxworks\\repos\\example\\repos").toURI().relativize(new File(d.getRequirementsFile().toString()).toURI()).getPath().replace("/requirements.txt","").replaceAll("/","\\\\")));
        }

        return dependencies;
    }
}
