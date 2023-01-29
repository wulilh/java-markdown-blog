create table message_board_info(
    id integer constraint message_board_info_pk primary key autoincrement ,	-- '评论主表id'
    parent_message_id INTEGER , -- '父留言id'
    to_email varchar ,	--'被评论者邮箱'
    to_user_id varchar ,	--'被评论者id'
    user_id varchar, -- '评论者id'
    nickname VARCHAR , --'评论者昵称'
    email VARCHAR , --'评论者邮箱'
    content text not null ,	-- '评论内容'
    reply_inform INTEGER , -- '留言被回复后是否发邮件通知'
    create_time timestamp default (datetime('now','localtime')) ,   -- '创建时间'
    update_time timestamp default (datetime('now','localtime')) ,	-- '修改时间'
    is_valid varchar default 'Y' -- '是否有效'
);

