/*
insert into public.tm_role (id_role, name)
values  (1,'ADMIN'),
        (2,'PROFESSIONAL'),
        (3,'PATIENT');

INSERT INTO public.tm_professional (id_role, code, username, email, password, first_name, last_name, age, gender, image_data, created_at, updated_at)
VALUES
    (2, '1BD6371E', 'PROFESSIONAL', 'PROFESSIONAL@PROFESSIONAL.com', '$2a$10$m.pEjgaHeQdQ/.Ka3V5hiOpmva4/8fTkIsNLu21sw0ZRoVcMZFmh.', 'alfonzo', 'stalin', 30, null, null, '2023-08-28 22:53:37.625573', '2023-08-28 22:53:37.625573'),
    (2, 'EF58F71E', 'camiPro', 'camiPro@speakapp.com', '$2a$10$EkOHuGaDtQoqouDIS.PBZOSrref6WvCHPiBS8blDyhN2EfO9HYHY.', 'camiPro', 'Paciente', 45, null, null, '2023-10-24 00:14:01.459927', '2023-10-24 00:14:01.459927'),
    (2, 'B0656457', 'luanaPro', 'luanaPro@speakapp.com', '$2a$10$X0.O9119dSb14DQ7PCKO1OPdIgm8EIqgleNZjZ2y1HSXae7wzAKJ6', 'LuanaPro', 'Profesional', 32, null, null, '2023-10-24 00:08:14.516085', '2023-10-24 00:08:14.516085'),
    (2, '4E11B9BA', 'germanPro', 'germanPro@speakapp.com', '$2a$10$/AEPlJgKScS9cXb2cXx7p.T4iyb.HARFi5IL6Ga1ZPGkGMHrON0nS', 'germanPro', 'Profesional', 27, null, null, '2023-10-24 00:14:27.186241', '2023-10-24 00:14:27.186241');

INSERT INTO public.tm_patient (id_professional, id_role, username, email, password, first_name, last_name, age, gender, image_data, created_at, updated_at)
VALUES
    (1, 3, 'PATIENT', 'PATIENT@PATIENT.com', '$2a$10$leDuhaHCdTWSgScKlgZqZ.4lmORlYxiUUwtBbvsjRlXotnZWExZdW', 'tomas', 'tomatoshi', 6, null, null, '2023-08-28 22:55:38.742894', '2023-08-28 22:55:38.742894'),
    (3, 3, 'luanaPac', 'luanaPac@speakapp.com', '$2a$10$tmjg1zZ.EwXY19zYMcUmp.bG3K/Uae.VdWtVgs/4WJ/1LXE02sJdK', 'LuanaPac', 'Paciente', 5, null, null, '2023-10-24 00:09:04.447008', '2023-10-24 00:09:04.447008'),
    (3, 3, 'guidoPac', 'guidoPac@speakapp.com', '$2a$10$27L1fuS78/F1uo94F.i3f.T1ITZWkqY9lTZpxKfSX2OtmqOLoz4EG', 'guidoPac', 'Paciente', 7, null, null, '2023-10-24 00:09:34.685110', '2023-10-24 00:09:34.685110'),
    (3, 3, 'apoloPac', 'apoloPac@speakapp.com', '$2a$10$tDpsPpunobOobUXq72y9K.EVTGk84d.ABzVj0Agnl/zAJKLyc4BZq', 'apoloPac', 'Paciente', 5, null, null, '2023-10-24 00:12:32.337325', '2023-10-24 00:12:32.337325'),
    (2, 3, 'camiPac', 'camiPac@speakapp.com', '$2a$10$fNKtgvaAUVWU5RhDEPJthen4b5qnkBYNifZMVs4FC0asSI7hchRhi', 'camiPac', 'Paciente', 5, null, null, '2023-10-24 00:12:04.619093', '2023-10-24 00:12:04.619093'),
    (3, 3, 'nicoPac', 'nicoPac@speakapp.com', '$2a$10$60lWGngN1E63JB9IMYmW1Osrb.30V97i/3G6/ojgq4KM5zZI2NeRG', 'nicoPac', 'Paciente', 6, null, null, '2023-10-24 00:11:28.541816', '2023-10-24 00:11:28.541816'),
    (2, 3, 'germanPac', 'germanPac@speakapp.com', '$2a$10$nRdbM6BvYwxODlJ3gvmtNOmw6ChXuhT.5TjKqrytcQPsGSTjWC79O', 'germanPac', 'Paciente', 6, null, null, '2023-10-24 00:11:00.181722', '2023-10-24 00:11:00.181722');

 */