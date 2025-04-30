package ru.hogwarts.school.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.service.AvatarService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RestController
public class AvatarController {

    AvatarService avatarService;
    AvatarRepository avatarRepository;

    public AvatarController(AvatarService avatarService, AvatarRepository avatarRepository) {
        this.avatarService = avatarService;
        this.avatarRepository = avatarRepository;
    }

    @PostMapping(value = "{id}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)

    public ResponseEntity<String> uploadAvatar(@PathVariable long id, @RequestParam MultipartFile avatar)
            throws IOException {
        avatarService.uploadAvatar(id, avatar);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{id}/avatar/bd")
    public ResponseEntity<byte[]> downloadAvatarFromBD(@PathVariable long id) {
        Avatar avatar = avatarService.findAvatar(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        headers.setContentLength(avatar.getData().length);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatar.getData());
    }

    @GetMapping(value = "{id}/avatar/server")
    public void downloadAvatarFromServer(@PathVariable long id, HttpServletResponse response)
            throws IOException {
        Avatar avatar = avatarService.findAvatar(id);

        Path path = Path.of(avatar.getFilePath());

        try (InputStream is = Files.newInputStream(path);
             OutputStream os = response.getOutputStream();) {
            response.setContentType(avatar.getMediaType());
            response.setContentLength((int) avatar.getFileSize());
            is.transferTo(os);
        }
    }

    @GetMapping("getPageAvatars/{pageNumber},{pageSize}")
    public ResponseEntity<List<Avatar>> getPageAvatars(@PathVariable int pageNumber, @PathVariable int pageSize) {
        return ResponseEntity.ok(avatarService.getPageAvatars(pageNumber,pageSize));
    }
}
