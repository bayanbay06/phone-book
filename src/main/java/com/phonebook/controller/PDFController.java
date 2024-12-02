package com.phonebook.controller;

import com.phonebook.model.Contact;
import com.phonebook.service.ContactService;
import com.phonebook.service.PDFService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class PDFController {

    private final ContactService contactService;
    private final PDFService pdfService;

    public PDFController(ContactService contactService, PDFService pdfService) {
        this.contactService = contactService;
        this.pdfService = pdfService;
    }

    @GetMapping("/contacts/pdf")
    public ResponseEntity<ByteArrayResource> downloadPdf() throws IOException {
        List<Contact> contacts = contactService.getAllContacts();
        byte[] pdfData = pdfService.generatePdf(contacts);

        ByteArrayResource resource = new ByteArrayResource(pdfData);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=contacts.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(pdfData.length)
                .body(resource);
    }


}
