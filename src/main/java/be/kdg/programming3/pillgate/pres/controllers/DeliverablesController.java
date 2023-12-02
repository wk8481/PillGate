package be.kdg.programming3.pillgate.pres.controllers;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;

@Controller
public class DeliverablesController {

    @GetMapping("/deliverables")
    public String showDeliverables(Model model) {
        // You can add any additional model attributes needed for the page
        return "deliverables";
    }

    @GetMapping("/pdf/{filename:.+}")
    public ResponseEntity<Resource> viewPDF(@PathVariable String filename) throws IOException {
        // Load PDF file from the resources directory
        Resource file = new ClassPathResource("static/pdfs/" + filename);

        // Set up the response headers
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + filename);
        headers.setContentType(MediaType.APPLICATION_PDF);

        // Return the PDF file as a ResponseEntity
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(file);
    }
}
