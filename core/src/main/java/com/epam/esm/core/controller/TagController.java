package com.epam.esm.core.controller;

import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagService;
import com.epam.esm.service.exception.DataNotFoundException;
import com.epam.esm.service.exception.ParameterNotPresentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/tags")
public class TagController {

    private TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Tag findTag(@PathVariable("id") Long id)
            throws ParameterNotPresentException, DataNotFoundException {
        return tagService.find(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Tag> findTags() {
        return tagService.findAll();
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Boolean> addTag(@RequestBody Tag tag) {
        boolean result = tagService.add(tag);
        HttpStatus httpStatus = result ? HttpStatus.CREATED : HttpStatus.NOT_MODIFIED;
        return new ResponseEntity(result, httpStatus);
    }

    @PatchMapping(consumes = "application/json")
    public ResponseEntity<Tag> updateTag(@RequestBody Tag tag)
            throws ParameterNotPresentException, DataNotFoundException {
        Tag updatedTag = tagService.update(tag);
        HttpStatus httpStatus = (updatedTag != null) ? HttpStatus.OK : HttpStatus.NOT_MODIFIED;
        return new ResponseEntity(updatedTag, httpStatus);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Tag> deleteTag(@PathVariable("id") Long id) throws ParameterNotPresentException {
        boolean result = tagService.delete(id);
        HttpStatus httpStatus = result ? HttpStatus.OK : HttpStatus.NOT_MODIFIED;
        return new ResponseEntity(result, httpStatus);
    }

    @PostMapping(params = {"certificateId", "tagId"})
    public ResponseEntity<Boolean> addTagToCertificate(@RequestParam("certificateId") Long certificateId,
                                                       @RequestParam("tagId") Long tagId)
            throws ParameterNotPresentException {
        boolean result = tagService.addTagToCertificate(certificateId, tagId);
        HttpStatus httpStatus = result ? HttpStatus.CREATED : HttpStatus.NOT_MODIFIED;
        return new ResponseEntity(result, httpStatus);
    }

    @PostMapping(consumes = "application/json", params = {"certificateId"})
    public ResponseEntity<Boolean> addTagToCertificate(@RequestBody Tag tag,
                                                       @RequestParam("certificateId") Long certificateId)
            throws ParameterNotPresentException {
        boolean result = tagService.addTagToCertificate(tag, certificateId);
        HttpStatus httpStatus = result ? HttpStatus.CREATED : HttpStatus.NOT_MODIFIED;
        return new ResponseEntity(result, httpStatus);
    }

    @DeleteMapping(params = {"certificateId", "tagId"})
    public ResponseEntity<Tag> deleteTagFromCertificate(@RequestParam("certificateId") Long certificateId,
                                                        @RequestParam("tagId") Long tagId)
            throws ParameterNotPresentException {
        boolean result = tagService.deleteTagFromCertificate(certificateId, tagId);
        HttpStatus httpStatus = result ? HttpStatus.OK : HttpStatus.NOT_MODIFIED;
        return new ResponseEntity(result, httpStatus);
    }

}
