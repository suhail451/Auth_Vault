package com.Project.Auth_Vault.DeveloperPortal.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ClientAppDTO {
    private String clientId;
    private String appName;
    private List<RouteDTO> routes;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class RouteDTO {
        private Long id;
        private String httpMethod;
        private String path;
        private String routeType;
    }
}