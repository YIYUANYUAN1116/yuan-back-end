-- 菜单 SQL
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2050125225267380224, '知识库文档切片表', '2000', '1', 'kbChunk', 'ai/kbChunk/index', 1, 0, 'C', '0', '0', 'ai:kbChunk:list', '#', 103, 1, sysdate(), null, null, '知识库文档切片表菜单');

-- 按钮 SQL
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2050125225267380225, '知识库文档切片表查询', 2050125225267380224, '1',  '#', '', 1, 0, 'F', '0', '0', 'ai:kbChunk:query',        '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2050125225267380226, '知识库文档切片表新增', 2050125225267380224, '2',  '#', '', 1, 0, 'F', '0', '0', 'ai:kbChunk:add',          '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2050125225267380227, '知识库文档切片表修改', 2050125225267380224, '3',  '#', '', 1, 0, 'F', '0', '0', 'ai:kbChunk:edit',         '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2050125225267380228, '知识库文档切片表删除', 2050125225267380224, '4',  '#', '', 1, 0, 'F', '0', '0', 'ai:kbChunk:remove',       '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2050125225267380229, '知识库文档切片表导出', 2050125225267380224, '5',  '#', '', 1, 0, 'F', '0', '0', 'ai:kbChunk:export',       '#', 103, 1, sysdate(), null, null, '');
