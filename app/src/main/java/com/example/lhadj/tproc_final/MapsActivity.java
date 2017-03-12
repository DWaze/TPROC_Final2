package com.example.lhadj.tproc_final;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    int destination=-1;
    int source=-1;
    int i = 0;
    private List<LatLng> stepsR;
    TextView textSource,textDestination ;
    Button calc ;
    Point chuConstantine,citéBoussouf,citeZiadia,musée,khroub,uc2,uc3,centreCult,citeBkira,villeZy,aeroport;
    Marker mchuConstantine,mcitéBoussouf,mciteZiadia,mmusée,mkhroub,muc2,muc3,mcentreCult,mciteBkira,mvilleZy,maeroport;
    String distance ;
    Graph graph;
    PolylineOptions options;
    double srcLat,srcLang,destlatitude,destlongitude;
    ArrayList<Point> points;
    Polyline pol;
    List<Polyline> polylines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        points = new ArrayList<Point>() ;
        textSource = (TextView)findViewById(R.id.Source);
        textDestination = (TextView)findViewById(R.id.Destination);
        calc =(Button)findViewById(R.id.calc);
        polylines = new ArrayList<Polyline>();


        // creation des ville

        villeZy = new Point(new LatLng(36.5310259,6.7010097));
        points.add(villeZy);
        citeBkira = new Point(new LatLng(36.3963102,6.6015815));
        points.add(citeBkira);
        citeZiadia = new Point(new LatLng(36.3816343,6.6352734));
        points.add(citeZiadia);
        chuConstantine = new Point(new LatLng(36.3722633,6.6166127));
        points.add(chuConstantine);
        centreCult = new Point(new LatLng(36.3638029,6.6058859));
        points.add(centreCult);
        citéBoussouf = new Point(new LatLng(36.3291012,6.5685124));
        points.add(citéBoussouf);
        musée = new Point(new LatLng(36.3500403,6.6190393));
        points.add(musée);
        khroub = new Point(new LatLng(36.2580879,6.6908924));
        points.add(khroub);
        aeroport = new Point(new LatLng(36.2836628,6.6205437));
        points.add(aeroport);
        uc3 = new Point(new LatLng(36.2771468,6.5906132));
        points.add(uc3);
        uc2 = new Point(new LatLng(36.2449935,6.5664618));
        points.add(uc2);


        int V = 11;  // Number of vertices in graph
        int E = 21;  // Number of chemin in graph

        graph = new Graph(V, E);

        //creation des arch du graphe

        // 0 ---(21km)---> 1
        graph.chemin[0].src = 0;
        graph.chemin[0].dest = 1;
        graph.chemin[0].poid = 21;

        // 2 ---(27km)---> 0
        graph.chemin[1].src = 2;
        graph.chemin[1].dest = 0;
        graph.chemin[1].poid = 27;

        // 1 ---(7km)---> 3
        graph.chemin[2].src = 1;
        graph.chemin[2].dest = 3;
        graph.chemin[2].poid = 7;

        // 5 ---(17km)---> 1
        graph.chemin[3].src = 5;
        graph.chemin[3].dest = 1;
        graph.chemin[3].poid = 17;

        // 3 ---(3km)---> 2
        graph.chemin[4].src = 3;
        graph.chemin[4].dest = 2;
        graph.chemin[4].poid = 3;

        // 3 ---(3km)---> 4
        graph.chemin[5].src = 3;
        graph.chemin[5].dest = 4;
        graph.chemin[5].poid = 3;

        // 4 ---(6km)---> 5
        graph.chemin[6].src = 4;
        graph.chemin[6].dest = 5;
        graph.chemin[6].poid = 6;

        // 7 ---(17km)---> 2
        graph.chemin[7].src = 7;
        graph.chemin[7].dest = 2;
        graph.chemin[7].poid = 17;

        // 2 ---(7km)---> 6
        graph.chemin[8].src = 2;
        graph.chemin[8].dest = 6;
        graph.chemin[8].poid = 7;

        // 6 ---(5km)---> 3
        graph.chemin[9].src = 6;
        graph.chemin[9].dest = 3;
        graph.chemin[9].poid = 5;

        // 4 ---(4km)---> 6
        graph.chemin[10].src = 4;
        graph.chemin[10].dest = 6;
        graph.chemin[10].poid = 4;

        // 3 ---(19km)---> 9
        graph.chemin[11].src = 3;
        graph.chemin[11].dest = 9;
        graph.chemin[11].poid = 19;

        // 6 ---(5km)---> 5
        graph.chemin[12].src = 6;
        graph.chemin[12].dest = 5;
        graph.chemin[12].poid = 5;

        // 6 ---(14km)---> 7
        graph.chemin[13].src = 6;
        graph.chemin[13].dest = 7;
        graph.chemin[13].poid = 14;

        // 6 ---(9km)---> 8
        graph.chemin[14].src = 6;
        graph.chemin[14].dest = 8;
        graph.chemin[14].poid = 9;

        // 5 ---(13km)---> 9
        graph.chemin[15].src = 5;
        graph.chemin[15].dest = 9;
        graph.chemin[15].poid = 13;

        // 9 ---(5km)---> 10
        graph.chemin[16].src = 9;
        graph.chemin[16].dest = 10;
        graph.chemin[16].poid = 5;

        // 8 ---(14km)---> 10
        graph.chemin[17].src = 8;
        graph.chemin[17].dest = 10;
        graph.chemin[17].poid = 14;

        // 8 ---(17km)---> 7
        graph.chemin[18].src = 8;
        graph.chemin[18].dest = 7;
        graph.chemin[18].poid = 17;

        // 10 ---(16km)---> 7
        graph.chemin[19].src = 10;
        graph.chemin[19].dest = 7;
        graph.chemin[19].poid = 16;


        calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(source!=-1 && destination!=-1 && pol==null){
                    //running belman ford algorithme to get the shortest path to any destination
                    graph.BellmanFord(graph,source);
//                    options = new PolylineOptions().width(8).color(getBaseContext().getResources().getColor(R.color.selectColor)).geodesic(true);
                    int y;
                    y=destination;
                    ExecuteRoot executeRoot = new ExecuteRoot();
                    // drawing the graphe starting from the destination
                    while(y!=source){
                        LatLng source = points.get(y).getPoint();
//                        options.add(source);
                        y=graph.dist.get(y).src;
                        // using the Direction API
                        LatLng destination =points.get(y).getPoint();
                        executeRoot.execute(source,destination);
                    }
                }

            }
        });
    }


    public class ExecuteRoot extends GetJsonRouteData{


        public void execute(LatLng source,LatLng destination){
                StartRun run = new StartRun();
                run.execute("https://maps.googleapis.com/maps/api/directions/json?origin="+ source.latitude+","+source.longitude+"&destination="+destination.latitude+","+destination.longitude+"&key=AIzaSyA6pI2SL2EU5JpVO4oSnWFlZdep9cwjDrE");
        }

        public class StartRun extends getData {
            @Override
            protected void onPostExecute(String Rinformation) {
                super.onPostExecute(Rinformation);
                stepsR =getSteps();

                options = new PolylineOptions().width(8).color(getBaseContext().getResources().getColor(R.color.colorPrimary)).geodesic(true);
                if(stepsR!=null){
                    for (int z = 0; z < stepsR.size(); z++) {
                        LatLng point = stepsR.get(z);
                        options.add(point);
                    }
                    pol=mMap.addPolyline(options);
                    polylines.add(pol);
                }
            }
        }
    }





    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        googleMap.setOnMarkerClickListener(this);
        // Add a marker in Sydney and move the camera
        LatLng constantineLatLeng = new LatLng(36.3539041,6.5073904);

        CameraPosition constantine = CameraPosition.builder()
                .target(constantineLatLeng)
                .zoom(10)
                .bearing(0)
                .tilt(0)
                .build();

        mchuConstantine=mMap.addMarker(new MarkerOptions().position(chuConstantine.getPoint()).title("CHU Connstantine"));
        mcitéBoussouf=mMap.addMarker(new MarkerOptions().position(citéBoussouf.getPoint()).title("Cité Boussouf"));
        mciteZiadia=mMap.addMarker(new MarkerOptions().position(citeZiadia.getPoint()).title("Cité Ziadia"));
        mmusée=mMap.addMarker(new MarkerOptions().position(musée.getPoint()).title("musée"));
        mkhroub=mMap.addMarker(new MarkerOptions().position(khroub.getPoint()).title("El Khroube "));
        muc2=mMap.addMarker(new MarkerOptions().position(uc2.getPoint()).title("UC2"));
        mcentreCult=mMap.addMarker(new MarkerOptions().position(centreCult.getPoint()).title("Centre Culturelle "));
        muc3=mMap.addMarker(new MarkerOptions().position(uc3.getPoint()).title("UC3"));
        mciteBkira=mMap.addMarker(new MarkerOptions().position(citeBkira.getPoint()).title("Cite Bkira"));
        mvilleZy=mMap.addMarker(new MarkerOptions().position(villeZy.getPoint()).title("ville zy"));
        maeroport=mMap.addMarker(new MarkerOptions().position(aeroport.getPoint()).title("Aeroport "));

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(constantine), 1000, null);


    }


    @Override
    public boolean onMarkerClick(Marker marker) {

        if(marker.equals(mchuConstantine)){
            if(i==0){
                i++;
                source=3;
                textSource.setText("CHU Constantine");
            }else {
                if(i==1){
                    i++;
                    destination=3;
                    textDestination.setText("CHU Constantine");
                }else{
                    textSource.setText("select source");
                    textDestination.setText("select destination");
                    i=0;
                    for(int z=0;z<polylines.size();z++){
                        Polyline p = polylines.get(z);
                        p.remove();
                    }
                    pol=null;
                }
            }
        }

        if(marker.equals(mcitéBoussouf)){
            if(i==0){
                i++;
                source=5;
                textSource.setText("Cite Boussof");
            }else {
                if(i==1){
                    i++;
                    destination=5;
                    textDestination.setText("Cite Boussof");
                }else{
                    textSource.setText("select source");
                    textDestination.setText("select destination");
                    i=0;
                    for(int z=0;z<polylines.size();z++){
                        Polyline p = polylines.get(z);
                        p.remove();
                    }
                    pol=null;
                }
            }
        }

        if(marker.equals(mciteZiadia)){
            if(i==0){
                i++;
                source=2;
                textSource.setText("Cite Ziadia");
            }else {
                if(i==1){
                    i++;
                    destination=2;
                    textDestination.setText("Cite Ziadia");
                }else{
                    textSource.setText("select source");
                    textDestination.setText("select destination");
                    i=0;
                    for(int z=0;z<polylines.size();z++){
                        Polyline p = polylines.get(z);
                        p.remove();
                    }
                    pol=null;
                }
            }
        }

        if(marker.equals(mmusée)){
            if(i==0){
                i++;
                source=6;
                textSource.setText("Musée");
            }else {
                if(i==1){
                    i++;
                    destination=6;
                    textDestination.setText("Musée");
                }else{
                    textSource.setText("select source");
                    textDestination.setText("select destination");
                    i=0;
                    if(pol!=null){
                        for(int z=0;z<polylines.size();z++){
                            Polyline p = polylines.get(z);
                            p.remove();
                        }
                        pol=null;
                    }
                }
            }
        }
        if(marker.equals(mkhroub)){
            if(i==0){
                i++;
                source=7;
                textSource.setText("Khroube");
            }else {
                if(i==1){
                    i++;
                    destination=7;
                    textDestination.setText("Khroube");
                }else{
                    textSource.setText("select source");
                    textDestination.setText("select destination");
                    i=0;
                    for(int z=0;z<polylines.size();z++){
                        Polyline p = polylines.get(z);
                        p.remove();
                    }
                    pol=null;
                }
            }
        }
        if(marker.equals(muc2)){
            if(i==0){
                i++;
                source=10;
                textSource.setText("UC2");
            }else {
                if(i==1){
                    i++;
                    destination=10;
                    textDestination.setText("UC2");
                }else{
                    textSource.setText("select source");
                    textDestination.setText("select destination");
                    i=0;
                    for(int z=0;z<polylines.size();z++){
                        Polyline p = polylines.get(z);
                        p.remove();
                    }
                    pol=null;
                }
            }
        }
        if(marker.equals(mcentreCult)){
            if(i==0){
                i++;
                source=4;
                textSource.setText("Centre Culturele");
            }else {
                if(i==1){
                    i++;
                    destination=4;
                    textDestination.setText("Centre Culturelle");
                }else{
                    textSource.setText("select source");
                    textDestination.setText("select destination");
                    i=0;
                    for(int z=0;z<polylines.size();z++){
                        Polyline p = polylines.get(z);
                        p.remove();
                    }
                    pol=null;
                }
            }
        }
        if(marker.equals(muc3)){
            if(i==0){
                i++;
                source=9;
                textSource.setText("UC3");
            }else {
                if(i==1){
                    i++;
                    destination=9;
                    textDestination.setText("UC3");
                }else{
                    textSource.setText("select source");
                    textDestination.setText("select destination");
                    i=0;
                    for(int z=0;z<polylines.size();z++){
                        Polyline p = polylines.get(z);
                        p.remove();
                    }
                    pol=null;
                }
            }
        }
        if(marker.equals(mciteBkira)){
            if(i==0){
                i++;
                source=1;
                textSource.setText("Cite Bkira");
            }else {
                if(i==1){
                    i++;
                    destination=1;
                    textDestination.setText("Cite Bkira");
                }else{
                    textSource.setText("select source");
                    textDestination.setText("select destination");
                    i=0;
                    for(int z=0;z<polylines.size();z++){
                        Polyline p = polylines.get(z);
                        p.remove();
                    }
                    pol=null;
                }
            }
        }
        if(marker.equals(mvilleZy)){
            if(i==0){
                i++;
                source=0;
                textSource.setText("Ville ZighoudY");
            }else {
                if(i==1){
                    i++;
                    destination=0;
                    textDestination.setText("Ville ZighoudY");
                }else{
                    textSource.setText("select source");
                    textDestination.setText("select destination");
                    i=0;
                    for(int z=0;z<polylines.size();z++){
                        Polyline p = polylines.get(z);
                        p.remove();
                    }
                    pol=null;
                }
            }
        }
        if(marker.equals(maeroport)){
            if(i==0){
                i++;
                source=8;
                textSource.setText("Aeroport");
            }else {
                if(i==1){
                    i++;
                    destination=8;
                    textDestination.setText("Aeroport");
                }else{
                    textSource.setText("select source");
                    textDestination.setText("select destination");
                    i=0;
                    for(int z=0;z<polylines.size();z++){
                        Polyline p = polylines.get(z);
                        p.remove();
                    }
                    pol=null;
                }
            }
        }





        return false;
    }
}
