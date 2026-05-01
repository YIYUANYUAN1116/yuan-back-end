-- 菜单 SQL
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2050125257966174208, '知识库检索日志表', '2000', '1', 'kbRetrievalLog', 'ai/kbRetrievalLog/index', 1, 0, 'C', '0', '0', 'ai:kbRetrievalLog:list', '#', 103, 1, sysdate(), null, null, '知识库检索日志表菜单');

-- 按钮 SQL
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2050125257966174209, '知识库检索日志表查询', 2050125257966174208, '1',  '#', '', 1, 0, 'F', '0', '0', 'ai:kbRetrievalLog:query',        '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2050125257966174210, '知识库检索日志表新增', 2050125257966174208, '2',  '#', '', 1, 0, 'F', '0', '0', 'ai:kbRetrievalLog:add',          '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2050125257966174211, '知识库检索日志表修改', 2050125257966174208, '3',  '#', '', 1, 0, 'F', '0', '0', 'ai:kbRetrievalLog:edit',         '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2050125257966174212, '知识库检索日志表删除', 2050125257966174208, '4',  '#', '', 1, 0, 'F', '0', '0', 'ai:kbRetrievalLog:remove',       '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2050125257966174213, '知识库检索日志表导出', 2050125257966174208, '5',  '#', '', 1, 0, 'F', '0', '0', 'ai:kbRetrievalLog:export',       '#', 103, 1, sysdate(), null, null, '');
