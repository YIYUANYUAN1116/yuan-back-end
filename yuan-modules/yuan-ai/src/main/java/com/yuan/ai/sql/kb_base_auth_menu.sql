-- 菜单 SQL
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2050125176084971520, '知识库授权表', '2000', '1', 'kbBaseAuth', 'ai/kbBaseAuth/index', 1, 0, 'C', '0', '0', 'ai:kbBaseAuth:list', '#', 103, 1, sysdate(), null, null, '知识库授权表菜单');

-- 按钮 SQL
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2050125176084971521, '知识库授权表查询', 2050125176084971520, '1',  '#', '', 1, 0, 'F', '0', '0', 'ai:kbBaseAuth:query',        '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2050125176084971522, '知识库授权表新增', 2050125176084971520, '2',  '#', '', 1, 0, 'F', '0', '0', 'ai:kbBaseAuth:add',          '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2050125176084971523, '知识库授权表修改', 2050125176084971520, '3',  '#', '', 1, 0, 'F', '0', '0', 'ai:kbBaseAuth:edit',         '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2050125176084971524, '知识库授权表删除', 2050125176084971520, '4',  '#', '', 1, 0, 'F', '0', '0', 'ai:kbBaseAuth:remove',       '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2050125176084971525, '知识库授权表导出', 2050125176084971520, '5',  '#', '', 1, 0, 'F', '0', '0', 'ai:kbBaseAuth:export',       '#', 103, 1, sysdate(), null, null, '');
