package br.com.alois.aloismobile.ui.view.requests.fragment;


import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.widget.ListView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import br.com.alois.aloismobile.R;
import br.com.alois.aloismobile.ui.view.patient.PatientDetailActivity;
import br.com.alois.aloismobile.ui.view.requests.adapter.LogoffRequestListAdapter;
import br.com.alois.domain.entity.user.Patient;
import br.com.alois.domain.entity.user.Request;

/**
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.fragment_logoff_list)
public class LogoffRequestListFragment extends Fragment
{
    //=====================================ATTRIBUTES=======================================
    @ViewById(R.id.logoffRequestListView)
    ListView logoffRequestListView;

    //======================================================================================

    //=====================================INJECTIONS=======================================
    @FragmentArg("patient")
    Patient patient;

    @Bean
    LogoffRequestListAdapter logoffRequestListAdapter;

    private ProgressDialog progressDialog;

    //======================================================================================

    //====================================CONSTRUCTORS======================================
    public LogoffRequestListFragment()
    {
        // Required empty public constructor
    }

    //======================================================================================

    //==================================GETTERS/SETTERS=====================================

    //======================================================================================

    //=====================================BEHAVIOUR========================================
    @AfterViews
    public void onAfterViews()
    {
        this.logoffRequestListView.setAdapter(this.logoffRequestListAdapter);

        this.progressDialog = ProgressDialog.show(this.getActivity(),
                this.getResources().getString(R.string.loading_requests),
                this.getResources().getString(R.string.please_wait),
                true,
                true
        );

        ((PatientDetailActivity) this.getActivity()).listPatientLogoffRequests(this.patient);
    }

    public void setPatientLogoffRequests(List<Request> patientLogoffRequests)
    {
        this.progressDialog.dismiss();

        if(this.logoffRequestListAdapter == null)
        {
            this.logoffRequestListAdapter = new LogoffRequestListAdapter();
        }

        this.logoffRequestListAdapter.setLogoffRequests(patientLogoffRequests);
    }
    //======================================================================================
}
