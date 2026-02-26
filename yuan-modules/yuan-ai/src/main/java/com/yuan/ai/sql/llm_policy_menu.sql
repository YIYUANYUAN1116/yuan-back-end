-- 菜单 SQL
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2027016920884043776, 'llm_policy', '2000', '1', 'llmPolicy', 'ai/llmPolicy/index', 1, 0, 'C', '0', '0', 'ai:llmPolicy:list', '#', 103, 1, sysdate(), null, null, 'llm_policy菜单');

-- 按钮 SQL
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2027016920884043777, 'llm_policy查询', 2027016920884043776, '1',  '#', '', 1, 0, 'F', '0', '0', 'ai:llmPolicy:query',        '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2027016920884043778, 'llm_policy新增', 2027016920884043776, '2',  '#', '', 1, 0, 'F', '0', '0', 'ai:llmPolicy:add',          '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2027016920884043779, 'llm_policy修改', 2027016920884043776, '3',  '#', '', 1, 0, 'F', '0', '0', 'ai:llmPolicy:edit',         '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2027016920884043780, 'llm_policy删除', 2027016920884043776, '4',  '#', '', 1, 0, 'F', '0', '0', 'ai:llmPolicy:remove',       '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2027016920884043781, 'llm_policy导出', 2027016920884043776, '5',  '#', '', 1, 0, 'F', '0', '0', 'ai:llmPolicy:export',       '#', 103, 1, sysdate(), null, null, '');
