create table if not exists message_board_info(
    id integer constraint message_board_info_pk primary key autoincrement ,	-- '评论主表id'
    parent_message_id INTEGER , -- '父留言id'
    to_email varchar ,	--'被评论者邮箱'
    to_user_id varchar ,	--'被评论者id'
    user_id varchar, -- '评论者id'
    nickname VARCHAR , --'评论者昵称'
    email VARCHAR , --'评论者邮箱'
    avatar VARCHAR , --'评论者头像'
    url VARCHAR , --'评论者网址'
    content text not null ,	-- '评论内容'
    reply_inform INTEGER , -- '留言被回复后是否发邮件通知'
    create_time timestamp default (datetime('now','localtime')) ,   -- '创建时间'
    update_time timestamp default (datetime('now','localtime')) ,	-- '修改时间'
    is_valid varchar default 'Y' -- '是否有效'
)^;
CREATE TRIGGER  IF NOT EXISTS u_trig_mbi
    AFTER UPDATE ON message_board_info
BEGIN
UPDATE message_board_info SET update_time = datetime('now','localtime') WHERE id = NEW.id;
END^;

create table if not exists message_comment_info(
    id integer constraint message_comment_info_pk primary key autoincrement ,	-- '评论主表id'
    article_id varchar , -- '文章id'
    parent_comment_id INTEGER , -- '父留言id'
    to_email varchar ,	--'被评论者邮箱'
    to_user_id varchar ,	--'被评论者id'
    user_id varchar, -- '评论者id'
    nickname VARCHAR , --'评论者昵称'
    email VARCHAR , --'评论者邮箱'
    avatar VARCHAR , --'评论者头像'
    url VARCHAR , --'评论者头像'
    content text not null ,	-- '评论内容'
    reply_inform INTEGER , -- '留言被回复后是否发邮件通知'
    create_time timestamp default (datetime('now','localtime')) ,   -- '创建时间'
    update_time timestamp default (datetime('now','localtime')) ,	-- '修改时间'
    is_valid varchar default 'Y' -- '是否有效'
)^;
CREATE TRIGGER  IF NOT EXISTS u_trig_mci
    AFTER UPDATE ON message_comment_info
BEGIN
UPDATE message_comment_info SET update_time = datetime('now','localtime') WHERE id = NEW.id;
END^;

create table if not exists user_github(
    id integer constraint user_github_pk primary key autoincrement ,	-- 'id'
    github_id varchar , -- 'github账号id'
    node_id varchar ,	--'github唯一标识'
    login_time varchar ,	--'被评论者id'
    avatar varchar, -- 'github头像'
    nickname VARCHAR , --'github账号名字'
    public_repos VARCHAR , --'公有仓库数量'
    subscriptions VARCHAR ,	-- '仓库详细信息url'
    received_events_url VARCHAR , -- '操作事件详细信息url'
    index_url VARCHAR , -- 'github用户首页'
    cip VARCHAR , -- 'ip地址'
    cid VARCHAR , -- '地区编号'
    cname VARCHAR , -- '所在地'
    create_time timestamp default (datetime('now','localtime')) ,   -- '创建时间'
    update_time timestamp default (datetime('now','localtime')) ,	-- '修改时间'
    is_valid varchar default 'Y' -- '是否有效'
)^;
CREATE TRIGGER  IF NOT EXISTS u_trig_ug
    AFTER UPDATE ON user_github
BEGIN
UPDATE user_github SET update_time = datetime('now','localtime') WHERE id = NEW.id;
END^;