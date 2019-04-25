package com.ptit.ptitplus;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class MarkFragment extends Fragment implements LoaderManager.LoaderCallbacks<String>, View.OnClickListener{

    private ArrayList<Mark> listMark;
    private final String TAG = "KhangHoa";
    private static final String CODE = "code";
    private static final String NAME = "name";
    private static final String SO_TC = "soTC";
    private static final String CC = "CC";
    private static final String KT = "KT";
    private static final String TH = "TH";
    private static final String BT = "BT";
    private static final String THI_1 = "Thi_1";
    private static final String THI_2 = "Thi_2";
    private static final String TK_NUM = "TK_num";
    private static final String TK_STRING = "TK_string";
    private static final String KY_HOC = "ky_hoc";
    private static final String MSV = "msv";
    private String ky;

    public MarkFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LoaderManager.getInstance(this).initLoader(0, null, this);
        Button buttonXemDiem = getView().findViewById(R.id.button_xemDiem);
        buttonXemDiem.setOnClickListener(this);
        Spinner spinner = getView().findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ky = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.ky, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (spinner!=null)
            spinner.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mark, container, false);
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int i, @Nullable Bundle bundle) {
        String msv = "";
        if (bundle!=null){
            msv = bundle.getString("msv");
        }
        return new MarkLoader(getActivity(), msv);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String s) {
        ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connMgr != null) {
            networkInfo = connMgr.getActiveNetworkInfo();
        }
        if (networkInfo == null || !networkInfo.isConnected()){
            Toast.makeText(getActivity(), "Please check your internet", Toast.LENGTH_SHORT).show();
            return;
        }
        listMark = new ArrayList<>();
        Log.d(TAG, s);
        try {
            String string;
            JSONArray jsonArray = new JSONArray(s);
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Mark mark = new Mark(jsonObject.getString(CODE),jsonObject.getString(NAME), jsonObject.getString(SO_TC),
                        jsonObject.getString(CC), jsonObject.getString(KT), jsonObject.getString(TH), jsonObject.getString(BT),
                        jsonObject.getString(THI_1), jsonObject.getString(THI_2), jsonObject.getString(TK_NUM), jsonObject.getString(TK_STRING),
                        jsonObject.getString(KY_HOC), jsonObject.getString(MSV));
                listMark.add(mark);
            }
            addRowIntoTable();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }

    public void addRowIntoTable(){
        TableLayout tableLayout = getView().findViewById(R.id.table_mark);
        for (int i = tableLayout.getChildCount()-1; i > 0; i--){
            tableLayout.removeViewAt(i);
        }
        for (int i = 0; i < listMark.size(); i++){
            if (!ky.equalsIgnoreCase("all")){
                Log.d(TAG, listMark.get(i).getKy_hoc());
                Log.d(TAG, listMark.get(i).getKy_hoc().length()+"");
                Log.d(TAG, ky);
                Log.d(TAG, ky.length()+"");
                if (!listMark.get(i).getKy_hoc().equalsIgnoreCase(ky)) {
                    Log.d(TAG, "ko bang nhau");
                    continue;
                }
            }
            TableRow tableRow = (TableRow) getLayoutInflater().inflate(R.layout.tablerow_template, null);
            TextView code = (TextView) getLayoutInflater().inflate(R.layout.textview_template2, null);
            code.setText(listMark.get(i).getCode());
            tableRow.addView(code);
            TextView cc = (TextView) getLayoutInflater().inflate(R.layout.textview_template, null);
            cc.setText(listMark.get(i).getCC());
            tableRow.addView(cc);
            TextView bt = (TextView) getLayoutInflater().inflate(R.layout.textview_template, null);
            bt.setText(listMark.get(i).getBT());
            tableRow.addView(bt);
            TextView kt = (TextView) getLayoutInflater().inflate(R.layout.textview_template, null);
            kt.setText(listMark.get(i).getKT());
            tableRow.addView(kt);
            TextView th = (TextView) getLayoutInflater().inflate(R.layout.textview_template, null);
            th.setText(listMark.get(i).getTH());
            tableRow.addView(th);
            TextView thi = (TextView) getLayoutInflater().inflate(R.layout.textview_template, null);
            thi.setText(listMark.get(i).getThi_1());
            tableRow.addView(thi);
            TextView tk_so = (TextView) getLayoutInflater().inflate(R.layout.textview_template, null);
            tk_so.setText(listMark.get(i).getTK_num());
            tableRow.addView(tk_so);
            TextView tk_chu = (TextView) getLayoutInflater().inflate(R.layout.textview_template, null);
            tk_chu.setText(listMark.get(i).getTK_string());
            tableRow.addView(tk_chu);
            tableLayout.addView(tableRow);
//            code.setPadding(10, 10, 10, 10);
        }
    }

    @Override
    public void onClick(View v) {
        InputMethodManager inputManager = (InputMethodManager)
                getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null) {
            inputManager.hideSoftInputFromWindow(v.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
        ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connMgr != null) {
            networkInfo = connMgr.getActiveNetworkInfo();
        }
        if (networkInfo == null || !networkInfo.isConnected()){
            Toast.makeText(getActivity(), "Please check your internet", Toast.LENGTH_SHORT).show();
            return;
        }
        Bundle bundle = new Bundle();
        String msv = null;
        EditText et = getView().findViewById(R.id.editText_msv);
        msv = et.getText().toString();
        if (msv.equalsIgnoreCase("")){
            Toast.makeText(getActivity(), "Nhập mã sinh viên", Toast.LENGTH_SHORT).show();
            return;
        }
        bundle.putString("msv", msv);
        LoaderManager.getInstance(this).restartLoader(0, bundle, this);
    }
}
