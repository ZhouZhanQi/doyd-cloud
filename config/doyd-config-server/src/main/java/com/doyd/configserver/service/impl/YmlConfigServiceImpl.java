package com.doyd.configserver.service.impl;

import com.doyd.configserver.client.BootAdminApiClient;
import com.doyd.configserver.helper.YmlFileHelper;
import com.doyd.configserver.service.IYmlConfigService;
import com.doyd.configserver.vo.AppInfoVo;
import com.doyd.configserver.vo.bootadmin.BootAdminApplication;
import com.doyd.configserver.vo.bootadmin.Endpoint;
import com.doyd.configserver.vo.bootadmin.Instance;
import com.doyd.core.exceptions.BusinessException;
import com.doyd.core.util.JacksonUtils;
import com.doyd.core.util.http.HttpClientUtils;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author zhouzq
 * @date 2019/7/2
 * @desc yml配置文件服务实现类
 */
@Slf4j
@Service
public class YmlConfigServiceImpl implements IYmlConfigService {

    //忽略的展示配置文件
    private static final List<String> IGNORED_SERVICES =
            Lists.newArrayList("doyd-eureka-server", "doyd-config-server", "doyd-hystrix-dashboard");

    @Value("${doyd.yml.repo-dir:./config-repo}")
    private String repoLocation;

    @Value("${doyd.yml.backup-dir:./backup-repo}")
    private String backupLocation;


    private final  DiscoveryClient discoveryClient;

    private  final BootAdminApiClient bootAdminApiClient;

    @Autowired
    public YmlConfigServiceImpl(DiscoveryClient discoveryClient, BootAdminApiClient bootAdminApiClient) {
        this.discoveryClient = discoveryClient;
        this.bootAdminApiClient = bootAdminApiClient;
    }

    @Override
    public List<AppInfoVo> listAppInfo() {
        List<AppInfoVo> result = Lists.newArrayList();
        //查询注册在注册中心的微服务
        List<String> services = discoveryClient.getServices();
        if (Objects.isNull(services) || services.isEmpty()) {
            return result;
        }

        services = services.stream().filter(appId -> !IGNORED_SERVICES.contains(appId)).sorted().collect(Collectors.toList());

        List<BootAdminApplication> applications = JacksonUtils.jsonToList(bootAdminApiClient.listApps(), List.class, BootAdminApplication.class);

        if (Objects.nonNull(applications) && !applications.isEmpty()) {
            // 获取应用的实例信息
            for (String serviceId : services) {
                applications.stream().filter(application -> serviceId.equalsIgnoreCase(application.getName())).findFirst()
                        .ifPresent( appInfo -> result.add(getAppInfo(appInfo)));
            }
        }

        return result;
    }

    @Override
    public String getYamlContent(String application) {
        Preconditions.checkArgument(StringUtils.isNotBlank(application), "application name not blank");
        return YmlFileHelper.getYamlContent(repoLocation, application);
    }

    @Override
    public void editYamlContent(String application, String content) {
        Preconditions.checkArgument(StringUtils.isNotBlank(application), "application name not blank");
        Preconditions.checkArgument(StringUtils.isNotBlank(content), "application config content not blank");
        YmlFileHelper.saveYamlFile(repoLocation, backupLocation, application, content);
        postRefreshCommand(application);
    }

    @Override
    public List<String> listYamlHistory(String application) {
        Preconditions.checkArgument(StringUtils.isNotBlank(application), "application name not blank");
        return YmlFileHelper.listHistoryVersion(backupLocation, application);
    }

    @Override
    public String getYamlHistoryContent(String application, String version) {
        Preconditions.checkArgument(StringUtils.isNotBlank(application), "application name not blank");
        Preconditions.checkArgument(StringUtils.isNotBlank(version), "application history version not blank");
        return YmlFileHelper.getHistoryContent(backupLocation, application, version);
    }

    @Override
    public void rollbackAppConfig(String application, String version) {
        Preconditions.checkArgument(StringUtils.isNotBlank(application), "application name not blank");
        Preconditions.checkArgument(StringUtils.isNotBlank(version), "application history version not blank");
        YmlFileHelper.rollback2version(repoLocation, backupLocation, application, version);
    }


    private AppInfoVo getAppInfo(BootAdminApplication application) {
        AppInfoVo appInfoVo = new AppInfoVo();
        appInfoVo.setName(application.getName().toLowerCase());
        List<String> urls = Lists.newArrayList();

        application.getInstances().forEach( instance -> {
            urls.add(instance.getRegistration().getServiceUrl());
            appInfoVo.setVersion(String.valueOf(instance.getVersion()));
            appInfoVo.setDescription(instance.getInfo().getDescription());
        });

        appInfoVo.setUrls(urls);
        return appInfoVo;
    }


    //推送yml配置文件的修改到各个服务
    private void postRefreshCommand(String applicationName) {

        log.debug("<< {} config push start", applicationName);

        ResponseEntity<BootAdminApplication> applicationInfo = bootAdminApiClient.getApplicationInfoByAppName(applicationName);
        Preconditions.checkArgument(HttpStatus.OK.equals(applicationInfo.getStatusCode()), applicationName + "应用信息查询失败");

        BootAdminApplication application = applicationInfo.getBody();

        //查询/bus/refresh 并推送
        for (Instance instance : application.getInstances()) {
            Optional<Endpoint> busRefreshOpt = instance.getEndpoints().stream().filter(endpoint -> endpoint.getId().equals("bus-refresh")).findFirst();
            String pushUrl = busRefreshOpt.orElseThrow(BusinessException::new).getUrl();
            HttpClientUtils.doPost(pushUrl);

            log.debug(">> {}, yml 配置更新成功", applicationName);
        }

        log.debug("<< {} config push end", applicationName);
    }

}
