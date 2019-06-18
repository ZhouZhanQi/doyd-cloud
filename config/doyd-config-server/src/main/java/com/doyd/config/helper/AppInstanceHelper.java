package com.doyd.config.helper;

import com.doyd.config.vo.AppInfoVo;
import org.apache.http.client.utils.HttpClientUtils;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

/**
 * Spring Boot Actuactor暴露的接口有可能被应用更改,
 * 如果有更改, 应该放入eureka.instance.metadata-map里。
 * 这里提供了公用的方法，将根据metadata-map里的值来获取正确的refresh接口。
 * @author zhouzq
 * @date 2019-06-19
 */
public class AppInstanceHelper {

    private static final String KEY_MANAGEMENT_PORT = "management.port";

    private static final String KEY_MANAGEMENT_PATH = "management.context-path";

    private static final String KEY_REFRESH_PATH = "refresh.path";

    private static final String KEY_VERSION = "version";

    private static final String KEY_DESCRIPTION = "description";

    /**
     * 默认的 management-url 的 context-path
     */
    private static final String DEFAULT_MANAGEMENT_PATH = "";

    /**
     * 默认的refresh enpoint的路径
     */
    private static final String DEFAULT_REFRESH_ENPOINT_PATH = "refresh";


    /**
     * 根据服务实例，构建应用的相关信息
     *
     * @param appInfoVo
     * @param instance
     * @return
     */
    public static void fetchAppInfo(AppInfoVo appInfoVo, ServiceInstance instance) {
        String instanceUrl = instance.getUri().toString();
        // 尝试通过management相关接口获取应用信息
        URI uri = UriComponentsBuilder.fromHttpUrl(getManagementUrl(instance).toString()).pathSegment("info").build().toUri();
        try {
            Map<String, String> headers = new HashMap<>(1);
            headers.put(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

            String infoResp = HttpClientUtils.doGet(uri.toString(), null, headers, 3000);
            Map<String, Object> info = JacksonUtils.json2map(infoResp);
            if (info.containsKey(KEY_VERSION) && info.get(KEY_VERSION) != null) {
                appInfoVo.setVersion(info.get(KEY_VERSION).toString());
            }
            if (info.containsKey(KEY_DESCRIPTION) && info.get(KEY_DESCRIPTION) != null) {
                appInfoVo.setDescription(info.get(KEY_DESCRIPTION).toString());
            }
            appInfoVo.setInitialized(true);
        } catch (Exception e) {
            log.warn(">> 获取应用信息失败, app={}, uri={}, infoUrl={}", instance.getServiceId(), instanceUrl, uri.toString());
        }
    }


}
