insert into users(id, username, password)
values('53f212a01f30402e9dbb3ab5b5fddd58', 'doctor', '$2a$10$WAbBs/g.8g9AXfkiGWKveeldtI2Or8D259iK2ocImc7IEFwaZC/pe');

insert into roles(id, role_name)
values ('9df9e950d0094499a5449e233bcae0d5','DOCTOR');

insert into user_role(user_id, role_id)
values ('53f212a01f30402e9dbb3ab5b5fddd58', '9df9e950d0094499a5449e233bcae0d5');
