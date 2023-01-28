create table comment_info(
    id integer constraint comment_info_pk primary key autoincrement ,	-- '评论主表id'
    user_id varchar, -- '评论者id'
    nickname VARCHAR , --'评论者昵称'
    email VARCHAR , --'评论者邮箱'
    content text not null ,	-- '评论内容'
    create_time timestamp default (datetime('now','localtime')) ,   -- '创建时间'
    update_time timestamp default (datetime('now','localtime')) ,	-- '修改时间'
    is_valid varchar default 'Y' -- '是否有效'
);

create table comment_reply(
    id integer constraint comment_reply_pk primary key autoincrement,	--'回复表id'
    comment_id integer NOT NULL ,	--'评论主表id'
    user_id varchar , -- '评论者id'
    nickname VARCHAR , --'评论者昵称'
    email VARCHAR , --'评论者邮箱'
    to_user_id varchar not null ,	--'被评论者id'
    content text not null ,--'评论内容'
    create_time timestamp default (datetime('now','localtime')) ,	-- '创建时间'
    update_time timestamp default (datetime('now','localtime')) ,	-- '修改时间'
    is_valid varchar default 'Y'	-- '是否有效'
);

