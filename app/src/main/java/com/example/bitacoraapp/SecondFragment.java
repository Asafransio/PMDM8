package com.example.bitacoraapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class SecondFragment extends Fragment implements Adapter_apuntes.OnRecyclerItemListener {
    ArrayList<String> apuntes;
    RecyclerView recyclerA;
    JSONArray result;
    JSONObject jsonobject;
    Integer idCuaderno;
    View view3;
    View view4;
    EditText fechaApunte;
    EditText textoApunte;
    String idApunteAUX;
    String fechaApunteAUX;
    String textoApunteAUX;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        recyclerA=view.findViewById(R.id.recyclerA);
        recyclerA.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        apuntes = new ArrayList<String>();
        AccesoRemoto acceso = new AccesoRemoto();
        acceso.execute();
        Adapter_apuntes adapter = new Adapter_apuntes(getContext(), apuntes, this);
        recyclerA.setAdapter(adapter);

        Button volver = view.findViewById(R.id.volver);

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(SecondFragment.this).navigate(R.id.action_SecondFragment_to_FirstFragment);

            }
        });

        FloatingActionButton fab = view.findViewById(R.id.fab2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = getActivity().getLayoutInflater();
                view4 = inflater.inflate(R.layout.insert_apunte, null);

                dialog.setView(view4);

                fechaApunte = view4.findViewById(R.id.editTextTextPersonName2);
                textoApunte = view4.findViewById(R.id.editTextTextPersonName3);

                dialog.setTitle(R.string.dialogAI_titulo).setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        fechaApunteAUX = fechaApunte.getText().toString();
                        textoApunteAUX = textoApunte.getText().toString();
                        InsertRemoto insert = new InsertRemoto(fechaApunteAUX, textoApunteAUX);
                        insert.execute();

                        dialog.dismiss();

                    }
                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        fechaApunte.setText("");
                        textoApunte.setText("");
                        dialog.dismiss();
                    }
                });
                dialog.show();


            }
        });
        return view;

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /* view.findViewById(R.id.button_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        }); */
    }

    @Override
    public void onItemHold(int position) throws JSONException {

        final AlertDialog.Builder dialog2 = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        view3 = inflater.inflate(R.layout.selection_layout, null);

        dialog2.setTitle(R.string.dialogCI_titulo2).setPositiveButton("Modificar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog2, int which) {

                //******************************//

                final AlertDialog.Builder dialog4 = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = getActivity().getLayoutInflater();
                view4 = inflater.inflate(R.layout.insert_apunte, null);

                dialog4.setView(view4);

                fechaApunte = view4.findViewById(R.id.editTextTextPersonName2);
                textoApunte = view4.findViewById(R.id.editTextTextPersonName3);

                Log.d("SALE", apuntes.toString());
                Log.d("SALE", "" + position);

                idApunteAUX = apuntes.get(position).split(" / ")[0];
                fechaApunte.setText(apuntes.get(position).split(" / ")[1]);
                textoApunte.setText(apuntes.get(position).split(" / ")[2]);


                dialog4.setTitle(R.string.dialogCI_titulo).setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog4, int which) {
                        fechaApunteAUX = fechaApunte.getText().toString();
                        textoApunteAUX = textoApunte.getText().toString();
                        ModificacionRemota alter = new ModificacionRemota(fechaApunte.getText().toString(), textoApunte.getText().toString(), idApunteAUX);
                        alter.execute();
                        AccesoRemoto acceso2 = new AccesoRemoto();
                        acceso2.execute();
                        dialog4.dismiss();

                    }
                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog4, int which) {
                        fechaApunte.setText("");
                        textoApunte.setText("");
                        dialog4.dismiss();
                    }
                });
                dialog4.show();



                dialog2.dismiss();

                //*****************************//

            }
        }).setNegativeButton("Borrar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog2, int which) {

                final AlertDialog.Builder dialog3 = new AlertDialog.Builder(getContext());

                LayoutInflater inflater = getActivity().getLayoutInflater();
                view3 = inflater.inflate(R.layout.selection_layout, null);

                dialog3.setTitle(R.string.dialogCI_titulo3).setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog3, int which) {

                        BajaRemota baja = new BajaRemota(apuntes.get(position).split(" / ")[0]);
                        baja.execute();
                        dialog3.dismiss();

                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog3, int which) {

                        dialog3.dismiss();

                    }
                });
                dialog3.show();
                dialog2.dismiss();
            }
        });
        dialog2.show();



        // Adapter_cuadernos adapter = new Adapter_cuadernos(getContext(), cuadernos, this);
        // recyclerC.setAdapter(adapter);

    }

    private class AccesoRemoto extends AsyncTask<Void, Void, String> {


        String fechaApunte = "";
        String textoApunte = "";
        String idApunte = "";

        protected void onPreExecute()
        {
            Bundle bundle = getArguments();
            idCuaderno = bundle.getInt("idCuaderno");
        }


        @Override
        protected String doInBackground(Void... voids) {
            try
            {


                Log.d("prueba", idCuaderno + "");
                // Crear la URL de conexión al API
                URL url = new URL("http://192.168.68.105/ApiRest/apuntes.php?idCuaderno=" + idCuaderno/* + "&sort=fechaApunte:des"*/);
                // Crear la conexión HTTP
                HttpURLConnection myConnection = (HttpURLConnection) url.openConnection();
                myConnection.setRequestMethod("GET");

                if (myConnection.getResponseCode() == 200)
                {
                    // Conexión exitosa
                    // Creamos Stream para la lectura de datos desde elservidor
                    InputStream responseBody = myConnection.getInputStream();
                    InputStreamReader responseBodyReader = new InputStreamReader(responseBody, "UTF-8");
                    // Creamos Buffer de lectura
                    BufferedReader bR = new BufferedReader(responseBodyReader);
                    String line = "";
                    StringBuilder responseStrBuilder = new StringBuilder();
                    // Leemos el flujo de entrada
                    while ((line = bR.readLine()) != null)
                    {
                        responseStrBuilder.append(line);
                    }
                    result = new JSONArray(responseStrBuilder.toString());
                    int posicion = 0;

                    for (int i = 0; i<result.length();i++) {

                        jsonobject = result.getJSONObject(posicion);
                        idApunte = jsonobject.getString("idApunte");
                        fechaApunte = jsonobject.getString("fechaApunte");
                        textoApunte = jsonobject.getString("textoApunte");
                        apuntes.add(idApunte + " / " + fechaApunte + " / " + textoApunte);
                        posicion++;
                    }




                    responseBody.close();
                    responseBodyReader.close();
                    myConnection.disconnect();
                }
                else
                {
                    // Error en la conexión

                    Toast.makeText(getContext(), "¡Conexión fallida!",
                            Toast.LENGTH_SHORT).show();
                }



            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private class InsertRemoto extends AsyncTask<Void, Void, String> {
        String fechaApunte = "";
        String textoApunte = "";

        public InsertRemoto(String fechaApunte, String textoApunte)
        {
            this.fechaApunte = fechaApunte;
            this.textoApunte = textoApunte;
            // Success

        }


        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL("http://192.168.68.105/ApiRest/apuntes.php");
                HttpURLConnection myConnection = (HttpURLConnection) url.openConnection();
                myConnection.setRequestMethod("POST");


                String response="";
                HashMap<String, String> postDataParams = new HashMap<String, String>();
                postDataParams.put("fechaApunte", this.fechaApunte);
                postDataParams.put("textoApunte", this.textoApunte);
                postDataParams.put("idCuadernoFK", idCuaderno.toString());
                Log.d("Muestra", fechaApunte + " - " + textoApunte + " - " + idCuaderno.toString());
                myConnection.setDoInput(true);
                myConnection.setDoOutput(true);
                OutputStream os = myConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));
                writer.flush();
                writer.close();
                os.close();
                myConnection.getResponseCode();
                if (myConnection.getResponseCode() == 200)
                {
                    // Success
                    Log.println(Log.ASSERT, "Result", "ENTRA");
                    myConnection.disconnect();
                }
                else {
                    // Error handling code goes here
                    Log.println(Log.ASSERT, "Error", "Error");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException
        {
            StringBuilder result = new StringBuilder();
            boolean first = true;
            for(Map.Entry<String, String> entry : params.entrySet())
            {

                result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            }
            Log.println(Log.ASSERT, "Result", result.toString());
            return result.toString();

        }
        protected void onPostExecute(String mensaje)
        {

        }

    }

    private class BajaRemota extends AsyncTask<Void, Void, String> {
        String idApunte;

        public BajaRemota(String id) {
            this.idApunte = id;
        }

        @Override
        protected String doInBackground(Void... voids) {
            try
            {
                // Crear la URL de conexión al API
                URI baseUri = new URI("http://192.168.68.105/ApiRest/apuntes.php");
                String[] parametros = {"id",this.idApunte};
                URI uri =  applyParameters(baseUri, parametros);
                // Create connection
                HttpURLConnection myConnection = (HttpURLConnection)
                        uri.toURL().openConnection();
                // Establecer método. Por defecto GET.
                myConnection.setRequestMethod("DELETE");
                if (myConnection.getResponseCode() == 200)
                {
                    // Success
                    Log.println(Log.ASSERT,"Resultado", "Registro borrado");
                    myConnection.disconnect();
                }
                else
                {
                    // Error handling code goes here
                    Log.println(Log.ASSERT,"Error", "Error");
                }
            }
            catch(Exception e)
            {
                Log.println(Log.ASSERT,"Excepción", e.getMessage());
            }
            return null;
        }

        URI applyParameters(URI uri, String[] urlParameters)
        {
            StringBuilder query = new StringBuilder();
            boolean first = true;
            for (int i = 0; i < urlParameters.length; i += 2)
            {
                if (first)
                {
                    first = false;
                }
                else
                {
                    query.append("&");
                }
                try
                {
                    query.append(urlParameters[i]).append("=").append(URLEncoder.encode(urlParameters[i + 1],
                            "UTF-8"));
                }
                catch (UnsupportedEncodingException ex)
                {
                    /* As URLEncoder are always correct, this exception
                     * should never be thrown. */
                    throw new RuntimeException(ex);
                }
            }
            try
            {
                return new URI(uri.getScheme(), uri.getAuthority(), uri.getPath(), query.toString(), null);
            }
            catch (Exception ex)
            {
                /* As baseUri and query are correct, this exception
                 * should never be thrown. */
                throw new RuntimeException(ex);
            }
        }

    }

    private class ModificacionRemota extends AsyncTask<Void, Void, String> {

        String fechaApunte;
        String idApunte;
        String textoApunte;

        public ModificacionRemota(String fechaApunte, String textoApunte, String idApunte)
        {
            this.fechaApunte = fechaApunte;
            this.idApunte = idApunte;
            this.textoApunte = textoApunte;
        }


        @Override
        protected String doInBackground(Void... voids) {

            try
            {
                String response = "";
                Uri uri = new Uri.Builder().scheme("http").authority("192.168.68.105").path("/ApiRest/apuntes.php").appendQueryParameter("idApunte", this.idApunte).appendQueryParameter("fechaApunte", this.fechaApunte).appendQueryParameter("textoApunte", this.textoApunte).build();
                // Create connection
                URL url = new URL(uri.toString());
                Log.d("PRUEBA", url.toString());
                HttpURLConnection connection = (HttpURLConnection)
                        url.openConnection();
                connection.setReadTimeout(15000);
                connection.setConnectTimeout(15000);
                connection.setRequestMethod("PUT");
                connection.setDoInput(true);
                connection.setDoOutput(true);
                int responseCode=connection.getResponseCode();
                if (responseCode == HttpsURLConnection.HTTP_OK)
                {
                    String line;
                    BufferedReader br=new BufferedReader(new
                            InputStreamReader(connection.getInputStream()));
                    while ((line=br.readLine()) != null)
                    {
                        response+=line;
                    }
                }
                else
                {
                    response="";
                }
                connection.getResponseCode();
                if (connection.getResponseCode() == 200)
                {
                    // Success
                    Log.println(Log.ASSERT,"Resultado", "Registro modificado:"+response);
                    connection.disconnect();
                }
                else
                {
                    // Error handling code goes here
                    Log.println(Log.ASSERT,"Error", "Error");
                }
            }
            catch(Exception e)
            {
                Log.println(Log.ASSERT,"Excepción", e.getMessage());
            }
            return null;

        }
    }
}