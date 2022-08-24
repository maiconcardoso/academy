package academy.cardoso.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import academy.cardoso.springboot.domain.AcademyUser;

@Repository
public interface AcademyUserRepository extends JpaRepository<AcademyUser, Long>{
    
    AcademyUser findByUsername(String username);
}
