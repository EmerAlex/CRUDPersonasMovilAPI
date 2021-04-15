package co.com.registropersonamovil2021;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.com.registropersonamovil2021.adapter.PersonaAdapter;
import co.com.registropersonamovil2021.dto.PersonaDTO;
import co.com.registropersonamovil2021.model.Persona;
import co.com.registropersonamovil2021.service.persona.PersonaServiceImpl;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.listViewPersonas)
    ListView listViewPersonas;


    private PersonaAdapter personaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        PersonaServiceImpl personaService = new PersonaServiceImpl(this);
        personaService.getPersona(listViewPersonas);
        seleccionarItem();


    }
    private void seleccionarItem(){
        listViewPersonas.setClickable(true);
        listViewPersonas.setOnItemClickListener((parent, view, position, id) -> {
            Persona personaSeleccionada= (Persona) listViewPersonas.getItemAtPosition(position);
            goToEditarPersona(view,personaSeleccionada.getIdPersona(),personaSeleccionada.getTipoDocumento().getIdTipoDocumento(), personaSeleccionada.getNumeroDocumento(),personaSeleccionada.getNombre(),personaSeleccionada.getApellido());

        });

    }



    public void goToEditarPersona(View view, int id, int tipoID, String documento, String nombre, String apellido) {
        Intent intent = new Intent(this,EditarPersonaActivity.class);
        intent.putExtra("id_persona", id);
        intent.putExtra("tipo_id", tipoID);
        intent.putExtra("documento_persona", documento);
        intent.putExtra("nombre_persona", nombre);
        intent.putExtra("apellido_persona", apellido);
        startActivity(intent);
    }

    public void goToRegistroPersona(View view) {
        Intent intent = new Intent(this,RegistroPersonaActivity.class);
        startActivity(intent);
    }

}