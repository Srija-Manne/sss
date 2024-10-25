package workload.masterdata.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import workload.masterdata.entity.Faculty;

import java.util.Optional;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    Optional<Faculty> findByName(String name);
}
