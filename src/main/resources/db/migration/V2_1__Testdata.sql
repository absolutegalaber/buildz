-- a build count
INSERT INTO build_count (id, branch, counter, project) VALUES (1, 'master', 1, 'buildz-project');

-- some builds
INSERT INTO build (id, branch, build_number, project) VALUES (1, 'master', 1, 'buildz-project');
INSERT INTO build (id, branch, build_number, project) VALUES (2, 'master', 2, 'buildz-project');
INSERT INTO build (id, branch, build_number, project) VALUES (3, 'next', 3, 'buildz-project');
INSERT INTO build (id, branch, build_number, project) VALUES (4, 'next', 4, 'buildz-project');
INSERT INTO build (id, branch, build_number, project) VALUES (5, 'master', 5, 'buildz-test');

-- some labels
-- labels of build #1
INSERT INTO build_label (id, label_key, label_value, build_id) VALUES (
  1, 'technical_branch', 'master', 1
);
INSERT INTO build_label (id, label_key, label_value, build_id) VALUES (
  2, 'git_revision', '19cfc933d6f276ced6ea6e5903e0bd2c28613def', 1
);

-- labels of build #2
INSERT INTO build_label (id, label_key, label_value, build_id) VALUES (
  3, 'technical_branch', 'master', 2
);
INSERT INTO build_label (id, label_key, label_value, build_id) VALUES (
  4, 'git_revision', '29cfc933d6f276ced6ea6e5903e0bd2c28613def', 2
);

-- labels of build #2
INSERT INTO build_label (id, label_key, label_value, build_id) VALUES (
  5, 'technical_branch', 'feature/some-feature', 3
);
INSERT INTO build_label (id, label_key, label_value, build_id) VALUES (
  6, 'git_revision', '39cfc933d6f276ced6ea6e5903e0bd2c28613def', 3
);

-- labels of build #2
INSERT INTO build_label (id, label_key, label_value, build_id) VALUES (
  7, 'technical_branch', 'feature/some-other-feature', 4
);
INSERT INTO build_label (id, label_key, label_value, build_id) VALUES (
  8, 'git_revision', '49cfc933d6f276ced6ea6e5903e0bd2c28613def', 4
);

-- labels of build #2
INSERT INTO build_label (id, label_key, label_value, build_id) VALUES (
  9, 'technical_branch', 'feature/some-other-feature', 4
);
INSERT INTO build_label (id, label_key, label_value, build_id) VALUES (
  10, 'git_revision', '59cfc933d6f276ced6ea6e5903e0bd2c28613def', 4
);
