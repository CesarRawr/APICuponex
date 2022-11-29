package ws;

import java.util.List;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojos.RespuestaLogin;
import pojos.UAdmin;
import pojos.UsuarioM;

@Path("acceso")
public class AccesoWS {
    
    @Context
    private UriInfo context;
    
    public AccesoWS(){
        
    }
    
    @Path("escritorio")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaLogin iniciarSesionEscritorio(@FormParam("nombre") String nombre,
                                                  @FormParam("password") String password){
        List<UAdmin> result = null;
        SqlSession conn = MyBatisUtil.getSession();
        RespuestaLogin respuesta = new RespuestaLogin();
        
        if(conn != null){
            try{
                result = conn.selectList("uAdmin.getAllAdmins");
            }catch (Exception e){
                e.printStackTrace();
                
            }finally{
                conn.close();
            }
        }
        
        Boolean logged = false;
        try {
            for (UAdmin uAdmin: result) {
                if (nombre.equals(uAdmin.getNombre()) && password.equals(uAdmin.getPassword())) {
                    logged = true;
                    respuesta.setError(false);
                    respuesta.setMensaje("Administrador encontrado");
                    respuesta.setIdUsuarioSesion(uAdmin.getIdUAdmin());
                    respuesta.setNombre(uAdmin.getNombre());
                    respuesta.setApellidoPaterno(uAdmin.getApellidoP());
                    respuesta.setApellidoMaterno(uAdmin.getApellidoM());
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
        
        if (!logged) {
            respuesta.setError(true);
            respuesta.setMensaje("Administrado y/o contraseña incorrectos");
        }
        
        return respuesta;
    }
    
    @Path("movil")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaLogin iniciarSesionMovil(@FormParam("nombre") String nombre,
                                                  @FormParam("password") String password){
        List<UsuarioM> result = null;
        SqlSession conn = MyBatisUtil.getSession();
        RespuestaLogin respuesta = new RespuestaLogin();
        
        if(conn != null){
            try{
                result = conn.selectList("usuariom.getAllUser");
            }catch (Exception e){
                e.printStackTrace();
                
            }finally{
                conn.close();
            }
        }
        
        Boolean logged = false;
        try {
            for (UsuarioM usuarioM: result) {
                if (nombre.equals(usuarioM.getNombre()) && password.equals(usuarioM.getPassword())) {
                    logged = true;
                    respuesta.setError(false);
                    respuesta.setMensaje("usuario encontrado");
                    respuesta.setIdUsuarioSesion(usuarioM.getIdUserM());
                    respuesta.setNombre(usuarioM.getNombre());
                    respuesta.setApellidoMaterno(usuarioM.getApellidoP());
                    respuesta.setApellidoPaterno(usuarioM.getApellidoM());
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
        
        if (!logged) {
            respuesta.setError(true);
            respuesta.setMensaje("Usuario y/o contraseña incorrectos");
        }
        
        return respuesta;
    }
}
