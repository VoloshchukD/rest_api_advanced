package com.epam.esm.controller;

import com.epam.esm.controller.util.CustomLinkBuilder;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagService;
import com.epam.esm.service.exception.DataNotFoundException;
import com.epam.esm.service.exception.ParameterNotPresentException;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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
        Tag tag = tagService.find(id);
        tag.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TagController.class).findTag(tag.getId()))
                .withSelfRel());
        tag.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TagController.class)
                .findTags(CustomLinkBuilder.limit, CustomLinkBuilder.offset))
                .withRel(CustomLinkBuilder.TAG_RELATION_NAME));
        tag.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TagController.class)
                .deleteTag(tag.getId())).withRel(CustomLinkBuilder.TAG_RELATION_NAME));
        return tag;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Tag> findTags(@RequestParam Integer limit, @RequestParam Integer offset)
            throws ParameterNotPresentException, DataNotFoundException {
        List<Tag> tags = tagService.findAll(limit, offset);
        for (Tag tag : tags) {
            tag.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TagController.class).findTag(tag.getId()))
                    .withSelfRel());
            tag.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TagController.class)
                    .deleteTag(tag.getId())).withRel(CustomLinkBuilder.TAG_RELATION_NAME));
        }
        return tags;
    }

    @PostMapping
    public ResponseEntity<Boolean> addTag(@RequestBody Tag tag) {
        boolean result = tagService.add(tag);
        HttpStatus httpStatus = result ? HttpStatus.CREATED : HttpStatus.NOT_MODIFIED;
        return new ResponseEntity(result, httpStatus);
    }

    @PatchMapping
    public ResponseEntity<Tag> updateTag(@RequestBody Tag tag)
            throws ParameterNotPresentException, DataNotFoundException {
        Tag updatedTag = tagService.update(tag);
        HttpStatus httpStatus = (updatedTag != null) ? HttpStatus.OK : HttpStatus.NOT_MODIFIED;
        return new ResponseEntity(updatedTag, httpStatus);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Boolean> deleteTag(@PathVariable("id") Long id)
            throws ParameterNotPresentException, DataNotFoundException {
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

    @PostMapping(params = {"certificateId"})
    public ResponseEntity<Boolean> addTagToCertificate(@RequestBody Tag tag,
                                                       @RequestParam("certificateId") Long certificateId)
            throws ParameterNotPresentException {
        boolean result = tagService.addTagToCertificate(tag, certificateId);
        HttpStatus httpStatus = result ? HttpStatus.CREATED : HttpStatus.NOT_MODIFIED;
        return new ResponseEntity(result, httpStatus);
    }

    @DeleteMapping
    public ResponseEntity<Boolean> deleteTagFromCertificate(@RequestParam Long certificateId, @RequestParam Long tagId)
            throws ParameterNotPresentException {
        boolean result = tagService.deleteTagFromCertificate(certificateId, tagId);
        HttpStatus httpStatus = result ? HttpStatus.OK : HttpStatus.NOT_MODIFIED;
        return new ResponseEntity(result, httpStatus);
    }

    @GetMapping(value = "/popular", params = {"userId"})
    @ResponseStatus(HttpStatus.OK)
    public Tag findPopularTag(@RequestParam("userId") Long userId) throws ParameterNotPresentException {
        return tagService.findPopularTag(userId);
    }

}
