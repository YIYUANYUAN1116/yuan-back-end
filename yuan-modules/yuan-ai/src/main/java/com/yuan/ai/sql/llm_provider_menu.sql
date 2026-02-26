-- 菜单 SQL
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2027016854299467776, 'llm_provider', '2000', '1', 'llmProvider', 'ai/llmProvider/index', 1, 0, 'C', '0', '0', 'ai:llmProvider:list', '#', 103, 1, sysdate(), null, null, 'llm_provider菜单');

-- 按钮 SQL
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2027016854299467777, 'llm_provider查询', 2027016854299467776, '1',  '#', '', 1, 0, 'F', '0', '0', 'ai:llmProvider:query',        '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2027016854299467778, 'llm_provider新增', 2027016854299467776, '2',  '#', '', 1, 0, 'F', '0', '0', 'ai:llmProvider:add',          '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2027016854299467779, 'llm_provider修改', 2027016854299467776, '3',  '#', '', 1, 0, 'F', '0', '0', 'ai:llmProvider:edit',         '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2027016854299467780, 'llm_provider删除', 2027016854299467776, '4',  '#', '', 1, 0, 'F', '0', '0', 'ai:llmProvider:remove',       '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2027016854299467781, 'llm_provider导出', 2027016854299467776, '5',  '#', '', 1, 0, 'F', '0', '0', 'ai:llmProvider:export',       '#', 103, 1, sysdate(), null, null, '');
