INSERT INTO todo_lists (id, name) VALUES (1, 'shopping');
INSERT INTO todo_lists (id, name) VALUES (2, 'groceries');

INSERT INTO todo_list_items (id, todo_list_id, description, is_completed) VALUES (1, 1, 'shirt', false);
INSERT INTO todo_list_items (id, todo_list_id, description, is_completed) VALUES (2, 1, 'watch', true);
INSERT INTO todo_list_items (id, todo_list_id, description, is_completed) VALUES (3, 1, 'shoes', false);

INSERT INTO todo_list_items (id, todo_list_id, description, is_completed) VALUES (4, 2, 'bread', false);
INSERT INTO todo_list_items (id, todo_list_id, description, is_completed) VALUES (5, 2, 'milk', false);
INSERT INTO todo_list_items (id, todo_list_id, description, is_completed) VALUES (6, 2, 'chicken', true);