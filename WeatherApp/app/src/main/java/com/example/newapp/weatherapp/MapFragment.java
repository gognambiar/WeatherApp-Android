package com.example.newapp.weatherapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MapFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int PLACE_PICKER_REQUEST = 1;
    private static final String TAG = "MyActivity";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onMapFragmentInteraction(uri);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Button button = (Button) getView().findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openmap();
            }
        });

    }

    public void openmap(){
        try {
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            //Context context = this.getActivity().getApplicationContext();
            startActivityForResult(builder.build(this.getActivity()), PLACE_PICKER_REQUEST);
        }
        catch (GooglePlayServicesNotAvailableException | GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == getActivity().RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this.getActivity());
                String toastMsg = String.format("Place: %s", place.getAddress());
                Toast.makeText(this.getActivity(), toastMsg, Toast.LENGTH_LONG).show();
                getWeatherDetails(place.getLatLng().toString().replaceAll("lat/lng: ","").replaceAll(",","%2C"),toastMsg);
            }
        }
    }

    public void getWeatherDetails(String locmsg,final String locad)
    {
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = getString(R.string.yahoo_api1) + locmsg +getString(R.string.yahoo_api2);
        Log.v(TAG,url);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.v(TAG,"Response: " + response.toString());
                        WeatherValueSet(response.toString(),locad);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                });
                queue.add(jsObjRequest);
    }

    public void WeatherValueSet(String det,String adrr){

        try{
            JSONObject js = new JSONObject(det);
            JSONArray ln = js.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("item").getJSONArray("forecast");
            TextView tx1 = (TextView)getView().findViewById(R.id.textView14);
            TextView tx2 = (TextView)getView().findViewById(R.id.textView12);
            TextView tx3 = (TextView)getView().findViewById(R.id.textView10);
            TextView tx4 = (TextView)getView().findViewById(R.id.textView22);
            TextView tx5 = (TextView)getView().findViewById(R.id.textView24);
            TextView tx6 = (TextView)getView().findViewById(R.id.textView26);
            TextView tx7 = (TextView)getView().findViewById(R.id.textView34);
            TextView tx8 = (TextView)getView().findViewById(R.id.textView36);
            TextView tx9 = (TextView)getView().findViewById(R.id.textView38);
            TextView tx10 = (TextView)getView().findViewById(R.id.textView40);
            TextView tx11 = (TextView)getView().findViewById(R.id.textView42);
            TextView tx12 = (TextView)getView().findViewById(R.id.textView44);
            TextView tx13 = (TextView)getView().findViewById(R.id.textView46);
            TextView tx14 = (TextView)getView().findViewById(R.id.textView48);
            TextView tx15 = (TextView)getView().findViewById(R.id.textView50);
            TextView tx16 = (TextView)getView().findViewById(R.id.textView2);
            tx16.setText(adrr);
            tx1.setText(ln.getJSONObject(0).getString("date"));
            tx2.setText(ln.getJSONObject(0).getString("high"));
            tx3.setText(ln.getJSONObject(0).getString("low"));
            tx4.setText(ln.getJSONObject(1).getString("date"));
            tx5.setText(ln.getJSONObject(1).getString("high"));
            tx6.setText(ln.getJSONObject(1).getString("low"));
            tx7.setText(ln.getJSONObject(2).getString("date"));
            tx8.setText(ln.getJSONObject(2).getString("high"));
            tx9.setText(ln.getJSONObject(2).getString("low"));
            tx10.setText(ln.getJSONObject(3).getString("date"));
            tx11.setText(ln.getJSONObject(3).getString("high"));
            tx12.setText(ln.getJSONObject(3).getString("low"));
            tx13.setText(ln.getJSONObject(4).getString("date"));
            tx14.setText(ln.getJSONObject(4).getString("high"));
            tx15.setText(ln.getJSONObject(4).getString("low"));
            Log.v(TAG, ln.toString());
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onMapFragmentInteraction(Uri uri);
    }
}
