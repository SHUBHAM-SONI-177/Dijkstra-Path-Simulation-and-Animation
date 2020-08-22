package sample;

import javafx.animation.FillTransition;
import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import javax.swing.*;
import java.io.*;
import java.util.*;





class Edge
{
    private City from;private City to;private Double weigh;private Line line;
    void setfrom(City from){this.from = from;}void setto(City to){this.to = to;}void setweigh(Double weigh){this.weigh = weigh;}
    void setline(Line line){this.line = line;}City getfrom(){return this.from;}City getto(){return this.to;}
    Double getweigh(){return this.weigh;}Line getline(){return line;}
}

class City
{
    private Text label;

    public Text getLabel() {
        return label;
    }

    public void setLabel(Text label) {
        this.label = label;
    }
    private String name;private Circle circle;
    void setcircle(Circle circle){this.circle= circle;}
    void setname(String name){this.name= name;}
    Circle getcircle(){return this.circle;}
    String getname(){return this.name;}
}


class sorte implements Comparator<City>{

    @Override
    public int compare(City e1, City e2) {
        return e1.getname().compareTo(e2.getname());
    }
}

public class Main extends Application {


    Group root;
    Scene scene;
    Double merapointx,merapointy;
    Boolean drag=false;
    ArrayList<City> Cityarr= new ArrayList<City>();
    Map<City,ArrayList<Edge>> adj = new TreeMap<City,ArrayList<Edge>>(new sorte());
    int size;
    @Override
    public void start(Stage s) {
        boolean arr[] = {true};
        root = new Group();
        VBox grp = new VBox();
        grp.setSpacing(10);
        Button submit = new Button("vertexMode");
        Button dra = new Button("add");
        Button path = new Button("FindPath");
        Button load = new Button("LOAD");
        Button loadsort = new Button("Load&Sort");
        TextField source = new TextField("Source");
        TextField dest = new TextField("Destination");
        Circle  animate = new Circle();
        Text note = new Text();
        grp.getChildren().addAll(submit,dra,path,source,dest,load,loadsort,note);
        root.getChildren().addAll(grp);
        scene = new Scene(root, 600, 600);

        s.setScene(scene);
        s.show();
        load.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try
                {
                    JFileChooser j = new JFileChooser(new File("C:\\Users\\swast\\GUI"));
                    if (j.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                        BufferedReader file = new BufferedReader(new FileReader(j.getSelectedFile()));
                        try {
                            String ss = file.readLine();
                            int nct = Integer.parseInt(ss);
                            for (int cc = 0; cc < nct; cc++)
                            {
                                int ind=0;
                                ss = file.readLine();
                                String s[] = ss.split(" ");
                                City ct = new City();
                                Text tex = new Text();
                                tex.setText(s[ind]);
                                ct.setname(s[ind]);ind++;
                                Circle cir = new Circle();
                                cir.setCenterX(Double.parseDouble(s[ind])*10);
                                tex.setX(Double.parseDouble(s[ind])*10);ind++;
                                cir.setCenterY(Double.parseDouble(s[ind])*10);
                                tex.setY(Double.parseDouble(s[ind])*10);ind++;
                                cir.setRadius(20);
                                ct.setcircle(cir);
                                ct.setLabel(tex);
                                cir.setFill(Color.SKYBLUE);
                                root.getChildren().addAll(cir,tex);
                                Cityarr.add(ct);
                                cir.setOnMouseDragged(new EventHandler<MouseEvent>() {
                                    @Override
                                    public void handle(MouseEvent ee) {
                                        try {
                                            if (arr[0] && drag) {
                                                cir.setCenterX(ee.getX());
                                                cir.setCenterY(ee.getY());
                                                tex.setX(ee.getX());
                                                tex.setY(ee.getY());
                                                Boolean chek2 = false;
                                                City merict = new City();
                                                for (City ct : Cityarr) {
                                                    if (ct.getcircle() == cir) {
                                                        chek2 = true;
                                                        merict = ct;
                                                    }
                                                }
                                                if (chek2) {
                                                    for (Map.Entry<City, ArrayList<Edge>> entry : adj.entrySet()) {
                                                        if (merict == entry.getKey()) {
                                                            for (Edge ed : entry.getValue()) {
                                                                Line ln = ed.getline();
                                                                if (merict.getcircle().contains(ln.getStartX(), ln.getStartY())) {
                                                                    ln.setStartX(ee.getX());
                                                                    ln.setStartY(ee.getY());
                                                                }
                                                            }
                                                        } else {
                                                            for (Edge ed : entry.getValue()) {
                                                                if (merict == ed.getto()) {
                                                                    Line ln = ed.getline();
                                                                    if (merict.getcircle().contains(ln.getEndX(), ln.getEndY())) {
                                                                        ln.setEndX(ee.getX());
                                                                        ln.setEndY(ee.getY());
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        } catch (Exception aae) {
                                            JOptionPane.showMessageDialog(null, "Error");

                                        }
                                    }
                                });
                                cir.setOnMousePressed(new EventHandler<MouseEvent>()
                                {
                                    @Override
                                    public void handle(MouseEvent ee)
                                    {
                                        try
                                        {
                                            if (!arr[0])
                                            {
                                                merapointx = cir.getCenterX();
                                                merapointy = cir.getCenterY();
                                            }
                                        }
                                        catch (Exception aae)
                                        {
                                            JOptionPane.showMessageDialog(null,"Error");

                                        }
                                    }
                                });
                                cir.setOnMouseClicked(new EventHandler<MouseEvent>()
                                {
                                    @Override
                                    public void handle(MouseEvent mouseEvent)
                                    {
                                        try
                                        {
                                            if (arr[0] && drag)
                                            {
                                                int chek5 = JOptionPane.showConfirmDialog(null, "Do you want to delete");
                                                if (chek5 == JOptionPane.YES_OPTION)
                                                {
                                                    root.getChildren().remove(cir);
                                                    root.getChildren().remove(tex);
                                                    for (City ct : Cityarr)
                                                    {
                                                        if (ct.getcircle() == cir)
                                                            Cityarr.remove(ct);
                                                    }
                                                    for (Map.Entry<City, ArrayList<Edge>> entry : adj.entrySet())
                                                    {
                                                        if (entry.getKey().getcircle() == cir)
                                                        {
                                                            for(Edge ede : entry.getValue())
                                                            {
                                                                root.getChildren().remove(ede.getline());
                                                            }
                                                            adj.remove(entry.getKey());
                                                        }
                                                        else
                                                        {
                                                            for (Edge edd : entry.getValue())
                                                            {
                                                                if (edd.getto().getcircle() == cir)
                                                                {
                                                                    entry.getValue().remove(edd);
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        catch (Exception aae)
                                        {
                                            JOptionPane.showMessageDialog(null,"Error");

                                        }
                                    }
                                });

                                cir.setOnMouseReleased(new EventHandler<MouseEvent>(){
                                    @Override
                                    public void handle (MouseEvent ee)
                                    {
                                        try {
                                            if (!arr[0]) {
                                                Boolean chek1 = false;
                                                double x = 0.00, y = 0.00;
                                                City ct1 = new City(), ct2 = new City();
                                                for (City ct : Cityarr) {
                                                    if (ct.getcircle().contains(merapointx, merapointy))
                                                        ct1 = ct;
                                                    if (ct.getcircle().contains(ee.getX(), ee.getY())) {
                                                        ct2 = ct;
                                                        chek1 = true;
                                                        x = ct.getcircle().getCenterX();
                                                        y = ct.getcircle().getCenterY();
                                                    }

                                                }
                                                if (chek1) {
                                                    Double w = Double.parseDouble(JOptionPane.showInputDialog("Enter weigh of the edge"));

                                                    Edge ed = new Edge();
                                                    ed.setweigh(w);
                                                    ed.setfrom(ct1);
                                                    ed.setto(ct2);
                                                    Line line = new Line();
                                                    line.setStrokeWidth(10);
                                                    line.setStartX(merapointx);
                                                    line.setStartY(merapointy);
                                                    line.setEndX(x);
                                                    line.setEndY(y);
                                                    ed.setline(line);
                                                    line.setStroke(Color.SKYBLUE);
                                                    root.getChildren().add(line);
                                                    ArrayList<Edge> temp = adj.get(ct1);
                                                    if (temp == null) {
                                                        temp = new ArrayList<Edge>();
                                                        temp.add(ed);
                                                        adj.put(ct1, temp);

                                                    } else
                                                        temp.add(ed);
                                                    Edge ed1 = new Edge();
                                                    ed1.setfrom(ct2);
                                                    ed1.setto(ct1);
                                                    ed1.setweigh(w);
                                                    ed1.setline(line);
                                                    ArrayList<Edge> temp1 = adj.get(ct2);
                                                    if (temp1 == null) {
                                                        temp1 = new ArrayList<Edge>();
                                                        temp1.add(ed1);
                                                        adj.put(ct2, temp1);

                                                    } else
                                                        temp1.add(ed1);
                                                    line.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                                        @Override
                                                        public void handle(MouseEvent eee) {
                                                            try {
                                                                if (!arr[0]) {
                                                                    int chek6 = JOptionPane.showConfirmDialog(null, "Do you want to delete");
                                                                    if (chek6 == JOptionPane.NO_OPTION) {
                                                                        int chek7 = JOptionPane.showConfirmDialog(null, "Do you want to alter the weigh");
                                                                        if (chek7 == JOptionPane.YES_OPTION) {
                                                                            double w = Double.parseDouble(JOptionPane.showInputDialog("Enter new weigh"));
                                                                            for (Map.Entry<City, ArrayList<Edge>> entry : adj.entrySet()) {
                                                                                for (Edge edd : entry.getValue()) {
                                                                                    if (edd.getline() == line) {
                                                                                        edd.setweigh(w);
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                    if (chek6 == JOptionPane.YES_OPTION) {
                                                                        root.getChildren().remove(line);
                                                                        for (Map.Entry<City, ArrayList<Edge>> entry : adj.entrySet()) {
                                                                            for (Edge edd : entry.getValue()) {
                                                                                if (edd.getline() == line) {
                                                                                    entry.getValue().remove(edd);
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            } catch (Exception aaae) {
                                                                JOptionPane.showMessageDialog(null, "Format Error");

                                                            }

                                                        }
                                                    });

                                                }
                                            }
                                        }
                                        catch (Exception aaa)
                                        {

                                        }
                                    }
                                });
                            }

                            ss = file.readLine();
                            int ne = Integer.parseInt(ss);
                            size = ne;
                            for (int cc = 0; cc < ne; cc++)
                            {
                                int ind = 0;
                                ss = file.readLine();
                                String s[] = ss.split(" ");
                                Edge ed = new Edge();
                                Line li = new Line();
                                City c1 = new City(), c2 = new City();
                                for (City ct : Cityarr) {
                                    if (ct.getname().equals(s[ind]))
                                    {
                                        c1 = ct;
                                        li.setStartX(ct.getcircle().getCenterX());
                                        li.setStartY(ct.getcircle().getCenterY());
                                    }
                                }
                                ed.setfrom(c1);
                                ind++;
                                for (City ct : Cityarr) {
                                    if (ct.getname().equals(s[ind]))
                                    {
                                        c2 = ct;
                                        li.setEndX(ct.getcircle().getCenterX());
                                        li.setEndY(ct.getcircle().getCenterY());
                                    }
                                }
                                Boolean mark = false;
                                for(Map.Entry<City, ArrayList<Edge>>entry : adj.entrySet())
                                {
                                    if(entry.getKey()==c1) {
                                        for (Edge edde : entry.getValue()){
                                            if(edde.getto()==c2)
                                            {
                                                mark = true;
                                            }
                                        }

                                    }
                                }
                                if(mark)
                                {
                                    continue;
                                }
                                li.setStrokeWidth(10);
                                root.getChildren().add(li);
                                li.setStroke(Color.SKYBLUE);
                                ed.setline(li);
                                ed.setto(c2);
                                ind++;
                                Edge ed1 = new Edge();
                                ed1.setweigh(Double.parseDouble(s[ind]));
                                ed.setweigh(Double.parseDouble(s[ind]));
                                ind++;
                                ArrayList<Edge> temp = adj.get(c1);
                                if (temp == null) {
                                    temp = new ArrayList<Edge>();
                                    temp.add(ed);
                                    adj.put(c1, temp);

                                } else
                                    temp.add(ed);



                                ed1.setfrom(c2);
                                ed1.setto(c1);
                                ed1.setline(li);
                                ArrayList<Edge> temp1 = adj.get(c2);
                                if (temp1 == null) {
                                    temp1 = new ArrayList<Edge>();
                                    temp1.add(ed1);
                                    adj.put(c2, temp1);

                                }
                                else
                                    temp1.add(ed1);

                            }
                            note.setText("Loaded");
                        } catch (Exception ae) {
                            JOptionPane.showMessageDialog(null, "Format is not correct");
                        }

                        file.close();
                    }

                }
                catch(IOException ff)
                {
                    JOptionPane.showMessageDialog(null,"File not present");
                }
            }
        });
        loadsort.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try
                {
                    JFileChooser j = new JFileChooser(new File("C:\\Users\\swast\\GUI"));
                    if (j.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                        BufferedWriter wfile = new BufferedWriter(new FileWriter(j.getSelectedFile()));
                        try {
                            Comparator<City> sort1 = new Comparator<City>() {
                                public int compare(City c1, City c2) {
                                    return c1.getname().compareTo(c2.getname());
                                }
                            };
                            Comparator<Edge> sort3 = new Comparator<Edge>() {
                                public int compare(Edge c1, Edge c2) {
                                    return c1.getto().getname().compareTo(c2.getto().getname());
                                }
                            };
                            Collections.sort(Cityarr, sort1);
                            for(Map.Entry<City,ArrayList<Edge>> entry : adj.entrySet())
                                Collections.sort(entry.getValue(), sort3);
                            wfile.write(Integer.toString(Cityarr.size()));
                            wfile.write("\n");
                            for (City ct : Cityarr) {
                                wfile.write(ct.getname());
                                wfile.write(' ');
                                wfile.write(Double.toString(ct.getcircle().getCenterX()));
                                wfile.write(' ');
                                wfile.write(Double.toString(ct.getcircle().getCenterY()));
                                wfile.write("\n");
                            }

                            wfile.write(Integer.toString(size));
                            wfile.write("\n");

                            for (Map.Entry<City,ArrayList<Edge>> entry :adj.entrySet()) {

                                for(Edge ed : entry.getValue()){
                                    wfile.write(entry.getKey().getname());
                                    wfile.write(' ');
                                    wfile.write(ed.getto().getname());
                                    wfile.write(' ');
                                    wfile.write(Double.toString(ed.getweigh()));
                                    wfile.write("\n");
                                }
                            }
                            note.setText("Sorted and Exported");
                        } catch (Exception ae) {
                            JOptionPane.showMessageDialog(null, "Format is not correct");
                        }
                        wfile.close();
                    }

                }
                catch(IOException ff){
                    JOptionPane.showMessageDialog(null,"File related Error");
                }
            }
        });
        path.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try
                {
                    String sc = source.getText();String dt = dest.getText();City sct = new City();
                    //System.out.println(sc+dt);

                    Map<City, Double> dis = new HashMap<City,Double>();Map<City,Boolean> check = new HashMap<City,Boolean>();
                    for(City merct : Cityarr){
                        dis.put(merct,Double.MAX_VALUE);
                        check.put(merct, false);
                        if(merct.getname().equals(sc))
                            sct = merct;
                    }
                    dis.put(sct,(double)0);
                    Map<City,Edge> paths = new HashMap<City,Edge>();int dij = adj.size();

                    for(int z=0;z<dij-1;z++)
                    {
                        Double min = Double.MAX_VALUE;
                        City vertex = new City();
                        for(Map.Entry<City,Double> entry : dis.entrySet())
                        {
                            City from1 = entry.getKey();
                            Double dist = entry.getValue();
                            if (!check.get(from1)){
                                if(min>dist){
                                    min = dist;vertex =from1;
                                }

                            }
                        }

                        if(vertex == null)continue;
                        check.put(vertex,true);

                        ArrayList<Edge> temparr = adj.get(vertex);
                        for(Edge r : temparr){
                            City to1 = r.getto();
                            if (!check.get(to1)){
                                if(dis.get(vertex) + r.getweigh() < dis.get(to1)){
                                    dis.put(to1,dis.get(vertex) + r.getweigh());
                                    paths.put(to1,r);
                                }
                            }
                        }

                    }
                    City to2 = new City();
                    for(City la : Cityarr)if(la.getname().equals(dt)){to2 = la;break;}
                    System.out.print(paths.get(to2));
                    if(dis.get(to2)==Double.MAX_VALUE){JOptionPane.showMessageDialog(null,"No path exist");}
                    else if(to2.equals(sct)){JOptionPane.showMessageDialog(null,"Source On landMark");}
                    else
                    {
                        while(paths.get(to2) != null) {
                            paths.get(to2).getline().setStroke(Color.ORANGE);
                            to2 = paths.get(to2).getfrom();
                        }


                    }
                }
                catch (Exception ae)
                {
                    JOptionPane.showMessageDialog(null,"Format error");
                }
            }
        });
        dra.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(drag){
                    drag = false;
                    dra.setText("add");
                }
                else{
                    drag = true;
                    dra.setText("Drag or delete");
                }


            }
        });
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(!arr[0]) {
                    grp.getChildren().remove(source);
                    grp.getChildren().remove(dest);
                    grp.getChildren().remove(path);
                    grp.getChildren().remove(load);
                    grp.getChildren().remove(loadsort);
                    grp.getChildren().remove(note);
                    grp.getChildren().add(dra);
                    grp.getChildren().add(path);

                    grp.getChildren().add(source);
                    grp.getChildren().add(dest);
                    grp.getChildren().add(load);
                    grp.getChildren().add(loadsort);
                    grp.getChildren().remove(note);
                    arr[0] = true;
                    submit.setText("vertexmode");
                }
                else
                {
                    arr[0]=false;
                    grp.getChildren().remove(dra);
                    submit.setText("edgemode");
                }
            }
        });
        scene.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent e) {
                try {
                    if (arr[0] && !drag) {
                        Boolean chec = true;
                        String nm = JOptionPane.showInputDialog("Enter name of the vertex");
                        for(City ctt: Cityarr)
                        {
                            if(ctt.getname().equals(nm))
                            {
                                chec = false;
                                JOptionPane.showMessageDialog(null,"Already present with same name");
                            }
                        }

                        if (nm != "" && nm != null && chec) {
                            City ct = new City();
                            ct.setname(nm);
                            Circle r = new Circle();
                            Text lab = new Text();
                            lab.setText(nm);
                            lab.setX(e.getX());
                            lab.setY(e.getY());
                            r.setCenterX(e.getX());
                            r.setCenterY(e.getY());
                            r.setRadius(20);
                            ct.setcircle(r);
                            ct.setLabel(lab);
                            r.setFill(Color.SKYBLUE);
                            root.getChildren().addAll(r,lab);
                            Cityarr.add(ct);
                            r.setOnMouseDragged(new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent ee) {
                                    try {
                                        if (arr[0] && drag) {
                                            r.setCenterX(ee.getX());
                                            r.setCenterY(ee.getY());
                                            lab.setX(ee.getX());
                                            lab.setY(ee.getY());
                                            Boolean check2 = false;
                                            City merict = new City();
                                            for (City ct : Cityarr) {
                                                if (ct.getcircle() == r) {
                                                    check2 = true;
                                                    merict = ct;
                                                }
                                            }
                                            if (check2) {
                                                for (Map.Entry<City, ArrayList<Edge>> entry : adj.entrySet()) {
                                                    if (merict == entry.getKey()) {
                                                        for (Edge ed : entry.getValue()) {
                                                            Line ln = ed.getline();
                                                            if (merict.getcircle().contains(ln.getStartX(), ln.getStartY())) {
                                                                ln.setStartX(ee.getX());
                                                                ln.setStartY(ee.getY());
                                                            }
                                                        }
                                                    } else {
                                                        for (Edge ed : entry.getValue()) {
                                                            if (merict == ed.getto()) {
                                                                Line ln = ed.getline();
                                                                if (merict.getcircle().contains(ln.getEndX(), ln.getEndY())) {
                                                                    ln.setEndX(ee.getX());
                                                                    ln.setEndY(ee.getY());
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    } catch (Exception aae) {
                                        JOptionPane.showMessageDialog(null,"Error");

                                    }
                                }
                            });
                            r.setOnMousePressed(new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent ee) {
                                    try {

                                        if (!arr[0]) {
                                            merapointx = r.getCenterX();
                                            merapointy = r.getCenterY();
                                        }
                                    } catch (Exception aae) {
                                        JOptionPane.showMessageDialog(null,"Error");

                                    }
                                }
                            });
                            r.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent mouseEvent) {
                                    try {

                                        if (arr[0] && drag) {
                                            int check5 = JOptionPane.showConfirmDialog(null, "Do you want to delete");
                                            if (check5 == JOptionPane.YES_OPTION) {
                                                root.getChildren().remove(r);
                                                root.getChildren().remove(lab);
                                                for (City ct : Cityarr) {
                                                    if (ct.getcircle() == r)
                                                        Cityarr.remove(ct);
                                                }
                                                for (Map.Entry<City, ArrayList<Edge>> entry : adj.entrySet()) {
                                                    if (entry.getKey().getcircle() == r) {
                                                        for(Edge ede : entry.getValue())
                                                            root.getChildren().remove(ede.getline());
                                                        adj.remove(entry.getKey());
                                                    } else {
                                                        for (Edge edd : entry.getValue()) {
                                                            if (edd.getto().getcircle() == r) {
                                                                entry.getValue().remove(edd);
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    } catch (Exception aae) {
                                        JOptionPane.showMessageDialog(null,"Error");

                                    }
                                }
                            });
                            r.setOnMouseReleased(new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent ee) {
                                    try {
                                        if (!arr[0]) {
                                            Boolean check1 = false;
                                            double x = 0.00, y = 0.00;
                                            City ct1 = new City(), ct2 = new City();
                                            for (City ct : Cityarr) {
                                                if (ct.getcircle().contains(merapointx, merapointy))
                                                    ct1 = ct;
                                                if (ct.getcircle().contains(ee.getX(), ee.getY())) {
                                                    ct2 = ct;
                                                    check1 = true;
                                                    x = ct.getcircle().getCenterX();
                                                    y = ct.getcircle().getCenterY();
                                                }

                                            }
                                            if (check1) {
                                                Double w = Double.parseDouble(JOptionPane.showInputDialog("Enter weigh of the edge"));

                                                Edge ed = new Edge();
                                                ed.setweigh(w);
                                                ed.setfrom(ct1);
                                                ed.setto(ct2);
                                                Line line = new Line();
                                                line.setStrokeWidth(10);
                                                line.setStartX(merapointx);
                                                line.setStartY(merapointy);
                                                line.setEndX(x);
                                                line.setEndY(y);
                                                ed.setline(line);
                                                line.setStroke(Color.SKYBLUE);
                                                root.getChildren().add(line);

                                                //System.out.println(adj.get(ct1));
                                                ArrayList<Edge> temp = adj.get(ct1);
                                                if (temp == null) {
                                                    //System.out.println("in");
                                                    temp = new ArrayList<Edge>();
                                                    temp.add(ed);
                                                    adj.put(ct1, temp);

                                                } else
                                                    temp.add(ed);
                                                Edge ed1 = new Edge();
                                                ed1.setfrom(ct2);
                                                ed1.setto(ct1);
                                                ed1.setweigh(w);
                                                ed1.setline(line);
                                                ArrayList<Edge> temp1 = adj.get(ct2);
                                                if (temp1 == null) {
                                                    temp1 = new ArrayList<Edge>();
                                                    temp1.add(ed1);
                                                    adj.put(ct2, temp1);

                                                } else
                                                    temp1.add(ed1);

                                                line.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                                    @Override
                                                    public void handle(MouseEvent eee) {
                                                        try {
                                                            if (!arr[0]) {
                                                                int check6 = JOptionPane.showConfirmDialog(null, "Do you want to delete");
                                                                if (check6 == JOptionPane.NO_OPTION) {

                                                                    int check7 = JOptionPane.showConfirmDialog(null, "Do you want to alter the weigh");
                                                                    if (check7 == JOptionPane.YES_OPTION) {
                                                                        double w = Double.parseDouble(JOptionPane.showInputDialog("Enter new weigh"));
                                                                        for (Map.Entry<City, ArrayList<Edge>> entry : adj.entrySet()) {
                                                                            for (Edge edd : entry.getValue()) {
                                                                                if (edd.getline() == line) {
                                                                                    edd.setweigh(w);
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                                if (check6 == JOptionPane.YES_OPTION) {
                                                                    root.getChildren().remove(line);
                                                                    for (Map.Entry<City, ArrayList<Edge>> entry : adj.entrySet()) {
                                                                        for (Edge edd : entry.getValue()) {
                                                                            if (edd.getline() == line) {
                                                                                entry.getValue().remove(edd);
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } catch (Exception aaae) {
                                                            JOptionPane.showMessageDialog(null,"Format Error");

                                                        }

                                                    }
                                                });

                                            }
                                        }
                                    } catch (Exception aae) {
                                        JOptionPane.showMessageDialog(null,"Format Error");

                                    }
                                }
                            });
                        }
                    }
                }
                catch(Exception ae)
                {
                    JOptionPane.showMessageDialog(null,"Format Error");
                }
            }
        });
    }


    public static void main(String[] args) throws Exception{
        launch(args);
    }
}
