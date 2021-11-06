package com.epam.esm.dao.impl;

import com.epam.esm.dao.ConstantQuery;
import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.CertificateTagMap;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class TagDaoImpl implements TagDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public boolean add(Tag tag) {
        return (entityManager.merge(tag) != null);
    }

    @Override
    public Tag find(Long id) {
        return entityManager.find(Tag.class, id);
    }

    @Override
    public List<Tag> findAll(Integer limit, Integer offset) {
        TypedQuery<Tag> query = entityManager.createQuery(ConstantQuery.FIND_ALL_TAGS_QUERY, Tag.class);
        return query.setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public Tag findPopularTag(Long userId) {
        TypedQuery<Tag> query = entityManager.createQuery(ConstantQuery.FIND_POPULAR_TAG_QUERY, Tag.class);
        return query.setParameter(ConstantQuery.USER_ID_PARAMETER_NAME, userId)
                .setMaxResults(ConstantQuery.SINGLE_LIMIT_VALUE).getSingleResult();
    }

    @Transactional
    @Override
    public Tag update(Tag tag) {
        return entityManager.merge(tag);
    }

    @Transactional
    @Override
    public boolean delete(Long id) {
        entityManager.createQuery(ConstantQuery.DELETE_TAG_FROM_CERTIFICATES_BY_TAG_ID_QUERY)
                .setParameter(ConstantQuery.TAG_ID_COLUMN_NAME, id).executeUpdate();
        int affectedRows = entityManager.createQuery(ConstantQuery.DELETE_TAG_QUERY)
                .setParameter(ConstantQuery.TAG_ID_COLUMN_NAME, id).executeUpdate();
        return (affectedRows == 1);
    }

    @Transactional
    @Override
    public boolean addTagToCertificate(Long certificateId, Long tagId) {
        CertificateTagMap certificateTagMap = new CertificateTagMap();
        GiftCertificate certificate = new GiftCertificate();
        certificate.setId(certificateId);
        certificateTagMap.setCertificate(certificate);
        Tag tag = new Tag();
        tag.setId(tagId);
        certificateTagMap.setTag(tag);
        return (entityManager.merge(certificateTagMap) != null);
    }

    @Transactional
    @Override
    public boolean addTagToCertificate(Tag tag, Long certificateId) {
        CertificateTagMap certificateTagMap = new CertificateTagMap();
        GiftCertificate certificate = new GiftCertificate();
        certificate.setId(certificateId);
        certificateTagMap.setCertificate(certificate);
        certificateTagMap.setTag(tag);
        return (entityManager.merge(certificateTagMap) != null);
    }

    @Transactional
    @Override
    public boolean deleteTagFromCertificate(Long certificateId, Long tagId) {
        int affectedRows = entityManager.createQuery(ConstantQuery.DELETE_TAG_FROM_CERTIFICATE_QUERY)
                .setParameter(ConstantQuery.CERTIFICATE_ID_PARAMETER_NAME, certificateId)
                .setParameter(ConstantQuery.TAG_ID_COLUMN_NAME, tagId).executeUpdate();
        return (affectedRows == 1);
    }

}
