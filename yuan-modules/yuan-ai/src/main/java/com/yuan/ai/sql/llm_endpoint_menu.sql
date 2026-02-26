-- 菜单 SQL
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2027016890135601152, 'llm_endpoint', '2000', '1', 'llmEndpoint', 'ai/llmEndpoint/index', 1, 0, 'C', '0', '0', 'ai:llmEndpoint:list', '#', 103, 1, sysdate(), null, null, 'llm_endpoint菜单');

-- 按钮 SQL
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2027016890135601153, 'llm_endpoint查询', 2027016890135601152, '1',  '#', '', 1, 0, 'F', '0', '0', 'ai:llmEndpoint:query',        '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2027016890135601154, 'llm_endpoint新增', 2027016890135601152, '2',  '#', '', 1, 0, 'F', '0', '0', 'ai:llmEndpoint:add',          '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2027016890135601155, 'llm_endpoint修改', 2027016890135601152, '3',  '#', '', 1, 0, 'F', '0', '0', 'ai:llmEndpoint:edit',         '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2027016890135601156, 'llm_endpoint删除', 2027016890135601152, '4',  '#', '', 1, 0, 'F', '0', '0', 'ai:llmEndpoint:remove',       '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2027016890135601157, 'llm_endpoint导出', 2027016890135601152, '5',  '#', '', 1, 0, 'F', '0', '0', 'ai:llmEndpoint:export',       '#', 103, 1, sysdate(), null, null, '');
