package com.epam.esm.dao;

import com.epam.esm.dao.configuration.TestDataSourceConfiguration;
import com.epam.esm.entity.GiftCertificate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Date;

@SpringJUnitConfig(TestDataSourceConfiguration.class)
public class GiftCertificateDaoTest {

    @Autowired
    private GiftCertificateDao giftCertificateDao;

    private static GiftCertificate giftCertificate;

    @BeforeAll
    public static void initializeGiftCertificate() {
        giftCertificate = new GiftCertificate();
        giftCertificate.setId(1L);
        giftCertificate.setName("qwerty");
        giftCertificate.setLastUpdateDate(new Date());
        giftCertificate.setCreateDate(new Date());
        giftCertificate.setDescription("test");
        giftCertificate.setDuration(1);
        giftCertificate.setPrice(1);
    }

    @Test
    public void testAddGiftCertificate() {
        Assertions.assertTrue(giftCertificateDao.add(giftCertificate));
    }

    @Test
    public void testFindCertificate() {
        Assertions.assertNotNull(giftCertificateDao.find(giftCertificate.getId()));
    }

    @Test
    public void testFindAllCertificates() {
        Assertions.assertNotNull(giftCertificateDao.findAll());
    }

    @Test
    public void testUpdateCertificate() {
        String updatedString = "updated";
        giftCertificate.setDescription(updatedString);
        Assertions.assertEquals(giftCertificate, giftCertificateDao.update(giftCertificate));
    }

    @Test
    public void testDeleteCertificate() {
        Assertions.assertTrue(giftCertificateDao.delete(2L));
    }

    @Test
    public void testFindByTagName() {
        Assertions.assertNotNull(giftCertificateDao.findByTagName("test"));
    }

    @Test
    public void testFindByNameAndDescription() {
        Assertions.assertNotNull(giftCertificateDao.findByNameAndDescription("qwer", "qwer"));
    }

    @Test
    public void testFindSorted() {
        Assertions.assertNotNull(giftCertificateDao.findSorted("name"));
    }

}
