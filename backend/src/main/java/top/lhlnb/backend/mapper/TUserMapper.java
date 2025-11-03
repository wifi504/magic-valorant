package top.lhlnb.backend.mapper;

import org.springframework.stereotype.Repository;
import top.lhlnb.backend.domain.entity.TUser;

import java.util.List;

@Repository
public interface TUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TUser record);

    int insertSelective(TUser record);

    TUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TUser record);

    int updateByPrimaryKey(TUser record);

    List<String> selectPermissionsCodeByUserId(Long id);
}