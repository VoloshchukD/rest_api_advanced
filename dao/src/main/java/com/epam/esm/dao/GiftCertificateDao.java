package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;

import java.util.Date;
import java.util.List;

public interface GiftCertificateDao extends BaseDao<GiftCertificate> {

    boolean addCertificateToUser(GiftCertificate certificate, Long userId);

    List<GiftCertificate> findByTagName(String tagName);

    List<GiftCertificate> findCertificatesByTags(Integer limit, Integer offset, String... tagNames);

    List<GiftCertificate> findByNameAndDescription(String name, String description, Integer limit, Integer offset);

    List<GiftCertificate> findSorted(String sortingParameter, Integer limit, Integer offset);

}
