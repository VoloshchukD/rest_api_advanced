package com.epam.esm.dao.impl;

import com.epam.esm.dao.ConstantQuery;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public boolean add(GiftCertificate giftCertificate) {
        return (entityManager.merge(giftCertificate) != null);
    }

    @Transactional
    @Override
    public boolean addCertificateToUser(GiftCertificate certificate, Long userId, Date purchaseTimestamp) {
        Order order = new Order();
        User user = new User();
        user.setId(userId);
        order.setUser(user);
        order.setCertificate(certificate);
        order.setPurchaseTimestamp(purchaseTimestamp);
        order.setTotalCost(certificate.getPrice());
        return (entityManager.merge(order) != null);
    }

    @Override
    public GiftCertificate find(Long id) {
        return entityManager.find(GiftCertificate.class, id);
    }

    @Override
    public List<GiftCertificate> findAll(Integer limit, Integer offset) {
        TypedQuery<GiftCertificate> query = entityManager.createQuery(ConstantQuery.FIND_ALL_GIFT_CERTIFICATES_QUERY,
                GiftCertificate.class);
        return query.setFirstResult(offset).setMaxResults(limit).getResultList();
    }

    @Transactional
    @Override
    public GiftCertificate update(GiftCertificate giftCertificate) {
        Query query = entityManager.createQuery(ConstantQuery.UPDATE_GIFT_CERTIFICATE_QUERY);
        int affectedRowNumber
                = query.setParameter(ConstantQuery.CERTIFICATE_NAME_PARAMETER_NAME, giftCertificate.getName())
                .setParameter(ConstantQuery.CERTIFICATE_DESCRIPTION_PARAMETER_NAME, giftCertificate.getDescription())
                .setParameter(ConstantQuery.CERTIFICATE_PRICE_PARAMETER_NAME, giftCertificate.getPrice())
                .setParameter(ConstantQuery.CERTIFICATE_DURATION_PARAMETER_NAME, giftCertificate.getDuration())
                .setParameter(ConstantQuery.CERTIFICATE_UPDATE_DATE_PARAMETER_NAME, giftCertificate.getLastUpdateDate())
                .setParameter(ConstantQuery.CERTIFICATE_ID_PARAMETER_NAME, giftCertificate.getId())
                .executeUpdate();
        return (affectedRowNumber == 1) ? giftCertificate : null;
    }

    @Transactional
    @Override
    public boolean delete(Long id) {
        entityManager.createQuery(ConstantQuery.DELETE_TAG_FROM_CERTIFICATES_BY_CERTIFICATE_ID_QUERY)
                .setParameter(ConstantQuery.CERTIFICATE_ID_PARAMETER_NAME, id)
                .executeUpdate();
        int affectedRowNumber = entityManager.createQuery(ConstantQuery.DELETE_GIFT_CERTIFICATE_QUERY)
                .setParameter(ConstantQuery.CERTIFICATE_ID_PARAMETER_NAME, id)
                .executeUpdate();
        return (affectedRowNumber == 1);
    }

    @Override
    public GiftCertificate findByTagName(String tagName) {
        TypedQuery<GiftCertificate> query = entityManager.createQuery(ConstantQuery.FIND_CERTIFICATE_BY_TAG_NAME_QUERY,
                GiftCertificate.class);
        return query.setParameter(ConstantQuery.TAG_NAME_PARAMETER_NAME, tagName)
                .setMaxResults(ConstantQuery.SINGLE_LIMIT_VALUE).getSingleResult();
    }

    @Override
    public List<GiftCertificate> findByNameAndDescription(String name, String description, Integer limit,
                                                          Integer offset) {
        TypedQuery<GiftCertificate> query = entityManager.createQuery(
                ConstantQuery.FIND_CERTIFICATES_BY_PART_OF_NAME_AND_DESCRIPTION_QUERY, GiftCertificate.class);
        return query.setParameter(ConstantQuery.CERTIFICATE_NAME_PARAMETER_NAME,
                ConstantQuery.PERCENT_VALUE + name + ConstantQuery.PERCENT_VALUE)
                .setParameter(ConstantQuery.CERTIFICATE_DESCRIPTION_PARAMETER_NAME,
                        ConstantQuery.PERCENT_VALUE + description + ConstantQuery.PERCENT_VALUE)
                .setFirstResult(offset).setMaxResults(limit).getResultList();
    }

    @Override
    public List<GiftCertificate> findSorted(String sortingParameter, Integer limit, Integer offset) {
        TypedQuery<GiftCertificate> query = entityManager.createQuery(
                ConstantQuery.FIND_SORTED_CERTIFICATES_QUERY, GiftCertificate.class);
        return query.setParameter(ConstantQuery.SORTING_PARAMETER_NAME, sortingParameter)
                .setFirstResult(offset).setMaxResults(limit).getResultList();
    }

    @Override
    public List<GiftCertificate> findCertificatesByTags(Integer limit, Integer offset, String... tagNames) {
        TypedQuery<GiftCertificate> query = entityManager.createQuery(
                ConstantQuery.FIND_CERTIFICATE_BY_TAGS_QUERY, GiftCertificate.class);
        return query.setParameter(ConstantQuery.CERTIFICATE_NAME_PARAMETER_NAME, tagNames[1])
                .setParameter(ConstantQuery.ANOTHER_TAG_NAME_PARAMETER_NAME, tagNames[0])
                .setFirstResult(offset).setMaxResults(limit).getResultList();
    }

}
