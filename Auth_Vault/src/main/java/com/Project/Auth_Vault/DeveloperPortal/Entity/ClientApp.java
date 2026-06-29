
package com.Project.Auth_Vault.DeveloperPortal.Entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "client_apps")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ClientApp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String appName;

    // UUID se generate hoga — unique client identifier
    @Column(unique = true, nullable = false)
    private String clientId;

    // Foreign key — Developer se link
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "developer_id", nullable = false)
    @JsonIgnore
    private DeveloperEntity developer;

    private Instant createdAt;

    // NEW — ek app ke paas many routes
    @OneToMany(mappedBy = "clientApp", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OpenRoute> routes = new ArrayList<>();
}