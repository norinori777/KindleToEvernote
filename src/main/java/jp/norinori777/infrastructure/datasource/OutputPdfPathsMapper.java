package jp.norinori777.infrastructure.datasource;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import jp.norinori777.domains.Settings.OutputPdfPath;

@Mapper
public interface OutputPdfPathsMapper {
    public List<OutputPdfPath> selectOutputPdfPaths();
}