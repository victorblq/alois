package br.com.alois.aloismobile.ui.view.memory.fragment;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;

import br.com.alois.aloismobile.R;
import br.com.alois.aloismobile.ui.view.home.PatientHomeActivity;
import br.com.alois.aloismobile.ui.view.memory.adapter.MemoryListAdapter;
import br.com.alois.aloismobile.ui.view.patient.fragment.PatientFormFragment;
import br.com.alois.aloismobile.ui.view.patient.fragment.PatientFormFragment_;
import br.com.alois.domain.entity.memory.Memory;
import br.com.alois.domain.entity.user.Patient;

@EFragment(R.layout.fragment_memory_detail)
public class MemoryDetailFragment extends Fragment {
    //=====================================ATTRIBUTES=======================================
    @ViewById(R.id.memoryDetailTitleLabel)
    TextView memoryDetailTitle;

    @ViewById(R.id.memoryDetailDescriptionLabel)
    TextView memoryDetailDescription;

    @ViewById(R.id.memoryDetailCreationDateLabel)
    TextView memoryDetailCreationDate;

    @ViewById(R.id.memoryDetailImageView)
    ImageView memoryDetailImage;

    @ViewById(R.id.memoryDetailEditButton)
    Button memoryDetailEdit;

    @ViewById(R.id.memoryDetailRequestDelete)
    Button memoryDetailRequestDelete;

    @FragmentArg("memory")
    Memory memory;

    //======================================================================================

    //=====================================INJECTIONS=======================================
    //======================================================================================

    //====================================CONSTRUCTORS======================================
    public MemoryDetailFragment() {
        // Required empty public constructor
    }
    //======================================================================================

    //==================================GETTERS/SETTERS=====================================
    public void setMemory(Memory memory){
        this.memory = memory;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        this.memoryDetailCreationDate.setText(simpleDateFormat.format(this.memory.getCreationDate().getTime()));
        this.memoryDetailDescription.setText(this.memory.getDescription());
        this.memoryDetailTitle.setText(this.memory.getTitle());
        Bitmap bmp = BitmapFactory.decodeByteArray(this.memory.getFile(), 0, this.memory.getFile().length);
        this.memoryDetailImage.setImageBitmap(bmp);
    }

    //======================================================================================

    //=====================================BEHAVIOUR========================================
    @AfterViews
    public void onAfterViews()
    {
        ((PatientHomeActivity) this.getActivity()).getMemory(this.memory.getId());
    }

    @Click(R.id.memoryDetailEditButton)
    public void editMemory(){
        MemoryFormFragment memoryFormFragment = MemoryFormFragment_.builder()
                .memory(memory)
                .build();

        this.getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.patient_home_frame_layout, memoryFormFragment)
                .addToBackStack("memoryFormFragment")
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

}
