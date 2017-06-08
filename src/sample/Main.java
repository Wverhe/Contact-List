package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {
    TabPane root;
    Tab tabAdd, tabSearch, tabView, tabExport, tabImport;
    GridPane paneAdd, paneSearch, paneView, paneExport, paneImport;

    Label lblFirstName, lblLastName, lblEmail, lblNumber, lblViewFirstName, lblViewLastName, lblViewEmail, lblViewNumber, lblResultFirstName, lblResultLastName, lblResultEmail, lblResultNumber;
    TextField txtFirstName, txtLastName, txtEmail, txtNumber;
    Button btnAdd, btnExport, btnImport, btnPrevious, btnNext;

    ArrayList<Person> people;
    private int index = -1;

    @Override
    public void start(Stage primaryStage) throws Exception{
        people = new ArrayList<>();

        root = new TabPane();
        root.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        tabAdd = new Tab("Add");
        tabSearch = new Tab("Search");
        tabView = new Tab("View");
        tabExport = new Tab("Export");
        tabImport = new Tab("Import");

        paneAdd = new GridPane();
        paneAdd.setVgap(5);
        paneAdd.setHgap(5);
        paneAdd.setPadding(new Insets(5));
        lblFirstName = new Label("First Name: ");
        lblLastName = new Label("Last Name: ");
        lblEmail = new Label("Email: ");
        lblNumber = new Label("Number: ");
        txtFirstName = new TextField();
        txtLastName = new TextField();
        txtEmail = new TextField();
        txtNumber = new TextField();
        btnAdd = new Button("Add Contact");
        paneAdd.add(lblFirstName, 0, 0);
        paneAdd.add(txtFirstName, 1, 0, 2, 1);
        paneAdd.add(lblLastName, 0, 1);
        paneAdd.add(txtLastName, 1, 1, 2, 1);
        paneAdd.add(lblEmail, 0, 2);
        paneAdd.add(txtEmail, 1, 2, 2, 1);
        paneAdd.add(lblNumber, 0, 3);
        paneAdd.add(txtNumber, 1, 3, 2, 1);
        paneAdd.add(btnAdd, 1, 4);

        paneSearch = new GridPane();
        paneSearch.setVgap(5);
        paneSearch.setHgap(5);
        paneSearch.setPadding(new Insets(5));

        lblViewFirstName = new Label("First Name: ");
        lblViewLastName = new Label("Last Name: ");
        lblViewEmail = new Label("Email: ");
        lblViewNumber = new Label("Phone Number: ");
        lblResultFirstName = new Label();
        lblResultLastName = new Label();
        lblResultEmail = new Label();
        lblResultNumber = new Label();
        btnPrevious = new Button("Previous");
        btnNext = new Button("Next");

        paneView = new GridPane();
        paneView.setVgap(5);
        paneView.setHgap(5);
        paneView.setPadding(new Insets(5));
        paneView.add(lblViewFirstName, 0, 0);
        paneView.add(lblResultFirstName, 1, 0, 2, 1);
        paneView.add(lblViewLastName, 0, 1);
        paneView.add(lblResultLastName, 1, 1, 2, 1);
        paneView.add(lblViewEmail, 0, 2);
        paneView.add(lblResultEmail, 1, 2, 2, 1);
        paneView.add(lblViewNumber, 0, 3);
        paneView.add(lblResultNumber, 1, 3, 2, 1);
        paneView.add(btnPrevious, 1, 4);
        paneView.add(btnNext, 2, 4);

        paneExport = new GridPane();
        paneExport.setVgap(5);
        paneExport.setHgap(5);
        paneExport.setPadding(new Insets(5));
        btnExport = new Button("Export");
        paneExport.add(btnExport, 0, 0);

        paneImport = new GridPane();
        paneImport.setVgap(5);
        paneImport.setHgap(5);
        paneImport.setPadding(new Insets(5));
        btnImport = new Button("Import");
        paneImport.add(btnImport, 0, 0);

        //TODO: Add format verification
        btnAdd.setOnAction(
            e -> {
                people.add(new Person(txtFirstName.getText(), txtLastName.getText(), txtEmail.getText(), txtNumber.getText()));
                txtFirstName.setText("");
                txtLastName.setText("");
                txtEmail.setText("");
                txtNumber.setText("");
            }
        );

        btnNext.setOnAction(
            e -> {
                if (index == people.size() - 1){
                    index = 0;
                } else {
                    index += 1;
                }
                lblResultFirstName.setText(people.get(index).getFirstName());
                lblResultLastName.setText(people.get(index).getLastName());
                lblResultEmail.setText(people.get(index).getEmail());
                lblResultNumber.setText(people.get(index).getNumber());
            }
        );

        btnPrevious.setOnAction(
            e -> {
                if (index == 0){
                    index = people.size() - 1;
                } else {
                    index -= 1;
                }
                lblResultFirstName.setText(people.get(index).getFirstName());
                lblResultLastName.setText(people.get(index).getLastName());
                lblResultEmail.setText(people.get(index).getEmail());
                lblResultNumber.setText(people.get(index).getNumber());
            }
        );

        tabAdd.setContent(paneAdd);
        tabSearch.setContent(paneSearch);
        tabView.setContent(paneView);
        tabExport.setContent(paneExport);
        tabImport.setContent(paneImport);

        root.getTabs().addAll(tabAdd, tabSearch, tabView, tabExport, tabImport);

        primaryStage.setTitle("Contact Book");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}