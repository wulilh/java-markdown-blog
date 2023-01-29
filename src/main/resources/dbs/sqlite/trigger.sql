CREATE TRIGGER u_trig_mbi
    AFTER UPDATE ON message_board_info
BEGIN
    UPDATE message_board_info SET update_time = datetime('now','localtime') WHERE id = NEW.id;
END;