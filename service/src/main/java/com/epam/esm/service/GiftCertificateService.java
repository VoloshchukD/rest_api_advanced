package com.epam.esm.service;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;

import java.util.List;

/**
 * Base interface with logics specific for {@link GiftCertificate}.
 *
 * @author Daniil Valashchuk
 */
public interface GiftCertificateService extends BaseService<GiftCertificate> {

    /**
     * Method for seeking {@link GiftCertificate} by part of {@link Tag} name.
     *
     * @param tagName - target certificate identifier
     * @return {@link GiftCertificate} containing required data
     */
    GiftCertificate findByTagName(String tagName);

    /**
     * Method for seeking {@link GiftCertificate}s by part of its name and description.
     *
     * @param name        - required name part
     * @param description - required description part
     * @return list of {@link GiftCertificate} matching search condition
     */
    List<GiftCertificate> findByNameAndDescription(String name, String description);

    /**
     * Method for receiving sorted {@link GiftCertificate}s.
     *
     * @param sortingParameter - parameter to order by
     * @param descending       - required ordering type
     * @return ordered list of {@link GiftCertificate}
     */
    List<GiftCertificate> findSorted(String sortingParameter, boolean descending);

}
