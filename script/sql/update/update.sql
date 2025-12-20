ALTER TABLE sys_user AUTO_INCREMENT = 10;

ALTER TABLE sys_role AUTO_INCREMENT = 10;

ALTER TABLE sys_menu AUTO_INCREMENT = 2000;

ALTER TABLE sys_dict_data AUTO_INCREMENT = 50;

ALTER TABLE sys_menu add column route_name varchar(50) after menu_name;