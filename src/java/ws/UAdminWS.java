package ws;

import java.util.List;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojos.Respuesta;
import pojos.UAdmin;

@Path("administrador")
public class UAdminWS {
    
    @Context
    private UriInfo context;
    
    public UAdminWS() {
    }
    
    @Path("all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<UAdmin> buscarTodos(){
        List<UAdmin> uAdmin = null;
        SqlSession conn = MyBatisUtil.getSession();
        if(conn != null){
            try{
                uAdmin = conn.selectList("uAdmin.getAllAdmins");
            }catch (Exception e){
                e.printStackTrace();
            }finally{
                conn.close();
            }
        }
        return uAdmin;
    }
    
    @Path("registrar")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta registrar(@FormParam("nombre")String nombre,
                            @FormParam("apellidoP") String apellidoP,
                            @FormParam("apellidoM") String apellidoM,
                            @FormParam("correo") String correo,
                            @FormParam("password") String password){
        
        UAdmin administradorRegistro = new UAdmin();
        administradorRegistro.setNombre(nombre);
        administradorRegistro.setApellidoP(apellidoP);
        administradorRegistro.setApellidoM(apellidoM);
        administradorRegistro.setCorreo(correo);
        administradorRegistro.setPassword(password);
        
        Respuesta respuestaWS = new Respuesta();
        SqlSession conexionBD = MyBatisUtil.getSession();
        if(conexionBD != null){
            try{
                int resultadoMapper = conexionBD.insert("uAdmin.registrar", administradorRegistro);
                conexionBD.commit();
                if(resultadoMapper > 0){
                    respuestaWS.setError(false);
                    respuestaWS.setMensaje("Registro de administrador ACEPTADO.");
                }else{
                    respuestaWS.setError(true);
                    respuestaWS.setMensaje("Registro de administrador DENEGADO."); 
                }
            } catch (Exception e){
                respuestaWS.setError(true);
                respuestaWS.setMensaje(e.getMessage());
            } finally{
                conexionBD.close();
            }
        }else{
            respuestaWS.setError(true);
            respuestaWS.setMensaje("CONEXION CON EL SISTEMA PERDIDO");
        }
        return respuestaWS;
    }
    
    @Path("modificar")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta modificar(@FormParam("idUAdmin")Integer idUAdmin,
                            @FormParam("nombre")String nombre,
                            @FormParam("apellidoP") String apellidoP,
                            @FormParam("apellidoM") String apellidoM,
                            @FormParam("correo") String correo,
                            @FormParam("password") String password){
        
       UAdmin administradorEdicion = new UAdmin();
        administradorEdicion.setIdUAdmin(idUAdmin);
        administradorEdicion.setNombre(nombre);
        administradorEdicion.setApellidoP(apellidoP);
        administradorEdicion.setApellidoM(apellidoM);
        administradorEdicion.setCorreo(correo);
        administradorEdicion.setPassword(password);
        
        Respuesta respuestaWS = new Respuesta();
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null){
            try{
                int resultadoMapper = conexionBD.update("uAdmin.modificar", administradorEdicion);
                conexionBD.commit();
                if(resultadoMapper > 0){
                    respuestaWS.setError(false);
                    respuestaWS.setMensaje("Actualización de información ACEPTADA.");
                }else{
                    respuestaWS.setError(true);
                    respuestaWS.setMensaje("Actualización de información DENEGADA."); 
                }
            } catch (Exception e){
                respuestaWS.setError(true);
                respuestaWS.setMensaje(e.getMessage());
            } finally{
                conexionBD.close();
            }
        }else{
            respuestaWS.setError(true);
            respuestaWS.setMensaje("CONEXION CON EL SISTEMA PERDIDO");
        }
        return respuestaWS;
    }
    
    @Path("eliminar")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta eliminar(@FormParam("idUAdmin") Integer idUAdmin){
        Respuesta respuestaWS = new Respuesta();
        SqlSession conexionBD = MyBatisUtil.getSession();
        if(conexionBD != null){
            try{
                int resultadoMapper = conexionBD.delete("uAdmin.eliminar", idUAdmin);
                conexionBD.commit();
                if(resultadoMapper > 0){
                    respuestaWS.setError(false);
                    respuestaWS.setMensaje("Baja de administrador ACEPTADA.");
                }else{
                    respuestaWS.setError(true);
                    respuestaWS.setMensaje("Identificador de administrador DENEGADO."); 
                }
            } catch (Exception e){
                respuestaWS.setError(true);
                respuestaWS.setMensaje(e.getMessage());
            } finally{
                conexionBD.close();
            }
        }else{
            respuestaWS.setError(true);
            respuestaWS.setMensaje("CONEXION CON EL SISTEMA PERDIDO");
        }
        return respuestaWS;
    }
}
