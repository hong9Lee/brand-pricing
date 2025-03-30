CREATE TABLE bp_brand
(
    seq           BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '시퀀스',
    brand_id      VARCHAR(64)  NOT NULL COMMENT '브랜드 ID',
    brand_name    VARCHAR(255) NOT NULL COMMENT '브랜드 이름',
    brand_status  VARCHAR(32)  NOT NULL COMMENT '브랜드 상태',
    reg_date_time DATETIME(6) NULL COMMENT '등록일자',
    mod_date_time DATETIME(6) NULL COMMENT '수정일자'
);
CREATE INDEX idx_bp_brand_id ON bp_brand (brand_id);

CREATE TABLE bp_category
(
    seq           BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '시퀀스',
    category_id   VARCHAR(64)  NOT NULL COMMENT '카테고리 ID',
    category_name VARCHAR(255) NOT NULL COMMENT '카테고리 이름',
    reg_date_time DATETIME(6) NULL COMMENT '등록일자',
    mod_date_time DATETIME(6) NULL COMMENT '수정일자'
);
CREATE INDEX idx_bp_category_id_name ON bp_category (category_id, category_name);

CREATE TABLE bp_product
(
    seq            BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '시퀀스',
    product_id     VARCHAR(64)    NOT NULL COMMENT '상품 ID',
    product_status VARCHAR(32)    NOT NULL COMMENT '상품 상태',
    product_name   VARCHAR(255)   NOT NULL COMMENT '상품 이름',
    price          DECIMAL(19, 2) NOT NULL COMMENT '가격',
    reg_date_time  DATETIME(6) NULL COMMENT '등록일자',
    mod_date_time  DATETIME(6) NULL COMMENT '수정일자'
);
CREATE INDEX idx_bp_product_id_status ON bp_product (product_id, product_status);

CREATE TABLE bp_product_mapping
(
    seq           BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '시퀀스',
    brand_id      VARCHAR(64) NOT NULL COMMENT '브랜드 ID',
    category_id   VARCHAR(64) NOT NULL COMMENT '카테고리 ID',
    product_id    VARCHAR(64) NOT NULL COMMENT '상품 ID',
    is_active     BOOLEAN     NOT NULL DEFAULT TRUE COMMENT '활성 여부',
    reg_date_time DATETIME(6) NULL COMMENT '등록일자',
    mod_date_time DATETIME(6) NULL COMMENT '수정일자'
);
CREATE UNIQUE INDEX uq_bp_product_mapping_brand_category_product ON bp_product_mapping (brand_id, category_id, product_id);
CREATE INDEX idx_bp_product_mapping_brand_category_active ON bp_product_mapping (brand_id, category_id, is_active);

CREATE TABLE bp_brand_category_price_summary
(
    seq                   BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '시퀀스',
    brand_id              VARCHAR(64)    NOT NULL COMMENT '브랜드 ID',
    category_id           VARCHAR(64)    NOT NULL COMMENT '카테고리 ID',
    min_price             DECIMAL(19, 2) NOT NULL COMMENT '최소 가격',
    min_price_product_ids TEXT           NOT NULL COMMENT '최소 가격 상품 ID',
    max_price             DECIMAL(19, 2) NOT NULL COMMENT '최대 가격',
    max_price_product_ids TEXT           NOT NULL COMMENT '최대 가격 상품 ID',
    reg_date_time         DATETIME(6) NULL COMMENT '등록일자',
    mod_date_time         DATETIME(6) NULL COMMENT '수정일자'
);
CREATE UNIQUE INDEX uq_bp_brand_category_price_summary_brand_category ON bp_brand_category_price_summary (category_id, brand_id);
CREATE INDEX idx_bp_brand_category_price_summary_min_price ON bp_brand_category_price_summary (category_id, brand_id, min_price);
CREATE INDEX idx_bp_brand_category_price_summary_max_price ON bp_brand_category_price_summary (category_id, brand_id, max_price);

CREATE TABLE bp_summary_update_fail_log
(
    seq           BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '시퀀스',
    brand_id      VARCHAR(64) NOT NULL COMMENT '브랜드 ID',
    category_id   VARCHAR(64) NOT NULL COMMENT '카테고리 ID',
    product_id    VARCHAR(64) NOT NULL COMMENT '상품 ID',
    error_message TEXT NULL COMMENT '에러 메시지',
    is_active     BOOLEAN     NOT NULL DEFAULT TRUE COMMENT '활성 여부',
    reg_date_time DATETIME(6) NULL COMMENT '등록일자',
    mod_date_time DATETIME(6) NULL COMMENT '수정일자'
);
CREATE INDEX idx_bp_summary_update_fail_log_brand_category_active ON bp_summary_update_fail_log (brand_id, category_id, is_active);

