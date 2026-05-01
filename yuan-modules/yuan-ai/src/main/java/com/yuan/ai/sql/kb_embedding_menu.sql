-- 菜单 SQL
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2050125238215196672, '知识库向量元数据表', '2000', '1', 'kbEmbedding', 'ai/kbEmbedding/index', 1, 0, 'C', '0', '0', 'ai:kbEmbedding:list', '#', 103, 1, sysdate(), null, null, '知识库向量元数据表菜单');

-- 按钮 SQL
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2050125238215196673, '知识库向量元数据表查询', 2050125238215196672, '1',  '#', '', 1, 0, 'F', '0', '0', 'ai:kbEmbedding:query',        '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2050125238215196674, '知识库向量元数据表新增', 2050125238215196672, '2',  '#', '', 1, 0, 'F', '0', '0', 'ai:kbEmbedding:add',          '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2050125238215196675, '知识库向量元数据表修改', 2050125238215196672, '3',  '#', '', 1, 0, 'F', '0', '0', 'ai:kbEmbedding:edit',         '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2050125238215196676, '知识库向量元数据表删除', 2050125238215196672, '4',  '#', '', 1, 0, 'F', '0', '0', 'ai:kbEmbedding:remove',       '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2050125238215196677, '知识库向量元数据表导出', 2050125238215196672, '5',  '#', '', 1, 0, 'F', '0', '0', 'ai:kbEmbedding:export',       '#', 103, 1, sysdate(), null, null, '');
