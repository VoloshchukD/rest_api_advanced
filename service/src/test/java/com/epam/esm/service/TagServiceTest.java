package com.epam.esm.service;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.exception.DataNotFoundException;
import com.epam.esm.service.exception.ParameterNotPresentException;
import com.epam.esm.service.impl.TagServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;

public class TagServiceTest {

    private TagService tagService;

    private TagDao tagDao;

    private static Tag tag;

    @BeforeAll
    public static void initializeTag() {
        tag = new Tag();
        tag.setId(1L);
        tag.setName("test-certificate");
    }

    @BeforeEach
    public void setUpMocks() {
        tagDao = Mockito.mock(TagDaoImpl.class);
        tagService = new TagServiceImpl(tagDao);
    }

    @Test
    public void testAddTag() {
        Mockito.when(tagDao.add(tag)).thenReturn(true);
        Assertions.assertTrue(tagService.add(tag));
    }

    @Test
    public void testFindTag() throws ParameterNotPresentException, DataNotFoundException {
        Mockito.when(tagDao.find(tag.getId())).thenReturn(tag);
        Assertions.assertEquals(tag, tagService.find(tag.getId()));
    }

    @Test
    public void testFindAllTags() {
        Mockito.when(tagDao.findAll()).thenReturn(Collections.singletonList(tag));
        Assertions.assertNotNull(tagService.findAll());
    }

    @Test
    public void testAddTagToCertificate() throws ParameterNotPresentException {
        Mockito.when(tagDao.addTagToCertificate(1L, 1L)).thenReturn(true);
        Assertions.assertTrue(tagService.addTagToCertificate(1L, 1L));
    }

    @Test
    public void testDeleteTagFromCertificate() throws ParameterNotPresentException {
        Mockito.when(tagDao.deleteTagFromCertificate(1L, 1L)).thenReturn(true);
        Assertions.assertTrue(tagService.deleteTagFromCertificate(1L, 1L));
    }

}
