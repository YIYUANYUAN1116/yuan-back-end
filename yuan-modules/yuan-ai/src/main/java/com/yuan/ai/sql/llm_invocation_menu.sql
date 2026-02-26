-- 菜单 SQL
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2027016906174619648, 'llm_invocation', '2000', '1', 'llmInvocation', 'ai/llmInvocation/index', 1, 0, 'C', '0', '0', 'ai:llmInvocation:list', '#', 103, 1, sysdate(), null, null, 'llm_invocation菜单');

-- 按钮 SQL
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2027016906174619649, 'llm_invocation查询', 2027016906174619648, '1',  '#', '', 1, 0, 'F', '0', '0', 'ai:llmInvocation:query',        '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2027016906174619650, 'llm_invocation新增', 2027016906174619648, '2',  '#', '', 1, 0, 'F', '0', '0', 'ai:llmInvocation:add',          '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2027016906174619651, 'llm_invocation修改', 2027016906174619648, '3',  '#', '', 1, 0, 'F', '0', '0', 'ai:llmInvocation:edit',         '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2027016906174619652, 'llm_invocation删除', 2027016906174619648, '4',  '#', '', 1, 0, 'F', '0', '0', 'ai:llmInvocation:remove',       '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2027016906174619653, 'llm_invocation导出', 2027016906174619648, '5',  '#', '', 1, 0, 'F', '0', '0', 'ai:llmInvocation:export',       '#', 103, 1, sysdate(), null, null, '');
