import java.util.*
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

fun getSin(beg: Double, dots: Int, end: Double): Pair<List<Double>, List<Double>> {
    val step = (end - beg) / dots
    var cur = beg
    val x = listOf<Double>().toMutableList()
    val y = listOf<Double>().toMutableList()
    while (cur < end) {
        x.add(cur)
        y.add(sin(cur) + 2)
        cur += step
    }
    return Pair(x, y)
}

fun getNoise(beg: Double, dots: Int, end: Double, deviation: Double): Pair<List<Double>, List<Double>> {
    val tmp = getSin(beg, dots, end)
    val r = Random()
    val ys = tmp.second.map { it + r.nextGaussian() * deviation }
    return Pair(tmp.first, ys)
}

fun getA(): Double {
    val first = preGenNoiseX.size * preGenNoiseX.zip(
        preGenNoiseY,
        Double::times
    ).sum()
    val denom = first - preGenNoiseX.sum() * preGenNoiseY.sum()
    val numF = preGenNoiseX.map { it -> it.pow(2) }.sum()
    val numS = (preGenNoiseX.sum().pow(2))
    val num =
        preGenNoiseX.size * numF - numS
    return denom / num
}

fun getB(a: Double): Double {
    val denom = preGenNoiseY.sum() - a * preGenNoiseX.sum()
    val num = preGenNoiseX.size
    return denom / num.toDouble()
}

fun getLine(beg: Double, end: Double): Pair<List<Double>, List<Double>> {
    val a = getA()
    val func = getFunc(a, getB(a))
    return Pair(listOf(beg, end), listOf(func(beg), func(end)))
}

fun getFunc(a: Double, b: Double): (Double) -> Double {
    return { x -> a * x + b }
}

fun getError():Double {
    val a = getA()
    val tmp = sqrt(testNoiseY.mapIndexed{iter, it -> (it - getFunc(a,getB(a))(testNoiseX[iter])).pow(2)}.sum()/preGenNoiseX.size)
    return tmp
}

