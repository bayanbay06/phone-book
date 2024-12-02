package com.phonebook.ui;


import com.phonebook.model.Contact;
import com.phonebook.service.ContactService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.apache.commons.lang3.StringUtils;

@Route("")
public class MainView extends VerticalLayout {

    private final ContactService contactService;
    private final Grid<Contact> grid;
    private final TextField nameField;
    private final TextField phoneField;
    private final EmailField emailField;
    private final Button saveButton;
    private final Button deleteButton;
    private final Button downloadPdfButton;

    private Contact selectedContact;

    public MainView(ContactService contactService) {
        this.contactService = contactService;
        this.grid = new Grid<>(Contact.class);
        this.nameField = new TextField("Name");
        this.phoneField = new TextField("Phone Number");
        this.emailField = new EmailField("Email");
        this.saveButton = new Button("Save");
        this.deleteButton = new Button("Delete");
        this.downloadPdfButton = new Button("Download PDF");

        grid.setColumns("name", "phoneNumber", "email");
        grid.setItems(contactService.getAllContacts());

        saveButton.addClickListener(e -> saveContact());
        deleteButton.addClickListener(e -> deleteContact());
        downloadPdfButton.addClickListener(e -> downloadPdf());

        grid.addSelectionListener(event -> {
            selectedContact = event.getFirstSelectedItem().orElse(null);
            if (selectedContact != null) {
                nameField.setValue(selectedContact.getName());
                phoneField.setValue(selectedContact.getPhoneNumber());
                emailField.setValue(selectedContact.getEmail());
            }
        });

        add(grid, nameField, phoneField, emailField, saveButton, deleteButton, downloadPdfButton);
    }

    private void saveContact() {
        String name = nameField.getValue();
        String phone = phoneField.getValue();
        String email = emailField.getValue();

        if (StringUtils.isBlank(name)) {
            Notification.show("Name cannot be empty", 3000, Notification.Position.MIDDLE);
            return;
        }

        if (StringUtils.isBlank(phone)) {
            Notification.show("Phone number cannot be empty", 3000, Notification.Position.MIDDLE);
            return;
        }

        if (StringUtils.isBlank(email)) {
            Notification.show("Email cannot be empty", 3000, Notification.Position.MIDDLE);
            return;
        }

        Contact contact;
        if (selectedContact != null) {
            contact = selectedContact;
        } else {
            contact = new Contact();
        }
        contact.setName(name);
        contact.setPhoneNumber(phone);
        contact.setEmail(email);
        contactService.saveContact(contact);
        refreshGrid();

        Notification.show("Contact saved successfully", 3000, Notification.Position.BOTTOM_START);
    }

    private void deleteContact() {
        if (selectedContact != null) {
            contactService.deleteContact(selectedContact.getId());
            refreshGrid();
        }
    }

    private void refreshGrid() {
        grid.setItems(contactService.getAllContacts());
        nameField.clear();
        phoneField.clear();
        emailField.clear();
        selectedContact = null;
    }

    private void downloadPdf() {
        getUI().ifPresentOrElse(ui -> ui.getPage().open("/contacts/pdf"),
                () -> Notification.show("Failed to download PDF file. Please try again."));
    }
}
