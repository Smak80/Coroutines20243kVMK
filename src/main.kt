import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.random.Random

suspend fun f(){
    delay(1100)
    println("Привет из функции f")
}

val evenFlow = flow {
    var v = 0
    while (true){
        emit(v)
        delay(500)
        v+=2
    }
}

fun main(): Unit = runBlocking{
    launch {
        withTimeout(25000) {
            evenFlow.collect {
                println(it)
            }
        }
    }
    val d = async {
        val res = Random.nextLong(100, 2001)
        println("Привет из async-корутины")
        delay(res)
        res
    }
    launch {
        delay(1000)
        println("Привет из корутины 1")
    }
    launch {
        f()
    }
    println("Привет из main")
    println("Значение из async: ${d.await()}")
}