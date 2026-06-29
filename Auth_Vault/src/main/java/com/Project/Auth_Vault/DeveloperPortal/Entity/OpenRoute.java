package com.Project.Auth_Vault.DeveloperPortal.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "open_routes")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class OpenRoute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Foreign key — ClientApp se link
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_app_id", nullable = false)
    private ClientApp clientApp;

    @Column(nullable = false)
    private String httpMethod;  // GET, POST, PUT, DELETE

    @Column(nullable = false)
    private String path;        // /api/products

    // open ya protected
    @Column(nullable = false)
    private String routeType;   // "open" or "protected"
}
