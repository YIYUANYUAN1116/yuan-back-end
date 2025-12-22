-- 菜单 SQL
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2002998994849824768, 'post-user', '2000', '1', 'sysUserPost', 'system/sysUserPost/index', 1, 0, 'C', '0', '0', 'system:sysUserPost:list', '#', 103, 1, sysdate(), null, null, 'post-user菜单');

-- 按钮 SQL
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2002998994849824769, 'post-user查询', 2002998994849824768, '1',  '#', '', 1, 0, 'F', '0', '0', 'system:sysUserPost:query',        '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2002998994849824770, 'post-user新增', 2002998994849824768, '2',  '#', '', 1, 0, 'F', '0', '0', 'system:sysUserPost:add',          '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2002998994849824771, 'post-user修改', 2002998994849824768, '3',  '#', '', 1, 0, 'F', '0', '0', 'system:sysUserPost:edit',         '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2002998994849824772, 'post-user删除', 2002998994849824768, '4',  '#', '', 1, 0, 'F', '0', '0', 'system:sysUserPost:remove',       '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2002998994849824773, 'post-user导出', 2002998994849824768, '5',  '#', '', 1, 0, 'F', '0', '0', 'system:sysUserPost:export',       '#', 103, 1, sysdate(), null, null, '');
