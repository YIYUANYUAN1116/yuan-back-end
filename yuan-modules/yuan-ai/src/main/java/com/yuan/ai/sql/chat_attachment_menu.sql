-- 菜单 SQL
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2027016982838108160, 'chat_attachment', '2000', '1', 'chatAttachment', 'ai/chatAttachment/index', 1, 0, 'C', '0', '0', 'ai:chatAttachment:list', '#', 103, 1, sysdate(), null, null, 'chat_attachment菜单');

-- 按钮 SQL
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2027016982838108161, 'chat_attachment查询', 2027016982838108160, '1',  '#', '', 1, 0, 'F', '0', '0', 'ai:chatAttachment:query',        '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2027016982838108162, 'chat_attachment新增', 2027016982838108160, '2',  '#', '', 1, 0, 'F', '0', '0', 'ai:chatAttachment:add',          '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2027016982838108163, 'chat_attachment修改', 2027016982838108160, '3',  '#', '', 1, 0, 'F', '0', '0', 'ai:chatAttachment:edit',         '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2027016982838108164, 'chat_attachment删除', 2027016982838108160, '4',  '#', '', 1, 0, 'F', '0', '0', 'ai:chatAttachment:remove',       '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2027016982838108165, 'chat_attachment导出', 2027016982838108160, '5',  '#', '', 1, 0, 'F', '0', '0', 'ai:chatAttachment:export',       '#', 103, 1, sysdate(), null, null, '');
