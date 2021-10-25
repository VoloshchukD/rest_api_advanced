package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagService;
import com.epam.esm.service.exception.DataNotFoundException;
import com.epam.esm.service.exception.ParameterNotPresentException;
import com.epam.esm.service.util.ExceptionMessageHandler;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    private TagDao tagDao;

    public TagServiceImpl(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    public boolean add(Tag tag) {
        return tagDao.add(tag);
    }

    @Override
    public Tag find(Long id) throws ParameterNotPresentException, DataNotFoundException {
        if (id == null) {
            throw new ParameterNotPresentException(ExceptionMessageHandler.TAG_CODE,
                    ExceptionMessageHandler.TAG_ID_NOT_PRESENT_MESSAGE_NAME);
        }
        Tag tag = tagDao.find(id);
        if (tag == null) {
            throw new DataNotFoundException(ExceptionMessageHandler.TAG_CODE,
                    ExceptionMessageHandler.TAG_NOT_FOUND_MESSAGE_NAME);
        }
        return tag;
    }

    @Override
    public List<Tag> findAll() {
        return tagDao.findAll();
    }

    @Override
    public Tag update(Tag tag) throws ParameterNotPresentException, DataNotFoundException {
        if (tag.getId() == null) {
            throw new ParameterNotPresentException(ExceptionMessageHandler.TAG_CODE,
                    ExceptionMessageHandler.TAG_ID_NOT_PRESENT_MESSAGE_NAME);
        }
        Tag updated = tagDao.update(tag);
        if (updated != null) {
            updated = find(tag.getId());
        }
        return updated;
    }

    @Override
    public boolean delete(Long id) throws ParameterNotPresentException {
        if (id == null) {
            throw new ParameterNotPresentException(ExceptionMessageHandler.TAG_CODE,
                    ExceptionMessageHandler.TAG_ID_NOT_PRESENT_MESSAGE_NAME);
        }
        return tagDao.delete(id);
    }

    @Override
    public boolean addTagToCertificate(Long certificateId, Long tagId) throws ParameterNotPresentException {
        if (certificateId == null) {
            throw new ParameterNotPresentException(ExceptionMessageHandler.CERTIFICATE_CODE,
                    ExceptionMessageHandler.CERTIFICATE_ID_NOT_PRESENT_MESSAGE_NAME);
        }
        if (tagId == null) {
            throw new ParameterNotPresentException(ExceptionMessageHandler.TAG_CODE,
                    ExceptionMessageHandler.TAG_ID_NOT_PRESENT_MESSAGE_NAME);
        }
        return tagDao.addTagToCertificate(certificateId, tagId);
    }

    @Override
    public boolean addTagToCertificate(Tag tag, Long certificateId)
            throws ParameterNotPresentException {
        if (certificateId == null) {
            throw new ParameterNotPresentException(ExceptionMessageHandler.CERTIFICATE_CODE,
                    ExceptionMessageHandler.CERTIFICATE_ID_NOT_PRESENT_MESSAGE_NAME);
        }
        return tagDao.addTagToCertificate(tag, certificateId);
    }

    @Override
    public boolean deleteTagFromCertificate(Long certificateId, Long tagId) throws ParameterNotPresentException {
        if (certificateId == null) {
            throw new ParameterNotPresentException(ExceptionMessageHandler.CERTIFICATE_CODE,
                    ExceptionMessageHandler.CERTIFICATE_ID_NOT_PRESENT_MESSAGE_NAME);
        }
        if (tagId == null) {
            throw new ParameterNotPresentException(ExceptionMessageHandler.TAG_CODE,
                    ExceptionMessageHandler.TAG_ID_NOT_PRESENT_MESSAGE_NAME);
        }
        return tagDao.deleteTagFromCertificate(certificateId, tagId);
    }

}
