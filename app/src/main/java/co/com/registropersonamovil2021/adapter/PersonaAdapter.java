package co.com.registropersonamovil2021.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.com.registropersonamovil2021.R;
import co.com.registropersonamovil2021.model.Persona;
import co.com.registropersonamovil2021.service.persona.PersonaServiceImpl;

public class PersonaAdapter extends BaseAdapter {

    private final LayoutInflater inflater;

    private final List<Persona> personas;

    public PersonaAdapter(Context context, List<Persona> personas) {
        inflater = LayoutInflater.from(context);
        this.personas = personas;
    }

    @Override
    public int getCount() {
        return personas.size();
    }

    @Override
    public Persona getItem(int position) {
        return personas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return personas.get(position).getIdPersona().longValue();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(R.layout.item_persona, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder.numeroDocumento.setText(personas.get(position).getNumeroDocumento());
        holder.nombre.setText(personas.get(position).getNombre());
        holder.apellido.setText(personas.get(position).getApellido());
        holder.botonEliminar.setOnClickListener(v -> {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(inflater.getContext());
            builder.setCancelable(false);
            builder.setTitle(R.string.confirm);
            builder.setMessage(R.string.confirm_message_eliminar_informacion);
            builder.setPositiveButton(R.string.confirm_action, (dialog, which) -> {
                PersonaServiceImpl personaService = new PersonaServiceImpl(parent.getContext());
                personaService.Eliminar(personas.get(position).getIdPersona());
                personas.remove(position);
                notifyDataSetChanged();


            });
            builder.setNegativeButton(R.string.cancelar, (dialog, which) ->  dialog.cancel() );
            AlertDialog dialog = builder.create();
            dialog.show();
        });
        return convertView;
    }




    class ViewHolder {
        @BindView(R.id.txtNumeroDocumento)
        TextView numeroDocumento;
        @BindView(R.id.txNombre)
        TextView nombre;
        @BindView(R.id.txtApellido)
        TextView apellido;
        @BindView(R.id.btnEliminar)
        ImageButton botonEliminar;
        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
