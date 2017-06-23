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
import java.util.Optional;

/**
 * Created by agaspari on 6/19/2017.
 */
public class MainFrame extends TabPane{
    private Tab tabAdd, tabSearch, tabView, tabEdit, tabExport, tabImport, tabSettings;
    private CustomGridPane paneAdd, paneSearch, paneView, paneEdit, paneExport, paneImport, paneSettings;

    private Label lblFirstName, lblLastName, lblEmail, lblNumber, lblViewFirstName, lblViewLastName, lblViewEmail, lblViewNumber, lblResultFirstName, lblResultLastName, lblResultEmail, lblResultNumber, lblEditFirstName, lblEditLastName, lblEditEmail, lblEditNumber, lblSearchFirstName, lblSearchLastName, lblSearchEmail, lblSearchNumber, lblSearchResultFirstName, lblSearchResultLastName, lblSearchResultEmail, lblSearchResultNumber;
    private InfoLabel lblInfoAdd, lblInfoEdit;
    private TextField txtFirstName, txtLastName, txtEmail, txtNumber, txtEditFirstName, txtEditLastName, txtEditEmail, txtEditNumber, txtSearch;
    private CustomButton btnAdd, btnExport, btnImport, btnViewPrevious, btnViewNext, btnSave, btnEdit, btnDelete, btnSearch, btnSearchPrevious, btnSearchNext, btnEditSearch;
    //TODO: Potentially remove all informative labels and replace with TextField promptTexts
    //TODO: Make every tab an object (Maybe not)

    private static ArrayList<Person> people;
    private ArrayList<Person> searchResults;
    private static Encrypter encrypter;
    private static File contacts;
    private int index, searchIndex;
    private static boolean saved = true;

    public MainFrame(){
        encrypter = new Encrypter();
        contacts = new File("contact-list.wver");
        searchResults = new ArrayList<>();
        people = importContactList(contacts);
        index = -1;
        searchIndex = -1;
        setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        setStyle(" -fx-background-color: #3c3c3c;");

        tabAdd = new Tab("Add");
        tabSearch = new Tab("Search");
        tabView = new Tab("View");
        tabEdit = new Tab("Edit");
        tabExport = new Tab("Export");
        tabImport = new Tab("Import");
        tabSettings = new Tab("Settings");

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

        tabSearch.setOnSelectionChanged(
                e -> {
                    if(searchIndex != -1){
                        lblResultFirstName.setText(people.get(searchIndex).getFirstName());
                        lblResultLastName.setText(people.get(searchIndex).getLastName());
                        lblResultEmail.setText(people.get(searchIndex).getEmail());
                        lblResultNumber.setText(people.get(searchIndex).getNumber());
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
        btnSearchPrevious = new CustomButton("Previous");
        btnSearchNext = new CustomButton("Next");
        btnEditSearch = new CustomButton("Edit");
        btnSearch = new CustomButton("Search");
        paneSearch.add(lblSearchFirstName, 0, 0);
        paneSearch.add(lblSearchResultFirstName, 1, 0, 2, 1);
        paneSearch.add(lblSearchLastName, 0, 1);
        paneSearch.add(lblSearchResultLastName, 1, 1, 2, 1);
        paneSearch.add(lblSearchEmail, 0, 2);
        paneSearch.add(lblSearchResultEmail, 1, 2, 2, 1);
        paneSearch.add(lblSearchNumber, 0, 3);
        paneSearch.add(lblSearchResultNumber, 1, 3, 2, 1);
        paneSearch.add(btnSearchPrevious, 0, 4);
        paneSearch.add(btnSearchNext, 1, 4);
        paneSearch.add(txtSearch, 0, 5, 2, 1);
        paneSearch.add(btnSearch, 0, 6, 2, 1);
        paneSearch.add(btnEditSearch, 0, 7, 2, 1);

        paneView = new CustomGridPane();
        paneView.addColumns(2);
        lblViewFirstName = new CustomLabel("First Name: ");
        lblViewLastName = new CustomLabel("Last Name: ");
        lblViewEmail = new CustomLabel("Email: ");
        lblViewNumber = new CustomLabel("Phone Number: ");
        lblResultFirstName = new CustomLabel(""); //TODO: Change result to something else
        lblResultLastName = new CustomLabel("");
        lblResultEmail = new CustomLabel("");
        lblResultNumber = new CustomLabel("");
        btnViewPrevious = new CustomButton("Previous");
        btnViewNext = new CustomButton("Next");
        btnEdit = new CustomButton("Edit");
        paneView.add(lblViewFirstName, 0, 0);
        paneView.add(lblResultFirstName, 1, 0, 2, 1);
        paneView.add(lblViewLastName, 0, 1);
        paneView.add(lblResultLastName, 1, 1, 2, 1);
        paneView.add(lblViewEmail, 0, 2);
        paneView.add(lblResultEmail, 1, 2, 2, 1);
        paneView.add(lblViewNumber, 0, 3);
        paneView.add(lblResultNumber, 1, 3, 2, 1);
        paneView.add(btnViewPrevious, 0, 4);
        paneView.add(btnViewNext, 1, 4);
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
        btnDelete = new CustomButton("Delete");
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

        paneSettings = new CustomGridPane();

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
                    saved = false;
                }
                e.consume();
            }
        );

        btnSearch.setOnAction(
             e -> {
                 String searchTerm = txtSearch.getText();
                 searchResults.clear();
                 for(Person aPeople : people){
                    if(aPeople.getFirstName().equalsIgnoreCase(searchTerm)){
                        searchResults.add(aPeople);
                    }
                 }
                 //Second pass since I want names containing at the end of the list.
                 for(Person aPeople : people){
                     if(!searchResults.contains(aPeople) && aPeople.getFirstName().contains(searchTerm)){
                         searchResults.add(aPeople);
                     }
                 }
                 if(searchResults.size() > 0){
                     lblSearchResultFirstName.setText(searchResults.get(0).getFirstName());
                     lblSearchResultLastName.setText(searchResults.get(0).getLastName());
                     lblSearchResultEmail.setText(searchResults.get(0).getEmail());
                     lblSearchResultNumber.setText(searchResults.get(0).getNumber());
                     searchIndex = 0;
                 }
                 e.consume();
             }
        );

        btnSearchNext.setOnAction(
            e -> {
                if(searchResults.size() > 0){
                    if (searchIndex == searchResults.size() - 1){
                        searchIndex = 0;
                    } else {
                        searchIndex += 1;
                    }
                    lblSearchResultFirstName.setText(searchResults.get(searchIndex).getFirstName());
                    lblSearchResultLastName.setText(searchResults.get(searchIndex).getLastName());
                    lblSearchResultEmail.setText(searchResults.get(searchIndex).getEmail());
                    lblSearchResultNumber.setText(searchResults.get(searchIndex).getNumber());
                }
                e.consume();
            }
        );

        btnSearchPrevious.setOnAction(
            e -> {
                if(searchResults.size() > 0){
                    if (searchIndex == 0) {
                        searchIndex = searchResults.size() - 1;
                    } else {
                        searchIndex -= 1;
                    }
                    lblSearchResultFirstName.setText(searchResults.get(searchIndex).getFirstName());
                    lblSearchResultLastName.setText(searchResults.get(searchIndex).getLastName());
                    lblSearchResultEmail.setText(searchResults.get(searchIndex).getEmail());
                    lblSearchResultNumber.setText(searchResults.get(searchIndex).getNumber());
                }
                e.consume();
            }
        );

        btnEditSearch.setOnAction(
            e -> {
                txtEditFirstName.setText(lblSearchResultFirstName.getText());
                txtEditLastName.setText(lblSearchResultLastName.getText());
                txtEditEmail.setText(lblSearchResultEmail.getText());
                txtEditNumber.setText(lblSearchResultNumber.getText());
                getTabs().add(3, tabEdit);
                getSelectionModel().select(tabEdit);
                e.consume();
            }
        );

        btnViewNext.setOnAction(
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

        btnViewPrevious.setOnAction(
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
                getTabs().add(3, tabEdit);
                getSelectionModel().select(tabEdit);
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
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Overwrite Contact");
                    alert.setHeaderText("Clicking OK will overwrite the current contacts inforamtion.");
                    alert.setContentText("You will not be able to revert this edit, are you sure you want to do this?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK){
                        people.get(index).setFirstName(txtEditFirstName.getText());
                        people.get(index).setLastName(txtEditLastName.getText());
                        people.get(index).setEmail(txtEditEmail.getText());
                        people.get(index).setNumber(txtEditNumber.getText());
                        getSelectionModel().select(tabView);
                        getTabs().remove(tabEdit);
                        saved = false;
                    } else {}
                }
                e.consume();
            }
        );

        btnDelete.setOnAction(
            e -> {
                //TODO: Find Blank Error
                if(index != -1){
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Delete Contact");
                    alert.setHeaderText("Clicking OK will delete the contact permanently.");
                    alert.setContentText("You will not be able to retrieve this contact, are you sure you want to do this?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK){
                        people.remove(index);
                        index--;
                        txtEditFirstName.setText("");
                        txtEditLastName.setText("");
                        txtEditEmail.setText("");
                        txtEditNumber.setText("");
                        getSelectionModel().select(tabView);
                        getTabs().remove(tabEdit);
                        saved = false;
                    } else {}
                }
                e.consume();
            }
        );

        btnExport.setOnAction(
            e -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Save Information");
                alert.setHeaderText("Would you like to save the data you entered or edited?");
                alert.setContentText("Saving will overwrite previously saved data, would you like to continue?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    save();
                } else {}

                e.consume();
            }
        );

        tabAdd.setContent(paneAdd);
        tabSearch.setContent(paneSearch);
        tabView.setContent(paneView);
        tabEdit.setContent(paneEdit);
        tabExport.setContent(paneExport);
        tabImport.setContent(paneImport);
        tabSettings.setContent(paneSettings);

        getTabs().addAll(tabAdd, tabSearch, tabView, tabExport, tabImport, tabSettings);
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

    public static boolean getSaved(){
        return saved;
    }

    public static void save(){
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
                    contacts.createNewFile();
                    //TODO: Potentially change to FileWriter
                    PrintWriter writer = new PrintWriter(contacts);
                    DOMSource source = new DOMSource(doc);
                    StringWriter sw = new StringWriter();
                    StreamResult result2 = new StreamResult(sw);
                    transformer.transform(source, result2);
                    writer.print(encrypter.encryptText(sw.toString()));
                    writer.close();
                    saved = true;
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        } catch (ParserConfigurationException | TransformerException e1) {
            e1.printStackTrace();
        }
    }
}