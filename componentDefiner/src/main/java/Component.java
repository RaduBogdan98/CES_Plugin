import java.util.*;

public class Component {
    private String fullyQualifiedName;
    private List<String> files;

    public Component(String fullyQualifiedName) {
        this.fullyQualifiedName = fullyQualifiedName;
        this.files = new ArrayList<>();
    }

    public void addFileToComponent(String filename){
        files.add(filename);
    }

    public String getFullyQualifiedName() {
        return fullyQualifiedName;
    }

    public List<String> getFiles() {
        return files;
    }

    @Override
    public String toString() {
        return "Component{" +
                "fullyQualifiedName='" + fullyQualifiedName + '\'' +
                ", files=" + files +
                '}';
    }
}
