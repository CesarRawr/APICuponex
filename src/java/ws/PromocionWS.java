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
import pojos.Promocion;

@Path("promocion")
public class PromocionWS {
    
    @Context
    private UriInfo context;
    
    public PromocionWS(){
    }
    
    @Path("all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Promocion> buscarTodos(){
        List<Promocion> promocion = null;
        SqlSession conn = MyBatisUtil.getSession();
        if(conn != null){
            try{
                promocion = conn.selectList("promocion.getAllPromociones");
            }catch (Exception e){
                e.printStackTrace();
            }finally{
                conn.close();
            }
        }
        return promocion;
    }
    
    @Path("registrar")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta registrar(@FormParam("nPromocion") String nPromocion,
                            @FormParam("descripcion") String descripcion,
                            @FormParam("fecha_In") String fecha_In,
                            @FormParam("fecha_Fn") String fecha_Fn,
                            @FormParam("restricciones") String restricciones,
                            @FormParam("porcentDesc") int porcentDesc,
                            @FormParam("costoProm") int costoProm,
                            @FormParam("tipoProm") String tipoProm,
                            @FormParam("idCategoriaE") int idCategoriaE,
                            @FormParam("idEstatusP") int idEstatusP,
                            @FormParam("idSucursal") int idSucursal){
        
        Promocion promocionRegistro = new Promocion(nPromocion, descripcion, fecha_In, fecha_Fn, restricciones, porcentDesc, costoProm, tipoProm, idCategoriaE, idEstatusP, idSucursal);
        
        Respuesta respuestaWS = new Respuesta();
        SqlSession conexionBD = MyBatisUtil.getSession();
        if(conexionBD != null){
            try{
                int resultadoMapper = conexionBD.insert("promocion.registrar", promocionRegistro);
                conexionBD.commit();
                if(resultadoMapper > 0){
                    respuestaWS.setError(false);
                    respuestaWS.setMensaje("Registro de promocion ACEPTADO.");
                }else{
                    respuestaWS.setError(true);
                    respuestaWS.setMensaje("Registro de promocion DENEGADO."); 
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
    public Respuesta modificar(@FormParam("nPromocion") String nPromocion,
                            @FormParam("descripcion") String descripcion,
                            @FormParam("restricciones") String restricciones,
                            @FormParam("porcentDesc") int porcentDesc,
                            @FormParam("costoProm") int costoProm,
                            @FormParam("tipoProm") String tipoProm){
        
        Promocion promocionRegistro = new Promocion(nPromocion, descripcion,restricciones, porcentDesc, costoProm, tipoProm);
        
        Respuesta respuestaWS = new Respuesta();
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null){
            try{
                int resultadoMapper = conexionBD.update("promocion.modificar", promocionRegistro);
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
    public Respuesta eliminar(@FormParam("idPromocion") Integer idPromocion){
        Respuesta respuestaWS = new Respuesta();
        SqlSession conexionBD = MyBatisUtil.getSession();
        if(conexionBD != null){
            try{
                int resultadoMapper = conexionBD.delete("promocion.eliminar", idPromocion);
                conexionBD.commit();
                if(resultadoMapper > 0){
                    respuestaWS.setError(false);
                    respuestaWS.setMensaje("Baja de promocion ACEPTADA.");
                }else{
                    respuestaWS.setError(true);
                    respuestaWS.setMensaje("Identificador de promocion DENEGADO."); 
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

