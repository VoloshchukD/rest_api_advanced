package com.epam.esm.service;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.exception.ParameterNotPresentException;

/**
 * Base interface with logics specific for {@link Tag}.
 *
 * @author Daniil Valashchuk
 */
public interface TagService extends BaseService<Tag> {

    /**
     * Method with logics for binding certificate and tag.
     *
     * @param certificateId - target certificate identifier
     * @param tagId         - target tag identifier
     * @return boolean result of binding
     * @throws {@link ParameterNotPresentException}
     */
    boolean addTagToCertificate(Long certificateId, Long tagId) throws ParameterNotPresentException;

    /**
     * Method with logics for saving {@link Tag} and binding it to certificate.
     *
     * @param tag           - new {@link Tag}
     * @param certificateId - certificate identifier to bind to
     * @return boolean result of operation
     * @throws {@link ParameterNotPresentException}
     */
    boolean addTagToCertificate(Tag tag, Long certificateId) throws ParameterNotPresentException;

    /**
     * Method with unbinding logics for {@link Tag} and {@link GiftCertificate} data.
     *
     * @param certificateId - target certificate identifier
     * @param tagId         - target tag identifier
     * @return boolean result of unbinding
     * @throws {@link ParameterNotPresentException}
     */
    boolean deleteTagFromCertificate(Long certificateId, Long tagId) throws ParameterNotPresentException;

}
