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
import pojos.Sucursal;

@Path("sucursal")
public class SucursalWS {
    
    @Context
    private UriInfo context;
    
    public SucursalWS(){
    }
    
    @Path("all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Sucursal> buscarTodos(){
        List<Sucursal> sucursal = null;
        SqlSession conn = MyBatisUtil.getSession();
        if(conn != null){
            try{
                sucursal = conn.selectList("sucursal.getAllSucursal");
            }catch (Exception e){
                e.printStackTrace();
            }finally{
                conn.close();
            }
        }
        return sucursal;
    }
    
    @Path("registrar")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta registrar(@FormParam("idEmpresa") Integer idEmpresa,
                            @FormParam("nombre") String nombre,
                            @FormParam("direccion") String direccion,
                            @FormParam("codigo_P") String codigo_P,
                            @FormParam("colonia") String colonia,
                            @FormParam("ciudad") String ciudad,
                            @FormParam("telefono") String telefono,
                            @FormParam("latitud") float latitud,
                            @FormParam("longitud") float longitud,
                            @FormParam("encargado") String encargado){
        
        Sucursal sucursalRegistro = new Sucursal();
        sucursalRegistro.setIdEmpresa(idEmpresa);
        sucursalRegistro.setNombre(nombre);
        sucursalRegistro.setDireccion(direccion);
        sucursalRegistro.setCodigo_P(codigo_P);
        sucursalRegistro.setColonia(colonia);
        sucursalRegistro.setCiudad(ciudad);
        sucursalRegistro.setTelefono(telefono);
        sucursalRegistro.setLatitud(latitud);
        sucursalRegistro.setLongitud(longitud);
        sucursalRegistro.setEncargado(encargado);
        
        Respuesta respuestaWS = new Respuesta();
        SqlSession conexionBD = MyBatisUtil.getSession();
        if(conexionBD != null){
            try{
                int resultadoMapper = conexionBD.insert("sucursal.registrar", sucursalRegistro);
                conexionBD.commit();
                if(resultadoMapper > 0){
                    respuestaWS.setError(false);
                    respuestaWS.setMensaje("Registro de sucursal ACEPTADO.");
                }else{
                    respuestaWS.setError(true);
                    respuestaWS.setMensaje("Registro de sucursal DENEGADO."); 
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
    public Respuesta modificar(@FormParam("idSucursal")Integer idSucursal,
                            @FormParam("nombre") String nombre,
                            @FormParam("direccion") String direccion,
                            @FormParam("codigo_P") String codigo_P,
                            @FormParam("colonia") String colonia,
                            @FormParam("ciudad") String ciudad,
                            @FormParam("telefono") String telefono,
                            @FormParam("latitud") float latitud,
                            @FormParam("longitud") float longitud,
                            @FormParam("encargado") String encargado){
        
        Sucursal usuarioEdicion = new Sucursal();
        usuarioEdicion.setIdSucursal(idSucursal);
        usuarioEdicion.setNombre(nombre);
        usuarioEdicion.setDireccion(direccion);
        usuarioEdicion.setCodigo_P(codigo_P);
        usuarioEdicion.setColonia(colonia);
        usuarioEdicion.setCiudad(ciudad);
        usuarioEdicion.setDireccion(direccion);
        usuarioEdicion.setTelefono(telefono);
        usuarioEdicion.setLatitud(latitud);
        usuarioEdicion.setLongitud(longitud);
        usuarioEdicion.setEncargado(encargado);
        
        Respuesta respuestaWS = new Respuesta();
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null){
            try{
                int resultadoMapper = conexionBD.update("sucursal.modificar", usuarioEdicion);
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
    public Respuesta eliminar(@FormParam("idSucursal") Integer idSucursal){
        Respuesta respuestaWS = new Respuesta();
        SqlSession conexionBD = MyBatisUtil.getSession();
        if(conexionBD != null){
            try{
                int resultadoMapper = conexionBD.delete("sucursal.eliminar", idSucursal);
                conexionBD.commit();
                if(resultadoMapper > 0){
                    respuestaWS.setError(false);
                    respuestaWS.setMensaje("Baja de sucursal ACEPTADA.");
                }else{
                    respuestaWS.setError(true);
                    respuestaWS.setMensaje("Identificador de sucursal DENEGADO."); 
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
