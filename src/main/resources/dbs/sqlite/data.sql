INSERT INTO comment_info(id,user_id,nickname,email,content) VALUES(1,'101','阿夏','123@qq.com','测试评论');
INSERT INTO comment_reply(comment_id,user_id,nickname,email,to_user_id,to_nickname,to_email,content) VALUES(1,'102','小黑','111@qq.com','101','阿夏','123@qq.com','测试回复评论');
INSERT INTO comment_reply(comment_id,user_id,nickname,email,to_user_id,to_nickname,to_email,content) VALUES(1,'103','小白','112@qq.com','102','小黑','111@qq.com','你好呀');
INSERT INTO comment_reply(comment_id,user_id,nickname,email,to_user_id,to_nickname,to_email,content) VALUES(1,'103','小白','112@qq.com','101','阿夏','123@qq.com','哈哈');
