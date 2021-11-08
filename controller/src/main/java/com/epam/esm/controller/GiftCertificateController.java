package com.epam.esm.controller;

import com.epam.esm.controller.util.assembler.GiftCertificateModelAssembler;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.exception.DataNotFoundException;
import com.epam.esm.service.exception.IllegalPageNumberException;
import com.epam.esm.service.exception.ParameterNotPresentException;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/gift-certificates")
public class GiftCertificateController {

    private GiftCertificateService giftCertificateService;

    private GiftCertificateModelAssembler certificateModelAssembler;

    public GiftCertificateController(GiftCertificateService giftCertificateService,
                                     GiftCertificateModelAssembler certificateModelAssembler) {
        this.giftCertificateService = giftCertificateService;
        this.certificateModelAssembler = certificateModelAssembler;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<GiftCertificate> findGiftCertificate(@PathVariable("id") Long id)
            throws ParameterNotPresentException, DataNotFoundException {
        GiftCertificate certificate = giftCertificateService.find(id);
        return certificateModelAssembler.toModel(certificate);
    }

    @GetMapping(params = {"page"})
    @ResponseStatus(HttpStatus.OK)
    public List<EntityModel<GiftCertificate>> findGiftCertificates(@RequestParam("page") Integer page)
            throws IllegalPageNumberException {
        List<GiftCertificate> certificates = giftCertificateService.findAll(page);
        return certificateModelAssembler.toCollectionModel(certificates);
    }

    @PostMapping
    public ResponseEntity<Boolean> addGiftCertificate(@RequestBody GiftCertificate certificate) {
        boolean result = giftCertificateService.add(certificate);
        HttpStatus httpStatus = result ? HttpStatus.CREATED : HttpStatus.NOT_MODIFIED;
        return new ResponseEntity<>(result, httpStatus);
    }

    @PostMapping(params = {"certificate-id", "user-id"})
    public ResponseEntity<Boolean> addCertificateToUser(@RequestParam("certificate-id") Long certificateId,
                                                        @RequestParam("user-id") Long userId)
            throws ParameterNotPresentException, DataNotFoundException {
        boolean result = giftCertificateService.addCertificateToUser(certificateId, userId);
        HttpStatus httpStatus = result ? HttpStatus.CREATED : HttpStatus.NOT_MODIFIED;
        return new ResponseEntity<>(result, httpStatus);
    }

    @PatchMapping
    public ResponseEntity<GiftCertificate> updateGiftCertificate(@RequestBody GiftCertificate giftCertificate)
            throws ParameterNotPresentException, DataNotFoundException {
        GiftCertificate updatedGiftCertificate = giftCertificateService.update(giftCertificate);
        HttpStatus httpStatus = (updatedGiftCertificate != null) ? HttpStatus.OK : HttpStatus.NOT_MODIFIED;
        return new ResponseEntity<>(updatedGiftCertificate, httpStatus);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Boolean> deleteGiftCertificate(@PathVariable("id") Long id)
            throws ParameterNotPresentException, DataNotFoundException {
        boolean result = giftCertificateService.delete(id);
        HttpStatus httpStatus = result ? HttpStatus.OK : HttpStatus.NOT_MODIFIED;
        return new ResponseEntity<>(result, httpStatus);
    }

    @GetMapping(params = {"tag-name"})
    @ResponseStatus(HttpStatus.OK)
    public List<EntityModel<GiftCertificate>> findGiftCertificateByTagName(@RequestParam("tag-name") String tagName) {
        return certificateModelAssembler.toCollectionModel(giftCertificateService.findByTagName(tagName));
    }

    @GetMapping(params = {"tag-names", "page"})
    @ResponseStatus(HttpStatus.OK)
    public List<EntityModel<GiftCertificate>> findCertificatesByTags(@RequestParam("tag-names") List<String> tagNames,
                                                                     @RequestParam("page") Integer page)
            throws IllegalPageNumberException {
        String[] tagNamesArray = new String[tagNames.size()];
        List<GiftCertificate> certificates
                = giftCertificateService.findCertificatesByTags(page, tagNames.toArray(tagNamesArray));
        return certificateModelAssembler.toCollectionModel(certificates);
    }

    @GetMapping(params = {"name", "description", "page"})
    @ResponseStatus(HttpStatus.OK)
    public List<EntityModel<GiftCertificate>> findByNameAndDescription(@RequestParam("name") String name,
                                                                       @RequestParam("description") String description,
                                                                       @RequestParam("page") Integer page)
            throws IllegalPageNumberException {
        List<GiftCertificate> certificates = giftCertificateService.findByNameAndDescription(name, description, page);
        return certificateModelAssembler.toCollectionModel(certificates);
    }

    @GetMapping(params = {"sorting-value", "descending", "page"})
    @ResponseStatus(HttpStatus.OK)
    public List<EntityModel<GiftCertificate>> findSorted(@RequestParam("sorting-value") String sortingValue,
                                                         @RequestParam("descending") boolean descending,
                                                         @RequestParam("page") Integer page)
            throws IllegalPageNumberException {
        List<GiftCertificate> certificates = giftCertificateService.findSorted(sortingValue, descending, page);
        return certificateModelAssembler.toCollectionModel(certificates);
    }

}

