INSERT INTO line_assessment (user_fk, grade, line_fk)
VALUES ('loic.herman@heig-vd.ch', 5.0, 200); -- succeeds

INSERT INTO line_assessment (user_fk, grade, line_fk)
VALUES ('loic.herman@heig-vd.ch', 5.0, 100); -- expected to fail due to CI being broken
