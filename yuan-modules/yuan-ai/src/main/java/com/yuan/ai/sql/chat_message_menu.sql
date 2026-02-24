-- 菜单 SQL
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2023291014319222784, 'chat-message', '2000', '1', 'chatMessage', 'system/chatMessage/index', 1, 0, 'C', '0', '0', 'system:chatMessage:list', '#', 103, 1, sysdate(), null, null, 'chat-message菜单');

-- 按钮 SQL
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2023291014319222785, 'chat-message查询', 2023291014319222784, '1',  '#', '', 1, 0, 'F', '0', '0', 'system:chatMessage:query',        '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2023291014319222786, 'chat-message新增', 2023291014319222784, '2',  '#', '', 1, 0, 'F', '0', '0', 'system:chatMessage:add',          '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2023291014319222787, 'chat-message修改', 2023291014319222784, '3',  '#', '', 1, 0, 'F', '0', '0', 'system:chatMessage:edit',         '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2023291014319222788, 'chat-message删除', 2023291014319222784, '4',  '#', '', 1, 0, 'F', '0', '0', 'system:chatMessage:remove',       '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2023291014319222789, 'chat-message导出', 2023291014319222784, '5',  '#', '', 1, 0, 'F', '0', '0', 'system:chatMessage:export',       '#', 103, 1, sysdate(), null, null, '');
