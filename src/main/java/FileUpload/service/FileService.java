package FileUpload.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import FileUpload.Entity.FileDetails;
import FileUpload.Repository.FileRepository;

@Service
public class FileService {
	@Autowired
	private FileRepository fileRepository;
	  

	
	public FileDetails saveToFile(MultipartFile file)throws Exception
	{
		String filename=StringUtils.cleanPath(file.getOriginalFilename());
		FileDetails fileDetails=new FileDetails(filename,file.getContentType(),file.getBytes());
        FileDetails savedFile = fileRepository.save(fileDetails);		
		// Schedule a task to delete the file after 7 minutes

      //  scheduledFuture = scheduledExecutorService.schedule(() -> deleteFile(savedFile.getId()), 7, TimeUnit.MINUTES);


        return savedFile;
	}
	
	
	
	public FileDetails getFileById(Long id) throws Exception 
	{
        return fileRepository.findById(id).orElseThrow(() -> new Exception("File not found with id " + id));
    }
	
	//@Scheduled(fixedDelay=5 * 60 * 1000)
	@Scheduled(cron="0 0/1 * * * ?")
	
    public void deleteOldFiles() {
//
//        LocalDateTime currentTime = LocalDateTime.now();
//
       LocalDateTime oneDayAgo =LocalDateTime.now().minusMinutes(2) ;
//
//        Date oneDayAgoDate = Date.from(oneDayAgo.atZone(ZoneId.systemDefault()).toInstant());
//
//
      List<FileDetails> filesToDelete = fileRepository.findByCreatedAtBefore(oneDayAgo);
  for(FileDetails file:filesToDelete)
     fileRepository.delete(file);

    }
	
}
