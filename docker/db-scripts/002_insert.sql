/*
insert into public.tm_role (id_role, name)
values  (1,'ADMIN'),
        (2,'PROFESSIONAL'),
        (3,'PATIENT');

INSERT INTO public.tm_professional (id_role, code, username, email, password, first_name, last_name, age, gender, image_data, created_at, updated_at)
VALUES (3, '1BD6371E', 'PROFESSIONAL', 'PROFESSIONAL@PROFESSIONAL.com', '$2a$10$m.pEjgaHeQdQ/.Ka3V5hiOpmva4/8fTkIsNLu21sw0ZRoVcMZFmh.', 'alfonzo', 'stalin', 30, null, null, '2023-08-28 22:53:37.625573', '2023-08-28 22:53:37.625573');

INSERT INTO public.tm_patient (id_professional, id_role, username, email, password, first_name, last_name, age, gender, image_data, created_at, updated_at)
VALUES (1, 2, 'PATIENT', 'PATIENT@PATIENT.com', '$2a$10$leDuhaHCdTWSgScKlgZqZ.4lmORlYxiUUwtBbvsjRlXotnZWExZdW', 'tomas', 'tomatoshi', 6, null, null, '2023-08-28 22:55:38.742894', '2023-08-28 22:55:38.742894');
*/