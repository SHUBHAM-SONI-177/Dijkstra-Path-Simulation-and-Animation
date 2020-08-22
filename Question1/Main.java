

package  sample;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.lang.*;
import java.util.*;
import java.io.*;

class Edge
{
    private City from;
    private City to;
    private Double weigh;
    void setfrom(City from){this.from = from;}
    void setto(City to){this.to = to;}
    void setweigh(Double weigh){this.weigh = weigh;}
    City getfrom(){return this.from;}
    City getto(){return this.to;}
    Double getweigh(){return this.weigh;}
}

class City
{
    private double xcor;
    private double ycor;
    private String name;
    void setxcor(double xcor){this.xcor= xcor;}
    void setycor(double ycor){this.ycor=ycor;}
    void setname(String name){this.name= name;}
    double getxcor(){return this.xcor;}
    double getycor(){return this.ycor;}
    String getname(){return this.name;}
}

class sorte implements Comparator<City>{

    @Override
    public int compare(City e1, City e2) {
        return e1.getname().compareTo(e2.getname());
    }
}

class Main extends JFrame implements ActionListener
{
    JButton add,search,delete,modify,addedge,deletedge,modifyedge,load,loadsort,path;
    JTextField xcor,ycor,namecor,searchname,delname,xmod,ymod,namemod,from,to,weigh,fromdel,todel,fromod,tomod,weighmod,source,dest,ans;
    JLabel note;
    Map<City,ArrayList<Edge>> adj = new TreeMap<City,ArrayList<Edge>>(new sorte());
    ArrayList<City> arr = new ArrayList<City>();
    BufferedReader file;
    BufferedWriter wfile;
    int size;
    Main(String s)
    {
        super(s);

        add = new JButton(); add.setBounds(400,10,150,20);add(add);add.setText("ADD");add.addActionListener(this);
        xcor = new JTextField("xcor"); xcor.setBounds(200,10,80,20);add(xcor);
        ycor = new JTextField("ycor"); ycor.setBounds(300,10,80,20);add(ycor);
        namecor = new JTextField("name"); namecor.setBounds(100,10,80,20);add(namecor);
        search = new JButton(); search.setBounds(400,40,150,20);add(search);search.setText("SEARCH");search.addActionListener(this);
        searchname = new JTextField("name"); searchname.setBounds(300,40,80,20);add(searchname);
        delete = new JButton(); delete.setBounds(400,70,150,20);add(delete);delete.setText("DELETE");delete.addActionListener(this);
        delname = new JTextField("name"); delname.setBounds(300,70,80,20);add(delname);
        modify = new JButton(); modify.setBounds(400,100,150,20);add(modify);modify.setText("MODIFY");modify.addActionListener(this);
        xmod = new JTextField("xcor"); xmod.setBounds(200,100,80,20);add(xmod);
        ymod = new JTextField("ycor"); ymod.setBounds(300,100,80,20);add(ymod);
        namemod = new JTextField("name"); namemod.setBounds(100,100,80,20);add(namemod);
        addedge = new JButton(); addedge.setBounds(400,130,150,20);add(addedge);addedge.setText("ADDEDGE");addedge.addActionListener(this);
        from = new JTextField("from"); from.setBounds(100,130,80,20);add(from);
        to = new JTextField("to"); to.setBounds(200,130,80,20);add(to);
        weigh = new JTextField("weigh"); weigh.setBounds(300,130,80,20);add(weigh);
        deletedge = new JButton(); deletedge.setBounds(400,160,150,20);add(deletedge);deletedge.setText("DELETEDGE");deletedge.addActionListener(this);
        fromdel = new JTextField("from"); fromdel.setBounds(200,160,80,20);add(fromdel);
        todel = new JTextField("to"); todel.setBounds(300,160,80,20);add(todel);
        modifyedge = new JButton(); modifyedge.setBounds(400,190,150,20);add(modifyedge);modifyedge.setText("MODIFYEDGE");modifyedge.addActionListener(this);
        fromod = new JTextField("from"); fromod.setBounds(100,190,80,20);add(fromod);
        tomod = new JTextField("to"); tomod.setBounds(200,190,80,20);add(tomod);
        weighmod = new JTextField("weigh"); weighmod.setBounds(300,190,80,20);add(weighmod);
        load = new JButton(); load.setBounds(400,220,150,20);add(load);load.setText("LOAD");load.addActionListener(this);
        loadsort = new JButton(); loadsort.setBounds(400,250,150,20);add(loadsort);loadsort.setText("EXPORTSORT");loadsort.addActionListener(this);
        path = new JButton(); path.setBounds(400,280,150,20);add(path);path.setText("FINDPATH");path.addActionListener(this);
        source = new JTextField("Source"); source.setBounds(200,280,80,20);add(source);
        dest = new JTextField("Destiny"); dest.setBounds(300,280,80,20);add(dest);
        ans = new JTextField(); ans.setBounds(20,320,540,40);add(ans);
        note = new JLabel(); note.setBounds(250,360,200,40);add(note);note.setFont(new Font("Serif", Font.PLAIN, 28));
        getContentPane().setBackground(Color.YELLOW);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocation(250,100);
        setVisible(true);
        setSize(600,450);
    }
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()==add)
        {
            try
            {
                Boolean check = false;
                double x = Double.parseDouble(xcor.getText());
                double y = Double.parseDouble(ycor.getText());
                String nm = namecor.getText();
                for(City cit : arr)
                {
                    if(cit.getname().equals(nm))
                        check = true;
                }
                if(check)
                {
                    note.setBounds(150,360,300,40);
                    note.setText("Already present with same name");
                }
                else
                {
                    City ct = new City();
                    ct.setxcor(x);
                    ct.setycor(y);
                    ct.setname(nm);
                    arr.add(ct);
                    note.setText("ADDED");
                }
            }
            catch(Exception ae)
            {
                JOptionPane.showMessageDialog(this,"Format is not correct");
            }
        }
        if(e.getSource()==search)
        {
            try
            {
                Boolean check = false;
                String nm = searchname.getText();
                double x=0,y=0;
                for(City ct : arr)
                {
                    if(ct.getname().equals(nm))
                    {
                        check = true;
                        x = ct.getxcor();
                        y = ct.getycor();
                    }
                }
                if(check)
                {
                    JOptionPane.showMessageDialog(this,"X Cordinate is "+x+" Y Cordinate is "+y);
                }
                else
                {
                    note.setBounds(250,360,200,40);
                    note.setText("Not present");
                }
            }
            catch(Exception ae)
            {
                JOptionPane.showMessageDialog(this,"Format is not correct");
            }
        }
        if(e.getSource()==delete)
        {
            try
            {
                Boolean check = false;
                String nm = delname.getText();
                for(City ct : arr)
                {
                    if(ct.getname().equals(nm))
                    {
                        check = true;
                        arr.remove(ct);
                        break;
                    }
                }
                if(check)
                {
                    note.setBounds(250,360,200,40);
                    note.setText("Deleted");
                }
                else
                {
                    note.setBounds(250,360,200,40);
                    note.setText("Not present");
                }
            }
            catch(Exception ae)
            {
                JOptionPane.showMessageDialog(this,"Format is not correct");
            }
        }
        if(e.getSource()==modify)
        {
            try
            {
                Boolean check = false;
                double x = Double.parseDouble(xcor.getText());
                double y = Double.parseDouble(ycor.getText());
                String nm = namemod.getText();
                for(City ct : arr)
                {
                    if(ct.getname().equals(nm))
                    {
                        check = true;
                        ct.setxcor(x);
                        ct.setycor(y);
                        break;
                    }
                }
                if(check)
                {
                    note.setBounds(250,360,200,40);
                    note.setText("Modified");
                }
                else
                {
                    note.setBounds(250,360,200,40);
                    note.setText("Not present");
                }
            }
            catch(Exception ae)
            {
                JOptionPane.showMessageDialog(this,"Format is not correct");
            }
        }
        if(e.getSource()==addedge)
        {
            try
            {
                Boolean check1 = false;
                String fr = from.getText();
                String tr = to.getText();
                Double w = Double.parseDouble(weigh.getText());
                City ct1 = null,ct2=null;
                for(City ct : arr)
                {
                    if(ct.getname().equals(fr))
                    {
                        check1 = true;
                        ct1 = ct;
                    }

                }
                Boolean check2 = false;
                for(City ct : arr)
                {
                    if(ct.getname().equals(tr))
                    {
                        check2 = true;
                        ct2 = ct;
                    }
                }
                Boolean check3 = true;
                for(Map.Entry<City,ArrayList<Edge>> entry : adj.entrySet())
                {
                    if(entry.getKey().getname().equals(fr))
                    {
                        for(Edge ed : entry.getValue())
                        {
                            if(ed.getto().getname().equals(tr))
                                check3 = false;
                        }
                    }
                }
                if(check1 &&check2&&check3)
                {
                    Edge ed = new Edge();
                    ed.setfrom(ct1);
                    ed.setto(ct2);
                    ed.setweigh(w);
                    ArrayList<Edge> temp1 = adj.get(ct1);
                    if(temp1==null)
                    {
                        temp1 = new ArrayList<Edge>();
                        temp1.add(ed);
                        adj.put(ct1,temp1);
                    }
                    else
                        temp1.add(ed);

                    ed = new Edge();
                    ed.setfrom(ct2);
                    ed.setto(ct1);
                    ed.setweigh(w);
                    ArrayList<Edge> temp2 = adj.get(ct2);
                    if(temp2==null)
                    {
                        temp2 = new ArrayList<Edge>();
                        temp2.add(ed);
                        adj.put(ct2,temp2);
                    }
                    else
                        temp2.add(ed);
                    note.setBounds(250,360,200,40);
                    note.setText("ADDED");
                }
                else if(!check3&&check1&&check2)
                {
                    note.setBounds(250,360,200,40);
                    note.setText("Already present");
                }
                else
                {
                    note.setBounds(250,360,200,40);
                    note.setText("Not present");
                }

            }
            catch(Exception ae)
            {
                JOptionPane.showMessageDialog(this,"Format is not correct");
            }
        }
        if(e.getSource()==deletedge)
        {
            try
            {
                Boolean check1 = false;
                Boolean check2 = false;
                String fr = fromdel.getText();
                String tr = todel.getText();
                for(Map.Entry<City,ArrayList<Edge>> entry : adj.entrySet())
                {
                    ArrayList<Edge> temp1 = entry.getValue();
                    City ct1 = entry.getKey();
                    if(ct1.getname().equals(fr))
                    {
                        for(Edge ed : temp1)
                        {
                            City ct = ed.getto();
                            if(ct.getname().equals(tr))
                            {
                                check1 = true;
                                temp1.remove(ct);
                                break;
                            }
                        }
                    }
                    if(ct1.getname().equals(tr))
                    {
                        for(Edge ed : temp1)
                        {
                            City ct = ed.getto();
                            if(ct.getname().equals(fr))
                            {
                                check2 = true;
                                temp1.remove(ct);
                                break;
                            }
                        }
                    }
                    if(check1&&check2)
                        break;
                }
                note.setBounds(250,360,200,40);
                if(check1&&check2)
                    note.setText("DELETED");
                else
                    note.setText("Not present");

            }
            catch(Exception ae)
            {
                JOptionPane.showMessageDialog(this,"Format is not correct");
            }
        }
        if(e.getSource()==modifyedge)
        {
            try
            {
                Boolean check1 = false;
                Boolean check2 = false;
                String fr = fromod.getText();
                String tr = tomod.getText();
                Double w = Double.parseDouble(weighmod.getText());
                for(Map.Entry<City,ArrayList<Edge>> entry : adj.entrySet())
                {
                    ArrayList<Edge> temp1 = entry.getValue();
                    City ct1 = entry.getKey();
                    if(ct1.getname().equals(fr))
                    {
                        for(Edge ed : temp1)
                        {
                            City ct = ed.getto();
                            if(ct.getname().equals(tr))
                            {
                                check1 = true;
                                ed.setweigh(w);
                                break;
                            }
                        }
                    }
                    if(ct1.getname().equals(tr))
                    {
                        for(Edge ed : temp1)
                        {
                            City ct = ed.getto();
                            if(ct.getname().equals(fr))
                            {
                                check2 = true;
                                ed.setweigh(w);
                                break;
                            }
                        }
                    }
                    if(check1&&check2)
                        break;
                }
                note.setBounds(250,360,200,40);
                if(check1&&check2)
                    note.setText("MODIFIED");
                else
                    note.setText("Not present");

            }
            catch(Exception ae)
            {
                JOptionPane.showMessageDialog(this,"Format is not correct");
            }
        }
        if(e.getSource()==load)
        {
            try
            {
                JFileChooser j = new JFileChooser(new File("c:"));
                if (j.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
                    file = new BufferedReader(new FileReader(j.getSelectedFile()));
                try
                {
                    String ss = file.readLine();
                    int nct = Integer.parseInt(ss);
                    for(int cc=0;cc<nct;cc++)
                    {
                        int ind =0;
                        ss=file.readLine();
                        String s[] = ss.split(" ");
                        City ct = new City();
                        ct.setname(s[ind]);namecor.setText(s[ind]);ind++;
                        ct.setxcor(Double.parseDouble(s[ind]));xcor.setText(s[ind]);ind++;
                        ct.setycor(Double.parseDouble(s[ind]));ycor.setText(s[ind]);ind++;
                        actionPerformed(new ActionEvent(add, ActionEvent.ACTION_FIRST, "ADD"));
                    }
                    ss=file.readLine();
                    int ne = Integer.parseInt(ss);
                    size = ne;
                    for(int cc=0;cc<ne;cc++)
                    {
                        int ind =0;
                        ss=file.readLine();
                        String s[] = ss.split(" ");
                        Edge ed = new Edge();
                        City c1= new City(),c2 = new City();
                        for(City ct : arr)
                        {
                            if(ct.getname().equals(s[ind]))
                                c1 = ct;
                        }
                        ed.setfrom(c1);from.setText(s[ind]);ind++;
                        for(City ct : arr)
                        {
                            if(ct.getname().equals(s[ind]))
                                c2 = ct;
                        }
                        ed.setto(c2);to.setText(s[ind]);ind++;
                        ed.setweigh(Double.parseDouble(s[ind]));weigh.setText(s[ind]);ind++;
                        actionPerformed(new ActionEvent(addedge, ActionEvent.ACTION_FIRST, "ADDEDGE"));
                    }
                    note.setBounds(250,360,200,40);
                    note.setText("Loaded");
                }
                catch(Exception ae){
                }

                file.close();

            }
            catch(IOException ff)
            {
                JOptionPane.showMessageDialog(this,"File not present");
            }
        }
        if(e.getSource()==loadsort)
        {
            try
            {
                JFileChooser j = new JFileChooser(new File("C:"));
                if (j.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
                    wfile = new BufferedWriter(new FileWriter(j.getSelectedFile()));
                try
                {
                    Comparator<City> sort1 = new Comparator<City>()
                    {
                        public int compare(City c1,City c2)
                        {
                            return c1.getname().compareTo(c2.getname());
                        }
                    };
                    Comparator<Edge> sort3 = new Comparator<Edge>()
                    {
                        public int compare(Edge c1,Edge c2)
                        {
                            return c1.getto().getname().compareTo(c2.getto().getname());
                        }
                    };
                    Collections.sort(arr,sort1);
                    for(Map.Entry<City,ArrayList<Edge>> entry : adj.entrySet())
                        Collections.sort(entry.getValue(),sort3);
                    wfile.write(Integer.toString(arr.size()));wfile.write("\n");
                    for(City ct : arr)
                    {
                        wfile.write(ct.getname());wfile.write(' ');
                        wfile.write(Double.toString(ct.getxcor()));wfile.write(' ');
                        wfile.write(Double.toString(ct.getycor()));wfile.write("\n");
                    }

                    wfile.write(Integer.toString(size));wfile.write("\n");

                    for(Map.Entry<City,ArrayList<Edge>> entry : adj.entrySet())
                    {

                        for(Edge ed : entry.getValue()) {
                            wfile.write(entry.getKey().getname());wfile.write(' ');
                            wfile.write(ed.getto().getname());
                            wfile.write(' ');
                            wfile.write(Double.toString(ed.getweigh()));
                            wfile.write("\n");
                        }
                    }
                    note.setBounds(150,360,300,40);
                    note.setText("Sorted and Exported");
                }
                catch(Exception ae)
                {
                }
                wfile.close();

            }
            catch(IOException ff){
                JOptionPane.showMessageDialog(this,"File related Error");
            }
        }
        if(e.getSource()==path)
        {
            try
            {
                String sc = source.getText();
                String dt = dest.getText();
                City sct = new City();

                Map<City, Double> dis = new HashMap<City,Double>();
                Map<City,Boolean> check = new HashMap<City,Boolean>();
                for(City merict : arr)
                {
                    dis.put(merict,Double.MAX_VALUE);
                    check.put(merict, false);
                    if(merict.getname().equals(sc))
                        sct = merict;
                }
                dis.put(sct,(double)0);
                Map<City,Edge> path = new HashMap<>();
                int dij = adj.size();
                for(int z=0;z<dij-1;z++)
                {
                    Double min = Double.MAX_VALUE;
                    City vertex = new City();
                    for(Map.Entry<City,Double> entry : dis.entrySet())
                    {
                        City from1 = entry.getKey();
                        Double dist = entry.getValue();
                        if (!check.get(from1)){
                            if(min>dist)
                            {
                                min = dist;
                                vertex =from1;
                            }

                        }

                    }
                    if(vertex == null)
                        continue;
                    check.put(vertex,true);

                    ArrayList<Edge> temparr = adj.get(vertex);
                    for(Edge r : temparr)
                    {
                        City to1 = r.getto();
                        if (!check.get(to1)) {
                            if(dis.get(vertex) + r.getweigh() < dis.get(to1))
                            {
                                dis.put(to1,dis.get(vertex) + r.getweigh());
                                path.put(to1,r);
                            }
                        }
                    }

                }
                City to2 = new City();
                for(City la : arr)
                {
                    if(la.getname().equals(dt))
                    {
                        to2 = la;
                        break;
                    }
                }
                //System.out.println(to2);
                if(dis.get(to2)==Double.MAX_VALUE)
                {
                    ans.setText("no path exists");
                }
                else if(to2.equals(source))
                {
                    ans.setText("source and destination are same");
                }
                else
                {
                    String merans=sc+" ";
                    Stack<Edge> stack = new Stack<Edge>();
                    while(path.get(to2) != null)
                    {
                        stack.push(path.get(to2));
                        to2 = path.get(to2).getfrom();
                    }
                    while(!stack.empty())
                    {
                        merans+=stack.pop().getto().getname();
                        merans+=" ";
                    }
                    ans.setText(merans);
                }


            }
            catch(Exception ae)
            {
                JOptionPane.showMessageDialog(this,"Error");
            }
        }

    }
    public static void main(String arg[]) throws Exception
    {
        new Main("graph");
    }
}

