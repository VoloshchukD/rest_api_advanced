package com.epam.esm.core.controller;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.exception.DataNotFoundException;
import com.epam.esm.service.exception.ParameterNotPresentException;
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

import java.util.List;

@RestController
@RequestMapping(path = "/gift-certificates")
public class GiftCertificateController {

    private GiftCertificateService giftCertificateService;

    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificate findGiftCertificate(@PathVariable("id") Long id)
            throws ParameterNotPresentException, DataNotFoundException {
        return giftCertificateService.find(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<GiftCertificate> findGiftCertificates() {
        return giftCertificateService.findAll();
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Boolean> addGiftCertificate(@RequestBody GiftCertificate certificate) {
        boolean result = giftCertificateService.add(certificate);
        HttpStatus httpStatus = result ? HttpStatus.CREATED : HttpStatus.NOT_MODIFIED;
        return new ResponseEntity(result, httpStatus);
    }

    @PatchMapping(consumes = "application/json")
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

    @GetMapping(params = {"name", "description"})
    @ResponseStatus(HttpStatus.OK)
    public List<GiftCertificate> findByNameAndDescription(@RequestParam("name") String name,
                                                          @RequestParam("description")
                                                                  String description) {
        return giftCertificateService.findByNameAndDescription(name, description);
    }

    @GetMapping(params = {"sortingParameter", "descending"})
    @ResponseStatus(HttpStatus.OK)
    public List<GiftCertificate> findSorted(@RequestParam("sortingParameter") String sortingParameter,
                                            @RequestParam("descending") boolean descending) {
        return giftCertificateService.findSorted(sortingParameter, descending);
    }

}

