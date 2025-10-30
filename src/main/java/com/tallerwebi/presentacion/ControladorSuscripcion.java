package com.tallerwebi.presentacion;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import com.tallerwebi.dominio.ServicioMercadoPago;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class ControladorSuscripcion {

    @Autowired
    private ServicioMercadoPago servicioMercadoPago;

    @PostMapping("/pago")
    public void generarPreferenciaDePago(@RequestParam("plan") String plan, HttpServletResponse response) throws IOException {

        try{
            //definir monto y descripcion de plan
            double monto;
            String descripcion;

            if("premium".equalsIgnoreCase(plan)){
                monto = 1500.0;
                descripcion = "premium";
            }else{
                monto = 0.0;
                descripcion = "Normal";
            }

            Preference preference = servicioMercadoPago.crearPreferenciaDePago(monto, descripcion);
            //obtener URL de pago
            String urlPago = "sandbox".equalsIgnoreCase(servicioMercadoPago.getMercadoMode())
                    ? preference.getSandboxInitPoint()
                    : preference.getInitPoint();

            response.sendRedirect(urlPago);
        } catch (MPException | MPApiException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "ERror al crear la preferencia de pago: "+e.getMessage());
        }
    }
    @GetMapping("/confirmacion")
    public String pagoExitoso(
            @RequestParam(name="collection_id", required=false) String collectionId,
            @RequestParam(name="status", required=false) String status,
            @RequestParam(name="payment_type", required=false) String paymentType,
            @RequestParam(name="preference_id", required=false) String preferenceId,
            @RequestParam(name="payment_id", required=false) String paymentId,
            Model model) {

        model.addAttribute("collectionId", collectionId);
        model.addAttribute("status", status);
        model.addAttribute("paymentType", paymentType);
        model.addAttribute("preferenceId", preferenceId);
        model.addAttribute("paymentId", paymentId);

        System.out.println("---PAGO CONFIRMADO---");
        System.out.println("collection_id=" + collectionId);
        System.out.println("status=" + status);
        System.out.println("payment_type=" + paymentType);
        System.out.println("preference_id=" + preferenceId);
        System.out.println("payment_id=" + paymentId);

        return "confirmacion";
    }

    @GetMapping("/cancelacion")
    public String pagoCancelado(){
        return "cancelacion";
    }
    @GetMapping("/pending")
    public String pagoPendiente(){
        return "pending";
    }

    @GetMapping("/elegir-tipo-plan")
    public ModelAndView irAElegirTipoPlan(){
        return new ModelAndView("elegir-tipo-plan");
    }
}
