package FileUpload.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import FileUpload.Entity.FileDetails;

public interface FileRepository extends JpaRepository<FileDetails,Long> {
	 //@Query("SELECT f FROM FileDetails f WHERE f.createdAt < ?1")

	  
	List<FileDetails> findByCreatedAtBefore(LocalDateTime dateTime);

}
