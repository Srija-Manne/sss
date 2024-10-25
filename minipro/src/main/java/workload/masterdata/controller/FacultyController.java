package workload.masterdata.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.ui.Model;
import workload.masterdata.service.FacultyService;

@Controller
public class FacultyController {

    @Autowired
    private FacultyService facultyService;

    @GetMapping("/admin")
    public String adminPage() {
        return "admin"; // Thymeleaf template for admin page
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, Model model) {
        boolean isProcessed = facultyService.processCSV(file);
        if (isProcessed) {
            model.addAttribute("message", "File processed and data successfully stored in the database.");
        } else {
            model.addAttribute("message", "Error processing file or storing data in the database.");
        }
        return "admin";
    }
}
