insert into public.pending_exercises (id, id_usr, id_exercise, id_group_exercise)
values (DEFAULT,'1','1','1');
insert into public.pending_exercises (id, id_usr, id_exercise, id_group_exercise)
values (DEFAULT,'1','2','1');
insert into public.pending_exercises (id, id_usr, id_exercise, id_group_exercise)
values (DEFAULT,'1','3','2');
insert into public.pending_exercises (id, id_usr, id_exercise, id_group_exercise)
values (DEFAULT,'1','4','2');


insert into tm_exercise (id, word_exercise, id_group_exercise, url_image_related, result_expected, level)
values (default,'R','1','assets/rat.png','ratón','1');
insert into tm_exercise (id, word_exercise, id_group_exercise, url_image_related, result_expected, level)
values (default,'R','1','assets/rat.png','rata','1');
insert into tm_exercise (id, word_exercise, id_group_exercise, url_image_related, result_expected, level)
values (default,'L','2','assets/rat.png','león','1');
insert into tm_exercise (id, word_exercise, id_group_exercise, url_image_related, result_expected, level)
values (default,'L','2','assets/rat.png','leo','1');


insert into tm_exercise (id, word_exercise, id_group_exercise, url_image_related, result_expected, level)
values (DEFAULT, 'R',1,'assets/caramelo.png','caramelo','1');
insert into tm_exercise (id, word_exercise, id_group_exercise, url_image_related, result_expected, level)
values (DEFAULT, 'L',2,'assets/isla.png','isla','1');
insert into tm_exercise (id, word_exercise, id_group_exercise, url_image_related, result_expected, level)
values (DEFAULT, 'L',2,'assets/globo1.png','globo','1');
insert into tm_exercise (id, word_exercise, id_group_exercise, url_image_related, result_expected, level)
values (DEFAULT, 'L',2,'assets/flan.png','flan','1');
insert into tm_exercise (id, word_exercise, id_group_exercise, url_image_related, result_expected, level)
values (DEFAULT, 'L',2,'assets/lapiz.png','lapiz','1');


insert into public.pending_exercises (id, id_usr, id_exercise, id_group_exercise)
values (DEFAULT,'1','5','1');
insert into public.pending_exercises (id, id_usr, id_exercise, id_group_exercise)
values (DEFAULT,'1','6','2');
insert into public.pending_exercises (id, id_usr, id_exercise, id_group_exercise)
values (DEFAULT,'1','7','2');
insert into public.pending_exercises (id, id_usr, id_exercise, id_group_exercise)
values (DEFAULT,'1','8','2');

insert into tm_group_exercise (id, word_group_exercise)
values (default,'RR');
insert into tm_group_exercise (id, word_group_exercise)
values (default,'L');