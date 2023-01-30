package top.b0x0.jmb.mapper;

import org.apache.ibatis.annotations.Param;
import top.b0x0.jmb.common.pojo.UserGithub;

/**
 * @author wuliling Created By 2023-01-30 15:02
 **/
public interface UserGithubMapper {

    void insert(UserGithub userGithub);

    UserGithub findByNodeId(String nodeId);

    void updateAvatar(@Param("avatar") String avatar, @Param("id") Integer id);

    void addIpAddress(@Param("cip") String cip, @Param("cid") String cid, @Param("cname") String cname, @Param("nodeId") String nodeId);
}
