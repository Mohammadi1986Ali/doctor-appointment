insert into users(id, username, password)
values('53f212a01f30402e9dbb3ab5b5fddd58', 'patient-1', '$2a$10$WAbBs/g.8g9AXfkiGWKveeldtI2Or8D259iK2ocImc7IEFwaZC/pe');
insert into users(id, username, password)
values('fb69e8e929a94a89ab70ce3c35afceb8', 'patient-2', '$2a$10$WAbBs/g.8g9AXfkiGWKveeldtI2Or8D259iK2ocImc7IEFwaZC/pe');
insert into users(id, username, password)
values('7c8179f6bd0e40a5bc801cf11b95247e', 'patient-3', '$2a$10$WAbBs/g.8g9AXfkiGWKveeldtI2Or8D259iK2ocImc7IEFwaZC/pe');
insert into users(id, username, password)
values('4d195952c39d4d1aa4baeee48df9e284', 'patient-4', '$2a$10$WAbBs/g.8g9AXfkiGWKveeldtI2Or8D259iK2ocImc7IEFwaZC/pe');

insert into roles(id, role_name)
values ('9df9e950d0094499a5449e233bcae0d5','PATIENT');
insert into roles(id, role_name)
values ('f573725f6e7d4e57be63cfed50bf6d32', 'OTHER');

insert into user_role(user_id, role_id)
values ('53f212a01f30402e9dbb3ab5b5fddd58', '9df9e950d0094499a5449e233bcae0d5');
insert into user_role(user_id, role_id)
values ('fb69e8e929a94a89ab70ce3c35afceb8', '9df9e950d0094499a5449e233bcae0d5');
insert into user_role(user_id, role_id)
values ('7c8179f6bd0e40a5bc801cf11b95247e', 'f573725f6e7d4e57be63cfed50bf6d32');