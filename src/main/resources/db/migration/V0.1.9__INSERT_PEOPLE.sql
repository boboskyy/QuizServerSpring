
INSERT INTO people.groups (name)
VALUES  ('1a'),
        ('1b'),
        ('1c'),
        ('2a');

INSERT INTO people.people (type, firstName, lastName, pesel)
VALUES  (2,  'Jerzy',    'Dudzic', '33311122211'),
        (1,  'Andrzej',  'Krasny', '22211122211'),
        (0,  'Marek',    'Sztywny','00001412223'),
        (0,  'Tomasz',   'Działo', '00011122212'),
        (0,  'Bear',     'Grylls', '00011122211'),
        (0,  'Kurt',     'Cobain', '00011122210');

INSERT INTO people.groups_people (groupId, personId)
VALUES  (1, 3),
        (2, 4),
        (3, 5),
        (4, 6);

INSERT INTO login.logins (personId, roleId, login, password)
VALUES  (1,  3, 'root','$2a$10$gpnrnkyMzbcbVgOE5g4bd.CjfSs8duF9cEINVKGjUpmQ1n7y20a6a'),
        (2,  2, 'teacher','$2a$10$gpnrnkyMzbcbVgOE5g4bd.CjfSs8duF9cEINVKGjUpmQ1n7y20a6a'),
        (3,  1, 'patryk.s','$2a$10$gpnrnkyMzbcbVgOE5g4bd.CjfSs8duF9cEINVKGjUpmQ1n7y20a6a'),
        (4,  1, 'tomasz.d','$2a$10$gpnrnkyMzbcbVgOE5g4bd.CjfSs8duF9cEINVKGjUpmQ1n7y20a6a'),
        (5,  1, 'bear.b','$2a$10$gpnrnkyMzbcbVgOE5g4bd.CjfSs8duF9cEINVKGjUpmQ1n7y20a6a'),
        (6,  1, 'kurt.c','$2a$10$gpnrnkyMzbcbVgOE5g4bd.CjfSs8duF9cEINVKGjUpmQ1n7y20a6a');
