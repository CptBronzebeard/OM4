import javafx.scene.chart.XYChart
import org.knowm.xchart.SwingWrapper
import org.knowm.xchart.XYChartBuilder
import org.knowm.xchart.XYSeries
import java.io.File
import java.io.FileWriter
import kotlin.math.PI

fun main() {
 /*   val fw = FileWriter("noise.txt")
    val noise = getNoise(-PI/2,100,PI/2,0.25)
    fw.append(noise.first.toString())
    fw.append("\n")
    fw.append(noise.second.toString())
    fw.flush()*/
    val chart = XYChartBuilder().width(600).height(500).build()
    val sine = getSin(-PI/2,2000, PI/2)
    chart.styler.defaultSeriesRenderStyle=XYSeries.XYSeriesRenderStyle.Line
    chart.addSeries("Sine",sine.first,sine.second)
    //val noise = getNoise(-PI/2,400,PI/2,0.125)
    val noiseSer = chart.addSeries("Noise", preGenNoiseX,preGenNoiseY)
    noiseSer.xySeriesRenderStyle = XYSeries.XYSeriesRenderStyle.Scatter
    val line = getLine(-PI/2, PI/2)
    val lineSer = chart.addSeries("Model", line.first,line.second)
    lineSer.lineWidth=10.0f
    SwingWrapper(chart).displayChart()
    println(getError())

}