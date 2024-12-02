package com.phonebook.service;

import com.phonebook.model.Contact;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class PDFService {
    public byte[] generatePdf(List<Contact> contacts) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
                contentStream.beginText();
                contentStream.setLeading(20f);
                contentStream.newLineAtOffset(50, 750);

                contentStream.showText("Contact List");
                contentStream.newLine();

                contentStream.setFont(PDType1Font.HELVETICA, 12);
                for (Contact contact : contacts) {
                    contentStream.showText(
                            "Name: " + contact.getName() +
                                    ", Phone: " + contact.getPhoneNumber() +
                                    ", Email: " + contact.getEmail()
                    );
                    contentStream.newLine();
                }

                contentStream.endText();
            } catch (IOException ioException) {
                System.out.println("Handling IOException...");
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            document.save(outputStream);
            return outputStream.toByteArray();
        }
    }
}