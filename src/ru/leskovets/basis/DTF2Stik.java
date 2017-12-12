import org.knowm.xchart.*;
import org.knowm.xchart.demo.charts.ExampleChart;
import org.knowm.xchart.style.Styler;

import java.util.ArrayList;
import java.util.List;

class DTF2Stik implements ExampleChart<CategoryChart> {

    // Series
    public static List<Integer> x1Data = new ArrayList<Integer>();
    public static List<Double> y1Data = new ArrayList<Double>();

    public static void main(String[] args) throws Exception {

        final int DATA_SAMPLE = 1024;
        final double DELTA_K = 0.008;
        final int DATA_SAMPLE_MIN_ONE = DATA_SAMPLE - 1;
        final double DATA_SAMPLE_INV = 1.0 / (double) DATA_SAMPLE;
        final double PI = 3.1415926535;
        final double TWO_PI = 6.2831853;
        double[] xData = new double[DATA_SAMPLE];
        double[] yData = new double[DATA_SAMPLE];
        double[] DFT_Re = new double[DATA_SAMPLE];
        double[] DFT_Im = new double[DATA_SAMPLE];
        double[] wHz = new double[DATA_SAMPLE];
        double[] mod2 = new double[DATA_SAMPLE];
        double[] arct = new double[DATA_SAMPLE];

        double k = 0;
        int counter = 0;
        while (counter < DATA_SAMPLE) {
            xData[counter] = k;
            yData[counter] = 2 * Math.sin(k) - Math.sin(2 * k) + (0.6666666667) * Math.sin(3 * k) - (0.2) * Math.sin(10 * k);
            //System.out.print("x: " + xData[counter]);
            //System.out.println(" sin(x): " + yData[counter]);
            k = k + DELTA_K;
            counter++;
        }

        counter = 0;

        for (int k1 = 0; k1 < 30; k1++) {

            //wHz[k1] = k1;
            x1Data.add(k1);
            //System.out.print("counter: " + counter + "; ");

            for (int i = 0; i < DATA_SAMPLE_MIN_ONE; i++){
                //System.out.print("i: " + i + "; ");
                //System.out.print("cos(i): " + Math.cos(i) + "; ");
                DFT_Re[k1] += DATA_SAMPLE_INV * yData[i] * Math.cos(TWO_PI*DATA_SAMPLE_INV*k1*i);
                DFT_Im[k1] += (-1.0) * DATA_SAMPLE_INV * yData[i] * Math.sin(TWO_PI*DATA_SAMPLE_INV*k1*i);
                //System.out.println("DFT_Re[k1]: " + DFT_Re[k1] + "; ");

            }
            mod2[k1] = (Math.sqrt(DFT_Re[k1] * DFT_Re[k1] + DFT_Im[k1] * DFT_Im[k1])) / (2.0);
            y1Data.add(mod2[k1]);
            //System.out.println("DFT_Re: " + DFT_Re[k1] + "; ");
            //System.out.println("");
            //System.out.println(" DFT_Re: " + DFT_Re[counter]);
        }

        ExampleChart<CategoryChart> exampleChart = new DTF2Stik();
        CategoryChart chart = exampleChart.getChart();
        new SwingWrapper<CategoryChart>(chart).displayChart();

        // Create Chart
        //XYChart chart = QuickChart.getChart("Sample Chart", "X", "Y", "y(x)", wHz, DFT_Re);
        //XYChart chart = QuickChart.getChart("Sample Chart", "X", "Y", "y(x)", xData, yData);

        // Show it
        //new SwingWrapper(chart).displayChart();

    }

    @Override
    public CategoryChart getChart() {
        // Create Chart
        CategoryChart chart = new CategoryChartBuilder().width(1900).height(1000).title("Ak").build();

        // Customize Chart
        chart.getStyler().setChartTitleVisible(true);
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideSE);
        chart.getStyler().setDefaultSeriesRenderStyle(CategorySeries.CategorySeriesRenderStyle.Stick);

        chart.addSeries("data", x1Data, y1Data);

        return chart;
    }
}