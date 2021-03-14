import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("Usage: java ${outputFile} ${filesToGroup}");

            return;
        }

        Component defaultComponent = new Component("@");

        String outputFile = args[0];
        String filesToGroup = args[1];

        List<String> files = new ArrayList<>();
        Scanner scanner = new Scanner(new File(filesToGroup));
        while (scanner.hasNext()) {
            files.add(scanner.nextLine());
        }

        //we take into consideration both modules containing build.gradle as well as build.gradle.kts
        List<String> componentFolders = files.stream()
                .filter(f -> f.endsWith("build.gradle"))
                .map(f -> f.substring(0, f.length() - 12))
                .collect(Collectors.toList());

        componentFolders.addAll(
                files.stream()
                        .filter(f -> f.endsWith("build.gradle.kts"))
                        .map(f -> f.substring(0, f.length() - 16))
                        .collect(Collectors.toList())
        );

        //map folders to components
        List<Component> components = componentFolders.stream()
                .filter(f -> f.length() > 0)
                .map(f -> new Component(f.substring(0, f.length() - 1)))
                .collect(Collectors.toList());

        components.add(defaultComponent);

        for (String file : files) {
            Component component;
            if ((component = components.stream().filter(c -> file.startsWith(c.getFullyQualifiedName()+"/")).findFirst().orElse(null)) != null)
            {
                component.addFileToComponent(file);
            }
            else{
                defaultComponent.addFileToComponent(file);
            }
        }

        ObjectMapper jsonMapper = new ObjectMapper();
        jsonMapper.writeValue(new File(outputFile), components);
    }
}
