package FileUpload.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import FileUpload.Entity.FileDetails;
import FileUpload.service.FileService;

@RestController
@RequestMapping("/api")
public class FileController {
	@Autowired
	private FileService fileService;
	@PostMapping("/upload")
	public FileDetails savefile(@RequestParam MultipartFile file)throws Exception
	{
		return fileService.saveToFile(file);
	}
	
	@GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long id) throws Exception {
        FileDetails fileDetails = fileService.getFileById(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDetails.getFileName() + "\"")
                .header(HttpHeaders.CONTENT_TYPE, fileDetails.getFileType())
                .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(fileDetails.getData().length))
                .body(fileDetails.getData());
    }

}
