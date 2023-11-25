package jp.norinori777.infrastructure.datasource;

import java.util.List;

import jp.norinori777.domains.Settings.Setting;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SettingsMapper {
    public List<Setting> selectSettings();
}  