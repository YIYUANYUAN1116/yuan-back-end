-- 菜单 SQL
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2027016950776848384, 'chat_conversation', '2000', '1', 'chatConversation', 'ai/chatConversation/index', 1, 0, 'C', '0', '0', 'ai:chatConversation:list', '#', 103, 1, sysdate(), null, null, 'chat_conversation菜单');

-- 按钮 SQL
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2027016950776848385, 'chat_conversation查询', 2027016950776848384, '1',  '#', '', 1, 0, 'F', '0', '0', 'ai:chatConversation:query',        '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2027016950776848386, 'chat_conversation新增', 2027016950776848384, '2',  '#', '', 1, 0, 'F', '0', '0', 'ai:chatConversation:add',          '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2027016950776848387, 'chat_conversation修改', 2027016950776848384, '3',  '#', '', 1, 0, 'F', '0', '0', 'ai:chatConversation:edit',         '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2027016950776848388, 'chat_conversation删除', 2027016950776848384, '4',  '#', '', 1, 0, 'F', '0', '0', 'ai:chatConversation:remove',       '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2027016950776848389, 'chat_conversation导出', 2027016950776848384, '5',  '#', '', 1, 0, 'F', '0', '0', 'ai:chatConversation:export',       '#', 103, 1, sysdate(), null, null, '');
