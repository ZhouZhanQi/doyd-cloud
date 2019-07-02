package com.doyd.configserver.service.impl;

import com.doyd.configserver.client.BootAdminApiClient;
import com.doyd.configserver.service.IYmlConfigService;
import com.doyd.configserver.vo.AppInfoVo;
import com.doyd.configserver.vo.bootadmin.BootAdminAppVo;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author zhouzq
 * @date 2019/7/2
 * @desc yml配置文件服务实现类
 */
@Service
public class YmlConfigServiceImpl implements IYmlConfigService {

    //忽略的展示配置文件
    private static final String[] IGNORED_SERVICES = {"doyd-eureka-server", "doyd-config-server", "doyd-hystrix-dashboard"};

    @Value("${doyd.yml.repo-dir:./config-repo}")
    private String repoLocation;
    @Value("${doyd.yml.backup-dir:./backup-repo}")
    private String backupLocation;

    @Autowired
    private final DiscoveryClient discoveryClient;

    @Autowired
    private final BootAdminApiClient bootAdminApiClient;

    @Autowired
    public YmlConfigServiceImpl(DiscoveryClient discoveryClient, BootAdminApiClient bootAdminApiClient) {
        this.discoveryClient = discoveryClient;
        this.bootAdminApiClient = bootAdminApiClient;
    }

    @Override
    public List<AppInfoVo> listAppInfo() {
        List<AppInfoVo> result = Lists.newArrayList();

        List<String> services = discoveryClient.getServices();
        if (Objects.isNull(services)) {
            return result;
        }

        if (!services.isEmpty()) {
            // 过滤掉要忽略的应用(不可配置的应用)
            services = services.stream().filter(appid -> {
                for (String ignoredId : IGNORED_SERVICES) {
                    if (ignoredId.equals(appid)) {
                        return false;
                    }
                }
                return true;
            }).sorted().collect(Collectors.toList());

            // 获取应用的实例信息
            List<BootAdminAppVo> instances = bootAdminApiClient.listApps();
            for (String serviceId : services) {
                result.add(getAppInfo(serviceId, instances));
            }
        }
        return result;
    }


    private AppInfoVo getAppInfo(String serviceId, List<BootAdminAppVo> instances) {
        // 找到第一个实例
        AppInfoVo appInfoVo = new AppInfoVo();
        appInfoVo.setName(serviceId);

        List<String> urls = Lists.newArrayList();
        if (instances != null && !instances.isEmpty()) {
            String version = null;
            String description = null;
            for (BootAdminAppVo instance : instances) {
                if (serviceId.equalsIgnoreCase(instance.getName())) {
                    if (StringUtils.isBlank(version)) {
                        version = instance.getInfoVersion();
                    }
                    if (StringUtils.isBlank(description)) {
                        description = instance.getInfoDescription();
                    }
                    urls.add(instance.getServiceUrl());
                }
            }
            appInfoVo.setDescription(description);
            appInfoVo.setVersion(version);
        }
        appInfoVo.setUrls(urls);
        return appInfoVo;
    }

}
