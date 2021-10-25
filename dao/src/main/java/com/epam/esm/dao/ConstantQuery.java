package com.epam.esm.dao;

public final class ConstantQuery {

    public static final String ADD_GIFT_CERTIFICATE_QUERY = "INSERT INTO gift_certificates" +
            " (name, description, price, duration, create_date, last_update_date) VALUES (?, ?, ?, ?, ?, ?)";

    public static final String FIND_GIFT_CERTIFICATE_QUERY = "SELECT * FROM gift_certificates WHERE id = ?";

    public static final String FIND_ALL_GIFT_CERTIFICATES_QUERY = "SELECT * FROM gift_certificates";

    public static final String UPDATE_GIFT_CERTIFICATE_QUERY = "UPDATE gift_certificates " +
            "SET name = COALESCE(?, name), description = COALESCE(?, description), " +
            "price = COALESCE(?, price), duration = COALESCE(?, duration), " +
            "last_update_date = COALESCE(?, last_update_date) " +
            "WHERE id = ?";

    public static final String DELETE_GIFT_CERTIFICATE_QUERY = "DELETE FROM gift_certificates WHERE id = ?";

    public static final String ADD_TAG_QUERY = "INSERT INTO tags (name) VALUES (?)";

    public static final String FIND_TAG_QUERY = "SELECT * FROM tags WHERE id = ?";

    public static final String FIND_ALL_TAGS_QUERY = "SELECT * FROM tags";

    public static final String UPDATE_TAG_QUERY = "UPDATE tags SET name = COALESCE(?, name) WHERE id = ?";

    public static final String DELETE_TAG_QUERY = "DELETE FROM tags WHERE id = ?";

    public static final String ADD_TAG_TO_CERTIFICATE_QUERY = "INSERT INTO " +
            "certificate_tag_maps(gift_certificate_id, tag_id) VALUES (?, ?)";

    public static final String DELETE_TAG_FROM_CERTIFICATE_QUERY = "DELETE FROM certificate_tag_maps " +
            "WHERE gift_certificate_id = ? AND tag_id = ?";

    public static final String DELETE_TAG_FROM_CERTIFICATES_BY_TAG_ID_QUERY = "DELETE FROM certificate_tag_maps " +
            "WHERE tag_id = ?";

    public static final String DELETE_TAG_FROM_CERTIFICATES_BY_CERTIFICATE_ID_QUERY
            = "DELETE FROM certificate_tag_maps WHERE gift_certificate_id = ?";

    public static final String FIND_CERTIFICATE_BY_TAG_NAME_QUERY = "SELECT " +
            "gift_certificates.id, gift_certificates.name, gift_certificates.description, " +
            "gift_certificates.price, gift_certificates.duration, gift_certificates.create_date, " +
            "gift_certificates.last_update_date FROM gift_certificates " +
            "INNER JOIN certificate_tag_maps ON certificate_tag_maps.gift_certificate_id = gift_certificates.id " +
            "INNER JOIN tags ON certificate_tag_maps.tag_id = tags.id WHERE tags.name = COALESCE(?, tags.name)";

    public static final String FIND_CERTIFICATES_BY_PART_OF_NAME_AND_DESCRIPTION_QUERY = "SELECT DISTINCT " +
            "gift_certificates.id, gift_certificates.name, gift_certificates.description, " +
            "gift_certificates.price, gift_certificates.duration, gift_certificates.create_date, " +
            "gift_certificates.last_update_date " +
            "FROM gift_certificates INNER JOIN certificate_tag_maps " +
            "ON certificate_tag_maps.gift_certificate_id = gift_certificates.id " +
            "INNER JOIN tags ON certificate_tag_maps.tag_id = tags.id " +
            "WHERE gift_certificates.name LIKE COALESCE(?, gift_certificates.name) " +
            "AND gift_certificates.description LIKE COALESCE(?, gift_certificates.description)";

    public static final String FIND_SORTED_CERTIFICATES_QUERY = "WITH constants (sortParam) AS (values (?))\n" +
            "SELECT gift_certificates.id, gift_certificates.name, gift_certificates.description, " +
            "gift_certificates.price, gift_certificates.duration, gift_certificates.create_date, " +
            "gift_certificates.last_update_date FROM constants, gift_certificates " +
            "INNER JOIN certificate_tag_maps ON certificate_tag_maps.gift_certificate_id = gift_certificates.id " +
            "INNER JOIN tags ON certificate_tag_maps.tag_id = tags.id " +
            "ORDER BY CASE WHEN sortParam = 'name' THEN gift_certificates.name END, " +
            "CASE WHEN sortParam = 'create-date' THEN gift_certificates.create_date END ASC";

    public static final String PERCENT_VALUE = "%";

    public static final String TAG_ID_COLUMN_NAME = "id";

    private ConstantQuery() {
    }

}
