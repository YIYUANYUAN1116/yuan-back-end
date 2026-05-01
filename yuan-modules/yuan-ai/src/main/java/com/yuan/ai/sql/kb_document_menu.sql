-- 菜单 SQL
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2050125189074731008, '知识库文档表', '2000', '1', 'kbDocument', 'ai/kbDocument/index', 1, 0, 'C', '0', '0', 'ai:kbDocument:list', '#', 103, 1, sysdate(), null, null, '知识库文档表菜单');

-- 按钮 SQL
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2050125189074731009, '知识库文档表查询', 2050125189074731008, '1',  '#', '', 1, 0, 'F', '0', '0', 'ai:kbDocument:query',        '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2050125189074731010, '知识库文档表新增', 2050125189074731008, '2',  '#', '', 1, 0, 'F', '0', '0', 'ai:kbDocument:add',          '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2050125189074731011, '知识库文档表修改', 2050125189074731008, '3',  '#', '', 1, 0, 'F', '0', '0', 'ai:kbDocument:edit',         '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2050125189074731012, '知识库文档表删除', 2050125189074731008, '4',  '#', '', 1, 0, 'F', '0', '0', 'ai:kbDocument:remove',       '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2050125189074731013, '知识库文档表导出', 2050125189074731008, '5',  '#', '', 1, 0, 'F', '0', '0', 'ai:kbDocument:export',       '#', 103, 1, sysdate(), null, null, '');
