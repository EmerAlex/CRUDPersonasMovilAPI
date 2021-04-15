package co.com.registropersonamovil2021.service.tipodocumento;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import co.com.registropersonamovil2021.R;
import co.com.registropersonamovil2021.model.TipoDocumento;
import co.com.registropersonamovil2021.util.RetrofitFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TipoDocumentoServiceImpl extends RetrofitFactory {



    public TipoDocumentoServiceImpl(Context context){
        super(context);
    }


    public void getTipoDocumento(Spinner spinner){
        Retrofit retrofit =  getRetrofitInstance();
        TipoDocumentoClient client = retrofit.create(TipoDocumentoClient.class);
        Call<List<TipoDocumento>> response = client.getTipoDocumento();
        response.enqueue(new Callback<List<TipoDocumento>>() {
            @Override
            public void onResponse(Call<List<TipoDocumento>> call, Response<List<TipoDocumento>> response) {
                List<TipoDocumento> tiposDocumentos = response.body();
                if(tiposDocumentos != null){
                    String [] documentosArray = new String[tiposDocumentos.size()+1];
                    documentosArray[0] = getContext().getString(R.string.seleccione);
                    for(int i = 0; i < tiposDocumentos.size(); i ++){
                        documentosArray[i+1] = tiposDocumentos.get(i).getNombreDocumento();
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(),R.layout.support_simple_spinner_dropdown_item,documentosArray);
                    spinner.setAdapter(arrayAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<TipoDocumento>> call, Throwable t) {
                Toast.makeText(getContext(), "Error de comunicación: "+ t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }
}