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
    public boolean addCertificateToUser(Long certificateId, Long userId) throws ParameterNotPresentException {
        if (certificateId == null) {
            throw new ParameterNotPresentException(ExceptionMessageHandler.CERTIFICATE_CODE,
                    ExceptionMessageHandler.CERTIFICATE_ID_NOT_PRESENT_MESSAGE_NAME);
        }
        if (userId == null) {
            throw new ParameterNotPresentException(ExceptionMessageHandler.USER_CODE,
                    ExceptionMessageHandler.USER_ID_NOT_PRESENT_MESSAGE_NAME);
        }
        GiftCertificate certificate = giftCertificateDao.find(certificateId);
        Date purchaseTimestamp = new Date();
        return giftCertificateDao.addCertificateToUser(certificate, userId, purchaseTimestamp);
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
    public List<GiftCertificate> findAll(Integer limit, Integer offset) {
        return giftCertificateDao.findAll(limit, offset);
    }

    @Override
    public List<GiftCertificate> findCertificatesByTags(Integer limit, Integer offset, String... tagNames) {
        return giftCertificateDao.findCertificatesByTags(limit, offset, tagNames);
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
    public List<GiftCertificate> findByNameAndDescription(String name, String description, Integer limit,
                                                          Integer offset) {
        return giftCertificateDao.findByNameAndDescription(name, description, limit, offset);
    }

    @Override
    public List<GiftCertificate> findSorted(String sortingParameter, boolean descending, Integer limit,
                                            Integer offset) {
        List<GiftCertificate> sortedCertificates = giftCertificateDao.findSorted(sortingParameter, limit, offset);
        if (descending) {
            Collections.reverse(sortedCertificates);
        }
        return sortedCertificates;
    }

}
