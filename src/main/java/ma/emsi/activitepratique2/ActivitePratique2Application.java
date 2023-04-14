package ma.emsi.activitepratique2;

import ma.emsi.activitepratique2.entities.Patient;
import ma.emsi.activitepratique2.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Date;
import java.util.List;

@SpringBootApplication
public class ActivitePratique2Application implements CommandLineRunner {

    @Autowired
    private PatientRepository patientRepository;


    public static void main(String[] args) {
        SpringApplication.run(ActivitePratique2Application.class, args);
    }
    @Override
    public void run(String... args) throws Exception {
        for (int i = 0; i < 100; i++) {
            patientRepository.save(new Patient(null, "Hamza Haimeur", new Date(), Math.random() > 0.5, (int) (Math.random() * 100)));
        }

        Page<Patient> patients = patientRepository.findAll(PageRequest.of(0, 5));
        System.out.println("Total pages: " + patients.getTotalPages());
        System.out.println("Total elements: " + patients.getTotalElements());
        System.out.println("Page number: " + patients.getNumber());

        List<Patient> content = patients.getContent();

        // Afficher les patients malades dans une page
        Page<Patient> byMalade = patientRepository.findByMalade(true, PageRequest.of(0, 4));
        byMalade.forEach(p -> {
            System.out.println("==============================================");
            System.out.println(p.getId());
            System.out.println(p.getNom());
            System.out.println(p.getScore());
            System.out.println(p.getDateNaissance());
            System.out.println(p.isMalade());
        });

        // Liste des patients qui ont le nom "h" et un score > 40
        List<Patient> patientList = patientRepository.chercherPatients("%h%", 40);

        // Afficher le premier patient malade d'id 1
        System.out.println("******************************");
        Patient patient = patientRepository.findById(1L).orElse(null);
        if (patient != null) {
            System.out.println(patient.getNom());
            System.out.println(patient.isMalade());

            // Modifier le score du patient
            patient.setScore(870);
            patientRepository.save(patient);

            // Supprimer le patient
            patientRepository.delete(patient);
        }
    }

}