package com.example.cbscomputer.appcomparateur;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import static android.widget.ArrayAdapter.createFromResource;

public class AutomobileFragment extends Fragment {
    private TextInputLayout layout_vv, layout_poids, layout_date, layout_nbPlaces, layout_prixCD;
    private TextInputEditText valeur_vehicule, poids_totalVeh, dateView, nbPlace, prix_RadioCd;
    private Spinner genre, puissance, usage, wilaya, type_vehicule;
    private CheckBox age, permis, mat_inf, protec_jurid, assist_auto, bris_de_glace, system_alarm, vol_inc;
    private Button btn_calculer;
    private RadioButton valv, lim1, lim2, lim3,lim4, lim5, turboOui, turboNon, viv_origine, viv_prop, tp, sm,
                        dasc_100, dasc_200, dasc_300, dasc_500;
    private Spinner  tonnage_chargeUtile_nbPlaque;
    private RadioGroup radioGroup_turbo, radioGroup_DC, radioGroup_VIV, radioGroupTransport, radioGroup_DASC;
    private TextView nb_tonnes, charge_utile, text_turbo;
    private boolean spinnerShowed = false, turbo_showed = false, transportShowed = false;
    int age_vehicule = 0;
    double primeNetteCASH = 0.00, primeNetteSAA = 0.00;
    private double devis_automobileCASH = 0.00, devis_automobileSAA = 0.00;
    double primeRC = 0.00;
    double PrimeRCNet = 0.00;
    boolean codeTarifExist = false;
    long valeur_veh = 0;
    double poids_vehicule = 0.00;
    String code_genre = null, code_usage = null, code_puissance = null, code_zone = null;
    String val_puissance = null;
    Calendar calendar;
    int year, month, day;
    int dateActuelle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_automobile, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // on peut implementer des boutons s'il y en a comme dans la méthode onCreate dans une activité
        super.onViewCreated(view, savedInstanceState);
        //DATE Actuelle
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        //TextInputLayout
        layout_vv = (TextInputLayout) view.findViewById(R.id.layout_vv);
        layout_poids = (TextInputLayout) view.findViewById(R.id.layout_poids);
        layout_date = (TextInputLayout) view.findViewById(R.id.layout_date);
        layout_nbPlaces = (TextInputLayout) view.findViewById(R.id.layut_nbPlace);
        layout_prixCD = (TextInputLayout) view.findViewById(R.id.layout_prixCD);

        //champs de saisi
        nbPlace = (TextInputEditText) view.findViewById(R.id.nbre_place);
        nbPlace.addTextChangedListener(new MyTextWatcher(nbPlace));

        valeur_vehicule = (TextInputEditText) view.findViewById(R.id.valeur_vehicule);
        valeur_vehicule.addTextChangedListener(new MyTextWatcher(valeur_vehicule));

        poids_totalVeh = (TextInputEditText) view.findViewById(R.id.poids_total);
        poids_totalVeh.addTextChangedListener(new MyTextWatcher(poids_totalVeh));

        prix_RadioCd = (TextInputEditText) view.findViewById(R.id.viv_prop_prix);
        prix_RadioCd.addTextChangedListener(new MyTextWatcher(prix_RadioCd));

        dateView = (TextInputEditText) view.findViewById(R.id.dateView);
        dateView.addTextChangedListener(new MyTextWatcher(dateView));

        //Chekbox majorations
        age = (CheckBox) view.findViewById(R.id.age);
        permis = (CheckBox) view.findViewById(R.id.permis);
        mat_inf =(CheckBox) view.findViewById(R.id.mat_inf);

        //Checkbox les garanties facultatives
        bris_de_glace = (CheckBox) view.findViewById(R.id.bdg);
        protec_jurid = (CheckBox) view.findViewById(R.id.protection_juridique);
        assist_auto = (CheckBox) view.findViewById(R.id.aa);
        vol_inc = (CheckBox) view.findViewById(R.id.vol_inc);

        //Systeme d'alarme
        system_alarm = (CheckBox) view.findViewById(R.id.sys_alarme);

        //Turbo
        radioGroup_turbo = view.findViewById(R.id.rg_turbo);

        //Boutton RADIO pour la garantie Facultative DASC
        radioGroup_DASC = (RadioGroup) view.findViewById(R.id.dascGroup);
        dasc_100 = (RadioButton) view.findViewById(R.id.dasc100);
        dasc_200 = (RadioButton) view.findViewById(R.id.dasc200);
        dasc_300 = (RadioButton) view.findViewById(R.id.dasc300);
        dasc_500 = (RadioButton) view.findViewById(R.id.dasc500);

        // Bouton RADIO pour la garantie Facultative Dommage Collision
        radioGroup_DC = (RadioGroup) view.findViewById(R.id.dommage_collision);
        valv = (RadioButton) view.findViewById(R.id.valv);
        lim1 = (RadioButton) view.findViewById(R.id.lim1);
        lim2 = (RadioButton) view.findViewById(R.id.lim2);
        lim3 = (RadioButton) view.findViewById(R.id.lim3);
        lim4 = (RadioButton) view.findViewById(R.id.lim4);
        lim5 = (RadioButton) view.findViewById(R.id.lim5);

        //la garantie facultative VOL & INCENDIE
        radioGroup_VIV = (RadioGroup) view.findViewById(R.id.rg_VIV);
        viv_origine = (RadioButton) view.findViewById(R.id.viv_origine);
        viv_prop = (RadioButton) view.findViewById(R.id.viv_prop);

        //type de vehicule Spinner
        type_vehicule = view.findViewById(R.id.type_vehicule);
        // puissance spinner
        puissance = view.findViewById(R.id.puissance);
        //Spinner genre
        genre = view.findViewById(R.id.genre);
        //Spinner wilaya
        wilaya = view.findViewById(R.id.wilaya);
        //Spinner usage
        usage = view.findViewById(R.id.usage);

        //spinner Tonnage, ChargeUtile, Nombre de plaques
        tonnage_chargeUtile_nbPlaque = view.findViewById(R.id.tonnage_chargeUtile);

        type_vehicule.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        implementGenre();
                        ArrayAdapter<CharSequence> adapterGenre = createFromResource(getActivity(), R.array.genre_vehiculeTourism,
                                android.R.layout.simple_spinner_item);
                        adapterGenre.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        genre.setAdapter(adapterGenre);
                        break;

                    case 1:
                        implementGenre();
                        adapterGenre = createFromResource(getActivity(), R.array.genre_camion,
                                android.R.layout.simple_spinner_item);
                        adapterGenre.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        genre.setAdapter(adapterGenre);
                        break;

                    case 2:
                        implementGenre();
                        adapterGenre = createFromResource(getActivity(), R.array.genre_vehiculUtilitr,
                                android.R.layout.simple_spinner_item);
                        adapterGenre.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        genre.setAdapter(adapterGenre);
                        break;

                    case 3:
                        implementGenre();
                        adapterGenre = createFromResource(getActivity(), R.array.genre_transportPersonnes,
                                android.R.layout.simple_spinner_item);
                        adapterGenre.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        genre.setAdapter(adapterGenre);
                        break;

                    case 4:
                        implementGenre();
                        adapterGenre = createFromResource(getActivity(), R.array.genre_vehParavecRemorque,
                                android.R.layout.simple_spinner_item);
                        adapterGenre.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        genre.setAdapter(adapterGenre);
                        break;

                    case 5:
                        implementGenre();
                        adapterGenre = createFromResource(getActivity(), R.array.genre_tanspMarch,
                                android.R.layout.simple_spinner_item);
                        adapterGenre.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        genre.setAdapter(adapterGenre);
                        break;

                    case 6:
                        implementGenre();
                        adapterGenre = createFromResource(getActivity(), R.array.genre_VS,
                                android.R.layout.simple_spinner_item);
                        adapterGenre.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        genre.setAdapter(adapterGenre);
                        break;

                    case 7:
                        implementGenre();
                        adapterGenre = createFromResource(getActivity(), R.array.genre_remorque,
                                android.R.layout.simple_spinner_item);
                        adapterGenre.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        genre.setAdapter(adapterGenre);
                        break;
                    case 8:
                        implementGenre();
                        adapterGenre = createFromResource(getActivity(), R.array.genre_moto,
                                android.R.layout.simple_spinner_item);
                        adapterGenre.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        genre.setAdapter(adapterGenre);
                        break;

                    case 9:
                        implementGenre();
                        adapterGenre = createFromResource(getActivity(), R.array.genre_engins,
                                android.R.layout.simple_spinner_item);
                        adapterGenre.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        genre.setAdapter(adapterGenre);
                        break;

                    default:
                        adapterGenre = createFromResource(getActivity(), R.array.genre,
                                android.R.layout.simple_spinner_item);
                        adapterGenre.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        genre.setAdapter(adapterGenre);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        // type vehicule
        ArrayAdapter<CharSequence> adapterTypeVeh = ArrayAdapter.createFromResource(getActivity(), R.array.type_vehicules,
                android.R.layout.simple_spinner_item);
        adapterTypeVeh.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type_vehicule.setAdapter(adapterTypeVeh);


        //wilaya
        wilaya.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) getActivity());
        ArrayAdapter<CharSequence> adapter_wilaya = createFromResource(getActivity(), R.array.wilaya,
                android.R.layout.simple_spinner_item);
        adapter_wilaya.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        wilaya.setAdapter(adapter_wilaya);

        //Radio group pour la GF dommage collision
        radioGroup_DC.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.valv) {
                    valv.setTextColor(Color.parseColor("#ff9800"));
                    lim1.setTextColor(Color.BLACK);
                    lim2.setTextColor(Color.BLACK);
                    lim3.setTextColor(Color.BLACK);
                    lim4.setTextColor(Color.BLACK);
                    lim5.setTextColor(Color.BLACK);
                }
                if (checkedId == R.id.lim1) {
                    lim1.setTextColor(Color.parseColor("#ff9800"));
                    valv.setTextColor(Color.BLACK);
                    lim2.setTextColor(Color.BLACK);
                    lim3.setTextColor(Color.BLACK);
                    lim4.setTextColor(Color.BLACK);
                    lim5.setTextColor(Color.BLACK);
                }
                if (checkedId == R.id.lim2) {
                    lim2.setTextColor(Color.parseColor("#ff9800"));
                    valv.setTextColor(Color.BLACK);
                    lim1.setTextColor(Color.BLACK);
                    lim3.setTextColor(Color.BLACK);
                    lim5.setTextColor(Color.BLACK);
                }
                if (checkedId == R.id.lim3) {
                    lim3.setTextColor(Color.parseColor("#ff9800"));
                    valv.setTextColor(Color.BLACK);
                    lim2.setTextColor(Color.BLACK);
                    lim1.setTextColor(Color.BLACK);
                    lim4.setTextColor(Color.BLACK);
                    lim5.setTextColor(Color.BLACK);
                }
                if (checkedId == R.id.lim4) {
                    lim4.setTextColor(Color.parseColor("#ff9800"));
                    valv.setTextColor(Color.BLACK);
                    lim2.setTextColor(Color.BLACK);
                    lim3.setTextColor(Color.BLACK);
                    lim1.setTextColor(Color.BLACK);
                    lim5.setTextColor(Color.BLACK);
                }

                if (checkedId == R.id.lim5) {
                    lim5.setTextColor(Color.parseColor("#ff9800"));
                    valv.setTextColor(Color.BLACK);
                    lim2.setTextColor(Color.BLACK);
                    lim3.setTextColor(Color.BLACK);
                    lim4.setTextColor(Color.BLACK);
                    lim1.setTextColor(Color.BLACK);
                }
            }
        });

        // RADIO GROUP DASC
        radioGroup_DASC.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.dasc100) {
                    dasc_100.setTextColor(Color.parseColor("#ff9800"));
                    dasc_200.setTextColor(Color.BLACK);
                    dasc_300.setTextColor(Color.BLACK);
                    dasc_500.setTextColor(Color.BLACK);
                }
                if (checkedId == R.id.dasc200) {
                    dasc_200.setTextColor(Color.parseColor("#ff9800"));
                    dasc_100.setTextColor(Color.BLACK);
                    dasc_300.setTextColor(Color.BLACK);
                    dasc_500.setTextColor(Color.BLACK);
                }
                if (checkedId == R.id.dasc300) {
                    dasc_300.setTextColor(Color.parseColor("#ff9800"));
                    dasc_100.setTextColor(Color.BLACK);
                    dasc_200.setTextColor(Color.BLACK);
                    dasc_500.setTextColor(Color.BLACK);
                }
                if (checkedId == R.id.dasc500) {
                    dasc_500.setTextColor(Color.parseColor("#ff9800"));
                    dasc_100.setTextColor(Color.BLACK);
                    dasc_200.setTextColor(Color.BLACK);
                    dasc_300.setTextColor(Color.BLACK);
                }

            }
        });

        //bouttons Radio pour Turbo
        turboOui = (RadioButton) view.findViewById(R.id.oui);
        turboNon = (RadioButton) view.findViewById(R.id.non);
        //Radio Group Turbo
         radioGroup_turbo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
             @Override
             public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                 if (checkedId == R.id.oui){
                     turboOui.setTextColor(Color.parseColor("#ff9800"));
                     turboNon.setTextColor(Color.BLACK);
                 }
                 if (checkedId == R.id.non){
                     turboNon.setTextColor(Color.parseColor("#ff9800"));
                     turboOui.setTextColor(Color.BLACK);
                 }
             }
         });

         // Radio Group Vol & Incendie
        radioGroup_VIV.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if (checkedId == R.id.viv_origine){
                    viv_origine.setTextColor(Color.parseColor("#ff9800"));
                    viv_prop.setTextColor(Color.BLACK);
                }
                if (checkedId == R.id.viv_prop){
                    viv_prop.setTextColor(Color.parseColor("#ff9800"));
                    viv_origine.setTextColor(Color.BLACK);
                }
            }
        });

         //reccuperer les Text view
        nb_tonnes = (TextView) view.findViewById(R.id.nb_tonnes);
        charge_utile = (TextView) view.findViewById(R.id.charge_utile);
        text_turbo = (TextView) view.findViewById(R.id.turbo_groupRadio);

        //Radio Group Transport
        radioGroupTransport = (RadioGroup) view.findViewById(R.id.tran_gr);
        tp = (RadioButton) view.findViewById(R.id.tp);
        sm = (RadioButton) view.findViewById(R.id.sm);
        radioGroupTransport.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if (checkedId == R.id.tp) {
                    tp.setTextColor(Color.parseColor("#ff9800"));
                    sm.setTextColor(Color.BLACK);
                }
                if (checkedId == R.id.sm){
                    sm.setTextColor(Color.parseColor("#ff9800"));
                    tp.setTextColor(Color.BLACK);
                }
            }
        });

        // le BOUTON pour CALCULER Le DEVIS AUTOMOBILE
        btn_calculer = view.findViewById(R.id.calcul_auto);
        calculerDevis();

    }

    // implementer le bouton calculer Devis
    private void calculerDevis() {
        btn_calculer.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                //récuperer la valeur du véhicule
                String date_mise_ciculation = null;

                try {
                    date_mise_ciculation = dateView.getText().toString();
                    valeur_veh = Long.valueOf(valeur_vehicule.getText().toString());
                    poids_vehicule = Double.parseDouble(poids_totalVeh.getText().toString());
                    dateActuelle = (int) Integer.parseInt(date_mise_ciculation);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

                age_vehicule = year - dateActuelle;

                // récuperer les spinners
                String val_genre = genre.getSelectedItem().toString();
                val_puissance = puissance.getSelectedItem().toString();
                String val_usage = usage.getSelectedItem().toString();
                String val_wialaya = wilaya.getSelectedItem().toString();

                //affectation du code genre
                if (val_genre.equals("Véhicules particulier sans remorques")) code_genre = "00";
                if (val_genre.equals("Remorque de véhicule particulier")) code_genre = "01";
                if (val_genre.equals("Motocycliste sans side cars jusqu’à 125")) code_genre = "02";
                if (val_genre.equals("S/Side cars- Tricycles – Triporteurs")) code_genre = "03";
                if (val_genre.equals("TPM dont la charge utile n’excède pas 2T sans transport de matière inflammable"))
                    code_genre = "04";
                if (val_genre.equals("Cyclomoteurs jusqu’au 50 CM3")) code_genre = "05";
                if (val_genre.equals("Scooters jusqu’au 125 CM3")) code_genre = "06";
                if (val_genre.equals("Scooters jusqu’au 175 CM3")) code_genre = "07";
                if (val_genre.equals("Triporteurs – Tricycles jusqu’au 125 CM3")) code_genre = "08";
                if (val_genre.equals("Vélomoteurs sans side cars jusqu’au 125 CM3"))
                    code_genre = "09";
                if (val_genre.equals("Voitures d’ambulance")) code_genre = "10";
                if (val_genre.equals("Voitures utilisées uniquement à l’occasion des défiles ou exhibition"))
                    code_genre = "11";
                if (val_genre.equals("Corbillards  et fourgons funéraires")) code_genre = "12";
                if (val_genre.equals("Chasse Neige (Prime Forf, Non reductible)"))
                    code_genre = "13";
                if (val_genre.equals("Camions et bennes utilises uniquement pour l’enlèvement des ordures ménagères"))
                    code_genre = "14";
                if (val_genre.equals("Camions salubrités (vidange)")) code_genre = "15";
                if (val_genre.equals("Tracteurs et camions appartenant aux entreprises ou exploitant d’attraction foraine ambulante"))
                    code_genre = "16";
                if (val_genre.equals("Véhicules moteurs utilisées indépendamment de la remorque comping"))
                    code_genre = "17";
                if (val_genre.equals("Véhicules de sapeurs-pompiers utilisés par le service incendie"))
                    code_genre = "18";
                if (val_genre.equals("Véhicules particuliers attelés d’une remorque"))
                    code_genre = "19";
                if (val_genre.equals("Véhicules dont le poids total excède 3,5 tonnes"))
                    code_genre = "30";
                if (val_genre.equals("Remorques de plus de 3,5 tonnes")) code_genre = "31";
                if (val_genre.equals("TPM dont la charge utile excède 2 TONNES sans transport de matières inflammables"))
                    code_genre = "32";
                if (val_genre.equals("Remorques Transport Public de Marchandises")) code_genre = "33";
                if (val_genre.equals("Transport public de voyageurs")) code_genre = "34";
                //if (val_genre.equals("Transport du personnel")) code_genre = "34 bis";
                if (val_genre.equals("Tracteurs routier non porteur attelé d’une semi-remorque"))
                    code_genre = "35";
                if (val_genre.equals("Tracteurs routier non porteur")) code_genre = "36";
                if (val_genre.equals("Tracteurs forestiers")) code_genre = "38";
                if (val_genre.equals("Engins de chantiers matériels de travaux publics avec utilisation sur la voie publique"))
                    code_genre = "45";
                if (val_genre.equals("Engins de chantiers matériels de travaux publics sans utilisation sur la voie publique"))
                    code_genre = "46";
                if (val_genre.equals("Garagistes automobile")) code_genre = "47";
                if (val_genre.equals("Garagistes cyclomoteurs")) code_genre = "48";
                if (val_genre.equals("Dépannage")) code_genre = "49";

                //affectation du code puissance selon code genre = 02 ou 03
                if (val_puissance.equals("2 C.V")) code_puissance = "0";
                if (val_puissance.equals("3 C.V")) code_puissance = "1";
                if (val_puissance.equals("4 C.V") | val_puissance.equals("5 A 6 C.V")) code_puissance = "2";
                if (val_puissance.equals("5 C.V") | val_puissance.equals("7 A 10 C.V")) code_puissance = "3";
                if (val_puissance.equals("6 C.V") | val_puissance.equals("11 A 14 C.V")) code_puissance = "4";
                if (val_puissance.equals("7 C.V ET PLUS") | val_puissance.equals("15 A 23 C.V")) code_puissance = "5";
                if (val_puissance.equals("24 C.V ET PLUS")) code_puissance = "6";
                if (val_puissance.equals("50 cm CUBE")) code_puissance = "7";
                if (val_puissance.equals("125 cm CUBE")) code_puissance = "8";
                if (val_puissance.equals("175 cm CUBE")) code_puissance = "9";

                //affectation du code usage
                if (val_usage.equals("AFFAIRE")) code_usage = "0";
                if (val_usage.equals("FONCTIONNAIRE")) code_usage = "1";
                if (val_usage.equals("COMMERCE")) code_usage = "2";
                if (val_usage.equals("AUTO ECOLE - TAXI- LOCATION")) code_usage = "3";
                if (val_usage.equals("COMMERCE C. bis")) code_usage = "4";
                if (val_usage.equals("TRANSPORT PUBLIC DE MARCHANDISES (T.P.M)")) code_usage = "5";
                if (val_usage.equals("TRANSPORT PUBLIC DE VOYAGEURS (T.PV)")) code_usage = "6";
                if (val_usage.equals("VEHICULES SPECIAUX")) code_usage = "7";
                if (val_usage.equals("DEPANNAGE")) code_usage = "8";
                if (val_usage.equals("TRANSPORT DU PERSONNEL")) code_usage = "9";


                //affectation du code wilaya
                String Nord [] = {"CHLEF - 02", "BATNA - 05", "BLIDA - 09", "TEBESSA - 12", "TIARET - 14", "ALGER - 16", "JIJEL - 18",
                        "SAIDA - 20", "SI DI BEL ABBES - 22", "GUELMA - 24","MEDEA - 26","MSILA - 28", "ORAN - 31",
                        "BOUMERDES - 35", "TISSEMSILT - 38", "SOUKAHRAS - 41", "MILA - 43", "AIN TI MOUCHENT - 46", "OUM EL BOUAGHI - 04", "BEJAIA - 06",
                "BOUIRA - 10", "TLEMCEN - 13", "TIZIOUZOU - 15", "BEJAIA - 06", "SETIF - 19", "SKIKDA - 21", "ANNABA - 23",
                        "CONSTANTINE - 25", "MOSTAGANEM - 27", "MASCARA - 29", "BORDJ BOU ARRERIDJ - 34", "EL TARF - 36",
                        "KHENCHELA - 40", "TIPAZA - 42", "AIN DEFLA - 44", "RELIZANE - 48"};
                String Sud [] = {"ADRAR - 01", "BISKRA - 07", "TAMANRASSET - 11", "EL BAYADH - 32", "TINDOUF - 37", "NAAMA - 45",
                        "LAGHOUAT - 03", "BECHAR - 08", "OUARGLA - 30", "ILLIZI - 33", "EL OUED - 39", "GHARDAIA - 47"};

                for (int i = 0; i < Nord.length; i ++){
                    if (val_wialaya.equals(Nord[i]))
                        code_zone = "1";
                }

                for (int j = 0; j < Sud.length; j ++){
                    if (val_wialaya.equals(Sud[j]))
                        code_zone = "2";
                }

                //Créer une HashMap pour stocker les Tarifs Automobile
                HashMap<String, Double> tarifs = new HashMap<String, Double>();
                // vehicules particuliers sans remorque sans Turbo
                tarifs.put("00100", 1045.31);
                tarifs.put("00101", 1495.20);
                tarifs.put("00102", 1711.99);
                tarifs.put("00103",1933.91);
                tarifs.put("00104", 3624.30);
                tarifs.put("00105", 3892.38);
                tarifs.put("00106", 4073.48);
                tarifs.put("00110", 784.06);
                tarifs.put("00111", 1121.39);
                tarifs.put("00112", 1284.12);
                tarifs.put("00113", 1450.43);
                tarifs.put("00114", 2718.19);
                tarifs.put("00115", 2919.26);
                tarifs.put("00116", 3055.39);
                tarifs.put("00120", 1165.40);
                tarifs.put("00121", 1560.12);
                tarifs.put("00122", 1776.90);
                tarifs.put("00123", 1960.75);
                tarifs.put("00124", 2399.47);
                tarifs.put("00125", 2568.30);
                tarifs.put("00126", 2681.52);
                tarifs.put("00200", 721.07);
                tarifs.put("00201", 1049.78);
                tarifs.put("00202", 1264.02);
                tarifs.put("00203", 1472.81);
                tarifs.put("00204", 2883.46);
                tarifs.put("00205", 3175.61);
                tarifs.put("00206", 3365.92);
                tarifs.put("00210", 540.71);
                tarifs.put("00211", 787.25);
                tarifs.put("00212", 948.10);
                tarifs.put("00213", 1104.78);
                tarifs.put("00214", 2162.58);
                tarifs.put("00215", 2381.57);
                tarifs.put("00216", 2524.33);
                tarifs.put("00220", 783.41);
                tarifs.put("00221", 1112.46);
                tarifs.put("00222", 1321.22);
                tarifs.put("00223", 1561.72);
                tarifs.put("00224", 1953.41);
                tarifs.put("00225", 2105.29);
                tarifs.put("00226", 2225.21);
                tarifs.put("00230", 1081.75);
                tarifs.put("00231", 1574.83);
                tarifs.put("00232", 1896.18);
                tarifs.put("00233", 2209.22);
                tarifs.put("00234", 2703.24);
                tarifs.put("00235", 2976.97);
                tarifs.put("00236", 3155.72);
                tarifs.put("00130", 1568.11);
                tarifs.put("00131", 2242.80);
                tarifs.put("00132", 2567.98);
                tarifs.put("00133", 2900.84);
                tarifs.put("00134", 3397.76);
                tarifs.put("00135", 3649.10);
                tarifs.put("00136", 3818.89); // fin vehicules particuliers sans remorques sans turbo
                tarifs.put("02110", 874.87);
                tarifs.put("02111", 1092.61);
                tarifs.put("02112", 1329.58);
                tarifs.put("02113", 1554.04);
                tarifs.put("02114", 1766.04);
                tarifs.put("02115", 1964.59);
                tarifs.put("02120", 1266.25);
                tarifs.put("02121", 1601.99);
                tarifs.put("02122", 1950.20);
                tarifs.put("02123", 2279.24);
                tarifs.put("02124", 2589.73);
                tarifs.put("02125", 2880.72);
                tarifs.put("02200", 926.66);
                tarifs.put("02201", 1191.41);
                tarifs.put("02202", 1415.90);
                tarifs.put("02203", 1663.38);
                tarifs.put("02204", 1887.86);
                tarifs.put("02205", 2095.07);
                tarifs.put("02210", 702.19);
                tarifs.put("02211", 894.05);
                tarifs.put("02212", 1061.93);
                tarifs.put("02213", 1248.04);
                tarifs.put("02214", 1415.90);
                tarifs.put("02215", 1571.32);
                tarifs.put("02220", 1019.40);
                tarifs.put("02221", 1310.38);
                tarifs.put("02222", 1557.55);
                tarifs.put("02223", 1830.00);
                tarifs.put("02224", 2076.84);
                tarifs.put("02225", 2304.85);
                tarifs.put("02100", 1151.14);
                tarifs.put("02101", 1456.19);
                tarifs.put("02102", 1902.25);
                tarifs.put("02103", 2072.04);
                tarifs.put("02104", 2354.08);
                tarifs.put("02105", 2618.83);
                tarifs.put("02110", 874.87);
                tarifs.put("02111", 1092.61);
                tarifs.put("02112", 1329.58);
                tarifs.put("02113", 1554.04);
                tarifs.put("02114", 1766.04);
                tarifs.put("02115", 1964.59);
                tarifs.put("02120", 1266.25);
                tarifs.put("02121", 1601.99);
                tarifs.put("02122", 1950.20);
                tarifs.put("02123", 2279.24);
                tarifs.put("02124", 2589.73);
                tarifs.put("02125", 2880.72);
                tarifs.put("02200", 926.66);
                tarifs.put("02201", 1191.41);
                tarifs.put("02202", 1415.90);
                tarifs.put("02203", 1663.38);
                tarifs.put("02204", 1887.86);
                tarifs.put("02205", 2095.07);
                tarifs.put("02200", 926.66);
                tarifs.put("02201", 1191.41);
                tarifs.put("02202", 1415.90);
                tarifs.put("02203", 1663.38);
                tarifs.put("02204", 1887.86);
                tarifs.put("02205", 2095.07);
                tarifs.put("02210", 702.19);
                tarifs.put("02211", 894.05);
                tarifs.put("02212", 1061.93);
                tarifs.put("02213", 1248.04);
                tarifs.put("02214", 1415.90);
                tarifs.put("02215", 1571.32);
                tarifs.put("02220", 1019.40);
                tarifs.put("02221", 1310.38);
                tarifs.put("02222", 1557.55);
                tarifs.put("02223", 1830.00);
                tarifs.put("02224", 2076.84);
                tarifs.put("02225", 2304.85);
                tarifs.put("03100", 947.99);
                tarifs.put("03101", 1250.90);
                tarifs.put("03102", 1561.72);
                tarifs.put("03103", 1895.54);
                tarifs.put("03104", 2202.52);
                tarifs.put("03105", 2413.54);
                tarifs.put("03120", 1042.42);
                tarifs.put("03121", 1375.94);
                tarifs.put("03122", 1717.74);
                tarifs.put("03123", 2085.16);
                tarifs.put("03124", 2422.81);
                tarifs.put("03125", 2654.99);
                tarifs.put("03110", 711.17);
                tarifs.put("03111", 938.17);
                tarifs.put("03112", 1171.61);
                tarifs.put("03113", 1421.65);
                tarifs.put("03114", 1651.99);
                tarifs.put("03115", 1810.48);
                tarifs.put("03200", 748.25);
                tarifs.put("03201", 997.64);
                tarifs.put("03202", 1250.90);
                tarifs.put("03203", 1500.30);
                tarifs.put("03204", 1757.40);
                tarifs.put("03205", 1999.14);
                tarifs.put("03220", 823.07);
                tarifs.put("03221", 1097.41);
                tarifs.put("03222", 1375.94);
                tarifs.put("03223", 1650.28);
                tarifs.put("03224", 1933.27);
                tarifs.put("03225", 2198.99);
                tarifs.put("03210", 561.50);
                tarifs.put("03211", 748.25);
                tarifs.put("03212", 938.17);
                tarifs.put("03213", 1125.55);
                tarifs.put("03214", 1318.03);
                tarifs.put("03215", 1499.66);
                tarifs.put("04250", 1437.64);
                tarifs.put("04251", 1971.95);
                tarifs.put("04252", 2279.24);
                tarifs.put("04253", 2654.65);
                tarifs.put("04254", 3212.95);
                tarifs.put("04255", 3461.09);
                tarifs.put("04256", 3644.59);
                tarifs.put("04150", 1437.64);
                tarifs.put("04151", 1971.95);
                tarifs.put("04152", 2279.24);
                tarifs.put("04153", 2654.65);
                tarifs.put("04154", 3212.95);
                tarifs.put("04155", 3461.09);
                tarifs.put("04256", 3644.59);
                tarifs.put("05107", 439.04);
                tarifs.put("05207", 439.04);
                tarifs.put("06108", 1093.57);
                tarifs.put("06208", 1093.57);
                tarifs.put("08108", 978.47);
                tarifs.put("08208", 771.26);
                tarifs.put("09108", 874.87);
                tarifs.put("09208", 702.19);
                tarifs.put("10170", 733.74);
                tarifs.put("10171", 1040.06);
                tarifs.put("10172", 1184.62);
                tarifs.put("10173", 1307.18);
                tarifs.put("10174", 1599.65);
                tarifs.put("10175", 1712.21);
                tarifs.put("10176", 1787.68);
                tarifs.put("10270", 522.28);
                tarifs.put("10271", 741.61);
                tarifs.put("10272", 880.84);
                tarifs.put("10273", 1041.13);
                tarifs.put("10274", 1302.26);
                tarifs.put("10275", 1403.30);
                tarifs.put("10276", 1483.46);
                tarifs.put("11170", 366.86);
                tarifs.put("11171", 520.02);
                tarifs.put("11172", 592.28);
                tarifs.put("11173", 653.58);
                tarifs.put("11174", 799.80);
                tarifs.put("11175", 856.10);
                tarifs.put("11176", 893.81);
                tarifs.put("11270", 261.12);
                tarifs.put("11271", 370.82);
                tarifs.put("11272", 440.40);
                tarifs.put("11273", 520.57);
                tarifs.put("11274", 651.12);
                tarifs.put("11275", 701.65);
                tarifs.put("11276", 741.73);
                tarifs.put("12170", 733.74);
                tarifs.put("12171", 1040.06);
                tarifs.put("12172", 1184.62);
                tarifs.put("12173", 1307.18);
                tarifs.put("12174", 1599.65);
                tarifs.put("12175", 1712.21);
                tarifs.put("12176", 1787.68);
                tarifs.put("12270", 522.28);
                tarifs.put("12271", 741.61);
                tarifs.put("12272", 880.84);
                tarifs.put("12273", 1041.13);
                tarifs.put("12274", 1302.26);
                tarifs.put("12275", 1403.30);
                tarifs.put("12276", 1483.46);
                tarifs.put("13170", 275.16);
                tarifs.put("13171", 390.04);
                tarifs.put("13172", 444.25);
                tarifs.put("13173", 490.20);
                tarifs.put("13174", 599.86);
                tarifs.put("13175", 642.08);
                tarifs.put("13176", 670.38);
                tarifs.put("14270", 391.73);
                tarifs.put("14271", 556.24);
                tarifs.put("14272", 660.62);
                tarifs.put("14273", 780.84);
                tarifs.put("14274", 976.70);
                tarifs.put("14275", 1052.51);
                tarifs.put("14276", 1112.62);
                tarifs.put("14170", 550.30);
                tarifs.put("14171", 780.06);
                tarifs.put("14172", 888.47);
                tarifs.put("14173", 980.38);
                tarifs.put("14174", 1199.75);
                tarifs.put("14175", 1284.14);
                tarifs.put("14176", 1340.75);
                tarifs.put("15170", 1100.60);
                tarifs.put("15171", 1560.12);
                tarifs.put("15172", 1776.90);
                tarifs.put("15173", 1960.75);
                tarifs.put("15174", 2399.47);
                tarifs.put("15175", 2568.30);
                tarifs.put("15176", 2681.52);
                tarifs.put("15270", 783.41);
                tarifs.put("15271", 1112.46);
                tarifs.put("15272", 1321.22);
                tarifs.put("15273", 1301.63);
                tarifs.put("15274", 1953.41);
                tarifs.put("15275", 2104.98);
                tarifs.put("15276", 2225.21);
                tarifs.put("16170", 550.30);
                tarifs.put("16171", 780.06);
                tarifs.put("16172", 888.47);
                tarifs.put("16173", 980.38);
                tarifs.put("16174", 1199.75);
                tarifs.put("16175", 1284.14);
                tarifs.put("16176", 1340.75);
                tarifs.put("17170", 1100.60);
                tarifs.put("17171", 1560.00);
                tarifs.put("17172", 1776.90);
                tarifs.put("17173", 1960.75);
                tarifs.put("17174", 2399.47);
                tarifs.put("17175", 2568.30);
                tarifs.put("17176", 2681.52);
                tarifs.put("17270", 783.41);
                tarifs.put("17271", 1112.46);
                tarifs.put("17272", 1321.22);
                tarifs.put("17273", 1561.72);
                tarifs.put("17274", 1953.41);
                tarifs.put("17275", 2104.98);
                tarifs.put("17276", 2225.21);
                tarifs.put("18170", 366.86);
                tarifs.put("18171", 520.02);
                tarifs.put("18172", 592.28);
                tarifs.put("18173", 653.58);
                tarifs.put("18174", 799.80);
                tarifs.put("18175", 856.10);
                tarifs.put("18176", 893.81);
                tarifs.put("18270", 261.12);
                tarifs.put("18271", 370.82);
                tarifs.put("18272", 440.40);
                tarifs.put("18273", 520.57);
                tarifs.put("18274", 651.12);
                tarifs.put("18275", 701.64);
                tarifs.put("18276", 741.73);
                tarifs.put("19100", 1149.86);
                tarifs.put("19101", 1644.84);
                tarifs.put("19102", 1883.32);
                tarifs.put("19103",2127.36);
                tarifs.put("19104", 3851.00);
                tarifs.put("19105", 4135.73);
                tarifs.put("19106", 4328.34);
                tarifs.put("19200", 794.63);
                tarifs.put("19201", 1155.00);
                tarifs.put("19202", 1371.47);
                tarifs.put("19203",1620.22);
                tarifs.put("19204", 3063.80);
                tarifs.put("19205", 3374.17);
                tarifs.put("19206", 3576.32);
                tarifs.put("19120", 1320.91);
                tarifs.put("19121", 1872.19);
                tarifs.put("19122", 2132.47);
                tarifs.put("19123", 2353.08);
                tarifs.put("19124", 2879.44);
                tarifs.put("19125", 3082.15);
                tarifs.put("19126", 3218.08);
                tarifs.put("19220", 940.09);
                tarifs.put("19221", 1335.01);
                tarifs.put("19222", 1585.66);
                tarifs.put("19223", 1874.12);
                tarifs.put("19224", 2344.14);
                tarifs.put("19225", 2526.10);
                tarifs.put("19226", 2670.30);
                tarifs.put("36170", 3056.892);
                tarifs.put("36270", 3056.892);
                tarifs.put("45170", 1023.23);
                tarifs.put("45270", 1023.23);
                tarifs.put("46170", 559.584);
                tarifs.put("46270", 559.584);

                //tonnage code tarif 30140 et 30240
                //Collection pour les véhicules dont le poids total excède 3.5 TONNES, selon nombre de tonnes
                HashMap<Integer, Double> tarifs_tonnageVehicule = new HashMap<Integer, Double>();
                tarifs_tonnageVehicule.put(4, 2558.06);
                tarifs_tonnageVehicule.put(5, 2757.912);
                tarifs_tonnageVehicule.put(6, 2957.76);
                tarifs_tonnageVehicule.put(7, 3157.608);
                tarifs_tonnageVehicule.put(8, 3357.456);
                tarifs_tonnageVehicule.put(9, 3557.304);
                tarifs_tonnageVehicule.put(10, 3757.152);
                tarifs_tonnageVehicule.put(11, 3957.00);
                tarifs_tonnageVehicule.put(12, 4156.848);
                tarifs_tonnageVehicule.put(13, 4356.696);
                tarifs_tonnageVehicule.put(14, 4556.544);
                tarifs_tonnageVehicule.put(15, 4756.392);
                tarifs_tonnageVehicule.put(16, 4956.24);
                tarifs_tonnageVehicule.put(17, 5156.088);
                tarifs_tonnageVehicule.put(18, 5355.936);
                tarifs_tonnageVehicule.put(19, 5555.784);
                tarifs_tonnageVehicule.put(20, 5755.632);
                tarifs_tonnageVehicule.put(21, 5955.48);
                tarifs_tonnageVehicule.put(22, 6155.328);
                tarifs_tonnageVehicule.put(23, 6355.176);
                tarifs_tonnageVehicule.put(24, 6555.024);
                tarifs_tonnageVehicule.put(25, 6754.872);

                //tonnage Remorque, code tarif 31140 et 31240
                //Collection pour les Remorques de plus de  3.5 TONNES, selon le nombre de tonnes
                HashMap<Integer, Double> tarifs_tonnageRemorque = new HashMap<Integer, Double>();
                tarifs_tonnageRemorque.put(4, 768.04);
                tarifs_tonnageRemorque.put(5, 828.14);
                tarifs_tonnageRemorque.put(6, 888.25);
                tarifs_tonnageRemorque.put(7, 948.36);
                tarifs_tonnageRemorque.put(8, 1008.47);
                tarifs_tonnageRemorque.put(9, 1068.58);
                tarifs_tonnageRemorque.put(10, 1128.68);
                tarifs_tonnageRemorque.put(11, 1188.79);
                tarifs_tonnageRemorque.put(12, 1248.90);
                tarifs_tonnageRemorque.put(13, 1309.01);
                tarifs_tonnageRemorque.put(14, 1369.12);
                tarifs_tonnageRemorque.put(15, 1429.22);
                tarifs_tonnageRemorque.put(16, 1489.33);
                tarifs_tonnageRemorque.put(17, 1549.44);
                tarifs_tonnageRemorque.put(18, 1609.55);
                tarifs_tonnageRemorque.put(19, 1669.66);
                tarifs_tonnageRemorque.put(20, 1729.76);
                tarifs_tonnageRemorque.put(21, 1789.87);
                tarifs_tonnageRemorque.put(22, 1849.98);
                tarifs_tonnageRemorque.put(23, 1910.09);
                tarifs_tonnageRemorque.put(24, 1970.20);
                tarifs_tonnageRemorque.put(25, 2030.30);

                //code tarif 32150 et 32250 à faire
                //TPM dont la charge utile excede 2 TONNES sans transport de matières inflammables
                HashMap<Integer, Double> tarifs_TPM = new HashMap<Integer, Double>();
                tarifs_TPM.put(30, 4447.93);
                tarifs_TPM.put(40, 4911.13);
                tarifs_TPM.put(50, 5374.69);
                tarifs_TPM.put(60, 5838.25);
                tarifs_TPM.put(70, 6301.81);
                tarifs_TPM.put(80, 6765.37);

                // code tarif 33150 et 33250
                //Remorque TPM, selon la charge en Quintal
                HashMap<Integer, Double> tarifs_remorqueTPM = new HashMap<Integer, Double>();
                tarifs_remorqueTPM.put(4, 498.792);
                tarifs_remorqueTPM.put(5, 545.148);
                tarifs_remorqueTPM.put(6, 591.504);
                tarifs_remorqueTPM.put(7, 637.86);
                tarifs_remorqueTPM.put(8, 684.216);
                tarifs_remorqueTPM.put(9, 730.572);
                tarifs_remorqueTPM.put(10, 776.928);
                tarifs_remorqueTPM.put(11, 823.284);
                tarifs_remorqueTPM.put(12, 869.64);
                tarifs_remorqueTPM.put(13, 915.996);
                tarifs_remorqueTPM.put(14, 962.352);
                tarifs_remorqueTPM.put(15, 1008.708);
                tarifs_remorqueTPM.put(16, 1055.064);
                tarifs_remorqueTPM.put(17, 1101.42);
                tarifs_remorqueTPM.put(18, 1147.776);
                tarifs_remorqueTPM.put(19, 1194.132);
                tarifs_remorqueTPM.put(20, 1240.488);
                tarifs_remorqueTPM.put(21, 1286.844);
                tarifs_remorqueTPM.put(22, 1333.2);
                tarifs_remorqueTPM.put(23, 1379.556);
                tarifs_remorqueTPM.put(24, 1425.912);
                tarifs_remorqueTPM.put(25, 1472.268);

                // code tarif 38170 et 38270
                //Tracteurs forestiers ,selon la charge en Quintal
                HashMap<Integer, Double> tarifs_tracteurForest = new HashMap<Integer, Double>();
                tarifs_tracteurForest.put(4, 1279.08);
                tarifs_tracteurForest.put(5, 1379.016);
                tarifs_tracteurForest.put(6, 1478.952);
                tarifs_tracteurForest.put(7, 1578.888);
                tarifs_tracteurForest.put(8, 1678.824);
                tarifs_tracteurForest.put(9, 1778.76);
                tarifs_tracteurForest.put(10, 1878.696);
                tarifs_tracteurForest.put(11, 1978.632);
                tarifs_tracteurForest.put(12, 2078.568);
                tarifs_tracteurForest.put(13, 2178.504);
                tarifs_tracteurForest.put(14, 2278.44);
                tarifs_tracteurForest.put(15, 2378.376);
                tarifs_tracteurForest.put(16, 2478.31);
                tarifs_tracteurForest.put(17, 2578.25);
                tarifs_tracteurForest.put(18, 2678.18);
                tarifs_tracteurForest.put(19, 2778.12);
                tarifs_tracteurForest.put(20, 2878.06);
                tarifs_tracteurForest.put(21, 2977.99);
                tarifs_tracteurForest.put(22, 3077.93);
                tarifs_tracteurForest.put(23, 3177.86);
                tarifs_tracteurForest.put(24, 3277.80);
                tarifs_tracteurForest.put(25, 3377.74);

                // véhicules particuliers sans remorques avec turbo
                HashMap<String, Double> tarifs_turbo = new HashMap<String, Double>();
                tarifs_turbo.put("00100", 1672.49);
                tarifs_turbo.put("00101", 2392.28);
                tarifs_turbo.put("00102", 2739.20);
                tarifs_turbo.put("00103", 3094.25);
                tarifs_turbo.put("00104", 5798.90);
                tarifs_turbo.put("00105", 6227.81);
                tarifs_turbo.put("00106", 6517.57);
                tarifs_turbo.put("00110", 1254.492);
                tarifs_turbo.put("00111", 1794.216);
                tarifs_turbo.put("00112", 2054.628);
                tarifs_turbo.put("00113", 2320.68);
                tarifs_turbo.put("00114", 4349.112);
                tarifs_turbo.put("00115", 4670.82);
                tarifs_turbo.put("00116", 4888.632);
                tarifs_turbo.put("00120", 1760.964);
                tarifs_turbo.put("00121", 2496.192);
                tarifs_turbo.put("00123", 2843.04);
                tarifs_turbo.put("00124", 3137.208);
                tarifs_turbo.put("00125", 3839.16);
                tarifs_turbo.put("00125", 4109.28); // dublication avec deux valeurs différentes ??
                tarifs_turbo.put("00126", 4290.432);
                tarifs_turbo.put("00220", 1253.448);
                tarifs_turbo.put("00221", 1779.936);
                tarifs_turbo.put("00222", 2113.956);
                tarifs_turbo.put("00223", 2498.748);
                tarifs_turbo.put("00224", 3125.448);
                tarifs_turbo.put("00225", 3368.472);
                tarifs_turbo.put("00226", 3560.328);
                tarifs_turbo.put("00210", 865.128);
                tarifs_turbo.put("00211", 1259.592);
                tarifs_turbo.put("00212", 1516.956);
                tarifs_turbo.put("00213", 1767.648);
                tarifs_turbo.put("00214", 3460.128);
                tarifs_turbo.put("00215", 3810.504);
                tarifs_turbo.put("00216", 4038.936);
                tarifs_turbo.put("00200", 1153.704);
                tarifs_turbo.put("00201", 1679.652);
                tarifs_turbo.put("00202", 2022.432);
                tarifs_turbo.put("00203", 2356.488);
                tarifs_turbo.put("00204", 4613.532);
                tarifs_turbo.put("00205", 5080.968);
                tarifs_turbo.put("00206", 5385.468);

                //Algorithme Calcul Prime RC dans les différentes collections
                //reccupérer le code Tarif à partir de la selection de l'utilisateur
                String code_tarifTape = code_genre+code_zone+code_usage+code_puissance;

                if (turbo_showed){
                    if (turboOui.isChecked()){
                        //Prime RC pour les vehicules sans remorque avec Turbo
                        for (Map.Entry mapTarifs : tarifs_turbo.entrySet()){
                            if (code_tarifTape.equals(mapTarifs.getKey().toString())){
                                primeRC = (double) mapTarifs.getValue() + (double) mapTarifs.getValue()*0.2 ;
                            }
                        }
                    }
                    else {
                        //Prime PC pour les vehicules de la collection tarifs
                        for (Map.Entry mapTarifs : tarifs.entrySet()){
                            if (code_tarifTape.equals(mapTarifs.getKey().toString())){
                                primeRC = (double) mapTarifs.getValue() + (double) mapTarifs.getValue()*0.2 ;
                            }
                        }
                    }
                }

                    //Rechercher dans les collections qui ont rapport avec Spinner tonnage_chargeUtile_nbPlaque
                if (spinnerShowed){
                        //PrimeRC pour les vehicules dont le poids total excede 3.5 TONNES
                        String val_spinnerTonnage = tonnage_chargeUtile_nbPlaque.getSelectedItem().toString();
                        if (code_tarifTape.equals("30140") | code_tarifTape.equals("30240")){
                            for (Map.Entry tonnageTarifs : tarifs_tonnageVehicule.entrySet()){
                                if (Integer.parseInt(val_spinnerTonnage) == (int)(tonnageTarifs.getKey())){
                                    primeRC = (double) tonnageTarifs.getValue() + (double) tonnageTarifs.getValue() * 0.2;
                                }
                            }
                        }
                        // primeRC pour les Remorques de plus de 3.5 TONNES
                        if (code_tarifTape.equals("31140") | code_tarifTape.equals("31240")){
                            for (Map.Entry tonnageTarifs : tarifs_tonnageRemorque.entrySet()){
                                if (Integer.parseInt(val_spinnerTonnage) == (int)(tonnageTarifs.getKey())){
                                    primeRC = (double) tonnageTarifs.getValue() + (double) tonnageTarifs.getValue() * 0.2;
                                }
                            }
                        }
                        //primeRC pour les remorques transport public de marhandises TPM
                        if (code_tarifTape.equals("33150") | code_tarifTape.equals("33250")){
                            for (Map.Entry tarifsTPM : tarifs_remorqueTPM.entrySet()){
                                if (Integer.parseInt(val_spinnerTonnage) == (int)(tarifsTPM.getKey())){
                                    primeRC = (double) tarifsTPM.getValue() + (double) tarifsTPM.getValue() * 0.2;
                                }
                            }
                        }
                        //primeRC pour les tracteurs forestiers
                        if (code_tarifTape.equals("38170") | code_tarifTape.equals("38270")){
                            for (Map.Entry tarifTracteur : tarifs_tracteurForest.entrySet()){
                                if (Integer.parseInt(val_spinnerTonnage) == (int)(tarifTracteur.getKey())){
                                    primeRC = (double) tarifTracteur.getValue() + (double) tarifTracteur.getValue() * 0.2;
                                }
                            }
                        }

                        //primeRC pour les TPM dont la charge utile excede 2 tonnes sans transport de matInflammables
                        if (code_tarifTape.equals("32150") | code_tarifTape.equals("32250")){
                            for (Map.Entry tarifTPM: tarifs_TPM.entrySet()){
                                if (Integer.parseInt(val_spinnerTonnage) == (int) (tarifTPM.getKey())){
                                    primeRC = (double) tarifTPM.getValue() + (double) tarifTPM.getValue()*0.2;
                                }
                            }
                        }
                }
                if (transportShowed){
                    if (code_tarifTape.equals("34160") | code_tarifTape.equals("34260") | code_tarifTape.equals("34120") | code_tarifTape.equals("34220")) {
                        double place = 0.00;
                        try {
                            place = Double.parseDouble(nbPlace.getText().toString());
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                        if (sm.isChecked()) primeRC = (double)(place* 306.429);
                        if (tp.isChecked()) primeRC = (double) (place* 372.1055);
                    }
                }

                //Appliquer les majorations sur la prime RC
                if (age.isChecked() | permis.isChecked() | mat_inf.isChecked()) {
                    if (age.isChecked()) PrimeRCNet = primeRC + primeRC * 0.15;

                    if ((age.isChecked() & permis.isChecked()) | permis.isChecked() | mat_inf.isChecked())
                        PrimeRCNet = primeRC + primeRC * 0.25;

                    if ((age.isChecked() & permis.isChecked() & mat_inf.isChecked()) |
                            (permis.isChecked() & mat_inf.isChecked())) {
                        PrimeRCNet = 2 * (primeRC + primeRC * 0.25);
                    } else if (mat_inf.isChecked() & age.isChecked()) {
                        PrimeRCNet = (primeRC + primeRC * 0.25) + (primeRC + primeRC * 0.15);
                    }

                } else PrimeRCNet = primeRC;

                PrimeRCNet = (double) Math.round(PrimeRCNet*100)/100;

                if (primeRC != 0.00 | code_genre.equals("34"))
                    codeTarifExist = true;


                if (codeTarifExist){
                    submitForm();
                    if (validateVV() & validateDate() & validatePoids()){
                        if (transportShowed) {
                            if ( validateNbPlace() | validatePrixCD()){
                                devisCASH();
                                devisSAA();
                                showCustomDialog();
                            }
                        } else {
                            devisCASH();
                            devisSAA();
                            showCustomDialog();
                        }
                    }
                }else // si le code tarif n'existe pas dans les colletions retourner false
                           Toast.makeText(getActivity(), "Veuillez refaire votre selection", Toast.LENGTH_LONG).show();
                    }
        });
    }

    //classe privée MyTextWatcher
    private class MyTextWatcher implements  TextWatcher{
        private View view;
        private MyTextWatcher(View v){
            this.view = v;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void afterTextChanged(Editable editable) {
            switch (view.getId()){
                case R.id.valeur_vehicule : validateVV();
                break;
                case R.id.poids_total : validatePoids();
                break;
                case R.id.dateView : validateDate();
                break;
                case R.id.nbre_place : validateNbPlace();
                break;
                case R.id.viv_prop_prix : validatePrixCD();
                break;
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    //methode pour gerer le champs de saisi valeur du vehicule
    private boolean validateVV (){
        if (valeur_vehicule.getText().toString().trim().isEmpty()){
            layout_vv.setError(getString(R.string.error_vv));
            requestFocus (valeur_vehicule);
            return false;
        } else {
            try {
                if (valeur_vehicule.length() >= 6) {
                    int prix = Integer.parseInt(valeur_vehicule.toString());
                    if (prix > 400000) {
                        valeur_vehicule.setError("valeur erronée!");
                        valeur_vehicule.setText("");
                    }
                } else valeur_vehicule.setError("valeur erronée!");
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            layout_vv.setErrorEnabled(false);
        }
        return true;
    }
    //methode pour gerer le champs de saisi Poids total du vehicule
    private boolean validatePoids(){
        if (poids_totalVeh.getText().toString().trim().isEmpty()) {
            layout_poids.setError(getString(R.string.error_poid));
            requestFocus(poids_totalVeh);}
            return false;
////        } else{
////            try {
////                if (poids_totalVeh.length() == 3){
////                    if (!(poids_vehicule != 00.00)){
////                        poids_totalVeh.setError("Poids erronnée");
////                        poids_totalVeh.setText("");
////                    }
////                }
////            } catch (Exception e) {
////                e.printStackTrace();
////            }
////            layout_poids.setErrorEnabled(false);
////        }
//        return true;
    }

    //methode pour gerer le champs de saisi date de mise en circulation
    private boolean validateDate() {
        if (dateView.getText().toString().trim().isEmpty()){
            layout_date.setError(getString(R.string.error_date));
            requestFocus (dateView);
            return false;
        } else {
            try {
                if (dateView.length()== 4) {
                    int date = Integer.parseInt(dateView.getText().toString());
                    if (!(date > 1980 && date < (year +1))) {
                        dateView.setError("date erronée");
                        dateView.setText("");
                    }
                } else  dateView.setError("date erronée");
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            layout_date.setErrorEnabled(false);
        }
        return true;
    }
    //methode pour gerer le champs de saisi Nombre de places
    private boolean validateNbPlace() {
        if (nbPlace.getText().toString().trim().isEmpty()) {
            layout_nbPlaces.setError(getString(R.string.error_place));
            requestFocus(nbPlace);
            return false;
        }else {
            try {
                int nb_place = Integer.parseInt(nbPlace.getText().toString());
                if ( (nb_place==0) || (nb_place > 100)) {
                    nbPlace.setError("le nombre de place doit être entre 4 et 100!");
                    nbPlace.setText("");
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            layout_nbPlaces.setErrorEnabled(false);
        }
        return true;
    }
    //methode pour gerer le champs de saisi Prix du poste Radio CD
    private boolean validatePrixCD() {
        if (prix_RadioCd.getText().toString().trim().isEmpty()) {
            layout_prixCD.setError(getString(R.string.error_prix));
            requestFocus(prix_RadioCd);
            return false;
        }else {
            try {
                int prix = 0;
                if (prix_RadioCd.length() == 5){
                    prix = Integer.parseInt(prix_RadioCd.getText().toString());
                    if (prix > 25000 | prix < 05000) {
                        prix_RadioCd.setError("le prix doit être < 25000 !");
                        prix_RadioCd.setText("");
                    }
                } else  prix_RadioCd.setError("valeur erronée");
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            layout_prixCD.setErrorEnabled(false);
        }
        return true;
    }
    private void requestFocus (View v){
        if (v.requestFocus()) {
            final int softInputStateAlwaysVisible = WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE;
        }
    }

    //methode pour valider le formulaire
    private void submitForm() {
        if (!validateVV()) {
            return;
        }
        if (!validatePoids()) {
            return;
        }
        if (!validateDate()) {
            return;
        }
        if (transportShowed){
            if (!validateNbPlace()) {
            return;
            }
        }
        /*if (!validatePrixCD()) {
                return;
        }*/

    }

    // methode implementer le Spinner genre
    private void implementGenre(){
        genre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (genre.getSelectedItem().toString().equals("Motocycliste sans side cars jusqu’à 125") |
                        genre.getSelectedItem().toString().equals("S/Side cars- Tricycles – Triporteurs")){
                    ArrayAdapter<CharSequence> adapter_puissance = ArrayAdapter.createFromResource(getActivity(), R.array.puissance_genre,
                            android.R.layout.simple_spinner_item);
                    adapter_puissance.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    puissance.setAdapter(adapter_puissance);
                }else {
                    ArrayAdapter<CharSequence> adapter_puissance = ArrayAdapter.createFromResource(getActivity(), R.array.puissance,
                            android.R.layout.simple_spinner_item);
                    adapter_puissance.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    puissance.setAdapter(adapter_puissance);
                }
                // Vehicules, Remorque dont le poids total excede 3.5 TONNES
                if (genre.getSelectedItem().toString().equals("Véhicules dont le poids total excède 3,5 tonnes") |
                        genre.getSelectedItem().toString().equals("Remorques de plus de 3,5 tonnes")){
                    usage.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) getActivity());
                    ArrayAdapter<CharSequence> adapter_usage = createFromResource(getActivity(), R.array.usage_30,
                            android.R.layout.simple_spinner_item);
                    adapter_usage.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    usage.setAdapter(adapter_usage);
                    //puissance vehciles dont le poids total excede 3.5 TONNES
                    ArrayAdapter<CharSequence> adapter_puissance = ArrayAdapter.createFromResource(getActivity(),
                            R.array.puissance_vehiculesPoidLourd, android.R.layout.simple_spinner_item);
                    adapter_puissance.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    puissance.setAdapter(adapter_puissance);
                    nb_tonnes.setVisibility(View.VISIBLE);
                } else {  // Vehicules, transport public de voyageurs
                    usage.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) getActivity());
                    ArrayAdapter<CharSequence> adapter_usage = createFromResource(getActivity(), R.array.usage,
                                android.R.layout.simple_spinner_item);
                    adapter_usage.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    usage.setAdapter(adapter_usage);
                    nb_tonnes.setVisibility(View.GONE);
                }

                //Remorque transport public de marchandise
                if (genre.getSelectedItem().toString().equals("Remorques Transport Public de Marchandises")){
                    usage.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) getActivity());
                    ArrayAdapter<CharSequence> adapter_usage = createFromResource(getActivity(), R.array.usage_33,
                            android.R.layout.simple_spinner_item);
                    adapter_usage.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    usage.setAdapter(adapter_usage);
                    //puissance vehciles dont le poids total excede 3.5 TONNES
                    ArrayAdapter<CharSequence> adapter_puissance = ArrayAdapter.createFromResource(getActivity(),
                            R.array.puissance_vehiculesPoidLourd, android.R.layout.simple_spinner_item);
                    adapter_puissance.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    puissance.setAdapter(adapter_puissance);
                }

                //Transport public de voyageurs
                if (genre.getSelectedItem().toString().equals("Transport public de voyageurs")){
                    usage.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) getActivity());
                    ArrayAdapter<CharSequence> adapter_usage = createFromResource(getActivity(), R.array.usage_34,
                            android.R.layout.simple_spinner_item);
                    adapter_usage.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    usage.setAdapter(adapter_usage);
                    radioGroupTransport.setVisibility(View.VISIBLE);
                    nbPlace.setVisibility(View.VISIBLE);
                    layout_nbPlaces.setVisibility(View.VISIBLE);
                    transportShowed = true;
                } else {
                    radioGroupTransport.setVisibility(View.GONE);
                    nbPlace.setVisibility(View.GONE);
                    layout_nbPlaces.setVisibility(View.GONE);
                }

                // Tracteur Forestier, Garagistes automobiles, Garagistes cyclomoteurs
                if (genre.getSelectedItem().toString().equals("Tracteurs forestiers")){
                    usage.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) getActivity());
                    ArrayAdapter<CharSequence> adapter_usage = createFromResource(getActivity(), R.array.usage_38,
                            android.R.layout.simple_spinner_item);
                    adapter_usage.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    usage.setAdapter(adapter_usage);
                    //puissance vehciles dont le poids total excede 3.5 TONNES
                    ArrayAdapter<CharSequence> adapter_puissance = ArrayAdapter.createFromResource(getActivity(),
                            R.array.puissance_vehiculesPoidLourd, android.R.layout.simple_spinner_item);
                    adapter_puissance.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    puissance.setAdapter(adapter_puissance);
                }
                //gerer le spinner  Nb de tonnes, charge utile
                if (genre.getSelectedItem().toString().equals("Véhicules dont le poids total excède 3,5 tonnes") |
                        genre.getSelectedItem().toString().equals("Remorques de plus de 3,5 tonnes") |
                        genre.getSelectedItem().toString().equals("Tracteurs forestiers") |
                        genre.getSelectedItem().toString().equals("Remorques Transport Public de Marchandises") |
                        genre.getSelectedItem().toString().equals("TPM dont la charge utile excède 2 TONNES sans transport de matières inflammables")){
                    tonnage_chargeUtile_nbPlaque.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) getActivity());
                    ArrayAdapter<CharSequence> adapter_tonnage = ArrayAdapter.createFromResource(getActivity(), R.array.tonnageChUtl,
                            android.R.layout.simple_spinner_item);
                    adapter_tonnage.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    tonnage_chargeUtile_nbPlaque.setAdapter(adapter_tonnage);
                    tonnage_chargeUtile_nbPlaque.setVisibility(View.VISIBLE);
                    spinnerShowed = true;
                }else
                    tonnage_chargeUtile_nbPlaque.setVisibility(View.GONE);

                //Text View charge utile pour les tracteurs foresriers et Remorques TPM
                if (genre.getSelectedItem().toString().equals("Tracteurs forestiers") |
                        genre.getSelectedItem().toString().equals("Remorques Transport Public de Marchandises") |
                        genre.getSelectedItem().toString().equals("TPM dont la charge utile excède 2 TONNES sans transport de matières inflammables")){
                    charge_utile.setVisibility(View.VISIBLE);
                }else  charge_utile.setVisibility(View.GONE);

                //Radio Group Turbo
                if (genre.getSelectedItem().toString().equals("Véhicules particulier sans remorques")){
                    radioGroup_turbo.setVisibility(View.VISIBLE);
                    text_turbo.setVisibility(View.VISIBLE);
                    turbo_showed = true;
                }else {
                    radioGroup_turbo.setVisibility(View.GONE);
                    text_turbo.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    // methode pour la boite de dialogue
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void showCustomDialog (){
        final Bundle bundle = new Bundle();
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View viewDialog = layoutInflater.inflate(R.layout.custom_dialog, null, false);
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setView(viewDialog);
        //alertDialog.setTitle("Comparez selon votre Budget!");
        alertDialog.setPositiveButton("Etude Immediate", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                ViewDevisFragment devis = new ViewDevisFragment();
                devis.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                transaction.replace(R.id.screen_area, devis);
                transaction.addToBackStack(null);
                transaction.commit();

            }});
        alertDialog.setNegativeButton("Exit", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        final AlertDialog alertCustomDialog = alertDialog.create();
        alertCustomDialog.show();
        //reccuperer le textView
        final TextView seekBarValue = (TextView) viewDialog.findViewById(R.id.seekValue);
        //reccuperer la SeekBar
        final SeekBar budget = (SeekBar) viewDialog.findViewById(R.id.seekBar);
        budget.setProgress(10000);
        budget.incrementProgressBy(10000);
        budget.setMax(600000);
        //OnClick sur Seek Bar
        budget.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                if (b) {
                    if (progress >= 0 && progress <= budget.getMax()) {
                        progress = progress / 10000;
                        progress = progress * 10000;
                        seekBarValue.setText(String.valueOf(progress));
                        budget.setSecondaryProgress(progress);
                        if (devis_automobileCASH < progress & devis_automobileSAA < progress) {
                            bundle.putString("primeRC", String.valueOf(PrimeRCNet));
                            bundle.putString("devisCASH", String.valueOf(devis_automobileCASH));
                            bundle.putString("devisSAA", String.valueOf(devis_automobileSAA));
                        }
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    //methode pour calculer le devis pour la CASH
    private double devisCASH(){
        double primeBDG = 0.00;
        double primeDASC = 0.00;
        double primeVIV = 0.00;
        double primeDC = 0.00;
        double primePJ = 0.00;
        double primeAA = 0.00;

        // BRIS DE GLACE
        primeBDG = 0.0015 * valeur_veh;
        if ((code_genre.equals("00")|code_genre.equals("19")) & (poids_vehicule <= 3.5)) {
            if(primeBDG < 1500.00 ) primeBDG =1500.00 ; else  primeBDG = primeBDG;
            if(primeBDG > 4000.00 ) primeBDG = 4000.00 ; else primeBDG = primeBDG;
        } else if ((code_genre.equals("04")|code_genre.equals("10")|
                    code_genre.equals("12")|code_genre.equals("13")| code_genre.equals("14")|
                    code_genre.equals("15")|code_genre.equals("16")|
                    code_genre.equals("18")|code_genre.equals("30")|
                    code_genre.equals("32")|code_genre.equals("34")|
                    code_genre.equals("46"))& (poids_vehicule > 3.5)) {
            if(primeBDG < 2000.00 ) primeBDG =2000.00 ;
            if(primeBDG > 6000.00 ) primeBDG = 6000.00 ;
            if (primeBDG > 2000 & primeBDG < 6000)  primeBDG = primeBDG ;
        }else primeBDG = 0.00 ;

        // PROTECTION JURIDIQUE
        if ((poids_vehicule <= 3.5) | code_genre.equals("00")) primePJ = 300;
        if (poids_vehicule > 3.5) primePJ = 500;
        if (code_genre.equals("02") | code_genre.equals("05") | code_genre.equals("06") | code_genre.equals("07")) primePJ = 200;
        if (code_usage.equals("03") | code_usage.equals("05")) primePJ = 400;

        //DASC
        if (((age_vehicule) < 8) & (!permis.isChecked())){
            primeDASC = (double)(valeur_veh*0.05);
        }

        //VOL ET INCENDIE
        if (vol_inc.isChecked()){
            primeVIV = valeur_veh*0.1;
            if (viv_origine.isChecked()) primeVIV = primeVIV;
            if (viv_prop.isChecked()) {
                int prixRadioCD = 0;
                try {
                    prixRadioCD = Integer.parseInt(prix_RadioCd.getText().toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                primeVIV = primeVIV + (prixRadioCD*0.0224);
            }
        }

        //DOMMAGE ET COLLISION
        if( age_vehicule < 15) {
            if (valv.isChecked()) primeDC = (double) valeur_veh * 0.025;
            if (lim1.isChecked()) primeDC = (double) PrimeRCNet * 1.5;
            if (lim2.isChecked()) primeDC = (double) PrimeRCNet * 2.00;
            if (lim3.isChecked()) primeDC = (double) PrimeRCNet * 2.5;
            if (lim5.isChecked()) primeDC = (double) PrimeRCNet * 3.00;
        }

        //ASSISTANCE AUTOMOBILE
        primeAA = 1200.00 ;

        primeNetteCASH = PrimeRCNet + primePJ;

        if (lim1.isChecked() | lim2.isChecked() | lim3.isChecked() | lim5.isChecked() | valv.isChecked())
            primeNetteCASH = primeNetteCASH + primeDC;

        if (bris_de_glace.isChecked() & (viv_origine.isChecked() | viv_prop.isChecked()))
            primeNetteCASH = primeNetteCASH + primeBDG + primeVIV;

        // tout risques
        if ((dasc_100.isChecked()|dasc_200.isChecked()|dasc_300.isChecked()|dasc_500.isChecked()))
            primeNetteCASH = PrimeRCNet + primeDASC + primeVIV;

        // assistance
        if (assist_auto.isChecked())
            primeNetteCASH = primeNetteCASH + primeAA;

        primeNetteCASH = (double) Math.round(primeNetteCASH*100)/100;
        devis_automobileCASH = primeNetteCASH;
        return devis_automobileCASH;
    }

    //methode pour calculer le devis pour la SAA
    private double devisSAA(){
        double primeBDG = 0.00;
        double primeDASC = 0.00;
        double primeVIV = 0.00;
        double primeDC = 0.00;
        double primePJ = 0.00;
        double primeAA = 0.00;
        double primeRachatV = 0.00;
        double primeTP = 0.00;

        // BRIS DE GLACE
        double taux = 1.7 * valeur_veh;
        if (code_genre.equals("00")) {
            if(taux <= 1500.00 ) primeBDG =1500.00 ;
            else primeBDG = taux;
        }
        if (code_genre.equals("34")){
            if (taux <= 4000) primeBDG = 4000;
            else primeBDG  = taux;
        }
        if (code_genre.equals("13") | code_genre.equals("14") | code_genre.equals("15") | code_genre.equals("16") |
                code_genre.equals("18") | code_genre.equals("30") | code_genre.equals("32")){
            if (taux <= 2500) primeBDG = 2500;
            else primeBDG = taux;
        } else primeBDG = 2000;

        // PROTECTION JURIDIQUE
        primePJ = 600;

        //DASC
        if (((age_vehicule) < 15) & (!permis.isChecked())){
            if (dasc_100.isChecked()) primeDASC = 0.04*valeur_veh; // BDG + PJ sont gratuites
            if (dasc_200.isChecked()) primeDASC = 28000.00;
            if (dasc_300.isChecked()) primeDASC = 31000.00;
            if (dasc_500.isChecked()) primeDASC = 35000.00;
        }

        //VOL ET INCENDIE
        if ((val_puissance.equals("2 C.V") | val_puissance.equals("3 C.V") | val_puissance.equals("5 A 6 C.V") |
                val_puissance.equals("7 A 10 C.V")) & (vol_inc.isChecked())){
                int prixRadioCD = 0;
                try {
                    prixRadioCD = Integer.parseInt(prix_RadioCd.getText().toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                primeVIV = valeur_veh*0.1 + (prixRadioCD*0.02);
            }
         else {
                    int prixRadioCD = 0;
                    try {
                        prixRadioCD = Integer.parseInt(prix_RadioCd.getText().toString());
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    primeVIV = valeur_veh*0.016 + (prixRadioCD*0.02);
        }

        //DOMMAGE ET COLLISION
        if( age_vehicule < 15) {
            if (valv.isChecked()) primeDC = (double) valeur_veh * 0.025;
            if (lim1.isChecked()) primeDC = (double) PrimeRCNet * 1.5;
            if (lim2.isChecked()) primeDC = (double) PrimeRCNet * 2.8;
            if (lim3.isChecked()) primeDC = (double) PrimeRCNet * 3.9;
            if (lim4.isChecked()) primeDC = (double) PrimeRCNet * 4.2;
            if (lim5.isChecked()) primeDC = (double) PrimeRCNet * 4.8;
        }

        //ASSISTANCE AUTOMOBILE
        primeAA = 1500.00 ;

        //Rachat et vetusté
        double tauxRV = 0.37*valeur_veh;
        if (age_vehicule < 15){
            if (tauxRV <= 2300) primeRachatV = 2300;
            else primeRachatV = tauxRV;
        }

        //Taux Preparateur
        if (code_genre.equals("00")) {
            double tauxTP = 0.006*valeur_veh;
            if (tauxTP <= 7000) primeTP = 7000;
            else primeTP = tauxTP;
        } else if ( code_genre.equals("13") | code_genre.equals("14") | code_genre.equals("15") | code_genre.equals("16") |
                code_genre.equals("18") | code_genre.equals("30") | code_genre.equals("32") | code_genre.equals("34")) {
            double tauxTP = 0.0095*valeur_veh;
            if (tauxTP <= 9000) primeTP = 9000;
            else primeTP = tauxTP;
        }


        //les Combinaisons
        primeNetteSAA = PrimeRCNet + primePJ;

        if (assist_auto.isChecked()) primeNetteSAA = primeNetteSAA + primeAA;

        if (bris_de_glace.isChecked()) primeNetteSAA = primeNetteSAA + primeBDG;

        if (vol_inc.isChecked()) primeNetteSAA = primeNetteSAA + primeVIV;

        if (lim1.isChecked() | lim2.isChecked() | lim3.isChecked() | lim4.isChecked() | lim5.isChecked() | valv.isChecked())
            primeNetteSAA = primeNetteSAA + primeDC;

        if (dasc_100.isChecked()) //BDG + PJ gratuites
            primeNetteSAA = PrimeRCNet + primeDASC + primeVIV + primeAA + primeTP + primeRachatV;

        if (dasc_200.isChecked()|dasc_300.isChecked()| dasc_500.isChecked())
            primeNetteSAA = PrimeRCNet + primePJ + primeDASC + primeVIV + primeBDG + primeAA ;

        primeNetteSAA = (double) Math.round(primeNetteSAA*100)/100;
        devis_automobileSAA = primeNetteSAA;
        return devis_automobileSAA;
    }


}
