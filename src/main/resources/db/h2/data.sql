insert into camera values (1, '测试相机', 'rtsp://', 'box,garbage');

insert into device values (1, '测试设备', 0);
insert into device values (2, '下线设备', 1);

insert into device_camera values ('2e548651-939b-4951-a7a7-c2a7e562c0f7', 1, 1);
insert into device_camera values ('3e2b8770-64c0-476b-b9d1-4e1d65732997', 2, 1);

insert into person values (1, '测试人员', '18299098812', '[3.11212,11.11112,4.66554]');

insert into device_person values ('d5cbdbc4-2d2e-4d56-ac48-0e7f8284a4b4', 1, 1);
insert into device_person values ('b50142e3-6adc-4c5f-b108-c618896b71f7', 2, 1);

insert into event values ('095d8805-f7cd-4749-982f-e57227818663', 1, 1, 'box', 1, '2020-07-10 11:47:56', '周转箱未盖盖子');