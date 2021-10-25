package com.epam.esm.service;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.impl.GiftCertificateDaoImpl;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.service.exception.DataNotFoundException;
import com.epam.esm.service.exception.ParameterNotPresentException;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.Date;

public class GiftCertificateServiceTest {

    private GiftCertificateService giftCertificateService;

    private GiftCertificateDao giftCertificateDao;

    private static GiftCertificate giftCertificate;

    @BeforeAll
    public static void initializeGiftCertificate() {
        giftCertificate = new GiftCertificate();
        giftCertificate.setId(1L);
        giftCertificate.setName("test-certificate");
        giftCertificate.setLastUpdateDate(new Date());
        giftCertificate.setCreateDate(new Date());
        giftCertificate.setDescription("test");
        giftCertificate.setDuration(1);
        giftCertificate.setPrice(1);
    }

    @BeforeEach
    public void setUpMocks() {
        giftCertificateDao = Mockito.mock(GiftCertificateDaoImpl.class);
        giftCertificateService = new GiftCertificateServiceImpl(giftCertificateDao);
    }

    @Test
    public void testAddGiftCertificate() {
        Mockito.when(giftCertificateDao.add(giftCertificate)).thenReturn(true);
        Assertions.assertTrue(giftCertificateService.add(giftCertificate));
    }

    @Test
    public void testFindGiftCertificate() throws ParameterNotPresentException, DataNotFoundException {
        Mockito.when(giftCertificateDao.find(giftCertificate.getId())).thenReturn(giftCertificate);
        Assertions.assertEquals(giftCertificate, giftCertificateService.find(giftCertificate.getId()));
    }

    @Test
    public void testFindAllGiftCertificates() {
        Mockito.when(giftCertificateDao.findAll()).thenReturn(Collections.singletonList(giftCertificate));
        Assertions.assertNotNull(giftCertificateService.findAll());
    }

    @Test
    public void testFindByTagName() {
        String tagName = "test";
        Mockito.when(giftCertificateDao.findByTagName(tagName)).thenReturn(giftCertificate);
        Assertions.assertNotNull(giftCertificateService.findByTagName(tagName));
    }

    @Test
    public void testFindByNameAndDescription() {
        String name = "qwerty";
        String description = "qwerty";
        Mockito.when(giftCertificateDao.findByNameAndDescription(name, description)).thenReturn(
                Collections.singletonList(giftCertificate));
        Assertions.assertNotNull(giftCertificateService.findByNameAndDescription(name, description));
    }

    @Test
    public void testFindSorted() {
        String sortingParameter = "name";
        Mockito.when(giftCertificateDao.findSorted(sortingParameter)).thenReturn(
                Collections.singletonList(giftCertificate));
        Assertions.assertNotNull(giftCertificateService.findSorted(sortingParameter, false));
    }

}
