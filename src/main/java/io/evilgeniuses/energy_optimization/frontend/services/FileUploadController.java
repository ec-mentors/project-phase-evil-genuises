package io.evilgeniuses.energy_optimization.frontend.services;


import io.evilgeniuses.energy_optimization.parsing.FileParser_Upload;
import io.evilgeniuses.energy_optimization.parsing.CustomUploadData;
import io.evilgeniuses.energy_optimization.storage.FileSystemStorageService;
import io.evilgeniuses.energy_optimization.storage.StorageFileNotFoundException;
import io.evilgeniuses.energy_optimization.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Comparator;
import java.util.stream.Collectors;

@Controller
public class FileUploadController {

    private final StorageService storageService;
    private final FileParser_Upload upload;
    private final FileSystemStorageService fileSystemStorageService;

    @Autowired
    public FileUploadController(StorageService storageService, FileParser_Upload upload, FileSystemStorageService fileSystemStorageService) {
        this.storageService = storageService;
        this.upload = upload;
        this.fileSystemStorageService = fileSystemStorageService;
    }

    @GetMapping("/upload")
    public String listUploadedFiles(Model model) throws IOException {

        model.addAttribute("files", storageService.loadAll().map(
                        path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                                "serveFile", path.getFileName().toString()).build().toUri().toString())
                .collect(Collectors.toList()));

        return "uploadForm";
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

        storageService.store(file);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        var uploadData = fileSystemStorageService.getUploadDataList()
                .stream()
                .sorted(Comparator.comparing(CustomUploadData::getTimestamp))
                .toList();

        upload.parseAndSave("upload-dir/" + uploadData.get(uploadData.size() -1).getFileName());

        return "uploadForm";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }


}
