-- 菜单 SQL
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2027016936189059072, 'llm_route_rule', '2000', '1', 'llmRouteRule', 'ai/llmRouteRule/index', 1, 0, 'C', '0', '0', 'ai:llmRouteRule:list', '#', 103, 1, sysdate(), null, null, 'llm_route_rule菜单');

-- 按钮 SQL
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2027016936189059073, 'llm_route_rule查询', 2027016936189059072, '1',  '#', '', 1, 0, 'F', '0', '0', 'ai:llmRouteRule:query',        '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2027016936189059074, 'llm_route_rule新增', 2027016936189059072, '2',  '#', '', 1, 0, 'F', '0', '0', 'ai:llmRouteRule:add',          '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2027016936189059075, 'llm_route_rule修改', 2027016936189059072, '3',  '#', '', 1, 0, 'F', '0', '0', 'ai:llmRouteRule:edit',         '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2027016936189059076, 'llm_route_rule删除', 2027016936189059072, '4',  '#', '', 1, 0, 'F', '0', '0', 'ai:llmRouteRule:remove',       '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2027016936189059077, 'llm_route_rule导出', 2027016936189059072, '5',  '#', '', 1, 0, 'F', '0', '0', 'ai:llmRouteRule:export',       '#', 103, 1, sysdate(), null, null, '');
