package co.com.registropersonamovil2021.service.tipodocumento;

import java.util.List;

import co.com.registropersonamovil2021.model.TipoDocumento;
import retrofit2.Call;
import retrofit2.http.GET;

public interface TipoDocumentoClient {
    @GET("v1/tipo-documento")
    Call<List<TipoDocumento>> getTipoDocumento();
}
