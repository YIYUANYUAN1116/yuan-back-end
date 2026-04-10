-- 菜单 SQL
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2027016996146634752, 'chat_message_chunk', '2000', '1', 'chatMessageChunk', 'ai/chatMessageChunk/index', 1, 0, 'C', '0', '0', 'ai:chatMessageChunk:list', '#', 103, 1, sysdate(), null, null, 'chat_message_chunk菜单');

-- 按钮 SQL
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2027016996146634753, 'chat_message_chunk查询', 2027016996146634752, '1',  '#', '', 1, 0, 'F', '0', '0', 'ai:chatMessageChunk:query',        '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2027016996146634754, 'chat_message_chunk新增', 2027016996146634752, '2',  '#', '', 1, 0, 'F', '0', '0', 'ai:chatMessageChunk:add',          '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2027016996146634755, 'chat_message_chunk修改', 2027016996146634752, '3',  '#', '', 1, 0, 'F', '0', '0', 'ai:chatMessageChunk:edit',         '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2027016996146634756, 'chat_message_chunk删除', 2027016996146634752, '4',  '#', '', 1, 0, 'F', '0', '0', 'ai:chatMessageChunk:remove',       '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2027016996146634757, 'chat_message_chunk导出', 2027016996146634752, '5',  '#', '', 1, 0, 'F', '0', '0', 'ai:chatMessageChunk:export',       '#', 103, 1, sysdate(), null, null, '');
