-- 菜单 SQL
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2050125210574733312, '知识库文档解析文本表', '2000', '1', 'kbDocumentText', 'ai/kbDocumentText/index', 1, 0, 'C', '0', '0', 'ai:kbDocumentText:list', '#', 103, 1, sysdate(), null, null, '知识库文档解析文本表菜单');

-- 按钮 SQL
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2050125210574733313, '知识库文档解析文本表查询', 2050125210574733312, '1',  '#', '', 1, 0, 'F', '0', '0', 'ai:kbDocumentText:query',        '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2050125210574733314, '知识库文档解析文本表新增', 2050125210574733312, '2',  '#', '', 1, 0, 'F', '0', '0', 'ai:kbDocumentText:add',          '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2050125210574733315, '知识库文档解析文本表修改', 2050125210574733312, '3',  '#', '', 1, 0, 'F', '0', '0', 'ai:kbDocumentText:edit',         '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2050125210574733316, '知识库文档解析文本表删除', 2050125210574733312, '4',  '#', '', 1, 0, 'F', '0', '0', 'ai:kbDocumentText:remove',       '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2050125210574733317, '知识库文档解析文本表导出', 2050125210574733312, '5',  '#', '', 1, 0, 'F', '0', '0', 'ai:kbDocumentText:export',       '#', 103, 1, sysdate(), null, null, '');
