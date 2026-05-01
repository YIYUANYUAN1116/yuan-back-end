-- 菜单 SQL
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2050125157562925056, '知识库主表', '2000', '1', 'kbBase', 'ai/kbBase/index', 1, 0, 'C', '0', '0', 'ai:kbBase:list', '#', 103, 1, sysdate(), null, null, '知识库主表菜单');

-- 按钮 SQL
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2050125157562925057, '知识库主表查询', 2050125157562925056, '1',  '#', '', 1, 0, 'F', '0', '0', 'ai:kbBase:query',        '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2050125157562925058, '知识库主表新增', 2050125157562925056, '2',  '#', '', 1, 0, 'F', '0', '0', 'ai:kbBase:add',          '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2050125157562925059, '知识库主表修改', 2050125157562925056, '3',  '#', '', 1, 0, 'F', '0', '0', 'ai:kbBase:edit',         '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2050125157562925060, '知识库主表删除', 2050125157562925056, '4',  '#', '', 1, 0, 'F', '0', '0', 'ai:kbBase:remove',       '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2050125157562925061, '知识库主表导出', 2050125157562925056, '5',  '#', '', 1, 0, 'F', '0', '0', 'ai:kbBase:export',       '#', 103, 1, sysdate(), null, null, '');
