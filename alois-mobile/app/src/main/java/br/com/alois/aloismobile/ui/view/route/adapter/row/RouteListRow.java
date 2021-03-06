package br.com.alois.aloismobile.ui.view.route.adapter.row;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import br.com.alois.aloismobile.R;
import br.com.alois.aloismobile.application.preference.GeneralPreferences_;
import br.com.alois.aloismobile.ui.view.patient.PatientDetailActivity;
import br.com.alois.aloismobile.ui.view.route.fragment.RouteFormFragment;
import br.com.alois.aloismobile.ui.view.route.fragment.RouteFormFragment_;
import br.com.alois.domain.entity.route.Route;
import br.com.alois.domain.entity.user.UserType;

/**
 * Created by victor on 3/24/17.
 */
@EViewGroup(R.layout.route_list_row)
public class RouteListRow extends LinearLayout
{
    //=====================================ATTRIBUTES=======================================
    @ViewById(R.id.routeRowName)
    TextView routeRowName;

    @ViewById(R.id.routeRowMenuButton)
    ImageButton routeRowMenuButton;

    @Pref
    GeneralPreferences_ generalPreferences;

    Route route;
    private AppCompatActivity activity;

    //======================================================================================

    //=====================================INJECTIONS=======================================

    //======================================================================================

    //====================================CONSTRUCTORS======================================
    public RouteListRow(Context context)
    {
        super(context);
    }
    //======================================================================================

    //==================================GETTERS/SETTERS=====================================

    //======================================================================================

    //=====================================BEHAVIOUR========================================
    public void bind(Route route, AppCompatActivity activity)
    {
        this.routeRowName.setText(route.getName());
        this.route = route;

        if(this.generalPreferences.loggedUserType().get().equals(UserType.PATIENT.ordinal()))
        {
            this.routeRowMenuButton.setVisibility(INVISIBLE);
        }

        this.activity = activity;
    }

    @Click(R.id.routeRowMenuButton)
    public void onRouteRowMenuButtonClick()
    {
        PopupMenu routeRowPopupMenu = new PopupMenu(this.getContext(), this.routeRowMenuButton);
        routeRowPopupMenu.getMenuInflater().inflate(R.menu.route_list_pop_up_menu, routeRowPopupMenu.getMenu());

        routeRowPopupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
        {
            @Override
            public boolean onMenuItemClick(MenuItem item)
            {
                int id = item.getItemId();

                switch (id)
                {
                    case R.id.routeListMenuEditButton:
                        RouteFormFragment routeFormFragment = RouteFormFragment_.builder()
                                .patient(route.getPatient())
                                .route(route)
                                .build();

                        ((PatientDetailActivity) activity).setRouteFormFragment(routeFormFragment);

                        activity.getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.patientDetailFrame, routeFormFragment)
                                .addToBackStack("routeFormFragment")
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                .commit();
                        break;
                    case R.id.routeListMenuDeleteButton:
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity)
                                .setTitle(R.string.are_you_sure)
                                .setMessage(R.string.you_really_want_delete_route)
                                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        ((PatientDetailActivity) activity).deleteRoute(route);
                                    }
                                })
                                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        AlertDialog alert = alertDialog.create();
                        alert.show();
                        break;
                }

                return true;
            }
        });

        routeRowPopupMenu.show();
    }
    //======================================================================================

}
