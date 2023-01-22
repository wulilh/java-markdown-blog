CREATE TRIGGER u_trig_ci
    AFTER UPDATE ON comment_info
BEGIN
    UPDATE comment_info SET update_time = datetime('now','localtime') WHERE id = NEW.id;
END;
CREATE TRIGGER u_trig_cr
    AFTER UPDATE ON comment_reply
BEGIN
    UPDATE comment_reply SET update_time = datetime('now','localtime') WHERE id = NEW.id;
END;