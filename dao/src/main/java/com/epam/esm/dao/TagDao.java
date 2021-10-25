package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

public interface TagDao extends BaseDao<Tag> {

    boolean addTagToCertificate(Long certificateId, Long tagId);

    boolean addTagToCertificate(Tag tag, Long certificateId);

    boolean deleteTagFromCertificate(Long certificateId, Long tagId);

}
