package com.pc1.fragments;

import android.app.Activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.pc1.R;
import com.pc1.store.Book;


import static android.content.Intent.EXTRA_LOCAL_ONLY;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Form.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Form#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Form extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "mode";
    private int PICKFILE_REQUEST_CODE=20;

    // TODO: Rename and change types of parameters
    private int id=-1;

    private OnFragmentInteractionListener mListener;
   EditText name,autor,price,stock,description;
   RadioGroup type;
  CheckBox has_igv;
    public Form() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param book_id Parameter 1.
     * @return A new instance of fragment Form.
     */
    // TODO: Rename and change types and number of parameters
    public static Form newInstance(int book_id) {
        Form fragment = new Form();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, book_id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getInt(ARG_PARAM1);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_form, container, false);
        /*Spinner spinner = (Spinner) view.findViewById(R.id.category);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                        R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i("LIZ",position+"");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
        name=view.findViewById(R.id.name);
        autor=view.findViewById(R.id.autor);
        price=view.findViewById(R.id.price);
        stock=view.findViewById(R.id.stock);

        description=view.findViewById(R.id.description);
        type=view.findViewById(R.id.type);
        has_igv=view.findViewById(R.id.has_igv);
        final Button save=view.findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
        final Button back=view.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
        has_igv.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                price.setText(String.valueOf(calcPrice(isChecked)));
            }
        });
        if(id>0){
            onEdit();
            getActivity().setTitle("Book Shop / Edit");
        }else{
            getActivity().setTitle("Book Shop / Add");
        }

        return view;
    }
    private float calcPrice(boolean isChecked){
        float p=0;
        float mp=Float.parseFloat(price.getText().toString());
        if(isChecked){
            p= (float) (mp/1.18);
        }else{
            p=(float)(mp*1.18);
        }
        return p;
    }
private void back(){
    FragmentManager fragmentManager =getActivity().getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    Home fragment = new Home();
    fragmentTransaction.replace(R.id.container, fragment);
    fragmentTransaction.commit();
}
    private void onEdit() {
       Book b=new Book(getContext());
        com.pc1.entity.Book book=b.get(id);
        name.setText(book.getName());
        autor.setText(book.getAutor());
        description.setText(book.getDescription());
        price.setText(String.valueOf(book.getPrice()));
        stock.setText(String.valueOf(book.getStock()));
       has_igv.setChecked(book.getHas_igv()==1);
        if(book.getType()==1){
           type.check(R.id.journal);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {

        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    public void save(){
        int  ok=0;
        if(name.getText().toString().isEmpty()){
            name.setError("Ingrese el nombre");

        }else{
           ok++;
        }
        if(autor.getText().toString().isEmpty()){
            autor.setError("Ingrese el autor");
        }else{
            ok++;
        }
        if(price.getText().toString().isEmpty()){
            price.setError("Ingrese un precio");
        }else{
            ok++;
        }
        if(stock.getText().toString().isEmpty()){
            stock.setError("Ingrese el stock inicial");
        }else{
            ok++;
        }

        Book book=new Book(getContext());
        ContentValues values=new ContentValues();
        values.put("name",name.getText().toString());
        values.put("autor",autor.getText().toString());
        values.put("price",calcPrice(has_igv.isChecked()));
        values.put("stock",stock.getText().toString());
        values.put("has_igv",has_igv.isChecked()?1:0);
        values.put("description",description.getText().toString());

        if(type.getCheckedRadioButtonId()==R.id.book){
            values.put("type",0);
        }else{
            values.put("type",1);
        }
        if(ok==4){
            if(id>0){
               book.update(values,id);
            }else{
                book.insert(values);
            }
            back();
        }else{
            Snackbar.make(getView(), "Completa los campos requeridos", Snackbar.LENGTH_LONG)
                        .setAction("Aceptar", null).show();
        }


    }
}
