package com.core;

import com.core.objects.component.*;
import javafx.scene.control.*;
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
    private CustomGridPane paneAdd, paneSearch, paneView, paneEdit, paneExport, paneImport;


    private Label lblFirstName, lblLastName, lblEmail, lblNumber, lblViewFirstName, lblViewLastName, lblViewEmail, lblViewNumber, lblResultFirstName, lblResultLastName, lblResultEmail, lblResultNumber, lblEditFirstName, lblEditLastName, lblEditEmail, lblEditNumber, lblSearchFirstName, lblSearchLastName, lblSearchEmail, lblSearchNumber, lblSearchResultFirstName, lblSearchResultLastName, lblSearchResultEmail, lblSearchResultNumber;
    private InfoLabel lblInfoAdd, lblInfoEdit;
    private TextField txtFirstName, txtLastName, txtEmail, txtNumber, txtEditFirstName, txtEditLastName, txtEditEmail, txtEditNumber, txtSearch;
    private CustomButton btnAdd, btnExport, btnImport, btnPreviousView, btnNextView, btnSave, btnEdit, btnDelete, btnSearch, btnPreviousSearch, btnNextSearch;
    //TODO: Swap btnNext/btnPreivous words -> btnNextView = btnViewNext
    //TODO: Potentially remove all informative labels and replace with TextField promptTexts
    //TODO: Make every tab an object

    private ArrayList<Person> people, searchPeople; //TODO: Change this name? lol
    private Encrypter encrypter;
    private File contacts;
    private int index = -1, searchIndex = -1;

    public MainFrame(){
        encrypter = new Encrypter();
        contacts = new File("contact-list.wver");
        searchPeople = new ArrayList<>();
        people = importContactList(contacts);
        frame = new TabPane();
        frame.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        frame.setStyle(" -fx-background-color: #3c3c3c;");

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
                e.consume();
            }
        );

        //TODO: Add specialization, social networks, |age|, |location|
        //TODO: Remove labels or promptText
        paneAdd = new CustomGridPane();
        paneAdd.addColumns(3);
        lblFirstName = new CustomLabel("First Name: ");
        lblLastName = new CustomLabel("Last Name: ");
        lblEmail = new CustomLabel("Email: ");
        lblNumber = new CustomLabel("Number: ");
        txtFirstName = new TextField();
        txtFirstName.setPromptText("First Name");
        txtLastName = new TextField();
        txtLastName.setPromptText("Last Name");
        txtEmail = new TextField();
        txtEmail.setPromptText("Email");
        txtNumber = new TextField();
        txtNumber.setPromptText("Phone Number");
        btnAdd = new CustomButton("Add Contact");
        lblInfoAdd = new InfoLabel("Error: Test");
        paneAdd.add(lblFirstName, 0, 0);
        paneAdd.add(txtFirstName, 1, 0, 2, 1);
        paneAdd.add(lblLastName, 0, 1);
        paneAdd.add(txtLastName, 1, 1, 2, 1);
        paneAdd.add(lblEmail, 0, 2);
        paneAdd.add(txtEmail, 1, 2, 2, 1);
        paneAdd.add(lblNumber, 0, 3);
        paneAdd.add(txtNumber, 1, 3, 2, 1);
        paneAdd.add(btnAdd, 1, 4, 2, 1);
        paneAdd.add(lblInfoAdd, 0, 5, 3, 1);

        //TODO: Fix format
        //TODO: Add edit
        //TODO: Add error label
        paneSearch = new CustomGridPane();
        paneSearch.addColumns(2);
        lblSearchFirstName = new CustomLabel("First Name: ");
        lblSearchLastName = new CustomLabel("Last Name: ");
        lblSearchEmail = new CustomLabel("Email: ");
        lblSearchNumber = new CustomLabel("Phone Number: ");
        lblSearchResultFirstName = new CustomLabel("");
        lblSearchResultLastName = new CustomLabel("");
        lblSearchResultEmail = new CustomLabel("");
        lblSearchResultNumber = new CustomLabel("");
        txtSearch = new TextField();
        txtSearch.setPromptText("Input First Name");
        btnPreviousSearch = new CustomButton("Previous");
        btnPreviousSearch.setMinWidth(75);
        btnNextSearch = new CustomButton("Next");
        btnNextSearch.setMinWidth(75);
        btnSearch = new CustomButton("Search");
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

        paneView = new CustomGridPane();
        paneView.addColumns(2);
        lblViewFirstName = new CustomLabel("First Name: ");
        lblViewLastName = new CustomLabel("Last Name: ");
        lblViewEmail = new CustomLabel("Email: ");
        lblViewNumber = new CustomLabel("Phone Number: ");
        lblResultFirstName = new CustomLabel("");
        lblResultLastName = new CustomLabel("");
        lblResultEmail = new CustomLabel("");
        lblResultNumber = new CustomLabel("");
        btnPreviousView = new CustomButton("Previous");
        btnNextView = new CustomButton("Next");
        btnEdit = new CustomButton("Edit");
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

        paneEdit = new CustomGridPane();
        paneEdit.addColumns(3);
        lblEditFirstName = new CustomLabel("First Name: ");
        lblEditLastName = new CustomLabel("Last Name: ");
        lblEditEmail = new CustomLabel("Email: ");
        lblEditNumber = new CustomLabel("Number: ");
        txtEditFirstName = new TextField();
        txtEditLastName = new TextField();
        txtEditEmail = new TextField();
        txtEditNumber = new TextField();
        btnSave = new CustomButton("Save");
        btnSave.setMinWidth(75);
        btnDelete = new CustomButton("Delete");
        btnDelete.setMinWidth(75);
        lblInfoEdit = new InfoLabel("Error: Test");
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
        paneEdit.add(lblInfoEdit,0, 5, 3, 1);

        paneExport = new CustomGridPane();
        paneExport.addColumns(1);
        btnExport = new CustomButton("Export");
        paneExport.add(btnExport, 0, 0);

        paneImport = new CustomGridPane();
        paneImport.addColumns(1);
        btnImport = new CustomButton("Import");
        paneImport.add(btnImport, 0, 0);

        //TODO: Add format verification (Might be Completed)
        //TODO: Find flow for unknown email/number
        btnAdd.setOnAction(
            e -> {
                if(txtFirstName.getText().length() == 0) {
                    lblInfoAdd.showError("Error: Invalid First Name.");
                }else if(txtLastName.getText().length() == 0){
                    lblInfoAdd.showError("Error: Invalid Last Name.");
                }else if(txtEmail.getText().length() > 0 && !txtEmail.getText().contains("@")){
                    lblInfoAdd.showError("Error: Invalid Email.");
                }else if(txtNumber.getText().length() > 0 && (!txtNumber.getText().contains("(") || !txtNumber.getText().contains(")") || !txtNumber.getText().contains("-") || txtNumber.getText().length() != 17)){
                    lblInfoAdd.showError("Error: Invalid Phone Number.");
                }else{
                    people.add(new Person(txtFirstName.getText(), txtLastName.getText(), txtEmail.getText(), txtNumber.getText()));
                    lblInfoAdd.showInfo("Successfully added: " + txtFirstName.getText() + " " + txtLastName.getText());
                    txtFirstName.setText("");
                    txtLastName.setText("");
                    txtEmail.setText("");
                    txtNumber.setText("");
                }
                e.consume();
            }
        );

        //TODO: Add second pass through to check if name contains
        btnSearch.setOnAction(
             e -> {
                 String searchTerm = txtSearch.getText();
                 searchPeople.clear();
                 for(Person aPeople : people){
                    if(aPeople.getFirstName().equalsIgnoreCase(searchTerm)){
                        searchPeople.add(aPeople);
                    }
                 }
                 if(searchPeople.size() > 0){
                     lblSearchResultFirstName.setText(searchPeople.get(0).getFirstName());
                     lblSearchResultLastName.setText(searchPeople.get(0).getLastName());
                     lblSearchResultEmail.setText(searchPeople.get(0).getEmail());
                     lblSearchResultNumber.setText(searchPeople.get(0).getNumber());
                     searchIndex = 0;
                 }
                 e.consume();
             }
        );

        btnNextSearch.setOnAction(
            e -> {
                if(searchPeople.size() > 0){
                    if (searchIndex == searchPeople.size() - 1){
                        searchIndex = 0;
                    } else {
                        searchIndex += 1;
                    }
                    lblSearchResultFirstName.setText(searchPeople.get(searchIndex).getFirstName());
                    lblSearchResultLastName.setText(searchPeople.get(searchIndex).getLastName());
                    lblSearchResultEmail.setText(searchPeople.get(searchIndex).getEmail());
                    lblSearchResultNumber.setText(searchPeople.get(searchIndex).getNumber());
                }
                e.consume();
            }
        );

        btnPreviousSearch.setOnAction(
            e -> {
                if(searchPeople.size() > 0){
                    if (searchIndex == 0) {
                        searchIndex = searchPeople.size() - 1;
                    } else {
                        searchIndex -= 1;
                    }
                    lblSearchResultFirstName.setText(searchPeople.get(searchIndex).getFirstName());
                    lblSearchResultLastName.setText(searchPeople.get(searchIndex).getLastName());
                    lblSearchResultEmail.setText(searchPeople.get(searchIndex).getEmail());
                    lblSearchResultNumber.setText(searchPeople.get(searchIndex).getNumber());
                }
                e.consume();
            }
        );

        btnNextView.setOnAction(
            e -> {
                if(people.size() > 0){
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
                e.consume();
            }
        );

        btnPreviousView.setOnAction(
            e -> {
                if(people.size() > 0) {
                    if (index == 0) {
                        index = people.size() - 1;
                    } else {
                        index -= 1;
                    }
                    lblResultFirstName.setText(people.get(index).getFirstName());
                    lblResultLastName.setText(people.get(index).getLastName());
                    lblResultEmail.setText(people.get(index).getEmail());
                    lblResultNumber.setText(people.get(index).getNumber());
                }
                e.consume();
            }
        );

        btnEdit.setOnAction(
            e -> {
                txtEditFirstName.setText(lblResultFirstName.getText());
                txtEditLastName.setText(lblResultLastName.getText());
                txtEditEmail.setText(lblResultEmail.getText());
                txtEditNumber.setText(lblResultNumber.getText());
                frame.getSelectionModel().select(tabEdit);
                e.consume();
            }
        );

        btnSave.setOnAction(
            e -> {
                if(txtEditFirstName.getText().length() == 0) {
                    lblInfoEdit.showError("Error: Invalid First Name.");
                }else if(txtEditLastName.getText().length() == 0){
                    lblInfoEdit.showError("Error: Invalid Last Name.");
                }else if(txtEditEmail.getText().length() > 0 && !txtEditEmail.getText().contains("@")){
                    lblInfoEdit.showError("Error: Invalid Email.");
                }else if(txtEditNumber.getText().length() > 0 && (!txtEditNumber.getText().contains("(") || !txtEditNumber.getText().contains(")") || !txtEditNumber.getText().contains("-") || txtEditNumber.getText().length() != 17)){
                    lblInfoEdit.showError("Error: Invalid Phone Number.");
                }else{
                    lblInfoEdit.showInfo("Successfully editted: " + txtEditFirstName.getText() + " " + txtEditLastName.getText());
                    people.get(index).setFirstName(txtEditFirstName.getText());
                    people.get(index).setLastName(txtEditLastName.getText());
                    people.get(index).setEmail(txtEditEmail.getText());
                    people.get(index).setNumber(txtEditNumber.getText());
                }
                e.consume();
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
                e.consume();
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
                e.consume();
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
