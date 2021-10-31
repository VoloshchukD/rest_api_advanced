package com.epam.esm.controller;

import com.epam.esm.controller.util.CustomLinkBuilder;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.exception.DataNotFoundException;
import com.epam.esm.service.exception.ParameterNotPresentException;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/gift-certificates")
public class GiftCertificateController {

    private GiftCertificateService giftCertificateService;

    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificate findGiftCertificate(@PathVariable("id") Long id)
            throws ParameterNotPresentException, DataNotFoundException {
        GiftCertificate certificate = giftCertificateService.find(id);
        certificate.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(GiftCertificateController.class)
                .findGiftCertificate(certificate.getId()))
                .withSelfRel());
        certificate.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(GiftCertificateController.class)
                .findGiftCertificates(CustomLinkBuilder.limit, CustomLinkBuilder.offset))
                .withRel(CustomLinkBuilder.CERTIFICATE_RELATION_NAME));
        certificate.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(GiftCertificateController.class)
                .deleteGiftCertificate(certificate.getId()))
                .withRel(CustomLinkBuilder.CERTIFICATE_RELATION_NAME));
        return certificate;
    }

    @GetMapping(params = {"limit", "offset"})
    @ResponseStatus(HttpStatus.OK)
    public List<GiftCertificate> findGiftCertificates(@RequestParam("limit") Integer limit,
                                                      @RequestParam("offset") Integer offset)
            throws ParameterNotPresentException, DataNotFoundException {
        List<GiftCertificate> certificates = new ArrayList<>();
        for (GiftCertificate certificate : certificates){
            certificate.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(GiftCertificateController.class)
                    .findGiftCertificate(certificate.getId()))
                    .withSelfRel());
            certificate.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(GiftCertificateController.class)
                    .deleteGiftCertificate(certificate.getId()))
                    .withRel(CustomLinkBuilder.CERTIFICATE_RELATION_NAME));
        }
        return certificates;
    }

    @PostMapping
    public ResponseEntity<Boolean> addGiftCertificate(@RequestBody GiftCertificate certificate) {
        boolean result = giftCertificateService.add(certificate);
        HttpStatus httpStatus = result ? HttpStatus.CREATED : HttpStatus.NOT_MODIFIED;
        return new ResponseEntity(result, httpStatus);
    }

    @PostMapping(params = {"certificateId", "userId"})
    public ResponseEntity<Boolean> addCertificateToUser(@RequestParam("certificateId") Long certificateId,
                                                        @RequestParam("userId") Long userId)
            throws ParameterNotPresentException {
        boolean result = giftCertificateService.addCertificateToUser(certificateId, userId);
        HttpStatus httpStatus = result ? HttpStatus.CREATED : HttpStatus.NOT_MODIFIED;
        return new ResponseEntity(result, httpStatus);
    }

    @PatchMapping
    public ResponseEntity<GiftCertificate> updateGiftCertificate(@RequestBody GiftCertificate giftCertificate)
            throws ParameterNotPresentException, DataNotFoundException {
        GiftCertificate updatedGiftCertificate = giftCertificateService.update(giftCertificate);
        HttpStatus httpStatus = (updatedGiftCertificate != null)
                ? HttpStatus.OK : HttpStatus.NOT_MODIFIED;
        return new ResponseEntity(updatedGiftCertificate, httpStatus);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<GiftCertificate> deleteGiftCertificate(
            @PathVariable("id") Long id) throws ParameterNotPresentException {
        boolean result = giftCertificateService.delete(id);
        HttpStatus httpStatus = result ? HttpStatus.OK : HttpStatus.NOT_MODIFIED;
        return new ResponseEntity(result, httpStatus);
    }

    @GetMapping(params = {"tagName"})
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificate findGiftCertificateByTagName(@RequestParam("tagName") String tagName) {
        return giftCertificateService.findByTagName(tagName);
    }

    @GetMapping(params = {"tagNames", "limit", "offset"})
    @ResponseStatus(HttpStatus.OK)
    public List<GiftCertificate> findCertificatesByTags(@RequestParam("tagNames") List<String> tagNames,
                                                       @RequestParam("limit") Integer limit,
                                                       @RequestParam("offset") Integer offset) {
        String[] tagNamesArray = new String[tagNames.size()];
        return giftCertificateService.findCertificatesByTags(limit, offset, tagNames.toArray(tagNamesArray));
    }

    @GetMapping(params = {"name", "description", "limit", "offset"})
    @ResponseStatus(HttpStatus.OK)
    public List<GiftCertificate> findByNameAndDescription(@RequestParam("name") String name,
                                                          @RequestParam("description") String description,
                                                          @RequestParam("limit") Integer limit,
                                                          @RequestParam("offset") Integer offset)
            throws ParameterNotPresentException, DataNotFoundException {
        List<GiftCertificate> certificates = giftCertificateService.findByNameAndDescription(name,
                description, limit, offset);
        for (GiftCertificate certificate : certificates){
            certificate.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(GiftCertificateController.class)
                    .findGiftCertificate(certificate.getId()))
                    .withSelfRel());
            certificate.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(GiftCertificateController.class)
                    .deleteGiftCertificate(certificate.getId()))
                    .withRel(CustomLinkBuilder.CERTIFICATE_RELATION_NAME));
        }
        return certificates;
    }

    @GetMapping(params = {"sort", "descending", "limit", "offset"})
    @ResponseStatus(HttpStatus.OK)
    public List<GiftCertificate> findSorted(@RequestParam("sortingParameter") String sortingParameter,
                                            @RequestParam("descending") boolean descending,
                                            @RequestParam("limit") Integer limit,
                                            @RequestParam("offset") Integer offset)
            throws ParameterNotPresentException, DataNotFoundException {
        List<GiftCertificate> certificates = giftCertificateService.findSorted(sortingParameter,
                descending, limit, offset);
        for (GiftCertificate certificate : certificates){
            certificate.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(GiftCertificateController.class)
                    .findGiftCertificate(certificate.getId()))
                    .withSelfRel());
            certificate.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(GiftCertificateController.class)
                    .deleteGiftCertificate(certificate.getId()))
                    .withRel(CustomLinkBuilder.CERTIFICATE_RELATION_NAME));
        }
        return certificates;
    }

}

