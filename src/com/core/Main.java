package com.core;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main extends Application {
    TabPane root;
    Tab tabAdd, tabSearch, tabView, tabEdit, tabExport, tabImport;
    GridPane paneAdd, paneSearch, paneView, paneEdit, paneExport, paneImport;

    Label lblFirstName, lblLastName, lblEmail, lblNumber, lblViewFirstName, lblViewLastName, lblViewEmail, lblViewNumber, lblResultFirstName, lblResultLastName, lblResultEmail, lblResultNumber, lblEditFirstName, lblEditLastName, lblEditEmail, lblEditNumber;
    Label lblError;
    TextField txtFirstName, txtLastName, txtEmail, txtNumber, txtEditFirstName, txtEditLastName, txtEditEmail, txtEditNumber;
    Button btnAdd, btnExport, btnImport, btnPrevious, btnNext, btnSave, btnEdit, btnDelete;

    ArrayList<Person> people;
    Encrypter en;
    File contacts;
    private int index = -1;

    @Override
    public void start(Stage primaryStage) throws Exception{
        en = new Encrypter();
        contacts = new File("contact-list.wver");

        people = importContactList(contacts);

        root = new TabPane();
        root.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        tabAdd = new Tab("Add");
        tabSearch = new Tab("Search");
        tabView = new Tab("View");
        tabEdit = new Tab("Edit");
        tabExport = new Tab("Export");
        tabImport = new Tab("Import");

        tabView.setOnSelectionChanged(
            e -> {
                if(index != -1){
                    lblResultFirstName.setText(people.get(index).getFirstName());
                    lblResultLastName.setText(people.get(index).getLastName());
                    lblResultEmail.setText(people.get(index).getEmail());
                    lblResultNumber.setText(people.get(index).getNumber());
                }else{
                    //In case the all elements are deleted from table, the table will show blank.
                    lblResultFirstName.setText("");
                    lblResultLastName.setText("");
                    lblResultEmail.setText("");
                    lblResultNumber.setText("");
                }
            }
        );

        //TODO: Add specialization, social networks, |age|, |location|
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
        lblError = new Label("Error: Test");
        lblError.setMinWidth(200);
        lblError.setAlignment(Pos.CENTER);
        lblError.setTextFill(Color.web("#800000"));
        lblError.setStyle("-fx-border-color: #800000; -fx-background-color: rgba(128, 0, 0, .3)");
        lblError.setFont(Font.font("Tahoma", FontWeight.BOLD, 12));
        lblError.setVisible(false);
        paneAdd.add(lblFirstName, 0, 0);
        paneAdd.add(txtFirstName, 1, 0, 2, 1);
        paneAdd.add(lblLastName, 0, 1);
        paneAdd.add(txtLastName, 1, 1, 2, 1);
        paneAdd.add(lblEmail, 0, 2);
        paneAdd.add(txtEmail, 1, 2, 2, 1);
        paneAdd.add(lblNumber, 0, 3);
        paneAdd.add(txtNumber, 1, 3, 2, 1);
        paneAdd.add(btnAdd, 1, 4);
        paneAdd.add(lblError, 0, 5, 3, 1);

        paneSearch = new GridPane();
        paneSearch.setVgap(5);
        paneSearch.setHgap(5);
        paneSearch.setPadding(new Insets(5));

        paneView = new GridPane();
        paneView.setVgap(5);
        paneView.setHgap(5);
        paneView.setPadding(new Insets(5));
        lblViewFirstName = new Label("First Name: ");
        lblViewLastName = new Label("Last Name: ");
        lblViewEmail = new Label("Email: ");
        lblViewNumber = new Label("Phone Number: ");
        lblResultFirstName = new Label();
        lblResultLastName = new Label();
        lblResultEmail = new Label();
        lblResultNumber = new Label();
        btnPrevious = new Button("Previous");
        btnPrevious.setMinWidth(75);
        btnNext = new Button("Next");
        btnNext.setMinWidth(75);
        btnEdit = new Button("Edit");
        btnEdit.setMinWidth(167);
        paneView.add(lblViewFirstName, 0, 0);
        paneView.add(lblResultFirstName, 1, 0, 2, 1);
        paneView.add(lblViewLastName, 0, 1);
        paneView.add(lblResultLastName, 1, 1, 2, 1);
        paneView.add(lblViewEmail, 0, 2);
        paneView.add(lblResultEmail, 1, 2, 2, 1);
        paneView.add(lblViewNumber, 0, 3);
        paneView.add(lblResultNumber, 1, 3, 2, 1);
        paneView.add(btnPrevious, 0, 4);
        paneView.add(btnNext, 1, 4);
        paneView.add(btnEdit, 0, 5, 2, 1);
        if(people.size() > 0){
            index = 0;
        }

        paneEdit = new GridPane();
        paneEdit.setVgap(5);
        paneEdit.setHgap(5);
        paneEdit.setPadding(new Insets(5));
        lblEditFirstName = new Label("First Name: ");
        lblEditLastName = new Label("Last Name: ");
        lblEditEmail = new Label("Email: ");
        lblEditNumber = new Label("Number: ");
        txtEditFirstName = new TextField();
        txtEditLastName = new TextField();
        txtEditEmail = new TextField();
        txtEditNumber = new TextField();
        btnSave = new Button("Save");
        btnSave.setMinWidth(75);
        btnDelete = new Button("Delete");
        btnDelete.setMinWidth(75);
        paneEdit.add(lblEditFirstName, 0, 0);
        paneEdit.add(txtEditFirstName, 1, 0, 2, 1);
        paneEdit.add(lblEditLastName, 0, 1);
        paneEdit.add(txtEditLastName, 1, 1, 2, 1);
        paneEdit.add(lblEditEmail, 0, 2);
        paneEdit.add(txtEditEmail, 1, 2, 2, 1);
        paneEdit.add(lblEditNumber, 0, 3);
        paneEdit.add(txtEditNumber, 1, 3, 2, 1);
        paneEdit.add(btnSave, 1, 4);
        paneEdit.add(btnDelete, 2, 4);

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

        //TODO: Add format verification (Might be Completed)
        //TODO: Find flow for unknown email/number
        btnAdd.setOnAction(
            e -> {
                if(txtFirstName.getText().length() == 0) {
                    lblError.setText("First Name Invalid");
                    lblError.setVisible(true);
                }else if(txtLastName.getText().length() == 0){
                    lblError.setText("Last Name Invalid");
                    lblError.setVisible(true);
                }else if(txtEmail.getText().length() > 0 && !txtEmail.getText().contains("@")){
                    lblError.setText("Error: Invalid Email");
                    lblError.setVisible(true);
                }else if(txtEmail.getText().length() > 0 && (!txtNumber.getText().contains("(") || !txtNumber.getText().contains(")") || !txtNumber.getText().contains("-") || txtNumber.getText().length() != 17)){
                    lblError.setText("Error: Invalid Phone Number");
                    lblError.setVisible(true);
                }else{
                    people.add(new Person(txtFirstName.getText(), txtLastName.getText(), txtEmail.getText(), txtNumber.getText()));
                    txtFirstName.setText("");
                    txtLastName.setText("");
                    txtEmail.setText("");
                    txtNumber.setText("");
                    lblError.setVisible(false);
                }
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

        btnEdit.setOnAction(
            e -> {
                txtEditFirstName.setText(lblResultFirstName.getText());
                txtEditLastName.setText(lblResultLastName.getText());
                txtEditEmail.setText(lblResultEmail.getText());
                txtEditNumber.setText(lblResultNumber.getText());
                root.getSelectionModel().select(tabEdit);
            }
        );

        btnSave.setOnAction(
            e -> {
                people.get(index).setFirstName(txtEditFirstName.getText());
                people.get(index).setLastName(txtEditLastName.getText());
                people.get(index).setEmail(txtEditEmail.getText());
                people.get(index).setNumber(txtEditNumber.getText());
            }
        );

        btnDelete.setOnAction(
            e -> {
                //TODO: Add confirmation
                //TODO: Find Blank Error
                if(index != -1){
                    people.remove(index);
                    index--;
                    txtEditFirstName.setText("");
                    txtEditLastName.setText("");
                    txtEditEmail.setText("");
                    txtEditNumber.setText("");
                    root.getSelectionModel().select(tabView);
                }
            }
        );

        btnExport.setOnAction(
            e -> {
                try {
                    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                    Document doc = docBuilder.newDocument();

                    Element rootElement = doc.createElement("people");
                    doc.appendChild(rootElement);

                    for (Person aPeople : people) {
                        Element person = doc.createElement("person");
                        Element firstName = doc.createElement("first-name");
                        firstName.appendChild(doc.createTextNode(aPeople.getFirstName()));
                        Element lastName = doc.createElement("last-name");
                        lastName.appendChild(doc.createTextNode(aPeople.getLastName()));
                        Element email = doc.createElement("email");
                        email.appendChild(doc.createTextNode(aPeople.getEmail()));
                        Element number = doc.createElement("phone-number");
                        number.appendChild(doc.createTextNode(aPeople.getNumber()));
                        person.appendChild(firstName);
                        person.appendChild(lastName);
                        person.appendChild(email);
                        person.appendChild(number);
                        rootElement.appendChild(person);

                        TransformerFactory transformerFactory = TransformerFactory.newInstance();
                        Transformer transformer = transformerFactory.newTransformer();

                        try {
                            File file = new File("contact-list.wver");
                            file.createNewFile();
                            PrintWriter writer = new PrintWriter(file);
                            DOMSource source = new DOMSource(doc);
                            StringWriter sw = new StringWriter();
                            StreamResult result2 = new StreamResult(sw);
                            transformer.transform(source, result2);
                            System.out.println(en.encryptText(sw.toString()));
                            writer.print(en.encryptText(sw.toString()));
                            writer.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }

                        //StreamResult result = new StreamResult(new File("contact-list.xml"));
                        //transformer.transform(source, result);

                    }
                } catch (ParserConfigurationException | TransformerException e1) {
                    e1.printStackTrace();
                }
            }
        );

        tabAdd.setContent(paneAdd);
        tabSearch.setContent(paneSearch);
        tabView.setContent(paneView);
        tabEdit.setContent(paneEdit);
        tabExport.setContent(paneExport);
        tabImport.setContent(paneImport);

        root.getTabs().addAll(tabAdd, tabSearch, tabView, tabEdit, tabExport, tabImport);

        primaryStage.setTitle("Contact Book");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    private ArrayList<Person> importContactList(File file){
        File tempContacts;
        PrintWriter writer;
        ArrayList<Person> people = new ArrayList<>();
        try {
            String ciphertext = readFile(file.getPath(), Charset.defaultCharset());
            tempContacts = new File("temp-contacts.xml");
            tempContacts.createNewFile();
            writer = new PrintWriter(tempContacts);
            writer.print(en.decryptText(ciphertext));
            writer.close();
            if(!file.exists() || !tempContacts.exists()){
                return people;
            }

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(tempContacts);
            NodeList nList = doc.getElementsByTagName("person");

            for(int i = 0; i < nList.getLength(); i++){
                people.add(new Person(nList.item(i).getChildNodes().item(0).getTextContent(), nList.item(i).getChildNodes().item(1).getTextContent(), nList.item(i).getChildNodes().item(2).getTextContent(), nList.item(i).getChildNodes().item(3).getTextContent()));
            }
            tempContacts.delete();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return people;
    }

    private String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    public static void main(String[] args) {
        launch(args);
    }
}