delete from user_role;
delete from usr;

insert into usr(id, active, password, username) values (1, true, '$2a$08$RqoUS7w8xdCFdnTdHjTx3OBN5Uk9dOfEtiJES3Oc1gvmQJpaT2Y8a', 'admin'),
                                                       (2, true, '$2a$08$RqoUS7w8xdCFdnTdHjTx3OBN5Uk9dOfEtiJES3Oc1gvmQJpaT2Y8a', 'mike');
inser into user_role(user_id, roles) values
    (1, 'USER'), (1, 'ADMIN'),
    (2, 'USER');
