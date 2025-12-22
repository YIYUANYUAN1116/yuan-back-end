-- 菜单 SQL
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2002998958938193920, 'post', '2000', '1', 'sysPost', 'system/sysPost/index', 1, 0, 'C', '0', '0', 'system:sysPost:list', '#', 103, 1, sysdate(), null, null, 'post菜单');

-- 按钮 SQL
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2002998958938193921, 'post查询', 2002998958938193920, '1',  '#', '', 1, 0, 'F', '0', '0', 'system:sysPost:query',        '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2002998958938193922, 'post新增', 2002998958938193920, '2',  '#', '', 1, 0, 'F', '0', '0', 'system:sysPost:add',          '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2002998958938193923, 'post修改', 2002998958938193920, '3',  '#', '', 1, 0, 'F', '0', '0', 'system:sysPost:edit',         '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2002998958938193924, 'post删除', 2002998958938193920, '4',  '#', '', 1, 0, 'F', '0', '0', 'system:sysPost:remove',       '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2002998958938193925, 'post导出', 2002998958938193920, '5',  '#', '', 1, 0, 'F', '0', '0', 'system:sysPost:export',       '#', 103, 1, sysdate(), null, null, '');
