package co.com.registropersonamovil2021;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.com.registropersonamovil2021.dto.PersonaDTO;
import co.com.registropersonamovil2021.service.persona.PersonaServiceImpl;
import co.com.registropersonamovil2021.service.tipodocumento.TipoDocumentoServiceImpl;
import co.com.registropersonamovil2021.util.ActionBarUtil;

public class EditarPersonaActivity extends AppCompatActivity {

    @BindView(R.id.editar_documento)
    TextView editarDocumento;

    @BindView(R.id.editar_nombre)
    EditText editarNombre;

    @BindView(R.id.editar_apellido)
    EditText editarApellido;

    @BindView(R.id.spinner_editar_persona)
    Spinner spinnerTipoDocumento;

    PersonaDTO persona;
    private Integer documentoSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_persona);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        ActionBarUtil.getInstance(this, false).setToolBar(getString(R.string.editar_persona), getString(R.string.editar));
        listarTiposDocumentos();
        onSelectItemSpinner();
        persona = new PersonaDTO();
        editarDocumento.setText(intent.getStringExtra("documento_persona"));
        editarNombre.setText(intent.getStringExtra("nombre_persona"));
        editarApellido.setText(intent.getStringExtra("apellido_persona"));

    }

    private void onSelectItemSpinner() {
        spinnerTipoDocumento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                documentoSeleccionado = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void listarTiposDocumentos() {
        TipoDocumentoServiceImpl tipoDocumentoService = new TipoDocumentoServiceImpl(this);
        tipoDocumentoService.getTipoDocumento(spinnerTipoDocumento);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save) {
            if(validarCampos()){
                AlertDialog.Builder builder = new AlertDialog.Builder(EditarPersonaActivity.this);
                builder.setCancelable(false);
                builder.setTitle(R.string.confirm);
                builder.setMessage(R.string.confirm_message_guardar_informacion);
                builder.setPositiveButton(R.string.confirm_action, (dialog, which) -> {
                    guardarInformacion();
                    goToPrincipal();
                });
                builder.setNegativeButton(R.string.cancelar, (dialog, which) -> dialog.cancel());
                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            }
            else {
                return false;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void guardarInformacion() {
        PersonaServiceImpl personaService = new PersonaServiceImpl(this);
        persona.setIdTipoDocumento(documentoSeleccionado);
        persona.setNumeroDocumento(editarDocumento.getText().toString());
        persona.setNombre(editarNombre.getText().toString());
        persona.setApellido(editarApellido.getText().toString());
        personaService.editar(getIntent().getIntExtra("id_persona",-1),persona);
    }
    private boolean validarCampos(){
        if(spinnerTipoDocumento.getSelectedItem().toString().equalsIgnoreCase("-- Seleccione --")) {
            Toast.makeText(this, R.string.tipo_documento_no_valido, Toast.LENGTH_LONG).show();
            return false;
        }        else if(editarDocumento.getText().toString().isEmpty() || editarDocumento.equals(null)){
            Toast.makeText(this, R.string.documeto_vacio, Toast.LENGTH_LONG).show();
            return false;
        }else if(editarNombre.getText().toString().isEmpty()|| editarNombre.equals(null)){
            Toast.makeText(this, R.string.nombre_vacio, Toast.LENGTH_LONG).show();
            return false;
        }else if(editarApellido.getText().toString().isEmpty() || editarApellido.equals(null)){
            Toast.makeText(this, R.string.apellido_vacio, Toast.LENGTH_LONG).show();
            return false;
        }else{

            return true;
        }

    }
    private void goToPrincipal(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}