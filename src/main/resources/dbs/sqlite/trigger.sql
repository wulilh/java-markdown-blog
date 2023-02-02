CREATE TRIGGER u_trig_mbi
    AFTER UPDATE ON message_board_info
BEGIN
    UPDATE message_board_info SET update_time = datetime('now','localtime') WHERE id = NEW.id;
END;

CREATE TRIGGER u_trig_mci
    AFTER UPDATE ON message_comment_info
BEGIN
    UPDATE message_comment_info SET update_time = datetime('now','localtime') WHERE id = NEW.id;
END;

CREATE TRIGGER u_trig_ug
    AFTER UPDATE ON user_github
BEGIN
    UPDATE user_github SET update_time = datetime('now','localtime') WHERE id = NEW.id;
END;