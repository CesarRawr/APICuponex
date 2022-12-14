package ws;

import java.util.Set;
import javax.ws.rs.core.Application;

@javax.ws.rs.ApplicationPath("ws")
public class ApplicationConfig extends Application{
    
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(ws.AccesoWS.class);
        resources.add(ws.EmpresaWS.class);
        resources.add(ws.PromocionWS.class);
        resources.add(ws.SucursalWS.class);
        resources.add(ws.UAdminWS.class);
        resources.add(ws.UsuarioMWS.class);
    }
}
