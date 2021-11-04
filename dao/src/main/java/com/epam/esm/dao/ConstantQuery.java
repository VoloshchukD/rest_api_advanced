package com.epam.esm.dao;

public final class ConstantQuery {

    public static final String ADD_GIFT_CERTIFICATE_QUERY = "INSERT INTO gift_certificates" +
            " (name, description, price, duration, create_date, last_update_date) VALUES (?, ?, ?, ?, ?, ?)";

    public static final String FIND_GIFT_CERTIFICATE_QUERY = "SELECT * FROM gift_certificates WHERE id = ?";

    public static final String FIND_ALL_GIFT_CERTIFICATES_QUERY = "SELECT c FROM GiftCertificate c";

    public static final String UPDATE_GIFT_CERTIFICATE_QUERY = "UPDATE GiftCertificate " +
            "SET name = COALESCE(:name, name), description = COALESCE(:description, description), " +
            "price = COALESCE(:price, price), duration = COALESCE(:duration, duration), " +
            "lastUpdateDate = COALESCE(:last_update_date, lastUpdateDate) " +
            "WHERE id = :certificate_id";

    public static final String DELETE_GIFT_CERTIFICATE_QUERY = "DELETE FROM GiftCertificate WHERE id = :certificate_id";

    public static final String ADD_TAG_QUERY = "INSERT INTO tags (name) VALUES (?)";

    public static final String FIND_TAG_QUERY = "SELECT * FROM tags WHERE id = ?";

    public static final String FIND_ALL_TAGS_QUERY = "SELECT t FROM Tag t";

    public static final String UPDATE_TAG_QUERY = "UPDATE Tag SET name = COALESCE(:name, name) WHERE id = :tag_id";

    public static final String DELETE_TAG_QUERY = "DELETE FROM Tag WHERE id = :tag_id";

    public static final String ADD_TAG_TO_CERTIFICATE_QUERY = "INSERT INTO " +
            "rest_api.certificate_tag_maps(gift_certificate_id, tag_id) VALUES (:certificate_id, :tag_id)";

    public static final String DELETE_TAG_FROM_CERTIFICATE_QUERY = "DELETE FROM CertificateTagMap " +
            "WHERE certificate.id = :certificate_id AND tag.id = :tag_id";

    public static final String DELETE_TAG_FROM_CERTIFICATES_BY_TAG_ID_QUERY = "DELETE FROM CertificateTagMap " +
            "WHERE tag.id = :tag_id";

    public static final String DELETE_TAG_FROM_CERTIFICATES_BY_CERTIFICATE_ID_QUERY
            = "DELETE FROM CertificateTagMap c WHERE c.certificate.id = :certificate_id";

    public static final String FIND_CERTIFICATE_BY_TAG_NAME_QUERY = "SELECT " +
            "g FROM GiftCertificate g JOIN g.tags t " +
            "WHERE t.name = COALESCE(:name, t.name)";

    public static final String FIND_CERTIFICATES_BY_PART_OF_NAME_AND_DESCRIPTION_QUERY = "SELECT DISTINCT g " +
            "FROM GiftCertificate g JOIN g.tags t " +
            "WHERE g.name LIKE COALESCE(:name, g.name) " +
            "AND g.description LIKE COALESCE(:description, g.description)";

    public static final String FIND_SORTED_CERTIFICATES_QUERY = "SELECT g FROM GiftCertificate g JOIN g.tags t " +
            "ORDER BY CASE WHEN :sorting_parameter = 'name' THEN g.name END, " +
            "CASE WHEN :sorting_parameter = 'create-date' THEN g.createDate END";

    public static final String FIND_CERTIFICATE_BY_TAGS_QUERY = "SELECT DISTINCT g FROM GiftCertificate g " +
            "JOIN g.tags t WHERE t.name = :name AND g.id IN (SELECT DISTINCT g.id " +
            "FROM GiftCertificate g JOIN g.tags t WHERE t.name = :another_name)";

    public static final String FIND_POPULAR_TAG_QUERY = "SELECT t FROM Tag t JOIN t.certificates c JOIN c.orders o " +
            "WHERE o.user.id = :user_id GROUP BY t.id ORDER BY SUM(o.totalCost) DESC, COUNT(t) DESC";

    public static final String FIND_USER_QUERY = "SELECT * FROM users WHERE id = ?";

    public static final String FIND_ALL_USERS_QUERY = "SELECT u FROM User u";

    public static final String ADD_CERTIFICATE_TO_USER_QUERY = "INSERT INTO " +
            "orders (certificate_id, purchase_timestamp, total_cost, user_id) VALUES (?, ?, ?, ?)";

    public static final String FIND_USER_ORDER_QUERY = "SELECT o " +
            "FROM Order o WHERE o.id = :order_id AND o.user.id = :user_id";

    public static final String FIND_ALL_USER_ORDERS_QUERY = "SELECT o " +
            "FROM Order o WHERE o.user.id = :user_id";

    public static final String PERCENT_VALUE = "%";

    public static final Integer SINGLE_LIMIT_VALUE = 1;

    public static final String ORDER_ID_PARAMETER_NAME = "order_id";

    public static final String USER_ID_PARAMETER_NAME = "user_id";

    public static final String TAG_ID_COLUMN_NAME = "tag_id";

    public static final String CERTIFICATE_ID_PARAMETER_NAME = "certificate_id";

    public static final String CERTIFICATE_NAME_PARAMETER_NAME = "name";

    public static final String CERTIFICATE_DESCRIPTION_PARAMETER_NAME = "description";

    public static final String CERTIFICATE_PRICE_PARAMETER_NAME = "price";

    public static final String CERTIFICATE_DURATION_PARAMETER_NAME = "duration";

    public static final String CERTIFICATE_UPDATE_DATE_PARAMETER_NAME = "last_update_date";

    public static final String TAG_NAME_PARAMETER_NAME = "name";

    public static final String SORTING_PARAMETER_NAME = "sorting_parameter";

    public static final String ANOTHER_TAG_NAME_PARAMETER_NAME = "another_name";

    private ConstantQuery() {
    }

}
