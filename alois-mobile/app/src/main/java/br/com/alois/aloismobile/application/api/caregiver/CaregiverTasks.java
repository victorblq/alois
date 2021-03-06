package br.com.alois.aloismobile.application.api.caregiver;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.List;

import br.com.alois.aloismobile.application.preference.GeneralPreferences_;
import br.com.alois.aloismobile.application.preference.ServerConfiguration;
import br.com.alois.aloismobile.ui.view.home.AdministratorHomeActivity;
import br.com.alois.api.jackson.JacksonDecoder;
import br.com.alois.api.jackson.JacksonEncoder;
import br.com.alois.domain.entity.user.Caregiver;
import feign.Feign;
import feign.FeignException;

/**
 * Created by sarah on 3/29/17.
 */
@EBean
public class CaregiverTasks
{
    @Pref
    GeneralPreferences_ generalPreferences;

    @RootContext
    AdministratorHomeActivity administratorHomeActivity;

    @Background
    public void getCaregiverList()
    {
        CaregiverClient caregiverClient = Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(CaregiverClient.class, ServerConfiguration.API_ENDPOINT);

        try
        {
            getCaregiverListHandleSucess( caregiverClient.getCaregiverList( this.generalPreferences.loggedUserAuthToken().get() ) );
        }
        catch(FeignException e)
        {
            e.printStackTrace();
        }
    }

    @UiThread
    public void getCaregiverListHandleSucess(List<Caregiver> caregiverList)
    {
        this.administratorHomeActivity.progressDialog.dismiss();
        this.administratorHomeActivity.setCaregiverList(caregiverList);
    }

    @Background
    public void addCaregiver(Caregiver caregiver)
    {
        CaregiverClient caregiverClient = Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(CaregiverClient.class, ServerConfiguration.API_ENDPOINT);

        try
        {
            addCaregiverHandleSucces(caregiverClient.addCaregiver(caregiver, this.generalPreferences.loggedUserAuthToken().get()));
        }
        catch (FeignException e)
        {
            e.printStackTrace();
        }
    }

    @UiThread
    public void addCaregiverHandleSucces(Caregiver caregiver)
    {
        this.administratorHomeActivity.progressDialog.dismiss();
        this.administratorHomeActivity.onAddCaregiver(caregiver);
        this.administratorHomeActivity.getSupportFragmentManager().popBackStack();
    }

    @Background
    public void deleteCaregiver(Caregiver caregiver)
    {
        CaregiverClient caregiverClient = Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(CaregiverClient.class, ServerConfiguration.API_ENDPOINT);

        try
        {
            caregiverClient.deleteCaregiver(caregiver.getId(), this.generalPreferences.loggedUserAuthToken().get());
            deleteCaregiverHandleSucces(caregiver);
        }
        catch (FeignException e)
        {
            e.printStackTrace();
        }
    }

    @UiThread
    public void deleteCaregiverHandleSucces(Caregiver caregiver)
    {
        this.administratorHomeActivity.progressDialog.dismiss();
        this.administratorHomeActivity.onDeleteCaregiver(caregiver);
    }

    @Background
    public void editCaregiver(Caregiver caregiver)
    {
        CaregiverClient caregiverClient = Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(CaregiverClient.class, ServerConfiguration.API_ENDPOINT);

        try
        {
            this.editCaregiverHandleSucces(caregiverClient.updateCaregiver(caregiver, this.generalPreferences.loggedUserAuthToken().get()));
        }
        catch (FeignException e)
        {
            e.printStackTrace();
        }
    }

    @UiThread
    public void editCaregiverHandleSucces(Caregiver caregiver)
    {
        this.administratorHomeActivity.progressDialog.dismiss();
        this.administratorHomeActivity.getSupportFragmentManager().popBackStack();
        this.administratorHomeActivity.onUpdateCaregiver(caregiver);
    }
}
