-- 菜单 SQL
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2027016967294017536, 'chat_message', '2000', '1', 'chatMessage', 'ai/chatMessage/index', 1, 0, 'C', '0', '0', 'ai:chatMessage:list', '#', 103, 1, sysdate(), null, null, 'chat_message菜单');

-- 按钮 SQL
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2027016967294017537, 'chat_message查询', 2027016967294017536, '1',  '#', '', 1, 0, 'F', '0', '0', 'ai:chatMessage:query',        '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2027016967294017538, 'chat_message新增', 2027016967294017536, '2',  '#', '', 1, 0, 'F', '0', '0', 'ai:chatMessage:add',          '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2027016967294017539, 'chat_message修改', 2027016967294017536, '3',  '#', '', 1, 0, 'F', '0', '0', 'ai:chatMessage:edit',         '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2027016967294017540, 'chat_message删除', 2027016967294017536, '4',  '#', '', 1, 0, 'F', '0', '0', 'ai:chatMessage:remove',       '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2027016967294017541, 'chat_message导出', 2027016967294017536, '5',  '#', '', 1, 0, 'F', '0', '0', 'ai:chatMessage:export',       '#', 103, 1, sysdate(), null, null, '');
