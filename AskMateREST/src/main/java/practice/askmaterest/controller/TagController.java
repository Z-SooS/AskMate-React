package practice.askmaterest.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import practice.askmaterest.model.Tag;
import practice.askmaterest.services.TagService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("${tag_service_path}")
public class TagController {

    private TagService tagService;

    @GetMapping("/all-tags")
    public ResponseEntity<List<Tag>> getTags() {
        var result = tagService.getTags();
        if (result.isEmpty()) return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
