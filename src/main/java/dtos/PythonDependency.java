package dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PythonDependency {
    private String name;
    private String version;
    private File requirementsFile;

    @Override
    public String toString() {
        return "PythonDependency{" +
                "name:" + name +
                ", version:" + version +
                '}';
    }
}
