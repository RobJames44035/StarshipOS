import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModuleSelector extends Application {

    private final File pomFile = new File(System.getProperty("user.home") + "/IdeaProjects/StarshipOS/pom.xml");
    private final List<ModuleEntry> modules = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) throws IOException {
        VBox vbox = new VBox(10);
        vbox.setStyle("-fx-padding: 10; -fx-alignment: center-left;");

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        VBox checkboxesVBox = new VBox(5);
        checkboxesVBox.setStyle("-fx-alignment: top-left;");

        loadModulesFromPom();

        for (ModuleEntry entry : modules) {
            entry.checkbox.setSelected(!entry.commented); // <-- FIXED: reflects comment status
            checkboxesVBox.getChildren().add(entry.checkbox);
        }

        scrollPane.setContent(checkboxesVBox);

        Button saveButton = new Button("Save");
        saveButton.setOnAction(event -> {
            try {
                backupPom();
                savePom();
                System.out.println("Updated pom.xml");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        HBox buttonBox = new HBox();
        buttonBox.getChildren().add(saveButton);
        buttonBox.setStyle("-fx-padding: 10; -fx-alignment: center;");

        vbox.getChildren().addAll(scrollPane, buttonBox);

        Scene scene = new Scene(vbox, 500, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("StarshipOS Module Selector");
        primaryStage.show();
    }

    private void loadModulesFromPom() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(pomFile));
        String line;
        Pattern pattern = Pattern.compile("(\\s*)(<!--)?\\s*<module>(.*?)</module>\\s*(-->)?");
        while ((line = reader.readLine()) != null) {
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                String indent = matcher.group(1);
                boolean commented = matcher.group(2) != null || matcher.group(4) != null;
                String moduleName = matcher.group(3);

                String displayName = moduleName.replaceFirst("^starship-os_", "");

                CheckBox box = new CheckBox(displayName);
                box.setSelected(!commented); // <-- FIXED: initial state reflects commented status
                modules.add(new ModuleEntry(indent, moduleName, box, commented));
            }
        }
        reader.close();
    }

    private void savePom() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(pomFile));
        StringBuilder newPom = new StringBuilder();
        String line;
        Pattern pattern = Pattern.compile("(\\s*)(<!--)?\\s*<module>(.*?)</module>\\s*(-->)?");
        while ((line = reader.readLine()) != null) {
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                String indent = matcher.group(1);
                String moduleName = matcher.group(3);
                ModuleEntry entry = modules.stream().filter(m -> m.name.equals(moduleName)).findFirst().orElse(null);
                if (entry != null) {
                    if (entry.checkbox.isSelected()) {
                        newPom.append(indent).append("<module>").append(moduleName).append("</module>\n");
                    } else {
                        newPom.append(indent).append("<!-- <module>").append(moduleName).append("</module> -->\n");
                    }
                }
            } else {
                newPom.append(line).append("\n");
            }
        }
        reader.close();

        FileWriter writer = new FileWriter(pomFile);
        writer.write(newPom.toString());
        writer.close();
    }

    private void backupPom() throws IOException {
        File backupFile = new File(pomFile.getAbsolutePath() + ".bak");
        Files.copy(pomFile.toPath(), backupFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    static class ModuleEntry {
        String indent;
        String name;
        CheckBox checkbox;
        boolean commented;

        public ModuleEntry(String indent, String name, CheckBox checkbox, boolean commented) {
            this.indent = indent;
            this.name = name;
            this.checkbox = checkbox;
            this.commented = commented;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
