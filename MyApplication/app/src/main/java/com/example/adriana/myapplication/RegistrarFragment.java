package com.example.adriana.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Patterns;
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

import org.json.JSONObject;

import java.util.regex.Pattern;

public class RegistrarFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {
       RequestQueue rq;
       JsonRequest jrq;

    EditText txtfirstname, txtlastname, txtusernameregister, txtpasswordregister, txtconfirmpassword, txtemail;
    Button btnregisteruser, btnSesion;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_registrar, container, false);
        txtfirstname = (EditText) vista.findViewById(R.id.txtfirstname);
        txtlastname = (EditText) vista.findViewById(R.id.txtlastname);
        txtusernameregister = (EditText) vista.findViewById(R.id.txtusernameregister);
        txtpasswordregister = (EditText) vista.findViewById(R.id.txtpasswordregister);
        txtconfirmpassword = (EditText) vista.findViewById(R.id.txtconfirmpassword);
        txtemail = (EditText) vista.findViewById(R.id.txtemail);


        btnregisteruser = (Button) vista.findViewById(R.id.btnregistrar);
        //rq = Volley.newRequestQueue(getContext());

        btnSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iniciar_sesion();
            }
        });


        btnregisteruser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password= txtpasswordregister.getText().toString();
                String password2=txtconfirmpassword.getText().toString();


                if(!validarEmail("miEmail@mail.com")){
                    txtemail.setError("The email address is not valid");
                }
                if(validarPassword(password)){
                    txtemail.setError("The password must have at least 8 characters and maximum 16\n" +
                            "characters");
                }

                if(!ConfirmarPassword(password,password2)){
                    txtemail.setError("Your passwords don't match");
                }

                //registrar_usuario();
            }
        });

        return vista;
    }


    void iniciar_sesion() {

        SesionFragment fr=new SesionFragment();
        //fr.setArguments(bn);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.escenario,fr)
                .addToBackStack(null)
                .commit();
    }
    void registrar_usuario(){

        String url = "http://milan.esoft.com.mx/examen2018/api/Users/Create firstName=" +txtfirstname.getText().toString()+"lastName="+ txtlastname.getText().toString() +
                "username=" + txtusernameregister.getText().toString()+ "password=" + txtpasswordregister.getText().toString()+ "email=" + txtemail.getText().toString();;

        jrq = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        rq.add(jrq);
    }
    void limpiarCajas() {
        txtfirstname.setText("");
        txtlastname.setText("");
        txtusernameregister.setText("");
        txtpasswordregister.setText("");
        txtconfirmpassword.setText("");
        txtemail.setText("");

    }
      boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    boolean validarPassword(String pass){
        return pass!=null && pass.trim().length()<8 && pass.trim().length()>16 ;
    }
    boolean ConfirmarPassword(String password, String password2){
        return password!=null && password2!=null  && password.equals(password2);
    }


    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(), "The user has not registered " +error.toString()+  txtusernameregister.getText().toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(getContext(), "The user has registered " + txtusernameregister.getText().toString(), Toast.LENGTH_SHORT).show();
        limpiarCajas();
    }
}


