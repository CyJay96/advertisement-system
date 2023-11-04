INSERT INTO advertisers(title, description, location, picture_url)
VALUES
    ('Google', 'Google Advertising', 'Minsk', 'google_picture_url.com'),
    ('Yandex', 'Yandex Advertising', 'Gomel', 'yandex_picture_url.com'),
    ('BYNEX', 'BYNEX Advertising', 'Brest', 'bynex_picture_url.com');

INSERT INTO campaigns(title, description, location, picture_url, user_type, advertiser_id)
VALUES
    ('Google Campaign', 'Google Campaign description', 'Minsk', 'google_campaign_picture_url.com', 'FROM_3_TO_12_YEARS', 1),
    ('Yandex Campaign', 'Yandex Campaign description', 'Gomel', 'yandex_campaign_picture_url.com', 'FROM_12_TO_18_YEARS', 2),
    ('BYNEX Campaign', 'BYNEX Campaign description', 'Brest', 'bynex_campaign_picture_url.com', 'FROM_18_YEARS', 3);

INSERT INTO countries(name)
VALUES
    ('Belarus'),
    ('Netherlands'),
    ('Switzerland');

INSERT INTO languages(name)
VALUES
    ('English'),
    ('Russian'),
    ('German');

INSERT INTO campaign_countries(campaign_id, country_id)
VALUES
    (1, 1),
    (1, 2),
    (2, 2),
    (2, 3),
    (3, 1),
    (3, 3);

INSERT INTO campaign_languages(campaign_id, language_id)
VALUES
    (1, 1),
    (1, 2),
    (2, 2),
    (2, 3),
    (3, 1),
    (3, 3);
