-- 菜单 SQL
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2050125276324642816, '知识库检索命中明细表', '2000', '1', 'kbRetrievalHit', 'ai/kbRetrievalHit/index', 1, 0, 'C', '0', '0', 'ai:kbRetrievalHit:list', '#', 103, 1, sysdate(), null, null, '知识库检索命中明细表菜单');

-- 按钮 SQL
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2050125276324642817, '知识库检索命中明细表查询', 2050125276324642816, '1',  '#', '', 1, 0, 'F', '0', '0', 'ai:kbRetrievalHit:query',        '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2050125276324642818, '知识库检索命中明细表新增', 2050125276324642816, '2',  '#', '', 1, 0, 'F', '0', '0', 'ai:kbRetrievalHit:add',          '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2050125276324642819, '知识库检索命中明细表修改', 2050125276324642816, '3',  '#', '', 1, 0, 'F', '0', '0', 'ai:kbRetrievalHit:edit',         '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2050125276324642820, '知识库检索命中明细表删除', 2050125276324642816, '4',  '#', '', 1, 0, 'F', '0', '0', 'ai:kbRetrievalHit:remove',       '#', 103, 1, sysdate(), null, null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark)
values(2050125276324642821, '知识库检索命中明细表导出', 2050125276324642816, '5',  '#', '', 1, 0, 'F', '0', '0', 'ai:kbRetrievalHit:export',       '#', 103, 1, sysdate(), null, null, '');
