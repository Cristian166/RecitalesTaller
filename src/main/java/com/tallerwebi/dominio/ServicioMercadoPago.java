package com.tallerwebi.dominio;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ServicioMercadoPago {

    //inserto token desde application
    @Value("${mercado.access.token}")
    private String accesToken;

    @Value("${mercado.mode}")
    private String mercadoMode;

    //inyectar url con ngrok https://d90f792b6be7.ngrok-free.app/ VA CAMBIANDO ACORDARSE
    @Value("${mercado.success.url}")
    private String successUrl;

    @Value("${mercado.failure.url}")
    private String failureUrl;

    @Value("${mercado.pending.url}")
    private String pendingUrl;

    public Preference crearPreferenciaDePago(Double monto, String descripcion) throws MPException, MPApiException {

        if ("sandbox".equals(mercadoMode)) {
            MercadoPagoConfig.setAccessToken(accesToken);
            MercadoPagoConfig.setPlatformId("Sandbox");
        }else{
            MercadoPagoConfig.setAccessToken(accesToken);
            MercadoPagoConfig.setPlatformId("production");
        }

        System.out.println("Acces token: " + accesToken);

        PreferenceItemRequest item = PreferenceItemRequest.builder()
                        .id("1234")
                        .title("Plan Premium")
                        .description(descripcion)
                        .categoryId("plan")
                        .quantity(1)
                        .currencyId("ARS")
                        .unitPrice(new BigDecimal(monto))
                        .build();

        List<PreferenceItemRequest> items = new ArrayList<>();
        items.add(item);

        //configurar url de retorno

        PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                .success(successUrl)
                .failure(failureUrl)
                .pending(pendingUrl)
                .build();

        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .items(items)
                .backUrls(backUrls)
                .build();

        //Llama a la api de mercado pago para crear preferencia
        PreferenceClient cliente = new PreferenceClient();
        Preference preference = cliente.create(preferenceRequest);

       if (preference != null) {
           String urlPago = "sandbox".equals(mercadoMode) ? preference.getSandboxInitPoint() :  preference.getInitPoint();
           System.out.println("Pago creado con exito URL: " + urlPago);
           System.out.println("preference ID"+ preference.getId());
       }else {
           System.out.println("La preferencia no se pudo pagar ERROR");
       }
        return preference;
    }
    public String getMercadoMode(){
        return mercadoMode;
    }
}
