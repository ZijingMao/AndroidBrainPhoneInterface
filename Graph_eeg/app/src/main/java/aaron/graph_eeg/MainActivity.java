package aaron.graph_eeg;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.LineChart;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.SeriesSelection;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private  GraphicalView view;
    //private LineGraph line = new LineGraph();
    private  Thread thread;

    private XYSeries aSeries;
    private XYSeries bSeries;
    private XYSeries cSeries;
    private XYSeries dSeries;
    private XYSeries eSeries;

    private XYMultipleSeriesDataset dataset;

    private XYSeriesRenderer arenderer; // This will be used to customize line 1
    private XYSeriesRenderer brenderer; // This will be used to customize line 1
    private XYSeriesRenderer crenderer; // This will be used to customize line 1
    private XYSeriesRenderer drenderer; // This will be used to customize line 1
    private XYSeriesRenderer erenderer; // This will be used to customize line 1

    private XYMultipleSeriesRenderer multiRenderer; // Holds a collection of XYSeriesRenderer and customizes the graph

    //private String[] xLabel = new String[] {"0","1","2","3","4","5","6","7","8","9"};

    private String[] xLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            LineGraph();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //new ChartTask().execute();
    }





    private void LineGraph() throws IOException {

        //int[] a = { 2000,3200,2700,3000,3400,3500,3400,3800,2000,2500,2700,3000,2100,3500,3700};
        //int[] b = {2200, 2700, 3200, 2800, 2600, 3000, 3300, 3400,2200, 2500, 2900, 2800, 2600, 3000, 3300 };

        BufferedReader reader = null;
        reader = new BufferedReader(
        new InputStreamReader(getAssets().open("data.txt")));

            // do reading, usually loop until end of file reading
        String line = reader.readLine();

        //List<Double> xa = new ArrayList<Double>();
        List<Double> data1 = new ArrayList<Double>();
        List<Double> data2 = new ArrayList<Double>();
        List<Double> data3 = new ArrayList<Double>();
        List<Double> data4 = new ArrayList<Double>();
        List<Double> data5 = new ArrayList<Double>();

        //Double[] x = new Double[xa.size()];
        Double[] a = new Double[data1.size()];
        Double[] b = new Double[data2.size()];
        Double[] c = new Double[data2.size()];
        Double[] d = new Double[data2.size()];
        Double[] e = new Double[data2.size()];

        int lineCount = 0;
            while (line != null) {
                String[] valueStr = line.split("\\s+");
                //String line0 = valueStr[0];
                //String line1 = valueStr[1];
                //String line2 = valueStr[2];
                //double[] x1 = {Double.parseDouble(valueStr[0])};
                double[] a1 = {Double.parseDouble(valueStr[0])};    //first column
                double[] b1 = {Double.parseDouble(valueStr[1])};    //second column
                double[] c1 = {Double.parseDouble(valueStr[2])};    //third column
                double[] d1 = {Double.parseDouble(valueStr[3])};    //fourth column
                double[] e1 = {Double.parseDouble(valueStr[4])};    //fifth column

                //xa.add(new Double(x1[0]));
                data1.add(new Double(a1[0]));   //add to the data lists
                data2.add(new Double(b1[0]));
                data3.add(new Double(c1[0]));
                data4.add(new Double(d1[0]));
                data5.add(new Double(e1[0]));

                line = reader.readLine();
                lineCount++;
            }
        //x = xa.toArray(x);
        //double[] x = {1,2,3,4,5,6,7,8,9,10};
        int [] x = new int[lineCount];    //create x axis numbers
        for (int i=0;i <lineCount;++i){         //from 0 to numbers of lines in data file
            x[i]=i;
        }
        a = data1.toArray(a);
        b = data2.toArray(b);
        c = data3.toArray(c);
        d = data4.toArray(d);
        e = data5.toArray(e);

        String xlabeltoString = Arrays.toString(x);     //create x as a string to show in graph
        xLabel = xlabeltoString.substring(1,xlabeltoString.length()-1).split(",");


        aSeries = new XYSeries("data1");
        bSeries = new XYSeries("data2");
        cSeries = new XYSeries("data3");
        dSeries = new XYSeries("data4");
        eSeries = new XYSeries("data5");

            for(int i=0;i<x.length;i++){
                aSeries.add(x[i],a[i]);
                bSeries.add(x[i],b[i]);
                cSeries.add(x[i],c[i]);
                dSeries.add(x[i],d[i]);
                eSeries.add(x[i],e[i]);
            }

        dataset = new XYMultipleSeriesDataset();
        // Add single dataset to multiple dataset
        dataset.addSeries(aSeries);
        dataset.addSeries(bSeries);
        dataset.addSeries(cSeries);
        dataset.addSeries(dSeries);
        dataset.addSeries(eSeries);

        // Customization time for data 1!
        arenderer = new XYSeriesRenderer();
        arenderer.setColor(Color.YELLOW);
        arenderer.setPointStyle(PointStyle.SQUARE);
        arenderer.setFillPoints(true);
        arenderer.setLineWidth(2);
        arenderer.setDisplayChartValues(true);

        // Customization time for data 2!
        brenderer = new XYSeriesRenderer();
        brenderer.setColor(Color.RED);
        brenderer.setPointStyle(PointStyle.SQUARE);
        brenderer.setFillPoints(true);
        brenderer.setLineWidth(2);
        brenderer.setDisplayChartValues(true);

        // Customization time for data 3!
        crenderer = new XYSeriesRenderer();
        crenderer.setColor(Color.BLUE);
        crenderer.setPointStyle(PointStyle.SQUARE);
        crenderer.setFillPoints(true);
        crenderer.setLineWidth(2);
        crenderer.setDisplayChartValues(true);

        // Customization time for data 4!
        drenderer = new XYSeriesRenderer();
        drenderer.setColor(Color.GREEN);
        drenderer.setPointStyle(PointStyle.SQUARE);
        drenderer.setFillPoints(true);
        drenderer.setLineWidth(2);
        drenderer.setDisplayChartValues(true);

        // Customization time for data 5!
        erenderer = new XYSeriesRenderer();
        erenderer.setColor(Color.WHITE);
        erenderer.setPointStyle(PointStyle.SQUARE);
        erenderer.setFillPoints(true);
        erenderer.setLineWidth(2);
        erenderer.setDisplayChartValues(true);

        // Customization for the whole chart
        multiRenderer = new XYMultipleSeriesRenderer();
        multiRenderer.setXLabels(0);
        multiRenderer.setChartTitle("EEG SIGNAL");
        multiRenderer.setXTitle("DataSet");
        multiRenderer.setYTitle("Range");
        multiRenderer.setZoomButtonsVisible(true);
        multiRenderer.setBarSpacing(2);
            for(int i=0;i<x.length;i++){
                multiRenderer.addXTextLabel(i, xLabel[i]);
            }

        // Add single renderer to multiple renderer
        multiRenderer.addSeriesRenderer(arenderer);
        multiRenderer.addSeriesRenderer(brenderer);
        multiRenderer.addSeriesRenderer(crenderer);
        multiRenderer.addSeriesRenderer(drenderer);
        multiRenderer.addSeriesRenderer(erenderer);


        LinearLayout chartContainer = (LinearLayout) findViewById(R.id.chart_container);

        String[] types = new String[] { LineChart.TYPE, LineChart.TYPE,LineChart.TYPE,LineChart.TYPE,LineChart.TYPE };  //Define chart type for each data!
        view = (GraphicalView) ChartFactory.getCombinedXYChartView(getBaseContext(), dataset, multiRenderer, types);
        multiRenderer.setClickEnabled(true);
        multiRenderer.setSelectableBuffer(10);


        view.setOnClickListener(new View.OnClickListener() {
            //Toast for to figure out which data and which coordinates!
            @Override
            public void onClick(View v) {

                SeriesSelection seriesSelection = view.getCurrentSeriesAndPoint();

                if (seriesSelection != null) {
                    int seriesIndex = seriesSelection.getSeriesIndex();
                    String selectedSeries="data1";
                    if(seriesIndex==0)
                        selectedSeries = "data1";
                    else
                        selectedSeries = "data2";
                    // Getting the clicked X and Y label
                    String xValue = xLabel[(int)seriesSelection.getXValue()];
                    // Getting the y value
                    int amount = (int) seriesSelection.getValue();  //  "y" value
                    Toast.makeText(
                            getBaseContext(),
                            selectedSeries + " -> " +  " x= " + xValue + " y= "+ amount,
                            Toast.LENGTH_SHORT).show();
                }
            }

        });

        // Adding the Combined Chart to the LinearLayout
        chartContainer.addView(view);
    }


    /*private class ChartTask extends AsyncTask<Void, String, Void> {

        // Generates dummy data in a non-ui thread
        @Override
        protected Void doInBackground(Void... params) {
            int i = 0;
            try{
                do{
                    String [] values = new String[2];
                    Random r = new Random();
                    int visits = r.nextInt(10);

                    values[0] = Integer.toString(i);
                    values[1] = Integer.toString(visits);

                    publishProgress(values);
                    Thread.sleep(500);
                    i++;
                }while(i<=15);
            }catch(Exception e){ }
            return null;
        }

        // Plotting generated data in the graph
        @Override
        protected void onProgressUpdate(String... values) {
            aSeries.add(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
            bSeries.add(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
            view.repaint();
        }

    }*/



/*
        thread = new Thread(){
            public void run(){
                for (int i = 0; 1<25; i++){
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Point p = MockData.getDataFromReceiver(i); //We got new data
                    addNewPoints(p); // add it to our graph
                    view.repaint();
                }
            }
        };
        thread.start();*/




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
