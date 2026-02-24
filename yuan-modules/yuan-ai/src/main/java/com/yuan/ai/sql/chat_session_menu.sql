-- 菜单 SQL
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2023291095898435584, 'chat-session', '2000', '1', 'chatSession', 'system/chatSession/index', 1, 0, 'C', '0', '0', 'system:chatSession:list', '#', 103, 1, sysdate(), null, null, 'chat-session菜单');

-- 按钮 SQL
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2023291095898435585, 'chat-session查询', 2023291095898435584, '1',  '#', '', 1, 0, 'F', '0', '0', 'system:chatSession:query',        '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2023291095898435586, 'chat-session新增', 2023291095898435584, '2',  '#', '', 1, 0, 'F', '0', '0', 'system:chatSession:add',          '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2023291095898435587, 'chat-session修改', 2023291095898435584, '3',  '#', '', 1, 0, 'F', '0', '0', 'system:chatSession:edit',         '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2023291095898435588, 'chat-session删除', 2023291095898435584, '4',  '#', '', 1, 0, 'F', '0', '0', 'system:chatSession:remove',       '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2023291095898435589, 'chat-session导出', 2023291095898435584, '5',  '#', '', 1, 0, 'F', '0', '0', 'system:chatSession:export',       '#', 103, 1, sysdate(), null, null, '');
