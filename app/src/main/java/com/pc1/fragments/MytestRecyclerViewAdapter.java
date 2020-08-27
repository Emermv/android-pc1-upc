package com.pc1.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;


import com.pc1.R;
import com.pc1.entity.Book;
import com.pc1.fragments.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link com.pc1.fragments.Home.OnFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MytestRecyclerViewAdapter extends RecyclerView.Adapter<MytestRecyclerViewAdapter.ViewHolder> {

    private final List<Book> mValues;
    private final Home.OnFragmentInteractionListener mListener;

    public MytestRecyclerViewAdapter(List<Book> items, Home.OnFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Book b=mValues.get(position);
        holder.mItem = b;
        holder.autor.setText(b.getAutor());
        holder.price.setText("S/ "+String.valueOf(b.getPrice()));
        holder.name.setText(b.getName());


        holder.destroy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final com.pc1.store.Book bs=new com.pc1.store.Book(v.getContext());
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage("¿Está seguro(a) que desea eliminar "+b.getName()+"?")
                        .setTitle("Alerta");
                builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        bs.delete(b.getId());
                        if (null != mListener) {
                            // Notify the active callbacks interface (the activity, if the
                            // fragment is attached to one) that an item has been selected.
                            mListener.onListFragmentInteraction(holder.mItem);
                        }
                    }
                });
                builder.setNegativeButton("Cerrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(null!=mListener){
                   mListener.onEditBook(b.getId());
               }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView name,autor,price;
        public Book mItem;
        public  final ImageButton destroy,edit;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            name = view.findViewById(R.id.name);
            autor = view.findViewById(R.id.autor);
            price = view.findViewById(R.id.price);
            destroy=view.findViewById(R.id.delete_btn);
            edit=view.findViewById(R.id.edit_btn);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + name.getText() + "'";
        }
    }
}
