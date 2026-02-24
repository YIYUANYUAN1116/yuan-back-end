-- 菜单 SQL
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2023291059328299008, 'chat_model', '2000', '1', 'chatModel', 'system/chatModel/index', 1, 0, 'C', '0', '0', 'system:chatModel:list', '#', 103, 1, sysdate(), null, null, 'chat_model菜单');

-- 按钮 SQL
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2023291059328299009, 'chat_model查询', 2023291059328299008, '1',  '#', '', 1, 0, 'F', '0', '0', 'system:chatModel:query',        '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2023291059328299010, 'chat_model新增', 2023291059328299008, '2',  '#', '', 1, 0, 'F', '0', '0', 'system:chatModel:add',          '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2023291059328299011, 'chat_model修改', 2023291059328299008, '3',  '#', '', 1, 0, 'F', '0', '0', 'system:chatModel:edit',         '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2023291059328299012, 'chat_model删除', 2023291059328299008, '4',  '#', '', 1, 0, 'F', '0', '0', 'system:chatModel:remove',       '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2023291059328299013, 'chat_model导出', 2023291059328299008, '5',  '#', '', 1, 0, 'F', '0', '0', 'system:chatModel:export',       '#', 103, 1, sysdate(), null, null, '');
