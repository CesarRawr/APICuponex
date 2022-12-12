package ws;

import java.math.BigInteger;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.PathParam;
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
    
    @Path("byName/{nombre}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Promocion buscarByNombre(@PathParam("nombre") String nombre){
        nombre = nombre.replace("-", " ");
        
        Promocion result = null;
        SqlSession conn = MyBatisUtil.getSession();
        if(conn != null){
            try {
                result = conn.selectOne("promocion.getByNombre",nombre);
            }catch (Exception e){
                e.printStackTrace();
            } finally{
                conn.close();
            }
        }
        
        return result;
    }
    
    @Path("byFechaIn/{fecha}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Promocion buscarByFechaIn(@PathParam("fecha") String fecha){
        Promocion result = null;
        SqlSession conn = MyBatisUtil.getSession();
        if(conn != null){
            try {
                result = conn.selectOne("promocion.getByFechaIn", fecha);
            }catch (Exception e){
                e.printStackTrace();
            } finally{
                conn.close();
            }
        }
        
        return result;
    }
    
    @Path("byFechaFn/{fecha}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Promocion buscarByFechaFn(@PathParam("fecha") String fecha){
        Promocion result = null;
        SqlSession conn = MyBatisUtil.getSession();
        if(conn != null){
            try {
                result = conn.selectOne("promocion.getByFechaFn", fecha);
            }catch (Exception e){
                e.printStackTrace();
            } finally{
                conn.close();
            }
        }
        
        return result;
    }
    
    @Path("registrar")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Respuesta registrar(Promocion body){        
        Respuesta respuestaWS = new Respuesta();
        
        SqlSession conexionBD = MyBatisUtil.getSession();
        if(conexionBD != null){
            try{
                HashMap<String, Object> promocion = new HashMap<String, Object>();
                promocion.put("nombre", body.getNombre());
                promocion.put("descripcion", body.getDescripcion());
                promocion.put("fecha_In", body.getFecha_In());
                promocion.put("fecha_Fn", body.getFecha_Fn());
                promocion.put("restricciones", body.getRestricciones());
                promocion.put("porcentDesc", body.getPorcentDesc());
                promocion.put("costoProm", body.getCostoProm());
                promocion.put("tipoProm", body.getTipoProm());
                promocion.put("idCategoriaE", body.getIdCategoriaE());
                promocion.put("is_active", body.getIs_active());
                
                int resultadoMapper = conexionBD.insert("promocion.registrar", promocion);
                conexionBD.commit();
                
                BigInteger idPromocion = (BigInteger)promocion.get("idPromocion");
                
                if(resultadoMapper > 0){
                    for (int i = 0; i<body.getSucursales().size(); i++) {
                        HashMap<String, Object> promocionSucursal = new HashMap<String, Object>();
                        promocionSucursal.put("idPromocion", idPromocion);
                        promocionSucursal.put("idSucursal", body.getSucursales().get(i));
                        
                        int resultado = conexionBD.insert("promocion.registrarSucursal", promocionSucursal);
                        conexionBD.commit();
                        
                        if (resultado <= 0) {
                            respuestaWS.setError(true);
                            respuestaWS.setMensaje("Registro de promocion DENEGADO."); 
                            return respuestaWS;
                        }
                    }
                    
                    respuestaWS.setError(false);
                    respuestaWS.setMensaje("Registro de promocion ACEPTADO.");
                    respuestaWS.setId(idPromocion.intValue());
                }else{
                    respuestaWS.setError(true);
                    respuestaWS.setMensaje("Registro de promocion DENEGADO."); 
                }
            } catch (Exception e){
                e.printStackTrace();
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
    @Consumes(MediaType.APPLICATION_JSON)
    public Respuesta modificar(Promocion body){
        
        HashMap<String, Object> promocion = new HashMap<String, Object>();
            promocion.put("idPromocion", body.getIdPromocion());
            promocion.put("nombre", body.getNombre());
            promocion.put("descripcion", body.getDescripcion());
            promocion.put("restricciones", body.getRestricciones());
            promocion.put("porcentDesc", body.getPorcentDesc());
            promocion.put("costoProm", body.getCostoProm());
            promocion.put("tipoProm", body.getTipoProm());
            promocion.put("idCategoriaE", body.getIdCategoriaE());
            promocion.put("is_active", body.getIs_active());
                
        Respuesta respuestaWS = new Respuesta();
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null){
            try{
                System.out.println("A punto de modificar");
                int resultadoMapper = conexionBD.update("promocion.modificar", promocion);
                conexionBD.commit();
                
                if(resultadoMapper > 0){
                    System.out.println("Nodificado con exito");
                    
                    System.out.println("A punto de eliminar");
                    int idPromocion = body.getIdPromocion();
                    resultadoMapper = conexionBD.delete("promocion.eliminarAllSucursalesInPromocion", idPromocion);
                    conexionBD.commit();
                    
                    if (resultadoMapper > 0){
                        System.out.println("Eliminado con exito");
                        for (int i = 0; i<body.getSucursales().size(); i++) {
                            HashMap<String, Object> promocionSucursal = new HashMap<String, Object>();
                            promocionSucursal.put("idPromocion", idPromocion);
                            promocionSucursal.put("idSucursal", body.getSucursales().get(i));

                            int resultado = conexionBD.insert("promocion.registrarSucursal", promocionSucursal);
                            conexionBD.commit();

                            if (resultado <= 0) {
                                respuestaWS.setError(true);
                                respuestaWS.setMensaje("Registro de promocion DENEGADO."); 
                                return respuestaWS;
                            }
                        }
                        
                        respuestaWS.setError(false);
                        respuestaWS.setMensaje("Registro de promocion Aceptado."); 
                    }
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
                int resultadoMapper = conexionBD.delete("promocion.eliminarAllSucursalesInPromocion", idPromocion);
                conexionBD.commit();
                if(resultadoMapper > 0){
                    resultadoMapper = conexionBD.delete("promocion.eliminar", idPromocion);
                    conexionBD.commit();
                    
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
    
    @Path("subirImagen/{id}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta subirImagen(@PathParam("id") Integer id, byte[] img){
        SqlSession conexionBD = MyBatisUtil.getSession();
        Respuesta respuesta = new Respuesta();
        respuesta.setError(true);
        if(conexionBD != null){
            try{
                HashMap<String, Object> parametros = new HashMap<>();
                parametros.put("id", id);
                parametros.put("imagen", img);
                int filasAfectadas = conexionBD.update("promocion.subirFoto", parametros);
                conexionBD.commit();
                if(filasAfectadas > 0){
                    respuesta.setError(false);
                    respuesta.setMensaje("Foto guardada correctamente.");
                }else{
                    respuesta.setMensaje("No se pudo guardar la foto");
                }
            } catch (Exception e){
                respuesta.setMensaje(e.getMessage());
            } finally{
                conexionBD.close();
            }
        }else{
            respuesta.setMensaje("Por el momento no hay conexion para el guardado de la imagen");
        }
        return respuesta;
    }
    
    @Path("desactivar")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta desactivar(@FormParam("idPromocion") Integer idPromocion) {
        Respuesta respuestaWS = new Respuesta();
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null){
            try{
                int resultadoMapper = conexionBD.update("promocion.desactivar", idPromocion);
                conexionBD.commit();
                if(resultadoMapper > 0){
                    respuestaWS.setError(false);
                    respuestaWS.setMensaje("Desactivado con exito");
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

