-- 菜单 SQL
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2027016872066539520, 'llm_model', '2000', '1', 'llmModel', 'ai/llmModel/index', 1, 0, 'C', '0', '0', 'ai:llmModel:list', '#', 103, 1, sysdate(), null, null, 'llm_model菜单');

-- 按钮 SQL
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2027016872066539521, 'llm_model查询', 2027016872066539520, '1',  '#', '', 1, 0, 'F', '0', '0', 'ai:llmModel:query',        '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2027016872066539522, 'llm_model新增', 2027016872066539520, '2',  '#', '', 1, 0, 'F', '0', '0', 'ai:llmModel:add',          '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2027016872066539523, 'llm_model修改', 2027016872066539520, '3',  '#', '', 1, 0, 'F', '0', '0', 'ai:llmModel:edit',         '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2027016872066539524, 'llm_model删除', 2027016872066539520, '4',  '#', '', 1, 0, 'F', '0', '0', 'ai:llmModel:remove',       '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2027016872066539525, 'llm_model导出', 2027016872066539520, '5',  '#', '', 1, 0, 'F', '0', '0', 'ai:llmModel:export',       '#', 103, 1, sysdate(), null, null, '');
