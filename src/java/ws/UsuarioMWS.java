package ws;

import java.util.List;
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
import pojos.UsuarioM;

@Path("usuario_Movil")
public class UsuarioMWS {
    
    @Context
    private UriInfo context;
    
    public UsuarioMWS(){
    }
    
    @Path("all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<UsuarioM> buscarTodos(){
        List<UsuarioM> usuarioM = null;
        SqlSession conn = MyBatisUtil.getSession();
        if(conn != null){
            try{
                usuarioM = conn.selectList("usuariom.getAllUser");
            }catch (Exception e){
                e.printStackTrace();
            }finally{
                conn.close();
            }
        }
        return usuarioM;
    }
    
    @Path("registrar")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta registrar(@FormParam("nombre")String nombre,
                            @FormParam("apellidoP") String apellidoP,
                            @FormParam("apellidoM") String apellidoM,
                            @FormParam("telefono") String telefono,
                            @FormParam("correo") String correo,
                            @FormParam("direccion") String direccion,
                            @FormParam("fechaNac") String fechaNac,
                            @FormParam("password") String password){
        
        UsuarioM usuarioRegistro = new UsuarioM();
        usuarioRegistro.setNombre(nombre);
        usuarioRegistro.setApellidoP(apellidoP);
        usuarioRegistro.setApellidoM(apellidoM);
        usuarioRegistro.setTelefono(telefono);
        usuarioRegistro.setCorreo(correo);
        usuarioRegistro.setDireccion(direccion);
        usuarioRegistro.setFechaNac(fechaNac);
        usuarioRegistro.setPassword(password);
        
        Respuesta respuestaWS = new Respuesta();
        SqlSession conexionBD = MyBatisUtil.getSession();
        if(conexionBD != null){
            try{
                int resultadoMapper = conexionBD.insert("usuariom.registrar", usuarioRegistro);
                conexionBD.commit();
                if(resultadoMapper > 0){
                    respuestaWS.setError(false);
                    respuestaWS.setMensaje("Registro de usuario ACEPTADO.");
                }else{
                    respuestaWS.setError(true);
                    respuestaWS.setMensaje("Registro de usuario DENEGADO."); 
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
    public Respuesta modificar(@FormParam("idUserM")Integer idUserM,
                            @FormParam("nombre")String nombre,
                            @FormParam("apellidoP") String apellidoP,
                            @FormParam("apellidoM") String apellidoM,
                            @FormParam("telefono") String telefono,
                            @FormParam("correo") String correo,
                            @FormParam("direccion") String direccion,
                            @FormParam("fechaNac") String fechaNac,
                            @FormParam("password") String password){
        
        UsuarioM usuarioEdicion = new UsuarioM();
        usuarioEdicion.setIdUserM(idUserM);
        usuarioEdicion.setNombre(nombre);
        usuarioEdicion.setApellidoP(apellidoP);
        usuarioEdicion.setApellidoM(apellidoM);
        usuarioEdicion.setTelefono(telefono);
        usuarioEdicion.setCorreo(correo);
        usuarioEdicion.setDireccion(direccion);
        usuarioEdicion.setFechaNac(fechaNac);
        usuarioEdicion.setPassword(password);
        
        Respuesta respuestaWS = new Respuesta();
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null){
            try{
                int resultadoMapper = conexionBD.update("usuariom.modificar", usuarioEdicion);
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
}
