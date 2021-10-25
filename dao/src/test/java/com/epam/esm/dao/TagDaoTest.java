package com.epam.esm.dao;

import com.epam.esm.dao.configuration.TestDataSourceConfiguration;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(TestDataSourceConfiguration.class)
public class TagDaoTest {

    @Autowired
    private TagDao tagDao;

    private static Tag tag;

    @BeforeAll
    public static void initializeTag() {
        tag = new Tag();
        tag.setId(1L);
        tag.setName("test-tag");
    }

    @Test
    public void testAddTag() {
        Assertions.assertTrue(tagDao.add(tag));
    }

    @Test
    public void testFindTag() {
        Assertions.assertNotNull(tagDao.find(tag.getId()));
    }

    @Test
    public void testFindAllTags() {
        Assertions.assertNotNull(tagDao.findAll());
    }

    @Test
    public void testUpdateTag() {
        String updatedString = "updated";
        tag.setName(updatedString);
        Assertions.assertEquals(tag, tagDao.update(tag));
    }

    @Test
    public void testDeleteTag() {
        Assertions.assertTrue(tagDao.delete(2L));
    }

    @Test
    public void testAddTagToCertificate() {
        Assertions.assertTrue(tagDao.addTagToCertificate(4L, 4L));
    }

    @BeforeTestMethod
    public void addTagToCertificate() {
        tagDao.addTagToCertificate(3L, 4L);
    }

    @Test
    public void testDeleteTagFromCertificate() {
        Assertions.assertTrue(tagDao.deleteTagFromCertificate(3L, 4L));
    }

}
