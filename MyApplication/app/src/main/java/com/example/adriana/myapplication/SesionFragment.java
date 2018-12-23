package com.example.adriana.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class SesionFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {
    RequestQueue rq;
    JsonRequest jrq;

    EditText txtusername, txtpassword;
    Button btnSesion,btnRegistrar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_sesion, container, false);
        txtusername = (EditText) vista.findViewById(R.id.txtusername);
        txtpassword = (EditText) vista.findViewById(R.id.txtpassword);

        btnSesion = (Button) vista.findViewById(R.id.btnsesion);
        btnRegistrar = vista.findViewById(R.id.btnregistrar);
        rq = Volley.newRequestQueue(getContext());


        btnSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iniciar_sesion();
            }
        });

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrar_usuario();
            }
        });

        return vista;
    }







    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(), "The user not found" +error.toString()+ txtusername.getText().toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(getContext(), "The User is found " + txtusername.getText().toString(), Toast.LENGTH_SHORT).show();
        User usuario = new User();
        JSONArray jsonArray = response.optJSONArray("Data");
        JSONObject jsonObject = null;

        try {
            jsonObject = jsonArray.getJSONObject(0);
            usuario.setFirtsname(jsonObject.optString("firstName"));
            usuario.setLastname(jsonObject.optString("lastName"));
            usuario.setUsername(jsonObject.optString("username"));
            usuario.setPass(jsonObject.optString("password"));
            usuario.setEmail(jsonObject.optString("email"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Intent intencion = new Intent(getContext(), Main2Activity.class);
        intencion.putExtra(Main2Activity.nombres, usuario.getFirtsname());
        startActivity(intencion);
    }
    void iniciar_sesion() {

        if(txtusername.getText().toString().isEmpty()) {
            txtusername.setError("El campo username no puede estar vacío, por favor ingrese un username e intente de nuevo.");
        }
        if(txtpassword.getText().toString().isEmpty())  {
            txtpassword.setError("El campo password no puede estar en blanco, por favor ingrese un username e intente de nuevo.");
        }

        String url = "http://milan.esoft.com.mx/examen2018/api/Users/Login  username=" + txtusername.getText().toString() +
                "password=" + txtpassword.getText().toString();
        jrq = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        rq.add(jrq);


    }

    void registrar_usuario(){
        RegistrarFragment fr=new RegistrarFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.escenario,fr)
                .addToBackStack(null)
                .commit();

    }
}
