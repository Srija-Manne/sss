package workload.masterdata.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import workload.masterdata.entity.Faculty;
import workload.masterdata.repository.FacultyRepository;
@Service
public class FacultyService 
{

    @Autowired
    private FacultyRepository facultyRepository;

    public boolean processCSV(MultipartFile file) 
    {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            // Skip the header
            br.readLine();

            while ((line = br.readLine()) != null) 
            {
                String[] columns = line.split(",");
                if (columns.length < 5) {
                    return false; // CSV does not have the required columns
                }

                // Extract values from columns
                Long id = Long.parseLong(columns[0]);
                String facultyName = columns[1];
                String desig = columns[2];
                String day = columns[3];
                String morningSlot = columns[4];
                String afternoonSlot = columns.length > 5 ? columns[5] : "";

                // Find or create a new Faculty entity
                Faculty faculty = facultyRepository.findById(id).orElse(new Faculty());
                faculty.setName(facultyName);
                faculty.setDesig(desig);

                // Assign slots based on the day
                switch (day.toUpperCase()) {
                    case "MONDAY":
                        faculty.setMONDAYM(morningSlot.isEmpty() ? "Yes" : "No");
                        faculty.setMONDAYA(afternoonSlot.isEmpty() ? "Yes" : "No");
                        break;
                    case "TUESDAY":
                        faculty.setTUESDAYM(morningSlot.isEmpty() ? "Yes" : "No");
                        faculty.setTUESDAYA(afternoonSlot.isEmpty() ? "Yes" : "No");
                        break;
                    case "WEDNESDAY":
                        faculty.setWEDNESDAYM(morningSlot.isEmpty() ? "Yes" : "No");
                        faculty.setWEDNESDAYA(afternoonSlot.isEmpty() ? "Yes" : "No");
                        break;
                    case "THURSDAY":
                        faculty.setTHURSDAYM(morningSlot.isEmpty() ? "Yes" : "No");
                        faculty.setTHURSDAYA(afternoonSlot.isEmpty() ? "Yes" : "No");
                        break;
                    case "FRIDAY":
                        faculty.setFRIDAYM(morningSlot.isEmpty() ? "Yes" : "No");
                        faculty.setFRIDAYA(afternoonSlot.isEmpty() ? "Yes" : "No");
                        break;
                    case "SATURDAY":
                        faculty.setSATURDAYM(morningSlot.isEmpty() ? "Yes" : "No");
                        faculty.setSATURDAYA(afternoonSlot.isEmpty() ? "Yes" : "No");
                        break;
                }

                // Save the faculty to the database
                facultyRepository.save(faculty);
            }
            return true; // Successfully processed and saved
        } catch (IOException e) {
            e.printStackTrace();
            return false; // Error processing the file
        }
    }
}
