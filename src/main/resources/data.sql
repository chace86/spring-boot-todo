INSERT INTO todo_lists (id, username, title) VALUES (1, 'jsmith', 'shopping');
INSERT INTO todo_lists (id, username, title) VALUES (2, 'rhenderson','groceries');
INSERT INTO todo_lists (id, username, title) VALUES (3, 'jsmith', 'chores');

INSERT INTO todo_list_items (id, todo_list_id, description, is_completed) VALUES (1, 1, 'shirt', false);
INSERT INTO todo_list_items (id, todo_list_id, description, is_completed) VALUES (2, 1, 'watch', true);
INSERT INTO todo_list_items (id, todo_list_id, description, is_completed) VALUES (3, 1, 'shoes', false);

INSERT INTO todo_list_items (id, todo_list_id, description, is_completed) VALUES (4, 2, 'bread', false);
INSERT INTO todo_list_items (id, todo_list_id, description, is_completed) VALUES (5, 2, 'milk', false);
INSERT INTO todo_list_items (id, todo_list_id, description, is_completed) VALUES (6, 2, 'chicken', true);

INSERT INTO todo_list_items (id, todo_list_id, description, is_completed) VALUES (7, 3, 'sweeping', false);
INSERT INTO todo_list_items (id, todo_list_id, description, is_completed) VALUES (8, 3, 'dusting', false);
INSERT INTO todo_list_items (id, todo_list_id, description, is_completed) VALUES (9, 3, 'mowing', true);
INSERT INTO todo_list_items (id, todo_list_id, description, is_completed) VALUES (10, 3, 'raking', true);