package com.mycompany.myapp.config;

import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.context.annotation.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.mycompany.myapp.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.mycompany.myapp.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.mycompany.myapp.domain.User.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Authority.class.getName());
            createCache(cm, com.mycompany.myapp.domain.User.class.getName() + ".authorities");
            createCache(cm, com.mycompany.myapp.domain.Estado.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Estado.class.getName() + ".ciudads");
            createCache(cm, com.mycompany.myapp.domain.Ciudad.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Ciudad.class.getName() + ".cPS");
            createCache(cm, com.mycompany.myapp.domain.CP.class.getName());
            createCache(cm, com.mycompany.myapp.domain.CP.class.getName() + ".colonias");
            createCache(cm, com.mycompany.myapp.domain.Colonia.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Colonia.class.getName() + ".direccions");
            createCache(cm, com.mycompany.myapp.domain.Direccion.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Direccion.class.getName() + ".direccionPersonas");
            createCache(cm, com.mycompany.myapp.domain.Direccion.class.getName() + ".viajes");
            createCache(cm, com.mycompany.myapp.domain.Sucursal.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Sucursal.class.getName() + ".inventarios");
            createCache(cm, com.mycompany.myapp.domain.Sucursal.class.getName() + ".empleados");
            createCache(cm, com.mycompany.myapp.domain.Persona.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Persona.class.getName() + ".personaMorals");
            createCache(cm, com.mycompany.myapp.domain.Persona.class.getName() + ".direccionPersonas");
            createCache(cm, com.mycompany.myapp.domain.Persona.class.getName() + ".empleados");
            createCache(cm, com.mycompany.myapp.domain.Persona.class.getName() + ".clientes");
            createCache(cm, com.mycompany.myapp.domain.Persona.class.getName() + ".contactos");
            createCache(cm, com.mycompany.myapp.domain.PersonaMoral.class.getName());
            createCache(cm, com.mycompany.myapp.domain.PersonaMoral.class.getName() + ".sucursals");
            createCache(cm, com.mycompany.myapp.domain.PersonaMoral.class.getName() + ".reportes");
            createCache(cm, com.mycompany.myapp.domain.PersonaMoral.class.getName() + ".pedidos");
            createCache(cm, com.mycompany.myapp.domain.PersonaFisica.class.getName());
            createCache(cm, com.mycompany.myapp.domain.TipoContacto.class.getName());
            createCache(cm, com.mycompany.myapp.domain.TipoContacto.class.getName() + ".contactos");
            createCache(cm, com.mycompany.myapp.domain.Contacto.class.getName());
            createCache(cm, com.mycompany.myapp.domain.DireccionPersona.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Empleado.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Empleado.class.getName() + ".empleadoTipoVehiculos");
            createCache(cm, com.mycompany.myapp.domain.Empleado.class.getName() + ".experienciaHabilidads");
            createCache(cm, com.mycompany.myapp.domain.Empleado.class.getName() + ".licenciaManejos");
            createCache(cm, com.mycompany.myapp.domain.Empleado.class.getName() + ".horarios");
            createCache(cm, com.mycompany.myapp.domain.Empleado.class.getName() + ".operadorUnidads");
            createCache(cm, com.mycompany.myapp.domain.Horario.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Horario.class.getName() + ".diaHorarios");
            createCache(cm, com.mycompany.myapp.domain.DiaHorario.class.getName());
            createCache(cm, com.mycompany.myapp.domain.LicenciaManejo.class.getName());
            createCache(cm, com.mycompany.myapp.domain.ExperienciaHabilidad.class.getName());
            createCache(cm, com.mycompany.myapp.domain.EmpleadoTipoVehiculo.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Vehiculo.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Vehiculo.class.getName() + ".unidads");
            createCache(cm, com.mycompany.myapp.domain.Unidad.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Unidad.class.getName() + ".aditamentoUnidads");
            createCache(cm, com.mycompany.myapp.domain.Unidad.class.getName() + ".polizas");
            createCache(cm, com.mycompany.myapp.domain.Unidad.class.getName() + ".unidadViajes");
            createCache(cm, com.mycompany.myapp.domain.Poliza.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Aditamento.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Aditamento.class.getName() + ".aditamentoUnidads");
            createCache(cm, com.mycompany.myapp.domain.AditamentoUnidad.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Cliente.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Cliente.class.getName() + ".vehiculoClientes");
            createCache(cm, com.mycompany.myapp.domain.Cliente.class.getName() + ".unidadServicios");
            createCache(cm, com.mycompany.myapp.domain.VehiculoCliente.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Viaje.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Viaje.class.getName() + ".itinerarios");
            createCache(cm, com.mycompany.myapp.domain.Viaje.class.getName() + ".unidadViajes");
            createCache(cm, com.mycompany.myapp.domain.Itinerario.class.getName());
            createCache(cm, com.mycompany.myapp.domain.UnidadViaje.class.getName());
            createCache(cm, com.mycompany.myapp.domain.UnidadViaje.class.getName() + ".operadorUnidads");
            createCache(cm, com.mycompany.myapp.domain.OperadorUnidad.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Estante.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Estante.class.getName() + ".nivels");
            createCache(cm, com.mycompany.myapp.domain.Nivel.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Nivel.class.getName() + ".seccions");
            createCache(cm, com.mycompany.myapp.domain.Seccion.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Seccion.class.getName() + ".ubicacionInventarios");
            createCache(cm, com.mycompany.myapp.domain.Producto.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Producto.class.getName() + ".inventarios");
            createCache(cm, com.mycompany.myapp.domain.Inventario.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Inventario.class.getName() + ".ubicacionInventarios");
            createCache(cm, com.mycompany.myapp.domain.Inventario.class.getName() + ".resurtirs");
            createCache(cm, com.mycompany.myapp.domain.Inventario.class.getName() + ".precioVentas");
            createCache(cm, com.mycompany.myapp.domain.Inventario.class.getName() + ".materialServicios");
            createCache(cm, com.mycompany.myapp.domain.Inventario.class.getName() + ".perdidas");
            createCache(cm, com.mycompany.myapp.domain.Inventario.class.getName() + ".listaPedidos");
            createCache(cm, com.mycompany.myapp.domain.UbicacionInventario.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Resurtir.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Resurtir.class.getName() + ".renglonResurtirs");
            createCache(cm, com.mycompany.myapp.domain.RenglonResurtir.class.getName());
            createCache(cm, com.mycompany.myapp.domain.PrecioVenta.class.getName());
            createCache(cm, com.mycompany.myapp.domain.PrecioVenta.class.getName() + ".renglonVentas");
            createCache(cm, com.mycompany.myapp.domain.Venta.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Venta.class.getName() + ".renglonVentas");
            createCache(cm, com.mycompany.myapp.domain.Venta.class.getName() + ".ventaPedidos");
            createCache(cm, com.mycompany.myapp.domain.RenglonVenta.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Perdida.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Servicio.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Servicio.class.getName() + ".materialServicios");
            createCache(cm, com.mycompany.myapp.domain.Servicio.class.getName() + ".precioServicios");
            createCache(cm, com.mycompany.myapp.domain.Servicio.class.getName() + ".listaServicios");
            createCache(cm, com.mycompany.myapp.domain.MaterialServicio.class.getName());
            createCache(cm, com.mycompany.myapp.domain.PrecioServicio.class.getName());
            createCache(cm, com.mycompany.myapp.domain.UnidadServicio.class.getName());
            createCache(cm, com.mycompany.myapp.domain.UnidadServicio.class.getName() + ".listaServicios");
            createCache(cm, com.mycompany.myapp.domain.ListaServicio.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Cita.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Cita.class.getName() + ".statusCitas");
            createCache(cm, com.mycompany.myapp.domain.StatusCita.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Cotizacion.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Pago.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Pedido.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Pedido.class.getName() + ".listaPedidos");
            createCache(cm, com.mycompany.myapp.domain.Pedido.class.getName() + ".ventaPedidos");
            createCache(cm, com.mycompany.myapp.domain.ListaPedido.class.getName());
            createCache(cm, com.mycompany.myapp.domain.VentaPedido.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Reporte.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
