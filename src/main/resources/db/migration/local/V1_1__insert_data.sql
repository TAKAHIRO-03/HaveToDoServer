BEGIN;

-- COPY PG_TIMEZONE_NAMES AND INSERT TO TIMEZONES
COPY
    (
    SELECT *
    FROM pg_catalog.pg_timezone_names) TO '/tmp/output.csv'
    WITH csv delimiter ',';

COPY public.timezones FROM '/tmp/output.csv' WITH CSV;

-- INSERT currency

INSERT INTO public.currency(ISO_CODE, COUNTRY, SIGN, NAME)
VALUES ('JPY', '日本', '￥', '円'),
       ('USD', 'アメリカ合衆国', '＄', 'アメリカ合衆国ドル');

-- INSERT account
INSERT INTO public.account(id, username, password)
VALUES (1, 'hogehoge@example.com', '$2a$08$xYQepvGGP6A31WN0leRCT.4w.0BPcxDsak/aUQRbcFlixy34HcQJO');

-- INSERT roles
INSERT INTO public.roles(name)
VALUES ('ROLE_ADMIN'),
       ('ROLE_USER');

-- INSERT account_roles
INSERT INTO public.account_roles(username, roles)
VALUES ('hogehoge@example.com', 'ROLE_ADMIN'),
       ('hogehoge@example.com', 'ROLE_USER');

-- INSERT planned_task
INSERT INTO public.planned_task(account_id, title, description, start_time, end_time, cost,
                                is_repeat)
VALUES (1, 'タスク1', 'タスク1の説明です。タスク1の説明です。タスク1の説明です。タスク1の説明です。', '2019/03/01 10:00:00',
        '2019/03/01 12:00:00', 1000.0, false),
       (1, 'タスク2', 'タスク2の説明です。タスク2の説明です。タスク2の説明です。タスク2の説明です。', '2019/03/02 10:00:00',
        '2019/03/02 12:00:00', 1000.0, false);

COMMIT;