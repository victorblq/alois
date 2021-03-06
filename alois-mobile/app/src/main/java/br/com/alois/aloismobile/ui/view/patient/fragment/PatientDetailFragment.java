package br.com.alois.aloismobile.ui.view.patient.fragment;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;

import br.com.alois.aloismobile.R;
import br.com.alois.aloismobile.ui.view.maps.fragment.PatientDetailMapFragment;
import br.com.alois.aloismobile.ui.view.maps.fragment.PatientDetailMapFragment_;
import br.com.alois.domain.entity.user.Patient;

/**
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.fragment_patient_detail)
public class PatientDetailFragment extends Fragment
{
    //=====================================ATTRIBUTES=======================================
    @ViewById(R.id.patientDetailPhone)
    TextView patientDetailPhone;

    @ViewById(R.id.patientDetailGender)
    TextView patientDetailGender;

    @ViewById(R.id.patientDetailDateOfBirth)
    TextView patientDetailDateOfBirth;

    @ViewById(R.id.patientDetailAddress)
    TextView patientDetailAddress;

    @ViewById(R.id.patientDetailEmergenyPhone)
    TextView patientDetailEmergencyPhone;

    @ViewById(R.id.patientDetailNote)
    TextView patientDetailNote;

    @ViewById(R.id.patientDetailMapFrame)
    FrameLayout patientDetailMapFrame;

    //======================================================================================

    //=====================================INJECTIONS=======================================
    @FragmentArg("patient")
    Patient patient;

    //======================================================================================

    //====================================CONSTRUCTORS======================================
    public PatientDetailFragment()
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
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        this.patientDetailPhone.setText(this.patient.getPhone());

        int genderStringId = 0;
        switch (this.patient.getGender())
        {
            case MALE:
                genderStringId = R.string.MALE;
                break;
            case FEMALE:
                genderStringId = R.string.FEMALE;
                break;
        }
        this.patientDetailGender.setText(this.getActivity().getResources().getString(genderStringId));

        this.patientDetailDateOfBirth.setText(simpleDateFormat.format(this.patient.getBirthDate().getTime()));
        this.patientDetailAddress.setText(this.patient.getAddress());
        this.patientDetailEmergencyPhone.setText(this.patient.getEmergencyPhone());
        this.patientDetailNote.setText(this.patient.getNote());

        PatientDetailMapFragment patientDetailMapFragment = PatientDetailMapFragment_
                .builder()
                .patient(this.patient)
                .build();

        this.getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(this.patientDetailMapFrame.getId(), patientDetailMapFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();

    }

    //======================================================================================

}
