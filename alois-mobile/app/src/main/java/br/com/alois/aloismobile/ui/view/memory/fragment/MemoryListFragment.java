package br.com.alois.aloismobile.ui.view.memory.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.GridView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import br.com.alois.aloismobile.R;
import br.com.alois.aloismobile.ui.view.home.PatientHomeActivity;
import br.com.alois.aloismobile.ui.view.memory.adapter.MemoryListAdapter;
import br.com.alois.domain.entity.memory.Memory;
import br.com.alois.domain.entity.user.Patient;

/**
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.fragment_memory_list)
public class MemoryListFragment extends Fragment {
    //=====================================ATTRIBUTES=======================================
    @ViewById(R.id.memoryList)
    GridView memoryList;

    @FragmentArg("patient")
    Patient patient;

    //======================================================================================

    //=====================================INJECTIONS=======================================
    @Bean
    MemoryListAdapter memoryListAdapter;

    public MemoryDetailFragment memoryDetailFragment;
    //======================================================================================

    //====================================CONSTRUCTORS======================================
    public MemoryListFragment()
    {
        // Required empty public constructor
    }

    //======================================================================================

    //==================================GETTERS/SETTERS=====================================
    public void setPatientMemory(Memory memory)
    {
        this.memoryDetailFragment.setMemory(memory);
    }
    //======================================================================================

    //=====================================BEHAVIOUR========================================

    @AfterViews
    public void onAfterViews()
    {
        this.memoryList.setAdapter(this.memoryListAdapter);
        ((PatientHomeActivity) this.getActivity()).getMemoriesByPatientId(this.patient.getId());
    }

    public void setPatientMemoryList(List<Memory> patientMemoryList) {
        this.memoryListAdapter.setMemories(patientMemoryList);
    }

    @ItemClick(R.id.memoryList)
    public void onClickButtonMyMemories(Memory memory)
    {
        this.memoryDetailFragment = MemoryDetailFragment_.builder().memory(memory).build();

        this.getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.patient_home_frame_layout, memoryDetailFragment)
                .addToBackStack("memory_detail_fragment")
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    public void onInsertMemory(Memory memory)
    {
        this.memoryListAdapter.onInsertMemory(memory);
    }

    public void onUpdateMemory(Memory memory)
    {
        this.memoryListAdapter.onUpdateMemory(memory);
    }
    //======================================================================================

}
