package top.lhlnb.backend.mapper;

import org.springframework.stereotype.Repository;
import top.lhlnb.backend.domain.entity.TFile;

@Repository
public interface TFileMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TFile record);

    int insertSelective(TFile record);

    TFile selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TFile record);

    int updateByPrimaryKey(TFile record);

    TFile selectByMd5(String md5);
}