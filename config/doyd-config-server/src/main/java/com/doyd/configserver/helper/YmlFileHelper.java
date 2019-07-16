package com.doyd.configserver.helper;

import com.doyd.core.exceptions.BusinessException;
import com.doyd.core.util.DateTimeUtils;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicates;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.scanner.ScannerException;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhouzq
 * @date 2019/7/16
 * @desc yml文件操作帮助类
 */
@Slf4j
public class YmlFileHelper {

    private static final String DEFAULT_CHARSET = "UTF-8";
    private static final String DEFAULT_YAML_FILE = "application.yml";
    private static final String[] CONFIG_EXTENSIONS = {"yml"};

    private static final String HISTORY_YML_REGEX = "^application_\\d{4}[0|1][0-9][0-3][0-9]_[0-2][0-9][0-5][0-9][0-5][0-9].yml$";
    private static final String HISTORY_VERSION_FORMAT = "yyyyMMdd_HHmmss";

    private static final Yaml YAML = new Yaml();
    private static final int INVALID_YAML_ERROR = 12099;


    /**
     * 获取某个应用的所有历史版本列表, 若无则返回一个空的ArrayList
     *
     * @param backupLocation 备份根目录
     * @param application    应用ID
     * @return
     */
    public static List<String> listHistoryVersion(String backupLocation, String application) {
        // 历史文件格式为: backup-dir/{application}/application{_yyyyMMdd_HHmmss}.yml
        String dirLocation = backupLocation + File.separator + application;
        File dir = new File(dirLocation);
        if (dir.exists() && dir.isDirectory()) {
            // 列出 backup-dir/{application} 下的所有yml文件
            Collection<File> files = FileUtils.listFiles(dir, CONFIG_EXTENSIONS, false);
            if (files.isEmpty()) {
                // 没有文件, 返回空列表
                return new ArrayList<>();
            }
            return files.stream()
                    // 过滤掉不符合格式的文件(文件名格式)
                    .filter(file -> file.isFile() && file.getName().matches(HISTORY_YML_REGEX))
                    // 从文件名提取版本号
                    .map(file -> getHistoryVersion(file.getName()))
                    // 按日期倒序排序
                    .sorted(Comparator.reverseOrder())
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 保存yaml配置文件, 先将旧配置备份为历史配置, 再保存新的配置内容
     *
     * @param repoLocation   配置文件的根目录
     * @param backupLocation 历史配置文件的备份根目录
     * @param application    应用ID
     * @param content        配置内容
     */
    public static void saveYamlFile(String repoLocation, String backupLocation, String application, String content) {
        verifyYamlContent(content);
        // 配置文件: repo-dir/{application}/application.yml
        String repoDirPath = repoLocation + File.separator + application;
        File ymlFile = new File(repoDirPath + File.separator + YmlFileHelper.DEFAULT_YAML_FILE);

        if (ymlFile.exists() && ymlFile.isFile()) {
            // yaml配置文件存在, 先进行备份
            backupYml(ymlFile, backupLocation, application);
        }

        // 保存新的配置内容
        try {
            FileUtils.writeStringToFile(ymlFile, content, DEFAULT_CHARSET);
        } catch (IOException e) {
            log.error(">> 保存yaml配置文件内容失败: {}", ymlFile.getAbsolutePath(), e);
            throw new BusinessException("保存配置文件失败");
        }
    }

    /**
     * 获取yaml配置文件的内容
     *
     * @param repoLocation 配置文件的根目录
     * @param application  应用ID
     * @return
     */
    public static String getYamlContent(String repoLocation, String application) {
        String fullPathFileName = repoLocation + File.separator + application + File.separator + YmlFileHelper.DEFAULT_YAML_FILE;
        return readFileContent(new File(fullPathFileName));
    }

    /**
     * 获取历史配置文件的内容
     *
     * @param backupLocation 历史配置文件的备份根目录
     * @param application    应用ID
     * @param version        历史版本
     * @return
     */
    public static String getHistoryContent(String backupLocation, String application, String version) {
        String path = backupLocation + File.separator + application + File.separator;
        String filename = "application_" + version + ".yml";
        return readFileContent(new File(path + filename));
    }

    /**
     * 将配置内容回滚到指定的历史版本, 会先备份当前配置, 再回滚
     *
     * @param repoLocation   配置文件的根目录
     * @param backupLocation 历史配置文件的备份根目录
     * @param application    应用ID
     * @param version        历史版本
     */
    public static void rollback2version(String repoLocation, String backupLocation, String application, String version) {
        String hisPath = backupLocation + File.separator + application + File.separator + "application_" + version + ".yml";
        File hisFile = new File(hisPath);
        if (!hisFile.exists() || !hisFile.isFile()) {
            throw new BusinessException("未找到版本为" + version + "的历史配置文件");
        }

        String content;
        try {
            content = FileUtils.readFileToString(hisFile, DEFAULT_CHARSET);
        } catch (IOException e) {
            log.error(">> 读取历史配置文件内容失败: {}", hisFile.getAbsolutePath(), e);
            throw new BusinessException("读取历史配置文件内容失败");
        }

        Preconditions.checkArgument(StringUtils.isNotEmpty(content), "选定的历史配置文件内容为空,回滚失败");

        String ymlPath = repoLocation + File.separator + application + File.separator + DEFAULT_YAML_FILE;
        File ymlFile = new File(ymlPath);
        if (ymlFile.exists() && ymlFile.isFile()) {
            // 先备份
            backupYml(ymlFile, backupLocation, application);
        }

        // 回滚到选定的历史配置内容
        try {
            FileUtils.writeStringToFile(ymlFile, content, DEFAULT_CHARSET);
        } catch (IOException e) {
            log.error(">> 更新配置文件错误,回滚失败: {} -> {}", ymlFile.getAbsolutePath(), version, e);
            throw new BusinessException("更新配置文件错误,回滚失败");
        }
    }

    private static void backupYml(File ymlFile, String backupLocation, String application) {
        String backupDirPath = backupLocation + File.separator + application;

        String backupVersion = DateTimeUtils.convertLocalDateTimeToStr(LocalDateTime.now(), HISTORY_VERSION_FORMAT);
        File backupFile = new File(backupDirPath + File.separator + "application_" + backupVersion + ".yml");
        try {
            FileUtils.copyFile(ymlFile, backupFile, false);
        } catch (IOException e) {
            log.error(">> 备份yaml配置文件失败: {} -> {}", ymlFile.getAbsolutePath(), backupFile.getAbsolutePath(), e);
            throw new BusinessException("备份配置文件失败");
        }
    }

    private static String readFileContent(File file) {
        log.debug(">> reading yaml content for: {}", file.getAbsolutePath());
        if (file.exists() && file.isFile()) {
            try {
                return FileUtils.readFileToString(file, DEFAULT_CHARSET);
            } catch (IOException e) {
                log.error(">> 读取yaml配置文件内容失败: {}", file.getAbsolutePath(), e);
                throw new BusinessException("读取配置文件内容失败");
            }
        }
        return null;
    }

    private static String getHistoryVersion(String filename) {
        String temp1 = filename.substring("application".length() + 1);
        return temp1.substring(0, temp1.indexOf(".yml"));
    }

    /**
     * 尝试解析yaml内容，以便判断内容是否为有效的yaml格式
     *
     * @param yamlContent
     */
    private static void verifyYamlContent(String yamlContent) {
        try {
            YAML.load(yamlContent);
        } catch (ScannerException e) {
            throw new BusinessException("错误的yaml格式", INVALID_YAML_ERROR);
        }
    }

}
