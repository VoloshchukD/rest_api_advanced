package com.epam.esm.dao.impl;

import com.epam.esm.dao.ConstantQuery;
import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class TagDaoImpl implements TagDao {

    private JdbcTemplate jdbcTemplate;

    public TagDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean add(Tag tag) {
        int affectedRows = jdbcTemplate.update(ConstantQuery.ADD_TAG_QUERY, tag.getName());
        return (affectedRows == 1);
    }

    @Override
    public Tag find(Long id) {
        return jdbcTemplate.query(ConstantQuery.FIND_TAG_QUERY, new BeanPropertyRowMapper<>(Tag.class),
                new Object[]{id})
                .stream()
                .findAny()
                .orElse(null);
    }

    @Override
    public List<Tag> findAll() {
        return jdbcTemplate.query(ConstantQuery.FIND_ALL_TAGS_QUERY,
                new BeanPropertyRowMapper<>(Tag.class));
    }

    @Transactional
    @Override
    public Tag update(Tag tag) {
        int affectedRows = jdbcTemplate.update(ConstantQuery.UPDATE_TAG_QUERY, tag.getName(), tag.getId());
        return (affectedRows == 1) ? tag : null;
    }

    @Transactional
    @Override
    public boolean delete(Long id) {
        jdbcTemplate.update(ConstantQuery.DELETE_TAG_FROM_CERTIFICATES_BY_TAG_ID_QUERY, id);
        int affectedRows = jdbcTemplate.update(ConstantQuery.DELETE_TAG_QUERY, id);
        return (affectedRows == 1);
    }

    @Override
    public boolean addTagToCertificate(Long certificateId, Long tagId) {
        int affectedRows = jdbcTemplate.update(ConstantQuery.ADD_TAG_TO_CERTIFICATE_QUERY, certificateId, tagId);
        return (affectedRows == 1);
    }

    @Transactional
    @Override
    public boolean addTagToCertificate(Tag tag, Long certificateId) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int affectedRows = jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(ConstantQuery.ADD_TAG_QUERY,
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, tag.getName());
            return preparedStatement;
        }, keyHolder);
        if (affectedRows == 1) {
            if (keyHolder.getKeys() != null) {
                return addTagToCertificate(certificateId,
                        ((Number) keyHolder.getKeys().get(ConstantQuery.TAG_ID_COLUMN_NAME)).longValue());
            }
        }
        return false;
    }

    @Override
    public boolean deleteTagFromCertificate(Long certificateId, Long tagId) {
        int affectedRows = jdbcTemplate.update(ConstantQuery.DELETE_TAG_FROM_CERTIFICATE_QUERY, certificateId, tagId);
        return (affectedRows == 1);
    }

}
