package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.exception.DataNotFoundException;
import com.epam.esm.service.exception.ParameterNotPresentException;
import com.epam.esm.service.util.ExceptionMessageHandler;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private GiftCertificateDao giftCertificateDao;

    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao) {
        this.giftCertificateDao = giftCertificateDao;
    }

    @Override
    public boolean add(GiftCertificate certificate) {
        Date currentDate = new Date();
        certificate.setCreateDate(currentDate);
        certificate.setLastUpdateDate(currentDate);
        return giftCertificateDao.add(certificate);
    }

    @Override
    public GiftCertificate find(Long id) throws ParameterNotPresentException, DataNotFoundException {
        if (id == null) {
            throw new ParameterNotPresentException(ExceptionMessageHandler.CERTIFICATE_CODE,
                    ExceptionMessageHandler.CERTIFICATE_ID_NOT_PRESENT_MESSAGE_NAME);
        }
        GiftCertificate giftCertificate = giftCertificateDao.find(id);
        if (giftCertificate == null) {
            throw new DataNotFoundException(ExceptionMessageHandler.CERTIFICATE_CODE,
                    ExceptionMessageHandler.CERTIFICATE_NOT_FOUND_MESSAGE_NAME);
        }
        return giftCertificate;
    }

    @Override
    public List<GiftCertificate> findAll() {
        return giftCertificateDao.findAll();
    }

    @Override
    public GiftCertificate update(GiftCertificate certificate)
            throws ParameterNotPresentException, DataNotFoundException {
        if (certificate.getId() == null) {
            throw new ParameterNotPresentException(ExceptionMessageHandler.CERTIFICATE_CODE,
                    ExceptionMessageHandler.CERTIFICATE_ID_NOT_PRESENT_MESSAGE_NAME);
        }
        certificate.setLastUpdateDate(new Date());
        GiftCertificate updated = giftCertificateDao.update(certificate);
        if (updated != null) {
            updated = find(certificate.getId());
        }
        return updated;
    }

    @Override
    public boolean delete(Long id) throws ParameterNotPresentException {
        if (id == null) {
            throw new ParameterNotPresentException(ExceptionMessageHandler.CERTIFICATE_CODE,
                    ExceptionMessageHandler.CERTIFICATE_ID_NOT_PRESENT_MESSAGE_NAME);
        }
        return giftCertificateDao.delete(id);
    }

    @Override
    public GiftCertificate findByTagName(String tagName) {
        return giftCertificateDao.findByTagName(tagName);
    }

    @Override
    public List<GiftCertificate> findByNameAndDescription(String name, String description) {
        return giftCertificateDao.findByNameAndDescription(name, description);
    }

    @Override
    public List<GiftCertificate> findSorted(String sortingParameter, boolean descending) {
        List<GiftCertificate> sortedCertificates = giftCertificateDao.findSorted(sortingParameter);
        if (descending) {
            Collections.reverse(sortedCertificates);
        }
        return sortedCertificates;
    }

}
