package com.core;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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

/**
 * Created by agaspari on 6/19/2017.
 */
public class MainFrame {
    static TabPane frame;
    private Tab tabAdd, tabSearch, tabView, tabEdit, tabExport, tabImport;
    private GridPane paneAdd, paneSearch, paneView, paneEdit, paneExport, paneImport;

    private Label lblFirstName, lblLastName, lblEmail, lblNumber, lblViewFirstName, lblViewLastName, lblViewEmail, lblViewNumber, lblResultFirstName, lblResultLastName, lblResultEmail, lblResultNumber, lblEditFirstName, lblEditLastName, lblEditEmail, lblEditNumber, lblSearchFirstName, lblSearchLastName, lblSearchEmail, lblSearchNumber, lblSearchResultFirstName, lblSearchResultLastName, lblSearchResultEmail, lblSearchResultNumber;
    private Label lblErrorAdd, lblErrorEdit;
    private TextField txtFirstName, txtLastName, txtEmail, txtNumber, txtEditFirstName, txtEditLastName, txtEditEmail, txtEditNumber, txtSearch;
    private Button btnAdd, btnExport, btnImport, btnPreviousView, btnNextView, btnSave, btnEdit, btnDelete, btnSearch, btnPreviousSearch, btnNextSearch;
    //TODO: Swap btnNext/btnPreivous words -> btnNextView = btnViewNext
    //TODO: Potentially remove all informative labels and replace with TextField promptTexts

    private ArrayList<Person> people;
    private Encrypter encrypter;
    private File contacts;
    private int index = -1;

    public MainFrame(){
        encrypter = new Encrypter();
        contacts = new File("contact-list.wver");

        people = importContactList(contacts);
        frame = new TabPane();
        frame.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

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
        //TODO: Remove Col and Row span
        //TODO: Remove labels or promptText
        paneAdd = new GridPane();
        paneAdd.setVgap(5);
        paneAdd.setHgap(5);
        paneAdd.setPadding(new Insets(5));
        lblFirstName = new Label("First Name: ");
        lblLastName = new Label("Last Name: ");
        lblEmail = new Label("Email: ");
        lblNumber = new Label("Number: ");
        txtFirstName = new TextField();
        txtFirstName.setPromptText("First Name");
        txtLastName = new TextField();
        txtLastName.setPromptText("Last Name");
        txtEmail = new TextField();
        txtEmail.setPromptText("Email");
        txtNumber = new TextField();
        txtNumber.setPromptText("Phone Number");
        btnAdd = new Button("Add Contact");
        lblErrorAdd = new Label("Error: Test");
        lblErrorAdd.setMinWidth(200);
        lblErrorAdd.setAlignment(Pos.CENTER);
        lblErrorAdd.setTextFill(Color.web("#800000"));
        lblErrorAdd.setStyle("-fx-border-color: #800000; -fx-background-color: rgba(128, 0, 0, .3)");
        lblErrorAdd.setFont(Font.font("Tahoma", FontWeight.BOLD, 12));
        lblErrorAdd.setVisible(false);
        paneAdd.add(lblFirstName, 0, 0);
        paneAdd.add(txtFirstName, 1, 0, 2, 1);
        paneAdd.add(lblLastName, 0, 1);
        paneAdd.add(txtLastName, 1, 1, 2, 1);
        paneAdd.add(lblEmail, 0, 2);
        paneAdd.add(txtEmail, 1, 2, 2, 1);
        paneAdd.add(lblNumber, 0, 3);
        paneAdd.add(txtNumber, 1, 3, 2, 1);
        paneAdd.add(btnAdd, 1, 4);
        paneAdd.add(lblErrorAdd, 0, 5, 3, 1);

        //TODO: Fix format
        paneSearch = new GridPane();
        paneSearch.setVgap(5);
        paneSearch.setHgap(5);
        paneSearch.setPadding(new Insets(5));
        lblSearchFirstName = new Label("First Name: ");
        lblSearchLastName = new Label("Last Name: ");
        lblSearchEmail = new Label("Email: ");
        lblSearchNumber = new Label("Phone Number: ");
        lblSearchResultFirstName = new Label();
        lblSearchResultLastName = new Label();
        lblSearchResultEmail = new Label();
        lblSearchResultNumber = new Label();
        txtSearch = new TextField();
        txtSearch.setPromptText("Input First Name");
        btnPreviousSearch = new Button("Previous");
        btnPreviousSearch.setMinWidth(75);
        btnNextSearch = new Button("Next");
        btnNextSearch.setMinWidth(75);
        btnSearch = new Button("Search");
        btnSearch.setMinWidth(167);
        paneSearch.add(lblSearchFirstName, 0, 0);
        paneSearch.add(lblSearchResultFirstName, 1, 0, 2, 1);
        paneSearch.add(lblSearchLastName, 0, 1);
        paneSearch.add(lblSearchResultLastName, 1, 1, 2, 1);
        paneSearch.add(lblSearchEmail, 0, 2);
        paneSearch.add(lblSearchResultEmail, 1, 2, 2, 1);
        paneSearch.add(lblSearchNumber, 0, 3);
        paneSearch.add(lblSearchResultNumber, 1, 3, 2, 1);
        paneSearch.add(btnPreviousSearch, 0, 4);
        paneSearch.add(btnNextSearch, 1, 4);
        paneSearch.add(txtSearch, 0, 5, 2, 1);
        paneSearch.add(btnSearch, 0, 6, 2, 1);

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
        btnPreviousView = new Button("Previous");
        btnPreviousView.setMinWidth(75);
        btnNextView = new Button("Next");
        btnNextView.setMinWidth(75);
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
        paneView.add(btnPreviousView, 0, 4);
        paneView.add(btnNextView, 1, 4);
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
        lblErrorEdit = new Label("Error: Test");
        lblErrorEdit.setMinWidth(200);
        lblErrorEdit.setAlignment(Pos.CENTER);
        lblErrorEdit.setTextFill(Color.web("#800000"));
        lblErrorEdit.setStyle("-fx-border-color: #800000; -fx-background-color: rgba(128, 0, 0, .3)");
        lblErrorEdit.setFont(Font.font("Tahoma", FontWeight.BOLD, 12));
        lblErrorEdit.setVisible(false);
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
        paneEdit.add(lblErrorEdit,0, 5, 3, 1);

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
                        lblErrorAdd.setText("First Name Invalid");
                        lblErrorAdd.setVisible(true);
                    }else if(txtLastName.getText().length() == 0){
                        lblErrorAdd.setText("Last Name Invalid");
                        lblErrorAdd.setVisible(true);
                    }else if(txtEmail.getText().length() > 0 && !txtEmail.getText().contains("@")){
                        lblErrorAdd.setText("Error: Invalid Email");
                        lblErrorAdd.setVisible(true);
                    }else if(txtNumber.getText().length() > 0 && (!txtNumber.getText().contains("(") || !txtNumber.getText().contains(")") || !txtNumber.getText().contains("-") || txtNumber.getText().length() != 17)){
                        lblErrorAdd.setText("Error: Invalid Phone Number");
                        lblErrorAdd.setVisible(true);
                    }else{
                        people.add(new Person(txtFirstName.getText(), txtLastName.getText(), txtEmail.getText(), txtNumber.getText()));
                        txtFirstName.setText("");
                        txtLastName.setText("");
                        txtEmail.setText("");
                        txtNumber.setText("");
                        lblErrorAdd.setVisible(false);
                    }
                }
        );

        btnNextView.setOnAction(
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

        btnPreviousView.setOnAction(
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
                    frame.getSelectionModel().select(tabEdit);
                }
        );

        btnSave.setOnAction(
                e -> {
                    if(txtEditFirstName.getText().length() == 0) {
                        lblErrorEdit.setText("First Name Invalid");
                        lblErrorEdit.setVisible(true);
                    }else if(txtEditLastName.getText().length() == 0){
                        lblErrorEdit.setText("Last Name Invalid");
                        lblErrorEdit.setVisible(true);
                    }else if(txtEditEmail.getText().length() > 0 && !txtEditEmail.getText().contains("@")){
                        lblErrorEdit.setText("Error: Invalid Email");
                        lblErrorEdit.setVisible(true);
                    }else if(txtEditNumber.getText().length() > 0 && (!txtEditNumber.getText().contains("(") || !txtEditNumber.getText().contains(")") || !txtEditNumber.getText().contains("-") || txtEditNumber.getText().length() != 17)){
                        lblErrorEdit.setText("Error: Invalid Phone Number");
                        lblErrorEdit.setVisible(true);
                    }else{
                        people.get(index).setFirstName(txtEditFirstName.getText());
                        people.get(index).setLastName(txtEditLastName.getText());
                        people.get(index).setEmail(txtEditEmail.getText());
                        people.get(index).setNumber(txtEditNumber.getText());
                        lblErrorEdit.setVisible(false);
                    }
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
                        frame.getSelectionModel().select(tabView);
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

                                //TODO: Potentially change to FileWriter
                                PrintWriter writer = new PrintWriter(file);
                                DOMSource source = new DOMSource(doc);
                                StringWriter sw = new StringWriter();
                                StreamResult result2 = new StreamResult(sw);
                                transformer.transform(source, result2);
                                writer.print(encrypter.encryptText(sw.toString()));
                                writer.close();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
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

        frame.getTabs().addAll(tabAdd, tabSearch, tabView, tabEdit, tabExport, tabImport);
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
            writer.print(encrypter.decryptText(ciphertext));
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

    public static TabPane getFrame(){
        return frame;
    }
}
