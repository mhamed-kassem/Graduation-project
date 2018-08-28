package com.example.waleed.firebaseapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Sara Alkurdy on 17/06/18.
 */

public class dialog  {



   public static class DialogRestPass extends DialogFragment {
       Button buttonC;
       Button buttonO;
       EditText option;
       View.OnClickListener clickListener;

       public DialogRestPass getDialogRestPass(){
           return this;
       }
       public void setclickListener(View.OnClickListener clickListener){
           this.clickListener=clickListener;
       }

       @Override
       public Dialog onCreateDialog(Bundle savedInstanceState) {
           AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
           // Get the layout inflater
           LayoutInflater inflater = getActivity().getLayoutInflater();
           View view=inflater.inflate(R.layout.layout_restpass,null);
           // Inflate and set the layout for the dialog
           // Pass null as the parent view because its going in the dialog layout
           option=view.findViewById(R.id.option);
           builder.setView(view)
                   // Add action buttons
                   .setPositiveButton("Step1", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int id) {

                       }
                   })
                   .setNegativeButton("Cancel" ,new DialogInterface.OnClickListener() {
                       public void onClick(DialogInterface dialog, int id) {

                       }
                   });

           AlertDialog dialog = builder.create();
           dialog.show();
           buttonO = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
           buttonO.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   clickListener.onClick(null);
               }
           });

           buttonC = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
           buttonC.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   getDialog().cancel();
               }
           });

           return dialog;
       }
   }

}
